/**
 * PagosEscucha.java
 */
package Escucha;

import DAOs.ClientesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrerosDAO;
import Dominio.Pagos;
import Enumeradores.MetodoPago;
import Herramientas.Fecha;
import java.text.ParseException;
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
     * Método que escucha cada que se quiera pesistir una entidad Pago
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e pago a guardar.
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(Pagos e) {
        // Se inserta la fecha del pago del preciso momento
        e.setFecha(new Fecha().fechaAhora());
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
                + " - Fecha: " + new Fecha().formatoFecha(e.getFecha())
                + " - Monto: $ " + e.getMonto() + " MXN"
                + " - ID cliente: " + e.getCliente().getId()
                + " - ID obra: " + e.getObra().getId()
                + " - ID obrero: " + e.getObrero() != null ? e.getObrero().getId() : "No aplica"
                + " - ID: " + e.getId()
                + "\n");
    }
    
    /**
     * Método que escucha antes que se quiera actulizar una entidad Pago
     * para desplegar un mensaje en consola.
     * 
     * @param e pago a guardar.
     */
//    @PreUpdate
//    public void PreActualizarPagos(Pagos e) {
//        // Se elimina el pago de obra
//        new ObrasDAO().consultarObra(e.getObra().getId()).getPagos().remove(e);
//        // Se actualiza la deuda de cliente sumando el monto del pago asignado
//        new ClientesDAO().sumarDeudaCliente(e.getCliente().getId(), e.getMonto());
//        // Si el pago se hizo en efectivo se relaciona con un obrero
//        if (e.getMetodoPago() == MetodoPago.EFECTIVO) {
//            new ObrerosDAO().eliminarPagoObrero(e.getObrero().getId(), e);
//        }
//    }
    
    /**
     * Método que escucha después que se haya actulizado una entidad Pago
     * para desplegar un mensaje en consola.
     * 
     * @param e pago a guardar.
     */
    @PostUpdate
    public void PostActualizarPagos(Pagos e) {
//        // Se agrega el pago de obra
//        new ObrasDAO().consultarObra(e.getObra().getId()).getPagos().add(e);
//        // Se actualiza la deuda de cliente restando el monto del pago asignado
//        new ClientesDAO().restarDeudaCliente(e.getCliente().getId(), e.getMonto());
//        // Si el pago se hizo en efectivo se relaciona con un obrero
//        if (e.getMetodoPago() == MetodoPago.EFECTIVO) {
//            new ObrerosDAO().agregarPagoObrero(e.getObrero().getId(), e);
//        }
        // Se imprime en consola un mensaje
        System.out.println("\n" + " > Se actualizó la entidad pago: "
                + " - ID: " + e.getId()
                + "\n");
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
//        // Se elimina el pago de obra
//        new ObrasDAO().consultarObra(e.getObra().getId()).getPagos().remove(e);
//        // Se actualiza la deuda de cliente sumando el monto del pago asignado
//        new ClientesDAO().sumarDeudaCliente(e.getCliente().getId(), e.getMonto());
//        // Se imprime en consola
        System.out.println("\n" + " = Se eliminó la entidad pago: : "
                + " - Fecha: " + new Fecha().formatoFecha(e.getFecha())
                + " - Monto: $ " + e.getMonto() + " MXN"
                + " - ID cliente: " + e.getCliente().getId()
                + " - ID obra: " + e.getObra().getId()
                + " - ID obrero: " + e.getObrero().getId() != null ? e.getObrero().getId() : "No aplica"
                + " - ID: " + e.getId()
                + "\n");
    }  
}
