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

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo #.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class PlanosDAO {
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
