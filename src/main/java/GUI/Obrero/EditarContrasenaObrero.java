/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Obrero;

import DAOs.ObrerosDAO;
import Dominio.Obreros;
import Herramientas.Encriptador;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 52644
 */
public class EditarContrasenaObrero extends javax.swing.JFrame {
    
    // Atributos
    Obreros obrero = new Obreros();
    
    /**
     * Creates new form EditarContrasenaObrero
     */
    public EditarContrasenaObrero(Obreros obrero) throws Exception {
        Encriptador crypt = new Encriptador();
        this.obrero = obrero;
        this.lblInsertarContrasenia.setText(crypt.decrypt(obrero.getContrasena()));
        this.lblInsertarUsuario.setText(crypt.decrypt(obrero.getUsuario()));
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Separador1 = new javax.swing.JSeparator();
        Separator2 = new javax.swing.JSeparator();
        lblTitulo = new javax.swing.JLabel();
        lblUsuarioActual = new javax.swing.JLabel();
        lblContraseniaActual = new javax.swing.JLabel();
        lblInsertarUsuario = new javax.swing.JLabel();
        lblInsertarContrasenia = new javax.swing.JLabel();
        lblNuevaContrasenia = new javax.swing.JLabel();
        txtNuevaContrasenia = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Editar Contraseña");

        lblUsuarioActual.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUsuarioActual.setText("Usuario actual");

        lblContraseniaActual.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblContraseniaActual.setText("Contraseña actual");

        lblInsertarUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarUsuario.setText("usuario actual");

        lblInsertarContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarContrasenia.setText("contraseña actual");

        lblNuevaContrasenia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNuevaContrasenia.setText("Nueva contraseña");

        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(lblUsuarioActual)
                        .addGap(18, 18, 18)
                        .addComponent(lblInsertarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblContraseniaActual)
                                .addGap(18, 18, 18)
                                .addComponent(lblInsertarContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTitulo)
                                    .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGap(130, 130, 130)
                                            .addComponent(btnActualizar)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnCancelar))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addComponent(lblNuevaContrasenia)
                                            .addGap(18, 18, 18)
                                            .addComponent(txtNuevaContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuarioActual)
                    .addComponent(lblInsertarUsuario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContraseniaActual)
                    .addComponent(lblInsertarContrasenia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNuevaContrasenia)
                    .addComponent(txtNuevaContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnCancelar))
                .addGap(16, 16, 16))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        if (!this.txtNuevaContrasenia.getText().isBlank()) {
            try {
                ObrerosDAO obrerosDAO = new ObrerosDAO();
                obrerosDAO.editarContrasena(this.obrero.getId(), this.txtNuevaContrasenia.getText());
                JOptionPane.showMessageDialog(null, "Se ha actualizado la contraseña exitosamente.");
                new PanelObrero(this.obrero).setVisible(true);
                this.dispose();
            } catch (Exception ex) {
                Logger.getLogger(EditarContrasenaObrero.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Ingrese una nueva contraseña. (Contraseña en blanco).");
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        new PanelObrero(this.obrero).setVisible(true);    
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separator2;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblContraseniaActual;
    private javax.swing.JLabel lblInsertarContrasenia;
    private javax.swing.JLabel lblInsertarUsuario;
    private javax.swing.JLabel lblNuevaContrasenia;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JTextField txtNuevaContrasenia;
    // End of variables declaration//GEN-END:variables
}
