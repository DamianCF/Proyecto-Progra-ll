/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author damia
 */
@Entity
@Table(name = "TD_USUARIOS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByUsrId", query = "SELECT u FROM Usuarios u WHERE u.id = :id"),
    @NamedQuery(name = "Usuarios.findUser", query = "SELECT u FROM Usuarios u WHERE u.usuario = :usuario AND u.clave = :clave"),
    //@NamedQuery(name = "Usuarios.findByusuario", query = "SELECT u FROM Usuarios u WHERE u.usuario = :usuario AND u.clave = :clave"),
    //@NamedQuery(name = "Usuarios.findByclave", query = "SELECT u FROM Usuarios u WHERE u.clave = :clave"),
    /*@NamedQuery(name = "Usuarios.findByavatar", query = "SELECT u FROM Usuarios u WHERE u.avatar = :avatar")*/})


public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "TD_USUARIOS_USR_ID_GENERATOR", sequenceName = "una.TD_USUARIOS_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TD_USUARIOS_USR_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "USR_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "USR_NOMBRE")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "USR_CLAVE")
    private String clave;
    @Basic(optional = false)
    @Column(name = "USR_AVATAR")
    private String avatar;
    @OneToMany(mappedBy = "idUsuario", fetch = FetchType.LAZY)
    private List<Partida> partidas;

    public Usuarios() {
    }

    public Usuarios(Long id) {
        this.id = id;
    }

    public Usuarios(Long id, String usuario, String clave, String avatar) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.avatar = avatar;
    }
    
      public Usuarios(UsuarioDto usuarioDto) {//este se lama solo cuando sehace un empleado nuevo ....ojo con version
        this.id = usuarioDto.getId();
        actualizarUsuario(usuarioDto);
    }    
    
    public void  actualizarUsuario(UsuarioDto usuario) {
       this.usuario = usuario.getUsuario();
       this.clave = usuario.getClave();
       this.avatar = usuario.getAvatar().impl_getUrl(); 
    } 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setNombre(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @XmlTransient
    public List<Partida> getPartidaList() {
        return partidas;
    }

    public void setPartidaList(List<Partida> partida) {
        this.partidas = partida;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.towerdefense.model.Usuarios[ usrId=" + id + " ]";
    }
    
}
