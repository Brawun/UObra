/**
 * PlanosDAO.java
 */
package DAOs;

import Dominio.Jefes;
import Dominio.Planos;
import Enumeradores.Escala;
import Enumeradores.TipoPlano;
import Herramientas.Encriptador;
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
 * y agregar entidades de tipo Planos.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class PlanosDAO {

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
    public void registrarPlano(Planos plano) {
        entityManager.getTransaction().begin();
        this.persist(plano);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    public void editarFechaRealizacionPlano(Long id, Calendar fechaRealizacion) {
        if (verificarPlano(id)) {
            entityManager.getTransaction().begin();
            Planos plano = consultarPlano(id);
            if (!plano.getFechaRealizacion().equals(fechaRealizacion)) {
               plano.setFechaRealizacion(fechaRealizacion);
               entityManager.merge(plano); 
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con ID: " + id);
        }
    }
    
    public void editarFechaRealizacionPlanoFolio(String folio, Calendar fechaRealizacion) throws Exception {
        Encriptador crypt = new Encriptador();
        String verifico = folio;
        verifico = crypt.encrypt(verifico);
        if (verificarPlanoFolio(verifico)) {
            entityManager.getTransaction().begin();
            Planos plano = consultarPlanoFolio(folio);
            if (!plano.getFechaRealizacion().equals(fechaRealizacion)) {
               plano.setFechaRealizacion(fechaRealizacion);
               entityManager.merge(plano); 
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con folio: " + folio);
        }
    }
    
    public void editarTipoPlano(Long id, TipoPlano tipo) {
        if (verificarPlano(id)) {
            entityManager.getTransaction().begin();
            Planos plano = consultarPlano(id);
            if (!plano.getTipo().equals(tipo)) {
                plano.setTipo(tipo);
                entityManager.merge(plano);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con ID: " + id);
        }
    }
    
    public void editarTipoPlanoFolio(String folio, TipoPlano tipo) throws Exception {
        Encriptador crypt = new Encriptador();
        String verifico = folio;
        verifico = crypt.encrypt(verifico);
        if (verificarPlanoFolio(verifico)) {
            entityManager.getTransaction().begin();
            Planos plano = consultarPlanoFolio(folio);
            if (!plano.getTipo().equals(tipo)) {
                plano.setTipo(tipo);
                entityManager.merge(plano);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con folio: " + folio);
        }
    }
    
    public void editarEscalaPlano(Long id, Escala escala) {
        if (verificarPlano(id)) {
            entityManager.getTransaction().begin();
            Planos plano = consultarPlano(id);
            if (!plano.getEscala().equals(escala)) {
                plano.setEscala(escala);
                entityManager.merge(plano);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con ID: " + id);
        }
    }
    
    public void editarEscalaFolio(String folio, Escala escala) throws Exception {
        Encriptador crypt = new Encriptador();
        String verifico = folio;
        verifico = crypt.encrypt(verifico);
        if (verificarPlanoFolio(verifico)) {
            entityManager.getTransaction().begin();
            Planos plano = consultarPlanoFolio(folio);
            if (!plano.getEscala().equals(escala)) {
                plano.setEscala(escala);
                entityManager.merge(plano);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con folio: " + folio);
        }
    }

    public void eliminarPlano(Long id) {
        if (verificarPlano(id)) {
            entityManager.getTransaction().begin();
            Planos plano = consultarPlano(id);
            entityManager.remove(plano);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con ID: " + id);
        }
    }
    
    public void eliminarPlanoFolio(String folio) throws Exception {
        Encriptador crypt = new Encriptador();
        String verifico = folio;
        verifico = crypt.encrypt(verifico);
        if (verificarPlanoFolio(verifico)) {
            entityManager.getTransaction().begin();
            Planos plano = consultarPlanoFolio(folio);
            entityManager.remove(plano);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con folio: " + folio);
        }
    }


    // Métodos de consulta 
    public Boolean verificarPlano(Long id) {
        entityManager.getTransaction().begin();
        Planos plano = entityManager.find(Planos.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return plano != null;
    }
    
    public Boolean verificarPlanoFolio(String folio) {
        entityManager.getTransaction().begin();
        Planos plano = entityManager.find(Planos.class, folio);
        entityManager.getTransaction().commit();
        entityManager.close();
        return plano != null;
    }

    public Planos consultarPlano(Long id) {
        if (verificarPlano(id)) {
            entityManager.getTransaction().begin();
            Planos plano = entityManager.find(Planos.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return plano;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con ID: " + id);
        }
    }
    
    public Planos consultarPlanoFolio(String folio) throws Exception {
        Encriptador crypt = new Encriptador();
        folio = crypt.encrypt(folio);
        if (verificarPlanoFolio(folio)) {
            entityManager.getTransaction().begin();
            Planos plano = entityManager.find(Planos.class, folio);
            entityManager.getTransaction().commit();
            entityManager.close();
            return plano;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el plano con folio: " + crypt.decrypt(folio));
        }
    }

    // Regresa una lista de planos que hayan sido registrados dentro del periodo
    // dado, con la escala dada, del tipo dado y registrados por el jefe dado
    public List<Planos> consultarPlanosRegistro(Calendar periodoInicio, Calendar periodoFin, Escala escala, TipoPlano tipo, Jefes jefe) {
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Planos> criteria = criteriaBuilder.createQuery(Planos.class);
        Root<Planos> root = criteria.from(Planos.class);
        List<Predicate> predicates = new LinkedList();

        // Filtrado por periodo
        if (periodoInicio != null && periodoFin != null) { // Filtramos por ambos periodos si ambos están presentes
            predicates.add(criteriaBuilder.between(root.get("fechaRegistro"), periodoInicio.getTime(), periodoFin.getTime()));
        } else if (periodoInicio != null) { // Filtramos por periodoInicio si está presente
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaRegistro"), periodoInicio.getTime()));
        } else if (periodoFin != null) { // Filtramos por periodoFin si está presente
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaRegistro"), periodoFin.getTime()));
        }

        // Filtramos por escala de plano si existe
        if (tipo != null) {
            predicates.add(criteriaBuilder.equal(root.get("escala"), escala));
        }
        
        // Filtramos por tipo de plano si existe
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
    
    // Regresa una lista de planos que hayan sido realizados dentro del periodo
    // dado, con la escala dada, del tipo dado y registrados por el jefe dado
    public List<Planos> consultarPlanosRealizacion(Calendar periodoInicio, Calendar periodoFin, Escala escala, TipoPlano tipo, Jefes jefe) {
        entityManager.getTransaction().begin();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Planos> criteria = criteriaBuilder.createQuery(Planos.class);
        Root<Planos> root = criteria.from(Planos.class);
        List<Predicate> predicates = new LinkedList();

        // Filtrado por periodo
        if (periodoInicio != null && periodoFin != null) { // Filtramos por ambos periodos si ambos están presentes
            predicates.add(criteriaBuilder.between(root.get("fechaRealizacion"), periodoInicio.getTime(), periodoFin.getTime()));
        } else if (periodoInicio != null) { // Filtramos por periodoInicio si está presente
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fechaRealizacion"), periodoInicio.getTime()));
        } else if (periodoFin != null) { // Filtramos por periodoFin si está presente
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("fechaRealizacion"), periodoFin.getTime()));
        }

        // Filtramos por escala de plano si existe
        if (tipo != null) {
            predicates.add(criteriaBuilder.equal(root.get("escala"), escala));
        }
        
        // Filtramos por tipo de plano si existe
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
    public List<Planos> consultarPlanosJefe(Long id) throws Exception {
        JefesDAO jefesDAO = new JefesDAO();
        return consultarPlanosRegistro(null, null, null, null, jefesDAO.consultarJefe(id));
    }

    public List<Planos> consultarTodosPlanos() throws Exception {
        return consultarPlanosRegistro(null, null, null, null, null);
    }
}
