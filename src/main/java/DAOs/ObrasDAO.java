/**
 * ObrasDAO.java
 */
package DAOs;

import Dominio.Jefes;
import Dominio.Obras;
import Dominio.Obreros;
import Enumeradores.EstadoObra;
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
public class ObrasDAO {
    
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("com.itson_AgenciaTransito");
    EntityManager entityManager = managerFactory.createEntityManager();
    
    // Métodos de acceso
    public void registrarObra(Obras obra) {
        
    }
    
    public void eliminarObra(Long id) {
        
    }
    
    // Cambia la obra al estado dado
    public void cambiarEstadoObra(EstadoObra estadoNuevo) { 
        
    }
    
    // Inserta una fecha inicio
    public void iniciarObra(Long id) { 
        
    }
    
    // Inserta una fecha fin
    public void terminarObra(Long id) {
        
    }
    
    // Cambia el booleando pagada a true
    public void pagarObra(Long id) {
        
    }
    
    // Cambia el booleando pagada a false
    public void endeudarObra(Long id) {
        
    }
    
    // Suma monto a deuda
    public void restarDeudaObra(Long id, Float monto) {
        
    }
    
    // Resta monto a deuda
    public void sumarDeudaObra(Long id, Float monto) {
        
    }
    
    // Suma monto a costo arranque
    public void sumarCostoArranqueObra(Long id, Float monto) { 
        
    }
    
    // Resta monto a costo arranque
    public void restarCostoArranqueObra(Long id, Float monto) {
        
    }
    
    // Suma monto a inversion
    public void sumarInversionObra(Long id, Float monto) { 
        
    }
    
    // Resta monto a inversion
    public void restarInversionObra(Long id, Float monto) { 
        
    }
    
    // Se asigna un jefe a una obra
    public void asignarJefeObra(Long id, Jefes jefe) { 
        
    }
    
    // Asigna un nuevo obrero a una obra
    public void asingarObreroObra(Long id, Obreros obrero) {
        
    }
    
    // Asigna 3 nuevos obreros a una obra, para que esta pueda comenzar
    public void asingarTresObrerosObra(Long id, Obreros obrero1, Obreros obrero2, Obreros obrero3) { 
        
    }
    
    // Asigna un nuevo pago a una obra 
    public void agregarPagoObra(Long idObra, Long idPago) { 
        
    }
    
    // Asigna un nuevo plano a obra
    public void agregarPlanoObra(Long idObra, Long idPlano) { 
        
    }
    
    // Asigna un nuevo permiso a obra
    public void agregarPermisoObra(Long idObra, Long idPermiso) { 
        
    }
    
    // Asigna una nueva ubicación a obra
    public void agregarUbicacionObra(Long idObra, Long idUbicacion) {
        
    }
    
    // Métodos de consulta 
    public Boolean verificarObra(Long id) {
        
    }
    
    public Obras consultarObra(Long id) { 
        
    }
    
    // Arroja una lista de obras que hayan iniciado dentro del periodo dado, 
    // tengan el estado dado y estén o no pagadas
    public List<Obras> consultarObras(Calendar periodoInicio, Calendar periodoFin, EstadoObra estado, Boolean pagada) { 
        
    }
}
