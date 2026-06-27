package servicio.descuentos;
import java.util.HashMap;
import java.util.Map;

interface EstrategiaDescuento {
    double calcular(double subtotal);
}

class DescuentoVIP implements EstrategiaDescuento {
    @Override public double calcular(double subtotal) { return subtotal * 0.20; }
}
class DescuentoFrecuente implements EstrategiaDescuento {
    @Override public double calcular(double subtotal) { return subtotal * 0.10; }
}
class DescuentoRegular implements EstrategiaDescuento {
    @Override public double calcular(double subtotal) { return subtotal * 0.05; }
}
class DescuentoNuevo implements EstrategiaDescuento {
    @Override public double calcular(double subtotal) { return 0.0; }
}

public class ValidadorDescuentos {
    private static final Map<String, EstrategiaDescuento> estrategias = new HashMap<>();

    static {
        estrategias.put("VIP", new DescuentoVIP());
        estrategias.put("FRECUENTE", new DescuentoFrecuente());
        estrategias.put("REGULAR", new DescuentoRegular());
        estrategias.put("NUEVO", new DescuentoNuevo());
    }

    public static double obtenerDescuento(String tipoCliente, double subtotal) {
        EstrategiaDescuento estrategia = estrategias.getOrDefault(
            tipoCliente != null ? tipoCliente.toUpperCase() : "NUEVO", 
            new DescuentoNuevo()
        );
        return estrategia.calcular(subtotal);
    }
}
