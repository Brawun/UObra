/**
 * PermisosDAO.java
 */
package DAOs;

import Dominio.Jefes;
import Dominio.Obras;
import Dominio.Permisos;
import Enumeradores.TipoPermiso;
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
public class PermisosDAO {
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
