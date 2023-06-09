/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe.Planos;

import DAOs.JefesDAO;
import DAOs.ObrasDAO;
import DAOs.PlanosDAO;
import Dominio.Jefes;
import Dominio.Obras;
import static Dominio.ObrasObrero_.obrero;
import Dominio.Planos;
import Enumeradores.EstadoObra;
import GUI.Jefe.PanelJefe;
import Herramientas.Encriptador;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author 52644
 */
public class AsignarPlanos extends javax.swing.JFrame {

    // Atributos
    Jefes jefe;
    ObrasDAO ObrasDAO = new ObrasDAO();
    JefesDAO JefesDAO = new JefesDAO();
    PlanosDAO PlanosDAO = new PlanosDAO();
    Encriptador crypt = new Encriptador();
    Validadores valido = new Validadores();
    
    /**
     * Creates new form AsignarPlanos
     */
    public AsignarPlanos(Jefes jefe) throws Exception {
        this.jefe = jefe;
        UIManager.put("OptionPane.yesButtonText", "Aceptar");
        UIManager.put("OptionPane.noButtonText", "Cancelar");
        initComponents();
        new Icono().insertarIcono(this);
        List<Obras> obras = ObrasDAO.consultarObrasConEstadoJefe(EstadoObra.DESARROLLO, this.jefe.getId());
        for (Obras obra : obras) {
            this.cbxObra.addItem(
                    "Nombre: " + obra.getNombre()
                    + " - ID Cliente: " + obra.getCliente().getId()
                    + " - ID: " + obra.getId());
        }
        List<Planos> obreros = PlanosDAO.consultarPlanosJefe(this.jefe.getId());
        for (Planos plano : obreros) {
            this.cbxPlano.addItem(
                    "Folio: " + crypt.decrypt(plano.getFolio())
                    + " - Tipo: " + plano.getTipo().toString()
                    + " - ID Jefe: " + plano.getJefe().getId()
                    + " - ID: " + plano.getId());
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

        cbxPlano = new javax.swing.JComboBox<>();
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
        setTitle("Asignar Plano");
        setResizable(false);

        cbxPlano.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elija uno..." }));
        cbxPlano.setToolTipText("Elija un tipo de permiso");
        cbxPlano.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnAgregar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.setToolTipText("Agregar Obrero");
        btnAgregar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        lblElijaMetodo.setText("Elija un plano...");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblMetodoPago.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblMetodoPago.setText("Plano:");

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
        lblTitulo.setText("Asingar Plano");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(164, 164, 164)
                        .addComponent(UObraLogoPeque))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(lblBusqueda))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(lblElijaFactura))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(lblElijaMetodo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(btnAgregar)
                        .addGap(78, 78, 78)
                        .addComponent(btnCancelar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblMetodoPago)
                                .addGap(12, 12, 12)
                                .addComponent(cbxPlano, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblFactura)
                                .addGap(12, 12, 12)
                                .addComponent(cbxObra, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblTitulo))
                    .addComponent(UObraLogoPeque))
                .addGap(6, 6, 6)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblBusqueda)
                .addGap(6, 6, 6)
                .addComponent(lblElijaFactura)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblFactura))
                    .addComponent(cbxObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lblElijaMetodo)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblMetodoPago))
                    .addComponent(cbxPlano, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAgregar)
                    .addComponent(btnCancelar))
                .addGap(23, 23, 23))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        if (this.cbxObra.getSelectedItem() != "Elija una...") {
            if (this.cbxPlano.getSelectedItem() != "Elija uno...") {
                // Plano
                String PlanoElegido = this.cbxPlano.getSelectedItem().toString();
                String idElegido = PlanoElegido.substring(PlanoElegido.length() - 3, PlanoElegido.length());
                idElegido = valido.obtenerNumeros(idElegido);
                Long id = Long.valueOf(idElegido);
                Planos plano = PlanosDAO.consultarPlano(id);
                // Obra
                String ObraElegida = this.cbxObra.getSelectedItem().toString();
                String idElegida = ObraElegida.substring(ObraElegida.length() - 3, ObraElegida.length());
                idElegida = valido.obtenerNumeros(idElegida);
                Long ida = Long.valueOf(idElegida);
                Obras obra = ObrasDAO.consultarObra(ida);
                List<Planos> planosO = obra.getPlanos();
                for (Planos planos : planosO) {
                    if (planos.getId().equals(plano.getId())) {
                        try {
                            JOptionPane.showMessageDialog(null, "Error: El plano ya se encuentra en uso en la presente obra.", "¡Error!", JOptionPane.ERROR_MESSAGE);
                            this.dispose();
                            new AsignarPlanos(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
                        } catch (Exception ex) {
                            Logger.getLogger(AsignarPlanos.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                ObrasDAO.agregarPlanoObra(ida, id);
                int i = 0;
                try {
                    i = JOptionPane.showConfirmDialog(null,
                            "Se realizó exitosamente la asignación del plano a la obra..."
                            + "\n Folio plano: " + crypt.decrypt(plano.getFolio())
                            + "\n Tipo: " + plano.getTipo().toString()
                            + "\n Escala: " + plano.getEscala().toString()
                            + "\n\n Nombre obra: " + obra.getNombre()
                            + "\n Costo total: $ " + obra.getCostoTotal() + " MXN"
                            + "\n - ID Jefe: " + this.jefe.getId()
                            + "\n - ID Cliente: " + obra.getCliente().getId()
                            + "\n - ID Obra: " + obra.getId()
                            + ". ☺\n"
                            + "\n ¿Desea asignar otro plano?", "Asignación de plano exitosa", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new Icono().obtenerIcono());
                } catch (Exception ex) {
                    Logger.getLogger(AsignarPlanos.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (i == JOptionPane.YES_OPTION) {
                    try {
                        this.dispose();
                        new AsignarPlanos(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
                    } catch (Exception ex) {
                        Logger.getLogger(AsignarPlanos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    this.dispose();
                    new PanelJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error: Seleccione un plano válido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Error: Seleccione una plano válido.", "¡Error!", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar la asignación de plano? Los datos de asignación no se guardarán", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
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
    private javax.swing.JComboBox<String> cbxPlano;
    private javax.swing.JLabel lblBusqueda;
    private javax.swing.JLabel lblElijaFactura;
    private javax.swing.JLabel lblElijaMetodo;
    private javax.swing.JLabel lblFactura;
    private javax.swing.JLabel lblMetodoPago;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
