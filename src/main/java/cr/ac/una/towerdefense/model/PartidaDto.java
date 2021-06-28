package cr.ac.una.towerdefense.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Ronald Blanco - Damian Cordero
 */

public class PartidaDto {
    
    public SimpleStringProperty id;
    public SimpleStringProperty nivel;// nivel actual de la partida
    public ObjectProperty<String> tipoBallesta;///seleccionar la apariencia de ballesta
    public ObjectProperty<String> tipoUsoBallesta;///seleccionar la manera de uso de ballesta entre mouse o teclado String
    public SimpleStringProperty nivelBallesta;//nivel actual de ballesta
    public SimpleStringProperty nivelCastillo;//nivel actual de castillo
    public SimpleStringProperty nivelElixir;//nivel actual de Elixir
    public SimpleStringProperty monedas;// Cantidad de monedas del usuario segun se maten mounstruos
    public SimpleStringProperty nivelPoderMeteoro;// nivel actual de Poder Meteoro
    public SimpleStringProperty nivelPoderHielo;//nivel actual de Poder Hielo
    private Boolean modificado;
    
    public UsuarioDto usuario;
    
    public PartidaDto() {
        this.id = new SimpleStringProperty();
        this.nivel = new SimpleStringProperty();
        this.tipoBallesta = new SimpleObjectProperty("A");///Seria usar tipo A o tipo B segun apariencia selecionada
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
    
    public PartidaDto(Partida partida) {// resive la entidad Partida y la transforma dto  
        this();
        this.id.set(partida.getId().toString()); 
        this.nivel.set(partida.getNivel().toString());
        this.tipoBallesta.set(partida.getTipoBallesta());
        this.tipoUsoBallesta.set(partida.getTipoUsoBallesta());
        this.nivelBallesta.set(partida.getNivelBallesta().toString());
        this.nivelCastillo.set(partida.getNivelCastillo().toString());
        this.nivelElixir.set(partida.getNivelElixir().toString());
        this.monedas.set(partida.getMonedas().toString());
        this.nivelPoderMeteoro.set(partida.getNivelPoderMeteoro().toString());
        this.nivelPoderHielo.set(partida.getNivelPoderHielo().toString());
        
        this.usuario = new UsuarioDto(partida.getIdUsuario());//Comunicacion de la partida con el usuario
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
