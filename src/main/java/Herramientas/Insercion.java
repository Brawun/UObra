/**
 * Insercion.java
 */
package Herramientas;

import DAOs.ClientesDAO;
import DAOs.JefesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrerosDAO;
import Dominio.Clientes;
import Dominio.Facturas;
import Dominio.Jefes;
import Dominio.Obras;
import Dominio.ObrasObrero;
import Dominio.Obreros;
import Dominio.Pagos;
import Dominio.Permisos;
import Dominio.Planos;
import Dominio.Ubicaciones;
import Enumeradores.Escala;
import Enumeradores.MetodoPago;
import Enumeradores.TipoPermiso;
import Enumeradores.TipoPlano;
import Enumeradores.TipoUbicacion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;

/**
 * Esta clase permite encapsular herramientas útiles a la hora de querer
 * implementar una inserción masiva en ciertas entidades.
 *
 * @author Brandon Figueroa Ugalde - ID: 00000233295
 * @author Guimel Naely Rubio Morillon - ID: 00000229324
 * @since Pruebas de Software Prof. María de los Ángeles Germán ITSON
 */
public class Insercion {

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
     * Método para eliminar toda la base de datos.
     *
     * @return Verdadero si se pudo eliminar la base datos falso en caso
     * contrario.
     * @throws Exception en caso que no se pueda eliminar la base de datos.
     */
    public boolean EliminarBaseDeDatos() throws Exception {
        try {
            entityManager.getTransaction().begin();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            // Eliminar todos los Clientes
            CriteriaDelete<Clientes> eliminarClientes = criteriaBuilder.createCriteriaDelete(Clientes.class);
            entityManager.createQuery(eliminarClientes).executeUpdate();
            // Eliminar todas las Facturas
            CriteriaDelete<Facturas> eliminarFacturas = criteriaBuilder.createCriteriaDelete(Facturas.class);
            entityManager.createQuery(eliminarFacturas).executeUpdate();
            // Eliminar todos los Jefes
            CriteriaDelete<Jefes> eliminarJefes = criteriaBuilder.createCriteriaDelete(Jefes.class);
            entityManager.createQuery(eliminarJefes).executeUpdate();
            // Eliminar todas las Obras
            CriteriaDelete<Obras> eliminarObras = criteriaBuilder.createCriteriaDelete(Obras.class);
            entityManager.createQuery(eliminarObras).executeUpdate();
            // Eliminar todas las ObrasObrero
            CriteriaDelete<ObrasObrero> eliminarObrasObrero = criteriaBuilder.createCriteriaDelete(ObrasObrero.class);
            entityManager.createQuery(eliminarObrasObrero).executeUpdate();
            // Eliminar todos los Obreros
            CriteriaDelete<Obreros> eliminarObreros = criteriaBuilder.createCriteriaDelete(Obreros.class);
            entityManager.createQuery(eliminarObreros).executeUpdate();
            // Eliminar todos los Pagos
            CriteriaDelete<Pagos> eliminarPagos = criteriaBuilder.createCriteriaDelete(Pagos.class);
            entityManager.createQuery(eliminarPagos).executeUpdate();
            // Eliminar todos los Permisos
            CriteriaDelete<Permisos> eliminarPermisos = criteriaBuilder.createCriteriaDelete(Permisos.class);
            entityManager.createQuery(eliminarPermisos).executeUpdate();
            // Eliminar todas las Planos
            CriteriaDelete<Planos> eliminarPlanos = criteriaBuilder.createCriteriaDelete(Planos.class);
            entityManager.createQuery(eliminarPlanos).executeUpdate();
            // Eliminar todos los Ubicaciones
            CriteriaDelete<Ubicaciones> eliminarUbicaciones = criteriaBuilder.createCriteriaDelete(Ubicaciones.class);
            entityManager.createQuery(eliminarUbicaciones).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Clientes.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaClientes() {
        try {
            entityManager.getTransaction().begin();
            // Se crean las entidades tipo cliente
            Clientes cliente1 = new Clientes("Brandon", "Figueroa", "Ugalde", "(644) 124-9359", "seguridad", "Brandon15");
            Clientes cliente2 = new Clientes("Naely", "Rubio", "Morillon", "(622) 173-2685", "frontend", "Miel2");
            Clientes cliente3 = new Clientes("Bruce", "Wayne", "Lopez", "(544) 152-5932", "nosoybatman", "Batiboy");
            Clientes cliente4 = new Clientes("Tony", "Stark", "Garnica", "(423) 504-9560", "yosoyironman", "Dinero");
            Clientes cliente5 = new Clientes("Scooby", "Doo", "Mendez", "(950) 194-5693", "scoobygalletas", "Misterio");
            Clientes cliente6 = new Clientes("Jhon", "Wick", "Torres", "(913) 530-2345", "miperrito", "WickJ");
            Clientes cliente7 = new Clientes("Benito", "Juárez", "Juárez", "(268) 469-3094", "500", "BenitoPresi");
            Clientes cliente8 = new Clientes("José", "José", "Guadalupe", "(104) 923-2412", "baladas", "JoseJose");
            Clientes cliente9 = new Clientes("Gabriel", "García", "López", "(503) 243-3452", "escribo", "MarquezG");
            Clientes cliente10 = new Clientes("Octavio", "Paz", "Guerra", "(353) 195-5895", "amorypaz", "PazO");
            // Se persisten las entidades tipo cliente
            entityManager.persist(cliente1);
            entityManager.persist(cliente2);
            entityManager.persist(cliente3);
            entityManager.persist(cliente4);
            entityManager.persist(cliente5);
            entityManager.persist(cliente6);
            entityManager.persist(cliente7);
            entityManager.persist(cliente8);
            entityManager.persist(cliente9);
            entityManager.persist(cliente10);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Jefes.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaJefes() {
        try {
            entityManager.getTransaction().begin();
            // Se crean las entidades tipo jefe
            Jefes jefe1 = new Jefes("Alejandra", "García", "Martínez", "(123) 456-7890", "eibq37", "SilverSwan");
            Jefes jefe2 = new Jefes("Carlos", "Hernández", "Sánchez", "(234) 567-8901", "hdsl42", "Bluejay");
            Jefes jefe3 = new Jefes("Sofía", "López", "González", "(345) 678-9012", "xckw21", "RedFox");
            Jefes jefe4 = new Jefes("Luis", "Gutiérrez", "Torres", "(456) 789-0123", "plrm95", "GreenFrog");
            Jefes jefe5 = new Jefes("Ana", "Ramírez", "Moreno", "(567) 890-1234", "fqde83", "YellowBird");
            Jefes jefe6 = new Jefes("Daniel", "Pérez", "Castro", "(678) 901-2345", "mngz14", "OrangeCat");
            Jefes jefe7 = new Jefes("Gabriela", "Ruiz", "Ramos", "(789) 012-3456", "uyjp56", "BlackPanther");
            Jefes jefe8 = new Jefes("David", "Castro", "González", "(890) 123-4567", "wvkt19", "PinkRose");
            Jefes jefe9 = new Jefes("Carmen", "Sánchez", "Reyes", "(901) 234-5678", "trfn28", "WhiteDove");
            Jefes jefe10 = new Jefes("Fernando", "Torres", "Ruiz", " (012) 345-6789", "sahy75", "GreyWolf");
            // Se persisten las entidades tipo jefe
            entityManager.persist(jefe1);
            entityManager.persist(jefe2);
            entityManager.persist(jefe3);
            entityManager.persist(jefe4);
            entityManager.persist(jefe5);
            entityManager.persist(jefe6);
            entityManager.persist(jefe7);
            entityManager.persist(jefe8);
            entityManager.persist(jefe9);
            entityManager.persist(jefe10);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Obreros.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaObreros() {
        try {
            entityManager.getTransaction().begin();
            // Se crean las entidades tipo obrero
            Obreros obrero1 = new Obreros("Sofía", "Hernández", "Ramírez", "(512) 555-1234", "lpcx62", "PurpleDragon", (float) 200.0);
            Obreros obrero2 = new Obreros("Diego", "Vargas", "García", "(512) 555-5678", "oevr47", "BrownBear", (float) 250.0);
            Obreros obrero3 = new Obreros("Ana", "Moreno", "González", "(512) 555-9012", "zdbm58", "GoldenEagle", (float) 300.0);
            Obreros obrero4 = new Obreros("Luisa", "Torres", "Gómez", "(512) 555-3456", "nkjt39", "SapphireTurtle", (float) 200.0);
            Obreros obrero5 = new Obreros("Gabriel", "Sánchez", "Ortiz", "(512) 555-7890", "gwxu51", "RubyHorse", (float) 280.0);
            Obreros obrero6 = new Obreros("Carla", "Núñez", "Reyes", "(512) 555-2345", "iyca26", "EmeraldLion", (float) 320.0);
            Obreros obrero7 = new Obreros("Juan", "Castro", "Cruz", "(512) 555-6789", "vqps93", "TopazOtter", (float) 230.0);
            Obreros obrero8 = new Obreros("Mariana", "Pérez", "Flores", "(512) 555-0123", "rulf84", "AmethystDeer", (float) 340.0);
            Obreros obrero9 = new Obreros("José", "Velázquez", "Días", "(512) 555-4567", "bxze73", "DiamondSeal", (float) 260.0);
            Obreros obrero10 = new Obreros("Paola", "Rodríguez", "Morales", "(512) 555-8901", "ktoh65", "OpalHawk", (float) 400.0);
            // Se persisten las entidades tipo obrero
            entityManager.persist(obrero1);
            entityManager.persist(obrero2);
            entityManager.persist(obrero3);
            entityManager.persist(obrero4);
            entityManager.persist(obrero5);
            entityManager.persist(obrero6);
            entityManager.persist(obrero7);
            entityManager.persist(obrero8);
            entityManager.persist(obrero9);
            entityManager.persist(obrero10);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Facturas.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaFacturas() {
        try {
            entityManager.getTransaction().begin();
            // Se crea el objeto DAO tipo jefes
            JefesDAO JefesDAO = new JefesDAO();
            // Se crean las entidades tipo factura
            Facturas factura1 = new Facturas((float) 1000.0, "Pago Materiales", JefesDAO.consultarJefe(1L));
            Facturas factura2 = new Facturas((float) 500.0, "Gasolina", JefesDAO.consultarJefe(1L));
            Facturas factura3 = new Facturas((float) 1800.0, "Nómina", JefesDAO.consultarJefe(1L));
            Facturas factura4 = new Facturas((float) 300.0, "Agua", JefesDAO.consultarJefe(2L));
            Facturas factura5 = new Facturas((float) 1200.0, "Gastos Mensuales", JefesDAO.consultarJefe(2L));
            Facturas factura6 = new Facturas((float) 800.0, "Servicio Troca", JefesDAO.consultarJefe(2L));
            Facturas factura7 = new Facturas((float) 750.0, "Luz", JefesDAO.consultarJefe(3L));
            Facturas factura8 = new Facturas((float) 600.0, "Mantenimiento", JefesDAO.consultarJefe(3L));
            Facturas factura9 = new Facturas((float) 1500.0, "Gasolina", JefesDAO.consultarJefe(3L));
            Facturas factura10 = new Facturas((float) 900.0, "Pago Materiales", JefesDAO.consultarJefe(4L));
            Facturas factura11 = new Facturas((float) 1000.0, "Pago Materiales", JefesDAO.consultarJefe(4L));
            Facturas factura12 = new Facturas((float) 500.0, "Gasolina", JefesDAO.consultarJefe(4L));
            Facturas factura13 = new Facturas((float) 1800.0, "Nómina", JefesDAO.consultarJefe(5L));
            Facturas factura14 = new Facturas((float) 300.0, "Agua", JefesDAO.consultarJefe(5L));
            Facturas factura15 = new Facturas((float) 1200.0, "Gastos Mensuales", JefesDAO.consultarJefe(6L));
            Facturas factura16 = new Facturas((float) 800.0, "Servicio Troca", JefesDAO.consultarJefe(6L));
            Facturas factura17 = new Facturas((float) 750.0, "Luz", JefesDAO.consultarJefe(7L));
            Facturas factura18 = new Facturas((float) 600.0, "Mantenimiento", JefesDAO.consultarJefe(8L));
            Facturas factura19 = new Facturas((float) 1500.0, "Gasolina", JefesDAO.consultarJefe(9L));
            Facturas factura20 = new Facturas((float) 900.0, "Pago Materiales", JefesDAO.consultarJefe(10L));
            // Se persisten las entidades tipo factura
            entityManager.persist(factura1);
            entityManager.persist(factura2);
            entityManager.persist(factura3);
            entityManager.persist(factura4);
            entityManager.persist(factura5);
            entityManager.persist(factura6);
            entityManager.persist(factura7);
            entityManager.persist(factura8);
            entityManager.persist(factura9);
            entityManager.persist(factura10);
            entityManager.persist(factura11);
            entityManager.persist(factura12);
            entityManager.persist(factura13);
            entityManager.persist(factura14);
            entityManager.persist(factura15);
            entityManager.persist(factura16);
            entityManager.persist(factura17);
            entityManager.persist(factura18);
            entityManager.persist(factura19);
            entityManager.persist(factura20);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Obras.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaObras() {
        try {
            entityManager.getTransaction().begin();
            // Se crea el objeto DAO tipo cliente
            ClientesDAO ClientesDAO = new ClientesDAO();
            // Se crean las entidades tipo obra
            Obras obra1 = new Obras((float) 10000.0, (float) 30000.0, "Hotel Centro", ClientesDAO.consultarCliente(1L));
            Obras obra2 = new Obras((float) 1500.0, (float) 12000.0, "Remodelación", ClientesDAO.consultarCliente(1L));
            Obras obra3 = new Obras((float) 1200.0, (float) 8000.0, "Estacionamiento", ClientesDAO.consultarCliente(1L));
            Obras obra4 = new Obras((float) 2000.0, (float) 4000.0, "Pared", ClientesDAO.consultarCliente(2L));
            Obras obra5 = new Obras((float) 3000.0, (float) 20000.0, "Casa", ClientesDAO.consultarCliente(2L));
            Obras obra6 = new Obras((float) 10000.0, (float) 40000.0, "Mansión", ClientesDAO.consultarCliente(2L));
            Obras obra7 = new Obras((float) 5000.0, (float) 35000.0, "Local comercial", ClientesDAO.consultarCliente(3L));
            Obras obra8 = new Obras((float) 20000.0, (float) 60000.0, "Fraccionamiento", ClientesDAO.consultarCliente(3L));
            Obras obra9 = new Obras((float) 12000.0, (float) 28000.0, "Plaza", ClientesDAO.consultarCliente(3L));
            Obras obra10 = new Obras((float) 10000.0, (float) 25000.0, "Hotel Avenida", ClientesDAO.consultarCliente(4L));
            Obras obra11 = new Obras((float) 10000.0, (float) 30000.0, "Hotel Centro", ClientesDAO.consultarCliente(4L));
            Obras obra12 = new Obras((float) 1500.0, (float) 12000.0, "Remodelación", ClientesDAO.consultarCliente(5L));
            Obras obra13 = new Obras((float) 1200.0, (float) 8000.0, "Estacionamiento", ClientesDAO.consultarCliente(6L));
            Obras obra14 = new Obras((float) 2000.0, (float) 4000.0, "Pared", ClientesDAO.consultarCliente(6L));
            Obras obra15 = new Obras((float) 3000.0, (float) 20000.0, "Casa", ClientesDAO.consultarCliente(7L));
            Obras obra16 = new Obras((float) 10000.0, (float) 40000.0, "Mansión", ClientesDAO.consultarCliente(7L));
            Obras obra17 = new Obras((float) 5000.0, (float) 35000.0, "Local comercial", ClientesDAO.consultarCliente(8L));
            Obras obra18 = new Obras((float) 20000.0, (float) 60000.0, "Fraccionamiento", ClientesDAO.consultarCliente(8L));
            Obras obra19 = new Obras((float) 12000.0, (float) 28000.0, "Plaza", ClientesDAO.consultarCliente(9L));
            Obras obra20 = new Obras((float) 10000.0, (float) 25000.0, "Hotel Avenida", ClientesDAO.consultarCliente(10L));
            // Se persisten las entidades tipo obra
            entityManager.persist(obra1);
            entityManager.persist(obra2);
            entityManager.persist(obra3);
            entityManager.persist(obra4);
            entityManager.persist(obra5);
            entityManager.persist(obra6);
            entityManager.persist(obra7);
            entityManager.persist(obra8);
            entityManager.persist(obra9);
            entityManager.persist(obra10);
            entityManager.persist(obra11);
            entityManager.persist(obra12);
            entityManager.persist(obra13);
            entityManager.persist(obra14);
            entityManager.persist(obra15);
            entityManager.persist(obra16);
            entityManager.persist(obra17);
            entityManager.persist(obra18);
            entityManager.persist(obra19);
            entityManager.persist(obra20);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de ObrasObrero.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaObrasObrero() {
        try {
            entityManager.getTransaction().begin();
            // Se crea el objeto DAO tipo obra y obrero
            ObrasDAO ObrasDAO = new ObrasDAO();
            ObrerosDAO ObrerosDAO = new ObrerosDAO();
            // Se crean las entidades tipo obraObrero
            // Obrero 1
            ObrasObrero obraObrero1 = new ObrasObrero(ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(1L));
            ObrasObrero obraObrero2 = new ObrasObrero(ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(2L));
            ObrasObrero obraObrero3 = new ObrasObrero(ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(3L));
            ObrasObrero obraObrero4 = new ObrasObrero(ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(4L));
            ObrasObrero obraObrero5 = new ObrasObrero(ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(5L));
            ObrasObrero obraObrero6 = new ObrasObrero(ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(6L));
            ObrasObrero obraObreroInactiva1 = new ObrasObrero(ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(7L), false);
            ObrasObrero obraObreroInactiva2 = new ObrasObrero(ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(8L), false);
            // Obrero 2
            ObrasObrero obraObrero7 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(1L));
            ObrasObrero obraObrero8 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(2L));
            ObrasObrero obraObrero9 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(3L));
            ObrasObrero obraObrero10 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(4L));
            ObrasObrero obraObrero11 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(5L));
            ObrasObrero obraObrero12 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(6L));
            ObrasObrero obraObreroInactiva3 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(7L), false);
            ObrasObrero obraObreroInactiva4 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(8L), false);
            // Obrero 3
            ObrasObrero obraObrero13 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(1L));
            ObrasObrero obraObrero14 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(2L));
            ObrasObrero obraObrero15 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(3L));
            ObrasObrero obraObrero16 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(4L));
            ObrasObrero obraObrero17 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(5L));
            ObrasObrero obraObrero18 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(6L));
            ObrasObrero obraObreroInactiva5 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(7L), false);
            ObrasObrero obraObreroInactiva6 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(8L), false);
            // Obrero 4
            ObrasObrero obraObrero19 = new ObrasObrero(ObrerosDAO.consultarObrero(4L), ObrasDAO.consultarObra(7L));
            ObrasObrero obraObrero20 = new ObrasObrero(ObrerosDAO.consultarObrero(4L), ObrasDAO.consultarObra(8L));
            ObrasObrero obraObrero21 = new ObrasObrero(ObrerosDAO.consultarObrero(4L), ObrasDAO.consultarObra(9L));
            ObrasObrero obraObrero22 = new ObrasObrero(ObrerosDAO.consultarObrero(4L), ObrasDAO.consultarObra(10L));
            ObrasObrero obraObrero23 = new ObrasObrero(ObrerosDAO.consultarObrero(4L), ObrasDAO.consultarObra(11L));
            ObrasObrero obraObrero24 = new ObrasObrero(ObrerosDAO.consultarObrero(4L), ObrasDAO.consultarObra(12L));
            // Obrero 5
            ObrasObrero obraObrero25 = new ObrasObrero(ObrerosDAO.consultarObrero(5L), ObrasDAO.consultarObra(7L));
            ObrasObrero obraObrero26 = new ObrasObrero(ObrerosDAO.consultarObrero(5L), ObrasDAO.consultarObra(8L));
            ObrasObrero obraObrero27 = new ObrasObrero(ObrerosDAO.consultarObrero(5L), ObrasDAO.consultarObra(9L));
            ObrasObrero obraObrero28 = new ObrasObrero(ObrerosDAO.consultarObrero(5L), ObrasDAO.consultarObra(10L));
            ObrasObrero obraObrero29 = new ObrasObrero(ObrerosDAO.consultarObrero(5L), ObrasDAO.consultarObra(11L));
            ObrasObrero obraObrero30 = new ObrasObrero(ObrerosDAO.consultarObrero(5L), ObrasDAO.consultarObra(12L));
            // Obrero 6
            ObrasObrero obraObrero31 = new ObrasObrero(ObrerosDAO.consultarObrero(6L), ObrasDAO.consultarObra(7L));
            ObrasObrero obraObrero32 = new ObrasObrero(ObrerosDAO.consultarObrero(6L), ObrasDAO.consultarObra(8L));
            ObrasObrero obraObrero33 = new ObrasObrero(ObrerosDAO.consultarObrero(6L), ObrasDAO.consultarObra(9L));
            ObrasObrero obraObrero34 = new ObrasObrero(ObrerosDAO.consultarObrero(6L), ObrasDAO.consultarObra(10L));
            ObrasObrero obraObrero35 = new ObrasObrero(ObrerosDAO.consultarObrero(6L), ObrasDAO.consultarObra(11L));
            ObrasObrero obraObrero36 = new ObrasObrero(ObrerosDAO.consultarObrero(6L), ObrasDAO.consultarObra(12L));
            // Se persisten las entidades tipo obraObrero
            entityManager.persist(obraObrero1);
            entityManager.persist(obraObrero2);
            entityManager.persist(obraObrero3);
            entityManager.persist(obraObrero4);
            entityManager.persist(obraObrero5);
            entityManager.persist(obraObrero6);
            entityManager.persist(obraObrero7);
            entityManager.persist(obraObrero8);
            entityManager.persist(obraObrero9);
            entityManager.persist(obraObrero10);
            entityManager.persist(obraObrero11);
            entityManager.persist(obraObrero12);
            entityManager.persist(obraObrero13);
            entityManager.persist(obraObrero14);
            entityManager.persist(obraObrero15);
            entityManager.persist(obraObrero16);
            entityManager.persist(obraObrero17);
            entityManager.persist(obraObrero18);
            entityManager.persist(obraObrero19);
            entityManager.persist(obraObrero20);
            entityManager.persist(obraObrero21);
            entityManager.persist(obraObrero22);
            entityManager.persist(obraObrero23);
            entityManager.persist(obraObrero24);
            entityManager.persist(obraObrero25);
            entityManager.persist(obraObrero26);
            entityManager.persist(obraObrero27);
            entityManager.persist(obraObrero28);
            entityManager.persist(obraObrero29);
            entityManager.persist(obraObrero30);
            entityManager.persist(obraObrero31);
            entityManager.persist(obraObrero32);
            entityManager.persist(obraObrero33);
            entityManager.persist(obraObrero34);
            entityManager.persist(obraObrero35);
            entityManager.persist(obraObrero36);
            entityManager.persist(obraObreroInactiva1);
            entityManager.persist(obraObreroInactiva2);
            entityManager.persist(obraObreroInactiva3);
            entityManager.persist(obraObreroInactiva4);
            entityManager.persist(obraObreroInactiva5);
            entityManager.persist(obraObreroInactiva6);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Pagos.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaPagos() {
        try {
            entityManager.getTransaction().begin();
            // Se crea el objeto DAO tipo cliente, obrero y obra
            ClientesDAO ClientesDAO = new ClientesDAO();
            ObrerosDAO ObrerosDAO = new ObrerosDAO();
            ObrasDAO ObrasDAO = new ObrasDAO();
            // Se crean las entidades tipo pago
            Pagos pago1 = new Pagos((float) 0.0, MetodoPago.CREDITO, ObrasDAO.consultarObra(1L), ClientesDAO.consultarCliente(1L));
            Pagos pago2 = new Pagos((float) 0.0, MetodoPago.CREDITO, ObrasDAO.consultarObra(2L), ClientesDAO.consultarCliente(2L));
            Pagos pago3 = new Pagos((float) 0.0, MetodoPago.CREDITO, ObrasDAO.consultarObra(3L), ClientesDAO.consultarCliente(3L));
            Pagos pago4 = new Pagos((float) 0.0, MetodoPago.DEBITO, ObrasDAO.consultarObra(4L), ClientesDAO.consultarCliente(4L));
            Pagos pago5 = new Pagos((float) 0.0, MetodoPago.DEBITO, ObrasDAO.consultarObra(5L), ClientesDAO.consultarCliente(5L));
            Pagos pago6 = new Pagos((float) 0.0, MetodoPago.DEBITO, ObrasDAO.consultarObra(6L), ClientesDAO.consultarCliente(6L));
            // En caso que el método de pago del pago sea por efectivo se registra a que obrero se le entregó el pago
            Pagos pago7 = new Pagos((float) 0.0, MetodoPago.EFECTIVO, ObrerosDAO.consultarObrero(1L), ObrasDAO.consultarObra(7L), ClientesDAO.consultarCliente(7L));
            Pagos pago8 = new Pagos((float) 0.0, MetodoPago.EFECTIVO, ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(8L), ClientesDAO.consultarCliente(8L));
            Pagos pago9 = new Pagos((float) 0.0, MetodoPago.EFECTIVO, ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(9L), ClientesDAO.consultarCliente(9L));
            Pagos pago10 = new Pagos((float) 0.0, MetodoPago.EFECTIVO, ObrerosDAO.consultarObrero(4L), ObrasDAO.consultarObra(10L), ClientesDAO.consultarCliente(10L));
            // Se persisten las entidades tipo pago
            entityManager.persist(pago1);
            entityManager.persist(pago2);
            entityManager.persist(pago3);
            entityManager.persist(pago4);
            entityManager.persist(pago5);
            entityManager.persist(pago6);
            entityManager.persist(pago7);
            entityManager.persist(pago8);
            entityManager.persist(pago9);
            entityManager.persist(pago10);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Permisos.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaPermisos() {
        try {
            entityManager.getTransaction().begin();
            // Se crea el objeto DAO tipo jefe
            JefesDAO JefesDAO = new JefesDAO();
            // Se crean las entidades tipo permiso
            Permisos permiso1 = new Permisos("152321", TipoPermiso.INICIACION, fecha.crearFecha(15, 10, 2022), JefesDAO.consultarJefe(1L));
            Permisos permiso2 = new Permisos("243112", TipoPermiso.INICIACION, fecha.crearFecha(20, 2, 2022), JefesDAO.consultarJefe(1L));
            Permisos permiso3 = new Permisos("513353", TipoPermiso.INICIACION, fecha.crearFecha(30, 1, 2022), JefesDAO.consultarJefe(1L));
            Permisos permiso4 = new Permisos("533113", TipoPermiso.INICIACION, fecha.crearFecha(24, 12, 2022), JefesDAO.consultarJefe(2L));
            Permisos permiso5 = new Permisos("124141", TipoPermiso.INICIACION, fecha.crearFecha(17, 5, 2002), JefesDAO.consultarJefe(2L));
            Permisos permiso6 = new Permisos("532313", TipoPermiso.FINALIZACION, fecha.crearFecha(19, 8, 2022), JefesDAO.consultarJefe(1L));
            Permisos permiso7 = new Permisos("795311", TipoPermiso.FINALIZACION, fecha.crearFecha(4, 4, 2022), JefesDAO.consultarJefe(1L));
            Permisos permiso8 = new Permisos("462244", TipoPermiso.FINALIZACION, fecha.crearFecha(8, 6, 2022), JefesDAO.consultarJefe(1L));
            Permisos permiso9 = new Permisos("233295", TipoPermiso.FINALIZACION, fecha.crearFecha(19, 7, 2022), JefesDAO.consultarJefe(2L));
            Permisos permiso10 = new Permisos("204233", TipoPermiso.FINALIZACION, fecha.crearFecha(22, 3, 2022), JefesDAO.consultarJefe(2L));
            // Se persisten las entidades tipo permiso
            entityManager.persist(permiso1);
            entityManager.persist(permiso2);
            entityManager.persist(permiso3);
            entityManager.persist(permiso4);
            entityManager.persist(permiso5);
            entityManager.persist(permiso6);
            entityManager.persist(permiso7);
            entityManager.persist(permiso8);
            entityManager.persist(permiso9);
            entityManager.persist(permiso10);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Planos.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaPlanos() {
        try {
            entityManager.getTransaction().begin();
            // Se crea el objeto DAO tipo jefe
            JefesDAO JefesDAO = new JefesDAO();
            // Se crean las entidades tipo plano
            // Permisos de desague 
            Planos plano1 = new Planos("123424", TipoPlano.DESAGUE, Escala.UNO_100, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(1L));
            Planos plano2 = new Planos("543233", TipoPlano.DESAGUE, Escala.UNO_1000, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(2L));
            // Permisos de ejecucion
            Planos plano3 = new Planos("242312", TipoPlano.EJECUCION, Escala.UNO_2000, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(1L));
            Planos plano4 = new Planos("594203", TipoPlano.EJECUCION, Escala.UNO_2500, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(2L));
            // Permisos de electricidad
            Planos plano5 = new Planos("423244", TipoPlano.ELECTRICO, Escala.UNO_500, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(1L));
            Planos plano6 = new Planos("491302", TipoPlano.ELECTRICO, Escala.UNO_5000, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(2L));
            // Permisos de ubicacion
            Planos plano7 = new Planos("924002", TipoPlano.UBICACION, Escala.UNO_100, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(1L));
            Planos plano8 = new Planos("845024", TipoPlano.UBICACION, Escala.UNO_500, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(1L));
            Planos plano9 = new Planos("723933", TipoPlano.UBICACION, Escala.UNO_1000, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(1L));
            Planos plano10 = new Planos("499222", TipoPlano.UBICACION, Escala.UNO_2500, fecha.crearFecha(15, 10, 2002), JefesDAO.consultarJefe(2L));
            // Se persisten las entidades tipo plano
            entityManager.persist(plano1);
            entityManager.persist(plano2);
            entityManager.persist(plano3);
            entityManager.persist(plano4);
            entityManager.persist(plano5);
            entityManager.persist(plano6);
            entityManager.persist(plano7);
            entityManager.persist(plano8);
            entityManager.persist(plano9);
            entityManager.persist(plano10);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método para hacer una inserción masiva de Ubicaciones.
     *
     * @return Verdadero si se pudo realizar la inserción falso en caso
     * contrario.
     */
    public boolean InsercionMasivaUbicaciones() {
        try {
            entityManager.getTransaction().begin();
            // Se crea el objeto DAO tipo cliente
            ClientesDAO ClientesDAO = new ClientesDAO();
            // Se crean las entidades tipo ubicacion
            Ubicaciones ubicacion1 = new Ubicaciones("Calle del Olivo, 23, 29004 Málaga, España", TipoUbicacion.TERRENO, (float) 120.0, (float) 110.0, ClientesDAO.consultarCliente(1L));
            Ubicaciones ubicacion2 = new Ubicaciones("1830 SW 17th Ave, Miami, FL 33145, Estados Unidos", TipoUbicacion.TERRENO, (float) 150.0, (float) 140.0, ClientesDAO.consultarCliente(1L));
            Ubicaciones ubicacion3 = new Ubicaciones("27A, Jalan SS 4d/2, Ss 4d, 47301 Petaling Jaya, Selangor, Malasia", TipoUbicacion.TERRENO, (float) 200.0, (float) 200.0, ClientesDAO.consultarCliente(1L));
            Ubicaciones ubicacion4 = new Ubicaciones("1207 N Charles St, Baltimore, MD 21201, Estados Unidos", TipoUbicacion.TERRENO, (float) 180.0, (float) 150.0, ClientesDAO.consultarCliente(2L));
            Ubicaciones ubicacion5 = new Ubicaciones("5-5-1 Tsukumo, Sanda, Hyogo 669-1335, Japón", TipoUbicacion.TERRENO, (float) 110.0, (float) 220.0, ClientesDAO.consultarCliente(2L));
            Ubicaciones ubicacion6 = new Ubicaciones("111 W 57th St, New York, NY 10019, Estados Unidos", TipoUbicacion.SOLAR, (float) 240.0, (float) 210.0, ClientesDAO.consultarCliente(2L));
            Ubicaciones ubicacion7 = new Ubicaciones("15 Sackville St, Kew, Victoria 3101, Australia", TipoUbicacion.SOLAR, (float) 300.0, (float) 290.0, ClientesDAO.consultarCliente(3L));
            Ubicaciones ubicacion8 = new Ubicaciones("8-10 Darling Dr, Sydney NSW 2000, Australia", TipoUbicacion.SOLAR, (float) 270.0, (float) 250.0, ClientesDAO.consultarCliente(3L));
            Ubicaciones ubicacion9 = new Ubicaciones("Calle 12 de Octubre, #52, Ciudad de México, México", TipoUbicacion.SOLAR, (float) 260.0, (float) 240.0, ClientesDAO.consultarCliente(4L));
            Ubicaciones ubicacion10 = new Ubicaciones("1203 Paseo de la Reforma, Ciudad de México, México", TipoUbicacion.SOLAR, (float) 280.0, (float) 300.0, ClientesDAO.consultarCliente(4L));
            // Se persisten las entidades tipo ubicacion
            entityManager.persist(ubicacion1);
            entityManager.persist(ubicacion2);
            entityManager.persist(ubicacion3);
            entityManager.persist(ubicacion4);
            entityManager.persist(ubicacion5);
            entityManager.persist(ubicacion6);
            entityManager.persist(ubicacion7);
            entityManager.persist(ubicacion8);
            entityManager.persist(ubicacion9);
            entityManager.persist(ubicacion10);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
