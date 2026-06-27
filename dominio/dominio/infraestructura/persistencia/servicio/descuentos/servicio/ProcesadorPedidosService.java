package servicio;

import dominio.Pedido;
import infraestructura.persistencia.PedidoDAO;
import servicio.descuentos.ValidadorDescuentos;

class ValidadorUtil {
    public static boolean esCadenaInvalida(String valor) {
        return valor == null || valor.trim().isEmpty();
    }
    public static boolean esEmailInvalido(String email) {
        return email == null || !email.contains("@");
    }
}

public class ProcesadorPedidosService {
    private final PedidoDAO pedidoDAO;

    public ProcesadorPedidosService(PedidoDAO pedidoDAO) {
        this.pedidoDAO = pedidoDAO;
    }

    public void procesarPedido(Pedido pedido) {
        if (ValidadorUtil.esCadenaInvalida(pedido.getNombreCliente()) || 
            ValidadorUtil.esEmailInvalido(pedido.getEmailCliente())) {
            System.out.println("Error: Datos de cliente o email inválidos.");
            return;
        }

        double subtotal = 0;
        for (int i = 0; i < pedido.getProductos().size(); i++) {
            subtotal += pedido.getPrecios().get(i) * pedido.getCantidades().get(i);
        }

        double descuento = ValidadorDescuentos.obtenerDescuento(pedido.getTipoCliente(), subtotal);
        double impuesto = (subtotal - descuento) * 0.12;
        double total = subtotal - descuento + impuesto;

        pedidoDAO.guardar(pedido, total);
        System.out.println("[LOG] Pedido procesado exitosamente para " + pedido.getNombreCliente());
    }

    public void cancelarPedido(int idPedido, String nombreCliente, String emailCliente) {
        if (ValidadorUtil.esCadenaInvalida(nombreCliente) || ValidadorUtil.esEmailInvalido(emailCliente)) {
            System.out.println("Error: Datos de cliente o email inválidos.");
            return;
        }

        pedidoDAO.cancelar(idPedido);
        System.out.println("[LOG] Pedido #" + idPedido + " cancelado para " + nombreCliente);
    }
}
