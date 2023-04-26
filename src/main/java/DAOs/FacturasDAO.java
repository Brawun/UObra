/**
 * FacturasDAO.java
 */
package DAOs;

import Dominio.Facturas;
import Enumeradores.EstadoFactura;
import Enumeradores.MetodoPago;
import java.util.Calendar;
import java.util.List;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo Facturas.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class FacturasDAO {
    // Métodos de acceso
    public void registrarFactura(Facturas factura) {
        
    }
    
    public void eliminarFactura(Long id) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarFactura(Long id) {
        
    }
    
    public Facturas consultarFactura(Long id) { 
        
    }
    
    // Consulta una lista de facturas que estén dentro del periodo dado, 
    // con el estado dado, con el método de pago dado y el monto mayor al dado
    public List<Facturas> consultarFacturas(Calendar periodoInicio, Calendar periodoFin, EstadoFactura estado, MetodoPago metodoPago, Float monto) {
        
    }
}
