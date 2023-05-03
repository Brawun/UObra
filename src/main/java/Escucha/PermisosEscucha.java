/**
 * PermisosEscucha.java
 */
package Escucha;

import Dominio.Permisos;
import Herramientas.Encriptador;
import Herramientas.Fecha;
import java.text.ParseException;
import java.util.Calendar;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

/**
 * Esta clase permite encapsular todos los métodos escucha pertenecientes a
 * entidades de tipo Permiso.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class PermisosEscucha {
    
    /**
     * Encriptar:
     * Folio
     */
    
    /**
     * Autogenerar:
     * Fecha registro
     */
    
    /**
     * Llamada a paquete de Herramientas para manipular fechas y encriptar.
     */
    Fecha fecha = new Fecha();
    
    /**
     * Método que escucha cada que se quiera pesistir una entidad Permiso
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e permiso a guardar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(Permisos e) throws Exception {
        Encriptador crypt = new Encriptador();
        Calendar ahora = fecha.fechaAhora();
        // Se encriptan datos sensibles
        System.out.println("\n" + "Encriptando datos... ¡Folio: " + e.getFolio()
                + " encriptado! Resultado: " + crypt.encrypt(e.getFolio())
                + "\n");
        // Encripta el folio
        e.setFolio(crypt.encrypt(e.getFolio()));
        // Se inserta la fecha de registro del preciso momento
        e.setFechaRegistro(ahora);
    }

    /**
     * Método que escucha cada que se quiera guardar una entidad Permiso
     * para desplegar un mensaje en consola.
     *
     * @param e permiso a guardar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PostPersist
    public void guardarPermiso(Permisos e) throws ParseException, Exception {
        Encriptador crypt = new Encriptador();
        System.out.println("\n" + " + Se agregó la entidad permiso: "
                + " - Folio: " + crypt.decrypt(e.getFolio())
                + " - Fecha registro: " + fecha.formatoFecha(e.getFechaRegistro())
                + " - Tipo: " + e.getTipo().toString()
                + " - ID jefe: " + e.getJefe().getId()
                + " - ID: " + e.getId()
                + "\n");
    }
    
    /**
     * Método que escucha cada que se quiera actuliza una entidad Permiso
     * para desplegar un mensaje en consola.
     * 
     * @param e permiso a guardar.
     */
    @PostUpdate
    public void actualizarPermisos(Permisos e) {
        System.out.println("\n" + " > Se actualizó la entidad permiso: "
                + " - ID: " + e.getId()
                + "\n");
    }

    /**
     * Método que escucha cada que se quiera eliminar una entidad Permiso
     * para desplegar un mensaje en consola.
     *
     * @param e permiso a eliminar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     * @throws ParseException en caso que haya una excepción con el formato.
     */
    @PreRemove
    public void removerPermiso(Permisos e) throws ParseException, Exception {
        Encriptador crypt = new Encriptador();
        System.out.println("\n" + " = Se eliminó la entidad permiso: "
                + " - Folio: " + crypt.decrypt(e.getFolio())
                + " - Fecha registro: " + fecha.formatoFecha(e.getFechaRegistro())
                + " - Tipo: " + e.getTipo().toString()
                + " - ID jefe: " + e.getJefe().getId()
                + " - ID: " + e.getId()
                + "\n");
    }  
}
