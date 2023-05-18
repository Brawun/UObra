/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe;

import DAOs.JefesDAO;
import Dominio.Jefes;
import GUI.Acceso.IniciarSesion;
import GUI.Cliente.ConsultarObras;
import GUI.Jefe.Clientes.ConsultarClientes;
import GUI.Jefe.Facturas.ConsultarFacturas;
import GUI.Jefe.Facturas.PagarFactura;
import GUI.Jefe.Facturas.RegistrarFactura;
import GUI.Jefe.Jefes.ConsultarDeudores;
import GUI.Jefe.Obras.IniciarObra;
import GUI.Jefe.Obras.TerminarObra;
import GUI.Jefe.Obreros.AgregarObrero;
import GUI.Jefe.Obreros.ConsultarObreros;
import GUI.Jefe.Obreros.PagarObreros;
import GUI.Jefe.Permisos.ConsultarPermisos;
import GUI.Jefe.Permisos.RegistrarPermiso;
import GUI.Jefe.Planos.AsignarPlanos;
import GUI.Jefe.Planos.ConsultarPlanos;
import GUI.Jefe.Planos.RegistrarPlano;
import Herramientas.Icono;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author naely
 */
public class PanelJefe extends javax.swing.JFrame {

    // Atributos
    Jefes jefe;
    JefesDAO JefesDAO = new JefesDAO();
    
    /**
     * Creates new form JefeObra
     * @param jefe
     */
    public PanelJefe(Jefes jefe) {
        this.jefe = jefe;
        initComponents();
        this.mniPagarObrero.setVisible(false);
        new Icono().insertarIcono(this);
        this.lblInsertarID.setText(this.jefe.getId().toString());
        this.lblInsertarNombre.setText(this.jefe.getNombre());
        this.lblInsertarApellidoPaterno.setText(this.jefe.getApellidoPaterno());
        this.lblInsertarApellidoMaterno.setText(this.jefe.getApellidoMaterno());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator3 = new javax.swing.JSeparator();
        lblInsertarNombre = new javax.swing.JLabel();
        Separador1 = new javax.swing.JSeparator();
        lblInsertarApellidoPaterno = new javax.swing.JLabel();
        lblInsertarApellidoMaterno = new javax.swing.JLabel();
        lblInsertarID = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        UObraLogoPeque = new javax.swing.JLabel();
        lblID = new javax.swing.JLabel();
        btnCerrarSesion1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mniConsultarClientes = new javax.swing.JMenuItem();
        jSeparator11 = new javax.swing.JPopupMenu.Separator();
        mniConsultarDeudores = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        mniConsultarObreros = new javax.swing.JMenuItem();
        jSeparator12 = new javax.swing.JPopupMenu.Separator();
        mniAgregarObreroObra = new javax.swing.JMenuItem();
        jSeparator8 = new javax.swing.JPopupMenu.Separator();
        mniPagarObrero = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        mniIniciarObra = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        mniTerminarObra = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        mniConsultarPermisos = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniRegistrarPermiso = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        mniConsultarPlanos = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniRegistrarPlano = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JPopupMenu.Separator();
        mniAsignarPlanos = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mniConsultarFacturas = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JPopupMenu.Separator();
        mniRegistrarFactura = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JPopupMenu.Separator();
        mniPagarFactura = new javax.swing.JMenuItem();
        MenuCuenta = new javax.swing.JMenu();
        mniCambiarContrasena = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        mniCambiarUsuario = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Panel Jefe");
        setResizable(false);

        lblInsertarNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarNombre.setText("nombre");

        lblInsertarApellidoPaterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarApellidoPaterno.setText("paterno");

        lblInsertarApellidoMaterno.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarApellidoMaterno.setText("materno");

        lblInsertarID.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblInsertarID.setText("id");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Panel Jefe");

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblID.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblID.setText("- ID:");

        btnCerrarSesion1.setText("Cerrar Sesión");
        btnCerrarSesion1.setToolTipText("Cerrar Sesión");
        btnCerrarSesion1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrarSesion1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesion1ActionPerformed(evt);
            }
        });

        jMenu1.setText("Clientes");

        mniConsultarClientes.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuLupa.png")); // NOI18N
        mniConsultarClientes.setText("Consultar Clientes");
        mniConsultarClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarClientesActionPerformed(evt);
            }
        });
        jMenu1.add(mniConsultarClientes);
        jMenu1.add(jSeparator11);

        mniConsultarDeudores.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuLupa.png")); // NOI18N
        mniConsultarDeudores.setText("Consultar Deudores");
        mniConsultarDeudores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarDeudoresActionPerformed(evt);
            }
        });
        jMenu1.add(mniConsultarDeudores);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Obreros");

        mniConsultarObreros.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuLupa.png")); // NOI18N
        mniConsultarObreros.setText("Consultar Obreros");
        mniConsultarObreros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarObrerosActionPerformed(evt);
            }
        });
        jMenu3.add(mniConsultarObreros);
        jMenu3.add(jSeparator12);

        mniAgregarObreroObra.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuCasco.png")); // NOI18N
        mniAgregarObreroObra.setText("Agregar Obrero Obra");
        mniAgregarObreroObra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAgregarObreroObraActionPerformed(evt);
            }
        });
        jMenu3.add(mniAgregarObreroObra);
        jMenu3.add(jSeparator8);

        mniPagarObrero.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuDinero.png")); // NOI18N
        mniPagarObrero.setText("Pagar Obrero Obra");
        mniPagarObrero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniPagarObreroActionPerformed(evt);
            }
        });
        jMenu3.add(mniPagarObrero);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Obras");

        mniIniciarObra.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuInsercionRapida.png")); // NOI18N
        mniIniciarObra.setText("Iniciar Obra");
        mniIniciarObra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniIniciarObraActionPerformed(evt);
            }
        });
        jMenu4.add(mniIniciarObra);
        jMenu4.add(jSeparator5);

        mniTerminarObra.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuPregunta.png")); // NOI18N
        mniTerminarObra.setText("Terminar Obra");
        mniTerminarObra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniTerminarObraActionPerformed(evt);
            }
        });
        jMenu4.add(mniTerminarObra);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Permisos");

        mniConsultarPermisos.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuLupa.png")); // NOI18N
        mniConsultarPermisos.setText("Consultar Permisos");
        mniConsultarPermisos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarPermisosActionPerformed(evt);
            }
        });
        jMenu5.add(mniConsultarPermisos);
        jMenu5.add(jSeparator1);

        mniRegistrarPermiso.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuRegistro.png")); // NOI18N
        mniRegistrarPermiso.setText("Registrar Permiso");
        mniRegistrarPermiso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniRegistrarPermisoActionPerformed(evt);
            }
        });
        jMenu5.add(mniRegistrarPermiso);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Planos");

        mniConsultarPlanos.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuLupa.png")); // NOI18N
        mniConsultarPlanos.setText("Consultar Planos");
        mniConsultarPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarPlanosActionPerformed(evt);
            }
        });
        jMenu6.add(mniConsultarPlanos);
        jMenu6.add(jSeparator2);

        mniRegistrarPlano.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuRegistro.png")); // NOI18N
        mniRegistrarPlano.setText("Registrar Plano");
        mniRegistrarPlano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniRegistrarPlanoActionPerformed(evt);
            }
        });
        jMenu6.add(mniRegistrarPlano);
        jMenu6.add(jSeparator7);

        mniAsignarPlanos.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuInsercion.png")); // NOI18N
        mniAsignarPlanos.setText("Asignar Planos");
        mniAsignarPlanos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniAsignarPlanosActionPerformed(evt);
            }
        });
        jMenu6.add(mniAsignarPlanos);

        jMenuBar1.add(jMenu6);

        jMenu2.setText("Facturas");

        mniConsultarFacturas.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuLupa.png")); // NOI18N
        mniConsultarFacturas.setText("Consultar Facturas");
        mniConsultarFacturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniConsultarFacturasActionPerformed(evt);
            }
        });
        jMenu2.add(mniConsultarFacturas);
        jMenu2.add(jSeparator9);

        mniRegistrarFactura.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuRegistro.png")); // NOI18N
        mniRegistrarFactura.setText("Registrar Factura");
        mniRegistrarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniRegistrarFacturaActionPerformed(evt);
            }
        });
        jMenu2.add(mniRegistrarFactura);
        jMenu2.add(jSeparator6);

        mniPagarFactura.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuDinero.png")); // NOI18N
        mniPagarFactura.setText("Pagar Factura");
        mniPagarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniPagarFacturaActionPerformed(evt);
            }
        });
        jMenu2.add(mniPagarFactura);

        jMenuBar1.add(jMenu2);

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
        MenuCuenta.add(jSeparator4);

        mniCambiarUsuario.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\MenuCuenta.png")); // NOI18N
        mniCambiarUsuario.setText("Cambiar usuario");
        mniCambiarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        mniCambiarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mniCambiarUsuarioActionPerformed(evt);
            }
        });
        MenuCuenta.add(mniCambiarUsuario);

        jMenuBar1.add(MenuCuenta);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(UObraLogoPeque)
                                .addGap(11, 11, 11))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblInsertarNombre)
                                .addGap(6, 6, 6)
                                .addComponent(lblInsertarApellidoPaterno)
                                .addGap(7, 7, 7)
                                .addComponent(lblInsertarApellidoMaterno)
                                .addGap(56, 56, 56)
                                .addComponent(lblID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblInsertarID)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
                                .addComponent(btnCerrarSesion1)
                                .addGap(14, 14, 14))))
                    .addComponent(Separador1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitulo)
                    .addComponent(UObraLogoPeque, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCerrarSesion1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblID)
                        .addComponent(lblInsertarID))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblInsertarNombre)
                        .addComponent(lblInsertarApellidoPaterno)
                        .addComponent(lblInsertarApellidoMaterno)))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarSesion1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesion1ActionPerformed
        this.setVisible(false);
        int i = JOptionPane.showConfirmDialog(this, "¿Seguro que desea cerrar sesión?", "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (i == JOptionPane.YES_OPTION) {
            this.dispose();
            new IniciarSesion().setVisible(true);
        } else {
            this.setVisible(true);
        }
    }//GEN-LAST:event_btnCerrarSesion1ActionPerformed

    private void mniCambiarContrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCambiarContrasenaActionPerformed
        try {
            new EditarContrasenaJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniCambiarContrasenaActionPerformed

    private void mniCambiarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniCambiarUsuarioActionPerformed
        try {
            new EditarUsuarioJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniCambiarUsuarioActionPerformed

    private void mniConsultarDeudoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarDeudoresActionPerformed
        try {
            new ConsultarDeudores(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniConsultarDeudoresActionPerformed

    private void mniAgregarObreroObraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAgregarObreroObraActionPerformed
        try {
            new AgregarObrero(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniAgregarObreroObraActionPerformed

    private void mniIniciarObraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniIniciarObraActionPerformed
        try {
            new IniciarObra(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniIniciarObraActionPerformed

    private void mniTerminarObraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniTerminarObraActionPerformed
        try {
            new TerminarObra(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniTerminarObraActionPerformed

    private void mniRegistrarPermisoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniRegistrarPermisoActionPerformed
        new RegistrarPermiso(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mniRegistrarPermisoActionPerformed

    private void mniRegistrarPlanoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniRegistrarPlanoActionPerformed
        new RegistrarPlano(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mniRegistrarPlanoActionPerformed

    private void mniRegistrarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniRegistrarFacturaActionPerformed
        new RegistrarFactura(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_mniRegistrarFacturaActionPerformed

    private void mniPagarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniPagarFacturaActionPerformed
        try {
            new PagarFactura(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniPagarFacturaActionPerformed

    private void mniConsultarPermisosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarPermisosActionPerformed
        try {
            new ConsultarPermisos(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniConsultarPermisosActionPerformed

    private void mniConsultarPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarPlanosActionPerformed
        try {
            new ConsultarPlanos(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniConsultarPlanosActionPerformed

    private void mniAsignarPlanosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniAsignarPlanosActionPerformed
        try {
            new AsignarPlanos(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniAsignarPlanosActionPerformed

    private void mniPagarObreroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniPagarObreroActionPerformed
        try {
            new PagarObreros(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniPagarObreroActionPerformed

    private void mniConsultarFacturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarFacturasActionPerformed
        try {
            new ConsultarFacturas(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniConsultarFacturasActionPerformed

    private void mniConsultarObrerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarObrerosActionPerformed
        try {
            new ConsultarObreros(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniConsultarObrerosActionPerformed

    private void mniConsultarClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mniConsultarClientesActionPerformed
        try {
            new ConsultarClientes(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
            this.dispose();
        } catch (Exception ex) {
            Logger.getLogger(PanelJefe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_mniConsultarClientesActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu MenuCuenta;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnCerrarSesion1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator11;
    private javax.swing.JPopupMenu.Separator jSeparator12;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JPopupMenu.Separator jSeparator6;
    private javax.swing.JPopupMenu.Separator jSeparator7;
    private javax.swing.JPopupMenu.Separator jSeparator8;
    private javax.swing.JPopupMenu.Separator jSeparator9;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblInsertarApellidoMaterno;
    private javax.swing.JLabel lblInsertarApellidoPaterno;
    private javax.swing.JLabel lblInsertarID;
    private javax.swing.JLabel lblInsertarNombre;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JMenuItem mniAgregarObreroObra;
    private javax.swing.JMenuItem mniAsignarPlanos;
    private javax.swing.JMenuItem mniCambiarContrasena;
    private javax.swing.JMenuItem mniCambiarUsuario;
    private javax.swing.JMenuItem mniConsultarClientes;
    private javax.swing.JMenuItem mniConsultarDeudores;
    private javax.swing.JMenuItem mniConsultarFacturas;
    private javax.swing.JMenuItem mniConsultarObreros;
    private javax.swing.JMenuItem mniConsultarPermisos;
    private javax.swing.JMenuItem mniConsultarPlanos;
    private javax.swing.JMenuItem mniIniciarObra;
    private javax.swing.JMenuItem mniPagarFactura;
    private javax.swing.JMenuItem mniPagarObrero;
    private javax.swing.JMenuItem mniRegistrarFactura;
    private javax.swing.JMenuItem mniRegistrarPermiso;
    private javax.swing.JMenuItem mniRegistrarPlano;
    private javax.swing.JMenuItem mniTerminarObra;
    // End of variables declaration//GEN-END:variables
}
