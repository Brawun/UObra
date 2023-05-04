/**
 * ObrerosDAO.java
 */
package DAOs;

import Dominio.Obreros;
import Dominio.Pagos;
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
 * y agregar entidades de tipo Obreros.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ObrerosDAO {

    // Métodos de inicio de sesión
    /**
     * Este método regresa un obrero, el cuál es utilizado para generar el
     * inicio de sesión deseado.
     *
     * @param usuario usuario ingresado.
     * @param contrasena constraseña ingresada.
     * @return Obrero si el inicio de sesión fue existoso, nulo en caso
     * contrario.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public Obreros iniciarSesionObrero(String usuario, String contrasena) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        String verifico = usuario;
        String consulta = usuario;
        if (verificarUsuarioObrero(verifico)) {
            entityManager.getTransaction().begin();
            // Obrero a regresar inicializado
            Obreros obrero = null;
            if (this.verificarContrasenaUsuario(usuario, contrasena)) {
                // Se busca obrero a regresar
                obrero = this.consultarObrerosUsuario(consulta);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrero;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con usuario: " + usuario);
        }
    }

    /**
     * Método para verificar que un usuario exista en la base de datos de
     * obreros.
     *
     * @param usuario usuario a buscar en la base de datos
     * @return Verdadero si el usuario es encontrado, falso en caso contrario
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public boolean verificarUsuarioObrero(String usuario) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        // Encriptador/Desencriptador
        Encriptador crypt = new Encriptador();
        // Se encripta el usuario a buscar antes de buscarlo ya que en la base de datos
        // se encuentran todos encriptados
        String usuarioEncriptado = crypt.encrypt(usuario);
        // Verifica si el usuario ingresado existe en la base de datos de obreros
        TypedQuery<Obreros> query;
        String jpql = "SELECT o FROM Obreros o WHERE o.usuario = :usuarioEncriptado";
        query = entityManager.createQuery(jpql, Obreros.class);
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
     * Método para buscar un usuario en la base de datos de obreros.
     *
     * @param usuario usuario a buscar en la base de datos
     * @return Verdadero si el usuario es encontrado, falso en caso contrario
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public Obreros consultarObrerosUsuario(String usuario) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        String verifico = usuario;
        if (verificarUsuarioObrero(verifico)) {
            entityManager.getTransaction().begin();
            // Encriptador/Desencriptador
            Encriptador crypt = new Encriptador();
            // Se encripta el usuario a buscar antes de buscarlo ya que en la base de datos
            // se encuentran todos encriptados
            String usuarioEncriptado = crypt.encrypt(usuario);
            // Consulta el obrero con el usuario dado de la base de datos
            TypedQuery<Obreros> query;
            String jpql = "SELECT o FROM Obreros o WHERE o.usuario = :usuarioEncriptado";
            query = entityManager.createQuery(jpql, Obreros.class);
            query.setParameter("usuarioEncriptado", usuarioEncriptado);
            Obreros obrero = query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrero;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con usuario: " + usuario);
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
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        String verifico = usuario;
        if (verificarUsuarioObrero(verifico)) {
            entityManager.getTransaction().begin();
            // Encriptador/Desencriptador
            Encriptador crypt = new Encriptador();
            // Se encripta el usuario y contraseña a buscar antes de buscarlo ya 
            // que en la base de datos se encuentran todos encriptados
            String contrasenaEncriptada = crypt.encrypt(contrasena);
            // Consulta el obrero con el usuario dado de la base de datos
            Obreros obrero = this.consultarObrerosUsuario(usuario);
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrero.getContrasena().equals(contrasenaEncriptada);
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con usuario: " + usuario);
        }
    }
    
    public void editarContrasena(Long id, String nuevaContrasena) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            Encriptador crypt = new Encriptador();
            obrero.setContrasena(crypt.encrypt(nuevaContrasena));
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    public void editarUsuario(Long id, String nuevoUsuario) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            Encriptador crypt = new Encriptador();
            obrero.setUsuario(crypt.encrypt(nuevoUsuario));
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }

    // Métodos de acceso
    public void registrarObrero(Obreros obrero) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(obrero);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    public void agregarPagoObrero(Long id, Pagos pago) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            //obrero.getPagos().add(pago);
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    }
    
    public void eliminarPagoObrero(Long id, Pagos pago) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        if (verificarObrero(id)) {
            entityManager.getTransaction().begin();
            Obreros obrero = consultarObrero(id);
            obrero.getPagos().remove(pago);
            entityManager.merge(obrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el obrero con ID: " + id);
        }
    } 

    public void eliminarObrero(Long id) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
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
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Obreros obrero = entityManager.find(Obreros.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return obrero != null;
    }

    public Obreros consultarObrero(Long id) {
        if (verificarObrero(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Obreros obrero = entityManager.find(Obreros.class, id);
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
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
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
        } else if (diasTrabajados != null && sueldoDiario == null) { // Si se buscan obreros con deudas (true)
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

    // Métodos drivers para búsqueda dinámica
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
