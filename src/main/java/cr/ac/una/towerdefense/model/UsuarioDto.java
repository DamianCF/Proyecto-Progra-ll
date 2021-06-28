package cr.ac.una.towerdefense.model;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

/**
 *
 * @author Ronald Blanco - Damian Cordero
 */
public class UsuarioDto {
    
    public SimpleStringProperty id;
    public SimpleStringProperty usuario;// nombre de usuario
    public SimpleStringProperty clave;// contraseña de usuario
    public ObjectProperty<Image> avatar;// imagen de avatar de usuario
    private Boolean modificado;
    
    public ObservableList<PartidaDto> partidas;
    public List<PartidaDto> partidasEliminadas;
    
    public UsuarioDto() {
        modificado=false;
        this.id = new SimpleStringProperty();
        this.usuario = new SimpleStringProperty();
        this.clave = new SimpleStringProperty();
        this.avatar = new SimpleObjectProperty();        
    }

    public UsuarioDto(Usuarios usuario){
        this();
        this.id.set(usuario.getId().toString());
        this.usuario.set(usuario.getUsuario());
        this.clave.set(usuario.getClave());
        this.avatar.set(new Image(usuario.getAvatar()));    
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

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(String usuario) {
        this.usuario.setValue(usuario);
    }

    public String getClave() {
        return clave.get();
    }

    public void setClave(String clave) {
        this.clave.setValue(clave);
    }

    public Image getAvatar() {
        return avatar.get();
    }

    public void setAvatar(Image avatar) {
        this.avatar.setValue(avatar);
    }
  
    public Boolean getModificado() {
        return modificado;
    }

    public void setModificado(Boolean modificado) {
        this.modificado = modificado;
    }

    ///agregado
    public ObservableList<PartidaDto> getPartidas() {
        return partidas;
    }

    public void setPartidas(ObservableList<PartidaDto> partidas) {
        this.partidas = partidas;
    }

    public List<PartidaDto> getPartidasEliminadas() {
        return partidasEliminadas;
    }

    public void setPartidasEliminadas(List<PartidaDto> partidasEliminadas) {
        this.partidasEliminadas = partidasEliminadas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UsuarioDto{id=").append(id.get());
        sb.append(", usuario=").append(usuario.get());
        sb.append(", contrase\u00f1a=").append(clave.get());
        sb.append('}');
        return sb.toString();
    }
}
