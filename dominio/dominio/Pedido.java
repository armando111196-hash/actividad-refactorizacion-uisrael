package dominio;
import java.util.List;

public class Pedido {
    private final String nombreCliente;
    private final String emailCliente;
    private final List<String> productos;
    private final List<Double> precios;
    private final List<Integer> cantidades;
    private final String tipoCliente;

    public Pedido(String nombreCliente, String emailCliente, List<String> productos, 
                  List<Double> precios, List<Integer> cantidades, String tipoCliente) {
        this.nombreCliente = nombreCliente;
        this.emailCliente = emailCliente;
        this.productos = productos;
        this.precios = precios;
        this.cantidades = cantidades;
        this.tipoCliente = tipoCliente;
    }

    public String getNombreCliente() { return nombreCliente; }
    public String getEmailCliente() { return emailCliente; }
    public List<String> getProductos() { return productos; }
    public List<Double> getPrecios() { return precios; }
    public List<Integer> getCantidades() { return cantidades; }
    public String getTipoCliente() { return tipoCliente; }
}
