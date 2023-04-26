/**
 * ObrasObreroDAO.java
 */
package DAOs;

import Dominio.Obras;
import Dominio.ObrasObrero;
import java.util.Calendar;
import java.util.List;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo #.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ObrasObreroDAO {
    // Métodos de acceso
    public void registrarObraObrero(ObrasObrero obrasObrero) {
        
    }
    
    public void eliminarObrasObrero(Long id) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarObrasObrero(Long id) {
        
    }
    
    public ObrasObrero consultarObrasObrero(Long id) { 
        
    }
    
    // Manda una lista de obras - obreros que cumplan con el periodo de inicio 
    // y fin, con el estado de activa y con la obra en particular
    public List<ObrasObrero> consultarObrasObreros(Calendar periodoInicio, Calendar periodoFin, Boolean activa, Obras obra) {
        
    } 
}
