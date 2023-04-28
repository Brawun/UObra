/**
 * JefesDAO.java
 */
package DAOs;

import Dominio.Jefes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo Jefes.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class JefesDAO {
    
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
    public void registrarJefe(Jefes jefe) {
        entityManager.getTransaction().begin();
        this.persist(jefe);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    public void eliminarJefe(Long id) {
        if (verificarJefe(id)) {
            entityManager.getTransaction().begin();
            Jefes jefe = consultarJefe(id);
            entityManager.remove(jefe);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con ID: " + id);
        }
    }
    
    // Métodos de consulta 
    public Boolean verificarJefe(Long id) {
        entityManager.getTransaction().begin();
        Jefes jefe = entityManager.find(Jefes.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return jefe != null;
    }
    
    public Jefes consultarJefe(Long id) {
        if (verificarJefe(id)) {
            entityManager.getTransaction().begin();
            Jefes jefe = entityManager.find(Jefes.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return jefe;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con ID: " + id);
        }
    }
    
    // Método para consultar todos los jefes
    public List<Jefes> consultarJefes() { 
        entityManager.getTransaction().begin();
        TypedQuery<Jefes> query;
        String jpql = "SELECT j FROM Jefes j";
        query = entityManager.createQuery(jpql, Jefes.class);
        List<Jefes> jefes = query.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return jefes;
    }
}
