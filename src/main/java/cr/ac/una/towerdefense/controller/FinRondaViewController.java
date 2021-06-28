package cr.ac.una.towerdefense.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.towerdefense.util.FlowController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Damian Cordero - Ronald Blanco
 */
public class FinRondaViewController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private ImageView imgGenerica;
    @FXML
    private JFXButton btnGenerico;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @Override
    public void initialize() {
    }
    
    public void derrota(){
        // asignacion de imagen cuando el jugador pierda la partida
        Image img1 = new Image("cr/ac/una/towerdefense/resources/derrota.png");
        imgGenerica.setImage(img1);
        btnGenerico.setText("Reintentar");
    }
    
    public void victoria(){
        // asignacion de imagen cuando el jugador gana la partida
        Image img1 = new Image("cr/ac/una/towerdefense/resources/victoria.png");
        imgGenerica.setImage(img1);
        btnGenerico.setText("Siguiente nivel");
    }

    @FXML
    private void onActionbtnGenerico(ActionEvent event) {
        // Pasar a vista de Partida
        FlowController.getInstance().goView("PartidaView");
    }
}
