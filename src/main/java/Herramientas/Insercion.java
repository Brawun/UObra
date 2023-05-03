/**
 * Insercion.java
 */
package Herramientas;

import DAOs.ClientesDAO;
import DAOs.FacturasDAO;
import DAOs.JefesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrasObreroDAO;
import DAOs.ObrerosDAO;
import DAOs.PagosDAO;
import DAOs.PermisosDAO;
import DAOs.PlanosDAO;
import DAOs.UbicacionesDAO;
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
     * Método para hacer una inserción masiva de Clientes.
     */
    public void InsecionMasivaClientes() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo cliente
        ClientesDAO ClientesDAO = new ClientesDAO();
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
        ClientesDAO.registrarCliente(cliente1);
        ClientesDAO.registrarCliente(cliente2);
        ClientesDAO.registrarCliente(cliente3);
        ClientesDAO.registrarCliente(cliente4);
        ClientesDAO.registrarCliente(cliente5);
        ClientesDAO.registrarCliente(cliente6);
        ClientesDAO.registrarCliente(cliente7);
        ClientesDAO.registrarCliente(cliente8);
        ClientesDAO.registrarCliente(cliente9);
        ClientesDAO.registrarCliente(cliente10);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de Jefes.
     */
    public void InsecionMasivaJefes() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo jefe
        JefesDAO JefesDAO = new JefesDAO();
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
        JefesDAO.registrarJefe(jefe1);
        JefesDAO.registrarJefe(jefe2);
        JefesDAO.registrarJefe(jefe3);
        JefesDAO.registrarJefe(jefe4);
        JefesDAO.registrarJefe(jefe5);
        JefesDAO.registrarJefe(jefe6);
        JefesDAO.registrarJefe(jefe7);
        JefesDAO.registrarJefe(jefe8);
        JefesDAO.registrarJefe(jefe9);
        JefesDAO.registrarJefe(jefe10);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de Obreros.
     */
    public void InsecionMasivaObreros() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo obrero
        ObrerosDAO ObrerosDAO = new ObrerosDAO();
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
        ObrerosDAO.registrarObrero(obrero1);
        ObrerosDAO.registrarObrero(obrero2);
        ObrerosDAO.registrarObrero(obrero3);
        ObrerosDAO.registrarObrero(obrero4);
        ObrerosDAO.registrarObrero(obrero5);
        ObrerosDAO.registrarObrero(obrero6);
        ObrerosDAO.registrarObrero(obrero7);
        ObrerosDAO.registrarObrero(obrero8);
        ObrerosDAO.registrarObrero(obrero9);
        ObrerosDAO.registrarObrero(obrero10);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de Facturas.
     */
    public void InsecionMasivaFacturas() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo factura y jefes
        FacturasDAO FacturasDAO = new FacturasDAO();
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
        FacturasDAO.registrarFactura(factura1);
        FacturasDAO.registrarFactura(factura2);
        FacturasDAO.registrarFactura(factura3);
        FacturasDAO.registrarFactura(factura4);
        FacturasDAO.registrarFactura(factura5);
        FacturasDAO.registrarFactura(factura6);
        FacturasDAO.registrarFactura(factura7);
        FacturasDAO.registrarFactura(factura8);
        FacturasDAO.registrarFactura(factura9);
        FacturasDAO.registrarFactura(factura10);
        FacturasDAO.registrarFactura(factura11);
        FacturasDAO.registrarFactura(factura12);
        FacturasDAO.registrarFactura(factura13);
        FacturasDAO.registrarFactura(factura14);
        FacturasDAO.registrarFactura(factura15);
        FacturasDAO.registrarFactura(factura16);
        FacturasDAO.registrarFactura(factura17);
        FacturasDAO.registrarFactura(factura18);
        FacturasDAO.registrarFactura(factura19);
        FacturasDAO.registrarFactura(factura20);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de Obras.
     */
    public void InsecionMasivaObras() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo obra y cliente
        ObrasDAO ObrasDAO = new ObrasDAO();
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
        ObrasDAO.registrarObra(obra1);
        ObrasDAO.registrarObra(obra2);
        ObrasDAO.registrarObra(obra3);
        ObrasDAO.registrarObra(obra4);
        ObrasDAO.registrarObra(obra5);
        ObrasDAO.registrarObra(obra6);
        ObrasDAO.registrarObra(obra7);
        ObrasDAO.registrarObra(obra8);
        ObrasDAO.registrarObra(obra9);
        ObrasDAO.registrarObra(obra10);
        ObrasDAO.registrarObra(obra11);
        ObrasDAO.registrarObra(obra12);
        ObrasDAO.registrarObra(obra13);
        ObrasDAO.registrarObra(obra14);
        ObrasDAO.registrarObra(obra15);
        ObrasDAO.registrarObra(obra16);
        ObrasDAO.registrarObra(obra17);
        ObrasDAO.registrarObra(obra18);
        ObrasDAO.registrarObra(obra19);
        ObrasDAO.registrarObra(obra20);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de ObrasObrero.
     */
    public void InsecionMasivaObrasObrero() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo obraObrero, obra y obrero
        ObrasObreroDAO ObrasObreroDAO = new ObrasObreroDAO();
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
        // Obrero 2
        ObrasObrero obraObrero7 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(1L));
        ObrasObrero obraObrero8 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(2L));
        ObrasObrero obraObrero9 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(3L));
        ObrasObrero obraObrero10 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(4L));
        ObrasObrero obraObrero11 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(5L));
        ObrasObrero obraObrero12 = new ObrasObrero(ObrerosDAO.consultarObrero(2L), ObrasDAO.consultarObra(6L));
        // Obrero 3
        ObrasObrero obraObrero13 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(1L));
        ObrasObrero obraObrero14 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(2L));
        ObrasObrero obraObrero15 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(3L));
        ObrasObrero obraObrero16 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(4L));
        ObrasObrero obraObrero17 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(5L));
        ObrasObrero obraObrero18 = new ObrasObrero(ObrerosDAO.consultarObrero(3L), ObrasDAO.consultarObra(6L));
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
        ObrasObreroDAO.registrarObraObrero(obraObrero1);
        ObrasObreroDAO.registrarObraObrero(obraObrero2);
        ObrasObreroDAO.registrarObraObrero(obraObrero3);
        ObrasObreroDAO.registrarObraObrero(obraObrero4);
        ObrasObreroDAO.registrarObraObrero(obraObrero5);
        ObrasObreroDAO.registrarObraObrero(obraObrero6);
        ObrasObreroDAO.registrarObraObrero(obraObrero7);
        ObrasObreroDAO.registrarObraObrero(obraObrero8);
        ObrasObreroDAO.registrarObraObrero(obraObrero9);
        ObrasObreroDAO.registrarObraObrero(obraObrero10);
        ObrasObreroDAO.registrarObraObrero(obraObrero11);
        ObrasObreroDAO.registrarObraObrero(obraObrero12);
        ObrasObreroDAO.registrarObraObrero(obraObrero13);
        ObrasObreroDAO.registrarObraObrero(obraObrero14);
        ObrasObreroDAO.registrarObraObrero(obraObrero15);
        ObrasObreroDAO.registrarObraObrero(obraObrero16);
        ObrasObreroDAO.registrarObraObrero(obraObrero17);
        ObrasObreroDAO.registrarObraObrero(obraObrero18);
        ObrasObreroDAO.registrarObraObrero(obraObrero19);
        ObrasObreroDAO.registrarObraObrero(obraObrero20);
        ObrasObreroDAO.registrarObraObrero(obraObrero21);
        ObrasObreroDAO.registrarObraObrero(obraObrero22);
        ObrasObreroDAO.registrarObraObrero(obraObrero23);
        ObrasObreroDAO.registrarObraObrero(obraObrero24);
        ObrasObreroDAO.registrarObraObrero(obraObrero25);
        ObrasObreroDAO.registrarObraObrero(obraObrero26);
        ObrasObreroDAO.registrarObraObrero(obraObrero27);
        ObrasObreroDAO.registrarObraObrero(obraObrero28);
        ObrasObreroDAO.registrarObraObrero(obraObrero29);
        ObrasObreroDAO.registrarObraObrero(obraObrero30);
        ObrasObreroDAO.registrarObraObrero(obraObrero31);
        ObrasObreroDAO.registrarObraObrero(obraObrero32);
        ObrasObreroDAO.registrarObraObrero(obraObrero33);
        ObrasObreroDAO.registrarObraObrero(obraObrero34);
        ObrasObreroDAO.registrarObraObrero(obraObrero35);
        ObrasObreroDAO.registrarObraObrero(obraObrero36);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de Pagos.
     */
    public void InsecionMasivaPagos() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo pago, cliente, obrero y obra
        PagosDAO PagosDAO = new PagosDAO();
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
        PagosDAO.registrarPago(pago1);
        PagosDAO.registrarPago(pago2);
        PagosDAO.registrarPago(pago3);
        PagosDAO.registrarPago(pago4);
        PagosDAO.registrarPago(pago5);
        PagosDAO.registrarPago(pago6);
        PagosDAO.registrarPago(pago7);
        PagosDAO.registrarPago(pago8);
        PagosDAO.registrarPago(pago9);
        PagosDAO.registrarPago(pago10);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de Permisos.
     */
    public void InsecionMasivaPermisos() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo permiso y jefe
        PermisosDAO PermisosDAO = new PermisosDAO();
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
        PermisosDAO.registrarPermiso(permiso1);
        PermisosDAO.registrarPermiso(permiso2);
        PermisosDAO.registrarPermiso(permiso3);
        PermisosDAO.registrarPermiso(permiso4);
        PermisosDAO.registrarPermiso(permiso5);
        PermisosDAO.registrarPermiso(permiso6);
        PermisosDAO.registrarPermiso(permiso7);
        PermisosDAO.registrarPermiso(permiso8);
        PermisosDAO.registrarPermiso(permiso9);
        PermisosDAO.registrarPermiso(permiso10);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de Planos.
     */
    public void InsecionMasivaPlanos() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo plano y jefe
        PlanosDAO PlanosDAO = new PlanosDAO();
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
        PlanosDAO.registrarPlano(plano1);
        PlanosDAO.registrarPlano(plano2);
        PlanosDAO.registrarPlano(plano3);
        PlanosDAO.registrarPlano(plano4);
        PlanosDAO.registrarPlano(plano5);
        PlanosDAO.registrarPlano(plano6);
        PlanosDAO.registrarPlano(plano7);
        PlanosDAO.registrarPlano(plano8);
        PlanosDAO.registrarPlano(plano9);
        PlanosDAO.registrarPlano(plano10);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    /**
     * Método para hacer una inserción masiva de Ubicaciones.
     */
    public void InsecionMasivaUbicaciones() {
        entityManager.getTransaction().begin();
        // Se crea el objeto DAO tipo ubicacion y cliente
        UbicacionesDAO UbicacionesDAO = new UbicacionesDAO();
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
        UbicacionesDAO.registrarUbicacion(ubicacion1);
        UbicacionesDAO.registrarUbicacion(ubicacion2);
        UbicacionesDAO.registrarUbicacion(ubicacion3);
        UbicacionesDAO.registrarUbicacion(ubicacion4);
        UbicacionesDAO.registrarUbicacion(ubicacion5);
        UbicacionesDAO.registrarUbicacion(ubicacion6);
        UbicacionesDAO.registrarUbicacion(ubicacion7);
        UbicacionesDAO.registrarUbicacion(ubicacion8);
        UbicacionesDAO.registrarUbicacion(ubicacion9);
        UbicacionesDAO.registrarUbicacion(ubicacion10);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
