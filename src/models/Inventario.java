
package models;
import excepciones.ProductoInvalidoException;
import interfaces.ISerializableCsv;
import utils.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private ArrayList<ProductoFarmaceutico> productos;
    private final int DIAS_VENCIMIENTO = 30;
    
    public Inventario() {
        this.productos = new ArrayList<>();
    }

    public ArrayList<ProductoFarmaceutico> getProductos() {
        return productos;
    }

    public void agregar(ProductoFarmaceutico prod) {
        if (productos.contains(prod)) {
            throw new ProductoInvalidoException("El producto ya se encuentra en el inventario.");
        }
        productos.add(prod);
    }

    public void eliminar(ProductoFarmaceutico prod) {
        productos.remove(prod);
    }

    public List<Medicamento> getMedicamentosAVencer() {
        List<Medicamento> lista = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (ProductoFarmaceutico prod : productos) {
            if (prod instanceof Medicamento) {
                LocalDate venc = prod.getFechaVencimiento();
                if (!venc.isBefore(hoy) && venc.isBefore(hoy.plusDays(DIAS_VENCIMIENTO))) {
                    lista.add((Medicamento) prod);
                }
            }
        }
        return lista;
    }

    public void guardarCSV(String ruta) {
        ArrayList<ISerializableCsv> lista = new ArrayList<>();
        for (ProductoFarmaceutico prod : productos) {
            lista.add(prod);
        }
        CSVUtilsGeneric<ISerializableCsv> herramienta = new CSVUtilsGeneric<>();
        herramienta.escribirCSV(lista, ruta);
    }

    public void cargarCSV(String ruta) {
        CSVUtilsGeneric<ISerializableCsv> herramienta = new CSVUtilsGeneric<>();
        ArrayList<String> lineas = herramienta.leerCSV(ruta);
        productos.clear();

        for (String linea : lineas) {
            if (linea == null || linea.isBlank()) continue;
            String[] campos = linea.split(",");
            ProductoFarmaceutico prod = null;

            if (campos[4].equals("Medicamento")) {
                prod = new Medicamento().fromCSV(linea);
            } else if (campos[4].equals("Suplemento")) {
                prod = new Suplemento().fromCSV(linea);
            }

            if (prod != null) productos.add(prod);
        }
    }
    
    public void generarInformeVencimientos(String ruta) {
        List<Medicamento> vencenPronto = getMedicamentosAVencer();
        InformeVencimientos.generarInforme(vencenPronto, ruta);
    }
    
    
}
