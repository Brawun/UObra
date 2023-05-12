/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe.Obras;

import DAOs.ClientesDAO;
import DAOs.JefesDAO;
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
import GUI.Jefe.PanelJefe;
import Herramientas.Encriptador;
import Herramientas.Fecha;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
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
    ObrasDAO ObrasDAO = new ObrasDAO();
    ObrasObreroDAO ObrasObreroDAO = new ObrasObreroDAO();
    JefesDAO JefesDAO = new JefesDAO();
    PermisosDAO PermisosDAO = new PermisosDAO();
    Validadores valido = new Validadores();
    Encriptador crypt = new Encriptador();
    Obras obra;

    /**
     * Creates new form TerminarObra
     */
    public TerminarObra(Jefes jefe) throws Exception {
        this.jefe = jefe;
        this.obra = null;
        UIManager.put("OptionPane.yesButtonText", "Aceptar");
        UIManager.put("OptionPane.noButtonText", "Cancelar");
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

        lblResultado = new javax.swing.JLabel();
        UObraLogoPeque = new javax.swing.JLabel();
        lblObrero = new javax.swing.JLabel();
        cbxPermisos = new javax.swing.JComboBox<>();
        lblTitulo = new javax.swing.JLabel();
        lblFechaSolicitada = new javax.swing.JLabel();
        Separador3 = new javax.swing.JSeparator();
        lblNombre = new javax.swing.JLabel();
        lbl$1 = new javax.swing.JLabel();
        lblMXN1 = new javax.swing.JLabel();
        ScrollPanel = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        lblUbicacionSeleccionada = new javax.swing.JLabel();
        lblPermisos = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        lblInsertarID = new javax.swing.JLabel();
        lblIDObra = new javax.swing.JLabel();
        lblInstrucción = new javax.swing.JLabel();
        lblInsertarNombre = new javax.swing.JLabel();
        lblInsertarFechaSolicitada = new javax.swing.JLabel();
        lblInsertarDeuda = new javax.swing.JLabel();
        Separador1 = new javax.swing.JSeparator();
        btnFinalizarObra = new javax.swing.JButton();
        lblIniciarObra = new javax.swing.JLabel();
        btnAceptarSeleccion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Terminar Obra");
        setResizable(false);

        lblResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResultado.setText("Resultado:");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblObrero.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblObrero.setText("Permiso finalización:");

        cbxPermisos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxPermisos.setToolTipText("Elija un permiso");
        cbxPermisos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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

        lblUbicacionSeleccionada.setText("Obra seleccionada:");

        lblPermisos.setText("Seleccione el permiso finalización...");

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

        lblInstrucción.setText("Seleccione una obra para realizar un pago");

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
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar la terminación de obra? Los datos de terminación no se guardarán", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new PanelJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
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
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separador3;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnAceptarSeleccion;
    private javax.swing.JButton btnFinalizarObra;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<String> cbxPermisos;
    private javax.swing.JLabel lbl$1;
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
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblObrero;
    private javax.swing.JLabel lblPermisos;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUbicacionSeleccionada;
    private javax.swing.JTable tblResultados;
    // End of variables declaration//GEN-END:variables
}
