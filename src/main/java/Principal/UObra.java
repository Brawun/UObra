/**
 * UObra.java
 */

package Principal;

import GUI.Acceso.IniciarSesion;

/**
 * Esta clase se utiliza para ejecutar el sistema UObra.
 *
 * Materia: Pruebas de Software
 * Titular: Maria de los Angeles
 * @author Brandon Figueroa Ugalde ID: 00000233295
 * @author Guimel Naely Rubio Morillon ID: 00000229324
 */
public class UObra {

    /**
     * Método main() en el que se invocan a los métodos de las clase #######.
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
