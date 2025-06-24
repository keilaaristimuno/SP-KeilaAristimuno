
package models;
import java.time.LocalDate;
import excepciones.ProductoInvalidoException;

public class Suplemento extends ProductoFarmaceutico{
    private String objetivo;

    public Suplemento(String nombre, String dosis, LocalDate fechaVencimiento, String objetivo) {
        super(nombre, dosis, fechaVencimiento);

        if (fechaVencimiento.isBefore(LocalDate.now())) {
            throw new ProductoInvalidoException("La fecha de vencimiento no puede ser anterior a hoy.");
        }

        if (objetivo == null || objetivo.isBlank()) {
            throw new ProductoInvalidoException("El objetivo del suplemento no puede estar vacío.");
        }

        this.objetivo = objetivo;
    }

    public Suplemento() {

    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public String toCSV() {
        return nombre + "," + dosis + "," + fechaVencimiento + "," + objetivo + ",Suplemento";
    }

    @Override
    public Suplemento fromCSV(String line) {
        String[] campos = line.split(",");
        String nombre = campos[0];
        String dosis = campos[1];
        LocalDate fecha = LocalDate.parse(campos[2]);
        String objetivo = campos[3];

        return new Suplemento(nombre, dosis, fecha, objetivo);
    }

    @Override
    public String toString() {
        return super.toString() + " | Objetivo: " + objetivo;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Suplemento)) 
            return false;
        Suplemento otro = (Suplemento) obj;
        return this.sonIgualesBase(otro) && this.objetivo.equals(otro.objetivo);
        }
}
