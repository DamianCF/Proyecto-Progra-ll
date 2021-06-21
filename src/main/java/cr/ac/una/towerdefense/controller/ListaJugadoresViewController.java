/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.controller;

import com.jfoenix.controls.JFXButton;
import cr.ac.una.towerdefense.model.PartidaDto;
import cr.ac.una.towerdefense.model.UsuarioDto;
import cr.ac.una.towerdefense.service.PartidaService;
import cr.ac.una.towerdefense.service.UsuarioService;
import cr.ac.una.towerdefense.util.FlowController;
import cr.ac.una.towerdefense.util.Mensaje;
import cr.ac.una.towerdefense.util.Respuesta;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;


/**
 * FXML Controller class
 *
 * @author damia
 */
public class ListaJugadoresViewController extends Controller implements Initializable {

    private UsuarioDto usuarioDto;
    private PartidaDto partidaDto;
    @FXML
    private TableColumn<UsuarioDto, ImageView> tbcAvatar;
    @FXML
    private TableColumn<UsuarioDto, String> tbcIdJugador;
    @FXML
    private TableColumn<PartidaDto, String> tbcNivel;
    @FXML
    private TableColumn<UsuarioDto, String> tbcNombreJugador;
    @FXML
    private TableColumn<PartidaDto, String> tbcPuntaje;
    @FXML
    private JFXButton btnAtras;
    @FXML
    private TableView<UsuarioDto> tbvUsuarios;
    @FXML
    private TableView<PartidaDto> tbvPartidas;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @Override
    public void initialize() {

        usuarioDto= new UsuarioDto();
        partidaDto = new PartidaDto();

        tbcIdJugador.setCellValueFactory(cd->cd.getValue().id);
        tbcNombreJugador.setCellValueFactory(cd->cd.getValue().usuario);
        tbcNivel.setCellValueFactory(cd->cd.getValue().nivel);        
        tbcPuntaje.setCellValueFactory(cd->cd.getValue().monedas);

        cargarUsuarios();

    }
    
    
    private void cargarUsuarios() {
        UsuarioService service = new UsuarioService();
        Respuesta respuesta = service.getUsuarios();

        if (respuesta.getEstado()) {
            
            List<UsuarioDto> usuariosDto = new ArrayList<>();
            usuariosDto = (List<UsuarioDto>)respuesta.getResultado("Usuarios");// tiene que coincidir con un una parte en especifico en TorneosService
            
            ObservableList<UsuarioDto> usuariosObserbables =  FXCollections.observableArrayList();
            ObservableList<PartidaDto> partidaUsuariosObserbables =  FXCollections.observableArrayList();
            

            usuariosDto.forEach((UsuarioDto usuario) -> { 
                usuariosObserbables.add(usuario);
                
                        PartidaService service1 = new PartidaService();
                        Respuesta respuesta1 = service1.getPartidaPorUsuario(usuario.getId());

                        if (respuesta1.getEstado()) {
                            partidaDto = new PartidaDto();
                            partidaDto = (PartidaDto) respuesta1.getResultado("Partida");// tiene que coincidir DeportesService
                            partidaUsuariosObserbables.add(partidaDto);
                        }else{
                            partidaDto = new PartidaDto();
                            partidaUsuariosObserbables.add(partidaDto);
                        } 
             });            
            
            tbvUsuarios.setItems(usuariosObserbables);//se muestra lista empleados mas un bindeo
            tbvUsuarios.refresh(); 
            tbvPartidas.setItems(partidaUsuariosObserbables);//se muestra lista empleados mas un bindeo
            tbvPartidas.refresh(); 
            
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar usuario", getStage(), respuesta.getMensaje());
        }
    } 

    @FXML
    private void onActionbtnAtras(ActionEvent event) {
        FlowController.getInstance().goMain();
    }

    
}
