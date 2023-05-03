/**
 * JefesDAO.java
 */
package DAOs;

import Dominio.Jefes;
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
 * y agregar entidades de tipo Jefes.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class JefesDAO {

    // Métodos de inicio de sesión
    /**
     * Este método regresa un jefe, el cuál es utilizado para generar el inicio
     * de sesión deseado.
     *
     * @param usuario usuario ingresado.
     * @param contrasena constraseña ingresada.
     * @return Jefe si el inicio de sesión fue existoso, nulo en caso contrario.
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public Jefes iniciarSesionJefe(String usuario, String contrasena) throws Exception {
        String verifico = usuario;
        String consulta = usuario;
        if (verificarUsuarioJefe(verifico)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            // Jefe a regresar inicializado
            Jefes jefe = null;
            if (this.verificarContrasenaUsuario(usuario, contrasena)) {
                // Se busca jefe a regresar
                jefe = this.consultarJefesUsuario(consulta);
            }
            entityManager.getTransaction().commit();
            entityManager.close();
            return jefe;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con usuario: " + usuario);
        }
    }

    /**
     * Método para verificar que un usuario exista en la base de datos de jefes.
     *
     * @param usuario usuario a buscar en la base de datos
     * @return Verdadero si el usuario es encontrado, falso en caso contrario
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public boolean verificarUsuarioJefe(String usuario) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        // Encriptador/Desencriptador
        Encriptador crypt = new Encriptador();
        // Se encripta el usuario a buscar antes de buscarlo ya que en la base de datos
        // se encuentran todos encriptados
        String usuarioEncriptado = crypt.encrypt(usuario);
        // Verifica si el usuario ingresado existe en la base de datos de jefes
        TypedQuery<Jefes> query;
        String jpql = "SELECT o FROM Jefes o WHERE o.usuario = :usuarioEncriptado";
        query = entityManager.createQuery(jpql, Jefes.class);
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
     * Método para buscar un usuario en la base de datos de jefes.
     *
     * @param usuario usuario a buscar en la base de datos
     * @return Verdadero si el usuario es encontrado, falso en caso contrario
     * @throws Exception en caso que haya una excepción con la encriptación.
     */
    public Jefes consultarJefesUsuario(String usuario) throws Exception {
        String verifico = usuario;
        if (verificarUsuarioJefe(verifico)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            // Encriptador/Desencriptador
            Encriptador crypt = new Encriptador();
            // Se encripta el usuario a buscar antes de buscarlo ya que en la base de datos
            // se encuentran todos encriptados
            String usuarioEncriptado = crypt.encrypt(usuario);
            // Consulta el jefe con el usuario dado de la base de datos
            TypedQuery<Jefes> query;
            String jpql = "SELECT o FROM Jefes o WHERE o.usuario = :usuarioEncriptado";
            query = entityManager.createQuery(jpql, Jefes.class);
            query.setParameter("usuarioEncriptado", usuarioEncriptado);
            Jefes jefe = query.getSingleResult();
            entityManager.getTransaction().commit();
            entityManager.close();
            return jefe;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con usuario: " + usuario);
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
        if (verificarUsuarioJefe(verifico)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            // Encriptador/Desencriptador
            Encriptador crypt = new Encriptador();
            // Se encripta el usuario y contraseña a buscar antes de buscarlo ya 
            // que en la base de datos se encuentran todos encriptados
            String contrasenaEncriptada = crypt.encrypt(contrasena);
            // Consulta el jefe con el usuario dado de la base de datos
            Jefes jefe = this.consultarJefesUsuario(usuario);
            entityManager.getTransaction().commit();
            entityManager.close();
            return jefe.getContrasena().equals(contrasenaEncriptada);
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con usuario: " + usuario);
        }
    }
    
    public void editarContrasena(Long id, String nuevaContrasena) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        if (verificarJefe(id)) {
            entityManager.getTransaction().begin();
            Jefes jefe = consultarJefe(id);
            Encriptador crypt = new Encriptador();
            jefe.setContrasena(crypt.encrypt(nuevaContrasena));
            entityManager.merge(jefe);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con ID: " + id);
        }
    }
    
    public void editarUsuario(Long id, String nuevoUsuario) throws Exception {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        if (verificarJefe(id)) {
            entityManager.getTransaction().begin();
            Jefes jefe = consultarJefe(id);
            Encriptador crypt = new Encriptador();
            jefe.setUsuario(crypt.encrypt(nuevoUsuario));
            entityManager.merge(jefe);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con ID: " + id);
        }
    }

    // Métodos de acceso
    public void registrarJefe(Jefes jefe) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(jefe);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void eliminarJefe(Long id) {
        if (verificarJefe(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Jefes jefe = consultarJefe(id);
            entityManager.remove(jefe);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con ID: " + id);
        }
    }

    // Métodos de consulta 
    public Boolean verificarJefe(Long id) {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Jefes jefe = entityManager.find(Jefes.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return jefe != null;
    }

    public Jefes consultarJefe(Long id) {
        if (verificarJefe(id)) {
            EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
            EntityManager entityManager = managerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            Jefes jefe = entityManager.find(Jefes.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return jefe;
        } else {
            throw new EntityNotFoundException("No se puede encontrar el jefe con ID: " + id);
        }
    }

    // Método para consultar todos los jefes
    public List<Jefes> consultarJefes() {
        EntityManagerFactory managerFactory = Persistence.createEntityManagerFactory("Pruebas_UObra");
        EntityManager entityManager = managerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        TypedQuery<Jefes> query;
        String jpql = "SELECT j FROM Jefes j";
        query = entityManager.createQuery(jpql, Jefes.class);
        List<Jefes> jefes = query.getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return jefes;
    }
}
