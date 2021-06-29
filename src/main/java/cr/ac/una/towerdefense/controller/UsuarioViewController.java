/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.towerdefense.model.PartidaDto;
import cr.ac.una.towerdefense.model.UsuarioDto;
import cr.ac.una.towerdefense.service.PartidaService;
import cr.ac.una.towerdefense.service.UsuarioService;
import cr.ac.una.towerdefense.util.AppContext;
import cr.ac.una.towerdefense.util.FlowController;
import cr.ac.una.towerdefense.util.Formato;
import cr.ac.una.towerdefense.util.Mensaje;
import cr.ac.una.towerdefense.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ronald Blanco - Damian Cordero
 */
public class UsuarioViewController extends Controller implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private HBox hboxAvatares;
    @FXML
    private JFXButton btnAvatar1;
    @FXML
    private ImageView imgAvatar1;
    @FXML
    private JFXButton btnAvatar3;
    @FXML
    private ImageView imgAvatar3;
    @FXML
    private JFXButton btnAvatar2;
    @FXML
    private ImageView imgAvatar2;
    @FXML
    private JFXButton btnAvatar4;
     @FXML
    private JFXButton btnAvatar5;
    @FXML
    private ImageView imgAvatar5;
    @FXML
    private JFXButton btnAvatar6;
    @FXML
    private ImageView imgAvatar6;
    @FXML
    private ImageView imgAvatar4;
    private JFXCheckBox chkCambiarAvatar;
    @FXML
    private ImageView imgAvatarSelec;
    @FXML
    private JFXButton btnActualizar;
    @FXML
    private VBox vboxAvatares;
    @FXML
    private JFXButton btnAtras;
    
    private UsuarioDto usuarioDto;
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXTextField txtClave;
    // Lista de campos requeridos para guardar informacion del usuario
    List<Node> requeridos= new ArrayList<>(); 
    @FXML
    private JFXButton btnEliminar;
    private PartidaDto partidaDto;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtId.setTextFormatter(Formato.getInstance().integerFormat());
        indicarRequeridos();
    }    

    @Override
    public void initialize() {
        usuarioDto = new UsuarioDto();
        // cargado del usuario ingresado anteriormente en el login desde AppContext
        cargarUsuario((Long)AppContext.getInstance().get("idUsuario"));
        partidaDto = new PartidaDto();
        
      
    }

    @FXML
   private void onActionbtnAvatar1(ActionEvent event) {
       // seleccionado de avatar 1
        imgAvatarSelec.setImage(imgAvatar1.getImage());
    }

    @FXML
    private void onActionbtnAvatar2(ActionEvent event) {
       // seleccionado de avatar 2
        imgAvatarSelec.setImage(imgAvatar2.getImage());
    }

    @FXML
    private void onActionbtnAvatar3(ActionEvent event) {
       // seleccionado de avatar 3       
        imgAvatarSelec.setImage(imgAvatar3.getImage());
    }
    
    @FXML
    private void onActionbtnAvatar4(ActionEvent event) {
       // seleccionado de avatar 4
        imgAvatarSelec.setImage(imgAvatar4.getImage());
    }
    
    @FXML
    private void onActionbtnAvatar5(ActionEvent event) {
       // seleccionado de avatar 5        
        imgAvatarSelec.setImage(imgAvatar5.getImage());
    }

    @FXML
    private void onActionbtnAvatar6(ActionEvent event) {
       // seleccionado de avatar 6        
        imgAvatarSelec.setImage(imgAvatar6.getImage());
    }

    private void onActionchkCambiarAvatar(ActionEvent event) {// cambio de avatar de usuario
        if(chkCambiarAvatar.isSelected()==true){
            vboxAvatares.setDisable(false);
        }else if(chkCambiarAvatar.isSelected()==false){
            vboxAvatares.setDisable(true);
        }
    }

    @FXML
    private void onActionbtnActualizar(ActionEvent event) {
        // guardado de la informacion actualizada del usuario
        try {
                    String invalidos = validarRequeridos();
                    if (!invalidos.isEmpty()) {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), invalidos);
                    } else {
                        UsuarioService service = new UsuarioService();
                        Respuesta respuesta = service.guardarUsuario(usuarioDto);
                        if (!respuesta.getEstado()) {
                            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), respuesta.getMensaje());
                        } else {
                            unbindUsuario();
                            usuarioDto = (UsuarioDto) respuesta.getResultado("Usuario");
                            bindUsuario(false);
                            new Mensaje().showModal(Alert.AlertType.INFORMATION, "Guardar usuario", getStage(), "usuario actualizado correctamente.");
                        }
                    }
                } catch (Exception ex) {
                    Logger.getLogger(UsuarioViewController.class.getName()).log(Level.SEVERE, "Error guardando el usuario.", ex);
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar usuario", getStage(), "Ocurrio un error guardando el usuario.");
                }
    }

     public void indicarRequeridos(){
       // requeridos.clear();
        requeridos.addAll(Arrays.asList(txtUsuario,txtClave,imgAvatarSelec));//lista de requeridos para guardar un deporte
    }
      
    private void bindUsuario(Boolean nuevo){
        if(!nuevo){
          txtId.textProperty().bind(usuarioDto.id);
        }
        txtUsuario.textProperty().bindBidirectional(usuarioDto.usuario);
        txtClave.textProperty().bindBidirectional(usuarioDto.clave);
        imgAvatarSelec.imageProperty().bindBidirectional(usuarioDto.avatar);
    }
    
       private void unbindUsuario(){
        txtId.textProperty().unbind();
        txtUsuario.textProperty().unbindBidirectional(usuarioDto.usuario);
        txtClave.textProperty().unbindBidirectional(usuarioDto.clave);
        imgAvatarSelec.imageProperty().unbindBidirectional(usuarioDto.avatar);
    }
       
    private void cargarUsuario(Long id) {
        // cargdado del usuario desde la base de datos
        UsuarioService service = new UsuarioService();
        Respuesta respuesta = service.getUsuario(id);
        if (respuesta.getEstado()) {
            unbindUsuario();
            usuarioDto = (UsuarioDto) respuesta.getResultado("Usuario");// tiene que coincidir con un una parte en especifico en TorneosService
            bindUsuario(false);
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar usuario", getStage(), respuesta.getMensaje());
        }
    } 
    
    @FXML
    private void onActionbtnAtras(ActionEvent event) {
        // volver a la vista principal
         FlowController.getInstance().goMain();
    }

    private void cargarUsurio(){
       //txtId.setText();
    }
    
    public String validarRequeridos() {  
        // validacion de los campos requeridos para guardar informacion del usuario
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && ((JFXTextField) node).getText() == null) {
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof ImageView && ((ImageView) node).getImage()==null) { //ver como validar problemas con la imagen al comprobar si es un imageview y que no esta vacia
                if (validos) {
                    invalidos += ((ImageView) node).getAccessibleText();
                } else {
                    invalidos += "," + ((ImageView) node).getAccessibleText();
                }
                validos = false;
            }
        }
        if (validos) {
            return "";
        } else {
            return "Campos requeridos o con problemas de formato [" + invalidos + "].";
        }
    }

    @FXML
    private void onActionbtnEliminar(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("ADVERTENCIA");
        alert.setContentText("Perderás el progreso en esta cuenta (El juego se cerrará)");
        Optional<ButtonType> action = alert.showAndWait();
   
        if (action.get() == ButtonType.OK) {
            UsuarioService service2 = new UsuarioService();
            PartidaService service = new PartidaService();
            Respuesta respuesta = service.getPartidaPorUsuario((Long)AppContext.getInstance().get("idUsuario")); // id de usuario en AppContext
            
            if (respuesta.getEstado()) {
                partidaDto = (PartidaDto) respuesta.getResultado("Partida");
                service.eliminarPartida(partidaDto.getId());
            }
            service2.eliminarUsuario((Long)AppContext.getInstance().get("idUsuario"));
            ((Stage) btnEliminar.getScene().getWindow()).close();
        }
    }
}
