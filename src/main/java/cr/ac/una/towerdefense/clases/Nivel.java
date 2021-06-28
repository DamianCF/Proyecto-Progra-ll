
package cr.ac.una.towerdefense.clases;

import cr.ac.una.towerdefense.model.PartidaDto;
import java.util.ArrayList;

/**
 *
 * @author HP
 * 
 * Descricion: Esta clase es la encargada de lamarse al crear un nivel asignado 
 * las caracteristicas que luego seran usadas por varias clases que seran parte
 * fundamental del Area de Juego
 * 
 */
public class Nivel {
    
    ///  Variables para configuracion del nivel----------------------------------------
    
    private PartidaDto partida;
    
    //variables configuracion de la ballesta
    private double cadenciaDisparo;//maneja cada cuanto se dispara una flecha en la ballesta
    private int dañoFlecha;
    
    //variables correspondientes al castillo
    private int vidaCastillo;
    
    //variables corrspondientes al elixir
    private int cantElixir;
    private int cantMaxElixir;
    private int cantRecargaElixir;// si se va a recargar el elixir es necesario tener en cuenta que
    private double tiempoEntreRecarga; //el elixir tiene maximo y tiempo entre las recargas
    
    //variables relacionadas al poder de Meteoro
    private int dañoMeteoro;
    private int costoElixirMeteoro;
    private double duracionRecargaMeteoro;
    private double tiempoActualRecargaMeteoro;
    private boolean meteoroDisponible; 
    //encargado de de determinar si el meteoro esta disponible 
    //ya se por que esta cargando elixir o tiempo de espera entre disparos
    
    //variables realcionadas al poder Hielo
    private int dañoHielo;
    private int costoElixirHielo;
    private int tiempoHielo;
    private double duracionRecargaHielo;
    private double tiempoActualRecargaHielo;
    private boolean hieloDisponible; 
    //encargado de de determinar si el hielo esta disponible 
    //ya se por que esta cargando elixir o tiempo de espera entre disparos  
    
    private int costeCastillo;
    private int costeBallesta;
    private int costeMeteoro;
    private int costeHielo;
    private int costeElixir;

    private ArrayList<TuplaEnemigo> listaEnemigos = new ArrayList<>();
    private Boolean activarOrdaMontruos = false;

    //-------------------CONFIGURACION DE NIVELES EN ELEMENTOS DEL JUEGO---------------------------------------------    

    public Nivel(PartidaDto partida) {
        this.partida = partida;
    }
    
    //metodo para asignar dificutad al nivel, al asignar la configuracion del las herramientas del jugador 
    public void determinarDificultad(){  // mas especificar como se apareceran los enemigos en la area de juego
        configurarBallesta();
        configurarCastillo();
        configurarElixir();
        configurarPoderMeteoro();
        configuarPoderHielo();
        configurarDistribucionMonstruosNivel();
    }
    
// ---------------CONFIGURACION EN DISTRIBUCION DE ENEMIGOS DEL NIVEL------------------------    
    private void configurarDistribucionMonstruosNivel(){
        
        int numeroNivel = Integer.parseInt(partida.getNivel());
        for(int i=0; i<numeroNivel+10;i++){
            
            int  numSpawn= (int)(Math.random()*5+1);
            Integer  numTipoMonstruo= (int)(0);  //si se queda en cero genera problemas
            
            if(numeroNivel>=1 && numeroNivel<21){
                numTipoMonstruo = 1;
                activarOrdaMontruos = false;
                if(numeroNivel>=10 && numeroNivel<21){
                    activarOrdaMontruos = true;
                }            
            }
            if(numeroNivel>=21 && numeroNivel<41){
                if(numeroNivel>=21 && numeroNivel<30){
                    activarOrdaMontruos = false;
                    if(i%2==0){
                        numTipoMonstruo = (int)(1);
                    }else{
                        numTipoMonstruo = (int)(2);
                    }
                }
                else{
                    if(numeroNivel>=30 && numeroNivel<41){
                        activarOrdaMontruos = true;
                        numTipoMonstruo = (int)(Math.random()*2+1);
                    }                    
                }
            }
            if(numeroNivel>=41 && numeroNivel<61){
                if(numeroNivel>=41 && numeroNivel<50){
                    activarOrdaMontruos = false;
                    if(i%2==0){
                        numTipoMonstruo = (int)(Math.random()*2+1);
                    }else{
                        numTipoMonstruo = (int)(Math.random()*3+1);
                    }
                }
                else{
                    if(numeroNivel>=50 && numeroNivel<61){
                        activarOrdaMontruos = true;
                        numTipoMonstruo = (int)(Math.random()*3+1);
                    }                    
                }                
            }
            if(numeroNivel>=61 && numeroNivel<81){  
                if(numeroNivel>=61 && numeroNivel<70){
                    activarOrdaMontruos = false;
                    if(i%2==0){
                        numTipoMonstruo = (int)(Math.random()*2+1);
                    }else{
                        numTipoMonstruo = (int)(Math.random()*4+1);
                    }
                }
                else{
                    if(numeroNivel>=70 && numeroNivel<81){
                        activarOrdaMontruos = true;
                        numTipoMonstruo = (int)(Math.random()*4+1);
                    }                    
                }                
            }
            if(numeroNivel>=81 && numeroNivel<=100){
                if(numeroNivel>=81 && numeroNivel<90){
                    activarOrdaMontruos = false;
                    if(i%2==0){
                        numTipoMonstruo = (int)(Math.random()*2+1);
                    }else{
                        numTipoMonstruo = (int)(Math.random()*5+1);
                    }
                }
                else{
                    if(numeroNivel>=90 && numeroNivel<=100){
                        activarOrdaMontruos = true;
                        numTipoMonstruo = (int)(Math.random()*5+1);
                    }                    
                }                  
            }
            
            TuplaEnemigo tupla = new TuplaEnemigo(numTipoMonstruo.toString(),numSpawn);
            listaEnemigos.add(tupla);
        }
    }

    
    public ArrayList<TuplaEnemigo> getListaEnemigos() {
        return listaEnemigos;
    }

    public void setListaEnemigos(ArrayList<TuplaEnemigo> listaEnemigos) {
        this.listaEnemigos = listaEnemigos;
    }

    public Boolean getActivarOrdaMontruos() {
        return activarOrdaMontruos;
    }

    public void setActivarOrdaMontruos(Boolean activarOrdaMontruos) {
        this.activarOrdaMontruos = activarOrdaMontruos;
    }
    
//---------------------Configuracion de Ballesta-----------------------    
    public void configurarBallesta() {  //manipula el daño por flecha y la cadencia de disparo generados por la ballesta
        switch(partida.getNivelBallesta()){
            case "1":
                cadenciaDisparo = 2;
                dañoFlecha = 10;
                costeBallesta = 200;
                break;
            case "2":
                cadenciaDisparo = 1.9;
                dañoFlecha = 20;
                costeBallesta = 300;
                break;
            case "3":
                cadenciaDisparo = 1.8;
                dañoFlecha = 30;
                costeBallesta = 400;
                break;
            case "4":
                cadenciaDisparo = 1.4;
                dañoFlecha = 40;  
                costeBallesta = 500;
                break;
            case "5":
                cadenciaDisparo = 1.2;
                dañoFlecha = 50; 
                costeBallesta = 600;
                break;
            case "6":
                cadenciaDisparo = 1;
                dañoFlecha = 60; 
                costeBallesta = 700;
                break;
            case "7":
                cadenciaDisparo = 0.8;
                dañoFlecha = 70;
                costeBallesta = 800;
                break;
            case "8":
                cadenciaDisparo = 0.7;
                dañoFlecha = 80;  
                costeBallesta = 900;                
                break;
            case "9":
                cadenciaDisparo = 0.6;
                dañoFlecha = 90;  
                costeBallesta = 1000;
                break;
            case "10":
                cadenciaDisparo = 0.5;
                dañoFlecha = 100; 
                
                break;
            default:
                System.out.println("Error configurando nivel de ballesta");      
        }
    }

    public double getCadenciaDisparo() {
        return cadenciaDisparo;
    }

    public void setCadenciaDisparo(double cadenciaDisparo) {
        this.cadenciaDisparo = cadenciaDisparo;
    }

    public int getDañoFlecha() {
        return dañoFlecha;
    }

    public void setDañoFlecha(int dañoFlecha) {
        this.dañoFlecha = dañoFlecha;
    }
    
    public int getCosteBallesta() {
        return costeBallesta;
    }

    public void setCosteBallesta(int costeBallesta) {
        this.costeBallesta = costeBallesta;
    }
    
    
// ------------------------CONFIGURACION CASTILLO-----------------------------------
    public void configurarCastillo() { //configura la vida que puede poseer el castillo segun su nivel
        switch(partida.getNivelCastillo()){
            case "1":
                vidaCastillo = 100;
                costeCastillo = 200;
                break;
            case "2":
                vidaCastillo = 200;
                costeCastillo = 300;
                break;
            case "3":
                vidaCastillo = 300;
                costeCastillo = 400;
                break;
            case "4":
                vidaCastillo = 400;
                costeCastillo = 500;
                break;
            case "5":
                vidaCastillo = 500;
                costeCastillo = 600;
                break;
            case "6":
                vidaCastillo = 600;
                costeCastillo = 700;
                break;
            case "7":
                vidaCastillo = 700;
                costeCastillo = 800;
                break;
            case "8":
                vidaCastillo = 800;
                costeCastillo = 900;
                break;
            case "9":
                vidaCastillo = 900;
                costeCastillo = 1000;
                break; 
            case "10":
                vidaCastillo = 1000;
                
                break;   
            default:
                System.out.println("Error configuarando nivel castillo");
        }
    }

    
    public int getVidaCastillo() {
        return vidaCastillo;
    }

    
    public void setVidaCastillo(int vidaCastillo) {
        this.vidaCastillo = vidaCastillo;
    }
    
    public int getCosteCastillo() {
        return costeCastillo;
    }

    public void setCosteCastillo(int costeCastillo) {
        this.costeCastillo = costeCastillo;
    }
    
    
///------------------------CONFIGURACION DE ELIXIR---------------------------------//
    public void configurarElixir() {//configuracion del almacenage y cargado del elixir
        switch(partida.getNivelElixir()){
            case "1":
                    costeElixir = 200;
                    cantElixir = 100;
                    cantMaxElixir = 100;
                    cantRecargaElixir = 10;
                    tiempoEntreRecarga = 10;
                    cantElixir = 100;
                    break;
            case "2":
                    costeElixir = 300;
                    cantElixir = 200;
                    cantMaxElixir = 200;
                    cantRecargaElixir = 20;
                    tiempoEntreRecarga = 9;
                    cantElixir = 200;
                    break;
            case "3":
                    costeElixir = 400;
                    cantElixir = 300;
                    cantMaxElixir = 300;
                    cantRecargaElixir = 30;
                    tiempoEntreRecarga = 8;
                    cantElixir = 300;
                    break;
            case "4":
                    costeElixir = 500;
                    cantElixir = 400;
                    cantMaxElixir = 400;
                    cantRecargaElixir = 40;
                    tiempoEntreRecarga = 7;
                    cantElixir = 400;
                    break;
            case "5":
                    costeElixir = 600;
                    cantElixir = 500;
                    cantMaxElixir = 500;
                    cantRecargaElixir = 50;
                    tiempoEntreRecarga = 6;
                    cantElixir = 500;
                    break;
            case "6":
                    costeElixir = 700;
                    cantElixir = 600;
                    cantMaxElixir = 600;
                    cantRecargaElixir = 60;
                    tiempoEntreRecarga = 5;
                    cantElixir = 600;
                    break;
            case "7":
                    costeElixir = 800;
                    cantElixir = 700;
                    cantMaxElixir = 700;
                    cantRecargaElixir = 70;
                    tiempoEntreRecarga = 4;
                    cantElixir = 700;
                    break;
            case "8":
                    costeElixir = 900;
                    cantElixir = 800;
                    cantMaxElixir = 800;
                    cantRecargaElixir = 80;
                    tiempoEntreRecarga = 3;
                    cantElixir = 800;
                    break;
            case "9":
                    costeElixir = 1000;
                    cantElixir = 900;
                    cantMaxElixir = 900;
                    cantRecargaElixir = 90;
                    tiempoEntreRecarga = 2;
                    cantElixir = 900;
                    break;
            case "10":
                    cantElixir = 1000;
                    cantMaxElixir = 1000;
                    cantRecargaElixir = 100;
                    tiempoEntreRecarga = 1;
                    cantElixir = 1000;
                    break;                    
        }
    }

    public int getCantElixir() {
        return cantElixir;
    }

    public void setCantElixir(int cantElixir) {
        this.cantElixir = cantElixir;
    }

    public int getCantMaxElixir() {
        return cantMaxElixir;
    }

    public void setCantMaxElixir(int cantMaxElixir) {
        this.cantMaxElixir = cantMaxElixir;
    }

    public int getCantRecargaElixir() {
        return cantRecargaElixir;
    }

    public void setCantRecargaElixir(int cantRecargaElixir) {
        this.cantRecargaElixir = cantRecargaElixir;
    }

    public double getTiempoEntreRecarga() {
        return tiempoEntreRecarga;
    }

    public void setTiempoEntreRecarga(double tiempoEntreRecarga) {
        this.tiempoEntreRecarga = tiempoEntreRecarga;
    }
    
    public int getCosteElixir() {
        return costeElixir;
    }

    public void setCosteElixir(int costeElixir) {
        this.costeElixir = costeElixir;
    }
    
    //COMPROBACION DE DISPONIBILDAD DE PODERES
    public void comprobarDisponibilidadPoderes(boolean meteoroArrastrado, boolean hieloArrastrado){
        tiempoActualRecargaMeteoro+=1;  // como este metodo se llama desde area de juego cada segundo se aumenta su tiempo
        tiempoActualRecargaHielo+=1; // se usan como contadores
        
        
        
        if(meteoroArrastrado){//comprabacion de activacion del meteoro
            tiempoActualRecargaMeteoro=0;
            meteoroDisponible = false;
        }else{
            if(tiempoActualRecargaMeteoro<= duracionRecargaMeteoro){
                 meteoroDisponible = false;
            }else{
                if(tiempoActualRecargaMeteoro>= duracionRecargaMeteoro){
                    meteoroDisponible= true;
                    tiempoActualRecargaMeteoro= duracionRecargaMeteoro;
                }
            }
        }
        
        
        if(hieloArrastrado){//comprobacion de activacion de hielo
            tiempoActualRecargaHielo=0;
            hieloDisponible = false;
        }else{
            if(tiempoActualRecargaHielo<= duracionRecargaHielo){
                 hieloDisponible = false;
            }else{
                if(tiempoActualRecargaHielo>= duracionRecargaHielo){
                    hieloDisponible= true;
                    tiempoActualRecargaHielo= duracionRecargaHielo;
                }
            }
        }
        
            

  
    }
    
    //-----------------CONFIGURACION DE METEORO ----------------------------------
    public void configurarPoderMeteoro() {//configura los atributos del poder meteoro
        switch(partida.getNivelPoderMeteoro()){
            case "1":
                costeMeteoro = 200;
                dañoMeteoro=100;
                costoElixirMeteoro=30;
                duracionRecargaMeteoro=20;
                tiempoActualRecargaMeteoro=20;
                meteoroDisponible=false; 
                break;
            case "2":
                costeMeteoro = 300;
                dañoMeteoro=200;
                costoElixirMeteoro=60;
                duracionRecargaMeteoro=18;
                tiempoActualRecargaMeteoro=18;
                meteoroDisponible=false;                
                break;
            case "3":
                costeMeteoro = 400;
                dañoMeteoro=300;
                costoElixirMeteoro=100;
                duracionRecargaMeteoro=16;
                tiempoActualRecargaMeteoro=16;
                meteoroDisponible=false;                
                break;
            case "4":
                costeMeteoro = 500;
                dañoMeteoro=400;
                costoElixirMeteoro=120;
                duracionRecargaMeteoro=14;
                tiempoActualRecargaMeteoro=14;
                meteoroDisponible=false;                
                break;
            case "5":
                costeMeteoro = 600;
                dañoMeteoro=500;
                costoElixirMeteoro=140;
                duracionRecargaMeteoro=12;
                tiempoActualRecargaMeteoro=12;
                meteoroDisponible=false;                
                break;
            case "6":
                costeMeteoro = 700;
                dañoMeteoro=600;
                costoElixirMeteoro=160;
                duracionRecargaMeteoro=12;
                tiempoActualRecargaMeteoro=12;
                meteoroDisponible=false;                
                break;
            case "7":
                costeMeteoro = 800;
                dañoMeteoro=700;
                costoElixirMeteoro=190;
                duracionRecargaMeteoro=10;
                tiempoActualRecargaMeteoro=10;
                meteoroDisponible=false;                
                break;
            case "8":
                costeMeteoro = 900;
                dañoMeteoro=800;
                costoElixirMeteoro=220;
                duracionRecargaMeteoro=9;
                tiempoActualRecargaMeteoro=9;
                meteoroDisponible=false;                
                break;
            case "9":
                costeMeteoro = 1000;
                dañoMeteoro=900;
                costoElixirMeteoro=240;
                duracionRecargaMeteoro=8;
                tiempoActualRecargaMeteoro=8;
                meteoroDisponible=false;                
                break;
            case "10":
               
                dañoMeteoro=1000;
                costoElixirMeteoro=260;
                duracionRecargaMeteoro=7;
                tiempoActualRecargaMeteoro=7;
                meteoroDisponible=false;                
                break;
            default:
                System.out.println("Error configurando nivel de Meteoro");
        }
    }

    public int getDañoMeteoro() {
        return dañoMeteoro;
    }

    public void setDañoMeteoro(int dañoMeteoro) {
        this.dañoMeteoro = dañoMeteoro;
    }

    public int getCostoElixirMeteoro() {
        return costoElixirMeteoro;
    }

    public void setCostoElixirMeteoro(int costoElixirMeteoro) {
        this.costoElixirMeteoro = costoElixirMeteoro;
    }

    public double getDuracionRecargaMeteoro() {
        return duracionRecargaMeteoro;
    }

    public void setDuracionRecargaMeteoro(double duracionRecargaMeteoro) {
        this.duracionRecargaMeteoro = duracionRecargaMeteoro;
    }

    public double getTiempoActualRecargaMeteoro() {
        return tiempoActualRecargaMeteoro;
    }

    public void setTiempoActualRecargaMeteoro(double tiempoActualRecargaMeteoro) {
        this.tiempoActualRecargaMeteoro = tiempoActualRecargaMeteoro;
    }

    public boolean isMeteoroDisponible() {
        return meteoroDisponible;
    }

    public void setMeteoroDisponible(boolean meteoroDisponible) {
        this.meteoroDisponible = meteoroDisponible;
    }
    
    public int getCosteMeteoro() {
        return costeMeteoro;
    }

    public void setCosteMeteoro(int costeMeteoro) {
        this.costeMeteoro = costeMeteoro;
    }
    
///-----------------CONFIGURACION PODER HIELO------------------------------    
    public void configuarPoderHielo() { //configura los atributos del poder de hielo
        dañoHielo=4;
        switch(partida.getNivelPoderHielo()){
            
            case "1":
                costeHielo = 200;
                costoElixirHielo=35; 
                tiempoHielo=5;
                duracionRecargaHielo=10;
                tiempoActualRecargaHielo=10;
                hieloDisponible=false;                 
                break;
            case "2":
                costeHielo = 300;
                costoElixirHielo=70; 
                tiempoHielo=6;
                duracionRecargaHielo=28;
                tiempoActualRecargaHielo=28;
                hieloDisponible=false;                 
                break;
            case "3":
                costeHielo = 400;
                costoElixirHielo=105; 
                tiempoHielo=7;
                duracionRecargaHielo=26;
                tiempoActualRecargaHielo=26;
                hieloDisponible=false;                 
                break;
            case "4":
                costeHielo = 500;
                costoElixirHielo=140; 
                tiempoHielo=8;
                duracionRecargaHielo=24;
                tiempoActualRecargaHielo=24;
                hieloDisponible=false;                 
                break;
            case "5":
                costeHielo = 600;
                costoElixirHielo=175;
                tiempoHielo=9;
                duracionRecargaHielo=22;
                tiempoActualRecargaHielo=22;
                hieloDisponible=false;                 
                break;
            case "6":
                costeHielo = 700;
                costoElixirHielo=210; 
                tiempoHielo=10;
                duracionRecargaHielo=20;
                tiempoActualRecargaHielo=20;
                hieloDisponible=false;                 
                break;
            case "7":
                costeHielo = 800;
                costoElixirHielo=245; 
                tiempoHielo=11;
                duracionRecargaHielo=18;
                tiempoActualRecargaHielo=18;
                hieloDisponible=false;                 
                break;
            case "8":
                costeHielo = 900; 
                costoElixirHielo=280; 
                tiempoHielo=12;
                duracionRecargaHielo=16;
                tiempoActualRecargaHielo=16;
                hieloDisponible=false;                 
                break;
            case "9":
                costeHielo = 1000;
                costoElixirHielo=315; 
                tiempoHielo=13;
                duracionRecargaHielo=14;
                tiempoActualRecargaHielo=14;
                hieloDisponible=false;                 
                break;
            case "10":
                costoElixirHielo=350; 
                tiempoHielo=14;
                duracionRecargaHielo=12;
                tiempoActualRecargaHielo=12;
                hieloDisponible=false;                 
                break;
            default:
                System.out.println("Error configurando nivel de hielo");
        }

    }

    public double getDuracionRecargaHielo() {
        return duracionRecargaHielo;
    }

    public void setDuracionRecargaHielo(double duracionRecargaHielo) {
        this.duracionRecargaHielo = duracionRecargaHielo;
    }

    public int getDañoHielo() {
        return dañoHielo;
    }

    public void setDañoHielo(int dañoHielo) {
        this.dañoHielo = dañoHielo;
    }

    public int getCostoElixirHielo() {
        return costoElixirHielo;
    }

    public void setCostoElixirHielo(int costoElixirHielo) {
        this.costoElixirHielo = costoElixirHielo;
    }

    public int getTiempoHielo() {
        return tiempoHielo;
    }

    public void setTiempoHielo(int tiempoHielo) {
        this.tiempoHielo = tiempoHielo;
    }

    public double getTiempoActualRecargaHielo() {
        return tiempoActualRecargaHielo;
    }

    public void setTiempoActualRecargaHielo(double tiempoActualRecargaHielo) {
        this.tiempoActualRecargaHielo = tiempoActualRecargaHielo;
    }

    public boolean isHieloDisponible() {
        return hieloDisponible;
    }

    public void setHieloDisponible(boolean hieloDisponible) {
        this.hieloDisponible = hieloDisponible;
    }
    
    public int getCosteHielo() {
        return costeHielo;
    }

    public void setCosteHielo(int costeHielo) {
        this.costeHielo = costeHielo;
    }

    ////Clase que sirve solo para pasar una lista de monstruos a mostar en la area de juego
    public class TuplaEnemigo{ 
        private String tpMonstruo; 
        private int numSpawn;

        public TuplaEnemigo(String tpMonstruo, int numSpawn) {
            this.tpMonstruo = tpMonstruo;
            this.numSpawn = numSpawn;
        }

        public String getTpMonstruo() {
            return tpMonstruo;
        }

        public void setTpMonstruo(String tpMonstruo) {
            this.tpMonstruo = tpMonstruo;
        }

        public int getNumSpawn() {
            return numSpawn;
        }

        public void setNumSpawn(int numSpawn) {
            this.numSpawn = numSpawn;
        }
    }
    
    
    

//Sets y gets de costes de mejora
  

    

    

    

    
}
