/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Cliente;

import DAOs.ClientesDAO;
import DAOs.UbicacionesDAO;
import Dominio.Clientes;
import Dominio.Ubicaciones;
import Enumeradores.TipoUbicacion;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 *
 * @author 52644
 */
public class RegistrarUbicacion extends javax.swing.JFrame {

    // Atributos
    Clientes cliente = new Clientes();
    ClientesDAO ClientesDAO = new ClientesDAO();
    UbicacionesDAO UbicacionesDAO = new UbicacionesDAO();
    Validadores valido = new Validadores();

    /**
     * Creates new form RegistrarUbicación
     */
    public RegistrarUbicacion(Clientes cliente) {
        this.cliente = cliente;
        UIManager.put("OptionPane.yesButtonText", "Aceptar");
        UIManager.put("OptionPane.noButtonText", "Cancelar");
        initComponents();
        new Icono().insertarIcono(this);
    }

    public void corregirTamaños() {
        if (this.txtDireccion.getText().length() > 150) {
            this.txtDireccion.setText(this.txtDireccion.getText().substring(0, 150));
        }
        if (this.txtLargo.getText().length() > 5) {
            this.txtLargo.setText(this.txtLargo.getText().substring(0, 6));
        }
        if (this.txtAncho.getText().length() > 5) {
            this.txtAncho.setText(this.txtAncho.getText().substring(0, 6));
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

        Separador1 = new javax.swing.JSeparator();
        cbxTipo = new javax.swing.JComboBox<>();
        btnRegistrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtAncho = new javax.swing.JTextField();
        txtLargo = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        UObraLogoPeque = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblTipo = new javax.swing.JLabel();
        lblLargo = new javax.swing.JLabel();
        lblAncho = new javax.swing.JLabel();
        ImagenMapa = new javax.swing.JLabel();
        lblMetros1 = new javax.swing.JLabel();
        lblMetros2 = new javax.swing.JLabel();
        lblMetros3 = new javax.swing.JLabel();
        lblMetros4 = new javax.swing.JLabel();
        lblMetros5 = new javax.swing.JLabel();
        lblBusqueda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Registrar Ubicación");
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        setResizable(false);

        cbxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno...", "Solar", "Terreno" }));
        cbxTipo.setToolTipText("Elija un tipo de ubicación");
        cbxTipo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistrar.setText("Registrar");
        btnRegistrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar el registro de ubicación");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        txtAncho.setToolTipText("Ingrese en metros (m)");
        txtAncho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAnchoKeyTyped(evt);
            }
        });

        txtLargo.setToolTipText("Ingrese en metros (m)");
        txtLargo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtLargoKeyTyped(evt);
            }
        });

        txtDireccion.setToolTipText("Max. 150 caracteres");
        txtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDireccionKeyTyped(evt);
            }
        });

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Registrar Ubicación");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion.setText("Dirección:");

        lblTipo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipo.setText("Tipo:");

        lblLargo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblLargo.setText("Largo:");

        lblAncho.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblAncho.setText("Ancho:");

        ImagenMapa.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenMapa.png")); // NOI18N
        ImagenMapa.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblMetros1.setText("Ingrese medidas de largo y ancho...");

        lblMetros2.setText("metros");
        lblMetros2.setToolTipText("Ingrese en metros (m)");

        lblMetros3.setText("metros");

        lblMetros4.setText("Ingrese la dirección de la ubicación...");

        lblMetros5.setText("Elija un tipo de ubicación...");

        lblBusqueda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBusqueda.setText("Registro:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UObraLogoPeque)
                        .addGap(1, 1, 1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(Separador1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 502, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(btnRegistrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelar)
                                .addGap(10, 10, 10))
                            .addComponent(lblBusqueda, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDireccion)
                                    .addComponent(lblTipo, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblLargo, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblAncho, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblMetros4)
                                        .addGap(225, 225, 225))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lblMetros1)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                            .addComponent(txtLargo)
                                                            .addComponent(txtAncho, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(lblMetros3)
                                                            .addComponent(lblMetros2)))
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(cbxTipo, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblMetros5, javax.swing.GroupLayout.Alignment.LEADING)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(ImagenMapa)))
                                        .addGap(8, 8, 8)))))))
                .addGap(25, 25, 25))
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
                .addComponent(lblMetros4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDireccion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblMetros5)
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTipo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMetros1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtLargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblLargo)
                            .addComponent(lblMetros3))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAncho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblAncho)
                            .addComponent(lblMetros2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ImagenMapa, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnRegistrar))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar el registro de ubicación? Los datos de registro no se guardarán", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDireccionKeyTyped
        if (txtDireccion.getText().length() >= 150) {
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
    }//GEN-LAST:event_txtDireccionKeyTyped

    private void txtLargoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLargoKeyTyped
        if (txtLargo.getText().length() >= 5) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        } else if (c == '.' && txtLargo.getText().contains(".")) {
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
    }//GEN-LAST:event_txtLargoKeyTyped

    private void txtAnchoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnchoKeyTyped
        if (txtAncho.getText().length() >= 5) {
            evt.consume();
        }
        char c = evt.getKeyChar();
        if (Character.isLetter(c)) {
            evt.consume();
        } else if (c == '.' && txtAncho.getText().contains(".")) {
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
    }//GEN-LAST:event_txtAnchoKeyTyped

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        corregirTamaños();
        if (!txtDireccion.getText().isBlank()) {
            txtDireccion.setText(txtDireccion.getText().trim());
            if (!(cbxTipo.getSelectedItem() == "Elija uno...")) {
                if (!txtLargo.getText().isBlank()) {
                    if (!txtAncho.getText().isBlank()) {
                        // Se formatea largo y ancho
                        txtLargo.setText(valido.corregirFlotante(txtLargo.getText()));
                        txtAncho.setText(valido.corregirFlotante(txtAncho.getText()));
                        if (Float.parseFloat(txtLargo.getText()) <= 1) {
                            txtLargo.setText("1.0");
                        }
                        if (Float.parseFloat(txtAncho.getText()) <= 1) {
                            txtAncho.setText("1.0");
                        }
                        // Se determina tipo de ubicación
                        TipoUbicacion tipo;
                        if (cbxTipo.getSelectedItem() == "Solar") {
                            tipo = TipoUbicacion.SOLAR;
                        } else {
                            tipo = TipoUbicacion.TERRENO;
                        }
                        // Se crea el objeto ubicación a persistir
                        Ubicaciones ubicacion = new Ubicaciones(
                                txtDireccion.getText(),
                                tipo,
                                Float.valueOf(txtLargo.getText()),
                                Float.valueOf(txtAncho.getText()),
                                this.cliente);
                        Ubicaciones ubicacionRegistrada = UbicacionesDAO.registrarUbicacion(ubicacion);
                        if (ubicacionRegistrada.getId() != null) {
                            this.setVisible(false);
                            int i = JOptionPane.showConfirmDialog(null,
                                    "Se creó exitosamente la ubicación con..."
                                    + "\n Dirección: " + ubicacionRegistrada.getDireccion()
                                    + "\n Ancho: " + ubicacionRegistrada.getAncho() + " m"
                                    + "\n Largo: " + ubicacionRegistrada.getLargo() + " m"
                                    + "\n Área: " + ubicacionRegistrada.getArea() + " m²"
                                    + "\n - ID Cliente: " + ubicacionRegistrada.getCliente().getId()
                                    + "\n - ID: " + ubicacionRegistrada.getId()
                                    + ". ☺\n"
                                    + "\n ¿Desea registrar otra ubicación?", "Registro de ubicación exitoso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                            if (i == JOptionPane.YES_OPTION) {
                                this.txtDireccion.setText("");
                                this.txtAncho.setText("");
                                this.txtLargo.setText("");
                                this.cbxTipo.setSelectedItem("Elija uno...");
                                this.setVisible(true);
                            } else {
                                this.dispose();
                                new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Error interno: Ocurrió un errror al querer registrar la ubicación.", "¡Error interno!", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Ingrese un ancho de ubicación válido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error: Ingrese un largo de ubicación válido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Ingrese un tipo de ubicación válido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Ingrese una dirección de la ubicación a registrar.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegistrarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ImagenMapa;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> cbxTipo;
    private javax.swing.JLabel lblAncho;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblLargo;
    private javax.swing.JLabel lblMetros1;
    private javax.swing.JLabel lblMetros2;
    private javax.swing.JLabel lblMetros3;
    private javax.swing.JLabel lblMetros4;
    private javax.swing.JLabel lblMetros5;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtAncho;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtLargo;
    // End of variables declaration//GEN-END:variables
}
