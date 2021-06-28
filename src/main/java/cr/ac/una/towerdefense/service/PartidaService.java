/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.service;

import cr.ac.una.towerdefense.model.Partida;
import cr.ac.una.towerdefense.model.PartidaDto;
import cr.ac.una.towerdefense.model.UsuarioDto;
import cr.ac.una.towerdefense.model.Usuarios;
import cr.ac.una.towerdefense.util.EntityManagerHelper;
import cr.ac.una.towerdefense.util.Respuesta;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author HP
 */
public class PartidaService {
    
       EntityManager em = EntityManagerHelper.getInstance().getManager();
    private EntityTransaction et;
    
    public Respuesta getUsuario(String usuario, String clave) {
        try {      
            TypedQuery<Usuarios> query = em.createNamedQuery("Usuario.findByEmpUsuarioClave",Usuarios.class);
            query.setParameter("usuario", usuario);
            query.setParameter("clave", clave);
            return new Respuesta(true, "", "", "Usuario", new UsuarioDto(query.getSingleResult()));
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un usuario con las credenciales ingresadas.", "getUsuario NoResultException, para el usuario [ " + usuario + "]");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el usuario.", "getUsuario NonUniqueResultException, para el usuario [ " + usuario + "]");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + usuario + "]", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta getPartida(Long id) {
        try {
            TypedQuery<Partida> query = em.createNamedQuery("Partida.findByPrtId",Partida.class);
            query.setParameter("id", id);
            /*Partida partida = query.getSingleResult();
            PartidaDto partidaDto = new PartidaDto(partida);*/
            return new Respuesta(true, "", "", "Partida", new PartidaDto(query.getSingleResult()));
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un partida con el código ingresado.", "getPartida NoResultException, para el id [ " + id + "]");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el partida.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el partida.", "getPartida NonUniqueResultException, para el id [ " + id + "]");
        } catch (Exception ex) {
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error obteniendo el partida [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el partida.", "getPartida " + ex.getMessage());
        }
    }
    
    public Respuesta getPartidaPorUsuario(Long idUsuario) {
        try {
            TypedQuery<Partida> query = em.createNamedQuery("Partida.findByUsrId",Partida.class);
            query.setParameter("idUsuario", idUsuario);
            /*Partida partida = query.getSingleResult();
            PartidaDto partidaDto = new PartidaDto(partida);*/
            return new Respuesta(true, "", "", "Partida", new PartidaDto(query.getSingleResult()));
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un partida con el usuario ingresado.", "getPartida NoResultException, para el id [ " + idUsuario + "]");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el partida.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el partida.", "getPartida NonUniqueResultException, para el id [ " + idUsuario + "]");
        } catch (Exception ex) {
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error obteniendo el partida [" + idUsuario + "]", ex);
            return new Respuesta(false, "Error obteniendo el partida.", "getPartida " + ex.getMessage());
        }
    }    
    
    public Respuesta getPartidas() { //no se sabe si sirve Long id
        try {
            Query qryPartida = em.createNamedQuery("Partida.findAll", Partida.class);//Partida.findByDepId
            //qryPartida.setParameter("id", id);
            List<Partida>deportes = qryPartida.getResultList();
            List<PartidaDto> deportesDto = new ArrayList<>();
            deportes.forEach((deporte) -> { 
                deportesDto.add( new PartidaDto(deporte));
             });
            
            return new Respuesta(true, "", "", "Partidas", deportesDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe la partida", "getPartidas NoResultException");
                    
        } catch (Exception ex) {
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Ocurrio error obteniendo deporte.", ex);
            return new Respuesta(false, "Ocurrio error obteniendo partida.", "getPartidas " + ex.getMessage());
        }
    }    
        
    
//    public Respuesta getPartidas(String cedula, String nombre, String pApellido, String sApellido) {
//        try {
//            TypedQuery<Partida> query = em.createNamedQuery("Partida.findByCedulaNombreApellidos",Partida.class);
//            query.setParameter("cedula", cedula);
//            query.setParameter("nombre", nombre);
//            query.setParameter("primerApellido", pApellido);
//            query.setParameter("segundoApellido", sApellido);
//            List<Partida> partidas = (List<Partida>) query.getResultList();
//            List<PartidaDto> partidasDto = new ArrayList<>();
//            for(Partida emp :partidas){
//              //  partidasDto.add(new PartidaDto(emp));
//            }
//            return new Respuesta(true, "", "", "Partidas", partidasDto);
//        } catch (NoResultException ex) {
//            return new Respuesta(false, "No existen partidas con los criterios ingresados.", "getPartidas NoResultException");
//        } catch (Exception ex) {
//            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error obteniendo partidas.", ex);
//            return new Respuesta(false, "Error obteniendo partidas.", "getPartidas " + ex.getMessage());
//        }
//    }
    
    public Respuesta guardarPartida(PartidaDto partidaDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Partida partida;
            if (partidaDto.getId() != null && partidaDto.getId() > 0){//
                partida = em.find(Partida.class, partidaDto.getId());
                if (partida == null){
                    et.rollback();
                    return new Respuesta(false, "No se encrontró el partida a modificar.", "guardarPartida NoResultException");
                }
                //bloque insertar info
                partida.actualizarPartida(partidaDto);
                Usuarios usr = new Usuarios(partidaDto.getUsuario());
                partida.setIdUsuario(usr);
                
                partida = em.merge(partida);
            } else{
                partida = new Partida(partidaDto);
                em.persist(partida);
            }
            et.commit();
            return new Respuesta(true, "", "", "Partida", new PartidaDto(partida));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error guardando el partida.", ex);
            return new Respuesta(false, "Error guardando el partida.", "guardarPartida " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarPartida(Long id) {
        try {
            et = em.getTransaction();
            et.begin();
            Partida partida;
            if (id != null && id > 0){
                partida = em.find(Partida.class, id);
                if (partida == null){
                    et.rollback();
                    return new Respuesta(false, "No se encrontró el partida a eliminar.", "eliminarPartida NoResultException");
                }
                em.remove(partida);
                et.commit();
            } else{
                et.rollback();
                return new Respuesta(false, "Debe cargar el partida a eliminar.", "eliminarPartida NoResultException");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            et.rollback();
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, "No se puede eliminar el partida porque tiene relaciones con otros registros.", "eliminarPartida " + ex.getMessage());
            }
            Logger.getLogger(PartidaService.class.getName()).log(Level.SEVERE, "Error eliminando el partida.", ex);
            return new Respuesta(false, "Error eliminando el partida.", "eliminarPartida " + ex.getMessage());
        }
    }
        
}