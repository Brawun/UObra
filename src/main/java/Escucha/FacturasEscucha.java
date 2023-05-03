/**
 * FacturasEscucha.java
 */
package Escucha;

import Dominio.Facturas;
import Herramientas.Fecha;
import java.text.ParseException;
import java.util.Calendar;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

/**
 * Esta clase permite encapsular todos los métodos escucha pertenecientes a
 * entidades de tipo Factura.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class FacturasEscucha {
    
    /**
     * Autogenerar:
     * Fecha creada
     */
    
    /**
     * Llamada a paquete de Herramientas para manipular fechas y encriptar.
     */
    Fecha fecha = new Fecha();
    
    /**
     * Método que escucha cada que se quiera pesistir una entidad Cliente
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e factura a guardar
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(Facturas e) {
        Calendar ahora = fecha.fechaAhora();
        // Se inserta la fecha de creación del preciso momento
        e.setFechaCreada(ahora);
    }

    /**
     * Método que escucha cada que se quiera guardar una entidad Cliente
     * para desplegar un mensaje en consola.
     *
     * @param e factura a guardar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PostPersist
    public void guardarFacturas(Facturas e) throws ParseException {
        System.out.println("\n" + " + Se agregó la entidad factura: "
                + " - Creada el: " + fecha.formatoFecha(e.getFechaCreada())
                + " - Monto: $ " + e.getMonto() + " MXN"
                + " - Descripción: " + e.getDescripcion()
                + " - ID jefe: " + e.getJefe().getId()
                + " - ID: " + e.getId()
                + "\n");
    }
    
    /**
     * Método que escucha cada que se quiera actuliza una entidad Factura
     * para desplegar un mensaje en consola.
     * 
     * @param e factura a guardar.
     */
    @PostUpdate
    public void actualizarFacturas(Facturas e) {
        System.out.println("\n" + " > Se actualizó la entidad factura: "
                + " - ID: " + e.getId()
                + "\n");
    }

    /**
     * Método que escucha cada que se quiera eliminar una entidad Cliente
     * para desplegar un mensaje en consola.
     *
     * @param e factura a eliminar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PreRemove
    public void removerFacturas(Facturas e) throws ParseException {
        System.out.println("\n" + " = Se eliminó la entidad factura: "
                + " - Creada el: " + fecha.formatoFecha(e.getFechaCreada())
                + " - Monto: $ " + e.getMonto() + " MXN"
                + " - Descripción: " + e.getDescripcion()
                + " - ID jefe: " + e.getJefe().getId()
                + " - ID: " + e.getId()
                + "\n");
    }
}
