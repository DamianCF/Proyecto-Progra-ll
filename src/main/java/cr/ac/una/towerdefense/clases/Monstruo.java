/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Clase encargada de la cleasion de mounstruos
 * @author HP
 */

public class Monstruo extends ImageView{

    private String tipoMonstruo;  //5 tipos de monstruos
    private int cantDaño;
    private int cantVida;
    private float velocidad;
    private float frecuenciaAtaque;
    
    private AnchorPane root;
    int spawn;
    float tiempoUltimoAtaque = 0;
    Boolean accionadorDañoDisparo;
    
    TranslateTransition movimientoMonstruo = new TranslateTransition();
    private ImageView enemigo = new ImageView(); 
    private Timeline atacar;

    
    
    Integer dineroActual;

    public Monstruo(String tipoMonstruo, int spawn , AnchorPane root , Boolean accionadorDisparo) {
        this.tipoMonstruo = tipoMonstruo;
        this.spawn = spawn;
        this.root = root;
        accionadorDañoDisparo = accionadorDisparo;
    }

    public Monstruo() {
    }
    

    public void crearMonstruo(Nivel nivel,ArrayList<Monstruo> listaMonstruos){
        
        asignarAparienciaMovimientoMonstruo();
        asignarSpawnMonstruo();
        
        enemigo.setAccessibleText("Monstruo");  //esto sera usado para distinguir rapidamente si el nodo en root es montruo o flecha
        setAccessibleText("Monstruo");
        enemigo.addEventHandler(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                ///detectar si le hacen daño al montruo  MOUSE_CLICKED
                if (e.getEventType()==MouseEvent.MOUSE_CLICKED || e.getEventType()==MouseEvent.MOUSE_DRAGGED  && accionadorDañoDisparo) {
                    //  tambien se podriaponer que se seleccione el monstruo y se arrastre la flecha para que fijo le de
                    cantVida -= nivel.getDañoFlecha();
                    if (cantVida<=0) {
                        movimientoMonstruo.stop();
                        enemigo.setImage(new Image("cr/ac/una/towerdefense/resources/lapida.png"));
                        FadeTransition fade = new FadeTransition(Duration.seconds(5), enemigo);
                        fade.setFromValue(1.0);
                        fade.setToValue(0.0);
                        fade.play();
                        
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
           
        root.getChildren().add(enemigo);
        movimientoMonstruo.setDuration(Duration.seconds(velocidad));
        movimientoMonstruo.setNode(enemigo);
        movimientoMonstruo.setToX(-(root.getPrefWidth()/4)*2.8);//2.8
        movimientoMonstruo.play();

        
        //una vez que el monstruo llega al final del recorrido
        movimientoMonstruo.setOnFinished( (e) -> {//cando el monstruo llega al final se ataca al castillo
            asignarAparienciaMonstruoAtacando();
            atacar = new Timeline(new KeyFrame(Duration.seconds(frecuenciaAtaque), a-> {   
            nivel.setVidaCastillo(nivel.getVidaCastillo()-cantDaño);//quitarle vida al castillo
            
           // solucion de problema de eliminacion (si el monstruo llegaba al final de su recorrido y era eliminado, le seguia bajando vida al castillo)
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
    
    public void eliminarMonstruo(ArrayList<Monstruo> listaMonstruos){  //aplicar un time line para eliminar monstruo
        
        movimientoMonstruo.stop();
       
        if(root.getChildren().contains(enemigo)){
              
              root.getChildren().remove(enemigo);
            
              AreaJuegoViewController monedas = (AreaJuegoViewController) FlowController.getInstance().getController("AreaJuegoView"); 
              dineroActual=monedas.getDinero()+20;
              monedas.setDinero(dineroActual);
     
        }   
        if (listaMonstruos.contains(Monstruo.this)) {
            listaMonstruos.remove(Monstruo.this); 
        }    
         
    }
    
    public void detenerMonstruo(int tiempoHielo){
        movimientoMonstruo.pause();
        // se puede agregar una imagen detenida al monstruo
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
        switch(tipoMonstruo){
            case "1":
                cantDaño = 30;
                cantVida = 50;
                velocidad = 20;//10
                frecuenciaAtaque = 5;
                break;
            case "2":
                cantDaño = 60;
                cantVida = 100;
                velocidad = 9;
                frecuenciaAtaque = 4;                
                break;
            case "3":
                cantDaño = 90;
                cantVida = 150;
                velocidad = 8;
                frecuenciaAtaque = 3;                
                break;
            case "4":
                cantDaño = 120;
                cantVida = 200;
                velocidad = 7;
                frecuenciaAtaque = 2;                
                break;
            case "5":
                cantDaño = 150;
                cantVida = 250;
                velocidad = 5;
                frecuenciaAtaque = 1;                
                break;
        }
    }
    
    private void asignarAparienciaMovimientoMonstruo(){
        switch(tipoMonstruo){ //establecer una apariencia segun monstruo
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
        // dar ubicacion al mostruo
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

