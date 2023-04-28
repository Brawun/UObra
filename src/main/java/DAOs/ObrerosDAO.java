/**
 * ObrerosDAO.java
 */
package DAOs;

import Dominio.Obreros;
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
public class ObrerosDAO {
    
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
     * @param object Objeto a guardar en la base de datos perteneciente a la clase
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
    public void registrarObrero(Obreros obrero) {
        entityManager.getTransaction().begin();
        this.persist(obrero);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    public void eliminarObrero(Long id) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            entityManager.remove(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Sube el sueldo del obrero el monto dado
    public void subirSueldoObrero(Long id, Float monto) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.setSueldoDiario(obrero.getSueldoDiario() + monto);
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Baja el sueldo del obrero el monto dado
    public void bajarSueldoObrero(Long id, Float monto) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.setSueldoDiario(obrero.getSueldoDiario() - monto);
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Resta a obrero una cierta cantidad por pagar
    public void restarPorPagar(Long id, Float monto) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.setPorPagar(obrero.getPorPagar() - monto);
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Suma a obrero una cierta cantidad por pagar
    public void sumarPorPagar(Long id, Float monto) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.setPorPagar(obrero.getPorPagar() + monto);
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Resta a obrero una cierta cantidad por pagado
    public void restarPagado(Long id, Float monto) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.setPagado(obrero.getPagado() - monto);
            obrero.setPorPagar(obrero.getPorPagar() + monto);
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Suma a obrero una cierta cantidad por pagado
    public void sumarPagado(Long id, Float monto) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.setPagado(obrero.getPagado() + monto);
            obrero.setPorPagar(obrero.getPorPagar() - monto);
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Suma a los dias trabajados la cantidad dada
    public void sumarDiasTrabajadosObrero(Long id, Integer dias) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.setDiasTrabajados(obrero.getDiasTrabajados() + dias);
            obrero.setPorPagar(obrero.getPorPagar() + (obrero.getSueldoDiario() * dias));
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Resta a los dias trabajados la cantidad dada
    public void restarDiasTrabajadosObrero(Long id, Integer dias) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.setDiasTrabajados(obrero.getDiasTrabajados() - dias);
            obrero.setPorPagar(obrero.getPorPagar() + (obrero.getSueldoDiario() - dias));
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    } 
    
    // Métodos de consulta 
    public Boolean verificarObrero(Long id) {
        entityManager.getTransaction().begin();
        Obreros obrero = entityManager.find (Obreros.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return obrero != null;
    }
    
    public Obreros consultarObrero(Long id) {
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = entityManager.find (Obreros.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrero;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    // Regresa una lista de obreros con un mayor de dias trabajados y mayor 
    // sueldo diario al dado
    public List<Obreros> consultarObreros(Integer diasTrabajados, Float sueldoDiario) throws Exception {
        entityManager.getTransaction().begin();
        if (diasTrabajados != null && sueldoDiario != null) {
            TypedQuery<Obreros> query;
            String jpql = "SELECT o FROM Obreros o WHERE o.diasTrabajados >= :diasTrabajados AND o.sueldoDiario >= :sueldoDiario";
            query = entityManager.createQuery(jpql, Obreros.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            query.setParameter("sueldoDiario", sueldoDiario);
            List<Obreros> obreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obreros;
        } else if (diasTrabajados != null && sueldoDiario == null) { // Si se buscan clientes con deudas (true)
            TypedQuery<Obreros> query;
            String jpql = "SELECT o FROM Obreros o WHERE o.diasTrabajados >= :diasTrabajados";
            query = entityManager.createQuery(jpql, Obreros.class);
            query.setParameter("diasTrabajados", diasTrabajados);
            List<Obreros> obreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obreros;
        } else if (diasTrabajados == null && sueldoDiario != null) { // Si se buscan cli
            TypedQuery<Obreros> query;
            String jpql = "SELECT o FROM Obreros o WHERE o.sueldoDiario >= :sueldoDiario";
            query = entityManager.createQuery(jpql, Obreros.class);
            query.setParameter("sueldoDiario", sueldoDiario);
            List<Obreros> obreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obreros;
        } else if (diasTrabajados == null && sueldoDiario == null) { // Si buscan a todos los obreros
            TypedQuery<Obreros> query;
            String jpql = "SELECT o FROM Obreros o";
            query = entityManager.createQuery(jpql, Obreros.class);
            List<Obreros> obreros = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obreros;
        } else {
            throw new Exception("No se pudo realizar la búsqueda de obreros con éxito.");
        }
    }
    
    // Métodos drivers paa búsqueda dinámica
    public List<Obreros> consultarObrerosConDiasTrabajadosMínimo(Integer diasTrabajados) throws Exception {
        return consultarObreros(diasTrabajados, null);
    }

    public List<Obreros> consultarObrerosConSueldoMínimo(Float sueldoDiario) throws Exception {
        return consultarObreros(null, sueldoDiario);
    }

    public List<Obreros> consultarTodosObreros() throws Exception {
        return consultarObreros(null, null);
    }
}
