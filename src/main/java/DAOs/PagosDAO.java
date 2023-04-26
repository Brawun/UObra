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

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo #.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class PagosDAO {
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
