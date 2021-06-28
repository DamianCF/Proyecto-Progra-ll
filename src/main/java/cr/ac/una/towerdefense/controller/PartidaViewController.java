package cr.ac.una.towerdefense.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.towerdefense.clases.Nivel;
import cr.ac.una.towerdefense.model.PartidaDto;
import cr.ac.una.towerdefense.model.UsuarioDto;
import cr.ac.una.towerdefense.service.PartidaService;
import cr.ac.una.towerdefense.util.BindingUtils;
import cr.ac.una.towerdefense.util.FlowController;
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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import cr.ac.una.towerdefense.util.AppContext;
import cr.ac.una.towerdefense.service.UsuarioService;
import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * FXML Controller class
 * Clase utilizada para las configuracione en elementos de la partida 
 * y hacer mejoras a la ballesta, castillo, poderes, elixir
 *
 * @author Ronald Blanco - Damian Cordero
 */
public class PartidaViewController extends Controller implements Initializable {
    
    @FXML
    private AnchorPane root;
    //toggle group para escoger la ballesta
    @FXML
    private JFXRadioButton tgTipo1;
    @FXML
    private JFXRadioButton tgTipo2;
    @FXML
    private ToggleGroup tggTipoBallesta;
    
    //campos de imagenes donde se muestra el vatar y la ballesta selecionada por el jugador
    @FXML
    private ImageView imgTipoBallesta;
    public  MediaPlayer player;
    
    //botones
    @FXML
    private JFXButton btnIniciar;
    @FXML
    private JFXButton btnAtras;
    @FXML
    private JFXButton btnCcastillo;
    @FXML
    private JFXButton btnCballesta;
    @FXML
    private JFXButton btnCmeteoro;
    @FXML
    private JFXTextField txtLvLHielo;
    @FXML
    private JFXButton btnChielo;
    @FXML
    private JFXButton btnCelixir;
    
    //campos de texto y contenedores
     @FXML
    private JFXTextField txtCantElixir;
    @FXML
    private JFXTextField txtCantVidaCastillo;
    @FXML
    private JFXTextField txtPuntaje;
    @FXML
    private JFXTextField txtLvLCastillo;
    @FXML
    private JFXTextField txtLvLBallesta;
    @FXML
    private JFXTextField txtLvLMeteoro;
    @FXML
    private JFXTextField txtLvLElixir;
    @FXML
    private JFXTextField txtIdPartida;
    @FXML
    private JFXTextField txtDañoMeteoro;
    @FXML
    private JFXTextField txtRecargaMeteoro;
    @FXML
    private JFXTextField txtTiempoCongelado;
    @FXML
    private JFXTextField txtRecargaHielo;
    @FXML
    private JFXTextField txtDañoBallesta;
    
    private UsuarioDto usuarioDto;
    private PartidaDto partidaDto;
    private Integer monedas;    

    //objeto de Nivel que funciona como una clase que ayuda a determinar atributos de
    //objetos mejorables por el jugador
    Nivel nivel;     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // inicializacion de campos de texto y mas
        txtIdPartida.setTextFormatter(Formato.getInstance().integerFormat());
        txtCantElixir.setTextFormatter(Formato.getInstance().integerFormat());
        txtCantVidaCastillo.setTextFormatter(Formato.getInstance().integerFormat());
        txtPuntaje.setTextFormatter(Formato.getInstance().integerFormat());
        txtLvLCastillo.setTextFormatter(Formato.getInstance().integerFormat());
        txtLvLBallesta.setTextFormatter(Formato.getInstance().integerFormat());
        txtLvLMeteoro.setTextFormatter(Formato.getInstance().integerFormat());
        txtLvLHielo.setTextFormatter(Formato.getInstance().integerFormat());
        txtLvLElixir.setTextFormatter(Formato.getInstance().integerFormat());
        tgTipo1.setUserData("A");
        tgTipo2.setUserData("B");
        txtDañoMeteoro.setTextFormatter(Formato.getInstance().integerFormat());
        txtTiempoCongelado.setTextFormatter(Formato.getInstance().integerFormat());
        txtDañoBallesta.setTextFormatter(Formato.getInstance().integerFormat());
        
        txtCantElixir.setEditable(false);
        txtCantVidaCastillo.setEditable(false);
        txtPuntaje.setEditable(false);
        txtLvLBallesta.setEditable(false);
        txtLvLCastillo.setEditable(false);
        txtLvLElixir.setEditable(false);
        txtLvLHielo.setEditable(false);
        txtLvLMeteoro.setEditable(false);
        txtDañoMeteoro.setEditable(false);
        txtRecargaMeteoro.setEditable(false);
        txtTiempoCongelado.setEditable(false);
        txtRecargaHielo.setEditable(false);
        txtDañoBallesta.setEditable(false);
        
        usuarioDto= new UsuarioDto();
        partidaDto = new PartidaDto();
        nuevoPartida();
    }    

    @Override
    public void initialize() {
        // reproduccion de audio
        reproduce("menuInicio");
        
        ///comprobar si la partida fue anteriormente creada
        // si la partida no exite se limpia partidaDto y se setea la informacion de una partida nivel 1
        if(!cargarPartidaUsuario()){
            nuevoPartida();
            partidaDto.setNivelPartida("1");
            partidaDto.setNivelBallesta("1");
            partidaDto.setNivelCastillo("1");
            partidaDto.setNivelElixir("1");
            partidaDto.setNivelPoderMeteoro("1");
            partidaDto.setNivelPoderHielo("1");
            partidaDto.setMonedas("0");
        }
        
        // inicializacion de nivel con la informacion de la Partida traida desde base de datos
        nivel = new Nivel(partidaDto);
        nivel.determinarDificultad();
        
        // inicializacion de campos de texto 
        String str1 = Integer.toString(nivel.getVidaCastillo());
        txtCantVidaCastillo.setText(str1);
        String str2 = Integer.toString(nivel.getCantMaxElixir());
        txtCantElixir.setText(str2);
        String str3 = Integer.toString(nivel.getDañoFlecha());
        txtDañoBallesta.setText(str3);
        String str4 = Integer.toString(nivel.getDañoMeteoro());
        txtDañoMeteoro.setText(str4);
        
        String str5 = Double.toString(nivel.getDuracionRecargaMeteoro());
        txtRecargaMeteoro.setText(str5);
        String str6 = Integer.toString(nivel.getTiempoHielo());
        txtTiempoCongelado.setText(str6);
        String str7 = Double.toString(nivel.getDuracionRecargaHielo());
        txtRecargaHielo.setText(str7);

        actualizarCosteBotones();
    }
    
     public void reproduce(String nombSonido) {  /// reproduccion de un sonido en especifico 
        // creacion y reproduccion de archivo de audio 
        final String NOMBRE_ARCHIVO = "Audios/"+nombSonido+".mp3";
        File archivo = new File(NOMBRE_ARCHIVO);
        Media audio = new Media(archivo.toURI().toString());
        player = new MediaPlayer(audio);
        player.setVolume(0.1);
        player.play();
    }
     
    private void cargarUsuario() { // cargado de usuario desde base de datos
        // el usuario se busca con el id de usuario guardado en el AppContext
        UsuarioService service = new UsuarioService();
        Respuesta respuesta = service.getUsuario((Long)AppContext.getInstance().get("idUsuario"));
        if (respuesta.getEstado()) {
            usuarioDto = (UsuarioDto) respuesta.getResultado("Usuario");
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar usuario", getStage(), respuesta.getMensaje());
        }
    }    

    private void nuevoPartida(){ // limpiado de informacion para crear nueva partida
        unbindPartida();
        partidaDto = new PartidaDto();
        bindPartida(true);
        txtIdPartida.clear();
    }    
    
    private boolean cargarPartidaUsuario(){// cargado de Partida asignada a el usuario
     PartidaService service = new PartidaService();
     Respuesta respuesta = service.getPartidaPorUsuario((Long)AppContext.getInstance().get("idUsuario")); // id de usuario en AppContext
     if (respuesta.getEstado()) {
         unbindPartida();
         partidaDto = (PartidaDto) respuesta.getResultado("Partida");// tiene que coincidir DeportesService
         bindPartida(false);
         return true;///si encontro la partida para el usuario actual
     } else {
         return false;// no encontro la partida para el usuario actual
     }
 }
    
    private void bindPartida(Boolean nuevo) {// bindeo del partida con el partidaDTO
        if(!nuevo){
            txtIdPartida.textProperty().bind(partidaDto.id);
        }
        txtLvLBallesta.textProperty().bindBidirectional(partidaDto.nivelBallesta);
        txtLvLCastillo.textProperty().bindBidirectional(partidaDto.nivelCastillo);
        txtLvLElixir.textProperty().bindBidirectional(partidaDto.nivelElixir);
        txtLvLMeteoro.textProperty().bindBidirectional(partidaDto.nivelPoderMeteoro);
        txtLvLHielo.textProperty().bindBidirectional(partidaDto.nivelPoderHielo);
        txtPuntaje.textProperty().bindBidirectional(partidaDto.monedas);
        BindingUtils.bindToggleGroupToProperty(tggTipoBallesta, partidaDto.tipoBallesta);
        
         //este if se hizo debido que a la hora de hacer bind en la foto de la ballesta, esta no se muestra en el imageview
         // asignacion de apariencia en balleta segun informacion guardada en la partida
        if("A".equals(partidaDto.getTipoBallesta())){
            imgTipoBallesta.setImage(new Image("cr/ac/una/towerdefense/resources/ballesta.png"));
        }else if("B".equals(partidaDto.getTipoBallesta())){
            imgTipoBallesta.setImage(new Image("cr/ac/una/towerdefense/resources/ballesta2.png"));
        } 
    }
    
    private void unbindPartida() {//desbindeo del partida con el partidaDTO
        txtIdPartida.textProperty().unbind();
        txtLvLBallesta.textProperty().unbindBidirectional(partidaDto.nivelBallesta);
        txtLvLCastillo.textProperty().unbindBidirectional(partidaDto.nivelCastillo);
        txtLvLElixir.textProperty().unbindBidirectional(partidaDto.nivelElixir);
        txtLvLMeteoro.textProperty().unbindBidirectional(partidaDto.nivelPoderMeteoro);
        txtLvLHielo.textProperty().unbindBidirectional(partidaDto.nivelPoderHielo);
        txtPuntaje.textProperty().unbindBidirectional(partidaDto.monedas);
        BindingUtils.unbindToggleGroupToProperty(tggTipoBallesta, partidaDto.tipoBallesta);
    }  
    
    private void actualizarCosteBotones(){// Actualizacion de texto en costo de mejoras en botones
       Integer costeCastillo = nivel.getCosteCastillo();
       btnCcastillo.setText(costeCastillo.toString());
       
        Integer costeBallesta = nivel.getCosteBallesta();
       btnCballesta.setText(costeBallesta.toString());
       
        Integer costeMeteoro = nivel.getCosteMeteoro();
       btnCmeteoro.setText(costeMeteoro.toString());
       
        Integer costeHielo = nivel.getCosteHielo();
       btnChielo.setText(costeHielo.toString());
       
        Integer costeElixir = nivel.getCosteElixir();
       btnCelixir.setText(costeElixir.toString());
    }
 
    //----------------------------------EVENTOS---------------------------------------/
    @FXML
    private void onActiontgTipo1(ActionEvent event) {
        // asignacion de imagen a ballesta
        imgTipoBallesta.setImage(new Image("cr/ac/una/towerdefense/resources/ballesta.png"));
    }

    @FXML
    private void onActiontgTipo2(ActionEvent event) {
        // asignacion de imagen a ballesta
        imgTipoBallesta.setImage(new Image("cr/ac/una/towerdefense/resources/ballesta2.png"));
    }

    @FXML
    private void onActionbtnIniciar(ActionEvent event) {  
    //sirve para guardar los cambios de mejoras o combios de aspecto de ballesta
    // Y coontinua a la vista de Area de Juego
        cargarUsuario();
        partidaDto.setUsuario(usuarioDto);
        try {
                PartidaService service = new PartidaService();
                Respuesta respuesta = service.guardarPartida(partidaDto);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar partida", getStage(), respuesta.getMensaje());
                } else {
                    player.stop();
                    unbindPartida();
                    partidaDto = (PartidaDto) respuesta.getResultado("Partida");
                    bindPartida(false);
                    //aqui pasamos a la siguiente vista
                    AppContext.getInstance().set("idPartida", partidaDto.getId());
                    FlowController.getInstance().goView("AreaJuegoView");
                }
        } catch (Exception ex) {
            Logger.getLogger(PartidaViewController.class.getName()).log(Level.SEVERE, "Error guardando el partida.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar partida", getStage(), "Ocurrio un error guardando el partida.");
        }            
    }

    @FXML
    private void onActionbtnAtras(ActionEvent event) {
        // Volver a la vista principal
        FlowController.getInstance().goMain();
    }

    @FXML
    private void onActionbtnCcastillo(ActionEvent event) {
    // metodo para efectuar mejora de Catillo siempre que se posean las monedas suficientes
        if(nivel.getCosteCastillo()<=Integer.parseInt(txtPuntaje.getText())){
            if((Integer.parseInt(partidaDto.getNivelCastillo())<10)){
                Integer nivelCastillo =  Integer.parseInt(partidaDto.getNivelCastillo());//obtiene el nivel del castillo
                monedas = Integer.parseInt(txtPuntaje.getText())-nivel.getCosteCastillo();
                txtPuntaje.setText(monedas.toString());
                nivelCastillo=nivelCastillo+1;//se le suma 1 nivel al castillo
                partidaDto.setNivelCastillo(nivelCastillo.toString());//se setea el nivel en el dto
                nivel.configurarCastillo();//obtiene la vida maxima del castillo
                Integer vidaCastillo = nivel.getVidaCastillo();//se convierte el dato a int
                txtCantVidaCastillo.setText(vidaCastillo.toString());//se setea en el campo de texto
                actualizarCosteBotones();//se actualiza el valor en los botones(coste)
            }else{
                new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel de castillo", getStage(), "Maximo nivel");
            }
        }else{ 
          new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel de castillo", getStage(), "Monedas insuficientes");
        }
    }

    @FXML
    private void onActionbtnCballesta(ActionEvent event) {
    // metodo para efectuar mejora de Ballesta siempre que se posean las monedas suficientes        
        if(nivel.getCosteBallesta()<=Integer.parseInt(txtPuntaje.getText())){
            if((Integer.parseInt(partidaDto.getNivelBallesta())<10)){
                Integer nivelBallesta =  Integer.parseInt(partidaDto.getNivelBallesta());
                monedas = Integer.parseInt(txtPuntaje.getText())-nivel.getCosteBallesta();
                txtPuntaje.setText(monedas.toString());
                nivelBallesta=nivelBallesta+1;
                partidaDto.setNivelBallesta(nivelBallesta.toString());
                nivel.configurarBallesta();
                Integer dano = nivel.getDañoFlecha();
                txtDañoBallesta.setText(dano.toString());
                actualizarCosteBotones();
            }else{
                new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel de ballesta", getStage(), "Maximo nivel");
            }
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel de ballesta", getStage(), "Monedas insuficientes");
        }
    }

    @FXML
    private void onActionbtnCmeteoro(ActionEvent event) {
    // metodo para efectuar mejora de Meteoro siempre que se posean las monedas suficientes        
        if(nivel.getCosteMeteoro()<=Integer.parseInt(txtPuntaje.getText())){
            if((Integer.parseInt(partidaDto.getNivelPoderMeteoro())<10)){
                Integer nivelMeteoro =  Integer.parseInt(partidaDto.getNivelPoderMeteoro());
                monedas = Integer.parseInt(txtPuntaje.getText())-nivel.getCosteMeteoro();
                txtPuntaje.setText(monedas.toString());
                nivelMeteoro=nivelMeteoro+1;
                partidaDto.setNivelPoderMeteoro(nivelMeteoro.toString());
                nivel.configurarPoderMeteoro();
                Integer dano = nivel.getDañoMeteoro();
                txtDañoMeteoro.setText(dano.toString());
                Double time = nivel.getDuracionRecargaMeteoro();
                txtRecargaMeteoro.setText(time.toString());
                actualizarCosteBotones();
            }else{
                 new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel del poder de meteoro", getStage(), "Maximo nivel");
            }
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel del poder de meteoro", getStage(), "Monedas insuficientes");
        }
    }

    @FXML
    private void onActionbtnChielo(ActionEvent event) {
    // metodo para efectuar mejora de Hielo siempre que se posean las monedas suficientes         
        if(nivel.getCosteHielo()<=Integer.parseInt(txtPuntaje.getText())){
            if((Integer.parseInt(partidaDto.getNivelPoderHielo())<10)){
                Integer nivelHielo =  Integer.parseInt(partidaDto.getNivelPoderHielo());
                monedas = Integer.parseInt(txtPuntaje.getText())-nivel.getCosteHielo();
                txtPuntaje.setText(monedas.toString());
                nivelHielo=nivelHielo+1;
                partidaDto.setNivelPoderHielo(nivelHielo.toString());
                nivel.configuarPoderHielo();
                Integer congelado = nivel.getTiempoHielo();
                txtTiempoCongelado.setText(congelado.toString());
                Double recarga = nivel.getDuracionRecargaHielo();
                txtRecargaHielo.setText(recarga.toString());
                actualizarCosteBotones();
            }else{
                new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel del poder de hielo", getStage(), "Maximo nivel");
            }
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel del poder de hielo", getStage(), "Monedas insuficientes");
        }
    }

    @FXML
    private void onActionbtnCelixir(ActionEvent event) {
    // metodo para efectuar mejora de Elixir siempre que se posean las monedas suficientes         
        if(nivel.getCosteElixir()<=Integer.parseInt(txtPuntaje.getText())){
            if((Integer.parseInt(partidaDto.getNivelElixir())<10)){
                Integer nivelElixir =  Integer.parseInt(partidaDto.getNivelElixir());
                monedas = Integer.parseInt(txtPuntaje.getText())-nivel.getCosteElixir();
                txtPuntaje.setText(monedas.toString());
                nivelElixir=nivelElixir+1;
                partidaDto.setNivelElixir(nivelElixir.toString());
                nivel.configurarElixir();
                Integer cantidadMaxElixir = nivel.getCantMaxElixir();
                txtCantElixir.setText(cantidadMaxElixir.toString());
                actualizarCosteBotones();
            }else{
                new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel de capacidad de elixir", getStage(), "Maximo nivel");
            } 
        }else{
            new Mensaje().showModal(Alert.AlertType.ERROR, "Mejorar nivel de capacidad de elixir", getStage(), "Monedas insuficientes");         
        }
    }
}
