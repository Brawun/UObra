/**
 * PermisosDAO.java
 */
package DAOs;

import Dominio.Jefes;
import Dominio.Permisos;
import Enumeradores.TipoPermiso;
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
 * y agregar entidades de tipo Permisos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class PermisosDAO {

    /**
     * Se establece una conexión con la base de datos UObra mediante JPA,
     * creando un objeto EntityManager que puede ser utilizado para realizar
     * operaciones de creación, lectura, actualización y eliminación en la base
     * de datos utilizando el lenguaje JPQL.
     */
    EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
    EntityManager entityManager = managerFactory.createEntityManager();

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
    public void registrarPermiso(Permisos permiso) {
        entityManager.getTransaction().begin();
        this.persist(permiso);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    public void editarTipoPermiso(Long id, TipoPermiso tipo) {
        if (verificarPermiso(id)) {
            entityManager.getTransaction().begin();
            Permisos permiso = consultarPermiso(id);
            if (!permiso.getTipo().equals(tipo)) {
                permiso.setTipo(tipo);
                entityManager.merge(permiso);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el permiso con ID: " + id);
        }
    }
    
    public void editarTipoPermisoFolio(String folio, TipoPermiso tipo) {
        if (verificarPermisoFolio(folio)) {
            entityManager.getTransaction().begin();
            Permisos permiso = consultarPermisoFolio(folio);
            if (!permiso.getTipo().equals(tipo)) {
                permiso.setTipo(tipo);
                entityManager.merge(permiso);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el permiso con folio: " + folio);
        }
    }
    
    public void editarFechaConcesionPermiso(Long id, Calendar fechaConcesion) { 
        if (verificarPermiso(id)) {
            entityManager.getTransaction().begin();
            Permisos permiso = consultarPermiso(id);
            if (!permiso.getFechaConcesion().equals(fechaConcesion)) {
                permiso.setFechaConcesion(fechaConcesion);
                entityManager.merge(permiso);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el permiso con ID: " + id);
        }
    }
    
    public void editarFechaConcesionPermisoFolio(String folio, Calendar fechaConcesion) {
        if (verificarPermisoFolio(folio)) {
            entityManager.getTransaction().begin();
            Permisos permiso = consultarPermisoFolio(folio);
            if (!permiso.getFechaConcesion().equals(fechaConcesion)) {
                permiso.setFechaConcesion(fechaConcesion);
                entityManager.merge(permiso);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el permiso con folio: " + folio);
        }
    }

    public void eliminarPermiso(Long id) {
        if (verificarPermiso(id)) {
            entityManager.getTransaction().begin();
            Permisos permiso = consultarPermiso(id);
            entityManager.remove(permiso);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el permiso con ID: " + id);
        }
    }

    public void eliminarPermisoFolio(String folio) {
        if (verificarPermisoFolio(folio)) {
            entityManager.getTransaction().begin();
            Permisos permiso = consultarPermisoFolio(folio);
            entityManager.remove(permiso);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el permiso con folio: " + folio);
        }
    }

    // Métodos de consulta 
    public Boolean verificarPermiso(Long id) {
        entityManager.getTransaction().begin();
        Permisos permiso = entityManager.find(Permisos.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return permiso != null;
    }

    public Boolean verificarPermisoFolio(String folio) {
        entityManager.getTransaction().begin();
        Permisos permiso = entityManager.find(Permisos.class, folio);
        entityManager.getTransaction().commit();
        entityManager.close();
        return permiso != null;
    }

    public Permisos consultarPermiso(Long id) {
        if (verificarPermiso(id)) {
            entityManager.getTransaction().begin();
            Permisos permiso = entityManager.find(Permisos.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return permiso;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el permiso con ID: " + id);
        }
    }

    public Permisos consultarPermisoFolio(String folio) {
        if (verificarPermisoFolio(folio)) {
            entityManager.getTransaction().begin();
            Permisos permiso = entityManager.find(Permisos.class, folio);
            entityManager.getTransaction().commit();
            entityManager.close();
            return permiso;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el permiso con folio: " + folio);
        }
    }

    // Regresa una lista de permisos que hayan sido registrados dentro del periodo
    // dado, correspondan al tipo dado y hayan sido registrados por el jefe si es
    // que es dado
    public List<Permisos> consultarPermisosRegistro(Calendar periodoInicio, Calendar periodoFin, TipoPermiso tipo, Jefes jefe) {
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Permisos> criteria = criteriaBuilder.createQuery(Permisos.class);
        Root<Permisos> root = criteria.from(Permisos.class);
        List<Predicate> predicates = new LinkedList();

        // Filtrado por periodo
        if (periodoInicio != null && periodoFin != null) { // Filtramos por ambos periodos si ambos están presentes
            predicates.add(criteriaBuilder.between(root.get("fechaRegistro"), periodoInicio.getTime(), periodoFin.getTime()));
        } else if (periodoInicio != null) { // Filtramos por periodoInicio si está presente
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaRegistro"), periodoInicio.getTime()));
        } else if (periodoFin != null) { // Filtramos por periodoFin si está presente
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaRegistro"), periodoFin.getTime()));
        }

        // Filtramos por tipo de permiso si existe
        if (tipo != null) {
            predicates.add(criteriaBuilder.equal(root.get("tipo"), tipo));
        }

        // Filtramos por jefe si existe
        if (jefe != null) {
            predicates.add(criteriaBuilder.equal(root.get("jefe"), jefe));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        entityManager.getTransaction().commit();
        entityManager.close();
        return entityManager.createQuery(criteria).getResultList();
    }
    
    // Regresa una lista de permisos que hayan sido concedidos dentro del periodo
    // dado, correspondan al tipo dado y hayan sido registrados por el jefe si es
    // que es dado
    public List<Permisos> consultarPermisosConcesion(Calendar periodoInicio, Calendar periodoFin, TipoPermiso tipo, Jefes jefe) {
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Permisos> criteria = criteriaBuilder.createQuery(Permisos.class);
        Root<Permisos> root = criteria.from(Permisos.class);
        List<Predicate> predicates = new LinkedList();

        // Filtrado por periodo
        if (periodoInicio != null && periodoFin != null) { // Filtramos por ambos periodos si ambos están presentes
            predicates.add(criteriaBuilder.between(root.get("fechaConcesion"), periodoInicio.getTime(), periodoFin.getTime()));
        } else if (periodoInicio != null) { // Filtramos por periodoInicio si está presente
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaConcesion"), periodoInicio.getTime()));
        } else if (periodoFin != null) { // Filtramos por periodoFin si está presente
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaConcesion"), periodoFin.getTime()));
        }

        // Filtramos por tipo de permiso si existe
        if (tipo != null) {
            predicates.add(criteriaBuilder.equal(root.get("tipo"), tipo));
        }

        // Filtramos por jefe si existe
        if (jefe != null) {
            predicates.add(criteriaBuilder.equal(root.get("jefe"), jefe));
        }

        criteria.where(predicates.toArray(new Predicate[0]));
        entityManager.getTransaction().commit();
        entityManager.close();
        return entityManager.createQuery(criteria).getResultList();
    }

    // Métodos drivers para búsqueda dinámica
    public List<Permisos> consultarPermisosJefe(Long id) throws Exception {
        JefesDAO jefesDAO = new JefesDAO();
        return consultarPermisosRegistro(null, null, null, jefesDAO.consultarJefe(id));
    }

    public List<Permisos> consultarTodosPagos() throws Exception {
        return consultarPermisosRegistro(null, null, null, null);
    }
}
