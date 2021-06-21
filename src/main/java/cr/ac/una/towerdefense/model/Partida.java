/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.model;

import java.io.Serializable;
import java.lang.Long;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author damia
 */
@Entity
@Table(name = "TD_PARTIDA" , schema = "una")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partida.findAll", query = "SELECT p FROM Partida p"),
    @NamedQuery(name = "Partida.findByPrtId", query = "SELECT p FROM Partida p WHERE p.id = :id"),
    @NamedQuery(name = "Partida.findByUsrId", query = "SELECT p FROM Partida p WHERE p.idUsuario.id = :idUsuario"),
   /* @NamedQuery(name = "Partida.findByPrtNivelBallesta", query = "SELECT p FROM Partida p WHERE p.prtNivelBallesta = :prtNivelBallesta"),
    @NamedQuery(name = "Partida.findByPrtNivelCastillo", query = "SELECT p FROM Partida p WHERE p.prtNivelCastillo = :prtNivelCastillo"),
    @NamedQuery(name = "Partida.findByPrtNivelElixir", query = "SELECT p FROM Partida p WHERE p.prtNivelElixir = :prtNivelElixir"),
    @NamedQuery(name = "Partida.findByPrtNivelPoderMeteoro", query = "SELECT p FROM Partida p WHERE p.prtNivelPoderMeteoro = :prtNivelPoderMeteoro"),
    @NamedQuery(name = "Partida.findByPrtNivelPoderHielo", query = "SELECT p FROM Partida p WHERE p.prtNivelPoderHielo = :prtNivelPoderHielo"),
    @NamedQuery(name = "Partida.findByPrtTipoBallesta", query = "SELECT p FROM Partida p WHERE p.prtTipoBallesta = :prtTipoBallesta"),
    @NamedQuery(name = "Partida.findByPrtMonedas", query = "SELECT p FROM Partida p WHERE p.prtMonedas = :prtMonedas"),
    @NamedQuery(name = "Partida.findByPrtTipoUsoBallesta", query = "SELECT p FROM Partida p WHERE p.prtTipoUsoBallesta = :prtTipoUsoBallesta")*/})
public class Partida implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @SequenceGenerator(name = "TD_PARTIDA_PRT_ID_GENERATOR", sequenceName = "una.TD_PARTIDA_SEQ01", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TD_PARTIDA_PRT_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "PRT_ID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "PRT_NIVEL")
    private Long nivel;
    @Basic(optional = false)
    @Column(name = "PRT_NIVEL_BALLESTA")
    private Long nivelBallesta;
    @Basic(optional = false)
    @Column(name = "PRT_NIVEL_CASTILLO")
    private Long nivelCastillo;
    @Basic(optional = false)
    @Column(name = "PRT_NIVEL_ELIXIR")
    private Long nivelElixir;
    @Basic(optional = false)
    @Column(name = "PRT_NIVEL_PODER_METEORO")
    private Long nivelPoderMeteoro;
    @Basic(optional = false)
    @Column(name = "PRT_NIVEL_PODER_HIELO")
    private Long nivelPoderHielo;
    @Basic(optional = false)
    @Column(name = "PRT_TIPO_BALLESTA")
    private String tipoBallesta;
    @Basic(optional = false)
    @Column(name = "PRT_MONEDAS")
    private Long monedas;
    @Column(name = "PRT_TIPO_USO_BALLESTA")
    private String tipoUsoBallesta;
    @JoinColumn(name = "PRT_ID_USUARIO", referencedColumnName = "USR_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuarios idUsuario;

    public Partida() {
    }

    public Partida(Long prtId) {
        this.id = prtId;
    }

    public Partida(Long id, Long nivel, Long nivelBallesta, Long nivelCastillo, Long nivelElixir, Long nivelPoderMeteoro, Long nivelPoderHielo, String tipoBallesta, Long monedas) {
        this.id = id;
        this.nivel = nivel;
        this.nivelBallesta = nivelBallesta;
        this.nivelCastillo = nivelCastillo;
        this.nivelElixir = nivelElixir;
        this.nivelPoderMeteoro = nivelPoderMeteoro;
        this.nivelPoderHielo = nivelPoderHielo;
        this.tipoBallesta = tipoBallesta;
        this.monedas = monedas;
    }
    
    public Partida(PartidaDto partidaDto) {//este se lama solo cuando sehace un empleado nuevo ....ojo con version
        this.id = partidaDto.getId();
        actualizarPartida(partidaDto);
    }    
    
    public void  actualizarPartida(PartidaDto partida) {
       this.nivel = Long.parseLong(partida.getNivel());
       this.nivelBallesta = Long.parseLong(partida.getNivelBallesta());;
        this.nivelCastillo = Long.parseLong(partida.getNivelCastillo());
        this.nivelElixir = Long.parseLong(partida.getNivelElixir());
        this.nivelPoderMeteoro = Long.parseLong(partida.getNivelPoderMeteoro());
        this.nivelPoderHielo = Long.parseLong(partida.getNivelPoderHielo());
        this.tipoBallesta = partida.getTipoBallesta();
        this.monedas = Long.parseLong(partida.getMonedas());
        this.tipoUsoBallesta = partida.getTipoUsoBallesta();
        
        this.idUsuario= new Usuarios( partida.getUsuario());///agragada para ver que 
    } 
    

    public Long getId() {
        return id;
    }

    public void setId(Long prtId) {
        this.id = prtId;
    }

    public Long getNivel() {
        return nivel;
    }

    public void setNivel(Long prtNivel) {
        this.nivel = prtNivel;
    }

    public Long getNivelBallesta() {
        return nivelBallesta;
    }

    public void setNivelBallesta(Long prtNivelBallesta) {
        this.nivelBallesta = prtNivelBallesta;
    }

    public Long getNivelCastillo() {
        return nivelCastillo;
    }

    public void setNivelCastillo(Long prtNivelCastillo) {
        this.nivelCastillo = prtNivelCastillo;
    }

    public Long getNivelElixir() {
        return nivelElixir;
    }

    public void setNivelElixir(Long prtNivelElixir) {
        this.nivelElixir = prtNivelElixir;
    }

    public Long getNivelPoderMeteoro() {
        return nivelPoderMeteoro;
    }

    public void setNivelPoderMeteoro(Long prtNivelPoderMeteoro) {
        this.nivelPoderMeteoro = prtNivelPoderMeteoro;
    }

    public Long getNivelPoderHielo() {
        return nivelPoderHielo;
    }

    public void setNivelPoderHielo(Long prtNivelPoderHielo) {
        this.nivelPoderHielo = prtNivelPoderHielo;
    }

    public String getTipoBallesta() {
        return tipoBallesta;
    }

    public void setTipoBallesta(String prtTipoBallesta) {
        this.tipoBallesta = prtTipoBallesta;
    }

    public Long getMonedas() {
        return monedas;
    }

    public void setMonedas(Long prtMonedas) {
        this.monedas = prtMonedas;
    }

    public String getTipoUsoBallesta() {
        return tipoUsoBallesta;
    }

    public void setTipoUsoBallesta(String prtTipoUsoBallesta) {
        this.tipoUsoBallesta = prtTipoUsoBallesta;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios prtIdUsuario) {
        this.idUsuario = prtIdUsuario;
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
        if (!(object instanceof Partida)) {
            return false;
        }
        Partida other = (Partida) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cr.ac.una.towerdefense.model.Partida[ prtId=" + id + " ]";
    }
    
}
