/**
 * ObrerosEscucha.java
 */
package Escucha;

import Dominio.Obreros;
import Herramientas.Encriptador;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

/**
 * Esta clase permite encapsular todos los métodos escucha pertenecientes a
 * entidades de tipo Obrero.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ObrerosEscucha {
    
    /**
     * Encriptar:
     * Usuario
     * Contraseña
     * Teléfono
     */
    
    /**
     * Método que escucha cada que se quiera pesistir una entidad Obrero
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e obrero a guardar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(Obreros e) throws Exception {
        Encriptador crypt = new Encriptador();
        // Se encriptan datos sensibles
        System.out.println("\n" + "Encriptando datos... ¡Usuario: " + e.usuario
                + " encriptado! Resultado: " + crypt.encrypt(e.usuario)
                + "\n");
        // Encripta usuario
        e.usuario = crypt.encrypt(e.usuario);
        System.out.println("\n" + "Encriptando datos... ¡Contraseña: " + e.contrasena
                + " encriptada! Resultado: " + crypt.encrypt(e.contrasena)
                + "\n");
        // Encripta contraseña
        e.contrasena = crypt.encrypt(e.contrasena);
        System.out.println("\n" + "Encriptando datos... ¡Teléfono: " + e.telefono
                + " encriptado! Resultado: " + crypt.encrypt(e.telefono)
                + "\n");
        // Encripta teléfono
        e.telefono = crypt.encrypt(e.telefono);
    }

    /**
     * Método que escucha cada que se quiera guardar una entidad Obrero
     * para desplegar un mensaje en consola.
     *
     * @param e obrero a guardar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    @PostPersist
    public void guardarObreros(Obreros e) throws Exception {
        Encriptador crypt = new Encriptador();
        System.out.println("\n" + " + Se agregó la entidad obrero: "
                + " - Nombre completo: "
                + e.nombre + " "
                + e.apellidoPaterno + " "
                + e.apellidoMaterno + " "
                + " - Teléfono: " + crypt.decrypt(e.telefono)
                + " - Usuario: " + crypt.decrypt(e.usuario)
                + " - Contraseña: " + crypt.decrypt(e.contrasena)
                + " - Sueldo diario: $ " + e.sueldoDiario + " MXN"
                + " - ID: " + e.id
                + "\n");
    }
    
    /**
     * Método que escucha cada que se quiera actuliza una entidad Obrero
     * para desplegar un mensaje en consola.
     * 
     * @param e obrero a guardar.
     */
    @PostUpdate
    public void actualizarObreros(Obreros e) {
        System.out.println("\n" + " > Se actualizó la entidad obrero: "
                + " - ID: " + e.getId()
                + "\n");
    }

    /**
     * Método que escucha cada que se quiera eliminar una entidad Obrero
     * para desplegar un mensaje en consola.
     *
     * @param e obrero a eliminar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    @PreRemove
    public void removerObreros(Obreros e) throws Exception {
        Encriptador crypt = new Encriptador();
        System.out.println("\n" + " = Se eliminó la entidad obrero: "
                + " - Nombre completo: "
                + e.nombre + " "
                + e.apellidoPaterno + " "
                + e.apellidoMaterno + " "
                + " - Teléfono: " + crypt.decrypt(e.telefono)
                + " - Usuario: " + crypt.decrypt(e.usuario)
                + " - Contraseña: " + crypt.decrypt(e.contrasena)
                + " - Sueldo diario: $ " + e.sueldoDiario + " MXN"
                + " - ID: " + e.id
                + "\n");
    } 
}
