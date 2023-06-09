/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Obrero;

import DAOs.ObrerosDAO;
import Dominio.Obreros;
import GUI.Acceso.IniciarSesion;
import GUI.Obrero.ConsultarObrasObrero;
import GUI.Obrero.EditarContrasenaObrero;
import GUI.Obrero.EditarUsuarioObrero;
import GUI.Obrero.NominaObrero;
import GUI.Obrero.ConsultarPagosObrero;
import Herramientas.Icono;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author naely
 */
public class PanelObrero extends javax.swing.JFrame {

    // Atributo
    Obreros obrero;
    ObrerosDAO ObrerosDAO = new ObrerosDAO();
    
    /**
     * Crea un nuevo frame Obrero
     * 
     * @param obrero Obrero de cuenta iniciada
     */
    public PanelObrero(Obreros obrero) {
        this.obrero = obrero;
        initComponents();
        new Icono().insertarIcono(this);
        // Se ingresan los datos de obrero en el panel
        this.lblInsertarID.setText(this.obrero.id.toString());
        this.lblInsertarNombre.setText(this.obrero.nombre);
        this.lblInsertarApellidoPaterno.setText(this.obrero.apellidoPaterno);
        this.lblInsertarApellidoMaterno.setText(this.obrero.apellidoMaterno);
        this.lblInsertarSueldoDiario.setText(this.obrero.sueldoDiario.toString());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem3 = new javax.swing.JMenuItem();
        Separador1 = new javax.swing.JSeparator();
        btnCerrarSesion = new javax.swing.JButton();
        lblInsertarNombre = new javax.swing.JLabel();
        lblInsertarApellidoPaterno = new javax.swing.JLabel();
        lblInsertarApellidoMaterno = new javax.swing.JLabel();
        lblInsertarID = new javax.swing.JLabel();
        lblInsertarSueldoDiario = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblSueldoDiaro = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        lbl$ = new javax.swing.JLabel();
        lblMXN = new javax.swing.JLabel();
        UObraLogoPeque = new javax.swing.JLabel();
        Menu = new javax.swing.JMenuBar();
        MenuConsulta = new javax.swing.JMenu();
        mniConsultarPagos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniConsultarObras = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniConsultarNomina = new javax.swing.JMenuItem();
        MenuCuenta = new javax.swing.JMenu();
        mniCambiarContrasena = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mniCambiarUsuario = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Panel Obrero");
        setResizable(false);

        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setToolTipText("Cerrar Sesión");
        btnCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        lblInsertarNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarNombre.setText("nombre");

        lblInsertarApellidoPaterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarApellidoPaterno.setText("paterno");

        lblInsertarApellidoMaterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarApellidoMaterno.setText("materno");

        lblInsertarID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarID.setText("id");

        lblInsertarSueldoDiario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarSueldoDiario.setText("sueldo");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Panel Obrero");

        lblSueldoDiaro.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSueldoDiaro.setText("Sueldo diario: ");

        lblID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblID.setText("- ID:");

        lbl$.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbl$.setText("$");

        lblMXN.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblMXN.setText("MXN");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        MenuConsulta.setText("Consulta");
        MenuConsulta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        mniConsultarPagos.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuDinero.png")); // NOI18N
        mniConsultarPagos.setText("Consultar Pagos");
        mniConsultarPagos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mniConsultarPagos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarPagosActionPerformed(evt);
            }
        });
        MenuConsulta.add(mniConsultarPagos);
        MenuConsulta.add(jSeparator1);

        mniConsultarObras.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuCasco.png")); // NOI18N
        mniConsultarObras.setText("Consultar Obras");
        mniConsultarObras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mniConsultarObras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarObrasActionPerformed(evt);
            }
        });
        MenuConsulta.add(mniConsultarObras);
        MenuConsulta.add(jSeparator2);

        mniConsultarNomina.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuLupa.png")); // NOI18N
        mniConsultarNomina.setText("Consultar Nómina");
        mniConsultarNomina.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mniConsultarNomina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarNominaActionPerformed(evt);
            }
        });
        MenuConsulta.add(mniConsultarNomina);

        Menu.add(MenuConsulta);

        MenuCuenta.setText("Cuenta");
        MenuCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        mniCambiarContrasena.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuEngrane.png")); // NOI18N
        mniCambiarContrasena.setText("Cambiar contraseña");
        mniCambiarContrasena.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mniCambiarContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCambiarContrasenaActionPerformed(evt);
            }
        });
        MenuCuenta.add(mniCambiarContrasena);
        MenuCuenta.add(jSeparator3);

        mniCambiarUsuario.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuCuenta.png")); // NOI18N
        mniCambiarUsuario.setText("Cambiar usuario");
        mniCambiarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mniCambiarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCambiarUsuarioActionPerformed(evt);
            }
        });
        MenuCuenta.add(mniCambiarUsuario);

        Menu.add(MenuCuenta);

        setJMenuBar(Menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UObraLogoPeque)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblInsertarNombre)
                                .addGap(6, 6, 6)
                                .addComponent(lblInsertarApellidoPaterno)
                                .addGap(7, 7, 7)
                                .addComponent(lblInsertarApellidoMaterno))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblSueldoDiaro)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl$)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblInsertarSueldoDiario)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblMXN)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnCerrarSesion, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblInsertarID)))
                        .addGap(24, 24, 24))))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 366, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(UObraLogoPeque, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblInsertarNombre)
                    .addComponent(lblInsertarApellidoPaterno)
                    .addComponent(lblInsertarApellidoMaterno)
                    .addComponent(lblID)
                    .addComponent(lblInsertarID))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSueldoDiaro)
                    .addComponent(lblInsertarSueldoDiario)
                    .addComponent(lbl$)
                    .addComponent(lblMXN)
                    .addComponent(btnCerrarSesion))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que desea cerrar sesión?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new IniciarSesion().setVisible(true);
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void mniConsultarPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarPagosActionPerformed
        new ConsultarPagosObrero(ObrerosDAO.consultarObrero(this.obrero.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mniConsultarPagosActionPerformed

    private void mniConsultarObrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarObrasActionPerformed
        new ConsultarObrasObrero(ObrerosDAO.consultarObrero(this.obrero.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mniConsultarObrasActionPerformed

    private void mniCambiarContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCambiarContrasenaActionPerformed
        try {
            new EditarContrasenaObrero(ObrerosDAO.consultarObrero(this.obrero.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelObrero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniCambiarContrasenaActionPerformed

    private void mniCambiarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCambiarUsuarioActionPerformed
        try {
            new EditarUsuarioObrero(ObrerosDAO.consultarObrero(this.obrero.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelObrero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniCambiarUsuarioActionPerformed

    private void mniConsultarNominaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarNominaActionPerformed
        new NominaObrero(ObrerosDAO.consultarObrero(this.obrero.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mniConsultarNominaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar Menu;
    private javax.swing.JMenu MenuConsulta;
    private javax.swing.JMenu MenuCuenta;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel lbl$;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblInsertarApellidoMaterno;
    private javax.swing.JLabel lblInsertarApellidoPaterno;
    private javax.swing.JLabel lblInsertarID;
    private javax.swing.JLabel lblInsertarNombre;
    private javax.swing.JLabel lblInsertarSueldoDiario;
    private javax.swing.JLabel lblMXN;
    private javax.swing.JLabel lblSueldoDiaro;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenuItem mniCambiarContrasena;
    private javax.swing.JMenuItem mniCambiarUsuario;
    private javax.swing.JMenuItem mniConsultarNomina;
    private javax.swing.JMenuItem mniConsultarObras;
    private javax.swing.JMenuItem mniConsultarPagos;
    // End of variables declaration//GEN-END:variables
}
