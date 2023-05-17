/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe.Obras;

import DAOs.JefesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrerosDAO;
import DAOs.PermisosDAO;
import Dominio.Jefes;
import Dominio.Obras;
import Dominio.Obreros;
import Dominio.Permisos;
import Enumeradores.EstadoObra;
import Enumeradores.TipoPermiso;
import GUI.Cliente.PanelCliente;
import GUI.Jefe.PanelJefe;
import Herramientas.Encriptador;
import Herramientas.Fecha;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Objects;
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
public class IniciarObra extends javax.swing.JFrame {

    // Atributos
    Jefes jefe;
    Fecha fecha = new Fecha();
    JefesDAO JefesDAO = new JefesDAO();
    ObrasDAO ObrasDAO = new ObrasDAO();
    PermisosDAO PermisosDAO = new PermisosDAO();
    ObrerosDAO ObrerosDAO = new ObrerosDAO();
    Validadores valido = new Validadores();
    Encriptador crypt = new Encriptador();
    List<Obras> listaObras;
    Obras obra;

    /**
     * Creates new form IniciarObra
     */
    public IniciarObra(Jefes jefe) throws Exception {
        this.jefe = jefe;
        this.obra = null;
        UIManager.put("OptionPane.yesButtonText", "Aceptar");
        UIManager.put("OptionPane.noButtonText", "Cancelar");
        initComponents();
        new Icono().insertarIcono(this);
        cargarTablaObras();
        this.cbxPermisos.setEnabled(false);
        this.txtCostoArranque.setText("10000.0");
        List<Permisos> permisos = PermisosDAO.consultarPermisosConcesion(null, null, TipoPermiso.INICIACION, this.jefe);
        for (Permisos permiso : permisos) {
            this.cbxPermisos.addItem(
                    "Folio: " + crypt.decrypt(permiso.getFolio())
                    + " - ID: " + permiso.getId());
        }
        this.cbxPermisos.setSelectedItem("Elija uno...");
        this.cbxPermisos.setEnabled(false);
        List<Obreros> obreros = ObrerosDAO.consultarTodosObreros();
        for (Obreros obrero : obreros) {
            this.cbxObrero1.addItem(
                    obrero.getNombre()
                    + " " + obrero.getApellidoPaterno()
                    + " " + obrero.getApellidoMaterno()
                    + " - ID: " + obrero.getId());
            this.cbxObrero2.addItem(
                    obrero.getNombre()
                    + " " + obrero.getApellidoPaterno()
                    + " " + obrero.getApellidoMaterno()
                    + " - ID: " + obrero.getId());
            this.cbxObrero3.addItem(
                    obrero.getNombre()
                    + " " + obrero.getApellidoPaterno()
                    + " " + obrero.getApellidoMaterno()
                    + " - ID: " + obrero.getId());
        }
        this.cbxObrero1.setSelectedItem("Elija uno...");
        this.cbxObrero1.setEnabled(false);
        this.cbxObrero2.setSelectedItem("Elija uno...");
        this.cbxObrero2.setEnabled(false);
        this.cbxObrero3.setSelectedItem("Elija uno...");
        this.cbxObrero3.setEnabled(false);
    }

    public void cargarTablaObras() throws Exception {
        this.obra = null;
        EstadoObra estado = EstadoObra.EN_ESPERA;
        listaObras = ObrasDAO.consultarObrasConEstado(estado); // Cliente
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
                obras.getFechaFin() != null ? fecha.formatoFecha(obras.getFechaFin()) : "No aplica",
                "| Seleccionar |"};
            modeloTablaObras.addRow(filaNueva);
        }
        valido.centrarTabla(tblResultados);
    }

    public int obtenerFila() {
        try {
            int fila = tblResultados.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una ubicación. (De la tabla 'Ubicaciones registradas').", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
            return fila;
        } catch (Exception e) {
            Logger.getLogger(IniciarObra.class.getName()).log(Level.SEVERE, null, e);
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

        lblDireccion = new javax.swing.JLabel();
        lblUbicacionSeleccionada = new javax.swing.JLabel();
        lblMXN1 = new javax.swing.JLabel();
        lbl$1 = new javax.swing.JLabel();
        Separador3 = new javax.swing.JSeparator();
        lblTitulo = new javax.swing.JLabel();
        lblIniciarObra = new javax.swing.JLabel();
        btnIniciarObra = new javax.swing.JButton();
        Separador1 = new javax.swing.JSeparator();
        UObraLogoPeque = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();
        cbxPermisos = new javax.swing.JComboBox<>();
        lblPermiso = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        lblPermisos = new javax.swing.JLabel();
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
        lblObrero1 = new javax.swing.JLabel();
        cbxObrero1 = new javax.swing.JComboBox<>();
        lblPermisos1 = new javax.swing.JLabel();
        cbxObrero2 = new javax.swing.JComboBox<>();
        lblObrero2 = new javax.swing.JLabel();
        lblObrero3 = new javax.swing.JLabel();
        cbxObrero3 = new javax.swing.JComboBox<>();
        lblMXN2 = new javax.swing.JLabel();
        lbl$2 = new javax.swing.JLabel();
        txtCostoArranque = new javax.swing.JTextField();
        lblConMinimo1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Iniciar Obra");
        setResizable(false);

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion.setText("Deuda:");

        lblUbicacionSeleccionada.setText("Obra seleccionada:");

        lblMXN1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN1.setText("MXN");

        lbl$1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$1.setText("$");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Iniciar Obra");

        lblIniciarObra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIniciarObra.setText("Iniciar Obra:");

        btnIniciarObra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnIniciarObra.setText("Iniciar Obra");
        btnIniciarObra.setToolTipText("Iniciar la obra");
        btnIniciarObra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciarObra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarObraActionPerformed(evt);
            }
        });

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResultado.setText("Obras en espera:");

        cbxPermisos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxPermisos.setToolTipText("Elija un permiso");
        cbxPermisos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblPermiso.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPermiso.setText("Permiso iniciación:");

        btnRegresar.setText("Cancelar");
        btnRegresar.setToolTipText("Cancelar el inicio de obra");
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblPermisos.setText("Seleccione el permiso iniciación...");

        ScrollPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ScrollPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblResultados.setBackground(new java.awt.Color(255, 255, 255));
        tblResultados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Estado", "Costo Total", "Deuda", "Pagada", "Fecha Solicitada", "Fecha Inicio", "Fecha Fin", "Opciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
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

        lblObrero1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblObrero1.setText("Obrero #1:");

        cbxObrero1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxObrero1.setToolTipText("Obrero #1");
        cbxObrero1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblPermisos1.setText("Mínimo 3 obreros...");

        cbxObrero2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxObrero2.setToolTipText("Obrero #2");
        cbxObrero2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblObrero2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblObrero2.setText("Obrero #2:");

        lblObrero3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblObrero3.setText("Obrero #3:");

        cbxObrero3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxObrero3.setToolTipText("Obrero #3");
        cbxObrero3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblMXN2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN2.setText("MXN");

        lbl$2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$2.setText("$");

        txtCostoArranque.setToolTipText("Ingrese números decimales");
        txtCostoArranque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCostoArranqueKeyTyped(evt);
            }
        });

        lblConMinimo1.setText("Con un costo de arranque de...");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblIniciarObra)
                                    .addComponent(lblPermiso))
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPermisos)
                                    .addComponent(cbxPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(btnIniciarObra)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(lblObrero1))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblObrero3)))
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblObrero2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPermisos1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxObrero1, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxObrero2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxObrero3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(39, 39, 39)
                        .addComponent(lbl$2)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblConMinimo1)
                            .addComponent(txtCostoArranque, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(btnRegresar)))
                        .addComponent(lblMXN2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitulo)
                                .addGap(719, 719, 719)
                                .addComponent(UObraLogoPeque))
                            .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblResultado)
                            .addComponent(Separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(6, 6, 6)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblUbicacionSeleccionada)
                                            .addGap(18, 18, 18)
                                            .addComponent(lblIDObra)
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
                                            .addGap(6, 6, 6)
                                            .addComponent(lblInsertarDeuda)
                                            .addGap(30, 30, 30)
                                            .addComponent(lblMXN1)
                                            .addGap(20, 20, 20))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(lblInstrucción)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnAceptarSeleccion)
                                            .addGap(18, 18, 18))))
                                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 887, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(UObraLogoPeque))
                .addGap(6, 6, 6)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblResultado)
                .addGap(12, 12, 12)
                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInstrucción, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptarSeleccion))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUbicacionSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInsertarID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInsertarFechaSolicitada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInsertarNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl$1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblInsertarDeuda, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMXN1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIDObra)
                            .addComponent(lblFechaSolicitada)
                            .addComponent(lblNombre)
                            .addComponent(lblDireccion))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Separador3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(lblIniciarObra)
                        .addGap(9, 9, 9)
                        .addComponent(lblPermiso))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblPermisos)
                        .addGap(5, 5, 5)
                        .addComponent(cbxPermisos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnIniciarObra))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblPermisos1)
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxObrero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblObrero1))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxObrero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblObrero2))
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxObrero3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblObrero3)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lbl$2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblConMinimo1)
                        .addGap(6, 6, 6)
                        .addComponent(txtCostoArranque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnRegresar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(lblMXN2)))
                .addGap(22, 22, 22))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarObraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarObraActionPerformed
        if (this.cbxPermisos.isEnabled()) {
            if (this.obra != null) {
                if (this.cbxPermisos.getSelectedItem() != "Elija uno...") {
                    if (this.cbxObrero1.getSelectedItem() != "Elija uno..."
                            && this.cbxObrero2.getSelectedItem() != "Elija uno..."
                            && this.cbxObrero3.getSelectedItem() != "Elija uno...") {
                            // Obrero #1
                            String o1 = this.cbxObrero1.getSelectedItem().toString();
                            String idObrero1 = o1.substring(o1.length() - 3, o1.length());
                            idObrero1 = valido.obtenerNumeros(idObrero1);
                            Long idO1 = Long.valueOf(idObrero1);
                            // Obrero #2
                            String o2 = this.cbxObrero2.getSelectedItem().toString();
                            String idObrero2 = o2.substring(o2.length() - 3, o2.length());
                            idObrero2 = valido.obtenerNumeros(idObrero2);
                            Long idO2 = Long.valueOf(idObrero2);
                            // Obrero #3
                            String o3 = this.cbxObrero3.getSelectedItem().toString();
                            String idObrero3 = o3.substring(o3.length() - 3, o3.length());
                            idObrero3 = valido.obtenerNumeros(idObrero3);
                            Long idO3 = Long.valueOf(idObrero3);
                        if (!Objects.equals(idO1, idO2) && !Objects.equals(idO1, idO3) && !Objects.equals(idO2, idO3)) {
                            // Se valida y formatea el campo de costo arranque
                            txtCostoArranque.setText(valido.corregirFlotante(txtCostoArranque.getText()));
                            if (Float.parseFloat(txtCostoArranque.getText()) <= 99) {
                                txtCostoArranque.setText("100.0");
                            }
                            // Permiso
                            String permisoElegido = this.cbxPermisos.getSelectedItem().toString();
                            String idElegido = permisoElegido.substring(permisoElegido.length() - 3, permisoElegido.length());
                            idElegido = valido.obtenerNumeros(idElegido);
                            Long id = Long.valueOf(idElegido);
                            Permisos permiso = PermisosDAO.consultarPermiso(id);
                            if (permiso != null) {
                                try {
                                    ObrasDAO.agregarPermisoObra(this.obra.getId(), id);
                                    ObrasDAO.asignarJefeObra(id, this.jefe.getId());
                                    ObrasDAO.asingarTresObrerosObra(id, idO1, idO2, idO3);
                                    ObrasDAO.sumarCostoArranqueObra(id, Float.valueOf(this.txtCostoArranque.getText()));
                                    ObrasDAO.iniciarObra(id);
                                    this.setVisible(false);
                                    int i = 0;
                                    try {
                                        i = JOptionPane.showConfirmDialog(null,
                                                "Se realizó exitosamente el inicio de la obra con..."
                                                + "\n Nombre: " + this.obra.getNombre()
                                                + "\n Costo total: $ " + this.obra.getCostoTotal() + " MXN"
                                                + "\n Folio permiso de iniciación:  " + crypt.decrypt(permiso.getFolio())
                                                + "\n - ID Jefe: " + this.obra.getJefe().getId()
                                                + "\n - ID Cliente: " + this.obra.getCliente().getId()
                                                + "\n - ID Obra: " + this.obra.getId()
                                                + ". ☺\n"
                                                + "\n ¿Desea iniciar otra obra?", "Inicio de obra exitoso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                                    } catch (Exception ex) {
                                        Logger.getLogger(IniciarObra.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    if (i == JOptionPane.YES_OPTION) {
                                        this.txtCostoArranque.setText("100.0");
                                        this.cbxObrero1.setSelectedItem("Elija uno...");
                                        this.cbxObrero2.setSelectedItem("Elija uno...");
                                        this.cbxObrero3.setSelectedItem("Elija uno...");
                                        this.cbxPermisos.setSelectedItem("Elija uno...");
                                        this.lblInsertarDeuda.setText("");
                                        this.lblInsertarFechaSolicitada.setText("");
                                        this.lblInsertarID.setText("");
                                        this.lblInsertarNombre.setText("");
                                        tblResultados.clearSelection();
                                        cargarTablaObras();
                                        this.setVisible(true);
                                    } else {
                                        this.dispose();
                                        new PanelJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
                                    }
                                } catch (Exception ex) {
                                    Logger.getLogger(IniciarObra.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Error interno: Ocurrió un errror al querer realizar el pago.", "¡Error interno!", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error: Seleccione a tres obreros diferentes. (Ninguno repetido)", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Seleccione a tres obreros diferentes. (No deje inguno sin escoger)", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Seleccione un permiso válido de iniciación.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: La obra seleccionada es nula.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Seleccione una obra a la cual se le registrará el pago.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnIniciarObraActionPerformed

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar el inicio de obra? Los datos de inicio no se guardarán", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new PanelJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnRegresarActionPerformed

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
                this.cbxObrero1.setSelectedItem("Elija uno...");
                this.cbxObrero1.setEnabled(true);
                this.cbxObrero2.setSelectedItem("Elija uno...");
                this.cbxObrero2.setEnabled(true);
                this.cbxObrero3.setSelectedItem("Elija uno...");
                this.cbxObrero3.setEnabled(true);
            }
        } catch (Exception e) {
            Logger.getLogger(IniciarObra.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnAceptarSeleccionActionPerformed

    private void txtCostoArranqueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoArranqueKeyTyped
        if (txtCostoArranque.getText().length() >= 8) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        } else if (c == '.' && txtCostoArranque.getText().contains(".")) {
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
    }//GEN-LAST:event_txtCostoArranqueKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separador3;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnAceptarSeleccion;
    private javax.swing.JButton btnIniciarObra;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxObrero1;
    private javax.swing.JComboBox<String> cbxObrero2;
    private javax.swing.JComboBox<String> cbxObrero3;
    private javax.swing.JComboBox<String> cbxPermisos;
    private javax.swing.JLabel lbl$1;
    private javax.swing.JLabel lbl$2;
    private javax.swing.JLabel lblConMinimo1;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFechaSolicitada;
    private javax.swing.JLabel lblIDObra;
    private javax.swing.JLabel lblIniciarObra;
    private javax.swing.JLabel lblInsertarDeuda;
    private javax.swing.JLabel lblInsertarFechaSolicitada;
    private javax.swing.JLabel lblInsertarID;
    private javax.swing.JLabel lblInsertarNombre;
    private javax.swing.JLabel lblInstrucción;
    private javax.swing.JLabel lblMXN1;
    private javax.swing.JLabel lblMXN2;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObrero1;
    private javax.swing.JLabel lblObrero2;
    private javax.swing.JLabel lblObrero3;
    private javax.swing.JLabel lblPermiso;
    private javax.swing.JLabel lblPermisos;
    private javax.swing.JLabel lblPermisos1;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUbicacionSeleccionada;
    private javax.swing.JTable tblResultados;
    private javax.swing.JTextField txtCostoArranque;
    // End of variables declaration//GEN-END:variables
}
