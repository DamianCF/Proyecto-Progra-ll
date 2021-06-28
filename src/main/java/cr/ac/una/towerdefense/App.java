package cr.ac.una.towerdefense;

import cr.ac.una.towerdefense.util.FlowController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.image.Image;

/**
    UNIVERSIDAD NACIONAL
    SEDE REGIONAL BRUNCA
    DIVISIÓN DE CIENCIAS EXACTAS, NATURALES Y TECNOLOGÍA
    PROF. MÁSTER CARLOS CARRANZA BLANCO
    CURSO PROGRAMACIÓN II - I CICLO 2021
    * 
    Proyecto 2
    TowerDefense
    
    Estudiantes:
    Damian Cordero Fallas
    Ronald Blanco Navarro
 * 
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        //inicio de aplicacion y apertura del login
        FlowController.getInstance().InitializeFlow(stage, null);
        stage.getIcons().add(new Image("cr/ac/una/towerdefense/resources/fortress.png"));
        stage.setTitle("TowerDefense");        
        FlowController.getInstance().goViewInWindow("LoginView");
    }

   
    public static void main(String[] args) {
        launch();
    }

}