/**
 * Fecha.java
 */
package Herramientas;

// Importaciones
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Esta clase permite encapsular herramientas útiles a la hora de querer
 * implementar fechas.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class Fecha {

    /**
     * Método para obtener la fecha del preciso momento.
     *
     * @return ahora, fecha del preciso momento.
     */
    public Calendar fechaAhora() {
        Calendar ahora = Calendar.getInstance();
        ahora.setTime(new Date());
        return ahora;
    }

    /**
     * Método que toma un objeto Calendar y devuelve una cadena de texto que
     * representa la fecha en un formato específico "yyyy/MM/dd", que representa
     * el año, el mes y el día en el orden especificado.
     *
     * @param fecha Ojeto tipo Calendar que regresará como string.
     * @return Fecha representada en format yyyy/MM/dd en un string.
     * @throws ParseException en caso que haya una excepción en los tipos de
     * datos.
     */
    public String formatoFecha(Calendar fecha) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return formatter.format(fecha.getTime());
    }

    /**
     * Método que recibe dos objetos de tipo Calendar, fechaInicial y
     * fechaFinal, que representan las fechas a comparar. La función de este
     * método es calcular la diferencia en días entre las dos fechas y
     * retornarla como un entero.
     *
     * @param fechaInicial Fecha inicial
     * @param fechaFinal Fecha final
     * @return diferenciaDias entero con la diferencia de días entre fecha
     * inicial y final
     */
    public Integer calcularDiferenciaDias(Calendar fechaInicial, Calendar fechaFinal) {
        // Convertir las fechas a milisegundos
        long milisegundosFechaInicial = fechaInicial.getTimeInMillis();
        long milisegundosFechaFinal = fechaFinal.getTimeInMillis();

        // Calcular la diferencia en milisegundos
        long diferenciaMilisegundos = milisegundosFechaFinal - milisegundosFechaInicial;

        // Convertir la diferencia de milisegundos a días
        int diferenciaDias = (int) (diferenciaMilisegundos / (24 * 60 * 60 * 1000));

        // Retornar la diferencia en días
        return diferenciaDias;
    }
    
    public Calendar crearFecha(Integer dia, Integer mes, Integer anho) {
        // Se desplaza el mes -1
        mes = mes - 1;
        return new GregorianCalendar(anho, mes, dia);
    }
}
