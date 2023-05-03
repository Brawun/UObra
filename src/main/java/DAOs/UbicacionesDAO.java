/**
 * UbicacionesDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Ubicaciones;
import Enumeradores.TipoUbicacion;
import Herramientas.Encriptador;
import Herramientas.Fecha;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
 * y agregar entidades de tipo Ubicaciones.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class UbicacionesDAO {

    /**
     * Se establece una conexión con la base de datos UObra mediante JPA,
     * creando un objeto EntityManager que puede ser utilizado para realizar
     * operaciones de creación, lectura, actualización y eliminación en la base
     * de datos utilizando el lenguaje JPQL.
     */
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
    EntityManager entityManager = managerFactory.createEntityManager();

    /**
     * Llamada a paquete de Herramientas para manipular fechas.
     */
    Fecha fecha = new Fecha();

    /**
     * Método para persistir la entidad de la clase a la base de datos, en caso
     * que no se pueda realizar dicha transacción se cancela el guardado de la
     * entidad.
     *
     * @param object Objeto a guardar en la base de datos perteneciente a la
     * clase
     */
    public void persist(Object object) {
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    // Métodos de acceso
    public void registrarUbicacion(Ubicaciones ubicacion) {
        entityManager.getTransaction().begin();
        this.persist(ubicacion);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void ocuparUbicacion(Long id) {
        if (verificarUbicacion(id)) {
            entityManager.getTransaction().begin();
            Ubicaciones ubicacion = consultarUbicacion(id);
            if (ubicacion.getDisponible()) {
                ubicacion.setDisponible(false);
                ubicacion.setFechaOcupacion(fecha.fechaAhora());
                entityManager.merge(ubicacion);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la ubicación con ID: " + id);
        }
    }

    public void desocuparUbicacion(Long id) {
        if (verificarUbicacion(id)) {
            entityManager.getTransaction().begin();
            Ubicaciones ubicacion = consultarUbicacion(id);
            if (!ubicacion.getDisponible()) {
                ubicacion.setDisponible(true);
                entityManager.merge(ubicacion);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la ubicación con ID: " + id);
        }
    }

    public void editarTipo(Long id, TipoUbicacion tipo) {
        if (verificarUbicacion(id)) {
            entityManager.getTransaction().begin();
            Ubicaciones ubicacion = consultarUbicacion(id);
            if (!ubicacion.getTipo().equals(tipo)) {
                ubicacion.setTipo(tipo);
                entityManager.merge(ubicacion);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la ubicación con ID: " + id);
        }
    }

    public void editarDireccion(Long id, String direccion) {
        if (verificarUbicacion(id)) {
            entityManager.getTransaction().begin();
            Ubicaciones ubicacion = consultarUbicacion(id);
            if (!ubicacion.getDireccion().equalsIgnoreCase(direccion)) {
                ubicacion.setDireccion(direccion);
                entityManager.merge(ubicacion);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la ubicación con ID: " + id);
        }
    }

    public void editarLargo(Long id, Float largo) {
        if (verificarUbicacion(id)) {
            entityManager.getTransaction().begin();
            Ubicaciones ubicacion = consultarUbicacion(id);
            if (!Objects.equals(ubicacion.getLargo(), largo)) {
                ubicacion.setLargo(largo);
                entityManager.merge(ubicacion);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la ubicación con ID: " + id);
        }
    }

    public void editarAncho(Long id, Float ancho) {
        if (verificarUbicacion(id)) {
            entityManager.getTransaction().begin();
            Ubicaciones ubicacion = consultarUbicacion(id);
            if (!Objects.equals(ubicacion.getAncho(), ancho)) {
                ubicacion.setAncho(ancho);
                entityManager.merge(ubicacion);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la ubicación con ID: " + id);
        }
    }

    public void eliminarUbicacion(Long id) {
        if (verificarUbicacion(id)) {
            entityManager.getTransaction().begin();
            Ubicaciones ubicacion = consultarUbicacion(id);
            entityManager.remove(ubicacion);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la ubicación con ID: " + id);
        }
    }

    // Métodos de consulta 
    public Boolean verificarUbicacion(Long id) {
        entityManager.getTransaction().begin();
        Ubicaciones ubicacion = entityManager.find(Ubicaciones.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return ubicacion != null;
    }

    public Ubicaciones consultarUbicacion(Long id) {
        if (verificarUbicacion(id)) {
            entityManager.getTransaction().begin();
            Ubicaciones ubicacion = entityManager.find(Ubicaciones.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return ubicacion;
        } else {
            throw new EntityNotFoundException("No se puede encontrar la ubicación con ID: " + id);
        }
    }

    // Regresa una lista de ubicaciones que hayan sido registradas dentro del 
    // periodo dado, estén o no disponibles, sean del tipo dado, tengan un area
    // mayor o igual a la dada, tengan un cierto largo dado, un ancho cierto 
    // dado, una direccion parecida cierta dada y hayan sido registradas por el 
    // cliente dado
    public List<Ubicaciones> consultarUbicacionesRegistro(Calendar periodoInicio, Calendar periodoFin, Boolean disponible, TipoUbicacion tipo, Float ancho, Float largo, Float area, String direccion, Clientes cliente) {
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ubicaciones> criteria = criteriaBuilder.createQuery(Ubicaciones.class);
        Root<Ubicaciones> root = criteria.from(Ubicaciones.class);
        List<Predicate> predicates = new LinkedList();

        // Filtrado por periodo
        if (periodoInicio != null && periodoFin != null) { // Filtramos por ambos periodos si ambos están presentes
            predicates.add(criteriaBuilder.between(root.get("fechaRegistro"), periodoInicio.getTime(), periodoFin.getTime()));
        } else if (periodoInicio != null) { // Filtramos por periodoInicio si está presente
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaRegistro"), periodoInicio.getTime()));
        } else if (periodoFin != null) { // Filtramos por periodoFin si está presente
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaRegistro"), periodoFin.getTime()));
        }

        // Filtramos por escala de ubicacion si existe
        if (tipo != null) {
            predicates.add(criteriaBuilder.equal(root.get("disponible"), disponible));
        }

        // Filtramos por tipo de ubicacion si existe
        if (tipo != null) {
            predicates.add(criteriaBuilder.equal(root.get("tipo"), tipo));
        }

        // Filtramos por tipo de ubicacion si existe
        if (ancho != null) {
            predicates.add(criteriaBuilder.equal(root.get("ancho"), ancho));
        }

        // Filtramos por tipo de ubicacion si existe
        if (largo != null) {
            predicates.add(criteriaBuilder.equal(root.get("largo"), largo));
        }

        // Filtramos por area de ubicacion si existe
        if (largo != null) {
            predicates.add(criteriaBuilder.equal(root.get("area"), area));
        }

        // Filtramos por direccion de ubicacion si existe
        if (direccion != null) {
            predicates.add(criteriaBuilder.like(root.get("direccion"), "%" + direccion + "%"));
        }

        // Filtramos por jefe si existe
        if (cliente != null) {
            predicates.add(criteriaBuilder.equal(root.get("cliente"), cliente));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        entityManager.getTransaction().commit();
        entityManager.close();
        return entityManager.createQuery(criteria).getResultList();
    }

    // Regresa una lista de ubicaciones que hayan sido ocupadas dentro del 
    // periodo dado, estén o no disponibles, sean del tipo dado, tengan un area
    // mayor o igual a la dada, tengan un cierto largo dado, un ancho cierto 
    // dado, una direccion parecida cierta dada y hayan sido registradas por el 
    // cliente dado
    public List<Ubicaciones> consultarUbicacionesOcupacion(Calendar periodoInicio, Calendar periodoFin, Boolean disponible, TipoUbicacion tipo, Float ancho, Float largo, Float area, String direccion, Clientes cliente) throws Exception {
        entityManager.getTransaction().begin();
        Encriptador crypt = new Encriptador();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Ubicaciones> criteria = criteriaBuilder.createQuery(Ubicaciones.class);
        Root<Ubicaciones> root = criteria.from(Ubicaciones.class);
        List<Predicate> predicates = new LinkedList();

        // Filtrado por periodo
        if (periodoInicio != null && periodoFin != null) { // Filtramos por ambos periodos si ambos están presentes
            predicates.add(criteriaBuilder.between(root.get("fechaOcupacion"), periodoInicio.getTime(), periodoFin.getTime()));
        } else if (periodoInicio != null) { // Filtramos por periodoInicio si está presente
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaOcupacion"), periodoInicio.getTime()));
        } else if (periodoFin != null) { // Filtramos por periodoFin si está presente
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaOcupacion"), periodoFin.getTime()));
        }

        // Filtramos por escala de ubicacion si existe
        if (tipo != null) {
            predicates.add(criteriaBuilder.equal(root.get("disponible"), disponible));
        }

        // Filtramos por tipo de ubicacion si existe
        if (tipo != null) {
            predicates.add(criteriaBuilder.equal(root.get("tipo"), tipo));
        }

        // Filtramos por tipo de ubicacion si existe
        if (ancho != null) {
            predicates.add(criteriaBuilder.equal(root.get("ancho"), ancho));
        }

        // Filtramos por tipo de ubicacion si existe
        if (largo != null) {
            predicates.add(criteriaBuilder.equal(root.get("largo"), largo));
        }

        // Filtramos por area de ubicacion si existe
        if (largo != null) {
            predicates.add(criteriaBuilder.equal(root.get("area"), area));
        }

        // Filtramos por direccion de ubicacion si existe
        if (direccion != null) {
            predicates.add(criteriaBuilder.like(root.get("direccion"), "%" + direccion + "%"));
        }

        // Filtramos por jefe si existe
        if (cliente != null) {
            predicates.add(criteriaBuilder.equal(root.get("cliente"), cliente));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        entityManager.getTransaction().commit();
        entityManager.close();
        return entityManager.createQuery(criteria).getResultList();
    }

    // Métodos drivers para búsqueda dinámica
    public List<Ubicaciones> consultarUbicacionesCliente(Long id) throws Exception {
        ClientesDAO clientesDAO = new ClientesDAO();
        return consultarUbicacionesRegistro(null, null, null, null, null, null, null, null, clientesDAO.consultarCliente(id));
    }

    public List<Ubicaciones> consultarTodasUbicaciones() throws Exception {
        return consultarUbicacionesRegistro(null, null, null, null, null, null, null, null, null);
    }
}
