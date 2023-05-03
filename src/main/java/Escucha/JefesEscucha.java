/**
 * JefesEscucha.java
 */
package Escucha;

import Dominio.Jefes;
import Herramientas.Encriptador;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;

/**
 * Esta clase permite encapsular todos los métodos escucha pertenecientes a
 * entidades de tipo Jefe.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class JefesEscucha {
    
    /**
     * Encriptar:
     * Usuario
     * Contraseña
     * Teléfono
     */
    
    /**
     * Método que escucha cada que se quiera pesistir una entidad Jefe
     * se calcularán ciertos atributos referentes a la misma y se encriptarán 
     * otros antes de ser guardados en la base de datos.
     *
     * @param e jefe a guardar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    @PrePersist
    public void calcularAtributosAutogeneradosYEncriptar(Jefes e) throws Exception {
        Encriptador crypt = new Encriptador();
        // Se encriptan datos sensibles
        System.out.println("\n" + "Encriptando datos... ¡Usuario: " + e.getUsuario()
                + " encriptado! Resultado: " + crypt.encrypt(e.getUsuario())
                + "\n");
        // Encripta usuario
        e.setUsuario(crypt.encrypt(e.getUsuario()));
        System.out.println("\n" + "Encriptando datos... ¡Contraseña: " + e.getContrasena()
                + " encriptada! Resultado: " + crypt.encrypt(e.getContrasena())
                + "\n");
        // Encripta contraseña
        e.setContrasena(crypt.encrypt(e.getContrasena()));
        System.out.println("\n" + "Encriptando datos... ¡Teléfono: " + e.getTelefono()
                + " encriptado! Resultado: " + crypt.encrypt(e.getTelefono())
                + "\n");
        // Encripta teléfono
        e.setTelefono(crypt.encrypt(e.getTelefono()));
    }

    /**
     * Método que escucha cada que se quiera guardar una entidad Jefe
     * para desplegar un mensaje en consola.
     *
     * @param e jefe a guardar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    @PostPersist
    public void guardarJefes(Jefes e) throws Exception {
        Encriptador crypt = new Encriptador();
        System.out.println("\n" + " + Se agregó la entidad jefe: "
                + " - Nombre completo: "
                + e.getNombre()
                + e.getApellidoPaterno()
                + e.getApellidoMaterno()
                + " - Teléfono: " + crypt.decrypt(e.getTelefono())
                + " - Usuario: " + crypt.decrypt(e.getUsuario())
                + " - Contraseña: " + crypt.decrypt(e.getContrasena())
                + " - ID: " + e.getId()
                + "\n");
    }
    
    /**
     * Método que escucha cada que se quiera actuliza una entidad Jefe
     * para desplegar un mensaje en consola.
     * 
     * @param e jefe a guardar.
     */
    @PostUpdate
    public void actualizarJefes(Jefes e) {
        System.out.println("\n" + " > Se actualizó la entidad jefe: "
                + " - ID: " + e.getId()
                + "\n");
    }

    /**
     * Método que escucha cada que se quiera eliminar una entidad Jefe
     * para desplegar un mensaje en consola.
     *
     * @param e jefe a eliminar.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    @PreRemove
    public void removerJefes(Jefes e) throws Exception {
        Encriptador crypt = new Encriptador();
        System.out.println("\n" + " = Se eliminó la entidad jefe: "
                + " - Nombre completo: "
                + e.getNombre()
                + e.getApellidoPaterno()
                + e.getApellidoMaterno()
                + " - Teléfono: " + crypt.decrypt(e.getTelefono())
                + " - Usuario: " + crypt.decrypt(e.getUsuario())
                + " - Contraseña: " + crypt.decrypt(e.getContrasena())
                + " - ID: " + e.getId()
                + "\n");
    }   
}
