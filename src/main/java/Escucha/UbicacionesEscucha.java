/**
 * UbicacionesEscucha.java
 */
package Escucha;

import Dominio.Ubicaciones;
import Herramientas.Fecha;
import java.text.ParseException;
import java.util.Calendar;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

/**
 * Esta clase permite encapsular todos los métodos escucha pertenecientes a
 * entidades de tipo Ubicacion.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class UbicacionesEscucha {
    
    /**
     * Autogenerar:
     * Fecha registro
     * Area
     */
    
    /**
     * Llamada a paquete de Herramientas para manipular fechas y encriptar.
     */
    Fecha fecha = new Fecha();
    
    /**
     * Método que escucha cada que se quiera pesistir una entidad Ubicacion
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e ubicacion a guardar.
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(Ubicaciones e) {
        Calendar ahora = fecha.fechaAhora();
        // Se inserta la fecha de registro del preciso momento
        e.setFechaRegistro(ahora);
        // Se calcula el área multiplicando el ancho por el largo
        e.setArea(e.getAncho() * e.getLargo());
    }

    /**
     * Método que escucha cada que se quiera guardar una entidad Ubicacion
     * para desplegar un mensaje en consola.
     *
     * @param e ubicacion a guardar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PostPersist
    public void guardarUbicaciones(Ubicaciones e) throws ParseException {
        System.out.println("\n" + " + Se agregó la entidad ubicacion: "
                + " - Fecha registro: " + fecha.formatoFecha(e.getFechaRegistro())
                + " - Tipo: " + e.getTipo().toString()
                + " - Dirección: " + e.getDireccion()
                + " - Ancho: " + e.getAncho() + " m"
                + " - Largo: " + e.getLargo() + " m"
                + " - Área: " + e.getArea() + " m^2"
                + " - ID cliente: " + e.getCliente().getId()
                + " - ID: " + e.getId()
                + "\n");
    }
    
    /**
     * Método que escucha cada que se quiera actuliza una entidad Ubicacion
     * para desplegar un mensaje en consola.
     * 
     * @param e ubicacion a guardar.
     */
    @PostUpdate
    public void actualizarUbicaciones(Ubicaciones e) {
        System.out.println("\n" + " > Se actualizó la entidad ubicacion: "
                + " - ID: " + e.getId()
                + "\n");
    }

    /**
     * Método que escucha cada que se quiera eliminar una entidad Ubicacion
     * para desplegar un mensaje en consola.
     *
     * @param e ubicacion a eliminar.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PreRemove
    public void removerUbicaciones(Ubicaciones e) throws ParseException {
        System.out.println("\n" + " = Se eliminó la entidad ubicacion: "
                + " - Fecha registro: " + fecha.formatoFecha(e.getFechaRegistro())
                + " - Tipo: " + e.getTipo().toString()
                + " - Dirección: " + e.getDireccion()
                + " - Ancho: " + e.getAncho() + " m"
                + " - Largo: " + e.getLargo() + " m"
                + " - Área: " + e.getArea() + " m^2"
                + " - ID cliente: " + e.getCliente().getId()
                + " - ID: " + e.getId()
                + "\n");
    }
}
