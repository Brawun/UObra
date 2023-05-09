/**
 * ObrasObreroDAO.java
 */
package DAOs;

import Dominio.ObrasObrero;
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
 * y agregar entidades de tipo #.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ObrasObreroDAO {

    /**
     * Llamada a paquete de Herramientas para manipular fechas.
     */
    Fecha fecha = new Fecha();

    // Métodos de acceso
    public void registrarObraObrero(ObrasObrero obrasObrero) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(obrasObrero);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void eliminarObrasObrero(Long id) {
        if (verificarObrasObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            entityManager.remove(obrasObrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    public void desactivarObrasObrero(Long id) {
        if (verificarObrasObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            // Se cambia el estado a inactivo
            obrasObrero.setActivo(false);
            // Insertar fecha fin de ahora
            obrasObrero.setFechaFin(fecha.fechaAhora());
            // Contabilizar días trabajados e insertarlos
            Integer diferenciaDias = fecha.calcularDiferenciaDias(obrasObrero.getFechaInicio(), obrasObrero.getFechaFin());
            obrasObrero.setDiasTrabajados(obrasObrero.getDiasTrabajados() + diferenciaDias);
            entityManager.merge(obrasObrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    public void pagarObrasObrero(Long id) {
        if (verificarObrasObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            ObrerosDAO obrerosDAO = new ObrerosDAO();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            // Sumar a por pagar en obrero en la relación a obra, según su sueldo 
            // diario actual y sus días trabajados, calculados según la fecha de
            // inicio y fecha fin de relación obras - obrero
            Long idObrero = obrasObrero.getObrero().getId();
            obrerosDAO.sumarDiasTrabajadosObrero(idObrero, obrasObrero.getDiasTrabajados());
            entityManager.merge(obrasObrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    public void desactivarYPagarObrasObrero(Long id) {
        if (verificarObrasObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            ObrerosDAO obrerosDAO = new ObrerosDAO();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            // Se cambia el estado a inactivo
            obrasObrero.setActivo(false);
            // Insertar fecha fin de ahora
            obrasObrero.setFechaFin(fecha.fechaAhora());
            // Contabilizar días trabajados e insertarlos
            Integer diferenciaDias = fecha.calcularDiferenciaDias(obrasObrero.getFechaInicio(), obrasObrero.getFechaFin());
            obrasObrero.setDiasTrabajados(obrasObrero.getDiasTrabajados() + diferenciaDias);
            // Sumar a por pagar en obrero en la relación a obra, según su sueldo 
            // diario actual y sus días trabajados, calculados según la fecha de
            // inicio y fecha fin de relación obras - obrero
            Long idObrero = obrasObrero.getObrero().getId();
            obrerosDAO.sumarDiasTrabajadosObrero(idObrero, obrasObrero.getDiasTrabajados());
            entityManager.merge(obrasObrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    public void activarObrasObrero(Long id) {
        if (verificarObrasObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            obrasObrero.setActivo(true);
            // Insertar fecha inicio nueva de ahora
            obrasObrero.setFechaInicio(fecha.fechaAhora());
            // Reiniciar dias trabajados a cero
            obrasObrero.setDiasTrabajados(0);
            entityManager.merge(obrasObrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    public void sumarDiasTrabajadosObrasObrero(Long id, Integer dias) {
        if (verificarObrasObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            obrasObrero.setDiasTrabajados(obrasObrero.getDiasTrabajados() + dias);
            entityManager.merge(obrasObrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    public void restarDiasTrabajadosObrasObrero(Long id, Integer dias) {
        if (verificarObrasObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            // No se permite que una relación obras - obreros tenga un total de
            // dias trabajados menor que 0
            if (obrasObrero.getDiasTrabajados() != 0) {
                // No se permite que se resten más días de los que ya se tienen
                // trabajados, para que no queden en días negativos
                if (dias < obrasObrero.getDiasTrabajados()) {
                    obrasObrero.setDiasTrabajados(obrasObrero.getDiasTrabajados() - dias);
                } else {
                    obrasObrero.setDiasTrabajados(0);
                }
                entityManager.merge(obrasObrero);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    // Métodos de consulta 
    public Boolean verificarObrasObrero(Long id) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        ObrasObrero obrasObrero = entityManager.find(ObrasObrero.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return obrasObrero != null;
    }

    public ObrasObrero consultarObrasObrero(Long id) {
        if (verificarObrasObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = entityManager.find(ObrasObrero.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObrero;
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    // Manda una lista de obras - obreros que cumplan en iniciación con el
    // periodo de inicio y fin, con el estado de activa, con un mínimo de dias 
    // trabajados y con la obra/obrero en particular
    public List<ObrasObrero> consultarObrasObrerosFechaInicio(Calendar periodoInicio, Calendar periodoFin, Boolean activa, Integer diasTrabajados, Long obraId, Long obreroId) throws Exception {
        TypedQuery<ObrasObrero> query;
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        // BUSQUEDA POR 6 CAMPOS
        if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Se llenaron todos los campos
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 5 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, fin, obra, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, fin, obra, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, fin, obra, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, fin, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, obra, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por fin, obra, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 4 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, fin, obra, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, fin, obra, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, fin, obra, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, fin, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, fin, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, fin, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, obra, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, obra, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, obra, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por fin, obra, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por fin, obra, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por fin, obra, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por fin, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por obra, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 3 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, fin, obra
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, fin, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, fin, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, fin, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, obra, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, obra, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, obra, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por fin, obra, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por fin, obra, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por fin, obra, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por fin, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por fin, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por fin, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por obra, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por obra, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por obra, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 2 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, fin
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, obra
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por fin, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por fin, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por fin, obra
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por fin, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obrero.id = :obreroId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);;
            query.setParameter("activa", activa);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por obra, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por obra, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por obra, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 1 CAMPO
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por fin
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por obra
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 0 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // No se llenó ningún campo
            String jpql = "SELECT o FROM ObrasObrero o";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else {
            throw new Exception("No se pudo realizar la búsqueda dinámica de relacion obras - obreros");
        }
    }

    // Manda una lista de obras - obreros que cumplan en finalización con el
    // periodo de inicio y fin, con el estado de activa, con un mínimo de dias 
    // trabajados y con la obra/obrero en particular
    public List<ObrasObrero> consultarObrasObrerosFechaFin(Calendar periodoInicio, Calendar periodoFin, Boolean activa, Integer diasTrabajados, Long obraId, Long obreroId) throws Exception {
        TypedQuery<ObrasObrero> query;
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        // BUSQUEDA POR 6 CAMPOS
        if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Se llenaron todos los campos
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 5 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, fin, obra, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, fin, obra, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, fin, obra, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, fin, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, obra, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por fin, obra, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 4 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, fin, obra, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, fin, obra, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, fin, obra, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, fin, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, fin, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, fin, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, obra, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, obra, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, obra, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por fin, obra, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por fin, obra, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por fin, obra, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por fin, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por obra, obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 3 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, fin, obra
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, fin, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, fin, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, fin, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por inicio, obra, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, obra, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, obra, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por fin, obra, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por fin, obra, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por fin, obra, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por fin, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por fin, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por fin, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId != null) { // Búsqueda por obra, obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por obra, obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por obra, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por obrero, activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 2 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, fin
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("activa", activa);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por inicio, obra
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por inicio, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por fin, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("activa", activa);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por fin, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por fin, obra
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "AND o.obra.id = :obraId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por fin, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obrero.id = :obreroId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por activa, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.diasTrabajados >= :diasTrabajados";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por obrero, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);;
            query.setParameter("activa", activa);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por obra, activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa "
                    + "AND o.obra.id = :obraId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId != null
                && obreroId == null) { // Búsqueda por obra, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obra.id = :obraId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId != null) { // Búsqueda por obrero, dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId != null) { // Búsqueda por obra, obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId "
                    + "AND o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("obraId", obraId);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 1 CAMPO
        } else if (periodoInicio != null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por inicio
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin != null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por fin
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId != null
                && obreroId == null) { // Búsqueda por obra
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obra.id = :obraId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("obraId", obraId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId != null) { // Búsqueda por obrero
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.obrero.id = :obreroId";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("obreroId", obreroId);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa != null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // Búsqueda por activa
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.activa = :activa";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("activa", activa);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados != null
                && obraId == null
                && obreroId == null) { // Búsqueda por dias
            String jpql = "SELECT o FROM ObrasObrero o WHERE "
                    + "o.diasTrabajados >= :diasTrabajados";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
            // BÚSQUEDA POR 0 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && activa == null
                && diasTrabajados == null
                && obraId == null
                && obreroId == null) { // No se llenó ningún campo
            String jpql = "SELECT o FROM ObrasObrero o";
            query = entityManager.createQuery(jpql, ObrasObrero.class);
            List<ObrasObrero> obrasObreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObreros;
        } else {
            throw new Exception("No se pudo realizar la búsqueda dinámica de relacion obras - obreros");
        }
    }

    // Métodos drivers para búsqueda dinámica
    public List<ObrasObrero> consultarObrasObreroActivas() throws Exception {
        return this.consultarObrasObrerosFechaInicio(null, null, true, 0, null, null);
    }
    
    public List<ObrasObrero> consultarObrasObreroInactivas() throws Exception {
        return this.consultarObrasObrerosFechaInicio(null, null, false, 1, null, null);
    }
    
    public List<ObrasObrero> consultarObrasObreroDeObreroActivas(Long obreroId) throws Exception {
        return this.consultarObrasObrerosFechaInicio(null, null, true, null, null, obreroId);
    }
    
    public List<ObrasObrero> consultarObrasObreroDeObreroInactivas(Long obreroId) throws Exception {
        return this.consultarObrasObrerosFechaInicio(null, null, false, null, null, obreroId);
    }

    public List<ObrasObrero> consultarTodasObrasObrero() throws Exception {
        return this.consultarObrasObrerosFechaInicio(null, null, null, null, null, null);
    }
}
