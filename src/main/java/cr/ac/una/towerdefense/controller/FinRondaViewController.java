/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author damia
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
        // TODO
    }    

    @Override
    public void initialize() {
        
    }
    
    public void derrota(){
        Image img1 = new Image("cr/ac/una/towerdefense/resources/derrota.png");
        imgGenerica.setImage(img1);
        btnGenerico.setText("Reintentar");
    }
    
    public void victoria(){
        Image img1 = new Image("cr/ac/una/towerdefense/resources/victoria.png");
        imgGenerica.setImage(img1);
        btnGenerico.setText("Siguiente nivel");
    }

    @FXML
    private void onActionbtnGenerico(ActionEvent event) {
        FlowController.getInstance().goView("PartidaView");
    }
    
}
