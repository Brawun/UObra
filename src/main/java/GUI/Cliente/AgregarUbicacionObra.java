/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Cliente;

import DAOs.ClientesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrerosDAO;
import DAOs.PagosDAO;
import DAOs.UbicacionesDAO;
import Dominio.Clientes;
import Dominio.Obras;
import Dominio.Ubicaciones;
import Enumeradores.EstadoObra;
import Enumeradores.TipoUbicacion;
import Herramientas.Fecha;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 52644
 */
public class AgregarUbicacionObra extends javax.swing.JFrame {

    // Atributos
    Clientes cliente = new Clientes();
    Fecha fecha = new Fecha();
    ClientesDAO ClientesDAO = new ClientesDAO();
    ObrasDAO ObrasDAO = new ObrasDAO();
    UbicacionesDAO UbicacionesDAO = new UbicacionesDAO();
    ObrerosDAO ObrerosDAO = new ObrerosDAO();
    PagosDAO PagosDAO = new PagosDAO();
    Validadores valido = new Validadores();
    Obras obra;
    Ubicaciones ubicacion;
    List<Ubicaciones> listaUbicaciones;
    List<Obras> listaObras;

    /**
     * Creates new form AgregarUbicacionObra
     */
    public AgregarUbicacionObra(Clientes cliente) {
        this.cliente = cliente;
        this.obra = null;
        this.ubicacion = null;
        UIManager.put("OptionPane.yesButtonText", "Aceptar");
        UIManager.put("OptionPane.noButtonText", "Cancelar");
        initComponents();
        this.btnAceptarSeleccionUbicaciones.setEnabled(false);
        this.btnAceptarSeleccionObras.setEnabled(false);
        this.btnAgregar.setEnabled(false);
        new Icono().insertarIcono(this);
        this.txtCostoTotal.setText("1000.0");
        this.cbxAccion.setSelectedItem("Elija uno...");
        this.cbxEstado.setSelectedItem("Elija uno");
        this.lblInsertarArea.setText("");
        this.lblInsertarDireccion.setText("");
        this.lblInsertarFecha.setText("");
        this.lblInsertarIDUbicacion.setText("");
        this.lblInsertarDeuda.setText("");
        this.lblInsertarFechaSolicitada.setText("");
        this.lblInsertarIDObra.setText("");
        this.lblInsertarNombre.setText("");
        this.periodoFinal.setCalendar(null);
        this.periodoInicio.setCalendar(null);
        chxOcupar.setEnabled(false);
        tblObras.clearSelection();
        tblUbicaciones.clearSelection();
    }

    public int obtenerFilaObra() {
        try {
            int fila = tblObras.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una ubicación. (De la tabla 'Ubicaciones registradas').", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
            return fila;
        } catch (Exception e) {
            Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public int obtenerFilaUbicacion() {
        try {
            int fila = tblUbicaciones.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una ubicación. (De la tabla 'Ubicaciones registradas').", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
            return fila;
        } catch (Exception e) {
            Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public void cargarTablaUbicaciones() throws Exception {
        this.ubicacion = null;
        listaUbicaciones = UbicacionesDAO.consultarUbicacionesDisponibles(this.cliente);
        DefaultTableModel modeloTablaPersonas = (DefaultTableModel) this.tblUbicaciones.getModel();
        modeloTablaPersonas.setRowCount(0);
        for (Ubicaciones ubicaciones : listaUbicaciones) {
            Object[] filaNueva = {ubicaciones.getId(),
                ubicaciones.getDisponible() == true ? "Disponible" : "No disponible",
                ubicaciones.getDireccion(),
                ubicaciones.getTipo().toString(),
                ubicaciones.getArea().toString() + " m²",
                fecha.formatoFecha(ubicaciones.getFechaRegistro()),
                ubicaciones.getFechaOcupacion() != null ? fecha.formatoFecha(ubicaciones.getFechaOcupacion()) : "No aplica",
                "| Seleccionar |"};
            modeloTablaPersonas.addRow(filaNueva);
        }
        valido.centrarTabla(tblUbicaciones);
    }

    public void cargarTablaObras(Integer fechas) throws Exception {
        this.obra = null;
        EstadoObra estado;
        Float costo;
        // Se asigna valor a costo
        if (this.txtCostoTotal.getText().equalsIgnoreCase("0.0")) {
            costo = null;
        } else {
            costo = Float.valueOf(this.txtCostoTotal.getText());
        }
        // Se asigna valor a estado de obra
        if (this.cbxEstado.getSelectedItem() == "Desarrollo") {
            estado = EstadoObra.DESARROLLO;
        } else {
            estado = EstadoObra.EN_ESPERA;
        }
        // 0 buscar por fecha solicitada, 1 buscar por fecha inicio, 2 buscar por fecha fin
        switch (fechas) {
            case 0 ->
                listaObras = ObrasDAO.consultarObrasFechaSolicitada(// Se busca por acción FIN
                        this.periodoInicio.getCalendar() != null // Si el usuario ingresó un periodo inicio se ingresa en el campo
                        ? this.periodoInicio.getCalendar() : null,
                        this.periodoFinal.getCalendar() != null // Si el usuario ingresó un periodo fin se ingresa en el campo
                        ? this.periodoFinal.getCalendar() : null,
                        estado, // Estado
                        false, // Filtro de pagadas
                        costo, // Costo
                        this.cliente.getId()); // Cliente
            case 1 ->
                listaObras = ObrasDAO.consultarObrasFechaInicio( // Se busca por acción FIN
                        this.periodoInicio.getCalendar() != null // Si el usuario ingresó un periodo inicio se ingresa en el campo
                        ? this.periodoInicio.getCalendar() : null,
                        this.periodoFinal.getCalendar() != null // Si el usuario ingresó un periodo fin se ingresa en el campo
                        ? this.periodoFinal.getCalendar() : null,
                        estado, // Estado
                        false, // Filtro de pagadas
                        costo, // Costo
                        this.cliente.getId()); // Cliente
            default ->
                listaObras = ObrasDAO.consultarObrasFechaFin(// Se busca por acción FIN
                        this.periodoInicio.getCalendar() != null // Si el usuario ingresó un periodo inicio se ingresa en el campo
                        ? this.periodoInicio.getCalendar() : null,
                        this.periodoFinal.getCalendar() != null // Si el usuario ingresó un periodo fin se ingresa en el campo
                        ? this.periodoFinal.getCalendar() : null,
                        estado, // Estado
                        false, // Filtro de pagadas
                        costo, // Costo
                        this.cliente.getId()); // Cliente
        }
        DefaultTableModel modeloTablaObras = (DefaultTableModel) this.tblObras.getModel();
        modeloTablaObras.setRowCount(0);
        for (Obras obras : listaObras) {
            Object[] filaNueva = {obras.getId(),
                obras.getNombre(),
                obras.getEstado().equals(EstadoObra.EN_ESPERA) ? "EN ESPERA" : obras.getEstado().toString(),
                "$ " + obras.getCostoTotal() + " MXN",
                "$ " + obras.getDeuda() + " MXN",
                obras.getEstaPagada() == true ? "Pagada" : "No pagada",
                fecha.formatoFecha(obras.getFechaSolicitada()),
                obras.getFechaInicio() != null ? fecha.formatoFecha(obras.getFechaInicio()) : "No aplica",
                obras.getFechaFin() != null ? fecha.formatoFecha(obras.getFechaFin()) : "No aplica"};
            modeloTablaObras.addRow(filaNueva);
        }
        valido.centrarTabla(tblObras);
    }

    public void corregirTamaños() {
        if (this.txtCostoTotal.getText().length() > 12) {
            this.txtCostoTotal.setText(this.txtCostoTotal.getText().substring(0, 30));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnAceptarSeleccionUbicaciones = new javax.swing.JButton();
        lblTitulo = new javax.swing.JLabel();
        UObraLogoPeque = new javax.swing.JLabel();
        lblInsertarDireccion = new javax.swing.JLabel();
        lblInsertarArea = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        chxOcupar = new javax.swing.JCheckBox();
        lblUbicaciones = new javax.swing.JLabel();
        lblArea = new javax.swing.JLabel();
        lblFechaRegistrada = new javax.swing.JLabel();
        lblIDObra = new javax.swing.JLabel();
        lblInsertarIDUbicacion = new javax.swing.JLabel();
        ScrollPanel = new javax.swing.JScrollPane();
        tblUbicaciones = new javax.swing.JTable();
        lblInsertarFecha = new javax.swing.JLabel();
        lblInstrucción = new javax.swing.JLabel();
        lblUbicacionSeleccionada = new javax.swing.JLabel();
        Separador = new javax.swing.JSeparator();
        btnAgregar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        lblMXN1 = new javax.swing.JLabel();
        lbl$1 = new javax.swing.JLabel();
        Separador3 = new javax.swing.JSeparator();
        lblFechaInicio = new javax.swing.JLabel();
        lblBusqueda = new javax.swing.JLabel();
        Separador2 = new javax.swing.JSeparator();
        cbxEstado = new javax.swing.JComboBox<>();
        lblFechaFin = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        cbxAccion = new javax.swing.JComboBox<>();
        lblCosto = new javax.swing.JLabel();
        lbl$ = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();
        lblMXN = new javax.swing.JLabel();
        txtCostoTotal = new javax.swing.JTextField();
        lblAccion = new javax.swing.JLabel();
        lblQue = new javax.swing.JLabel();
        lblEnUnPeriodo = new javax.swing.JLabel();
        lblEnEstado = new javax.swing.JLabel();
        lblConMinimo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        periodoInicio = new com.toedter.calendar.JDateChooser();
        periodoFinal = new com.toedter.calendar.JDateChooser();
        ScrollPanel1 = new javax.swing.JScrollPane();
        tblObras = new javax.swing.JTable();
        lblNombre = new javax.swing.JLabel();
        lblFechaSolicitada = new javax.swing.JLabel();
        btnAceptarSeleccionObras = new javax.swing.JButton();
        lblIDObra1 = new javax.swing.JLabel();
        lblInsertarIDObra = new javax.swing.JLabel();
        lblInsertarDeuda = new javax.swing.JLabel();
        lblInsertarFechaSolicitada = new javax.swing.JLabel();
        lblInsertarNombre = new javax.swing.JLabel();
        lblInstrucción1 = new javax.swing.JLabel();
        lblDireccion1 = new javax.swing.JLabel();
        lblUbicacionSeleccionada1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agregar Ubicación Obra");

        btnAceptarSeleccionUbicaciones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAceptarSeleccionUbicaciones.setText("Aceptar selección");
        btnAceptarSeleccionUbicaciones.setToolTipText("Acepta la selección del primer elemento elegido");
        btnAceptarSeleccionUbicaciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptarSeleccionUbicaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarSeleccionUbicacionesActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Agregar Ubicación Obra");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblInsertarDireccion.setText("dirección");

        lblInsertarArea.setText("area");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion.setText("Dirección:");

        chxOcupar.setText("Ocupar ubicación");

        lblUbicaciones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUbicaciones.setText("Ubicaciones registradas:");

        lblArea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblArea.setText("Área:");

        lblFechaRegistrada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaRegistrada.setText("Fecha registrada:");

        lblIDObra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIDObra.setText("ID:");

        lblInsertarIDUbicacion.setText("id");

        ScrollPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ScrollPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblUbicaciones.setBackground(new java.awt.Color(255, 255, 255));
        tblUbicaciones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblUbicaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Disponible", "Dirección", "Tipo", "Área", "Fecha registro", "Fecha ocupación", "Opciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUbicaciones.setRequestFocusEnabled(false);
        tblUbicaciones.getTableHeader().setReorderingAllowed(false);
        ScrollPanel.setViewportView(tblUbicaciones);

        lblInsertarFecha.setText("fecha");

        lblInstrucción.setText("Seleccione una ubicación para ser agregada a la obra seleccionada");

        lblUbicacionSeleccionada.setText("Ubicación seleccionada:");

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setToolTipText("Solicitar obra");
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar solictud de obra");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        lblMXN1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN1.setText("MXN");

        lbl$1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$1.setText("$");

        lblFechaInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaInicio.setText("Fecha inicio:");

        lblBusqueda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBusqueda.setText("Búsqueda Dinámica:");

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "En espera", "Desarrollo" }));
        cbxEstado.setToolTipText("Elija un estado");
        cbxEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblFechaFin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaFin.setText("Fecha fin:");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEstado.setText("Estado:");

        cbxAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Solicitada", "Iniciada", "Terminada" }));
        cbxAccion.setToolTipText("Elija una acción");
        cbxAccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblCosto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCosto.setText("Costo:");

        lbl$.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$.setText("$");

        lblResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResultado.setText("Obras en desarrollo o en espera:");

        lblMXN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN.setText("MXN");

        txtCostoTotal.setToolTipText("Ingrese números decimales");
        txtCostoTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoTotalKeyTyped(evt);
            }
        });

        lblAccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAccion.setText("Acción:");

        lblQue.setText("Obra que haya sido...");

        lblEnUnPeriodo.setText("En un periodo de...");

        lblEnEstado.setText("Se encuentren en estado...");

        lblConMinimo.setText("Con un costo total mínimo de...");

        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setToolTipText("Buscar Obras");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        periodoInicio.setToolTipText("Periodo inicio");

        periodoFinal.setToolTipText("Periodo fin");

        ScrollPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ScrollPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblObras.setBackground(new java.awt.Color(255, 255, 255));
        tblObras.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblObras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Estado", "Costo Total", "Deuda", "Pagada", "Fecha Solicitada", "Fecha Inicio", "Fecha Fin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblObras.setRequestFocusEnabled(false);
        tblObras.getTableHeader().setReorderingAllowed(false);
        ScrollPanel1.setViewportView(tblObras);

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setText("Nombre:");

        lblFechaSolicitada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaSolicitada.setText("Fecha solicitada:");

        btnAceptarSeleccionObras.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAceptarSeleccionObras.setText("Aceptar selección");
        btnAceptarSeleccionObras.setToolTipText("Acepta la selección del primer elemento elegido");
        btnAceptarSeleccionObras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptarSeleccionObras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarSeleccionObrasActionPerformed(evt);
            }
        });

        lblIDObra1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIDObra1.setText("ID:");

        lblInsertarIDObra.setText("id");

        lblInsertarDeuda.setText("deuda");

        lblInsertarFechaSolicitada.setText("fecha");

        lblInsertarNombre.setText("nombre");

        lblInstrucción1.setText("Seleccione una obra para agregar una ubicación");

        lblDireccion1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion1.setText("Deuda:");

        lblUbicacionSeleccionada1.setText("Obra seleccionada:");

        jLabel1.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenLupaNombre.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBusqueda)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(lblQue))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addComponent(lblAccion)
                                .addGap(6, 6, 6)
                                .addComponent(cbxAccion, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(lblEnUnPeriodo))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(lblFechaInicio)
                                .addGap(6, 6, 6)
                                .addComponent(periodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(46, 46, 46)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblEstado)
                            .addComponent(lblCosto))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblConMinimo)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl$)
                                .addGap(6, 6, 6)
                                .addComponent(txtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(lblMXN))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEnEstado)
                                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addContainerGap(152, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Separador2, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblInstrucción1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblUbicacionSeleccionada1)
                                .addGap(24, 24, 24)
                                .addComponent(lblIDObra1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblInsertarIDObra)
                                .addGap(18, 18, 18)
                                .addComponent(lblFechaSolicitada)
                                .addGap(18, 18, 18)
                                .addComponent(lblInsertarFechaSolicitada)
                                .addGap(45, 45, 45)
                                .addComponent(lblNombre)
                                .addGap(18, 18, 18)
                                .addComponent(lblInsertarNombre)
                                .addGap(33, 33, 33)
                                .addComponent(lblDireccion1)
                                .addGap(18, 18, 18)
                                .addComponent(lbl$1)
                                .addGap(6, 6, 6)
                                .addComponent(lblInsertarDeuda)
                                .addGap(52, 52, 52)
                                .addComponent(lblMXN1))
                            .addComponent(Separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnAgregar)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblUbicacionSeleccionada)
                                        .addGap(26, 26, 26)
                                        .addComponent(lblIDObra)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblInsertarIDUbicacion)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblFechaRegistrada)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblInsertarFecha)
                                        .addGap(32, 32, 32)
                                        .addComponent(lblArea)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblInsertarArea)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(lblDireccion)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblInsertarDireccion))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCancelar))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(lblFechaFin)
                                .addGap(6, 6, 6)
                                .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(156, 156, 156)
                                .addComponent(btnBuscar))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(ScrollPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(9, 9, 9)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblUbicaciones)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnAceptarSeleccionUbicaciones))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(lblInstrucción)
                                            .addGap(18, 18, 18)
                                            .addComponent(chxOcupar)
                                            .addGap(0, 0, Short.MAX_VALUE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblResultado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 549, Short.MAX_VALUE)
                                .addComponent(btnAceptarSeleccionObras))
                            .addComponent(ScrollPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 852, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(13, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(lblTitulo)
                        .addGap(601, 601, 601)
                        .addComponent(UObraLogoPeque))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(Separador, javax.swing.GroupLayout.PREFERRED_SIZE, 862, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblTitulo))
                    .addComponent(UObraLogoPeque))
                .addGap(6, 6, 6)
                .addComponent(Separador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addComponent(jLabel1)
                            .addGap(35, 35, 35))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblEnEstado)
                            .addGap(5, 5, 5)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblConMinimo)
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lbl$)
                                    .addComponent(lblCosto))
                                .addComponent(txtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblMXN))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnBuscar)
                            .addGap(8, 8, 8)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblBusqueda)
                                .addGap(6, 6, 6)
                                .addComponent(lblQue)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(lblAccion))
                                    .addComponent(cbxAccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEnUnPeriodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFechaInicio)
                                    .addComponent(periodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblFechaFin)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addComponent(lblEstado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(Separador2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblResultado)
                    .addComponent(btnAceptarSeleccionObras))
                .addGap(8, 8, 8)
                .addComponent(ScrollPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblInstrucción1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUbicacionSeleccionada1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblInsertarIDObra, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblIDObra1))
                    .addComponent(lblInsertarFechaSolicitada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInsertarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl$1)
                    .addComponent(lblInsertarDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMXN1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaSolicitada)
                            .addComponent(lblNombre)
                            .addComponent(lblDireccion1))))
                .addGap(12, 12, 12)
                .addComponent(Separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUbicaciones)
                    .addComponent(btnAceptarSeleccionUbicaciones))
                .addGap(6, 6, 6)
                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInstrucción, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chxOcupar))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUbicacionSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInsertarIDUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInsertarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInsertarArea, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInsertarDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIDObra)
                            .addComponent(lblFechaRegistrada)
                            .addComponent(lblArea)
                            .addComponent(lblDireccion))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar)
                    .addComponent(btnCancelar))
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarSeleccionUbicacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarSeleccionUbicacionesActionPerformed
        try {
            int fila = obtenerFilaUbicacion();
            if (fila != -1) {
                this.btnAgregar.setEnabled(true);
                this.ubicacion = UbicacionesDAO.consultarUbicacion((Long) tblUbicaciones.getValueAt(fila, 0));
                if (this.ubicacion.getTipo().equals(TipoUbicacion.SOLAR)) {
                    this.chxOcupar.setEnabled(false);
                    this.chxOcupar.setSelected(true);
                } else {
                    this.chxOcupar.setEnabled(true);
                    this.chxOcupar.setSelected(false);
                }
                this.lblInsertarArea.setText(ubicacion.getArea().toString() + " m²");
                this.lblInsertarDireccion.setText(ubicacion.getDireccion());
                this.lblInsertarFecha.setText(fecha.formatoFecha(ubicacion.getFechaRegistro()));
                this.lblInsertarIDUbicacion.setText(ubicacion.getId().toString());
            }
        } catch (Exception e) {
            Logger.getLogger(ConsultarUbicaciones.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnAceptarSeleccionUbicacionesActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        corregirTamaños();
        if (btnAceptarSeleccionObras.isEnabled() && btnAceptarSeleccionUbicaciones.isEnabled()) {
            if (this.ubicacion != null && this.obra != null) {
                ObrasDAO.agregarUbicacionObra(this.obra.getId(), this.ubicacion.getId());
                try {
                    if (this.chxOcupar.isSelected()) {
                        UbicacionesDAO.ocuparUbicacion(this.ubicacion.getId());
                    }
                    this.setVisible(false);
                    int i = JOptionPane.showConfirmDialog(null,
                            "Se agregó exitosamente la ubicación a la obra con..."
                            + "\n Nombre: " + obra.getNombre()
                            + "\n Inversión inicial: $ " + obra.getInversion() + " MXN"
                            + "\n Fecha solicitud: " + fecha.formatoFecha(obra.getFechaSolicitada())
                            + "\n\nLa ubicación: " + ubicacion.getDireccion()
                            + "\n Dirección: " + ubicacion.getDireccion()
                            + "\n Ancho: " + ubicacion.getAncho() + " m"
                            + "\n Largo: " + ubicacion.getLargo() + " m"
                            + "\n Área: " + ubicacion.getArea() + " m²"
                            + "\n - ID Cliente: " + obra.getCliente().getId()
                            + "\n - ID Ubicación: " + this.ubicacion.getId()
                            + "\n - ID Obra: " + obra.getId()
                            + ". ☺\n"
                            + "\n ¿Desea agregar otra ubicación?", "Agregación de ubicación exitosa", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                    if (i == JOptionPane.YES_OPTION) {
                        this.btnAceptarSeleccionUbicaciones.setEnabled(false);
                        this.btnAceptarSeleccionObras.setEnabled(false);
                        this.btnAgregar.setEnabled(false);
                        this.txtCostoTotal.setText("1000.0");
                        this.cbxAccion.setSelectedItem("Elija uno...");
                        this.cbxEstado.setSelectedItem("Elija uno");
                        this.lblInsertarArea.setText("");
                        this.lblInsertarDireccion.setText("");
                        this.lblInsertarFecha.setText("");
                        this.lblInsertarIDUbicacion.setText("");
                        this.lblInsertarDeuda.setText("");
                        this.lblInsertarFechaSolicitada.setText("");
                        this.lblInsertarIDObra.setText("");
                        this.lblInsertarNombre.setText("");
                        this.periodoFinal.setCalendar(null);
                        this.periodoInicio.setCalendar(null);
                        DefaultTableModel modeloTablaObras = (DefaultTableModel) this.tblObras.getModel();
                        modeloTablaObras.setRowCount(0);
                        DefaultTableModel modeloTablaPersonas = (DefaultTableModel) this.tblUbicaciones.getModel();
                        modeloTablaPersonas.setRowCount(0);
                        tblObras.clearSelection();
                        tblUbicaciones.clearSelection();
                        this.setVisible(true);
                    } else {
                        this.dispose();
                        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(SolicitarObra.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Una obra, una ubicación o ambos son nulos.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Seleccione una obra y una ubicación válidas.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar la agregacón de ubicación? Los datos de la agregación no se guardarán", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtCostoTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoTotalKeyTyped
        if (txtCostoTotal.getText().length() >= 12) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        } else if (c == '.' && txtCostoTotal.getText().contains(".")) {
            // Si ya hay un punto en el texto, no permitir otro
            evt.consume();
        }
        // Obtener el componente fuente del evento
        JTextField textField = (JTextField) evt.getSource();

        // Verificar si el evento es una operación de pegar
        if (evt.isConsumed() || evt.getKeyChar() == KeyEvent.VK_V && evt.isControlDown()) {
            // Si es una operación de pegar, cancelar el evento
            evt.consume();

            // Vaciar el contenido del campo de texto
            textField.setText("");
        }
    }//GEN-LAST:event_txtCostoTotalKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        corregirTamaños();
        this.btnAceptarSeleccionObras.setEnabled(true);
        try {
            if (this.periodoFinal.getCalendar() != null && this.periodoInicio.getCalendar() != null) {
                if (valido.validarFechas(this.periodoInicio.getCalendar(), this.periodoFinal.getCalendar())) {
                    if (this.cbxAccion.getSelectedItem() != "Elija uno...") {
                        if (this.cbxEstado.getSelectedItem() != "Elija uno...") {
                            // Se valida y formatea el campo de costo total
                            txtCostoTotal.setText(valido.corregirFlotante(txtCostoTotal.getText()));
                            if (Float.parseFloat(txtCostoTotal.getText()) <= 999) {
                                txtCostoTotal.setText("1000.0");
                            }
                            // Se llama a la función que cargará la tabla
                            if (this.cbxAccion.getSelectedItem() == "Solicitada") {
                                this.cargarTablaObras(0);
                            } else if (this.cbxAccion.getSelectedItem() == "Iniciada") {
                                this.cargarTablaObras(1);
                            } else if (this.cbxAccion.getSelectedItem() == "Terminada") {
                                this.cargarTablaObras(2);
                            }
                            this.btnAceptarSeleccionObras.setEnabled(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: Elija un estado de obra válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Elija un tipo de acción válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: La fecha inicial no puede ser después que la fecha final.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (this.cbxAccion.getSelectedItem() != "Elija uno...") {
                    if (this.cbxEstado.getSelectedItem() != "Elija uno...") {
                        // Se valida y formatea el campo de costo total
                        txtCostoTotal.setText(valido.corregirFlotante(txtCostoTotal.getText()));
                        if (Float.parseFloat(txtCostoTotal.getText()) <= 999) {
                            txtCostoTotal.setText("1000.0");
                        }
                        // Se llama a la función que cargará la tabla
                        if (this.cbxAccion.getSelectedItem() == "Solicitada") {
                            this.cargarTablaObras(0);
                        } else if (this.cbxAccion.getSelectedItem() == "Iniciada") {
                            this.cargarTablaObras(1);
                        } else if (this.cbxAccion.getSelectedItem() == "Terminada") {
                            this.cargarTablaObras(2);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Elija un estado de obra válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Elija un tipo de acción válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnAceptarSeleccionObrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarSeleccionObrasActionPerformed
        try {
            int fila = obtenerFilaObra();
            if (fila != -1) {
                this.btnAceptarSeleccionUbicaciones.setEnabled(true);
                chxOcupar.setEnabled(true);
                cargarTablaUbicaciones();
                this.obra = ObrasDAO.consultarObra((Long) tblObras.getValueAt(fila, 0));
                this.lblInsertarNombre.setText(obra.getNombre());
                this.lblInsertarDeuda.setText(String.valueOf(obra.getDeuda()));
                this.lblInsertarFechaSolicitada.setText(fecha.formatoFecha(obra.getFechaSolicitada()));
                this.lblInsertarIDUbicacion.setText(obra.getId().toString());
            }
        } catch (Exception e) {
            Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnAceptarSeleccionObrasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JScrollPane ScrollPanel1;
    private javax.swing.JSeparator Separador;
    private javax.swing.JSeparator Separador2;
    private javax.swing.JSeparator Separador3;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnAceptarSeleccionObras;
    private javax.swing.JButton btnAceptarSeleccionUbicaciones;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> cbxAccion;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JCheckBox chxOcupar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl$;
    private javax.swing.JLabel lbl$1;
    private javax.swing.JLabel lblAccion;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblConMinimo;
    private javax.swing.JLabel lblCosto;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccion1;
    private javax.swing.JLabel lblEnEstado;
    private javax.swing.JLabel lblEnUnPeriodo;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblFechaRegistrada;
    private javax.swing.JLabel lblFechaSolicitada;
    private javax.swing.JLabel lblIDObra;
    private javax.swing.JLabel lblIDObra1;
    private javax.swing.JLabel lblInsertarArea;
    private javax.swing.JLabel lblInsertarDeuda;
    private javax.swing.JLabel lblInsertarDireccion;
    private javax.swing.JLabel lblInsertarFecha;
    private javax.swing.JLabel lblInsertarFechaSolicitada;
    private javax.swing.JLabel lblInsertarIDObra;
    private javax.swing.JLabel lblInsertarIDUbicacion;
    private javax.swing.JLabel lblInsertarNombre;
    private javax.swing.JLabel lblInstrucción;
    private javax.swing.JLabel lblInstrucción1;
    private javax.swing.JLabel lblMXN;
    private javax.swing.JLabel lblMXN1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblQue;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUbicacionSeleccionada;
    private javax.swing.JLabel lblUbicacionSeleccionada1;
    private javax.swing.JLabel lblUbicaciones;
    private com.toedter.calendar.JDateChooser periodoFinal;
    private com.toedter.calendar.JDateChooser periodoInicio;
    private javax.swing.JTable tblObras;
    private javax.swing.JTable tblUbicaciones;
    private javax.swing.JTextField txtCostoTotal;
    // End of variables declaration//GEN-END:variables
}
