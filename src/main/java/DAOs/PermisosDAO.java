/**
 * PermisosDAO.java
 */
package DAOs;

import Dominio.Jefes;
import Dominio.Permisos;
import Enumeradores.TipoPermiso;
import java.util.Calendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo #.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class PermisosDAO {
    
    /**
     * Se establece una conexión con la base de datos UObra mediante JPA,
     * creando un objeto EntityManager que puede ser utilizado para realizar
     * operaciones de creación, lectura, actualización y eliminación en la base
     * de datos utilizando el lenguaje JPQL.
     */
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
    EntityManager entityManager = managerFactory.createEntityManager();
    
    /**
     * Método para persistir la entidad de la clase a la base de datos, en caso
     * que no se pueda realizar dicha transacción se cancela el guardado de la 
     * entidad.
     * 
     * @param object Objeto a guardar en la base de datos perteneciente a la clase
     */
    public void persist(Object object) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }
    
    // Métodos de acceso
    public void registrarPermiso(Permisos permiso) {
        
    }
    
    public void eliminarPermiso(Long id) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarPermiso(Long id) {
        
    }
    
    public Permisos consultarPermiso(Long id) { 
        
    }
    
    // Regresa una lista de permisos que hayan sido concecidos dentro del periodo
    // dado, correspondan al tipo dado y hayan sido registrados por el jefe si es
    // que es dado
    public List<Permisos> consultarPermisos(Calendar periodoInicio, Calendar periodoFin, TipoPermiso tipo, Jefes jefe) {
        
    }
}
