/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Obrero;

import DAOs.ObrerosDAO;
import DAOs.PagosDAO;
import Dominio.Obreros;
import Dominio.Pagos;
import Enumeradores.MetodoPago;
import Herramientas.Fecha;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 52644
 */
public class PagosObrero extends javax.swing.JFrame {

    // Atributos
    Obreros obrero = new Obreros();
    ObrerosDAO ObrerosDAO = new ObrerosDAO();

    /**
     * Crea un nuevo frame PagosObrero
     *
     * @param obrero Obrero de cuenta iniciada
     */
    public PagosObrero(Obreros obrero) {
        this.obrero = obrero;
        initComponents();
        new Icono().insertarIcono(this);
        this.txtMonto.setText("0");
    }

    public void cargarTablaPagos() throws ParseException {
        Fecha fecha = new Fecha();
        List<Pagos> listaPagos = new PagosDAO().consultarPagos(
                this.periodoInicio.getCalendar() != null ? this.periodoInicio.getCalendar() : null,
                this.periodoFinal.getCalendar() != null ? this.periodoFinal.getCalendar() : null,
                MetodoPago.EFECTIVO,
                this.txtMonto.getText().equals("0") ? (float) 0 : Float.valueOf(this.txtMonto.getText()),
                new ObrerosDAO().consultarObrero(obrero.getId()),
                null,
                null);
        DefaultTableModel modeloTablaPagos = (DefaultTableModel) this.tblPagos.getModel();
        modeloTablaPagos.setRowCount(0);
        for (Pagos pagos : listaPagos) {
            Object[] filaNueva = {pagos.getId(),
                 "$ " + pagos.getMonto() + " MXN",
                 fecha.formatoFecha(pagos.getFecha()),
                 pagos.getObra().getId()};
            modeloTablaPagos.addRow(filaNueva);
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

        lblBusqueda = new javax.swing.JLabel();
        ScrollPanel = new javax.swing.JScrollPane();
        tblPagos = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        Separador1 = new javax.swing.JSeparator();
        UObraLogoPeque = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblResultado = new javax.swing.JLabel();
        lblMonto = new javax.swing.JLabel();
        lblEnUnPeriodo = new javax.swing.JLabel();
        lblConMinimo = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        periodoInicio = new com.toedter.calendar.JDateChooser();
        periodoFinal = new com.toedter.calendar.JDateChooser();
        txtMonto = new javax.swing.JTextField();
        lblFechaInicio = new javax.swing.JLabel();
        Separador2 = new javax.swing.JSeparator();
        lblFechaFin = new javax.swing.JLabel();
        lbl$ = new javax.swing.JLabel();
        lblMXN = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Pagos Obrero");

        lblBusqueda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBusqueda.setText("Búsqueda Dinámica:");

        ScrollPanel.setToolTipText("");

        tblPagos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Monto", "Fecha", "ID Obra"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Float.class, java.lang.Object.class, java.lang.Long.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPagos.setRequestFocusEnabled(false);
        ScrollPanel.setViewportView(tblPagos);

        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Pagos Obrero");

        lblResultado.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblResultado.setText("Pagos recibidos en efectivo:");

        lblMonto.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMonto.setText("Monto:");

        lblEnUnPeriodo.setText("En un periodo de...");

        lblConMinimo.setText("Con un monto mínimo de...");

        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        lblFechaInicio.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaInicio.setText("Fecha inicio:");

        lblFechaFin.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaFin.setText("Fecha fin:");

        lbl$.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$.setText("$");

        lblMXN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN.setText("MXN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addGap(220, 220, 220))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblBusqueda)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Separador2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Separador1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(UObraLogoPeque))
                            .addComponent(ScrollPanel)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblResultado)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(21, 21, 21))))
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblFechaFin)
                        .addGap(18, 18, 18)
                        .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 286, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFechaInicio)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblEnUnPeriodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblConMinimo)
                                .addGap(61, 61, 61))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(periodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addComponent(lblMonto)
                                .addGap(15, 15, 15)
                                .addComponent(lbl$)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                    .addComponent(txtMonto))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMXN)
                                .addGap(0, 0, Short.MAX_VALUE))))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitulo)
                    .addComponent(UObraLogoPeque))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblBusqueda)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEnUnPeriodo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFechaInicio)
                                .addGap(11, 11, 11))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(periodoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblConMinimo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMonto)
                            .addComponent(txtMonto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl$)
                            .addComponent(lblMXN))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblFechaFin)
                        .addComponent(periodoFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(Separador2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblResultado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnRegresar)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        new PanelObrero(ObrerosDAO.consultarObrero(this.obrero.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            Validadores valido = new Validadores();
            if (valido.validarFechas(this.periodoInicio.getCalendar(), this.periodoFinal.getCalendar())) {
                if (this.txtMonto.getText().isBlank()) {
                    this.txtMonto.setText("0.0");
                } else if (!valido.validarFlotante(this.txtMonto.getText())) {
                    this.txtMonto.setText(this.txtMonto.getText().trim() + ".0");
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Monto mínimo a buscar inválido. (Mal formato).", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
                if (valido.validarFlotante(this.txtMonto.getText()) && valido.validarSinEspacios(this.txtMonto.getText())) {
                    this.cargarTablaPagos();
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Monto mínimo a buscar inválido. (Con espacios).", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: La fecha inicial no puede ser después que la fecha final.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            Logger.getLogger(ConsultaObrasObrero.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separador2;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel lbl$;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblConMinimo;
    private javax.swing.JLabel lblEnUnPeriodo;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblMXN;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JLabel lblTitulo;
    private com.toedter.calendar.JDateChooser periodoFinal;
    private com.toedter.calendar.JDateChooser periodoInicio;
    private javax.swing.JTable tblPagos;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}
