/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Cliente;

import DAOs.ClientesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrerosDAO;
import DAOs.PagosDAO;
import Dominio.Clientes;
import Dominio.Obras;
import Dominio.Obreros;
import Dominio.Pagos;
import Enumeradores.EstadoObra;
import Enumeradores.MetodoPago;
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
public class RealizarPago extends javax.swing.JFrame {

    // Atributos
    Clientes cliente = new Clientes();
    Fecha fecha = new Fecha();
    ClientesDAO ClientesDAO = new ClientesDAO();
    ObrasDAO ObrasDAO = new ObrasDAO();
    ObrerosDAO ObrerosDAO = new ObrerosDAO();
    PagosDAO PagosDAO = new PagosDAO();
    Validadores valido = new Validadores();
    List<Obras> listaObras;
    Obras obra;

    /**
     * Creates new form RealizarPago
     */
    public RealizarPago(Clientes cliente) throws Exception {
        this.cliente = cliente;
        this.obra = null;
        initComponents();
        UIManager.put("OptionPane.yesButtonText", "Aceptar");
        UIManager.put("OptionPane.noButtonText", "Cancelar");
        new Icono().insertarIcono(this);
        this.txtCostoTotal.setText("1000.0");
        this.txtMonto.setText("100.0");
        List<Obreros> obreros = ObrerosDAO.consultarTodosObreros();
        for (Obreros obrero : obreros) {
            this.cbxObreros.addItem(
                    obrero.getNombre()
                    + " " + obrero.getApellidoPaterno()
                    + " " + obrero.getApellidoMaterno()
                    + " - ID: " + obrero.getId());
        }
        this.cbxObreros.setSelectedItem("Elija uno...");
        this.cbxAccion.setSelectedItem("Elija uno...");
        this.cbxEstado.setSelectedItem("Elija uno...");
        this.cbxMetodoPago.setSelectedItem("Elija uno...");
        this.lblInsertarDeuda.setText("");
        this.lblInsertarFechaSolicitada.setText("");
        this.lblInsertarID.setText("");
        this.lblInsertarNombre.setText("");
        this.txtMonto.setEditable(false);
        this.cbxMetodoPago.setEnabled(false);
        this.cbxObreros.setEnabled(false);
        tblResultados.clearSelection();
        DefaultTableModel modeloTablaResultados = (DefaultTableModel) this.tblResultados.getModel();
        modeloTablaResultados.setRowCount(0);
    }

    public int obtenerFila() {
        try {
            int fila = tblResultados.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una ubicación. (De la tabla 'Ubicaciones registradas').", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
            return fila;
        } catch (Exception e) {
            Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    public void corregirTamaños() {
        if (this.txtCostoTotal.getText().length() > 12) {
            this.txtCostoTotal.setText(this.txtCostoTotal.getText().substring(0, 12));
        }
        if (this.txtMonto.getText().length() > 12) {
            this.txtMonto.setText(this.txtMonto.getText().substring(0, 12));
        }
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
            estado = EstadoObra.TERMINADA;
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
        valido.centrarTabla(tblResultados);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        Separador1 = new javax.swing.JSeparator();
        lblBusqueda = new javax.swing.JLabel();
        Separador2 = new javax.swing.JSeparator();
        cbxEstado = new javax.swing.JComboBox<>();
        lblFechaFin = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        cbxAccion = new javax.swing.JComboBox<>();
        UObraLogoPeque = new javax.swing.JLabel();
        lblCosto = new javax.swing.JLabel();
        lbl$ = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();
        lblMXN = new javax.swing.JLabel();
        txtCostoTotal = new javax.swing.JTextField();
        lblAccion = new javax.swing.JLabel();
        lblQue = new javax.swing.JLabel();
        lblEnUnPeriodo = new javax.swing.JLabel();
        Imagen = new javax.swing.JLabel();
        lblEnEstado = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        lblConMinimo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        periodoInicio = new com.toedter.calendar.JDateChooser();
        periodoFinal = new com.toedter.calendar.JDateChooser();
        ScrollPanel = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        lblNombre = new javax.swing.JLabel();
        lblFechaSolicitada = new javax.swing.JLabel();
        btnAceptarSeleccion = new javax.swing.JButton();
        lblIDObra = new javax.swing.JLabel();
        lblInsertarID = new javax.swing.JLabel();
        lblInsertarDeuda = new javax.swing.JLabel();
        lblInsertarFechaSolicitada = new javax.swing.JLabel();
        lblInsertarNombre = new javax.swing.JLabel();
        lblInstrucción = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblUbicacionSeleccionada = new javax.swing.JLabel();
        lblMXN1 = new javax.swing.JLabel();
        lbl$1 = new javax.swing.JLabel();
        Separador3 = new javax.swing.JSeparator();
        lblRealizarPago = new javax.swing.JLabel();
        btnRealizarPago = new javax.swing.JButton();
        lblMontoPago = new javax.swing.JLabel();
        lblCosto1 = new javax.swing.JLabel();
        lbl$2 = new javax.swing.JLabel();
        lblMXN2 = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        cbxMetodoPago = new javax.swing.JComboBox<>();
        lblMetodo = new javax.swing.JLabel();
        lblMetodoPago = new javax.swing.JLabel();
        cbxObreros = new javax.swing.JComboBox<>();
        lblObrero = new javax.swing.JLabel();
        lblObreros = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Realizar Pago");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Realizar Pago");

        lblFechaInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaInicio.setText("Fecha inicio:");

        lblBusqueda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBusqueda.setText("Búsqueda Dinámica:");

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Desarrollo", "Teminada" }));
        cbxEstado.setToolTipText("Elija un estado");
        cbxEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblFechaFin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaFin.setText("Fecha fin:");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEstado.setText("Estado:");

        cbxAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Solicitada", "Iniciada", "Terminada" }));
        cbxAccion.setToolTipText("Elija una acción");
        cbxAccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblCosto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCosto.setText("Costo:");

        lbl$.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$.setText("$");

        lblResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResultado.setText("Resultado:");

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

        Imagen.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenObra.png")); // NOI18N

        lblEnEstado.setText("Se encuentren en estado...");

        btnRegresar.setText("Cancelar");
        btnRegresar.setToolTipText("Cancelar la realización del pago");
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

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
        tblResultados.setRequestFocusEnabled(false);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        ScrollPanel.setViewportView(tblResultados);

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNombre.setText("Nombre:");

        lblFechaSolicitada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaSolicitada.setText("Fecha solicitada:");

        btnAceptarSeleccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAceptarSeleccion.setText("Aceptar selección");
        btnAceptarSeleccion.setToolTipText("Acepta la selección del primer elemento elegido");
        btnAceptarSeleccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptarSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarSeleccionActionPerformed(evt);
            }
        });

        lblIDObra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIDObra.setText("ID:");

        lblInsertarID.setText("id");

        lblInsertarDeuda.setText("deuda");

        lblInsertarFechaSolicitada.setText("fecha");

        lblInsertarNombre.setText("nombre");

        lblInstrucción.setText("Seleccione una obra para realizar un pago");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion.setText("Deuda:");

        lblUbicacionSeleccionada.setText("Obra seleccionada:");

        lblMXN1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN1.setText("MXN");

        lbl$1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$1.setText("$");

        lblRealizarPago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRealizarPago.setText("Realizar pago:");

        btnRealizarPago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRealizarPago.setText("Realizar Pago");
        btnRealizarPago.setToolTipText("Realizar pago de obra");
        btnRealizarPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRealizarPago.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRealizarPagoActionPerformed(evt);
            }
        });

        lblMontoPago.setText("Monto del pago....");

        lblCosto1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCosto1.setText("Monto:");

        lbl$2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$2.setText("$");

        lblMXN2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN2.setText("MXN");

        txtMonto.setToolTipText("Ingrese números decimales");
        txtMonto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoKeyTyped(evt);
            }
        });

        cbxMetodoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Efectivo", "Débito", "Crédito" }));
        cbxMetodoPago.setToolTipText("Elija un estado");
        cbxMetodoPago.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbxMetodoPago.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                cbxMetodoPagoFocusLost(evt);
            }
        });

        lblMetodo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMetodo.setText("Método de pago:");

        lblMetodoPago.setText("Ingrese método de pago...");

        cbxObreros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxObreros.setToolTipText("Elija un estado");
        cbxObreros.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblObrero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblObrero.setText("Obrero:");

        lblObreros.setText("Obrero al quien se le dio el pago en efectivo...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRealizarPago))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UObraLogoPeque)
                        .addGap(33, 33, 33))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(periodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(lblFechaFin)
                                        .addGap(6, 6, 6)
                                        .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblEstado)
                                        .addGap(12, 12, 12)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblEnEstado)
                                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblCosto)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(65, 65, 65)
                                                .addComponent(btnBuscar))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(17, 17, 17)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblConMinimo)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(lbl$)
                                                        .addGap(6, 6, 6)
                                                        .addComponent(txtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(12, 12, 12)
                                                        .addComponent(lblMXN)))))))
                                .addGap(26, 26, 26)
                                .addComponent(Imagen))
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
                                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(19, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(lblCosto1)
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMontoPago)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lbl$2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblMXN2)))
                        .addGap(18, 18, 18)
                        .addComponent(lblMetodo)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblMetodoPago, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbxMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblObrero)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblObreros)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE))
                            .addComponent(cbxObreros, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnRealizarPago)
                        .addGap(271, 271, 271)
                        .addComponent(btnRegresar)))
                .addGap(28, 28, 28))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
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
                                        .addGap(49, 49, 49)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblEstado)
                                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(lblEnEstado)))
                                .addGap(18, 18, 18)
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
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnBuscar)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblFechaFin))
                            .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(14, 14, 14)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRealizarPago)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(lblCosto1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblMontoPago)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl$2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblMXN2))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblMetodoPago)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lblObrero)
                                            .addComponent(cbxObreros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblObreros))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(21, 21, 21)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblMetodo)
                                        .addComponent(cbxMetodoPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegresar)
                    .addComponent(btnRealizarPago))
                .addGap(17, 17, 17))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

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

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        corregirTamaños();
        try {
            this.cbxObreros.setSelectedItem("Elija uno...");
            this.txtMonto.setEditable(false);
            this.cbxMetodoPago.setEnabled(false);
            this.cbxObreros.setEnabled(false);
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

    private void btnAceptarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarSeleccionActionPerformed
        try {
            int fila = obtenerFila();
            if (fila != -1) {
                this.obra = ObrasDAO.consultarObra((Long) tblResultados.getValueAt(fila, 0));
                this.lblInsertarNombre.setText(obra.getNombre());
                this.lblInsertarDeuda.setText(String.valueOf(obra.getDeuda()));
                this.lblInsertarFechaSolicitada.setText(fecha.formatoFecha(obra.getFechaSolicitada()));
                this.lblInsertarID.setText(obra.getId().toString());
                this.cbxObreros.setSelectedItem("Elija uno...");
                this.txtMonto.setEditable(true);
                this.cbxMetodoPago.setEnabled(true);
                this.cbxObreros.setEnabled(true);
            }
        } catch (Exception e) {
            Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnAceptarSeleccionActionPerformed

    private void btnRealizarPagoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRealizarPagoActionPerformed
        corregirTamaños();
        if (this.cbxMetodoPago.getSelectedItem() != "Efectivo") {
            this.cbxObreros.setSelectedItem("No aplica");
            this.cbxObreros.setEnabled(false);
        }
        if (this.txtMonto.isEditable()) {
            if (this.obra != null) {
                if (this.cbxMetodoPago.getSelectedItem() != "Elija uno...") {
                    // Se valida y formatea el campo de monto
                    txtMonto.setText(valido.corregirFlotante(txtMonto.getText()));
                    if (Float.parseFloat(txtMonto.getText()) <= 99) {
                        txtMonto.setText("100.0");
                    }
                    if (this.cbxMetodoPago.getSelectedItem() == "Efectivo") {
                        if (this.cbxObreros.getSelectedItem() != "Elija uno...") {
                            try {
                                String obreroElegido = this.cbxObreros.getSelectedItem().toString();
                                String idElegido = obreroElegido.substring(obreroElegido.length() - 3, obreroElegido.length());
                                idElegido = valido.obtenerNumeros(idElegido);
                                Long id = Long.valueOf(idElegido);
                                Obreros obrero = ObrerosDAO.consultarObrero(id);
                                Pagos pago = new Pagos(
                                        Float.valueOf(this.txtMonto.getText()),
                                        MetodoPago.EFECTIVO,
                                        obrero,
                                        this.obra,
                                        this.cliente);
                                Pagos pagoRegistrado = PagosDAO.registrarPago(pago);
                                if (pagoRegistrado != null) {
                                    ObrerosDAO.agregarPagoObrero(id, pagoRegistrado);
                                    ClientesDAO.agregarPagosCliente(this.cliente.getId(), pagoRegistrado);
                                    ClientesDAO.sumarInversionTotalCliente(this.cliente.getId(), Float.valueOf(this.txtMonto.getText()));
                                    ClientesDAO.restarDeudaCliente(this.cliente.getId(), Float.valueOf(this.txtMonto.getText()));
                                    ObrasDAO.agregarPagoObra(this.obra.getId(), pagoRegistrado);
                                    this.setVisible(false);
                                    int i = JOptionPane.showConfirmDialog(null,
                                            "Se realizó exitosamente el pago con..."
                                            + "\n Monto: $ " + pagoRegistrado.getMonto() + " MXN"
                                            + "\n Obra: " + this.obra.getNombre()
                                            + "\n Fecha: " + fecha.formatoFecha(pagoRegistrado.getFecha())
                                            + "\n Método de pago: " + pagoRegistrado.getMetodoPago().toString()
                                            + "\n - ID Cliente: " + pagoRegistrado.getCliente().getId()
                                            + "\n - ID Obra: " + pagoRegistrado.getObra().getId()
                                            + "\n - ID Obrero: " + pagoRegistrado.getObrero() != null ? pagoRegistrado.getObrero().getId() : "No aplica (No se pagó en efectivo)"
                                            + "\n - ID: " + pagoRegistrado.getId()
                                            + ". ☺\n"
                                            + "\n ¿Desea registrar otra ubicación?", "Realización de pago exitoso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                                    if (i == JOptionPane.YES_OPTION) {
                                        this.dispose();
                                        new RealizarPago(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
                                    } else {
                                        this.dispose();
                                        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
                                    }
                                } else {
                                    JOptionPane.showMessageDialog(null, "Error interno: Ocurrió un errror al querer realizar el pago.", "¡Error interno!", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (ParseException ex) {
                                Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (Exception ex) {
                                Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: Seleccione un obrero válido que haya recibido el pago en efectivo.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        MetodoPago metodo;
                        if (cbxMetodoPago.getSelectedItem() == "Débito") {
                            metodo = MetodoPago.DEBITO;
                        } else {
                            metodo = MetodoPago.CREDITO;
                        }
                        Pagos pago = new Pagos(
                                Float.valueOf(this.txtMonto.getText()),
                                metodo,
                                this.obra,
                                this.cliente);
                        Pagos pagoRegistrado = PagosDAO.registrarPago(pago);
                        if (pagoRegistrado != null) {
                            try {
                                ClientesDAO.agregarPagosCliente(this.cliente.getId(), pagoRegistrado);
                                ClientesDAO.sumarInversionTotalCliente(this.cliente.getId(), Float.valueOf(this.txtMonto.getText()));
                                    ClientesDAO.restarDeudaCliente(this.cliente.getId(), Float.valueOf(this.txtMonto.getText()));
                                ObrasDAO.agregarPagoObra(this.obra.getId(), pagoRegistrado);
                                this.setVisible(false);
                                int i = JOptionPane.showConfirmDialog(null,
                                            "Se realizó exitosamente el pago con..."
                                            + "\n Monto: $ " + pagoRegistrado.getMonto() + " MXN"
                                            + "\n Obra: " + this.obra.getNombre()
                                            + "\n Fecha: " + fecha.formatoFecha(pagoRegistrado.getFecha())
                                            + "\n Método de pago: " + pagoRegistrado.getMetodoPago().toString()
                                            + "\n - ID Cliente: " + pagoRegistrado.getCliente().getId()
                                            + "\n - ID Obra: " + pagoRegistrado.getObra().getId()
                                            + "\n - ID Obrero: " + pagoRegistrado.getObrero() != null ? pagoRegistrado.getObrero().getId() : "No aplica (No se pagó en efectivo)"
                                            + "\n - ID: " + pagoRegistrado.getId()
                                            + ". ☺\n"
                                            + "\n ¿Desea registrar otra ubicación?", "Realización de pago exitoso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                                    if (i == JOptionPane.YES_OPTION) {
                                        this.dispose();
                                        new RealizarPago(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
                                    } else {
                                        this.dispose();
                                        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
                                    }
                            } catch (Exception ex) {
                                Logger.getLogger(RealizarPago.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error interno: Ocurrió un errror al querer realizar el pago.", "¡Error interno!", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Seleccione un método de pago válido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una obra a la cual se le realizará el pago.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Seleccione una obra a la cual se le realizará el pago.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRealizarPagoActionPerformed

    private void cbxMetodoPagoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cbxMetodoPagoFocusLost
        if (this.cbxMetodoPago.getSelectedItem() != "Efectivo") {
            this.cbxObreros.setEnabled(false);
            this.cbxObreros.setSelectedItem("No aplica");
        }
    }//GEN-LAST:event_cbxMetodoPagoFocusLost

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

    private void txtMontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoKeyTyped
        if (txtMonto.getText().length() >= 12) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        } else if (c == '.' && txtMonto.getText().contains(".")) {
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
    }//GEN-LAST:event_txtMontoKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen;
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separador2;
    private javax.swing.JSeparator Separador3;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnAceptarSeleccion;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRealizarPago;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxAccion;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxMetodoPago;
    private javax.swing.JComboBox<String> cbxObreros;
    private javax.swing.JLabel lbl$;
    private javax.swing.JLabel lbl$1;
    private javax.swing.JLabel lbl$2;
    private javax.swing.JLabel lblAccion;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblConMinimo;
    private javax.swing.JLabel lblCosto;
    private javax.swing.JLabel lblCosto1;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEnEstado;
    private javax.swing.JLabel lblEnUnPeriodo;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblFechaSolicitada;
    private javax.swing.JLabel lblIDObra;
    private javax.swing.JLabel lblInsertarDeuda;
    private javax.swing.JLabel lblInsertarFechaSolicitada;
    private javax.swing.JLabel lblInsertarID;
    private javax.swing.JLabel lblInsertarNombre;
    private javax.swing.JLabel lblInstrucción;
    private javax.swing.JLabel lblMXN;
    private javax.swing.JLabel lblMXN1;
    private javax.swing.JLabel lblMXN2;
    private javax.swing.JLabel lblMetodo;
    private javax.swing.JLabel lblMetodoPago;
    private javax.swing.JLabel lblMontoPago;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObrero;
    private javax.swing.JLabel lblObreros;
    private javax.swing.JLabel lblQue;
    private javax.swing.JLabel lblRealizarPago;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUbicacionSeleccionada;
    private com.toedter.calendar.JDateChooser periodoFinal;
    private com.toedter.calendar.JDateChooser periodoInicio;
    private javax.swing.JTable tblResultados;
    private javax.swing.JTextField txtCostoTotal;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
