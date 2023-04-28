/**
 * ObrasObreroDAO.java
 */
package DAOs;

import Dominio.ObrasObrero;
import Dominio.Obreros;
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
public class ObrasObreroDAO {

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

    /**
     * Método que recibe dos objetos de tipo Calendar, fechaInicial y
     * fechaFinal, que representan las fechas a comparar. La función de este
     * método es calcular la diferencia en días entre las dos fechas y
     * retornarla como un entero.
     *
     * @param fechaInicial Fecha inicial
     * @param fechaFinal Fecha final
     * @return diferenciaDias entero con la diferencia de días entre fecha
     * inicial y final
     */
    public Integer calcularDiferenciaDias(Calendar fechaInicial, Calendar fechaFinal) {
        // Convertir las fechas a milisegundos
        long milisegundosFechaInicial = fechaInicial.getTimeInMillis();
        long milisegundosFechaFinal = fechaFinal.getTimeInMillis();

        // Calcular la diferencia en milisegundos
        long diferenciaMilisegundos = milisegundosFechaFinal - milisegundosFechaInicial;

        // Convertir la diferencia de milisegundos a días
        int diferenciaDias = (int) (diferenciaMilisegundos / (24 * 60 * 60 * 1000));

        // Retornar la diferencia en días
        return diferenciaDias;
    }

    // Métodos de acceso
    public void registrarObraObrero(ObrasObrero obrasObrero) {
        entityManager.getTransaction().begin();
        this.persist(obrasObrero);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void eliminarObrasObrero(Long id) {
        if (verificarObrasObrero(id)) {
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
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            // Se cambia el estado a inactivo
            obrasObrero.setActivo(false);
            // Insertar fecha fin de ahora
            obrasObrero.setFechaFin(this.fechaAhora());
            // Contabilizar días trabajados e insertarlos
            Integer diferenciaDias = this.calcularDiferenciaDias(obrasObrero.getFechaInicio(), obrasObrero.getFechaFin());
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
            entityManager.getTransaction().begin();
            ObrerosDAO obrerosDAO = new ObrerosDAO();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            // Se cambia el estado a inactivo
            obrasObrero.setActivo(false);
            // Insertar fecha fin de ahora
            obrasObrero.setFechaFin(this.fechaAhora());
            // Contabilizar días trabajados e insertarlos
            Integer diferenciaDias = this.calcularDiferenciaDias(obrasObrero.getFechaInicio(), obrasObrero.getFechaFin());
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
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            obrasObrero.setActivo(true);
            // Insertar fecha inicio nueva de ahora
            obrasObrero.setFechaInicio(this.fechaAhora());
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
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = consultarObrasObrero(id);
            obrasObrero.setDiasTrabajados(obrasObrero.getDiasTrabajados() - dias);
            entityManager.merge(obrasObrero);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    // Métodos de consulta 
    public Boolean verificarObrasObrero(Long id) {
        entityManager.getTransaction().begin();
        ObrasObrero obrasObrero = entityManager.find(ObrasObrero.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return obrasObrero != null;
    }

    public ObrasObrero consultarObrasObrero(Long id) {
        if (verificarObrasObrero(id)) {
            entityManager.getTransaction().begin();
            ObrasObrero obrasObrero = entityManager.find(ObrasObrero.class, id);
            entityManager.getTransaction().commit();
            entityManager.close();
            return obrasObrero;
        } else {
            throw new EntityNotFoundException("No se puede encontrar la relación obra - obrero con ID: " + id);
        }
    }

    // Manda una lista de obras - obreros que cumplan con el periodo de inicio 
    // y fin, con el estado de activa y con la obra en particular
    public List<ObrasObrero> consultarObrasObreros(Calendar periodoInicio, Calendar periodoFin, Boolean activa, Long IdObra, Long IdObrero) {
        
    }
}
