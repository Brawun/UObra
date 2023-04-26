/**
 * FacturasDAO.java
 */
package DAOs;

import Dominio.Facturas;
import Enumeradores.EstadoFactura;
import Enumeradores.MetodoPago;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo Facturas.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class FacturasDAO {

    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("com.itson_AgenciaTransito");
    EntityManager entityManager = managerFactory.createEntityManager();

    // Métodos de acceso
    public void registrarFactura(Facturas factura) {
        entityManager.getTransaction().begin();
        entityManager.persist(factura);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void eliminarFactura(Long id) {
        if (verificarFactura(id)) {
            entityManager.getTransaction().begin();
            Facturas factura = consultarFactura(id);
            entityManager.remove(factura);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la factura con ID: " + id);
        }
    }

    public void pagarFactura(Long id) {
        if (verificarFactura(id)) {
            entityManager.getTransaction().begin();
            Facturas factura = consultarFactura(id);
            factura.setEstado(EstadoFactura.PAGADA);
            entityManager.merge(factura);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la factura con ID: " + id);
        }
    }

    public void cambiarMetodoPagoFactura(Long id, MetodoPago metodo) {
        if (verificarFactura(id)) {
            entityManager.getTransaction().begin();
            Facturas factura = consultarFactura(id);
            factura.setMetodoPago(metodo);
            entityManager.merge(factura);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la factura con ID: " + id);
        }
    }

    // Métodos de consulta 
    public Boolean verificarFactura(Long id) {
        entityManager.getTransaction().begin();
        Facturas factura = entityManager.find(Facturas.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return factura != null;
    }

    public Facturas consultarFactura(Long id) {
        entityManager.getTransaction().begin();
        if (verificarFactura(id)) {
            Facturas factura = entityManager.find(Facturas.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return factura;
        } else {
            throw new EntityNotFoundException("No se puede encontrar la factura con ID: " + id);
        }
    }

    // Consulta una lista de facturas que estén dentro del periodo dado, 
    // con el estado dado, con el método de pago dado y el monto mayor al dado
    public List<Facturas> consultarFacturas(Calendar periodoInicio, Calendar periodoFin, EstadoFactura estado, MetodoPago metodoPago, Float monto) throws Exception {
        entityManager.getTransaction().begin();
        TypedQuery<Facturas> query;
        // BUSQUEDA POR 5 CAMPOS
        if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto != null) { // Se llenaron todos los campos
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.metodoPago = :metodoPago AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        // BUSQUEDA POR 4 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto == null) { // No se lleno el campo de monto
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.metodoPago = :metodoPago AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto != null) { // No se lleno el campo de metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto != null) { // No se lleno el campo de estado
            String jpql = "SELECT f FROM Facturas f WHERE f.metodoPago = :metodoPago AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto != null) { // No se lleno el campo de fecha inicio
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.metodoPago = :metodoPago AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoInicio", periodoInicio);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto != null) { // No se lleno el campo de fecha fin
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.metodoPago = :metodoPago AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        // BUSQUEDA POR 3 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto == null) { // No se llenó el campo de método de pago ni monto
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto == null) { // No se llenó el campo de estado ni monto  
            String jpql = "SELECT f FROM Facturas f WHERE f.metodoPago = :metodoPago AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto != null) { // No se llenó el campo de estado ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto == null) { // No se llenó el campo de fin ni monto    
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.metodoPago = :metodoPago AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto != null) { // No se llenó el campo de fin ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto != null) { // No se llenó el campo de fin ni estado  
            String jpql = "SELECT f FROM Facturas f WHERE f.metodoPago = :metodoPago AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto == null) { // No se llenó el campo de inicio ni monto
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.metodoPago = :metodoPago AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto != null) { // No se llenó el campo de inicio ni metodo 
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto != null) { // No se llenó el campo de inicio ni estado 
            String jpql = "SELECT f FROM Facturas f WHERE f.metodoPago = :metodoPago AND f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto != null) { // No se llenó ningún campo de fecha
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.metodoPago = :metodoPago AND f.monto <= :monto";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        // BUSQUEDA POR 2 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto != null) { // No se llenó ningún campo de fecha ni estado
            String jpql = "SELECT f FROM Facturas f WHERE f.metodoPago = :metodoPago AND f.monto <= :monto";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto != null) { // No se llenó ningún campo de fecha ni metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.monto <= :monto";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto == null) { // No se llenó ningún campo de fecha ni monto
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.metodoPago = :metodoPago";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto == null) { // Solo se llenaron campos de fecha
            String jpql = "SELECT f FROM Facturas f WHERE f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto == null) { // Solo se lleno inicio y estado
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto == null) { // Solo se lleno inicio y metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE f.metodoPago = :metodoPago AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto != null) { // Solo se lleno inicio y monto
            String jpql = "SELECT f FROM Facturas f WHERE f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto == null) { // Solo se lleno fin y estado
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto == null) { // Solo se lleno fin y metodo
            String jpql = "SELECT f FROM Facturas f WHERE f.metodoPago = :metodoPago AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto != null) { // Solo se lleno fin y monto
            String jpql = "SELECT f FROM Facturas f WHERE f.monto <= :monto AND f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        // BUSQUEDA POR 1 CAMPO
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto == null) { // Solo se busca por inicio
            String jpql = "SELECT f FROM Facturas f WHERE f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto == null) { // Solo se busca por fin
            String jpql = "SELECT f FROM Facturas f WHERE f.fecha BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto == null) { // Solo se busca por estado
            String jpql = "SELECT f FROM Facturas f WHERE f.estado = :estado";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto == null) { // Solo se busca por metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE f.metodoPago = :metodoPago";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto != null) { // Solo se busca por monto
            String jpql = "SELECT f FROM Facturas f WHERE f.monto <= :monto";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        // BUSQUEDA POR 0 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto == null) { // No se llenó ningún campo
            String jpql = "SELECT f FROM Facturas f";
            query = entityManager.createQuery(jpql, Facturas.class);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else {
            throw new Exception("No se pudo realizar la búsqueda dinámica de facturas");
        }
    }
}
