
package sp_aristimuno_keila;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controladores.VistaController;


public class Main extends Application{

    @Override
    public void start(Stage stage) throws Exception { 
        
        //Cargo la vista de fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/vista.fxml"));
        
        //Creo la escena
        Scene scene = new Scene(loader.load()); 
        
        VistaController controlador = loader.getController();
        
        stage.setTitle("Farmacia Don Alberto");
        //Seteo la escena en el stage
        stage.setScene(scene);
        //bloquea que cambie el tamaño de la ventana el usuario
        stage.setResizable(false);
        
         // Guardar al cerrar
        stage.setOnCloseRequest(e -> controlador.guardarCSV());
        
        stage.show();
    }
    
    public static void main(String[] args) {
        Application.launch();

        }

}