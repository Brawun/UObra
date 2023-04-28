/**
 * ClientesDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Obras;
import Dominio.Pagos;
import Dominio.Ubicaciones;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo Clientes.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ClientesDAO {

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
    public void registrarCliente(Clientes cliente) {
        entityManager.getTransaction().begin();
        this.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    // Sumar a la deuda del cliente el monto dado
    public void sumarDeudaCliente(Long id, Float monto) {
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            cliente.setDeudaTotal(cliente.getDeudaTotal() + monto);
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }

    // Restar a la deuda del cliente el monto dado
    public void restarDeudaCliente(Long id, Float monto) {
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            cliente.setDeudaTotal(cliente.getDeudaTotal() - monto);
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }

    // Se agrega la obra dada al cliente
    public void agregarObraCliente(Long id, Obras obra) throws Exception {
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            if (cliente.getObras().add(obra)) {
                entityManager.merge(cliente);
            } else {
                throw new Exception("No se pudo agregar una obra al cliente con ID: " + id);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }

    // Se agrega la ubicacion dada al cliente
    public void agregarUbicacionCliente(Long id, Ubicaciones ubicacion) throws Exception {
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            if (cliente.getUbicaciones().add(ubicacion)) {
                entityManager.merge(cliente);
            } else {
                throw new Exception("No se pudo agregar una ubicación al cliente con ID: " + id);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }

    // Se agrega el pago dado al cliente
    public void agregarPagosCliente(Long id, Pagos pago) throws Exception {
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            if (cliente.getPagos().add(pago)) {
                entityManager.merge(cliente);
            } else {
                throw new Exception("No se pudo agregar un pago al cliente con ID: " + id);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }

    public void eliminarCliente(Long id) {
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            entityManager.remove(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }

    // Métodos de consulta 
    public Boolean verificarCliente(Long id) {
        entityManager.getTransaction().begin();
        Clientes cliente = entityManager.find(Clientes.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return cliente != null;
    }

    public Clientes consultarCliente(Long id) {
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = entityManager.find(Clientes.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return cliente;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }

    // Regresa una lista de clientes que esten endeudados o no y su deuda sea 
    // mayor o igual a la dada
    public List<Clientes> consultarClientes(Boolean endeudados, Float deuda) throws Exception {
        entityManager.getTransaction().begin();
        if (endeudados && deuda > (float) 0) { // Si se buscan clientes con deudas (true)
            TypedQuery<Clientes> query;
            String jpql = "SELECT c FROM Clientes c WHERE c.deudaTotal >= :deuda";
            query = entityManager.createQuery(jpql, Clientes.class);
            query.setParameter("deuda", deuda);
            List<Clientes> clientes = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return clientes;
        } else if (!endeudados && deuda == (float) 0) { // Si se buscan clientes sin deudas (false)
            TypedQuery<Clientes> query;
            String jpql = "SELECT c FROM Clientes c WHERE c.deudaTotal = 0";
            query = entityManager.createQuery(jpql, Clientes.class);
            List<Clientes> clientes = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return clientes;
        } else if (endeudados == null && deuda == null) { // Si buscan a todos los clientes
            TypedQuery<Clientes> query;
            String jpql = "SELECT c FROM Clientes c";
            query = entityManager.createQuery(jpql, Clientes.class);
            List<Clientes> clientes = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return clientes;
        } else {
            throw new Exception("No se pudo realizar la búsqueda de clientes con éxito.");
        }
    }

    // Métodos drivers paa búsqueda dinámica
    public List<Clientes> consultarClientesDeudores(Float deudaMínima) throws Exception {
        return consultarClientes(true, deudaMínima);
    }

    public List<Clientes> consultarClientesNoDeudores() throws Exception {
        return consultarClientes(false, (float) 0);
    }

    public List<Clientes> consultarTodosClientes() throws Exception {
        return consultarClientes(null, null);
    }
}
