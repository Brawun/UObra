/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe.Planos;

import DAOs.JefesDAO;
import DAOs.PlanosDAO;
import Dominio.Jefes;
import Dominio.Planos;
import Enumeradores.Escala;
import Enumeradores.TipoPlano;
import GUI.Jefe.PanelJefe;
import Herramientas.Encriptador;
import Herramientas.Fecha;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author 52644
 */
public class RegistrarPlano extends javax.swing.JFrame {

    // Atributos
    Jefes jefe;
    Fecha fecha = new Fecha();
    JefesDAO JefesDAO = new JefesDAO();
    PlanosDAO PlanosDAO = new PlanosDAO();
    Validadores valido = new Validadores();
    Encriptador crypt = new Encriptador();

    /**
     * Creates new form RegistrarPlano
     */
    public RegistrarPlano(Jefes jefe) {
        this.jefe = jefe;
        UIManager.put("OptionPane.yesButtonText", "Aceptar");
        UIManager.put("OptionPane.noButtonText", "Cancelar");
        initComponents();
        new Icono().insertarIcono(this);
        this.txtFolio.setText("######");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancelar = new javax.swing.JButton();
        fechaRealizacion = new com.toedter.calendar.JDateChooser();
        lblTipo = new javax.swing.JLabel();
        lblBusqueda = new javax.swing.JLabel();
        UObraLogoPeque = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        txtFolio = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        cbxTipo = new javax.swing.JComboBox<>();
        Separador1 = new javax.swing.JSeparator();
        lblFechaRealizado = new javax.swing.JLabel();
        lblTipoPlano = new javax.swing.JLabel();
        lblFolio = new javax.swing.JLabel();
        lblFechaRealizacion = new javax.swing.JLabel();
        lblEscala = new javax.swing.JLabel();
        cbxEscala = new javax.swing.JComboBox<>();
        lblEscalas = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Registrar Plano");
        setResizable(false);

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar el registro de plano");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        fechaRealizacion.setToolTipText("Periodo inicio");

        lblTipo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipo.setText("Tipo:");

        lblBusqueda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBusqueda.setText("Registro:");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.setToolTipText("Registrar plano");
        btnRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        txtFolio.setToolTipText("Max. 6 caracteres");
        txtFolio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFolioFocusLost(evt);
            }
        });
        txtFolio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFolioKeyTyped(evt);
            }
        });

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion.setText("Folio:");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Registrar Plano");

        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Ejecución", "Eléctrico", "Desague", "Ubicación" }));
        cbxTipo.setToolTipText("Elija un tipo de permiso");
        cbxTipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblFechaRealizado.setText("Ingrese una fecha de realización...");

        lblTipoPlano.setText("Elija un tipo de plano..");

        lblFolio.setText("Folio del permiso....");

        lblFechaRealizacion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaRealizacion.setText("Fecha realización:");

        lblEscala.setText("Elija una escala del plano...");

        cbxEscala.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "1:100", "1:500", "1:1000", "1:2000", "1:2500", "1:5000" }));
        cbxEscala.setToolTipText("Elija un tipo de permiso");
        cbxEscala.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblEscalas.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEscalas.setText("Escala:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(UObraLogoPeque))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(131, 131, 131)
                                .addComponent(btnRegistrar)
                                .addGap(42, 42, 42)
                                .addComponent(btnCancelar))
                            .addComponent(lblBusqueda)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblEscalas)
                                    .addComponent(lblTipo)
                                    .addComponent(lblFechaRealizacion)
                                    .addComponent(lblDireccion))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEscala)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lblFolio)
                                        .addComponent(lblFechaRealizado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtFolio)
                                        .addComponent(lblTipoPlano, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fechaRealizacion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbxTipo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cbxEscala, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitulo)
                    .addComponent(UObraLogoPeque))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBusqueda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblFolio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFolio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDireccion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTipoPlano)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblEscala)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxEscala, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEscalas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFechaRealizado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFechaRealizacion)
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCancelar)
                            .addComponent(btnRegistrar)))
                    .addComponent(fechaRealizacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar el registro de plano? Los datos de registro no se guardarán", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new PanelJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        if (this.cbxTipo.getSelectedItem() != "Elija uno...") {
            if (this.cbxEscala.getSelectedItem() != "Elija uno...") {
                if (this.fechaRealizacion.getCalendar() != null) {
                    if (valido.validarFechas(this.fechaRealizacion.getCalendar(), fecha.fechaAhora())) {
                        if (this.txtFolio.getText().length() > 6) {
                            this.txtFolio.setText(this.txtFolio.getText().substring(0, 6));
                        }
                        TipoPlano tipo;
                        if (this.cbxTipo.getSelectedItem() == "Ejecución") {
                            tipo = TipoPlano.EJECUCION;
                        } else if (this.cbxTipo.getSelectedItem() == "Eléctrico") {
                            tipo = TipoPlano.ELECTRICO;
                        } else if (this.cbxTipo.getSelectedItem() == "Desague") {
                            tipo = TipoPlano.DESAGUE;
                        } else {
                            tipo = TipoPlano.UBICACION;
                        }
                        Escala escala;
                        if (this.cbxEscala.getSelectedItem() == "1:100") {
                            escala = Escala.UNO_100;
                        } else if (this.cbxEscala.getSelectedItem() == "1:500") {
                            escala = Escala.UNO_500;
                        } else if (this.cbxEscala.getSelectedItem() == "1:1000") {
                            escala = Escala.UNO_1000;
                        } else if (this.cbxEscala.getSelectedItem() == "1:2000") {
                            escala = Escala.UNO_2000;
                        } else if (this.cbxEscala.getSelectedItem() == "1:2500") {
                            escala = Escala.UNO_2500;
                        } else {
                            escala = Escala.UNO_5000;
                        }
                        Planos plano = new Planos(
                                txtFolio.getText(), 
                                tipo, 
                                escala, 
                                fechaRealizacion.getCalendar(), 
                                this.jefe);
                        Planos planoRegistrado = PlanosDAO.registrarPlano(plano);
                        if (planoRegistrado.getId() != null) {
                           try {
                            int i = JOptionPane.showConfirmDialog(null,
                                    "Se realizó exitosamente el registro de plano con..."
                                    + "\n Folio: " + crypt.decrypt(planoRegistrado.getFolio())
                                    + "\n Tipo: " + planoRegistrado.getTipo().toString()
                                    + "\n Escala: " + planoRegistrado.getEscala().toString()
                                    + "\n Fecha de registro: " + fecha.formatoFecha(planoRegistrado.getFechaRegistro())
                                    + "\n Fecha de realizacion: " + fecha.formatoFecha(planoRegistrado.getFechaRealizacion())
                                    + "\n - ID Jefe: " + planoRegistrado.getJefe().getId()
                                    + "\n - ID: " + planoRegistrado.getId()
                                    + ". ☺\n"
                                    + "\n ¿Desea registrar otro plano?", "Registro de plano exitoso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                            if (i == JOptionPane.YES_OPTION) {
                                this.txtFolio.setText("######");
                                this.cbxEscala.setSelectedItem("Elija uno...");
                                this.cbxTipo.setSelectedItem("Elija uno...");
                                this.fechaRealizacion.setCalendar(null);
                                this.setVisible(true);
                            } else {
                                this.dispose();
                                new PanelJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(RegistrarPlano.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error interno: Ocurrió un errror al querer registrar el plano.", "¡Error interno!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: La fecha de realización no puede ser después que la fecha actual.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese una fecha de realización.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una escala válida.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Seleccione un tipo de plano válido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void txtFolioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyTyped
        if (txtFolio.getText().length() >= 6) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c) || !Character.isDigit(c)) {
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
    }//GEN-LAST:event_txtFolioKeyTyped

    private void txtFolioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFolioFocusLost
        if (txtFolio.getText().isBlank() || txtFolio.getText().length() < 6) {
            txtFolio.setText("######");
        }
    }//GEN-LAST:event_txtFolioFocusLost

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator Separador1;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cbxEscala;
    private javax.swing.JComboBox<String> cbxTipo;
    private com.toedter.calendar.JDateChooser fechaRealizacion;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblEscala;
    private javax.swing.JLabel lblEscalas;
    private javax.swing.JLabel lblFechaRealizacion;
    private javax.swing.JLabel lblFechaRealizado;
    private javax.swing.JLabel lblFolio;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTipoPlano;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtFolio;
    // End of variables declaration//GEN-END:variables
}
