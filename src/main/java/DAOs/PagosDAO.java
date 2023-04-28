/**
 * PagosDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Obras;
import Dominio.Obreros;
import Dominio.Pagos;
import Enumeradores.MetodoPago;
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
public class PagosDAO {
    
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
    public void registrarPago(Pagos pago) {
        
    }
    
    public void eliminarPago(Long id) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarPago(Long id) {
        
    }
    
    public Pagos consultarPago(Long id) { 
        
    }
    
    // Regresa una lista de pagos que se hayan hecho dentro del periodo definido
    // se hayan pagado con cierto método de pago, tenga un monto mayor o igual
    // al dado, hayan sido recibidas por cierto obrero, si aplica, hayan sido 
    // hechos por dado cliente y que pertenece a la obra dada
    public List<Pagos> consultarPagos(Calendar periodoInicio, Calendar periodoFin, MetodoPago metodoPago, Float monto, Obreros obrero, Obras obra, Clientes cliente) {
        
    }
}
