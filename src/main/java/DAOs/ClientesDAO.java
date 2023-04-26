/**
 * ClientesDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Obras;
import Dominio.Pagos;
import Dominio.Ubicaciones;
import java.util.List;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo Cliente.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ClientesDAO {
    // Métodos de acceso
    public void registrarCliente(Clientes cliente) {
        
    }
    
    // Sumar a la deuda del cliente el monto dado
    public void sumarDeudaCliente(Long id, Float monto) {
        
    }
    
    // Restar a la deuda del cliente el monto dado
    public void restarDeudaCliente(Long id, Float monto) {
        
    }
    
    // Se agrega la obra dada al cliente
    public void agregarObraCliente(Long id, Obras obra) {
        
    }
    
    // Se agrega la ubicacion dada al cliente
    public void agregarUbicacionCliente(Long id, Ubicaciones ubicacion) {
        
    }
    
    // Se agrega el pago dado al cliente
    public void agregarPagosCliente(Long id, Pagos pago) {
        
    }
    
    // Se agrega el pago dado al cliente
    public void eliminarCliente(Long id) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarCliente(Long id) {
        
    }
    
    public Clientes consultarCliente(Long id) { 
        
    }
    
    // Regresa una lista de clientes que esten endeudados o no y su deuda sea mayor a la dada
    public List<Clientes> consultarClientes(Boolean endeudados, Float deuda) {
        
    }
}
