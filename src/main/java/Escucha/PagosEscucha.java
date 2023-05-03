/**
 * PagosEscucha.java
 */
package Escucha;

import DAOs.ObrasDAO;
import Dominio.Pagos;
import Herramientas.Fecha;
import java.text.ParseException;
import java.util.Calendar;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Esta clase permite encapsular todos los métodos escucha pertenecientes a
 * entidades de tipo Pago.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class PagosEscucha {
    
    /**
     * Autogenerar:
     * Fecha
     */
    
    /**
     * Llamada a paquete de Herramientas para manipular fechas y encriptar.
     */
    Fecha fecha = new Fecha();
    
    /**
     * Método que escucha cada que se quiera pesistir una entidad Pago
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e pago a guardar.
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(Pagos e) {
        Calendar ahora = fecha.fechaAhora();
        // Se inserta la fecha del pago del preciso momento
        e.setFecha(ahora);
    }

    /**
     * Método que escucha cada que se quiera guardar una entidad Pago
     * para desplegar un mensaje en consola.
     *
     * @param e pago a guardar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PostPersist
    public void guardarPagos(Pagos e) throws ParseException {
        System.out.println("\n" + " + Se agregó la entidad pago: "
                + " - Fecha: " + fecha.formatoFecha(e.getFecha())
                + " - Monto: $ " + e.getMonto() + " MXN"
                + " - ID cliente: " + e.getCliente().getId()
                + " - ID obra: " + e.getObra().getId()
                + " - ID: " + e.getId()
                + "\n");
        ObrasDAO ObrasDAO = new ObrasDAO();
        ObrasDAO.agregarPagoObra(e.getObra().getId(), e.getId());
    }
    
    /**
     * Método que escucha antes que se quiera actulizar una entidad Pago
     * para desplegar un mensaje en consola.
     * 
     * @param e pago a guardar.
     */
    @PreUpdate
    public void PreActualizarPagos(Pagos e) {
        ObrasDAO ObrasDAO = new ObrasDAO();
        ObrasDAO.eliminarPagoObra(e.getObra().getId(), e.getId());
    }
    
    /**
     * Método que escucha después que se haya actulizado una entidad Pago
     * para desplegar un mensaje en consola.
     * 
     * @param e pago a guardar.
     */
    @PostUpdate
    public void PostActualizarPagos(Pagos e) {
        System.out.println("\n" + " > Se actualizó la entidad pago: "
                + " - ID: " + e.getId()
                + "\n");
        ObrasDAO ObrasDAO = new ObrasDAO();
        ObrasDAO.agregarPagoObra(e.getObra().getId(), e.getId());
    }

    /**
     * Método que escucha cada que se quiera eliminar una entidad Pago
     * para desplegar un mensaje en consola.
     *
     * @param e pago a eliminar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PreRemove
    public void removerPagos(Pagos e) throws ParseException {
        System.out.println("\n" + " = Se eliminó la entidad pago: : "
                + " - Fecha: " + fecha.formatoFecha(e.getFecha())
                + " - Monto: $ " + e.getMonto() + " MXN"
                + " - ID cliente: " + e.getCliente().getId()
                + " - ID obra: " + e.getObra().getId()
                + " - ID: " + e.getId()
                + "\n");
        ObrasDAO ObrasDAO = new ObrasDAO();
        ObrasDAO.eliminarPagoObra(e.getObra().getId(), e.getId());
    }  
}
