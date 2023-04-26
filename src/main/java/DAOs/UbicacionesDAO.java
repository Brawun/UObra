/**
 * UbicacionesDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Ubicaciones;
import Enumeradores.TipoUbicacion;
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
public class UbicacionesDAO {
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
