package cr.ac.una.towerdefense.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import cr.ac.una.towerdefense.clases.Monstruo;
import cr.ac.una.towerdefense.clases.Nivel;
import cr.ac.una.towerdefense.model.PartidaDto;
import cr.ac.una.towerdefense.service.PartidaService;
import cr.ac.una.towerdefense.util.AppContext;
import cr.ac.una.towerdefense.util.FlowController;
import cr.ac.una.towerdefense.util.Mensaje;
import cr.ac.una.towerdefense.util.Respuesta;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.scene.shape.Line;


/**
 * FXML Controller class
 * 
 *  Clase encargada de todas las funcioanes en la vista AreaJuegoView
 * 
 *
 * @author damia
 */
public class AreaJuegoViewController extends Controller implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btnPausa;
    @FXML
    private JFXProgressBar pgrbVidaCastillo;
    @FXML
    private JFXProgressBar pgrbElixir;
    @FXML
    private JFXTextField txtNivel;
    @FXML
    private JFXTextField txtMonedas;
    @FXML
    private HBox hboxPausa;
    @FXML
    private JFXButton btnAtras;
    @FXML
    private ImageView imgMeteoro;
    @FXML
    private ImageView imgHielo;    
 
    PartidaDto partida;
    Nivel nivel;
    Boolean isPause;
    ImageView ballesta ;
    boolean meteoroArrastrado = false;
    boolean hieloArrastrado = false;
    int vidaCastilloInicial;
    Monstruo monstruo;
    Integer dinero;
    Timeline health;
    Timeline elixir;//es el hilo timeline que monitorea constantemente el elixir 
    Timeline generadorMonstruos; // timeline en cargado de la creacion del los monstruos en oreden
    int contadorMonstruos;
    Boolean finRonda;
    Boolean hardEndRound;
    Monstruo m = new Monstruo();
    
    //ESTRUCTURAS PARA TRANSICIONES----------------------------------------------------------------------

     ArrayList<Monstruo> listaMonstruos = new ArrayList<>();//lista de monstruos en pantalla
     ArrayList<PathTransition> animacionFlechas = new ArrayList<>();//lista de  pathTransitions de flechas en pantalla
     ArrayList<ImageView> listaFlechas = new ArrayList<>();//lista de flechas en pantalla
  
    RotateTransition rotacionBallesta = new RotateTransition(); //transicion independiente de rotacion de ballesta
    private Boolean corriendo;
    
    Timeline controlDisparo;
    private Boolean accionadorDisparo = false;
     Timeline poderes;
    @FXML
    private HBox hboxVidaElixir;
    @FXML
    private HBox hboxPoderes;
    @FXML
    private HBox hboxInfoPartida;
    
    public  MediaPlayer player;
    public  MediaPlayer efecto;
    @FXML
    private Label txtElixir;
    @FXML
    private Label txtVidaCastillo;
    @FXML
    private JFXProgressBar pgrbAvance;

    /**
     * Clase encargada de la area de juego
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @Override
    public void initialize() {
        
        
        //cargado de la partida
        partida = new PartidaDto();
        cargarPartida((Long)AppContext.getInstance().get("idPartida"));
        //creacion de nivel 
        nivel = new Nivel(partida);
        nivel.determinarDificultad();
        //intnciacion de hilo timeline que comprueba el manejo de elixir
        comprobarElixir();

        //inicializacion de las acciones relacionadas con flechas
        inicializarBallesta();
        disparoFlecha();
        
        creacionMonstruos();
        reproduce("music");
        
        hboxPausa.setVisible(false);
        isPause=false;
          
        vidaCastilloInicial = nivel.getVidaCastillo();
        pgrbVidaCastillo.progressProperty().set(1);
        pgrbElixir.progressProperty().set(1);
        pgrbAvance.progressProperty().set(0);

        comprobarVidaCastillo();
        meteoroArrastrado = false;
        hieloArrastrado = false;        
        comprobarPoderes();
        
        txtNivel.setText(partida.getNivel());
        txtMonedas.setText(partida.getMonedas());
        setDinero(Integer.parseInt(partida.getMonedas()));
        setCorriendo(true);
        
        health.play();
        controlDisparo.play();
        generadorMonstruos.play();
        
        hboxPausa.setVisible(false);
        imgHielo.setDisable(false);
        imgMeteoro.setDisable(false);
        health.play();
        controlDisparo.play();
        hboxInfoPartida.setOpacity(1);
        hboxPoderes.setOpacity(1);
        hboxVidaElixir.setOpacity(1);
        hardEndRound=false;
        
    }
    
    //-----------------------------------------------------------METODOS--------------------------------------------------------------------------//
    
     public void reproduce(String nombSonido) {
           
        final String NOMBRE_ARCHIVO = "Audios/"+nombSonido+".mp3";
        File archivo = new File(NOMBRE_ARCHIVO);
        Media audio = new Media(archivo.toURI().toString());
        player = new MediaPlayer(audio);
        player.setVolume(0.1);
        player.play();
    }
     
     public void efectoSonido(String nombSonido) {
           
        final String NOMBRE_ARCHIVO = "Audios/"+nombSonido+".mp3";
        File archivo = new File(NOMBRE_ARCHIVO);
        Media audio = new Media(archivo.toURI().toString());
        efecto = new MediaPlayer(audio);
        efecto.setVolume(0.1);
        efecto.play();
    }
    

   
    
    
    public void guardarPartida(){
    try {
            PartidaService service = new PartidaService();
            Respuesta respuesta = service.guardarPartida(partida);
                if (!respuesta.getEstado()) {
                    new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar partida", getStage(), respuesta.getMensaje());
                } else {
                    partida = (PartidaDto) respuesta.getResultado("Partida");  
                    
                }
        } catch (Exception ex) {
            Logger.getLogger(PartidaViewController.class.getName()).log(Level.SEVERE, "Error guardando el partida.", ex);
            new Mensaje().showModal(Alert.AlertType.ERROR, "Guardar partida", getStage(), "Ocurrio un error guardando el partida.");
        }        
    }
    
    public void terminaRonda(){
            FinRondaViewController finPartida = (FinRondaViewController) FlowController.getInstance().getController("FinRondaView");
            health.pause();
            controlDisparo.pause();
            setCorriendo(false);
            generadorMonstruos.stop();
            root.getChildren().remove(ballesta);

            for(int i=0; i<root.getChildren().size();i++){
                for(int j=0; j<listaMonstruos.size();j++){
                    if("Monstruo".equals(root.getChildren().get(i).getAccessibleText())){
                      root.getChildren().remove(i);
                    }
                }
             }
            listaMonstruos.clear();
            
            //Este condicional verifica si jugador se salio de la partida, si el boleano "hardEndRound" tiene como valor
            // un true significa que el jugador se salio de la partida, por lo tanto no recive reconpensas
            
            if(hardEndRound==false){
                Integer monedasActual = Integer.parseInt(txtMonedas.getText());
                partida.setMonedas(monedasActual.toString());
            }
            
        
        if(finRonda==false){
            player.stop();
            guardarPartida();
            finPartida.derrota();
            efectoSonido("derrota");
            FlowController.getInstance().goView("FinRondaView");
        }else{
            player.stop();
            Integer nivelActual = Integer.parseInt(partida.getNivel())+1;
            if(nivelActual>=100){
                nivelActual=100;
            }
            partida.setNivelPartida(nivelActual.toString());
            guardarPartida();
            finPartida.victoria();
            efectoSonido("victoria");
            FlowController.getInstance().goView("FinRondaView");
        }
    }
    
    
    public Integer getDinero() {
        return dinero;
    }

    public void setDinero(Integer dinero) {
        this.dinero = dinero;
    }
    
    public void inicializarBallesta() { //inicializa la trancision de ratacion encargada de la ballesta
        if("A".equals(partida.getTipoBallesta())){
            Image img1 = new Image("cr/ac/una/towerdefense/resources/ballesta.png");
            ballesta = new ImageView(img1);
        }else if("B".equals(partida.getTipoBallesta())){
            Image img1 = new Image("cr/ac/una/towerdefense/resources/ballesta2.png");
            ballesta = new ImageView(img1);
        }
        
        ballesta.setFitHeight(80);
        ballesta.setFitWidth(80);
        ballesta.setX(root.getPrefWidth()/4.5);
        ballesta.setY((root.getPrefHeight()/2)-40);

        root.getChildren().add(ballesta);
        rotacionBallesta = new RotateTransition(Duration.millis(100),ballesta);
    }
    
    //Estos metodos retornan un numero float entre 0 y 1, debido a que para manipular el progres bar de jfoenix 8
    //solo funciona si recive numeros entre 0 y 1. Por ejemplo, 0.5 representa un 50% de la barra de avance
    private float setNumInBarraElixir(float maxElixir,float elixirActual){
        float avance=elixirActual/maxElixir;
        if(avance<=0){// comprobacion para no tocar numeros por debajo de cero
            avance=0;
        }
        return avance;
    }
    
    private float setNumInBarraCastillo(float vidaInicial,float vidaActual){
        float avance=vidaActual/vidaInicial;
        if(avance<=0){// comprobacion para no tocar numeros por debajo de cero
            avance=0;
        }        
        return avance;
    }
    
    private float setNumInBarraAvance(float max,Integer avanceActual){
       
        float avance=avanceActual/max;
        
        
        if(avance<=0){// comprobacion para no tocar numeros por debajo de cero
            avance=0;
        }      
        return avance;
    }
    
    public void disparoFlecha() {// metodo encargado de detectar la pocicion del mouse rotar la ballesta y dentro alvergara validaciones para crear los disparos de flechas
        controlDisparo = new Timeline(new KeyFrame(Duration.seconds(nivel.getCadenciaDisparo()), e-> {
            if (!accionadorDisparo){
                accionadorDisparo=true;
            }else{
                accionadorDisparo= false;
            }
        }));
        controlDisparo.setCycleCount(Animation.INDEFINITE);
        controlDisparo.play();        
        
        root.addEventHandler(MouseEvent.ANY, (MouseEvent e) -> {
            //rotacion de ballesta
              double mouseX;
              mouseX = e.getX();
              double mouseY;
              mouseY = e.getY();

              float xDistance = (float) (mouseX - ballesta.getX());//getX()
              float yDistance = (float) (mouseY - ballesta.getY());//

              //Calculo de angulo de rotacion de balllesta
              double angleToTurn = Math.toDegrees(Math.atan2(yDistance, xDistance));          
              rotacionBallesta.setToAngle(angleToTurn-5); 
              
              if(getCorriendo()==true){ //metodo usado para detener la rotacion de la ballesta
                  rotacionBallesta.play();
              }else{
                  rotacionBallesta.pause();
              }
               //disparto de ballesta
               
              if(e.getEventType()==MouseEvent.MOUSE_CLICKED && getCorriendo() || e.getEventType()==MouseEvent.MOUSE_DRAGGED && accionadorDisparo ){
                
                  efectoSonido("arco");
                  accionadorDisparo=false;

                  Image img2 = new Image("cr/ac/una/towerdefense/resources/flecha.png");
                  ImageView flecha = new ImageView(img2); 
                  flecha.setAccessibleText("flecha");
                  flecha.setFitHeight(80);
                  flecha.setFitWidth(80);

                  root.getChildren().add(flecha);              

                  flecha.setFitHeight(80);
                  flecha.setFitWidth(80);

                  //creacion de ruta disparo ballesta   hay que manejar tiempo entre disparos
                  PathTransition transicionGuiada = new PathTransition();
                  transicionGuiada.setNode(flecha);
            //          transicionGuiada.setDuration(Duration.seconds(1.5));  //esta parte sera fundamentala para manipular la velocidad de disparo
                  transicionGuiada.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);

                  Line caminoFlecha = new Line(root.getPrefWidth()/4.1, root.getPrefHeight()/2, mouseX, mouseY);
                  transicionGuiada.setPath(caminoFlecha);

                  transicionGuiada.play();         
                  animacionFlechas.add(transicionGuiada);
                  listaFlechas.add(flecha);

                  //eliminacion de las flechas de la vista
                  transicionGuiada.setOnFinished((n)->{
                      animacionFlechas.clear();
                      listaFlechas.clear();

                      int contador=0;
                      for(Node nodo: root.getChildren()){
                             if(root.getChildren().get(contador).getAccessibleText()=="flecha"){
                                 root.getChildren().remove(contador);
                                 break;
                             }
                             contador++;
                      }
                  });
              }    
        }); 
    }

    public Boolean getAccionadorDisparo() {
        return accionadorDisparo;
    }

    public void setAccionadorDisparo(Boolean accionadorDisparo) {
        this.accionadorDisparo = accionadorDisparo;
    }
    
    private void cargarPartida(Long id) {
        PartidaService service = new PartidaService();
        Respuesta respuesta = service.getPartida(id);

        if (respuesta.getEstado()) {
//            unbindPartida();
            partida= (PartidaDto) respuesta.getResultado("Partida");
//            bindPartida(false);
        } else {
            new Mensaje().showModal(Alert.AlertType.ERROR, "Cargar empleado", getStage(), respuesta.getMensaje());
        }
    }    

    //-------------------------------------------------EVENTOS------------------------------------------------------------------------------//
    
    @FXML
    private void onActionbtnPausa(ActionEvent event) {
       if(isPause==false){
         player.pause();
         generadorMonstruos.pause();
         detenerMonstruos();
         hboxPausa.setVisible(true);//se pone visible el mini menu de pausa
         imgHielo.setDisable(true);//se desabilita poder usar el poder de meteoro
         imgMeteoro.setDisable(true);//se desabilita poder usar el poder de hielo
         health.pause();
         controlDisparo.pause();
         hboxInfoPartida.setOpacity(0.1);
         hboxPoderes.setOpacity(0.1);
         hboxVidaElixir.setOpacity(0.1);
         setCorriendo(false);//se detiene la recarga de elixir
         isPause = true;
         finRonda=false;
       }else{
          player.play();
          generadorMonstruos.play();
          reanudarMonstruos();
          m.getMovimientoMonstruo().play();
          hboxPausa.setVisible(false);
          imgHielo.setDisable(false);
          imgMeteoro.setDisable(false);
          health.play();
          controlDisparo.play();
         hboxInfoPartida.setOpacity(1);
         hboxPoderes.setOpacity(1);
         hboxVidaElixir.setOpacity(1);
          setCorriendo(true);
          isPause = false;
       }
    }
    
    
    public void detenerMonstruos(){

        for(int i=0; i<root.getChildren().size();i++){
            for(int j=0; j<listaMonstruos.size();j++){
                listaMonstruos.get(j).getMovimientoMonstruo().pause();
                listaMonstruos.get(j).getEnemigo().setOpacity(0);
                ballesta.setOpacity(0);
            }
         }    
    }    

    public void reanudarMonstruos(){

        for(int i=0; i<root.getChildren().size();i++){
            for(int j=0; j<listaMonstruos.size();j++){
                listaMonstruos.get(j).getMovimientoMonstruo().play();
                listaMonstruos.get(j).getEnemigo().setOpacity(1);
                ballesta.setOpacity(1);                
            }
         }    
    }            
    
    @FXML
    private void onActionbtnAtras(ActionEvent event) {
        player.stop();
        hardEndRound=true;
       
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setTitle("ADVERTENCIA");
        alert.setContentText("Perderás tus monedas recolectadas");
        Optional<ButtonType> action = alert.showAndWait();
   
        if (action.get() == ButtonType.OK) {
            terminaRonda();
        }
        
    }

    @FXML
    private void onDragDetectedImgMeteoro(MouseEvent event) {
        meteoroArrastrado=true;
        Dragboard db = imgMeteoro.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putImage(imgMeteoro.getImage());
        db.setContent(content);
        event.consume();
    }

    @FXML
    private void onDragDetectedImgHielo(MouseEvent event) {
         hieloArrastrado=true;
        Dragboard db = imgHielo.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putImage(imgHielo.getImage());
        db.setContent(content);
        event.consume();  
    }

    @FXML
    private void onDragOverRoot(DragEvent event) {
        root.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getGestureSource() != root &&
                        event.getDragboard().hasImage()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });        
    }

    @FXML
    private void onDragDroppedRoot(DragEvent event) {

                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasImage()) {
                    
                    if(meteoroArrastrado){// si lo lanzado fue meteoro
                      for(int k=0; k<root.getChildren().size();k++){  
                        if("Monstruo".equals(root.getChildren().get(k).getAccessibleText())){
                        //detectar monstruo al cual afectar con poder metoro
                        for(int i=0;i<listaMonstruos.size();i++){
                                //comprebacion de area de choque veticalmente sobre monstruos
                               if(listaMonstruos.get(i).getEnemigo().getY()>=event.getY()-150 && listaMonstruos.get(i).getEnemigo().getY()<=event.getY()+150){
                                    
                                    double posicionX;        
                                    posicionX=( ((root.getPrefWidth()/4)*3.8)+listaMonstruos.get(i).getEnemigo().getTranslateX() ); //se determina posicion x del monstruo   
                                    
                                    if(posicionX>=event.getX()-150 && posicionX<=event.getX()+150){///comprobacion horizaontalemente en x
                                        listaMonstruos.get(i).setCantVida(listaMonstruos.get(i).getCantVida()-nivel.getDañoMeteoro()); // aplicar daño sobre el monstruo en cuanto poder meteoro        
                                        //ELIMINAR A MONSTRUO CUANDO ESTE LLEGA A 0
                                        if (listaMonstruos.get(i).getCantVida()<=0) {
                                            listaMonstruos.get(i).eliminarMonstruo(listaMonstruos);
                                        }
                                    }
                               }        
                        }
                      }
                    }                           
                        //REVISAR Y ARREGLAR CUANDO ELIXIR LLEGA A CERO
                        if(nivel.getCantElixir()<=0){ //si el elixir esta totalmente vacio
                            nivel.setCantElixir(0);
                            pgrbElixir.setProgress(0);
                        }else{
                            if (nivel.getCantElixir()-nivel.getCostoElixirMeteoro()>=0){ // comprobar si se puede aplicar el poder metoro
                                pgrbElixir.setProgress(setNumInBarraElixir(nivel.getCantMaxElixir(), nivel.getCantElixir()-nivel.getCostoElixirMeteoro()));
                                nivel.setCantElixir(nivel.getCantElixir()-nivel.getCostoElixirMeteoro());
                            }
                        }
                        meteoroArrastrado=false; //ya se arrstro y solto meteoro
                    }
                    else{
                        
                        if(hieloArrastrado==true){
                           // for(int x=0; x<listaMonstruos.size();x++){
                           for(int x=0; x<root.getChildren().size();x++){ 
                                if("Monstruo".equals(root.getChildren().get(x).getAccessibleText())){
                                //detectar monstruo al cual afectar con poder hielo
                                for(int i=0;i<listaMonstruos.size();i++){
                                        //comprebacion de area de choque veticalmente sobre monstruos
                                       if(listaMonstruos.get(i).getEnemigo().getY()>=event.getY()-150 && listaMonstruos.get(i).getEnemigo().getY()<=event.getY()+150){

                                            double posicionX;        
                                            posicionX=( ((root.getPrefWidth()/4)*3.8)+listaMonstruos.get(i).getEnemigo().getTranslateX() ); //se determina posicion x del monstruo   

                                            if(posicionX>=event.getX()-150 && posicionX<=event.getX()+150){///comprobacion horizaontalemente en x
                                                listaMonstruos.get(i).setCantVida(listaMonstruos.get(i).getCantVida()-nivel.getDañoHielo()); // aplicar daño sobre el monstruo en cuanto poder hielo       
                                                listaMonstruos.get(i).detenerMonstruo(nivel.getTiempoHielo()); //setValue(Duration.seconds(5));
                                                //ELIMINAR A MONSTRUO CUANDO ESTE LLEGA A 0
                                                if (listaMonstruos.get(i).getCantVida()<=0) {
                                                    listaMonstruos.get(i).eliminarMonstruo(listaMonstruos);
                                                }

                                            }
                                       }        
                                    }
                                }
                            }
                            //REVISAR Y ARREGLAR CUANDO ELIXIR LLEGA A CERO
                            if(nivel.getCantElixir()<=0){ //si el elixir esta totalmente vacio
                                nivel.setCantElixir(0);
                                pgrbElixir.setProgress(0);
                            }else{
                                if (nivel.getCantElixir()-nivel.getCostoElixirHielo()>=0){ // comprobar si se puede aplicar el poder metoro
                                    pgrbElixir.setProgress(setNumInBarraElixir(nivel.getCantMaxElixir(), nivel.getCantElixir()-nivel.getCostoElixirHielo()));
                                    nivel.setCantElixir(nivel.getCantElixir()-nivel.getCostoElixirHielo());
                                }
                            }                            
                                hieloArrastrado= false;
                        }
                    }
                    success = true;
                    
                }
                event.setDropCompleted(success);
                event.consume();     
    }
    
     @FXML
    private void onDragDoneImgMeteoro(DragEvent event) {
       // meteoroArrastrado=true;
    }

    @FXML
    private void onDragDoneImgHielo(DragEvent event) {
        //hieloArrastrado=true;
    }
    
        //------------Metodos timeline que funcionan como hilos------------------------
    
    public void creacionMonstruos(){

        contadorMonstruos=0;        
        generadorMonstruos = new Timeline(new KeyFrame(Duration.seconds(2), e -> {
           
            if(contadorMonstruos<nivel.getListaEnemigos().size()){

                Nivel.TuplaEnemigo tupla = nivel.getListaEnemigos().get(contadorMonstruos);
                /*Monstruo*/ m = new Monstruo(tupla.getTpMonstruo(), tupla.getNumSpawn(), root,accionadorDisparo);//tupla.getTpMonstruo()
                m.configurartipoMonstruo();
                m.crearMonstruo(nivel,listaMonstruos);  
                listaMonstruos.add(m);
                contadorMonstruos++;
                
                // caso liberador de orda de monstruos
                if(contadorMonstruos==nivel.getListaEnemigos().size()-15 && nivel.getActivarOrdaMontruos()){
                    generadorMonstruos.setDelay(Duration.seconds(5));
                    generadorMonstruos.stop();
                    generadorMonstruos.play();
                    
                    for (int i = 0; i<10; i++){
                        Nivel.TuplaEnemigo tupla2 = nivel.getListaEnemigos().get(contadorMonstruos);
                        Monstruo m2 = new Monstruo(tupla2.getTpMonstruo(), tupla2.getNumSpawn(), root,accionadorDisparo);//tupla.getTpMonstruo()
                        m2.configurartipoMonstruo();
                        m2.crearMonstruo(nivel,listaMonstruos);  
                        listaMonstruos.add(m2);
                        contadorMonstruos++;                        
                    }
                }
                 pgrbAvance.setProgress(setNumInBarraAvance(nivel.getListaEnemigos().size(),contadorMonstruos));
            } 
            
        }));
        generadorMonstruos.setCycleCount(Animation.INDEFINITE);
        generadorMonstruos.play();
    }
    
    public void comprobarPoderes(){
        poderes = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            nivel.comprobarDisponibilidadPoderes(meteoroArrastrado,hieloArrastrado);
            if(nivel.isMeteoroDisponible()){
                imgMeteoro.setDisable(false);
                imgMeteoro.setOpacity(1);
            }else{
                imgMeteoro.setDisable(true);
                imgMeteoro.setOpacity(0.3);
            }

            if(nivel.isHieloDisponible()){
                imgHielo.setDisable(false);
                imgHielo.setOpacity(1);
            }else{
                imgHielo.setDisable(true);
                imgHielo.setOpacity(0.3);
            }          
        }));
        poderes.setCycleCount(Animation.INDEFINITE);
        poderes.play();         
    }
    
    public void comprobarVidaCastillo(){
        
        
        health = new Timeline(new KeyFrame(Duration.millis(5), e ->{
            
           
            
            txtMonedas.setText(getDinero().toString());
            
            // se manipula la barra de vida del castillo
            Integer vidaActual=nivel.getVidaCastillo();
            txtVidaCastillo.setText(vidaActual.toString());
            
            pgrbVidaCastillo.setProgress(setNumInBarraCastillo(vidaCastilloInicial, nivel.getVidaCastillo()));
            if(nivel.getVidaCastillo()<=0){
                corriendo = false;
                finRonda=false;
                terminaRonda();
                
            }else{// si el tamaño de la lista de monstruos configurada en nivel es igual a la cantidad de monstruos desplegados en la partida<
                if (nivel.getListaEnemigos().size() == contadorMonstruos){   // indicador de que salio el ulimo monstruo
                    if(listaMonstruos.isEmpty()){// si la lista esta vacia al final de la partida
                        player.stop();
                        corriendo = false;
                        finRonda=true;
                        terminaRonda();                        
                    }
                }
            }        
        }));
        health.setCycleCount(Animation.INDEFINITE);
        health.play();        
    }
    
    public void comprobarElixir(){  //metodo usado para manejar el elixir
        long tiempo = (long)nivel.getTiempoEntreRecarga();//delay entre cada insercion de elixir
        elixir = new Timeline(new KeyFrame(Duration.seconds(tiempo), e -> {
            if(getCorriendo()){          
                if(nivel.getVidaCastillo()>0){ 
                // se manipula la barra de nivel de elixir
                Integer elixirActual=nivel.getCantElixir();
                txtElixir.setText(elixirActual.toString());
                
                pgrbElixir.setProgress(setNumInBarraElixir(nivel.getCantMaxElixir(), nivel.getCantElixir()));
                        if(nivel.getCantElixir()<nivel.getCantMaxElixir()){//si la cantidad actual de elixir recarga elixir
                            nivel.setCantElixir(nivel.getCantElixir()+nivel.getCantRecargaElixir());
                            if(nivel.getCantElixir()>nivel.getCantMaxElixir()){// si el nivel de recarga rebasa el limite de almacenamiento de elixir
                                nivel.setCantElixir(nivel.getCantMaxElixir());
                            }
                        }
                    }
                if(nivel.getVidaCastillo()<=0){
                     elixir.stop();
                }
             }
        }));
        elixir.setCycleCount(Animation.INDEFINITE);
        elixir.play();
    }  
    
   public Boolean getCorriendo() {
        return corriendo;
    }

    public void setCorriendo(Boolean corriendo) {
        this.corriendo = corriendo;
    }

   
    
}
