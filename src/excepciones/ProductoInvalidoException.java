
package excepciones;


public class ProductoInvalidoException extends RuntimeException{
    public ProductoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
