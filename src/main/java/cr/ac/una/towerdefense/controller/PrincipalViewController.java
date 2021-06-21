/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.towerdefense.util.FlowController;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author damia
 */
public class PrincipalViewController extends Controller implements Initializable {

    @FXML
    private BorderPane root;
    @FXML
    private JFXButton btnIniciar;
    @FXML
    private JFXButton btnAyuda;
    @FXML
    private JFXButton btnAcercaDe;
    @FXML
    private JFXButton btnSalir;
    @FXML
    private JFXButton btnPerfil;
   
    @FXML
    private JFXButton btnJugadores;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

//       root.setOnMouseMoved((event) ->{
//            ((Stage) root.getScene().getWindow()).setFullScreen(true);
//       });
       
    }    

    @Override
    public void initialize() {
       
    }
    
    @FXML
    private void onActionBtnIniciar(ActionEvent event) {
        FlowController.getInstance().goView("PartidaView");
    }

    @FXML
    private void onActionBtnAyuda(ActionEvent event) {
    }

    @FXML
    private void onActionBtnAcercaDe(ActionEvent event) {
    }

    @FXML
    private void onActionBtnSalir(ActionEvent event) {
        ((Stage)btnSalir.getScene().getWindow()).close();
    }

    @FXML
    private void onActionbtnPerfil(ActionEvent event) {
        FlowController.getInstance().goView("UsuarioView");
    }

    @FXML
    private void onActionbtnJugadores(ActionEvent event) {
        FlowController.getInstance().goView("ListaJugadoresView");
    }
    
}
