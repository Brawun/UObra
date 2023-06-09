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
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author 52644
 */
public class EditarContrasenaCliente extends javax.swing.JFrame {

    // Atributos
    Clientes cliente = new Clientes();
    ClientesDAO ClientesDAO = new ClientesDAO();

    /**
     * Crea un nuevo frame EditarContrasenaCliente
     *
     * @param cliente Cliente de cuenta iniciada
     * @throws Exception En caso que haya problemas con la desencriptación
     */
    public EditarContrasenaCliente(Clientes cliente) throws Exception {
        Encriptador crypt = new Encriptador();
        initComponents();
        new Icono().insertarIcono(this);
        this.cliente = cliente;
        this.txtNuevaContrasenia.setText("");
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
        lblTitulo = new javax.swing.JLabel();
        lblUsuarioActual = new javax.swing.JLabel();
        lblContraseniaActual = new javax.swing.JLabel();
        lblInsertarUsuario = new javax.swing.JLabel();
        lblInsertarContrasenia = new javax.swing.JLabel();
        lblNuevaContrasenia = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtNuevaContrasenia = new javax.swing.JPasswordField();
        chbVerContrasenia = new javax.swing.JCheckBox();
        UObraLogoPeque = new javax.swing.JLabel();
        ImagenCandado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Editar Contraseña");
        setResizable(false);

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Editar Contraseña");

        lblUsuarioActual.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblUsuarioActual.setText("Usuario actual:");

        lblContraseniaActual.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblContraseniaActual.setText("Contraseña actual:");

        lblInsertarUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarUsuario.setText("usuario actual");

        lblInsertarContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarContrasenia.setText("contraseña actual");

        lblNuevaContrasenia.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNuevaContrasenia.setText("Nueva contraseña:");

        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.setToolTipText("Actualizar contraseña");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar cambio de contraseña");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtNuevaContrasenia.setToolTipText("Max. 20 caracteres");
        txtNuevaContrasenia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNuevaContraseniaKeyTyped(evt);
            }
        });

        chbVerContrasenia.setText("👁");
        chbVerContrasenia.setToolTipText("Ver contraseña");
        chbVerContrasenia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        chbVerContrasenia.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chbVerContraseniaStateChanged(evt);
            }
        });

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        ImagenCandado.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenCandado.png")); // NOI18N

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
                            .addGroup(layout.createSequentialGroup()
                                .addGap(130, 130, 130)
                                .addComponent(btnActualizar)
                                .addGap(66, 66, 66)
                                .addComponent(btnCancelar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblNuevaContrasenia)
                                .addGap(18, 18, 18)
                                .addComponent(txtNuevaContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chbVerContrasenia))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblTitulo)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(UObraLogoPeque))
                                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(12, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblUsuarioActual)
                            .addComponent(lblContraseniaActual))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInsertarUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblInsertarContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ImagenCandado)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUsuarioActual)
                            .addComponent(lblInsertarUsuario))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblContraseniaActual)
                            .addComponent(lblInsertarContrasenia)))
                    .addComponent(ImagenCandado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Separator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNuevaContrasenia)
                    .addComponent(txtNuevaContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chbVerContrasenia))
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
        this.txtNuevaContrasenia.setText(this.txtNuevaContrasenia.getText().trim());
        if (!this.txtNuevaContrasenia.getText().isBlank() && valida.validarSinEspacios(this.txtNuevaContrasenia.getText())) {
            try {
                Encriptador crypt = new Encriptador();
                if (!crypt.decrypt(this.cliente.getContrasena()).equals(new String(this.txtNuevaContrasenia.getPassword()))) {
                    try {
                        if (this.txtNuevaContrasenia.getText().length() > 20) {
                            this.txtNuevaContrasenia.setText(this.txtNuevaContrasenia.getText().substring(0, 20));
                        }
                        ClientesDAO.editarContrasena(this.cliente.getId(), this.txtNuevaContrasenia.getText());
                        JOptionPane.showMessageDialog(null, "Se ha actualizado la contraseña exitosamente.", "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
                        this.dispose();
                    } catch (Exception ex) {
                        Logger.getLogger(EditarContrasenaCliente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: La nueva contraseña no puede ser la misma a la ya existente. (Intente con otra)", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                Logger.getLogger(EditarContrasenaCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Ingrese una nueva contraseña. (Contraseña en blanco o con espacios en blanco)", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void chbVerContraseniaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chbVerContraseniaStateChanged
        if (this.chbVerContrasenia.isSelected()) {
            this.txtNuevaContrasenia.setEchoChar((char) 0);
        } else {
            this.txtNuevaContrasenia.setEchoChar('•');
        }
    }//GEN-LAST:event_chbVerContraseniaStateChanged

    private void txtNuevaContraseniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNuevaContraseniaKeyTyped
        if (txtNuevaContrasenia.getText().length() >= 20) {
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
    }//GEN-LAST:event_txtNuevaContraseniaKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ImagenCandado;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JSeparator Separator2;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JCheckBox chbVerContrasenia;
    private javax.swing.JLabel lblContraseniaActual;
    private javax.swing.JLabel lblInsertarContrasenia;
    private javax.swing.JLabel lblInsertarUsuario;
    private javax.swing.JLabel lblNuevaContrasenia;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuarioActual;
    private javax.swing.JPasswordField txtNuevaContrasenia;
    // End of variables declaration//GEN-END:variables
}
