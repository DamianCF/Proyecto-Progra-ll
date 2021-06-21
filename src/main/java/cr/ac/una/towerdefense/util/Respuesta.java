/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.util;

import java.io.Serializable;
import java.util.HashMap;
/**
 *
 * @author ccarranza
 */
public class Respuesta implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Boolean estado;
    private String mensaje;    
    private String mensajeInterno;
    private HashMap<String, Object> resultado; 

    public Respuesta() {
        this.resultado = new HashMap<>();
    }

    public Respuesta(Boolean estado, String mensaje, String mensajeInterno) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.mensajeInterno = mensajeInterno;
        this.resultado = new HashMap<>();
    }
    
    public Respuesta(Boolean estado, String mensaje, String mensajeInterno, String usuario, Object resultado) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.mensajeInterno = mensajeInterno;
        this.resultado = new HashMap<>();
        this.resultado.put(usuario, resultado);
    }
    
    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeInterno() {
        return mensajeInterno;
    }

    public void setMensajeInterno(String mensajeInterno) {
        this.mensajeInterno = mensajeInterno;
    }
    
    public Object getResultado(String usuario) {
        return resultado.get(usuario);
    }

    public void setResultado(String usuario, Object resultado) {
        this.resultado.put(usuario, resultado);
    }    
}
