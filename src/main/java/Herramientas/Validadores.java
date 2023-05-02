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
     * Telefono
     * Nombres - Apellidos 
     * Folio (6 dígitos)
     */
    
    /**
     * Método para validar las RFC.
     *
     * @param rfc a validar.
     * @return Verdadero en caso que la cadena haya sido validada correctamente
     * falso en caso contrario
     */
    public boolean validarRFC(String rfc) {
        CharSequence cadena = rfc.trim();
        String reCadena = "[A-Za-z0-9]{13}";
        Pattern p = Pattern.compile(reCadena);

        Matcher matcher = p.matcher(cadena);
        return matcher.matches();
    }

    /**
     * Método para validar los Número de Serie.
     *
     * @param numSerie a validar.
     * @return Verdadero en caso que la cadena haya sido validada correctamente
     * falso en caso contrario
     */
    public boolean validarNumSerie(String numSerie) {
        CharSequence cadena = numSerie.trim();
        String reCadena = "^[A-Za-z]{3}-\\d{3}$";
        Pattern p = Pattern.compile(reCadena);

        Matcher matcher = p.matcher(cadena);
        return matcher.matches();
    }

    /**
     * Método para validar cadena con números.
     *
     * @param string a validar.
     * @return Verdadero en caso que la cadena haya sido validada correctamente
     * falso en caso contrario
     */
    public boolean validarCadenaConNumeros(String string) {
        CharSequence cadena = string.trim();
        String reCadena = "^[a-zA-Z0-9]{3,15}$";
        Pattern p = Pattern.compile(reCadena);

        Matcher matcher = p.matcher(cadena);
        return matcher.matches();
    }

    /**
     * Método para validar modelos.
     *
     * @param string a validar.
     * @return Verdadero en caso que la cadena haya sido validada correctamente
     * falso en caso contrario
     */
    public boolean validarModelo(String string) {
        CharSequence cadena = string.trim();
        String reCadena = "^[1-2]\\d{3}$";
        Pattern p = Pattern.compile(reCadena);

        Matcher matcher = p.matcher(cadena);
        return matcher.matches();
    }

    /**
     * Método para validar cadenas.
     *
     * @param string a validar.
     * @return Verdadero en caso que la cadena haya sido validada correctamente
     * falso en caso contrario
     */
    public boolean validarCadena(String string) {
        CharSequence cadena = string.trim();
        String reCadena = "^[a-zA-Z]{3,15}$";
        Pattern p = Pattern.compile(reCadena);

        Matcher matcher = p.matcher(cadena);
        return matcher.matches();
    }
}
