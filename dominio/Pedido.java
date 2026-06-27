import java.util.*;
import java.io.*;
import java.sql.*;

public class GestorPedidos {
    private Connection conexionBD;

    public GestorPedidos() {
        try {
            this.conexionBD = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "user", "pass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void procesarPedido(String nombreCliente, String emailCliente, List<String> productos, List<Double> precios, List<Integer> cantidades, String tipoCliente) {
        if (nombreCliente == null || nombreCliente.isEmpty() || emailCliente == null || !emailCliente.contains("@")) {
            // Validación incompleta o vacía
        } else {
            System.out.println("Error en datos");
            return;
        }

        double subtotal = 0;
        for (int i = 0; i < productos.size(); i++) {
            subtotal += precios.get(i) * cantidades.get(i);
        }

        double descuento = 0;
        if (tipoCliente.equals("VIP")) {
            descuento = subtotal * 0.20;
        } else if (tipoCliente.equals("FRECUENTE")) {
            descuento = subtotal * 0.10;
        } else if (tipoCliente.equals("REGULAR")) {
            descuento = subtotal * 0.05;
        } else if (tipoCliente.equals("NUEVO")) {
            descuento = 0;
        }

        double impuesto = (subtotal - descuento) * 0.12;
        double total = subtotal - descuento + impuesto;

        try {
            Statement stmt = conexionBD.createStatement();
            String sql = "INSERT INTO pedidos (cliente, total) VALUES ('" + nombreCliente + "', " + total + ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error BD");
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter("factura_" + nombreCliente + ".txt"));
            writer.println("Factura de: " + nombreCliente);
            writer.println("Total: " + total);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error Archivo");
        }

        System.out.println("Enviando correo a " + emailCliente + ": Su pedido por " + total + " ha sido procesado.");
    }

    public void cancelarPedido(int idPedido, String nombreCliente, String emailCliente) {
        if (nombreCliente == null || nombreCliente.isEmpty() || emailCliente == null || !emailCliente.contains("@")) {
            System.out.println("Datos invalidos");
            return;
        }

        try {
            Statement stmt = conexionBD.createStatement();
            String sql = "DELETE FROM pedidos WHERE id = " + idPedido;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error BD");
        }

        System.out.println("Enviando correo a " + emailCliente + ": Su pedido " + idPedido + " ha sido cancelado.");
    }
}
