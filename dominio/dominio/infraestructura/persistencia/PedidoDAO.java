package infraestructura.persistencia;
import dominio.Pedido;

public interface PedidoDAO {
    void guardar(Pedido pedido, double total);
    void cancelar(int idPedido);
}
