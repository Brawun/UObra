/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe;

import GUI.Jefe.*;
import DAOs.JefesDAO;
import Dominio.Jefes;
import Herramientas.Encriptador;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author 52644
 */
public class EditarUsuarioJefe extends javax.swing.JFrame {

    // Atributos
    Jefes Jefe = new Jefes();
    JefesDAO JefesDAO = new JefesDAO();

    /**
     * Crea un nuevo frame EditarUsuarioJefe
     *
     * @param Jefe Jefe de cuenta iniciada
     * @throws Exception En caso que haya problemas con la desencriptación
     */
    public EditarUsuarioJefe(Jefes Jefe) throws Exception {
        Encriptador crypt = new Encriptador();
        initComponents();
        new Icono().insertarIcono(this);
        this.Jefe = Jefe;
        this.lblInsertarContrasenia.setText(crypt.decrypt(Jefe.getContrasena()));
        this.lblInsertarUsuario.setText(crypt.decrypt(Jefe.getUsuario()));
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
        txtNuevoUsuario = new javax.swing.JTextField();
        ImagenUsuario = new javax.swing.JLabel();
        lblNuevoUsuario = new javax.swing.JLabel();
        lblInsertarContrasenia = new javax.swing.JLabel();
        lblUsuarioActual = new javax.swing.JLabel();
        lblInsertarUsuario = new javax.swing.JLabel();
        lblContraseniaActual = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        UObraLogoPeque = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Editar Usuario");

        txtNuevoUsuario.setToolTipText("Max. 20 caracteres");
        txtNuevoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoUsuarioKeyTyped(evt);
            }
        });

        ImagenUsuario.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenUsuario.png")); // NOI18N

        lblNuevoUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNuevoUsuario.setText("Nuevo usuario:");

        lblInsertarContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarContrasenia.setText("contraseña actual");

        lblUsuarioActual.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUsuarioActual.setText("Usuario actual:");

        lblInsertarUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarUsuario.setText("usuario actual");

        lblContraseniaActual.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblContraseniaActual.setText("Contraseña actual:");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Editar Usuario");

        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setToolTipText("Actualizar usuario");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar cambio de usuario");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblTitulo)
                        .addGap(176, 176, 176)
                        .addComponent(UObraLogoPeque))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(lblUsuarioActual))
                            .addComponent(lblContraseniaActual))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInsertarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblInsertarContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(ImagenUsuario))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(Separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(lblNuevoUsuario)
                        .addGap(18, 18, 18)
                        .addComponent(txtNuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(144, 144, 144)
                        .addComponent(btnActualizar)
                        .addGap(67, 67, 67)
                        .addComponent(btnCancelar)))
                .addContainerGap(13, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(UObraLogoPeque))
                .addGap(6, 6, 6)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblUsuarioActual)
                        .addGap(6, 6, 6)
                        .addComponent(lblContraseniaActual))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(lblInsertarUsuario))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblInsertarContrasenia))
                    .addComponent(ImagenUsuario))
                .addGap(9, 9, 9)
                .addComponent(Separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNuevoUsuario)
                    .addComponent(txtNuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnActualizar)
                    .addComponent(btnCancelar))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        Validadores valida = new Validadores();
        this.txtNuevoUsuario.setText(this.txtNuevoUsuario.getText().trim());
        if (!this.txtNuevoUsuario.getText().isBlank() && valida.validarSinEspacios(this.txtNuevoUsuario.getText())) {
            try {
                Encriptador crypt = new Encriptador();
                if (!crypt.decrypt(this.Jefe.getUsuario()).equals(this.txtNuevoUsuario.getText())) {
                    try {
                        if (this.txtNuevoUsuario.getText().length() > 20) {
                            this.txtNuevoUsuario.setText(this.txtNuevoUsuario.getText().substring(0, 20));
                        }
                        JefesDAO.editarUsuario(this.Jefe.getId(), this.txtNuevoUsuario.getText());
                        JOptionPane.showMessageDialog(null, "Se ha actualizado el usuario exitosamente.", "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                        new PanelJefe(JefesDAO.consultarJefe(this.Jefe.getId())).setVisible(true);
                        this.dispose();
                    } catch (Exception ex) {
                        Logger.getLogger(EditarContrasenaJefe.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: El nuevo usuario no puede ser el mismo al ya existente. (Intente con otro)", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                Logger.getLogger(EditarUsuarioJefe.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Ingrese un nuevo usuario. (Usuario en blanco o con espacios).", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        new PanelJefe(JefesDAO.consultarJefe(this.Jefe.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNuevoUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoUsuarioKeyTyped
        if (txtNuevoUsuario.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNuevoUsuarioKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ImagenUsuario;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separator2;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JLabel lblContraseniaActual;
    private javax.swing.JLabel lblInsertarContrasenia;
    private javax.swing.JLabel lblInsertarUsuario;
    private javax.swing.JLabel lblNuevoUsuario;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JTextField txtNuevoUsuario;
    // End of variables declaration//GEN-END:variables
}