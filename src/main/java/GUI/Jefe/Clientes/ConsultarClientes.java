/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Jefe.Clientes;

import DAOs.ClientesDAO;
import DAOs.JefesDAO;
import Dominio.Clientes;
import Dominio.Jefes;
import GUI.Jefe.PanelJefe;
import Herramientas.Encriptador;
import Herramientas.Icono;
import Herramientas.Validadores;
import java.text.ParseException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 52644
 */
public class ConsultarClientes extends javax.swing.JFrame {

    // Atributos
    Jefes jefe;
    Encriptador crypt = new Encriptador();
    List<Clientes> listaClientes;
    JefesDAO JefesDAO = new JefesDAO();
    ClientesDAO ClientesDAO = new ClientesDAO();
    Validadores valido = new Validadores();
    
    /**
     * Creates new form ConsultarClientes
     */
    public ConsultarClientes(Jefes jefe) throws Exception {
        this.jefe = jefe;
        initComponents();
        new Icono().insertarIcono(this);
        cargarTablaClientes();
    }
    
    public void cargarTablaClientes() throws ParseException, Exception {
        listaClientes = ClientesDAO.consultarTodosClientes();
        DefaultTableModel modeloTablaDeudores = (DefaultTableModel) this.tblResultados.getModel();
        modeloTablaDeudores.setRowCount(0);
        for (Clientes cliente : listaClientes) {
            Object[] filaNueva = {cliente.getId(),
                cliente.getNombre() + " " + cliente.getApellidoPaterno() + " " + cliente.getApellidoMaterno(),
                crypt.decrypt(cliente.getTelefono()),
                "$ " + cliente.getDeudaTotal() + " MXN",
                "$ " + cliente.getInversionTotal() + " MXN"};
            modeloTablaDeudores.addRow(filaNueva);
        }
        valido.centrarTabla(tblResultados);
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRegresar = new javax.swing.JButton();
        Separador1 = new javax.swing.JSeparator();
        lblPagos = new javax.swing.JLabel();
        ScrollPanel = new javax.swing.JScrollPane();
        tblResultados = new javax.swing.JTable();
        UObraLogoPeque = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Consultar Clientes");

        btnRegresar.setText("Regresar");
        btnRegresar.setToolTipText("Regresar a Panel Cliente");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        lblPagos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPagos.setText("Clientes:");

        ScrollPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ScrollPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblResultados.setBackground(new java.awt.Color(255, 255, 255));
        tblResultados.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre completo", "Teléfono", "Deuda total", "Inversion total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblResultados.setRequestFocusEnabled(false);
        tblResultados.getTableHeader().setReorderingAllowed(false);
        ScrollPanel.setViewportView(tblResultados);

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Clientes");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPagos)
                            .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(15, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(UObraLogoPeque)
                        .addGap(29, 29, 29))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegresar)
                .addGap(383, 383, 383))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitulo)
                    .addComponent(UObraLogoPeque))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblPagos)
                .addGap(18, 18, 18)
                .addComponent(ScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegresar)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        new PanelJefe(JefesDAO.consultarJefe(this.jefe.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollPanel;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel lblPagos;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblResultados;
    // End of variables declaration//GEN-END:variables
}
