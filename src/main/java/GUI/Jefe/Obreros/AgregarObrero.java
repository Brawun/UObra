/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe.Obreros;

import DAOs.ClientesDAO;
import DAOs.FacturasDAO;
import DAOs.JefesDAO;
import DAOs.ObrasDAO;
import DAOs.ObrerosDAO;
import Dominio.Clientes;
import Dominio.Jefes;
import Dominio.Obras;
import Dominio.ObrasObrero;
import Dominio.Obreros;
import Enumeradores.EstadoObra;
import GUI.Jefe.PanelJefe;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author 52644
 */
public class AgregarObrero extends javax.swing.JFrame {

    // Atributos
    Jefes jefe;
    ObrasDAO ObrasDAO = new ObrasDAO();
    JefesDAO JefesDAO = new JefesDAO();
    ObrerosDAO ObrerosDAO = new ObrerosDAO();
    Validadores valido = new Validadores();

    /**
     * Creates new form AgregarObrero
     */
    public AgregarObrero(Jefes jefe) throws Exception {
        this.jefe = jefe;
        UIManager.put("OptionPane.yesButtonText", "Aceptar");
        UIManager.put("OptionPane.noButtonText", "Cancelar");
        initComponents();
        new Icono().insertarIcono(this);
        List<Obras> obras = ObrasDAO.consultarObrasFechaInicio(null, null, EstadoObra.DESARROLLO, null, null, this.jefe.getId());
        for (Obras obra : obras) {
            this.cbxObra.addItem(
                    obra.getNombre()
                    + " - ID Cliente: " + obra.getCliente().getId()
                    + " - ID: " + obra.getId());
        }
        List<Obreros> obreros = ObrerosDAO.consultarTodosObreros();
        for (Obreros obrero : obreros) {
            this.cbxObrero.addItem(
                    obrero.getNombre()
                    + " " + obrero.getApellidoPaterno()
                    + " " + obrero.getApellidoMaterno()
                    + " - ID: " + obrero.getId());
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

        cbxObrero = new javax.swing.JComboBox<>();
        btnAgregar = new javax.swing.JButton();
        lblElijaMetodo = new javax.swing.JLabel();
        UObraLogoPeque = new javax.swing.JLabel();
        lblMetodoPago = new javax.swing.JLabel();
        lblBusqueda = new javax.swing.JLabel();
        lblFactura = new javax.swing.JLabel();
        lblElijaFactura = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        Separador1 = new javax.swing.JSeparator();
        cbxObra = new javax.swing.JComboBox<>();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Agregar Obrero");
        setResizable(false);

        cbxObrero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxObrero.setToolTipText("Elija un tipo de permiso");
        cbxObrero.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setToolTipText("Agregar Obrero");
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        lblElijaMetodo.setText("Elija un obrero...");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblMetodoPago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMetodoPago.setText("Obrero:");

        lblBusqueda.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblBusqueda.setText("Registro:");

        lblFactura.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFactura.setText("Obra:");

        lblElijaFactura.setText("Elija una obra..");

        btnCancelar.setText("Cancelar");
        btnCancelar.setToolTipText("Cancelar agregar obrero");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cbxObra.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija una..." }));
        cbxObra.setToolTipText("Elija un tipo de permiso");
        cbxObra.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Agregar Obrero");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAgregar)
                .addGap(48, 48, 48)
                .addComponent(btnCancelar)
                .addGap(17, 17, 17))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblBusqueda))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(UObraLogoPeque))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 363, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMetodoPago)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblElijaMetodo)
                                    .addComponent(cbxObrero, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFactura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblElijaFactura)
                                    .addComponent(cbxObra, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))))
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
                .addComponent(lblElijaFactura)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFactura))
                .addGap(18, 18, 18)
                .addComponent(lblElijaMetodo)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxObrero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMetodoPago))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAgregar))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (this.cbxObra.getSelectedItem() != "Elija una...") {
            if (this.cbxObrero.getSelectedItem() != "Elija uno...") {
                // Obrero
                String ObreroElegido = this.cbxObrero.getSelectedItem().toString();
                String idElegido = ObreroElegido.substring(ObreroElegido.length(), 3);
                idElegido = valido.obtenerNumeros(idElegido);
                Long id = Long.valueOf(idElegido);
                Obreros obrero = ObrerosDAO.consultarObrero(id);
                // Obra
                String ObraElegida = this.cbxObra.getSelectedItem().toString();
                String idElegida = ObraElegida.substring(ObraElegida.length(), 3);
                idElegida = valido.obtenerNumeros(idElegida);
                Long ida = Long.valueOf(idElegida);
                Obras obra = ObrasDAO.consultarObra(ida);
                List<ObrasObrero> obrasO = obra.getObreros();
                for (ObrasObrero obrasObrero : obrasO) {
                    if (obrasObrero.getObrero().equals(obrero)) {
                        JOptionPane.showMessageDialog(null, "Error: El obrero ya se encuentra participando en la obra.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                        this.dispose();
                    }
                }
                ObrasObrero obreroObra = new ObrasObrero(obrero, obra);
                ObrasDAO.asingarObreroObra(ida, id);
            } else {
                JOptionPane.showMessageDialog(null, "Error: Seleccione un obrero válido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Seleccione una obra válida.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar la agregación de obrero? Los datos de agregación no se guardarán", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new PanelJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator Separador1;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox<String> cbxObra;
    private javax.swing.JComboBox<String> cbxObrero;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblElijaFactura;
    private javax.swing.JLabel lblElijaMetodo;
    private javax.swing.JLabel lblFactura;
    private javax.swing.JLabel lblMetodoPago;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
