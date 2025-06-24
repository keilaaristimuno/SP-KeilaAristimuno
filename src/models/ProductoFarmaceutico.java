
package models;
import java.time.LocalDate;
import interfaces.ISerializableCsv;

public abstract class ProductoFarmaceutico implements ISerializableCsv{
    protected String nombre;
    protected String dosis;
    protected LocalDate fechaVencimiento;

    public ProductoFarmaceutico(String nombre, String dosis, LocalDate fechaVencimiento) {
        this.nombre = nombre;
        this.dosis = dosis;
        this.fechaVencimiento = fechaVencimiento;
    }

    public ProductoFarmaceutico() {
       
    }

    public String getNombre() { 
        return nombre; 
    }
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }
    
    public String getDosis() { 
        return dosis; 
    }
    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public LocalDate getFechaVencimiento() { 
        return fechaVencimiento; 
    }
    public void setFechaVencimiento(LocalDate fechaVencimiento) { 
        this.fechaVencimiento = fechaVencimiento; 
    }

    
    @Override
    public String toString() {
        return nombre + " - " + dosis + " (Vence: " + fechaVencimiento + ")";
    }
    
    protected boolean sonIgualesBase(ProductoFarmaceutico otro) {
    return this.nombre.equals(otro.nombre) &&
           this.dosis.equals(otro.dosis) &&
           this.fechaVencimiento.equals(otro.fechaVencimiento);
}
    
}
