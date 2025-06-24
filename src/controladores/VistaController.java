
package controladores;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import models.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class VistaController implements Initializable {
    @FXML
    private Button btnAgregar;

    @FXML
    private ListView<ProductoFarmaceutico> listViewEntidad;
    
    private Inventario inventario;
   
    private final String RUTA_CSV = "productos.csv";
    private final String RUTA_TXT = "vencimientos.txt";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.inventario = new Inventario();
        cargarCSV();
        refrescarVista();
    }

    private void refrescarVista() {
        listViewEntidad.getItems().setAll(inventario.getProductos());
    }

    @FXML
    public void agregarProducto() {
        try {
            TextInputDialog tipoDialog = new TextInputDialog();
            tipoDialog.setHeaderText("Ingrese el tipo de producto: Medicamento o Suplemento");
            Optional<String> tipoOpt = tipoDialog.showAndWait();
            if (tipoOpt.isEmpty()) return;

            String tipo = tipoOpt.get().toLowerCase();

            TextInputDialog nombreDialog = new TextInputDialog();
            nombreDialog.setHeaderText("Ingrese el nombre del producto:");
            Optional<String> nombreOpt = nombreDialog.showAndWait();
            if (nombreOpt.isEmpty()) return;

            TextInputDialog dosisDialog = new TextInputDialog();
            dosisDialog.setHeaderText("Ingrese la dosis (ej: 500 mg):");
            Optional<String> dosisOpt = dosisDialog.showAndWait();
            if (dosisOpt.isEmpty()) return;

            TextInputDialog fechaDialog = new TextInputDialog();
            fechaDialog.setHeaderText("Ingrese la fecha de vencimiento (AAAA-MM-DD):");
            Optional<String> fechaOpt = fechaDialog.showAndWait();
            if (fechaOpt.isEmpty()) return;

            LocalDate fecha = LocalDate.parse(fechaOpt.get());

            ProductoFarmaceutico prod = null;

            if (tipo.equals("medicamento")) {
                TextInputDialog recetaDialog = new TextInputDialog();
                recetaDialog.setHeaderText("¿Requiere receta?:");
                Optional<String> recetaOpt = recetaDialog.showAndWait();
                if (recetaOpt.isEmpty()) return;

                boolean receta = Boolean.parseBoolean(recetaOpt.get());
                prod = new Medicamento(nombreOpt.get(), dosisOpt.get(), fecha, receta);

            } else if (tipo.equals("suplemento")) {
                TextInputDialog objetivoDialog = new TextInputDialog();
                objetivoDialog.setHeaderText("Ingrese el objetivo del suplemento:");
                Optional<String> objetivoOpt = objetivoDialog.showAndWait();
                if (objetivoOpt.isEmpty()) return;

                prod = new Suplemento(nombreOpt.get(), dosisOpt.get(), fecha, objetivoOpt.get());

            } else {
                System.out.println("Tipo de producto no válido.");
                return;
            }

            inventario.agregar(prod);
            refrescarVista();

        } catch (Exception ex) {
            System.err.println("Error al agregar producto: " + ex.getMessage());
        }
    }

    @FXML
    public void modificarProducto() {
        ProductoFarmaceutico seleccionado = listViewEntidad.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
           //lo elimina primero,  no llege a que modifique como debe... 
            inventario.eliminar(seleccionado);
            refrescarVista();
            agregarProducto();
        } else {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("Modificar producto");
            alerta.setHeaderText(null);
            alerta.setContentText("Primero seleccioná un producto para modificar.");
            alerta.showAndWait();
        }
    }

    @FXML
    public void eliminarProducto() {
        ProductoFarmaceutico seleccionado = listViewEntidad.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Confirmar eliminación");
            alerta.setHeaderText("¿Estás segura que querés eliminar este producto?");
            alerta.setContentText(seleccionado.toString());

            Optional<ButtonType> resultado = alerta.showAndWait();

            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                inventario.eliminar(seleccionado);
                refrescarVista();
            }
        } else {
            Alert info = new Alert(Alert.AlertType.INFORMATION);
            info.setTitle("Eliminar");
            info.setHeaderText(null);
            info.setContentText("Primero seleccioná un producto de la lista.");
            info.showAndWait();
        }
    }

    @FXML
    public void guardarCSV() {
        inventario.guardarCSV(RUTA_CSV);
    }

    @FXML
    public void generarInformeVencimientos() {
        inventario.generarInformeVencimientos(RUTA_TXT);
    }

    private void cargarCSV() {
        inventario.cargarCSV(RUTA_CSV);
    }
    
    
}
