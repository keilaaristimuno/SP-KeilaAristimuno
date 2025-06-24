
package models;
import java.time.LocalDate;
import excepciones.ProductoInvalidoException;

public class Medicamento extends ProductoFarmaceutico{
    
    private boolean requiereReceta;

    public Medicamento(String nombre, String dosis, LocalDate fechaVencimiento, boolean requiereReceta) {
        super(nombre, dosis, fechaVencimiento);
        
        if (fechaVencimiento.isBefore(LocalDate.now())) {
            throw new ProductoInvalidoException("No se puede cargar un producto ya vencido. Ingrese una fecha futura.");
        }
        //verifico si esta vacio o hay espacios
        if (nombre == null || nombre.isBlank()) {
            throw new ProductoInvalidoException("El nombre del medicamento no puede estar vacío.");
        }

        this.requiereReceta = requiereReceta;
    }

    public Medicamento() {
       
    }

    public boolean isRequiereReceta() {
        return requiereReceta;
    }

    public void setRequiereReceta(boolean requiereReceta) {
        this.requiereReceta = requiereReceta;
    }

    @Override
    public String toCSV() {
        return nombre + "," + dosis + "," + fechaVencimiento + "," + requiereReceta + ",Medicamento";
    }

    @Override
    public Medicamento fromCSV(String line) {
        String[] campos = line.split(",");
        String nombre = campos[0];
        String dosis = campos[1];
        LocalDate fecha = LocalDate.parse(campos[2]);
        boolean receta = Boolean.parseBoolean(campos[3]);

        return new Medicamento(nombre, dosis, fecha, receta);
    }

    @Override
    public String toString() {
        return super.toString() + " | Requiere receta: " + (requiereReceta ? "Sí" : "No");
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Medicamento))
            return false;
        Medicamento otro = (Medicamento) obj;
        return this.sonIgualesBase(otro) && this.requiereReceta == otro.requiereReceta;
    }
}
