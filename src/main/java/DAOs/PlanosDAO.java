/**
 * PlanosDAO.java
 */
package DAOs;

import Dominio.Jefes;
import Dominio.Planos;
import Enumeradores.Escala;
import Enumeradores.TipoPlano;
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
public class PlanosDAO {
    
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
    public void registrarPlano(Planos plano) {
        
    }
    
    public void eliminarPlano(Long id) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarPlano(Long id) {
        
    }
    
    public Planos consultarPlano(Long id) { 
        
    }
    
    // Regresa una lista de planos que hayan sido realizados dentro del periodo
    // dado, con la escala dada, del tipo dado y registrados por el jefe dado
    public List<Planos> consultarPlanos(Calendar periodoInicio, Calendar periodoFin, Escala escala, TipoPlano tipo, Jefes jefe) {
        
    }
}
