/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Cliente;

import DAOs.ClientesDAO;
import DAOs.ObrasDAO;
import Dominio.Clientes;
import Dominio.Obras;
import Enumeradores.EstadoObra;
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
public class ConsultarObras extends javax.swing.JFrame {

    // Atributos
    Clientes cliente = new Clientes();
    ClientesDAO ClientesDAO = new ClientesDAO();
    ObrasDAO ObrasDAO = new ObrasDAO();

    /**
     * Creates new form ConsultarObras
     */
    public ConsultarObras(Clientes cliente) {
        this.cliente = cliente;
        initComponents();
        new Icono().insertarIcono(this);
        this.txtCostoTotal.setText("0.0");
    }

    public void cargarTablaObras(Integer fechas) throws Exception {
        List<Obras> listaObras;
        Fecha fecha = new Fecha();
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
        if (this.cbxEstado.getSelectedItem() == "Indistinto") {
            estado = null;
        } else if (this.cbxEstado.getSelectedItem() == "En espera") {
            estado = EstadoObra.EN_ESPERA;
        } else if (this.cbxEstado.getSelectedItem() == "Desarrollo") {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScrollPanel = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        cbxEstado = new javax.swing.JComboBox<>();
        cbxAccion = new javax.swing.JComboBox<>();
        cbxEconomia = new javax.swing.JComboBox<>();
        btnRegresar = new javax.swing.JButton();
        btnBuscar = new javax.swing.JButton();
        periodoInicio = new com.toedter.calendar.JDateChooser();
        periodoFinal = new com.toedter.calendar.JDateChooser();
        Separador1 = new javax.swing.JSeparator();
        Separador2 = new javax.swing.JSeparator();
        txtCostoTotal = new javax.swing.JTextField();
        UObraLogoPeque = new javax.swing.JLabel();
        Imagen = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        lblCosto = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();
        lblAccion = new javax.swing.JLabel();
        lblQue = new javax.swing.JLabel();
        lblEnUnPeriodo = new javax.swing.JLabel();
        lblEnEstado = new javax.swing.JLabel();
        lblConMinimo = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        lblBusqueda = new javax.swing.JLabel();
        lblFechaFin = new javax.swing.JLabel();
        lblDeuda = new javax.swing.JLabel();
        lblEconomia = new javax.swing.JLabel();
        lbl$ = new javax.swing.JLabel();
        lblMXN = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Consultar Obras ");
        setResizable(false);

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

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Indistinto", "En espera", "Desarrollo", "Teminada" }));
        cbxEstado.setToolTipText("Elija un estado");
        cbxEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbxAccion.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Solicitada", "Iniciada", "Terminada" }));
        cbxAccion.setToolTipText("Elija una acción");
        cbxAccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        cbxEconomia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Indistinto", "Pagadas", "No pagadas" }));
        cbxEconomia.setToolTipText("Elija una economía");
        cbxEconomia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnRegresar.setText("Regresar");
        btnRegresar.setToolTipText("Regresar a Panel Cliente");
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

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

        txtCostoTotal.setToolTipText("Ingrese números decimales");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        Imagen.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenObra.png")); // NOI18N

        lblEstado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEstado.setText("Estado:");

        lblCosto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCosto.setText("Costo:");

        lblResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResultado.setText("Resultado:");

        lblAccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAccion.setText("Acción:");

        lblQue.setText("Obra que haya sido...");

        lblEnUnPeriodo.setText("En un periodo de...");

        lblEnEstado.setText("Se encuentren en estado...");

        lblConMinimo.setText("Con un costo total mínimo de...");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Obras");

        lblFechaInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaInicio.setText("Fecha inicio:");

        lblBusqueda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBusqueda.setText("Búsqueda Dinámica:");

        lblFechaFin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaFin.setText("Fecha fin:");

        lblDeuda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDeuda.setText("Deuda:");

        lblEconomia.setText("Que económicamente estén...");

        lbl$.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$.setText("$");

        lblMXN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN.setText("MXN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblTitulo)
                        .addGap(709, 709, 709)
                        .addComponent(UObraLogoPeque))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
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
                            .addComponent(lblEstado)
                            .addComponent(lblDeuda)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(lblCosto)))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(lblEnEstado))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lblEconomia)
                            .addComponent(lblConMinimo)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(lbl$)
                                .addGap(6, 6, 6)
                                .addComponent(txtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(lblMXN))
                            .addComponent(btnBuscar)
                            .addComponent(cbxEconomia, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(Imagen))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(Separador2, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblResultado))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(402, 402, 402)
                        .addComponent(btnRegresar)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblTitulo))
                    .addComponent(UObraLogoPeque))
                .addGap(6, 6, 6)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(periodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(lblFechaFin))
                            .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEstado)
                            .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDeuda)
                            .addComponent(cbxEconomia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addComponent(lblCosto))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(lblEnEstado)
                        .addGap(37, 37, 37)
                        .addComponent(lblEconomia)
                        .addGap(40, 40, 40)
                        .addComponent(lblConMinimo)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl$)
                            .addComponent(txtCostoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblMXN))
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar))
                    .addComponent(Imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(Separador2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addComponent(lblResultado)
                .addGap(12, 12, 12)
                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegresar)
                .addGap(20, 20, 20))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            Validadores valido = new Validadores();
            if (this.periodoFinal.getCalendar() != null && this.periodoInicio.getCalendar() != null) {
                if (valido.validarFechas(this.periodoInicio.getCalendar(), this.periodoFinal.getCalendar())) {
                    if (this.cbxAccion.getSelectedItem() != "Elija uno...") {
                        if (this.cbxEstado.getSelectedItem() != "Elija uno...") {
                            if (this.cbxEconomia.getSelectedItem() != "Elija uno...") {
                                // Se valida y formatea el campo de monto
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
                        if (this.cbxEconomia.getSelectedItem() != "Elija uno...") {
                            // Se valida y formatea el campo de monto
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
                        JOptionPane.showMessageDialog(null, "Error: Elija un estado de obra válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Elija un tipo de acción válido para la búsqueda dinámica.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ConsultarObras.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Imagen;
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separador2;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxAccion;
    private javax.swing.JComboBox<String> cbxEconomia;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JLabel lbl$;
    private javax.swing.JLabel lblAccion;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblConMinimo;
    private javax.swing.JLabel lblCosto;
    private javax.swing.JLabel lblDeuda;
    private javax.swing.JLabel lblEconomia;
    private javax.swing.JLabel lblEnEstado;
    private javax.swing.JLabel lblEnUnPeriodo;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblMXN;
    private javax.swing.JLabel lblQue;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private com.toedter.calendar.JDateChooser periodoFinal;
    private com.toedter.calendar.JDateChooser periodoInicio;
    private javax.swing.JTable tblResultados;
    private javax.swing.JTextField txtCostoTotal;
    // End of variables declaration//GEN-END:variables
}
