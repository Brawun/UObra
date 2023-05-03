/**
 * ObrasEscucha.java
 */
package Escucha;

import Dominio.Obras;
import Herramientas.Fecha;
import java.text.ParseException;
import java.util.Calendar;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

/**
 * Esta clase permite encapsular todos los métodos escucha pertenecientes a
 * entidades de tipo Obra.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ObrasEscucha {
    
    /**
     * Autogenerar:
     * Fecha solicitada
     * Deuda = Costo total
     * Costo total = costo arranque + inversion
     */
    
    /**
     * Llamada a paquete de Herramientas para manipular fechas y encriptar.
     */
    Fecha fecha = new Fecha();
    
    /**
     * Método que escucha cada que se quiera pesistir una entidad Obra
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e obra a guardar.
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(Obras e) {
        Calendar ahora = fecha.fechaAhora();
        // Se inserta la fecha de solicitud del preciso momento
        e.setFechaSolicitada(ahora);
        // Se inserta el costo total de la obra, calculado a partir del costo de 
        // arranque e inversión
        e.setCostoTotal(e.getCostoArranque() + e.getInversion());
        // Se inserta la deuda basándose en el costo total de la obra
        e.setDeuda(e.getCostoTotal());
    }

    /**
     * Método que escucha cada que se quiera guardar una entidad Obra
     * para desplegar un mensaje en consola.
     *
     * @param e obra a guardar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PostPersist
    public void guardarObras(Obras e) throws ParseException {
        System.out.println("\n" + " + Se agregó la entidad obra: "
                + " - Nombre: " + e.getNombre()
                + " - Fecha solicitada: " + fecha.formatoFecha(e.getFechaSolicitada())
                + " - Costo total: $ " + e.getCostoTotal() + " MXN"
                + " - ID cliente: " + e.getCliente().getId()
                + " - ID: " + e.getId()
                + "\n");
    }
    
    /**
     * Método que escucha cada que se quiera actuliza una entidad Obra
     * para desplegar un mensaje en consola.
     * 
     * @param e obra a guardar.
     */
    @PostUpdate
    public void actualizarObras(Obras e) {
        System.out.println("\n" + " > Se actualizó la entidad obra: "
                + " - ID: " + e.getId()
                + "\n");
    }

    /**
     * Método que escucha cada que se quiera eliminar una entidad Obra
     * para desplegar un mensaje en consola.
     *
     * @param e obra a eliminar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PreRemove
    public void removerObras(Obras e) throws ParseException {
        System.out.println("\n" + " = Se eliminó la entidad obra: "
                + " - Nombre: " + e.getNombre()
                + " - Fecha solicitada: " + fecha.formatoFecha(e.getFechaSolicitada())
                + " - Costo total: $ " + e.getCostoTotal() + " MXN"
                + " - ID cliente: " + e.getCliente().getId()
                + " - ID: " + e.getId()
                + "\n");
    }   
}
