/**
 * ClientesDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Obras;
import Dominio.Pagos;
import Dominio.Ubicaciones;
import Herramientas.Encriptador;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
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

    // Métodos de inicio de sesión
    /**
     * Este método regresa un cliente, el cuál es utilizado para generar el
     * inicio de sesión deseado.
     *
     * @param usuario usuario ingresado.
     * @param contrasena constraseña ingresada.
     * @return Cliente si el inicio de sesión fue existoso, nulo en caso
     * contrario.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public Clientes iniciarSesionCliente(String usuario, String contrasena) throws Exception {
        String verifico = usuario;
        String consulta = usuario;
        if (verificarUsuarioCliente(verifico)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            // Cliente a regresar inicializado
            Clientes cliente = null;
            if (this.verificarContrasenaUsuario(usuario, contrasena)) {
                // Se busca cliente a regresar
                cliente = this.consultarClientesUsuario(consulta);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return cliente;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con usuario: " + usuario);
        }
    }

    /**
     * Método para verificar que un usuario exista en la base de datos de
     * clientes.
     *
     * @param usuario usuario a buscar en la base de datos
     * @return Verdadero si el usuario es encontrado, falso en caso contrario
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public boolean verificarUsuarioCliente(String usuario) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        // Encriptador/Desencriptador
        Encriptador crypt = new Encriptador();
        // Se encripta el usuario a buscar antes de buscarlo ya que en la base de datos
        // se encuentran todos encriptados
        String usuarioEncriptado = crypt.encrypt(usuario);
        // Verifica si el usuario ingresado existe en la base de datos de clientes
        TypedQuery<Clientes> query;
        String jpql = "SELECT o FROM Clientes o WHERE o.usuario = :usuarioEncriptado";
        query = entityManager.createQuery(jpql, Clientes.class);
        query.setParameter("usuarioEncriptado", usuarioEncriptado);
        entityManager.getTransaction().commit();
        try {
            query.getSingleResult();
        } catch (NoResultException e) {
            entityManager.close();
            return false;
        }
        entityManager.close();
        return true;
    }

    /**
     * Método para buscar un usuario en la base de datos de clientes.
     *
     * @param usuario usuario a buscar en la base de datos
     * @return Verdadero si el usuario es encontrado, falso en caso contrario
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public Clientes consultarClientesUsuario(String usuario) throws Exception {
        String verifico = usuario;
        if (verificarUsuarioCliente(verifico)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            // Encriptador/Desencriptador
            Encriptador crypt = new Encriptador();
            // Se encripta el usuario a buscar antes de buscarlo ya que en la base de datos
            // se encuentran todos encriptados
            String usuarioEncriptado = crypt.encrypt(usuario);
            // Consulta el cliente con el usuario dado de la base de datos
            TypedQuery<Clientes> query;
            String jpql = "SELECT o FROM Clientes o WHERE o.usuario = :usuarioEncriptado";
            query = entityManager.createQuery(jpql, Clientes.class);
            query.setParameter("usuarioEncriptado", usuarioEncriptado);
            Clientes cliente = query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return cliente;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con usuario: " + usuario);
        }
    }

    /**
     * Método para validar un inicio de sesión, correspondiendo un usuario y una
     * contraseña.
     *
     * @param usuario usuario al que le puede corresponder la contraseña
     * @param contrasena contraseña a la que se verificará si es la que
     * corresponde a usuario
     * @return Verdadero si el usuario y la contraseña corresponden, falso en
     * caso contrario
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public boolean verificarContrasenaUsuario(String usuario, String contrasena) throws Exception {
        String verifico = usuario;
        if (verificarUsuarioCliente(verifico)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            // Encriptador/Desencriptador
            Encriptador crypt = new Encriptador();
            // Se encripta el usuario y contraseña a buscar antes de buscarlo ya 
            // que en la base de datos se encuentran todos encriptados
            String contrasenaEncriptada = crypt.encrypt(contrasena);
            // Consulta el cliente con el usuario dado de la base de datos
            Clientes cliente = this.consultarClientesUsuario(usuario);
            entityManager.getTransaction().commit();
            entityManager.close();
            return cliente.getContrasena().equals(contrasenaEncriptada);
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con usuario: " + usuario);
        }
    }
    
    public void editarContrasena(Long id, String nuevaContrasena) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            Encriptador crypt = new Encriptador();
            cliente.setContrasena(crypt.encrypt(nuevaContrasena));
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }
    
    public void editarUsuario(Long id, String nuevoUsuario) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        if (verificarCliente(id)) {
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            Encriptador crypt = new Encriptador();
            cliente.setUsuario(crypt.encrypt(nuevoUsuario));
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el cliente con ID: " + id);
        }
    }

    // Métodos de acceso
    public Long registrarCliente(Clientes cliente) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(cliente);
        entityManager.getTransaction().commit();
        entityManager.close();
        return cliente.getId();
    }

    // Sumar a la deuda del cliente el monto dado
    public void sumarDeudaCliente(Long id, Float monto) {
        if (verificarCliente(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
    
    public void sumarInversionTotalCliente(Long id, Float monto) {
        if (verificarCliente(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Clientes cliente = consultarCliente(id);
            cliente.setInversionTotal(cliente.getInversionTotal() + monto);
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Clientes cliente = entityManager.find(Clientes.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return cliente != null;
    }

    public Clientes consultarCliente(Long id) {
        if (verificarCliente(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
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

    // Métodos drivers para búsqueda dinámica
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
