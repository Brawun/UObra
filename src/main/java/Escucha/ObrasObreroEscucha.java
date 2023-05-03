/**
 * ObrasObreroEscucha.java
 */
package Escucha;

import Dominio.ObrasObrero;
import Herramientas.Fecha;
import java.text.ParseException;
import java.util.Calendar;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

/**
 * Esta clase permite encapsular todos los métodos escucha pertenecientes a
 * entidades de tipo Obra - Obrero.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ObrasObreroEscucha {
    
    /**
     * Autogenerar:
     * Fecha inicio
     */
    
    /**
     * Llamada a paquete de Herramientas para manipular fechas y encriptar.
     */
    Fecha fecha = new Fecha();
    
    /**
     * Método que escucha cada que se quiera pesistir una entidad Obra - Obrero
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e obra - obrero a guardar.
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(ObrasObrero e) {
        Calendar ahora = fecha.fechaAhora();
        // Se inserta la fecha de inicio de la relación del preciso momento
        e.setFechaInicio(ahora);
    }

    /**
     * Método que escucha cada que se quiera guardar una entidad Obra - Obrero
     * para desplegar un mensaje en consola.
     *
     * @param e obra - obrero a guardar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PostPersist
    public void guardarObrasObrero(ObrasObrero e) throws ParseException {
        System.out.println("\n" + " + Se agregó la entidad obra - obrero: "
                + " - Fecha inicio: " + fecha.formatoFecha(e.getFechaInicio())
                + " - ID obra: " + e.getObra().getId()
                + " - ID obrero: " + e.getObrero().getId()
                + " - ID: " + e.getId()
                + "\n");
    }
    
    /**
     * Método que escucha cada que se quiera actuliza una entidad Obra - Obrero
     * para desplegar un mensaje en consola.
     * 
     * @param e obra - obrero a guardar.
     */
    @PostUpdate
    public void actualizarObrasObrero(ObrasObrero e) {
        System.out.println("\n" + " > Se actualizó la entidad obra - obrero: "
                + " - ID: " + e.getId()
                + "\n");
    }

    /**
     * Método que escucha cada que se quiera eliminar una entidad Obra - Obrero
     * para desplegar un mensaje en consola.
     *
     * @param e obra - obrero a eliminar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PreRemove
    public void removerObrasObrero(ObrasObrero e) throws ParseException {
        System.out.println("\n" + " = Se eliminó la entidad obra - obrero: : "
                + " - Fecha inicio: " + fecha.formatoFecha(e.getFechaInicio())
                + " - ID obra: " + e.getObra().getId()
                + " - ID obrero: " + e.getObrero().getId()
                + " - ID: " + e.getId()
                + "\n");
    } 
}
