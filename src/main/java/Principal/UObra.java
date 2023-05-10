/**
 * UObra.java
 */
package Principal;

import DAOs.ClientesDAO;
import DAOs.JefesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrerosDAO;
import DAOs.PagosDAO;
import Dominio.Obras;
import Dominio.Pagos;
import Enumeradores.MetodoPago;
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
//        PagosDAO PagosDAO = new PagosDAO();
//        ClientesDAO ClientesDAO = new ClientesDAO();
//        JefesDAO JefesDAO = new JefesDAO();
//        ObrasDAO ObrasDAO = new ObrasDAO();
//        ObrerosDAO ObrerosDAO = new ObrerosDAO();

//        Obras obra1 = new Obras((float) 10000.0, (float) 30000.0, "Hotel Centro", ClientesDAO.consultarCliente(1L));
//        ObrasDAO.registrarObra(obra1);
//        Pagos pago = new Pagos(
//                (float) 100,
//                MetodoPago.EFECTIVO,
//                ObrerosDAO.consultarObrero(1L),
//                ObrasDAO.consultarObra(1L),
//                ClientesDAO.consultarCliente(1L));
//        PagosDAO.registrarPago(pago);
//        Pagos pago = new Pagos(
//                (float) 100,
//                MetodoPago.CREDITO,
//                ObrasDAO.consultarObra(1L),
//                ClientesDAO.consultarCliente(1L));
//        PagosDAO.registrarPago(pago);

//        ObrasDAO.agregarPagoObra(1L, PagosDAO.consultarPago(2L));
        
//        System.out.println(ObrasDAO.consultarObra(1L).toString());

        // Nombre BD: UObra
        // Contraseña: 1234
    }
}
