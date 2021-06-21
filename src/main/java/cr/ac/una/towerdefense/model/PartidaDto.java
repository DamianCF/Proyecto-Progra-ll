/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author HP
 */

public class PartidaDto {
    
    public SimpleStringProperty id;//Long
    public SimpleStringProperty nivel;// String
    public ObjectProperty<String> tipoBallesta;///seleccionar la apariencia de ballesta  String
    public ObjectProperty<String> tipoUsoBallesta;///seleccionar la manera de uso de ballesta entre mouse o teclado String
    public SimpleStringProperty nivelBallesta;//String
    public SimpleStringProperty nivelCastillo;//String
    public SimpleStringProperty nivelElixir;//String
    public SimpleStringProperty monedas;// este se da segun se meten mounstruos String
    public SimpleStringProperty nivelPoderMeteoro;// String
    public SimpleStringProperty nivelPoderHielo;//String
    private Boolean modificado;
    
    public UsuarioDto usuario;

    
    
    public PartidaDto() {
        
        this.id = new SimpleStringProperty();
        this.nivel = new SimpleStringProperty();
        this.tipoBallesta = new SimpleObjectProperty("A");///Seria usar tipo A o tipo B
        this.tipoUsoBallesta = new SimpleObjectProperty("M");///Seria usar tipo M (mouse) o tipo T (teclado)
        this.nivelBallesta = new SimpleStringProperty();
        this.nivelCastillo = new SimpleStringProperty();
        this.nivelElixir = new SimpleStringProperty();
        this.monedas = new SimpleStringProperty();   
        this.nivelPoderMeteoro = new SimpleStringProperty();
        this.nivelPoderHielo = new SimpleStringProperty();
        modificado=false;
        usuario = new UsuarioDto();
     
    }
    
   // HAY QUE HACER CAMBIOS
    public PartidaDto(Partida partida) {// resive la entidad y la transforma dto  
        this();//llama al constructor por defecto
        this.id.set(partida.getId().toString()); //el toString para covertir de tipo Long a texto
        this.nivel.set(partida.getNivel().toString());
        this.tipoBallesta.set(partida.getTipoBallesta());
        this.tipoUsoBallesta.set(partida.getTipoUsoBallesta());
        this.nivelBallesta.set(partida.getNivelBallesta().toString());
        this.nivelCastillo.set(partida.getNivelCastillo().toString());
        this.nivelElixir.set(partida.getNivelElixir().toString());
        this.monedas.set(partida.getMonedas().toString());
        this.nivelPoderMeteoro.set(partida.getNivelPoderMeteoro().toString());
        this.nivelPoderHielo.set(partida.getNivelPoderHielo().toString());
        
        this.usuario = new UsuarioDto(partida.getIdUsuario());//no quitar
    }     
    
    
    
    public Long getId() {
        if(id.get()!=null && !id.get().isEmpty())
            return Long.valueOf(id.get());
        else
            return null;
    }

    public void setId(Long id) {
        this.id.setValue(id.toString());
    }

    public String getNivel() {
        return nivel.get();
    }

    public void setNivelPartida(String nivelPartida) {
        this.nivel.setValue(nivelPartida);
    }

    public String getTipoBallesta() {
        return tipoBallesta.get();
    }

    public void setTipoBallesta(String tipoBallesta) {
        this.tipoBallesta.setValue(tipoBallesta);
    }

    public String getTipoUsoBallesta() {
        return tipoUsoBallesta.get();
    }

    public void setTipoUsoBallesta(String tipoUsoBallesta) {
        this.tipoUsoBallesta.setValue(tipoUsoBallesta);
    }

    public String getNivelBallesta() {
        return nivelBallesta.get();
    }

    public void setNivelBallesta(String nivelBallesta) {
        this.nivelBallesta.setValue(nivelBallesta);
    }

    public String getNivelCastillo() {
        return nivelCastillo.get();
    }

    public void setNivelCastillo(String nivelCastillo) {
        this.nivelCastillo.setValue(nivelCastillo);
    }

    public String getNivelElixir() {
        return nivelElixir.get();
    }

    public void setNivelElixir(String nivelElixir) {
        this.nivelElixir.setValue(nivelElixir);
    }

    public String getMonedas() {
        return monedas.get();
    }

    public void setMonedas(String monedas) {
        this.monedas.setValue(monedas);
    }

    public String getNivelPoderMeteoro() {
        return nivelPoderMeteoro.get();
    }

    public void setNivelPoderMeteoro(String nivelPoderMeteoro) {
        this.nivelPoderMeteoro.setValue(nivelPoderMeteoro);
    }

    public String getNivelPoderHielo() {
        return nivelPoderHielo.get();
    }

    public void setNivelPoderHielo(String nivelPoderHielo) {
        this.nivelPoderHielo.setValue(nivelPoderHielo);
    }

    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    public UsuarioDto getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDto usuario) {
        this.usuario = usuario;
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PartidaDto{id=").append(id.get());
        sb.append(", nivelPartida=").append(nivel.get());
        sb.append(", tipoBallesta=").append(tipoBallesta.get());
        sb.append(", tipoUsoBallesta=").append(tipoUsoBallesta.get());
        sb.append(", nivelBallesta=").append(nivelBallesta.get());
        sb.append(", nivelCastillo=").append(nivelCastillo.get());
        sb.append(", nivelElixir=").append(nivelElixir.get());
        sb.append(", monedas=").append(monedas.get());
        sb.append(", nivelPoderMeteoro=").append(nivelPoderMeteoro.get());
        sb.append(", nivelPoderHielo=").append(nivelPoderHielo.get());
        sb.append(", modificado=").append(modificado);
        sb.append('}');
        return sb.toString();
    }
    
    
    
}
