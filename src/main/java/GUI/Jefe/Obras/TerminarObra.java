/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe.Obras;

import DAOs.ClientesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrasObreroDAO;
import DAOs.ObrerosDAO;
import DAOs.PermisosDAO;
import Dominio.Clientes;
import Dominio.Jefes;
import Dominio.Obras;
import Dominio.ObrasObrero;
import Dominio.Permisos;
import Enumeradores.EstadoObra;
import Enumeradores.TipoPermiso;
import GUI.Cliente.PanelCliente;
import Herramientas.Encriptador;
import Herramientas.Fecha;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 52644
 */
public class TerminarObra extends javax.swing.JFrame {

    // Atributos
    Jefes jefe;
    Clientes cliente = new Clientes();
    Fecha fecha = new Fecha();
    ClientesDAO ClientesDAO = new ClientesDAO();
    ObrasDAO ObrasDAO = new ObrasDAO();
    ObrasObreroDAO ObrasObreroDAO = new ObrasObreroDAO();
    PermisosDAO PermisosDAO = new PermisosDAO();
    ObrerosDAO ObrerosDAO = new ObrerosDAO();
    Validadores valido = new Validadores();
    Encriptador crypt = new Encriptador();
    Obras obra;

    /**
     * Creates new form TerminarObra
     */
    public TerminarObra(Jefes jefe) throws Exception {
        this.jefe = jefe;
        this.obra = null;
        initComponents();
        new Icono().insertarIcono(this);
        this.cbxPermisos.setEnabled(false);
        this.lblCosto.setText("0.0");
        List<Permisos> permisos = PermisosDAO.consultarPermisosConcesion(null, null, TipoPermiso.FINALIZACION, this.jefe);
        for (Permisos permiso : permisos) {
            this.cbxPermisos.addItem(
                    "Folio: " + crypt.decrypt(permiso.getFolio())
                    + " - ID: " + permiso.getId());
        }
        this.cbxPermisos.setSelectedItem("Elija uno...");
        this.cbxPermisos.setEnabled(false);
    }

    public void cargarTablaObras(Integer fechas) throws Exception {
        List<Obras> listaObras;
        Fecha fecha = new Fecha();
        this.obra = null;
        EstadoObra estado;
        Float costo;
        Boolean pagada;
        // Se asigna valor a costo
        if (this.txtCostoTotal.getText().equalsIgnoreCase("0.0")) {
            costo = null;
        } else {
            costo = Float.valueOf(this.txtCostoTotal.getText());
        }
        // Se asigna valor a estado económico 
        if (this.cbxEconomia.getSelectedItem() == "Indistinto") {
            pagada = null;
        } else if (this.cbxEconomia.getSelectedItem() == "Pagadas") {
            pagada = true;
        } else {
            pagada = false;
        }
        // Se asigna valor a estado de obra
        estado = EstadoObra.DESARROLLO;
        // 0 buscar por fecha solicitada, 1 buscar por fecha inicio, 2 buscar por fecha fin
        switch (fechas) {
            case 0 ->
                listaObras = ObrasDAO.consultarObrasFechaSolicitada(// Se busca por acción FIN
                        this.periodoInicio.getCalendar() != null // Si el usuario ingresó un periodo inicio se ingresa en el campo
                        ? this.periodoInicio.getCalendar() : null,
                        this.periodoFinal.getCalendar() != null // Si el usuario ingresó un periodo fin se ingresa en el campo
                        ? this.periodoFinal.getCalendar() : null,
                        estado, // Estado
                        pagada, // Filtro de pagadas
                        costo, // Costo
                        this.cliente.getId()); // Cliente
            case 1 ->
                listaObras = ObrasDAO.consultarObrasFechaInicio( // Se busca por acción FIN
                        this.periodoInicio.getCalendar() != null // Si el usuario ingresó un periodo inicio se ingresa en el campo
                        ? this.periodoInicio.getCalendar() : null,
                        this.periodoFinal.getCalendar() != null // Si el usuario ingresó un periodo fin se ingresa en el campo
                        ? this.periodoFinal.getCalendar() : null,
                        estado, // Estado
                        pagada, // Filtro de pagadas
                        costo, // Costo
                        this.cliente.getId()); // Cliente
            default ->
                listaObras = ObrasDAO.consultarObrasFechaFin(// Se busca por acción FIN
                        this.periodoInicio.getCalendar() != null // Si el usuario ingresó un periodo inicio se ingresa en el campo
                        ? this.periodoInicio.getCalendar() : null,
                        this.periodoFinal.getCalendar() != null // Si el usuario ingresó un periodo fin se ingresa en el campo
                        ? this.periodoFinal.getCalendar() : null,
                        estado, // Estado
                        pagada, // Filtro de pagadas
                        costo, // Costo
                        this.cliente.getId()); // Cliente
        }
        DefaultTableModel modeloTablaObras = (DefaultTableModel) this.tblResultados.getModel();
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
    }

    public int obtenerFila() {
        try {
            int fila = tblResultados.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una ubicación. (De la tabla 'Ubicaciones registradas').", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
            return fila;
        } catch (Exception e) {
            Logger.getLogger(TerminarObra.class.getName()).log(Level.SEVERE, null, e);
            return -1;
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

        lblMXN = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();
        lbl$ = new javax.swing.JLabel();
        lblCosto = new javax.swing.JLabel();
        lblEconomia = new javax.swing.JLabel();
        UObraLogoPeque = new javax.swing.JLabel();
        lblDeuda = new javax.swing.JLabel();
        cbxAccion = new javax.swing.JComboBox<>();
        cbxEconomia = new javax.swing.JComboBox<>();
        Imagen = new javax.swing.JLabel();
        lblEnUnPeriodo = new javax.swing.JLabel();
        lblQue = new javax.swing.JLabel();
        lblObrero = new javax.swing.JLabel();
        cbxPermisos = new javax.swing.JComboBox<>();
        lblAccion = new javax.swing.JLabel();
        txtCostoTotal = new javax.swing.JTextField();
        lblConMinimo = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblFechaSolicitada = new javax.swing.JLabel();
        Separador3 = new javax.swing.JSeparator();
        lblNombre = new javax.swing.JLabel();
        lbl$1 = new javax.swing.JLabel();
        lblMXN1 = new javax.swing.JLabel();
        ScrollPanel = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        lblUbicacionSeleccionada = new javax.swing.JLabel();
        periodoFinal = new com.toedter.calendar.JDateChooser();
        lblPermisos = new javax.swing.JLabel();
        periodoInicio = new com.toedter.calendar.JDateChooser();
        btnBuscar = new javax.swing.JButton();
        lblDireccion = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        lblInsertarID = new javax.swing.JLabel();
        lblIDObra = new javax.swing.JLabel();
        lblFechaFin = new javax.swing.JLabel();
        Separador2 = new javax.swing.JSeparator();
        lblInstrucción = new javax.swing.JLabel();
        lblBusqueda = new javax.swing.JLabel();
        lblInsertarNombre = new javax.swing.JLabel();
        lblInsertarFechaSolicitada = new javax.swing.JLabel();
        lblInsertarDeuda = new javax.swing.JLabel();
        Separador1 = new javax.swing.JSeparator();
        btnFinalizarObra = new javax.swing.JButton();
        lblFechaInicio = new javax.swing.JLabel();
        lblIniciarObra = new javax.swing.JLabel();
        btnAceptarSeleccion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Terminar Obra");
        setResizable(false);

        lblMXN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN.setText("MXN");

        lblResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResultado.setText("Resultado:");

        lbl$.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$.setText("$");

        lblCosto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCosto.setText("Costo:");

        lblEconomia.setText("Que económicamente estén...");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblDeuda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDeuda.setText("Deuda:");

        cbxAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Solicitada", "Iniciada", "Terminada" }));
        cbxAccion.setToolTipText("Elija una acción");
        cbxAccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbxEconomia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Indistinto", "Pagadas", "No pagadas" }));
        cbxEconomia.setToolTipText("Elija una economía");
        cbxEconomia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        Imagen.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenObra.png")); // NOI18N

        lblEnUnPeriodo.setText("En un periodo de...");

        lblQue.setText("Obra que haya sido...");

        lblObrero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblObrero.setText("Permiso finalización:");

        cbxPermisos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxPermisos.setToolTipText("Elija un permiso");
        cbxPermisos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblAccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAccion.setText("Acción:");

        txtCostoTotal.setToolTipText("Ingrese números decimales");
        txtCostoTotal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoTotalKeyTyped(evt);
            }
        });

        lblConMinimo.setText("Con un costo total mínimo de...");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Terminar Obra");

        lblFechaSolicitada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaSolicitada.setText("Fecha solicitada:");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setText("Nombre:");

        lbl$1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$1.setText("$");

        lblMXN1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN1.setText("MXN");

        ScrollPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ScrollPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblResultados.setBackground(new java.awt.Color(255, 255, 255));
        tblResultados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Estado", "Costo Total", "Deuda", "Pagada", "Fecha Solicitada", "Fecha Inicio", "Fecha Fin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        tblResultados.setRequestFocusEnabled(false);
        ScrollPanel.setViewportView(tblResultados);

        lblUbicacionSeleccionada.setText("Obra seleccionada:");

        periodoFinal.setToolTipText("Periodo fin");

        lblPermisos.setText("Seleccione el permiso finalización...");

        periodoInicio.setToolTipText("Periodo inicio");

        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setToolTipText("Buscar Obras");
        btnBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion.setText("Deuda:");

        btnRegresar.setText("Cancelar");
        btnRegresar.setToolTipText("Cancelar el fin de obra");
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblInsertarID.setText("id");

        lblIDObra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIDObra.setText("ID:");

        lblFechaFin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaFin.setText("Fecha fin:");

        lblInstrucción.setText("Seleccione una obra para realizar un pago");

        lblBusqueda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBusqueda.setText("Búsqueda Dinámica:");

        lblInsertarNombre.setText("nombre");

        lblInsertarFechaSolicitada.setText("fecha");

        lblInsertarDeuda.setText("deuda");

        btnFinalizarObra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnFinalizarObra.setText("Finalizar Obra");
        btnFinalizarObra.setToolTipText("Finalizar la obra ");
        btnFinalizarObra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFinalizarObra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinalizarObraActionPerformed(evt);
            }
        });

        lblFechaInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaInicio.setText("Fecha inicio:");

        lblIniciarObra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIniciarObra.setText("Finalizar Obra:");

        btnAceptarSeleccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAceptarSeleccion.setText("Aceptar selección");
        btnAceptarSeleccion.setToolTipText("Acepta la selección del primer elemento elegido");
        btnAceptarSeleccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptarSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarSeleccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UObraLogoPeque)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(lblFechaFin)
                                .addGap(6, 6, 6)
                                .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(120, 120, 120)
                                .addComponent(btnBuscar))
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
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblDeuda)
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblEconomia)
                                            .addComponent(cbxEconomia, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCosto)
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblConMinimo)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(7, 7, 7)
                                                .addComponent(lbl$)
                                                .addGap(6, 6, 6)
                                                .addComponent(txtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(12, 12, 12)
                                                .addComponent(lblMXN)))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Imagen)
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblObrero)
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPermisos)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbxPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(45, 45, 45)
                                        .addComponent(btnFinalizarObra)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnRegresar))))
                            .addComponent(Separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Separador2, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblResultado)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblInstrucción)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnAceptarSeleccion))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblUbicacionSeleccionada)
                                            .addGap(18, 18, 18)
                                            .addComponent(lblIDObra, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(lblInsertarID)
                                            .addGap(18, 18, 18)
                                            .addComponent(lblFechaSolicitada)
                                            .addGap(18, 18, 18)
                                            .addComponent(lblInsertarFechaSolicitada)
                                            .addGap(45, 45, 45)
                                            .addComponent(lblNombre)
                                            .addGap(18, 18, 18)
                                            .addComponent(lblInsertarNombre)
                                            .addGap(33, 33, 33)
                                            .addComponent(lblDireccion)
                                            .addGap(18, 18, 18)
                                            .addComponent(lbl$1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(lblInsertarDeuda)
                                            .addGap(52, 52, 52)
                                            .addComponent(lblMXN1)
                                            .addGap(0, 0, Short.MAX_VALUE))))
                                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblIniciarObra))
                        .addContainerGap(18, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblTitulo))
                    .addComponent(UObraLogoPeque, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(6, 6, 6)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblBusqueda)
                                .addGap(6, 6, 6)
                                .addComponent(lblQue)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(lblAccion))
                                    .addComponent(cbxAccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addComponent(lblEnUnPeriodo)
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFechaInicio)
                                    .addComponent(periodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblDeuda)
                                            .addComponent(cbxEconomia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblEconomia))
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(25, 25, 25)
                                        .addComponent(lblCosto))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblConMinimo)
                                        .addGap(6, 6, 6)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl$)
                                            .addComponent(txtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblMXN))))))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblFechaFin))
                            .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(Separador2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(lblResultado)
                .addGap(12, 12, 12)
                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblInstrucción, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAceptarSeleccion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUbicacionSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIDObra)
                            .addComponent(lblInsertarID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaSolicitada)
                            .addComponent(lblInsertarFechaSolicitada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInsertarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNombre)
                            .addComponent(lblDireccion)
                            .addComponent(lblInsertarDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl$1)
                            .addComponent(lblMXN1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIniciarObra)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblObrero)
                            .addComponent(cbxPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnFinalizarObra)
                            .addComponent(btnRegresar)))
                    .addComponent(lblPermisos))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCostoTotalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoTotalKeyTyped
        if (txtCostoTotal.getText().length() >= 12) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_txtCostoTotalKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            this.cbxPermisos.setSelectedItem("Elija uno...");
            this.cbxPermisos.setEnabled(false);
            if (this.periodoFinal.getCalendar() != null && this.periodoInicio.getCalendar() != null) {
                if (valido.validarFechas(this.periodoInicio.getCalendar(), this.periodoFinal.getCalendar())) {
                    if (this.cbxAccion.getSelectedItem() != "Elija uno...") {
                        if (this.cbxEconomia.getSelectedItem() != "Elija uno...") {
                            // Se valida y formatea el campo de costo total
                            if (this.txtCostoTotal.getText().isBlank()) {
                                this.txtCostoTotal.setText("0.0");
                            } else {
                                this.txtCostoTotal.setText(this.txtCostoTotal.getText().trim());
                                this.txtCostoTotal.setText(valido.recortarComas(this.txtCostoTotal.getText()));
                                if (valido.validarSinEspacios(this.txtCostoTotal.getText())) {
                                    this.txtCostoTotal.setText(valido.recortarSignoMas(this.txtCostoTotal.getText()));
                                    if (!valido.validarNumero(this.txtCostoTotal.getText())) {
                                        JOptionPane.showMessageDialog(null, "Error: Ingrese un número en el costo total. (No caracteres ni numeros negativos).", "¡Error!", JOptionPane.ERROR_MESSAGE);
                                    } else if (!valido.validarFlotante(this.txtCostoTotal.getText())) {
                                        this.txtCostoTotal.setText(this.txtCostoTotal.getText().concat(".0"));
                                        // Se llama a la función que cargará la tabla
                                        if (this.cbxAccion.getSelectedItem() == "Solicitada") {
                                            this.cargarTablaObras(0);
                                        } else if (this.cbxAccion.getSelectedItem() == "Iniciada") {
                                            this.cargarTablaObras(1);
                                        } else if (this.cbxAccion.getSelectedItem() == "Terminada") {
                                            this.cargarTablaObras(2);
                                        }
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error: Ingrese un costo total válido. (Sin espacios).", "¡Error!", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: Elija un estado económico de obra válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Elija un tipo de acción válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: La fecha inicial no puede ser después que la fecha final.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                if (this.cbxAccion.getSelectedItem() != "Elija uno...") {
                    if (this.cbxEconomia.getSelectedItem() != "Elija uno...") {
                        // Se valida y formatea el campo de costo total
                        if (this.txtCostoTotal.getText().isBlank()) {
                            this.txtCostoTotal.setText("0.0");
                        } else {
                            this.txtCostoTotal.setText(this.txtCostoTotal.getText().trim());
                            this.txtCostoTotal.setText(valido.recortarComas(this.txtCostoTotal.getText()));
                            if (valido.validarSinEspacios(this.txtCostoTotal.getText())) {
                                this.txtCostoTotal.setText(valido.recortarSignoMas(this.txtCostoTotal.getText()));
                                if (!valido.validarNumero(this.txtCostoTotal.getText())) {
                                    JOptionPane.showMessageDialog(null, "Error: Ingrese un número en el costo total. (No caracteres ni numeros negativos).", "¡Error!", JOptionPane.ERROR_MESSAGE);
                                } else if (!valido.validarFlotante(this.txtCostoTotal.getText())) {
                                    this.txtCostoTotal.setText(this.txtCostoTotal.getText().concat(".0"));
                                    // Se llama a la función que cargará la tabla
                                    if (this.cbxAccion.getSelectedItem() == "Solicitada") {
                                        this.cargarTablaObras(0);
                                    } else if (this.cbxAccion.getSelectedItem() == "Iniciada") {
                                        this.cargarTablaObras(1);
                                    } else if (this.cbxAccion.getSelectedItem() == "Terminada") {
                                        this.cargarTablaObras(2);
                                    }
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Error: Ingrese un costo total válido. (Sin espacios).", "¡Error!", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Elija un estado económico de obra válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Elija un tipo de acción válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(TerminarObra.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar el registro de ubicación? Los datos de registro no se guardarán", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnFinalizarObraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarObraActionPerformed
        if (this.cbxPermisos.isEnabled()) {
            if (this.obra != null) {
                if (this.cbxPermisos.getSelectedItem() != "Elija uno...") {
                    // Permiso
                    String permisoElegido = this.cbxPermisos.getSelectedItem().toString();
                    String idElegido = permisoElegido.substring(permisoElegido.length(), 3);
                    idElegido = valido.obtenerNumeros(idElegido);
                    Long id = Long.valueOf(idElegido);
                    Permisos permiso = PermisosDAO.consultarPermiso(id);
                    if (permiso != null) {
                        try {
                            ObrasDAO.agregarPermisoObra(this.obra.getId(), id);
                            ObrasDAO.terminarObra(id);
                            for (ObrasObrero obrasObrero : this.obra.getObreros()) {
//                                ObrasObreroDAO.desactivarObrasObrero(obrasObrero.getId());
                                  ObrasObreroDAO.desactivarYPagarObrasObrero(obrasObrero.getId());
                            }
                            JOptionPane.showMessageDialog(null,
                                    "Se realizó exitosamente el fin de la obra " + this.obra.getNombre()
                                    + " con el permiso de finalización de folio " + crypt.decrypt(permiso.getFolio())
                                    + "\n - ID Obra: " + this.obra.getId() + ". ☺", "Fin de obra exitoso", JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                        } catch (Exception ex) {
                            Logger.getLogger(TerminarObra.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error interno: Ocurrió un errror al querer realizar el pago.", "¡Error interno!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Seleccione un permiso válido de iniciación.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una obra cual finalizar.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Seleccione una obra cual finalizar.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnFinalizarObraActionPerformed

    private void btnAceptarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarSeleccionActionPerformed
        try {
            int fila = obtenerFila();
            if (fila != -1) {
                this.obra = ObrasDAO.consultarObra((Long) tblResultados.getValueAt(fila, 0));
                this.lblInsertarNombre.setText(obra.getNombre());
                this.lblInsertarDeuda.setText(String.valueOf(obra.getDeuda()));
                this.lblInsertarFechaSolicitada.setText(fecha.formatoFecha(obra.getFechaSolicitada()));
                this.lblInsertarID.setText(obra.getId().toString());
                this.cbxPermisos.setSelectedItem("Elija uno...");
                this.cbxPermisos.setEnabled(true);
            }
        } catch (Exception e) {
            Logger.getLogger(TerminarObra.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnAceptarSeleccionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen;
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separador2;
    private javax.swing.JSeparator Separador3;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnAceptarSeleccion;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnFinalizarObra;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxAccion;
    private javax.swing.JComboBox<String> cbxEconomia;
    private javax.swing.JComboBox<String> cbxPermisos;
    private javax.swing.JLabel lbl$;
    private javax.swing.JLabel lbl$1;
    private javax.swing.JLabel lblAccion;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblConMinimo;
    private javax.swing.JLabel lblCosto;
    private javax.swing.JLabel lblDeuda;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEconomia;
    private javax.swing.JLabel lblEnUnPeriodo;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblFechaSolicitada;
    private javax.swing.JLabel lblIDObra;
    private javax.swing.JLabel lblIniciarObra;
    private javax.swing.JLabel lblInsertarDeuda;
    private javax.swing.JLabel lblInsertarFechaSolicitada;
    private javax.swing.JLabel lblInsertarID;
    private javax.swing.JLabel lblInsertarNombre;
    private javax.swing.JLabel lblInstrucción;
    private javax.swing.JLabel lblMXN;
    private javax.swing.JLabel lblMXN1;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObrero;
    private javax.swing.JLabel lblPermisos;
    private javax.swing.JLabel lblQue;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUbicacionSeleccionada;
    private com.toedter.calendar.JDateChooser periodoFinal;
    private com.toedter.calendar.JDateChooser periodoInicio;
    private javax.swing.JTable tblResultados;
    private javax.swing.JTextField txtCostoTotal;
    // End of variables declaration//GEN-END:variables
}
