package cr.ac.una.towerdefense.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.towerdefense.model.UsuarioDto;
import cr.ac.una.towerdefense.service.UsuarioService;
import cr.ac.una.towerdefense.util.AppContext;
import cr.ac.una.towerdefense.util.FlowController;
import cr.ac.una.towerdefense.util.Mensaje;
import cr.ac.una.towerdefense.util.Respuesta;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class LoginViewController extends Controller implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private Hyperlink btnRegistrar;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXButton btnIngresar;
    @FXML
    private JFXPasswordField txtClave;
    private UsuarioDto usuarioDto;
    private List<Node> requeridos = new ArrayList<>();

   //------------------------------------------------------//
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
    }    

    @Override
    public void initialize() {
        txtUsuario.clear();
        txtClave.clear(); 
        
    }
    
    //METODOS
    
    
        
    void validarCamposRequeridos(){
        
        // validacion basica por si hay algun campo vacio
        if (txtUsuario.getText() == null || txtUsuario.getText().isEmpty()) {//verifica si el campo de usaario contiene texto
            new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", getStage(), "Espacios requeridos necesarios.");
        } else if (txtClave.getText() == null ||txtClave.getText().isEmpty()) {//verifica si el campo de clave contiene texto
            new Mensaje().showModal(Alert.AlertType.ERROR, "Validación de usuario", (Stage) btnIngresar.getScene().getWindow(), "Espacios requeridos necesarios.");
        } else {
            validarUsuario(txtUsuario.getText(), txtClave.getText());//si los dos campos de texxto contienen texto invoca el metodo que verifica el usuario en la base de datos
            txtUsuario.clear();
            txtClave.clear();
        }
    }
    
    
    void validarUsuario(String usuario, String clave){
        
        //metodo que recive el usuario y clave digitada por el usuario y lo busca en la base de datos
        UsuarioService user = new UsuarioService();
        Respuesta respuesta = user.getUsuario(usuario, clave);
        if (respuesta.getEstado()) {
            usuarioDto = (UsuarioDto) respuesta.getResultado("Usuario");// tiene que coincidir con un una parte en especifico en UsuarioService
            
            AppContext.getInstance().set("idUsuario", usuarioDto.getId());
            
            ((Stage) btnIngresar.getScene().getWindow()).close();      
            FlowController.getInstance().goMain();
            
          
            
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar usuario", getStage(), respuesta.getMensaje());
        }
    }

    public void indicarRequeridos(){
        requeridos.clear();
        requeridos.addAll(Arrays.asList(txtUsuario,txtClave));
        
    }

    public String validarRequeridos() {
        Boolean validos = true;
        String invalidos = "";
        for (Node node : requeridos) {
            if (node instanceof JFXTextField && ((JFXTextField) node).getText() == null) { //el validate define si el campo esta vacio 
                if (validos) {
                    invalidos += ((JFXTextField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXTextField) node).getPromptText();
                }
                validos = false;
            } else if (node instanceof JFXPasswordField && !((JFXPasswordField) node).validate()) {
                if (validos) {
                    invalidos += ((JFXPasswordField) node).getPromptText();
                } else {
                    invalidos += "," + ((JFXPasswordField) node).getPromptText();
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


//EVENTOS

    @FXML
    void onActionbtnRegistrar(ActionEvent event) {
        
        FlowController.getInstance().goViewInWindowModal("RegisterView", getStage(),false);
       
    }
    
    @FXML
    void onActionbtnIngresar(ActionEvent event) {
       try { 
           validarCamposRequeridos();
           
        } catch (Exception ex) {
           Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, "Error ingresando.", ex);
        }
    }
}



