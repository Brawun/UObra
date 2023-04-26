/**
 * JefesDAO.java
 */
package DAOs;

import Dominio.Jefes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo Jefes.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class JefesDAO {
    
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("com.itson_AgenciaTransito");
    EntityManager entityManager = managerFactory.createEntityManager();
    
    // Métodos de acceso
    public void registrarJefe(Jefes jefe) {
        
    }
    
    public void eliminarJefe(Long id) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarJefe(Long id) {
        
    }
    
    public Jefes consultarJefe(Long id) { 
        
    }
    
    public List<Jefes> consultarJefes() { 
        
    }
}
