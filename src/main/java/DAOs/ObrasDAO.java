/**
 * ObrasDAO.java
 */
package DAOs;

import Dominio.Obras;
import Dominio.Obreros;
import Enumeradores.EstadoObra;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;

/**
 * Esta clase DAO permite implementar métodos para acceder, consultar, eliminar
 * y agregar entidades de tipo #.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class ObrasDAO {

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

    /**
     * Método para obtener la fecha del preciso momento.
     *
     * @return ahora, fecha del preciso momento.
     */
    public Calendar fechaAhora() {
        Calendar ahora = Calendar.getInstance();
        ahora.setTime(new Date());
        return ahora;
    }

    // Métodos de acceso
    public void registrarObra(Obras obra) {
        entityManager.getTransaction().begin();
        this.persist(obra);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void eliminarObra(Long id) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            entityManager.remove(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Cambia la obra al estado dado
    public void cambiarEstadoObra(Long id, EstadoObra estadoNuevo) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setEstado(estadoNuevo);
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Inserta una fecha inicio y cambia el estado
    public void iniciarObra(Long id) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setEstado(EstadoObra.DESARROLLO);
            obra.setFechaInicio(fechaAhora());
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Inserta una fecha fin y cambia el estado
    public void terminarObra(Long id) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setEstado(EstadoObra.TERMINADA);
            obra.setFechaFin(fechaAhora());
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Cambia el booleando pagada a true por medio de una búsqueda por ID
    public void pagarObraID(Long id) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setEstaPagada(true);
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Cambia el booleando pagada a false por medio de una búsqueda por ID
    public void endeudarObraID(Long id) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setEstaPagada(false);
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Cambia el booleando pagada a true por medio de un objeto
    public Obras pagarObraObj(Obras obra) {
        obra.setEstaPagada(true);
        return obra;
    }

    // Cambia el booleando pagada a false por medio de un objeto
    public Obras endeudarObraObj(Obras obra) {
        obra.setEstaPagada(false);
        return obra;
    }

    // Suma monto a deuda
    public void restarDeudaObra(Long id, Float monto) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setDeuda(obra.getDeuda() - monto);
            if (obra.getDeuda() <= (float) 0) {
                obra = pagarObraObj(obra);
            }
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Resta monto a deuda
    public void sumarDeudaObra(Long id, Float monto) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setDeuda(obra.getDeuda() + monto);
            if (obra.getDeuda() > (float) 0) {
                obra = endeudarObraObj(obra);
            }
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Suma monto a costo arranque
    public void sumarCostoArranqueObra(Long id, Float monto) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setCostoArranque(obra.getCostoArranque() + monto);
            obra.setDeuda(obra.getDeuda() + monto);
            if (obra.getDeuda() > (float) 0) {
                obra = endeudarObraObj(obra);
            }
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Resta monto a costo arranque
    public void restarCostoArranqueObra(Long id, Float monto) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setCostoArranque(obra.getCostoArranque() - monto);
            obra.setDeuda(obra.getDeuda() - monto);
            if (obra.getDeuda() <= (float) 0) {
                obra = pagarObraObj(obra);
            }
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Suma monto a inversion
    public void sumarInversionObra(Long id, Float monto) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setInversion(obra.getInversion() + monto);
            obra.setDeuda(obra.getDeuda() + monto);
            if (obra.getDeuda() > (float) 0) {
                obra = endeudarObraObj(obra);
            }
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Resta monto a inversion
    public void restarInversionObra(Long id, Float monto) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setInversion(obra.getInversion() - monto);
            obra.setDeuda(obra.getDeuda() - monto);
            if (obra.getDeuda() <= (float) 0) {
                obra = pagarObraObj(obra);
            }
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Se asigna un jefe a una obra
    public void asignarJefeObra(Long id, Long IdJefe) {
        if (verificarObra(id)) {
            JefesDAO JefesDAO = new JefesDAO();
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.setJefe(JefesDAO.consultarJefe(id));
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Asigna un nuevo obrero a una obra
    public void asingarObreroObra(Long id, Long idObrero) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = consultarObra(id);
            obra.getObreros().add()
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Asigna 3 nuevos obreros a una obra, para que esta pueda comenzar
    public void asingarTresObrerosObra(Long id, Obreros obrero1, Obreros obrero2, Obreros obrero3) {

    }

    // Asigna un nuevo pago a una obra 
    public void agregarPagoObra(Long idObra, Long idPago) {

    }

    // Asigna un nuevo plano a obra
    public void agregarPlanoObra(Long idObra, Long idPlano) {

    }

    // Asigna un nuevo permiso a obra
    public void agregarPermisoObra(Long idObra, Long idPermiso) {

    }

    // Asigna una nueva ubicación a obra
    public void agregarUbicacionObra(Long idObra, Long idUbicacion) {

    }

    // Métodos de consulta 
    public Boolean verificarObra(Long id) {
        entityManager.getTransaction().begin();
        Obras obra = entityManager.find(Obras.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return obra != null;
    }

    public Obras consultarObra(Long id) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            Obras obra = entityManager.find(Obras.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return obra;
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Arroja una lista de obras que hayan iniciado dentro del periodo dado, 
    // tengan el estado dado y estén o no pagadas
    public List<Obras> consultarObras(Calendar periodoInicio, Calendar periodoFin, EstadoObra estado, Boolean pagada, Long clienteId) {

    }
}
