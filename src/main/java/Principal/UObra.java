/**
 * UObra.java
 */
package Principal;

import GUI.Acceso.IniciarSesion;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Esta clase principal permite ejecutra el sistema UObra.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class UObra {

    /**
     * Método main() en el que se invocan a los métodos de las clase #######.
     *
     * @param args Los argumentos en la línea de comando
     */
    public static void main(String[] args) {
        // Se abre el formulario de inicio de sesión
        IniciarSesion inicioSesion = new IniciarSesion();
        inicioSesion.setVisible(true);
        // Nombre BD: UObra
        // Contraseña: 1234
    }
}
