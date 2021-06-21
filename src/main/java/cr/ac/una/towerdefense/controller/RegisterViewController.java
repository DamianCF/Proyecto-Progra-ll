package cr.ac.una.towerdefense.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.towerdefense.model.UsuarioDto;
import cr.ac.una.towerdefense.service.UsuarioService;
import cr.ac.una.towerdefense.util.Formato;
import cr.ac.una.towerdefense.util.Mensaje;
import cr.ac.una.towerdefense.util.Respuesta;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class RegisterViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtContraseña;
    @FXML
    private JFXPasswordField txtContraseña1;
    @FXML
    private JFXTextField txtIdUsuario;
    @FXML
    private JFXButton btnAtras;
    @FXML
    private JFXButton btnConfirmar;

    private UsuarioDto usuarioDto;
    @FXML
    private ImageView imgUsuario;
    
    /**
     * Initializes the controller class.
     */
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtIdUsuario.setTextFormatter(Formato.getInstance().integerFormat());
        txtUsuario.setTextFormatter(Formato.getInstance().letrasFormat(30));          
        txtUsuario.setTextFormatter(Formato.getInstance().letrasFormat(30));        
        usuarioDto =new UsuarioDto();
        nuevoUsuario();
    }    

    @Override
    public void initialize() {
    }


    //Metodos y bindeos
    private void nuevoUsuario(){
        unbindUsuario();
        usuarioDto = new UsuarioDto();
        bindUsuario(true); 
        txtIdUsuario.clear();
    }
 
    private void bindUsuario(Boolean nuevo){
        if(!nuevo){
            txtIdUsuario.textProperty().bind(usuarioDto.id);
        }        
       txtUsuario.textProperty().bindBidirectional(usuarioDto.usuario);
       txtContraseña.textProperty().bindBidirectional(usuarioDto.clave);
    }
    
    private void unbindUsuario(){
       txtIdUsuario.textProperty().unbind();
       txtUsuario.textProperty().unbindBidirectional(usuarioDto.usuario);
       txtContraseña.textProperty().unbindBidirectional(usuarioDto.clave);
    }    

  
    //EVENTOS
    @FXML
    private void onActionbtnAtras(ActionEvent event) {
        getStage().close();
    }
    
    @FXML
    private void onActionbtnConfirmar(ActionEvent event) {
        try {
            if (txtUsuario.getText() == null || txtUsuario.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Creación de usuario", getStage(), "Es necesario digitar un usuario para ingresar al sistema.");
            } else if (txtContraseña.getText() == null ||txtContraseña.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Creación de usuario", (Stage) btnConfirmar.getScene().getWindow(), "Es necesario digitar la clave para ingresar al sistema.");
            }else if (txtContraseña1.getText() == null ||txtContraseña1.getText().isEmpty()) {
                new Mensaje().showModal(Alert.AlertType.ERROR, "Creación de usuario", (Stage) btnConfirmar.getScene().getWindow(), "Es necesario digitar la clave para ingresar al sistema.");
            }else if(txtContraseña.getText() == null ? txtContraseña1.getText() == null : txtContraseña.getText().equals(txtContraseña1.getText())) {
        
               Image img = new Image("cr/ac/una/towerdefense/resources/fortress.png");
               usuarioDto.setAvatar(img);           
               usuarioDto.getAvatar();
               
                UsuarioService service= new UsuarioService();
                Respuesta respuesta = service.guardarUsuario(usuarioDto);
                
                    if (!respuesta.getEstado()) {
                        new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar deporte", getStage(), respuesta.getMensaje());
                    } else {
                        unbindUsuario();
                        usuarioDto = (UsuarioDto) respuesta.getResultado("Usuario");
                        bindUsuario(false);//false
                        new Mensaje().showModal(Alert.AlertType.INFORMATION, "Crear usuario", getStage(), "Usuario creado correctamente.");
                        ((Stage)btnConfirmar.getScene().getWindow()).close(); 
                    }
            }else{
                txtContraseña.clear();
                txtContraseña1.clear();
                new Mensaje().showModal(Alert.AlertType.INFORMATION, "Creación de usuario", (Stage) btnConfirmar.getScene().getWindow(), "Las contraseñas digitadas no coinciden, verifique e intente de nuevo");
                }
            } catch (Exception ex) {
                Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
            }       
        }
}
