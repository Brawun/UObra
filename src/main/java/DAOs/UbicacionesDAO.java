/**
 * UbicacionesDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Ubicaciones;
import Enumeradores.TipoUbicacion;
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
public class UbicacionesDAO {
    
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
    public void registrarUbicacion(Ubicaciones ubicacion) {
        
    }
    
    public void eliminarUbicacion(Long id) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarUbicacion(Long id) {
        
    }
    
    public Ubicaciones consultarUbicacion(Long id) { 
        
    }
    
    // Regresa una lista de ubicaciones que hayan sido registradas dentro del 
    // periodo dado, estén o no disponibles, sean del tipo dado, tengan un area
    // mayor o igual a la dada y hayan sido registradas por el cliente dado
    public List<Ubicaciones> consultarUbicaciones(Calendar periodoInicio, Calendar periodoFin, Boolean disponible, TipoUbicacion tipo, Float area, Clientes cliente) {
        
    }
}
