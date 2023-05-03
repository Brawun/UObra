/**
 * ObrasDAO.java
 */
package DAOs;

import Dominio.Clientes;
import Dominio.Obras;
import Dominio.ObrasObrero;
import Dominio.Obreros;
import Dominio.Pagos;
import Dominio.Permisos;
import Dominio.Planos;
import Dominio.Ubicaciones;
import Enumeradores.EstadoObra;
import Enumeradores.TipoUbicacion;
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
 * y agregar entidades de tipo Obras.
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
     * Llamada a paquete de Herramientas para manipular fechas.
     */
    Fecha fecha = new Fecha(); 
    
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
            ClientesDAO ClientesDAO = new ClientesDAO();
            Obras obra = consultarObra(id);
            obra.setEstado(EstadoObra.DESARROLLO);
            obra.setFechaInicio(fecha.fechaAhora());
            // Se le suma la deuda a cliente al iniciar la obra
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() + obra.getCostoTotal());
            entityManager.merge(cliente);
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
            obra.setFechaFin(fecha.fechaAhora());
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
            ClientesDAO ClientesDAO = new ClientesDAO();
            Obras obra = consultarObra(id);
            obra.setDeuda(obra.getDeuda() - monto);
            if (obra.getDeuda() <= (float) 0) {
                obra = pagarObraObj(obra);
            }
            // Se le resta a la deuda de cliente igualmente
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() - monto);
            entityManager.merge(cliente);
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
            ClientesDAO ClientesDAO = new ClientesDAO();
            Obras obra = consultarObra(id);
            obra.setDeuda(obra.getDeuda() + monto);
            if (obra.getDeuda() > (float) 0) {
                obra = endeudarObraObj(obra);
            }
            // Se le suma a la deuda de cliente igualmente
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() + monto);
            entityManager.merge(cliente);
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
            ClientesDAO ClientesDAO = new ClientesDAO();
            Obras obra = consultarObra(id);
            obra.setCostoArranque(obra.getCostoArranque() + monto);
            obra.setCostoTotal(obra.getCostoTotal() + monto);
            obra.setDeuda(obra.getDeuda() + monto);
            if (obra.getDeuda() > (float) 0) {
                obra = endeudarObraObj(obra);
            }
            // Se le suma a la deuda de cliente igualmente
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() + monto);
            entityManager.merge(cliente);
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
            ClientesDAO ClientesDAO = new ClientesDAO();
            Obras obra = consultarObra(id);
            obra.setCostoArranque(obra.getCostoArranque() - monto);
            obra.setCostoTotal(obra.getCostoTotal() - monto);
            obra.setDeuda(obra.getDeuda() - monto);
            if (obra.getDeuda() <= (float) 0) {
                obra = pagarObraObj(obra);
            }
            // Se le resta a la deuda de cliente igualmente
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() - monto);
            entityManager.merge(cliente);
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
            ClientesDAO ClientesDAO = new ClientesDAO();
            Obras obra = consultarObra(id);
            obra.setInversion(obra.getInversion() + monto);
            obra.setCostoTotal(obra.getCostoTotal() + monto);
            obra.setDeuda(obra.getDeuda() + monto);
            if (obra.getDeuda() > (float) 0) {
                obra = endeudarObraObj(obra);
            }
            // Se le suma a la deuda de cliente igualmente
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() + monto);
            entityManager.merge(cliente);
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
            ClientesDAO ClientesDAO = new ClientesDAO();
            Obras obra = consultarObra(id);
            obra.setInversion(obra.getInversion() - monto);
            obra.setCostoTotal(obra.getCostoTotal() - monto);
            obra.setDeuda(obra.getDeuda() - monto);
            if (obra.getDeuda() <= (float) 0) {
                obra = pagarObraObj(obra);
            }
            // Se le resta a la deuda de cliente igualmente
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() - monto);
            entityManager.merge(cliente);
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
            // DAOs
            ObrerosDAO obrerosDAO = new ObrerosDAO();
            ObrasObreroDAO obrasObreroDAO = new ObrasObreroDAO();
            // Objetos
            Obreros obrero = obrerosDAO.consultarObrero(idObrero);
            Obras obra = consultarObra(id);
            // Se crea relación obras - obrero
            ObrasObrero obrasObrero = new ObrasObrero(obrero, obra);
            // Se registra la relación obra - obrero
            obrasObreroDAO.registrarObraObrero(obrasObrero);
            // Se agrega la relación obra - obrero a la obra en particular
            obra.getObreros().add(obrasObrero);
            // Se actualiza la obra
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Asigna 3 nuevos obreros a una obra, para que esta pueda comenzar
    public void asingarTresObrerosObra(Long id, Long idObrero1, Long idObrero2, Long idObrero3) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            ObrerosDAO obrerosDAO = new ObrerosDAO();
            ObrasObreroDAO obrasObreroDAO = new ObrasObreroDAO();
            // Objetos
            Obreros obrero1 = obrerosDAO.consultarObrero(idObrero1);
            Obreros obrero2 = obrerosDAO.consultarObrero(idObrero2);
            Obreros obrero3 = obrerosDAO.consultarObrero(idObrero3);
            Obras obra = consultarObra(id);
            // Se crean las relaciones obras - obrero
            ObrasObrero obrasObrero1 = new ObrasObrero(obrero1, obra);
            ObrasObrero obrasObrero2 = new ObrasObrero(obrero2, obra);
            ObrasObrero obrasObrero3 = new ObrasObrero(obrero3, obra);
            // Se registra la relación obra - obrero
            obrasObreroDAO.registrarObraObrero(obrasObrero1);
            obrasObreroDAO.registrarObraObrero(obrasObrero2);
            obrasObreroDAO.registrarObraObrero(obrasObrero3);
            // Se agrega la relación obra - obrero a la obra en particular
            obra.getObreros().add(obrasObrero1);
            obra.getObreros().add(obrasObrero2);
            obra.getObreros().add(obrasObrero3);
            // Se actualiza la obra
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Asigna un nuevo pago a una obra 
    public void agregarPagoObra(Long id, Long idPago) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            PagosDAO pagosDAO = new PagosDAO();
            ClientesDAO ClientesDAO = new ClientesDAO();
            // Objetos
            Pagos pago = pagosDAO.consultarPago(idPago);
            Obras obra = consultarObra(id);
            // Se agrega el pago a la obra en particular
            obra.getPagos().add(pago);
            // Se elimina a la deuda el pago realizado
            obra.setDeuda(obra.getDeuda() - pago.getMonto());
            if (obra.getDeuda() <= (float) 0) {
                obra = pagarObraObj(obra);
            }
            // Se actualiza la deuda de cliente restando el monto del pago asignado
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() - pago.getMonto());
            // Se actualiza la obra y cliente
            entityManager.merge(cliente);
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Asigna un nuevo plano a obra
    public void agregarPlanoObra(Long id, Long idPlano) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            PlanosDAO planosDAO = new PlanosDAO();
            // Objetos
            Planos plano = planosDAO.consultarPlano(idPlano);
            Obras obra = consultarObra(id);
            // Se agrega el plano a la obra en particular
            obra.getPlanos().add(plano);
            // Se actualiza la obra
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Asigna un nuevo permiso a obra
    public void agregarPermisoObra(Long id, Long idPermiso) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            PermisosDAO permisosDAO = new PermisosDAO();
            // Objetos
            Permisos permiso = permisosDAO.consultarPermiso(idPermiso);
            Obras obra = consultarObra(id);
            // Se agrega el plano a la obra en particular
            obra.getPermisos().add(permiso);
            // Se actualiza la obra
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Asigna una nueva ubicación a obra
    public void agregarUbicacionObra(Long id, Long idUbicacion) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            UbicacionesDAO ubicacionesDAO = new UbicacionesDAO();
            // Objetos
            Ubicaciones ubicacion = ubicacionesDAO.consultarUbicacion(idUbicacion);
            Obras obra = consultarObra(id);
            // Se agrega el plano a la obra en particular
            obra.getUbicaciones().add(ubicacion);
            // Se actualiza el estado de ubicacion a ocupada en casa de que sea 
            // un solar
            if (ubicacion.getTipo().equals(TipoUbicacion.SOLAR)) {
                ubicacionesDAO.ocuparUbicacion(idUbicacion);
            }
            // Se actualiza la obra
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }
    
    // Elimina un pago a una obra 
    public void eliminarPagoObra(Long id, Long idPago) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            PagosDAO PagosDAO = new PagosDAO();
            ClientesDAO ClientesDAO = new ClientesDAO();
            // Objetos
            Pagos pago = PagosDAO.consultarPago(idPago);
            Obras obra = consultarObra(id);
            // Se elimina el pago de la obra en particular
            obra.getPagos().remove(pago);
            // Se elimina el pago de la base de datos
            PagosDAO.eliminarPago(idPago);
            // Se agrega a la deuda el pago eliminado
            obra.setDeuda(obra.getDeuda() + pago.getMonto());
            if (obra.getDeuda() > (float) 0) {
                obra = endeudarObraObj(obra);
            }
            // Se actualiza la deuda de cliente sumando el monto del pago asignado
            Clientes cliente = ClientesDAO.consultarCliente(obra.getCliente().getId());
            cliente.setDeudaTotal(cliente.getDeudaTotal() + pago.getMonto());
            // Se actualiza la obra y cliente
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Elimina un plano de una obra en particular
    public void eliminarPlanoObra(Long id, Long idPlano) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            PlanosDAO planosDAO = new PlanosDAO();
            // Objetos
            Planos plano = planosDAO.consultarPlano(idPlano);
            Obras obra = consultarObra(id);
            // Se agrega el plano a la obra en particular
            obra.getPlanos().remove(plano);
            // Se actualiza la obra
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Elimina un permiso de una obra en particular
    public void eliminarPermisoObra(Long id, Long idPermiso) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            PermisosDAO permisosDAO = new PermisosDAO();
            // Objetos
            Permisos permiso = permisosDAO.consultarPermiso(idPermiso);
            Obras obra = consultarObra(id);
            // Se agrega el plano a la obra en particular
            obra.getPermisos().remove(permiso);
            // Se actualiza la obra
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
    }

    // Elimina una ubicación de una obra en particular
    public void eliminarUbicacionObra(Long id, Long idUbicacion) {
        if (verificarObra(id)) {
            entityManager.getTransaction().begin();
            // DAOs
            UbicacionesDAO ubicacionesDAO = new UbicacionesDAO();
            // Objetos
            Ubicaciones ubicacion = ubicacionesDAO.consultarUbicacion(idUbicacion);
            Obras obra = consultarObra(id);
            // Se agrega el plano a la obra en particular
            obra.getUbicaciones().remove(ubicacion);
            // Se actualiza la obra
            entityManager.merge(obra);
            entityManager.getTransaction().commit();
            entityManager.close();
        } else {
            throw new EntityNotFoundException("No se puede encontrar la obra con ID: " + id);
        }
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

    // Arroja una lista de obras que hayan sido iniciadas por cierto cliente 
    // dentro del periodo dado, tengan el estado dado, estén o no pagadas, hayan
    // costado un total o más dado
    public List<Obras> consultarObrasFechaInicio(Calendar periodoInicio, Calendar periodoFin, EstadoObra estado, Boolean estaPagada, Float costoTotal, Long clienteId) throws Exception {
        TypedQuery<Obras> query;
        // BUSQUEDA POR 6 CAMPOS
        if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 5 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 4 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 3 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 2 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 1 CAMPOS
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaInicio BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 0 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // No se llenó ningún campo
            String jpql = "SELECT o FROM Obras o";            
            query = entityManager.createQuery(jpql, Obras.class);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else {
            throw new Exception("No se pudo realizar la búsqueda dinámica de obras");
        }
    }
    
    // Arroja una lista de obras que hayan sido solicitadas por cierto cliente 
    // dentro del periodo dado, tengan el estado dado, estén o no pagadas, hayan
    // costado un total o más dado
    public List<Obras> consultarObrasFechaSolicitada(Calendar periodoInicio, Calendar periodoFin, EstadoObra estado, Boolean estaPagada, Float costoTotal, Long clienteId) throws Exception {
        TypedQuery<Obras> query;
        // BUSQUEDA POR 6 CAMPOS
        if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 5 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 4 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 3 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 2 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 1 CAMPOS
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaSolicitada BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 0 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // No se llenó ningún campo
            String jpql = "SELECT o FROM Obras o";            
            query = entityManager.createQuery(jpql, Obras.class);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else {
            throw new Exception("No se pudo realizar la búsqueda dinámica de obras");
        }
    }
    
    // Arroja una lista de obras que hayan sido terminadas por cierto cliente 
    // dentro del periodo dado, tengan el estado dado, estén o no pagadas, hayan
    // costado un total o más dado
    public List<Obras> consultarObrasFechaFin(Calendar periodoInicio, Calendar periodoFin, EstadoObra estado, Boolean estaPagada, Float costoTotal, Long clienteId) throws Exception {
        TypedQuery<Obras> query;
        // BUSQUEDA POR 6 CAMPOS
        if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 5 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 4 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, fin, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por estado, pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 3 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, fin, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, fin, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por inicio, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por fin, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por estado, pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por estado, pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por estado, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por pagada, costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 2 CAMPOS
        } else if (periodoInicio != null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, fin
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", periodoFin);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por inicio, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por inicio, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por fin, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por fin, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por estado, pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.estaPagada = :pagada";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por estado, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por estado, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por pagada, costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por pagada, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada "
                    + "AND o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId != null) { // Búsqueda por costo, cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId "
                    + "AND o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("costoTotal", costoTotal);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 1 CAMPOS
        } else if (periodoInicio != null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por inicio
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", periodoInicio);
            query.setParameter("periodoFin", new GregorianCalendar(3000, Calendar.JANUARY, 1));
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin != null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por fin
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.fechaFin BETWEEN :periodoInicio AND :periodoFin";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("periodoInicio", new GregorianCalendar(1600, Calendar.JANUARY, 1));
            query.setParameter("periodoFin", periodoFin);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado != null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por estado
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estado = :estado";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estado", estado);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada != null
                && costoTotal == null
                && clienteId == null) { // Búsqueda por pagada
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.estaPagada = :pagada";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("estaPagada", estaPagada);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal != null
                && clienteId == null) { // Búsqueda por costo
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.costoTotal >= :costoTotal";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("costoTotal", costoTotal);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId != null) { // Búsqueda por cliente
            String jpql = "SELECT o FROM Obras o WHERE "
                    + "o.cliente.id = :clienteId";
            query = entityManager.createQuery(jpql, Obras.class);
            query.setParameter("clienteId", clienteId);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
            // BÚSQUEDA POR 0 CAMPOS
        } else if (periodoInicio == null
                && periodoFin == null
                && estado == null
                && estaPagada == null
                && costoTotal == null
                && clienteId == null) { // No se llenó ningún campo
            String jpql = "SELECT o FROM Obras o";            
            query = entityManager.createQuery(jpql, Obras.class);
            List<Obras> obras = query.getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            return obras;
        } else {
            throw new Exception("No se pudo realizar la búsqueda dinámica de obras");
        }
    }
    
    // Métodos drivers para búsqueda dinámica
    public List<Obras> consultarObrasConEstado(EstadoObra estado) throws Exception {
        return this.consultarObrasFechaInicio(null, null, estado, null, null, null);
    }
    
    public List<Obras> consultarObrasConCostoMayorIgualA(Float monto) throws Exception {
        return this.consultarObrasFechaInicio(null, null, null, null, monto, null);
    }
    
    public List<Obras> consultarObrasNoPagadasConCostoMayorA(Float monto) throws Exception {
        return this.consultarObrasFechaInicio(null, null, null, false, monto, null);
    }
    
    public List<Obras> consultarObrasPagadasConCostoMayorA(Float monto) throws Exception {
        return this.consultarObrasFechaInicio(null, null, null, true, monto, null);
    }

    public List<Obras> consultarObrasPagadas() throws Exception {
        return this.consultarObrasFechaInicio(null, null, null, true, null, null);
    }
    
    public List<Obras> consultarObrasNoPagads() throws Exception {
        return this.consultarObrasFechaInicio(null, null, null, false, null, null);
    }
    
    public List<Obras> consultarTodasObras() throws Exception {
        return this.consultarObrasFechaInicio(null, null, null, null, null, null);
    }
}
