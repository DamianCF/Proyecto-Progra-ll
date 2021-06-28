package cr.ac.una.towerdefense.clases;

import cr.ac.una.towerdefense.controller.AreaJuegoViewController;
import cr.ac.una.towerdefense.util.FlowController;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * Clase encargada de la creacion de mounstruos, mas sus configuraciones
 *
 * @author Ronald Blanco - Damian Cordero
 */

public class Monstruo extends ImageView{

    private String tipoMonstruo;  //5 tipos de monstruos
    private int cantDaño;   // cuanto daño inflinge el monstruo al castillo
    private int cantVida; // vida del monstruo
    private float velocidad; //cantidad de tiempo que dura el monstruo trasladandose para llegar al castillo
    private float frecuenciaAtaque;/// cada cuanto tiempo el mosntruo inflinge daño sobre el castillo
    private int dineroXmonstruo;
    
    private AnchorPane root;
    int spawn;// lugar donde aparecera el mosnstruo en pantalla
    float tiempoUltimoAtaque = 0;
    Boolean accionadorDañoDisparo;// indica cunado el mosntruo puede atacar
    
    // transicion de movimiento de mosntruo horizaontalemente
    TranslateTransition movimientoMonstruo = new TranslateTransition();
    private ImageView enemigo = new ImageView(); 
    private Timeline atacar; // timeline que mañeja los ataques de los monstruos

    Integer dineroActual; // variable auxiliar de dinero prodicido por muerte de mosntruos

    public Monstruo(String tipoMonstruo, int spawn , AnchorPane root , Boolean accionadorDisparo) {
        this.tipoMonstruo = tipoMonstruo;
        this.spawn = spawn;
        this.root = root;
        accionadorDañoDisparo = accionadorDisparo;
    }

    public Monstruo() {
    }

    public void crearMonstruo(Nivel nivel,ArrayList<Monstruo> listaMonstruos){
        // creacion de montruo en si
        asignarAparienciaMovimientoMonstruo();
        asignarSpawnMonstruo();
        
        //esto sera usado para distinguir rapidamente si el nodo en root es montruo o flecha        
        enemigo.setAccessibleText("Monstruo");  
        setAccessibleText("Monstruo");
        
        enemigo.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ///detectar si le hacen daño al montruo segun se le clicke encima 
                // o se arraste el monstruo para que segun la cadencia de disparo de la ballesta se la hace dago
                if (e.getEventType()==MouseEvent.MOUSE_CLICKED || e.getEventType()==MouseEvent.MOUSE_DRAGGED  && accionadorDañoDisparo) {
                    //  quitar vida al monstruo
                    cantVida -= nivel.getDañoFlecha();
                    
                    if (cantVida<=0) { 
                        // si el monstruo muere se cambia su aspecto y empieza una transicion de desvanecer
                        movimientoMonstruo.stop();
                        // si el mostruo muere se le asigna como apariencia una lapida
                        enemigo.setImage(new Image("cr/ac/una/towerdefense/resources/lapida.png"));
                        FadeTransition fade = new FadeTransition(Duration.seconds(5), enemigo);
                        fade.setFromValue(1.0);
                        fade.setToValue(0.0);
                        fade.play();
                        
                        // funciona como delay para eliminar monstruo de pantalla
                       Timeline eliminar = new Timeline(new KeyFrame(Duration.millis(100),a->{
                           eliminarMonstruo(listaMonstruos);
                       }));
                       eliminar.setCycleCount(Animation.INDEFINITE);
                       eliminar.setDelay(Duration.seconds(5));
                       eliminar.play();

                    }
                }
            }
        });
           
        root.getChildren().add(enemigo);  // agregado de monstruo en el nodo root
        movimientoMonstruo.setDuration(Duration.seconds(velocidad));
        movimientoMonstruo.setNode(enemigo);
        // indicacion de punto en eje x donde el monstruo se dirige para golpear al castillo
        movimientoMonstruo.setToX(-(root.getPrefWidth()/4)*2.8);
        movimientoMonstruo.play();
        
        //una vez que el monstruo llega al final del recorrido
        movimientoMonstruo.setOnFinished( (e) -> {//cuando el monstruo llega al final se ataca al castillo
            asignarAparienciaMonstruoAtacando();
            atacar = new Timeline(new KeyFrame(Duration.seconds(frecuenciaAtaque), a-> {   // el monstruo ataca cada cierto tiempo
            nivel.setVidaCastillo(nivel.getVidaCastillo()-cantDaño);//quitarle vida al castillo
            
           // si el mostruo llega al final y muere
            if(cantVida<=0){
               if("Monstruo".equals(enemigo.getAccessibleText())){
                   eliminarMonstruo(listaMonstruos);
                   atacar.stop();
               } 
            }
            }));
            atacar.setCycleCount(Animation.INDEFINITE);
            atacar.play();
        }); 
    }
    
    public void eliminarMonstruo(ArrayList<Monstruo> listaMonstruos){ 
        // detencion de movimiento de mosntruo
        movimientoMonstruo.stop();
       // elimnacion de mosntruo de root, por tanto quitarlo de la pantalla
        if(root.getChildren().contains(enemigo)){
              root.getChildren().remove(enemigo);
            // sumar manedas al matar monstruo
              AreaJuegoViewController monedas = (AreaJuegoViewController) FlowController.getInstance().getController("AreaJuegoView"); 
              dineroActual=monedas.getDinero()+20+getDineroXmonstruo();
              monedas.setDinero(dineroActual);
        }   
        if (listaMonstruos.contains(Monstruo.this)) {// eliminar mosntruo de lista de monstruo
            listaMonstruos.remove(Monstruo.this); 
        }    
    }
    
    public void detenerMonstruo(int tiempoHielo){// detencion del monstruo en su avanzar
        movimientoMonstruo.pause();
        // delay para reactivar el movimiento del mostruo
        Timeline detener = new Timeline(new KeyFrame(Duration.millis(100), a-> {   
            movimientoMonstruo.play();
         }));
         detener.setDelay(Duration.seconds(tiempoHielo));
         detener.play();        
    }
    
    public String getTipoMonstruo() {
        return tipoMonstruo;
    }

    public void setTipoMonstruo(String tipoMonstruo) {
        this.tipoMonstruo = tipoMonstruo;
    }

    public int getCantDaño() {
        return cantDaño;
    }

    public void setCantDaño(int cantDaño) {
        this.cantDaño = cantDaño;
    }

    public int getCantVida() {
        return cantVida;
    }

    public void setCantVida(int cantVida) {
        this.cantVida = cantVida;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public float getFrecuenciaAtaque() {
        return frecuenciaAtaque;
    }

    public void setFrecuenciaAtaque(float frecuenciaAtaque) {
        this.frecuenciaAtaque = frecuenciaAtaque;
    }

    public AnchorPane getRoot() {
        return root;
    }

    public void setRoot(AnchorPane root) {
        this.root = root;
    }

    public int getSpawn() {
        return spawn;
    }

    public void setSpawn(int spawn) {
        this.spawn = spawn;
    }

    public float getTiempoUltimoAtaque() {
        return tiempoUltimoAtaque;
    }

    public void setTiempoUltimoAtaque(float tiempoUltimoAtaque) {
        this.tiempoUltimoAtaque = tiempoUltimoAtaque;
    }

    public TranslateTransition getMovimientoMonstruo() {
        return movimientoMonstruo;
    }

    public void setMovimientoMonstruo(TranslateTransition movimientoMonstruo) {
        this.movimientoMonstruo = movimientoMonstruo;
    }

    public Timeline getAtacar() {
        return atacar;
    }

    public void setAtacar(Timeline atacar) {
        this.atacar = atacar;
    }

    public ImageView getEnemigo() {
        return enemigo;
    }

    public void setEnemigo(ImageView enemigo) {
        this.enemigo = enemigo;
    }
    
    
    
    public void configurartipoMonstruo(){
        
    // configuracion de atributos del mosntruo segun su tipo
        frecuenciaAtaque = 1;
        switch(tipoMonstruo){
            case "1":
                cantDaño = 7;
                cantVida = 50;
                velocidad = 20;
                dineroXmonstruo=8;
                break;
            case "2":
                cantDaño = 10;
                cantVida = 100;
                velocidad = 9;  
                dineroXmonstruo=10;
                break;
            case "3":
                cantDaño = 90;
                cantVida = 150;
                velocidad = 8; 
                dineroXmonstruo=20;
                break;
            case "4":
                cantDaño = 120;
                cantVida = 200;
                velocidad = 12;
                dineroXmonstruo=25;
                break;
            case "5":
                cantDaño = 150;
                cantVida = 250;
                velocidad = 8; 
                dineroXmonstruo=30;
                break;
        }
    }

    public int getDineroXmonstruo() {
        return dineroXmonstruo;
    }

    public void setDineroXmonstruo(int dineroXmonstruo) {
        this.dineroXmonstruo = dineroXmonstruo;
    }
  
    private void asignarAparienciaMovimientoMonstruo(){
        switch(tipoMonstruo){ //establecer una apariencia segun tipo de monstruo
            case "1":
                Image img1 = new Image("cr/ac/una/towerdefense/resources/sprt_01_WK.gif");
                enemigo.setImage(img1);
                enemigo.setFitHeight(100);
                enemigo.setFitWidth(80);  
                break;
            case "2":
                Image img2 = new Image("cr/ac/una/towerdefense/resources/sprt_02_WK.gif");
                enemigo.setImage(img2);
                enemigo.setFitHeight(100);
                enemigo.setFitWidth(80);  
                break;                
            case "3":
                Image img3 = new Image("cr/ac/una/towerdefense/resources/sprt_03_WK.gif");
                enemigo.setImage(img3);
                enemigo.setFitHeight(130);
                enemigo.setFitWidth(130);  
                break;   
            case "4":
                Image img4 = new Image("cr/ac/una/towerdefense/resources/sprt_04_WK.gif");
                enemigo.setImage(img4);
                enemigo.setFitHeight(100);
                enemigo.setFitWidth(80);  
                break;  
            case "5":
                Image img5 = new Image("cr/ac/una/towerdefense/resources/sprt_05_WK.gif");
                enemigo.setImage(img5);
                enemigo.setFitHeight(100);
                enemigo.setFitWidth(80);  
                break;     
            default:
                System.out.println("Error dando apariencia al monstruo");
        }        
    }
    
    private void asignarAparienciaMonstruoAtacando(){
        switch(tipoMonstruo){ //establecer una apariencia segun monstruo
            case "1":
                Image img1 = new Image("cr/ac/una/towerdefense/resources/sprt_01_HIT.gif");
                enemigo.setImage(img1);
                enemigo.setFitHeight(100);
                enemigo.setFitWidth(80);  
                break;
            case "2":
                Image img2 = new Image("cr/ac/una/towerdefense/resources/sprt_02_HIT.gif");
                enemigo.setImage(img2);
                enemigo.setFitHeight(100);
                enemigo.setFitWidth(80);  
                break;                
            case "3":
                Image img3 = new Image("cr/ac/una/towerdefense/resources/sprt_03_HIT.gif");
                enemigo.setImage(img3);
                enemigo.setFitHeight(100);
                enemigo.setFitWidth(80);  
                break;   
            case "4":
                Image img4 = new Image("cr/ac/una/towerdefense/resources/sprt_04_HIT.gif");
                enemigo.setImage(img4);
                enemigo.setFitHeight(100);
                enemigo.setFitWidth(100);  
                break;  
            case "5":
                Image img5 = new Image("cr/ac/una/towerdefense/resources/sprt_05_HIT.gif");
                enemigo.setImage(img5);
                enemigo.setFitHeight(150);
                enemigo.setFitWidth(400);  
                break;     
            default:
                System.out.println("Error dando apariencia al monstruo");
        }        
    }    
    
    private void asignarSpawnMonstruo(){
        // dar ubicacion de generacion en pantalla para el mostruo segun su spawn
        switch(spawn){
            case 1:
                enemigo.setX((root.getPrefWidth()/4)*3.8);
                enemigo.setY((root.getPrefHeight()/8)*2.2);                
                break;
            case 2:
                enemigo.setX((root.getPrefWidth()/4)*3.8);
                enemigo.setY((root.getPrefHeight()/8)*3.2);                
                break;
            case 3:
                enemigo.setX((root.getPrefWidth()/4)*3.8);
                enemigo.setY((root.getPrefHeight()/8)*4.2);                
                break;
            case 4:
                enemigo.setX((root.getPrefWidth()/4)*3.8);
                enemigo.setY((root.getPrefHeight()/8)*5.2);                
                break;
            case 5:
                enemigo.setX((root.getPrefWidth()/4)*3.8);
                enemigo.setY((root.getPrefHeight()/8)*6.2);                
                break;
            default:
                System.out.println("Errror configurando Spawn del Monstruo");
        }
    }
}

