/**
 * Validadores.java
 */
package Herramientas;

// Importaciones
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Esta clase permite encapsular herramientas útiles a la hora de querer
 * implementar validaciones.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class Validadores {
    
    /**
     * Método para validar que un teléfono cumpla con el formato de: (111)
     * 111-1111
     *
     * @param telefono Teléfono a validar
     * @return Verdadero si el teléfono tiene el formato correcto, falso en caso
     * contrario
     */
    public static boolean validarTelefono(String telefono) {
        // Se remueven espacios en blanco
        CharSequence cadena = telefono.trim();
        // Expresión regular
        String patron = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
        // Se crea un objeto de tipo pattern para verificar patrones
        Pattern p = Pattern.compile(patron);
        // Objeto de verificación
        Matcher matcher = p.matcher(cadena);
        // Devuelve verdadero si la cadena coincide con el patrón
        return matcher.matches();
    }

    /**
     * Método para validar nombres y apellidos, verifica si una cadena contiene
     * solamente letras mayúsculas y minúsculas.
     * 
     * @param nombre Nombre a validar.
     * @return Verdadero si el nombre tiene el formato correcto, falso en caso
     * contrario 
     */
    public boolean validarNombre(String nombre) {
        // Se remueven espacios en blanco
        CharSequence cadena = nombre.trim();
        // Expresión regular
        String patron = "^[a-zA-Z]+$"; 
        // Se crea un objeto de tipo pattern para verificar patrones
        Pattern p = Pattern.compile(patron);
        // Objeto de verificación
        Matcher matcher = p.matcher(cadena);
        // Devuelve verdadero si la cadena coincide con el patrón
        return matcher.matches();
    }

    /**
     * Método para validar folios, se verifica que sean cadenas compuestas de 
     * solamente números de una longuitud constante de 6 dígitos.
     * 
     * @param folio Folio a validar.
     * @return Verdadero si el folio tiene el formato correcto, falso en caso
     * contrario 
     */
    public boolean validarFolio(String folio) {
        // Se remueven espacios en blanco
        CharSequence cadena = folio.trim();
        // Expresión regular
        String patron = "^\\d{1,6}$"; 
        // Se crea un objeto de tipo pattern para verificar patrones
        Pattern p = Pattern.compile(patron);
        // Objeto de verificación
        Matcher matcher = p.matcher(cadena);
        // Devuelve verdadero si la cadena coincide con el patrón
        return matcher.matches();
    }
    
    /**
     * Método para validar flotantes, se verifica que sea un número de longuitud
     * indeterminada con un punto decimal, se utiliza para verificar montos,
     * salarios, costos, inversiones, etc. No se aceptan número negativos.
     * 
     * @param flotante Flotante a validar.
     * @return Verdadero si el flotante tiene el formato correcto, falso en caso
     * contrario 
     */
    public boolean validarFlotante(String flotante) {
        // Se remueven espacios en blanco
        CharSequence cadena = flotante.trim();
        // Expresión regular
        String patron = "^[0-9]*\\.?[0-9]+$"; 
        // Se crea un objeto de tipo pattern para verificar patrones
        Pattern p = Pattern.compile(patron);
        // Objeto de verificación
        Matcher matcher = p.matcher(cadena);
        // Devuelve verdadero si la cadena coincide con el patrón
        return matcher.matches();
    }
}
