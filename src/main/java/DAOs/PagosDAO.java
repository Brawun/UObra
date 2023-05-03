/**
 * PagosDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Obras;
import Dominio.Obreros;
import Dominio.Pagos;
import Enumeradores.MetodoPago;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo Pagos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class PagosDAO {

    // Métodos de acceso
    public void registrarPago(Pagos pago) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(pago);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void eliminarPago(Long id) {
        if (verificarPago(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Pagos pago = consultarPago(id);
            entityManager.remove(pago);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el pago con ID: " + id);
        }
    }

    // Métodos de consulta 
    public Boolean verificarPago(Long id) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Pagos pago = entityManager.find(Pagos.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return pago != null;
    }

    public Pagos consultarPago(Long id) {
        if (verificarPago(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Pagos pago = entityManager.find(Pagos.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return pago;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el pago con ID: " + id);
        }
    }

    // Regresa una lista de pagos que se hayan hecho dentro del periodo definido
    // se hayan pagado con cierto método de pago, tenga un monto mayor o igual
    // al dado, hayan sido recibidas por cierto obrero, si aplica, hayan sido 
    // hechos por dado cliente y que pertenece a la obra dada
    public List<Pagos> consultarPagos(Calendar periodoInicio, Calendar periodoFin, MetodoPago metodoPago, Float monto, Obreros obrero, Obras obra, Clientes cliente) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pagos> criteria = criteriaBuilder.createQuery(Pagos.class);
        Root<Pagos> root = criteria.from(Pagos.class);
        List<Predicate> predicates = new LinkedList();

        // Filtrado por periodo
        if (periodoInicio != null && periodoFin != null) { // Filtramos por ambos periodos si ambos están presentes
            predicates.add(criteriaBuilder.between(root.get("fecha"), periodoInicio.getTime(), periodoFin.getTime()));
        } else if (periodoInicio != null) { // Filtramos por periodoInicio si está presente
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fecha"), periodoInicio.getTime()));
        } else if (periodoFin != null) { // Filtramos por periodoFin si está presente
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fecha"), periodoFin.getTime()));
        }

        // Filtramos por metodoPago si está presente
        if (metodoPago != null) {
            predicates.add(criteriaBuilder.equal(root.get("metodoPago"), metodoPago));
        }

        // Filtramos por monto si está presente
        if (monto != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("monto"), monto));
        }

        // Filtramos por obrero si está presente
        if (obrero != null) {
            predicates.add(criteriaBuilder.equal(root.get("obrero"), obrero));
        }

        // Filtramos por obra si está presente
        if (obra != null) {
            predicates.add(criteriaBuilder.equal(root.get("obra"), obra));
        }

        // Filtramos por cliente si está presente
        if (cliente != null) {
            predicates.add(criteriaBuilder.equal(root.get("cliente"), cliente));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        entityManager.getTransaction().commit();
        entityManager.close();
        return entityManager.createQuery(criteria).getResultList();
    }

    // Métodos drivers para búsqueda dinámica
    public List<Pagos> consultarPagosCliente(Long id) throws Exception {
        ClientesDAO clientesDAO = new ClientesDAO();
        return consultarPagos(null, null, null, null, null, null, clientesDAO.consultarCliente(id));
    }

    public List<Pagos> consultarTodosPagos() throws Exception {
        return consultarPagos(null, null, null, null, null, null, null);
    }
}
