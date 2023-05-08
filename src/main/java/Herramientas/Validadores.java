/**
 * Validadores.java
 */
package Herramientas;

// Importaciones
import java.util.Calendar;
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
    public boolean validarTelefono(String telefono) {
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
        String patron = "^[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?$";
        // Se crea un objeto de tipo pattern para verificar patrones
        Pattern p = Pattern.compile(patron);
        // Objeto de verificación
        Matcher matcher = p.matcher(cadena);
        // Devuelve verdadero si la cadena coincide con el patrón
        return matcher.matches();
    }

    /**
     * Método para validar numeros, se verifica que sea un número de longuitud
     * indeterminada.
     *
     * @param numero Numero a validar.
     * @return Verdadero si el numero tiene el formato correcto, falso en caso
     * contrario
     */
    public boolean validarNumero(String numero) {
        // Se remueven espacios en blanco
        CharSequence cadena = numero.trim();
        // Expresión regular
        String patron = "^\\d*\\.?\\d+$";
        // Se crea un objeto de tipo pattern para verificar patrones
        Pattern p = Pattern.compile(patron);
        // Objeto de verificación
        Matcher matcher = p.matcher(cadena);
        // Devuelve verdadero si la cadena coincide con el patrón
        return matcher.matches();
    }

    /**
     * Método para validar enteros, se verifica que sea un número de longuitud
     * indeterminada sin un punto decimal, se utiliza para verificar dias, entre
     * otros campos.
     *
     * @param entero Entero a validar.
     * @return Verdadero si el entero tiene el formato correcto, falso en caso
     * contrario
     */
    public boolean validarEntero(String entero) {
        // Se remueven espacios en blanco
        CharSequence cadena = entero.trim();
        // Expresión regular
        String patron = "^\\d+$";
        // Se crea un objeto de tipo pattern para verificar patrones
        Pattern p = Pattern.compile(patron);
        // Objeto de verificación
        Matcher matcher = p.matcher(cadena);
        // Devuelve verdadero si la cadena coincide con el patrón
        return matcher.matches();
    }

    /**
     * Método para recortar el signo de + de una cadena.
     *
     * @param cadena Cadena a recortar signo.
     * @return Cadena sin el signo de más.
     */
    public String recortarSignoMas(String cadena) {
        if (cadena.startsWith("+")) {
            return cadena.substring(1);
        } else {
            return cadena;
        }
    }

    /**
     * Busca todas las ocurrencias de la cadena dada (en este caso, una coma) y
     * las reemplaza con la cadena de reemplazo especificada (en este caso, una
     * cadena vacía). Este método devuelve una nueva cadena que es la entrada
     * original sin todas las comas.
     *
     * @param entrada Cadena entrante a la que se le retirarán todas las comas.
     * @return Cadena sin comas.
     */
    public String recortarComas(String entrada) {
        return entrada.replace(",", "");
    }

    /**
     * Método para validar que una cadena no tenga espacios.
     *
     * @param string Cadena a validar.
     * @return Verdadero si la cadena tiene el formato correcto, falso en caso
     * contrario
     */
    public boolean validarSinEspacios(String string) {
        // Se remueven espacios en blanco
        CharSequence cadena = string.trim();
        // Expresión regular
        String patron = "\\S+";
        // Se crea un objeto de tipo pattern para verificar patrones
        Pattern p = Pattern.compile(patron);
        // Objeto de verificación
        Matcher matcher = p.matcher(cadena);
        // Devuelve verdadero si la cadena coincide con el patrón
        return matcher.matches();
    }

    /**
     * El método "validarFechas" en Java toma dos parámetros de tipo Calendar:
     * "fechaInicial" y "fechaFinal". Utiliza el método "before" de la clase
     * Calendar para verificar si "fechaInicial" es anterior a "fechaFinal".
     *
     * @param fechaInicial Fecha inicial que tiene que ser antes
     * @param fechaFinal Fecha final que tiene que ser después
     * @return Si "fechaInicial" es anterior a "fechaFinal", el método devuelve
     * true; de lo contrario, devuelve false.
     */
    public boolean validarFechas(Calendar fechaInicial, Calendar fechaFinal) {
        return fechaInicial.before(fechaFinal);
    }
}
