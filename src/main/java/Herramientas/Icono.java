/**
 * Icono.java
 */
package Herramientas;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Esta clase permite encapsular herramientas útiles a la hora de querer
 * implementar íconos personalizados en las ventanas de sistema.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class Icono {
    
    /**
     * Método que recibe como parámetro un objeto JFrame y tiene como objetivo 
     * establecer un ícono personalizado para la ventana.
     * 
     * @param frame Frame al que se le insertará la icono personalizado.
     */
    public void insertarIcono(JFrame frame) {
        // Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\naely\\Downloads\\Itson\\6to Semestre\\Pruebas de software\\UC3\\UObras\\UObra\\src\\main\\java\\Multimedia\\Icono.png");
        Image image = Toolkit.getDefaultToolkit().getImage("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\Icono.png");
        if (image != null) {
            frame.setIconImage(image);
        }
    }
    
    public ImageIcon obtenerIcono() {
        ImageIcon icono = new ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\Icono.png");
        return icono;
    }
}
