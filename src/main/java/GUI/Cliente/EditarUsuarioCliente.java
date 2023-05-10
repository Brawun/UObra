/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Cliente;

import DAOs.ClientesDAO;
import Dominio.Clientes;
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
public class EditarUsuarioCliente extends javax.swing.JFrame {

    // Atributos
    Clientes cliente = new Clientes();
    ClientesDAO ClientesDAO = new ClientesDAO();

    /**
     * Crea un nuevo frame EditarUsuarioCliente
     *
     * @param cliente Cliente de cuenta iniciada
     * @throws Exception En caso que haya problemas con la desencriptación
     */
    public EditarUsuarioCliente(Clientes cliente) throws Exception {
        Encriptador crypt = new Encriptador();
        initComponents();
        new Icono().insertarIcono(this);
        this.cliente = cliente;
        this.lblInsertarContrasenia.setText(crypt.decrypt(cliente.getContrasena()));
        this.lblInsertarUsuario.setText(crypt.decrypt(cliente.getUsuario()));
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
        setResizable(false);

        txtNuevoUsuario.setToolTipText("Max. 20 caracteres");
        txtNuevoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevoUsuarioKeyTyped(evt);
            }
        });

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
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(130, 130, 130)
                                    .addComponent(btnActualizar)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCancelar))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addGap(25, 25, 25)
                                    .addComponent(lblNuevoUsuario)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtNuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(lblTitulo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(UObraLogoPeque))
                                .addComponent(Separador1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblUsuarioActual)
                            .addComponent(lblContraseniaActual))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInsertarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblInsertarContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(UObraLogoPeque))
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
                    .addComponent(lblNuevoUsuario)
                    .addComponent(txtNuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActualizar)
                    .addComponent(btnCancelar))
                .addGap(16, 16, 16))
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
                if (!crypt.decrypt(this.cliente.getUsuario()).equals(this.txtNuevoUsuario.getText())) {
                    try {
                        ClientesDAO.editarUsuario(this.cliente.getId(), this.txtNuevoUsuario.getText());
                        JOptionPane.showMessageDialog(null, "Se ha actualizado el usuario exitosamente.");
                        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
                        this.dispose();
                    } catch (Exception ex) {
                        Logger.getLogger(EditarContrasenaCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: El nuevo usuario no puede ser el mismo al ya existente. (Intente con otro)", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                Logger.getLogger(EditarUsuarioCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Ingrese un nuevo usuario. (Usuario en blanco).", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtNuevoUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevoUsuarioKeyTyped
        if (txtNuevoUsuario.getText().length() >= 20) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNuevoUsuarioKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
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
