/**
 * FacturasDAO.java
 */
package DAOs;

import Dominio.Facturas;
import Enumeradores.EstadoFactura;
import Enumeradores.MetodoPago;
import Herramientas.Fecha;
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

    // Métodos de acceso
    public Facturas registrarFactura(Facturas factura) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(factura);
        entityManager.getTransaction().commit();
        entityManager.close();
        return factura;
    }

    public void eliminarFactura(Long id) {
        if (verificarFactura(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Facturas factura = consultarFactura(id);
            entityManager.remove(factura);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la factura con ID: " + id);
        }
    }

    public void pagarFactura(Long id, MetodoPago metodo) {
        if (verificarFactura(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Facturas factura = consultarFactura(id);
            factura.setFechaPagada(new Fecha().fechaAhora());
            factura.setMetodoPago(metodo);
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Facturas factura = entityManager.find(Facturas.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return factura != null;
    }

    public Facturas consultarFactura(Long id) {
        if (verificarFactura(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Facturas factura = entityManager.find(Facturas.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return factura;
        } else {
            throw new EntityNotFoundException("No se puede encontrar la factura con ID: " + id);
        }
    }

    // Consulta una lista de facturas que hayan sido creadas dentro del periodo  
    // dado, con el estado dado, con el método de pago dado, el monto mayor o 
    // igual al dado y un jefe identificado por un ID dado
    public List<Facturas> consultarFacturasFechaCreada(Calendar periodoInicio, Calendar periodoFin, EstadoFactura estado, MetodoPago metodoPago, Float monto, Long jefeId) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Facturas> query;
        // SIN BÚSQUEDA POR JEFE
        // BUSQUEDA POR 5 CAMPOS
        if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId == null) { // Se llenaron todos los campos
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se lleno el campo de monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se lleno el campo de metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se lleno el campo de estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se lleno el campo de fecha inicio
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se lleno el campo de fecha fin
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se llenó el campo de método de pago ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se llenó el campo de estado ni monto  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de estado ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se llenó el campo de fin ni monto    
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de fin ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de fin ni estado  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se llenó el campo de inicio ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de inicio ni metodo 
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de inicio ni estado 
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó ningún campo de fecha
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto";
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
                && monto != null
                && jefeId == null) { // No se llenó ningún campo de fecha ni estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto";
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
                && monto != null
                && jefeId == null) { // No se llenó ningún campo de fecha ni metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto";
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
                && monto == null
                && jefeId == null) { // No se llenó ningún campo de fecha ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago";
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
                && monto == null
                && jefeId == null) { // Solo se llenaron campos de fecha
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se lleno inicio y estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se lleno inicio y metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // Solo se lleno inicio y monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se lleno fin y estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se lleno fin y metodo
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // Solo se lleno fin y monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se busca por inicio
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se busca por fin
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se busca por estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado";
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
                && monto == null
                && jefeId == null) { // Solo se busca por metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago";
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
                && monto != null
                && jefeId == null) { // Solo se busca por monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto";
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
                && monto == null
                && jefeId == null) { // No se llenó ningún campo
            String jpql = "SELECT f FROM Facturas f";
            query = entityManager.createQuery(jpql, Facturas.class);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // CON BÚSQUEDA POR JEFE
            // BUSQUEDA POR 5 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // Se llenaron todos los campos
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 4 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se lleno el campo de monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se lleno el campo de metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se lleno el campo de estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se lleno el campo de fecha inicio
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se lleno el campo de fecha fin
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 3 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // No se llenó el campo de método de pago ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se llenó el campo de estado ni monto  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se llenó el campo de estado ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se llenó el campo de fin ni monto    
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se llenó el campo de fin ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se llenó el campo de fin ni estado  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se llenó el campo de inicio ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se llenó el campo de inicio ni metodo 
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se llenó el campo de inicio ni estado 
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se llenó ningún campo de fecha
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 2 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se llenó ningún campo de fecha ni estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se llenó ningún campo de fecha ni metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se llenó ningún campo de fecha ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se llenaron campos de fecha
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se lleno inicio y estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // Solo se lleno inicio y metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // Solo se lleno inicio y monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se lleno fin y estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // Solo se lleno fin y metodo
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // Solo se lleno fin y monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 1 CAMPO
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se busca por inicio
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se busca por fin
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se busca por estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // Solo se busca por metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // Solo se busca por monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 0 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // No se llenó ningún campo
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else {
            throw new Exception("No se pudo realizar la búsqueda dinámica de facturas");
        }
    }

    // Consulta una lista de facturas que hayan sido pagadas dentro del periodo  
    // dado, con el estado dado, con el método de pago dado, el monto mayor o 
    // igual al dado y un jefe identificado por un ID dado
    public List<Facturas> consultarFacturasFechaPagada(Calendar periodoInicio, Calendar periodoFin, EstadoFactura estado, MetodoPago metodoPago, Float monto, Long jefeId) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Facturas> query;
        // SIN BÚSQUEDA POR JEFE
        // BUSQUEDA POR 5 CAMPOS
        if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId == null) { // Se llenaron todos los campos
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se lleno el campo de monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se lleno el campo de metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se lleno el campo de estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se lleno el campo de fecha inicio
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se lleno el campo de fecha fin
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se llenó el campo de método de pago ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se llenó el campo de estado ni monto  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de estado ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se llenó el campo de fin ni monto    
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de fin ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de fin ni estado  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // No se llenó el campo de inicio ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de inicio ni metodo 
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó el campo de inicio ni estado 
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // No se llenó ningún campo de fecha
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto";
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
                && monto != null
                && jefeId == null) { // No se llenó ningún campo de fecha ni estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto";
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
                && monto != null
                && jefeId == null) { // No se llenó ningún campo de fecha ni metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto";
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
                && monto == null
                && jefeId == null) { // No se llenó ningún campo de fecha ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago";
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
                && monto == null
                && jefeId == null) { // Solo se llenaron campos de fecha
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se lleno inicio y estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se lleno inicio y metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // Solo se lleno inicio y monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se lleno fin y estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se lleno fin y metodo
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto != null
                && jefeId == null) { // Solo se lleno fin y monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se busca por inicio
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se busca por fin
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
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
                && monto == null
                && jefeId == null) { // Solo se busca por estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado";
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
                && monto == null
                && jefeId == null) { // Solo se busca por metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago";
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
                && monto != null
                && jefeId == null) { // Solo se busca por monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto";
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
                && monto == null
                && jefeId == null) { // No se llenó ningún campo
            String jpql = "SELECT f FROM Facturas f";
            query = entityManager.createQuery(jpql, Facturas.class);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // CON BÚSQUEDA POR JEFE
            // BUSQUEDA POR 5 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // Se llenaron todos los campos
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 4 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se lleno el campo de monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se lleno el campo de metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se lleno el campo de estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se lleno el campo de fecha inicio
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se lleno el campo de fecha fin
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 3 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // No se llenó el campo de método de pago ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se llenó el campo de estado ni monto  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se llenó el campo de estado ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se llenó el campo de fin ni monto    
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se llenó el campo de fin ni metodo de pago  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se llenó el campo de fin ni estado  
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se llenó el campo de inicio ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se llenó el campo de inicio ni metodo 
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se llenó el campo de inicio ni estado 
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se llenó ningún campo de fecha
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 2 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto != null
                && jefeId != null) { // No se llenó ningún campo de fecha ni estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.monto >= :monto "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("monto", monto);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // No se llenó ningún campo de fecha ni metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.monto >= :monto "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("monto", monto);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // No se llenó ningún campo de fecha ni monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.metodoPago = :metodoPago "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se llenaron campos de fecha
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se lleno inicio y estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // Solo se lleno inicio y metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // Solo se lleno inicio y monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se lleno fin y estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // Solo se lleno fin y metodo
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // Solo se lleno fin y monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 1 CAMPO
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se busca por inicio
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se busca por fin
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.fechaPagada BETWEEN :periodoInicio "
                    + "AND :periodoFin "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // Solo se busca por estado
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.estado = :estado "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("estado", estado);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago != null
                && monto == null
                && jefeId != null) { // Solo se busca por metodo de pago
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.metodoPago = :metodoPago "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("metodoPago", metodoPago);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto != null
                && jefeId != null) { // Solo se busca por monto
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.monto >= :monto "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("monto", monto);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
            // BUSQUEDA POR 0 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && metodoPago == null
                && monto == null
                && jefeId != null) { // No se llenó ningún campo
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else {
            throw new Exception("No se pudo realizar la búsqueda dinámica de facturas");
        }
    }

    // Consulta facturas por medio de su descripción sin tomar en cuenta ningún
    // otro parámetro
    public List<Facturas> consultarFacturasPorDescripcion(String descripcion) throws Exception {
        if (descripcion != null) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<Facturas> query;
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.descripcion LIKE CONCAT('%',:descripcion,'%')";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("descripcion", descripcion);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else {
            throw new Exception("No se pudo realizar la búsqueda por descripción de facturas");
        }
    }

    // Consulta facturas por medio de su descripción y jefe que la emitió
    public List<Facturas> consultarFacturasPorDescripcionJefe(String descripcion, Long jefeId) throws Exception {
        if (descripcion != null
                && jefeId != null) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<Facturas> query;
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.descripcion LIKE CONCAT('%',:descripcion,'%') "
                    + "AND f.jefe.id = :jefeId";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("descripcion", descripcion);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else {
            throw new Exception("No se pudo realizar la búsqueda por descripción y jefe de facturas");
        }
    }

    // Consulta facturas por medio de su descripción, jefe que la emitió y periodo
    public List<Facturas> consultarFacturasPorDescripcionJefePeriodo(Calendar periodoInicio, Calendar periodoFin, String descripcion, Long jefeId) throws Exception {
        if (descripcion != null
                && jefeId != null
                && periodoInicio != null
                && periodoFin != null) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<Facturas> query;
            String jpql = "SELECT f FROM Facturas f WHERE "
                    + "f.descripcion LIKE CONCAT('%',:descripcion,'%') "
                    + "AND f.jefe.id = :jefeId"
                    + "AND f.fechaCreada BETWEEN :periodoInicio "
                    + "AND :periodoFin";
            query = entityManager.createQuery(jpql, Facturas.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("descripcion", descripcion);
            query.setParameter("jefeId", jefeId);
            List<Facturas> facturas = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return facturas;
        } else {
            throw new Exception("No se pudo realizar la búsqueda por descripción, jefe y periodo de facturas");
        }
    }

    // Métodos drivers para búsqueda dinámica
    public List<Facturas> consultarFacturasConMetodoPago(MetodoPago metodoPago) throws Exception {
        return consultarFacturasFechaCreada(null, null, null, metodoPago, null, null);
    }

    public List<Facturas> consultarFacturasConEstado(EstadoFactura estado) throws Exception {
        return consultarFacturasFechaCreada(null, null, estado, null, null, null);
    }

    public List<Facturas> consultarFacturasPorJefe(Long jefeId) throws Exception {
        return consultarFacturasFechaCreada(null, null, null, null, null, jefeId);
    }

    public List<Facturas> consultarFacturasConMontoMínimo(Float monto) throws Exception {
        return consultarFacturasFechaCreada(null, null, null, null, monto, null);
    }

    public List<Facturas> consultarFacturasEnPeriodoCreadas(Calendar periodoInicio, Calendar periodoFin) throws Exception {
        return consultarFacturasFechaCreada(periodoInicio, periodoFin, null, null, null, null);
    }

    public List<Facturas> consultarFacturasEnPeriodoPagadas(Calendar periodoInicio, Calendar periodoFin) throws Exception {
        return consultarFacturasFechaPagada(periodoInicio, periodoFin, null, null, null, null);
    }

    public List<Facturas> consultarTodasFacturas() throws Exception {
        return consultarFacturasFechaCreada(null, null, null, null, null, null);
    }

    public List<Facturas> consultarFacturasDescripcionInicia(String descripción, Long jefeId, Calendar periodoInicio) throws Exception {
        return consultarFacturasPorDescripcionJefePeriodo(periodoInicio, new GregorianCalendar(3000, Calendar.JANUARY, 1), descripción, jefeId);
    }

    public List<Facturas> consultarFacturasDescripcionFin(String descripción, Long jefeId, Calendar periodoFin) throws Exception {
        return consultarFacturasPorDescripcionJefePeriodo(new GregorianCalendar(1600, Calendar.JANUARY, 1), periodoFin, descripción, jefeId);
    }
}
