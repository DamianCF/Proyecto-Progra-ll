/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.towerdefense.service;

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
public class UsuarioService {
    
       
    EntityManager em = EntityManagerHelper.getInstance().getManager();
    
    private EntityTransaction et;
    
    public Respuesta getUsuario(String usuario, String clave) {
        try {      
            TypedQuery<Usuarios> query = em.createNamedQuery("Usuarios.findUser",Usuarios.class);
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
    
    public Respuesta getUsuario(Long id) {
        try {
            TypedQuery<Usuarios> query = em.createNamedQuery("Usuarios.findByUsrId",Usuarios.class);
            query.setParameter("id", id);
            /*Usuario usuario = query.getSingleResult();
            UsuarioDto usuarioDto = new UsuarioDto(usuario);*/
            return new Respuesta(true, "", "", "Usuario", new UsuarioDto(query.getSingleResult()));
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existe un usuario con el código ingresado.", "getUsuario NoResultException, para el id [ " + id + "]");
        } catch (NonUniqueResultException ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Ocurrio un error al consultar el usuario.", ex);
            return new Respuesta(false, "Ocurrio un error al consultar el usuario.", "getUsuario NonUniqueResultException, para el id [ " + id + "]");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo el usuario [" + id + "]", ex);
            return new Respuesta(false, "Error obteniendo el usuario.", "getUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta getUsuarios() { //no se sabe si sirve Long id
        try {
            Query qryUsuario = em.createNamedQuery("Usuarios.findAll", Usuarios.class);//Usuario.findByDepId
            List<Usuarios>usuarios = qryUsuario.getResultList();
            List<UsuarioDto> usuarioDto = new ArrayList<>();
            usuarios.forEach((usuario) -> { 
                usuarioDto.add( new UsuarioDto(usuario));
             });
            
            return new Respuesta(true, "", "", "Usuarios", usuarioDto);

        } catch (NoResultException ex) {
            return new Respuesta(false, "No existen usuarios con los criterios usuarios", "getUsuarios NoResultException");
                    
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Ocurrio error obteniendo usuario.", ex);
            return new Respuesta(false, "Ocurrio error obteniendo usuario.", "getUsuarios " + ex.getMessage());
        }
    }    
        
    
    public Respuesta getUsuarios(String cedula, String nombre, String pApellido, String sApellido) {
        try {
            TypedQuery<Usuarios> query = em.createNamedQuery("Usuario.findByCedulaNombreApellidos",Usuarios.class);
            query.setParameter("cedula", cedula);
            query.setParameter("nombre", nombre);
            query.setParameter("primerApellido", pApellido);
            query.setParameter("segundoApellido", sApellido);
            List<Usuarios> usuarios = (List<Usuarios>) query.getResultList();
            List<UsuarioDto> usuariosDto = new ArrayList<>();
            for(Usuarios emp :usuarios){
                usuariosDto.add(new UsuarioDto(emp));
            }
            return new Respuesta(true, "", "", "Usuarios", usuariosDto);
        } catch (NoResultException ex) {
            return new Respuesta(false, "No existen usuarios con los criterios ingresados.", "getUsuarios NoResultException");
        } catch (Exception ex) {
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error obteniendo usuarios.", ex);
            return new Respuesta(false, "Error obteniendo usuarios.", "getUsuarios " + ex.getMessage());
        }
    }
    
    public Respuesta guardarUsuario(UsuarioDto usuarioDto) {
        try {
            et = em.getTransaction();
            et.begin();
            Usuarios usuario;
            if (usuarioDto.getId() != null && usuarioDto.getId() > 0){//seria para modificar el usuario
                usuario = em.find(Usuarios.class, usuarioDto.getId());
                if (usuario == null){
                    et.rollback();
                    return new Respuesta(false, "No se encrontró el usuario a modificar.", "guardarUsuario NoResultException");
                }
                usuario.actualizarUsuario(usuarioDto);
                usuario = em.merge(usuario);
            } else{
                usuario = new Usuarios(usuarioDto);
                em.persist(usuario);
            }
            et.commit();
            return new Respuesta(true, "", "", "Usuario", new UsuarioDto(usuario));
        } catch (Exception ex) {
            et.rollback();
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error guardando el usuario.", ex);
            return new Respuesta(false, "Error guardando el usuario.", "guardarUsuario " + ex.getMessage());
        }
    }
    
    public Respuesta eliminarUsuario(Long id) {
        try {
            et = em.getTransaction();
            et.begin();
            Usuarios usuario;
            if (id != null && id > 0){
                usuario = em.find(Usuarios.class, id);
                if (usuario == null){
                    et.rollback();
                    return new Respuesta(false, "No se encrontró el usuario a eliminar.", "eliminarUsuario NoResultException");
                }
                em.remove(usuario);
                et.commit();
            } else{
                et.rollback();
                return new Respuesta(false, "Debe cargar el usuario a eliminar.", "eliminarUsuario NoResultException");
            }
            return new Respuesta(true, "", "");
        } catch (Exception ex) {
            et.rollback();
            if (ex.getCause() != null && ex.getCause().getCause().getClass() == SQLIntegrityConstraintViolationException.class) {
                return new Respuesta(false, "No se puede eliminar el usuario porque tiene relaciones con otros registros.", "eliminarUsuario " + ex.getMessage());
            }
            Logger.getLogger(UsuarioService.class.getName()).log(Level.SEVERE, "Error eliminando el usuario.", ex);
            return new Respuesta(false, "Error eliminando el usuario.", "eliminarUsuario " + ex.getMessage());
        }
    }
    
}
