/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI.Cliente;

import DAOs.ClientesDAO;
import DAOs.ObrasDAO;
import DAOs.UbicacionesDAO;
import Dominio.Clientes;
import Dominio.Obras;
import Dominio.Ubicaciones;
import Herramientas.Fecha;
import Herramientas.Icono;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 52644
 */
public class ConsultarUbicaciones extends javax.swing.JFrame {

    // Atributos
    Clientes cliente = new Clientes();
    ClientesDAO ClientesDAO = new ClientesDAO();
    UbicacionesDAO UbicacionesDAO = new UbicacionesDAO();
    Fecha fecha = new Fecha();
    List<Ubicaciones> listaUbicaciones;

    /**
     * Creates new form ConsultarUbicaciones
     */
    public ConsultarUbicaciones(Clientes cliente) throws Exception {
        this.cliente = cliente;
        initComponents();
        new Icono().insertarIcono(this);
        cargarTablaUbicaciones();
        this.lblInsertarArea.setText("");
        this.lblInsertarDireccion.setText("");
        this.lblInsertarFecha.setText("");
        this.lblInsertarID.setText("");
    }

    public void cargarTablaUbicaciones() throws Exception {
        listaUbicaciones = UbicacionesDAO.consultarUbicacionesCliente(this.cliente.getId());
        DefaultTableModel modeloTablaPersonas = (DefaultTableModel) this.tblUbicaciones.getModel();
        modeloTablaPersonas.setRowCount(0);
        for (Ubicaciones ubicaciones : listaUbicaciones) {
            Object[] filaNueva = {ubicaciones.getId(),
                ubicaciones.getDisponible() == true ? "Disponible" : "No disponible",
                ubicaciones.getDireccion(),
                ubicaciones.getTipo().toString(),
                ubicaciones.getArea().toString() + " m²",
                fecha.formatoFecha(ubicaciones.getFechaRegistro()),
                ubicaciones.getFechaOcupacion() != null ? fecha.formatoFecha(ubicaciones.getFechaOcupacion()) : "No aplica",
                "| Seleccionar |"};
            modeloTablaPersonas.addRow(filaNueva);
        }
    }

    public void cargarTablaObrasUbicacion(List<Obras> listaObras) throws ParseException {
        DefaultTableModel modeloTablaPersonas = (DefaultTableModel) this.tblObras.getModel();
        modeloTablaPersonas.setRowCount(0);
        for (Obras obras : listaObras) {
            Object[] filaNueva = {obras.getId(),
                obras.getNombre(),
                obras.getEstado().toString(),
                "$ " + obras.getCostoTotal() + " MXN",
                "$ " + obras.getDeuda() + " MXN",
                obras.getEstaPagada() == true ? "Pagada" : "No pagada",
                fecha.formatoFecha(obras.getFechaSolicitada()),
                obras.getFechaInicio() != null ? fecha.formatoFecha(obras.getFechaInicio()) : "No aplica",
                obras.getFechaFin() != null ? fecha.formatoFecha(obras.getFechaFin()) : "No aplica"};
            modeloTablaPersonas.addRow(filaNueva);
        }
    }

    public int obtenerFila() {
        try {
            int fila = tblUbicaciones.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Error: Seleccione una ubicación. (De la tabla 'Ubicaciones registradas').", "¡Error!", JOptionPane.ERROR_MESSAGE);
            }
            return fila;
        } catch (Exception e) {
            Logger.getLogger(ConsultarUbicaciones.class.getName()).log(Level.SEVERE, null, e);
            return -1;
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

        ScrollPanel1 = new javax.swing.JScrollPane();
        tblUbicaciones = new javax.swing.JTable();
        ScrollPanel2 = new javax.swing.JScrollPane();
        tblObras = new javax.swing.JTable();
        Separador1 = new javax.swing.JSeparator();
        btnRegresar = new javax.swing.JButton();
        btnAceptarSeleccion = new javax.swing.JButton();
        UObraLogoPeque = new javax.swing.JLabel();
        lblUbicaciones = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblUtilizadasObras = new javax.swing.JLabel();
        lblInstrucción = new javax.swing.JLabel();
        lblUbicacionSeleccionada = new javax.swing.JLabel();
        lblInsertarDireccion = new javax.swing.JLabel();
        lblInsertarArea = new javax.swing.JLabel();
        lblInsertarID = new javax.swing.JLabel();
        lblInsertarFecha = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        lblArea = new javax.swing.JLabel();
        lblFechaRegistrada = new javax.swing.JLabel();
        lblIDObra = new javax.swing.JLabel();
        ImagenEdificio = new javax.swing.JLabel();
        ImagenCasa = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Consultar Ubicaciones");
        setResizable(false);

        ScrollPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ScrollPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblUbicaciones.setBackground(new java.awt.Color(255, 255, 255));
        tblUbicaciones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblUbicaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Disponible", "Dirección", "Tipo", "Área", "Fecha registro", "Fecha ocupación", "Opciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblUbicaciones.setRequestFocusEnabled(false);
        ScrollPanel1.setViewportView(tblUbicaciones);

        ScrollPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        ScrollPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tblObras.setBackground(new java.awt.Color(255, 255, 255));
        tblObras.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblObras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nombre", "Estado", "Costo Total", "Deuda", "Pagada", "Fecha Solicitada", "Fecha Inicio", "Fecha Fin"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Float.class, java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblObras.setRequestFocusEnabled(false);
        ScrollPanel2.setViewportView(tblObras);

        btnRegresar.setText("Regresar");
        btnRegresar.setToolTipText("Regresar a Panel Cliente");
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        btnAceptarSeleccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnAceptarSeleccion.setText("Aceptar selección");
        btnAceptarSeleccion.setToolTipText("Acepta la selección del primer elemento elegido");
        btnAceptarSeleccion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAceptarSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarSeleccionActionPerformed(evt);
            }
        });

        UObraLogoPeque.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\UObraPeque.png")); // NOI18N

        lblUbicaciones.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUbicaciones.setText("Ubicaciones registradas:");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTitulo.setText("Ubicaciones");

        lblUtilizadasObras.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUtilizadasObras.setText("Ubicación utilizada en siguientes obras:");

        lblInstrucción.setText("Seleccione una obra para poder saber en qué ubicaciones ha sido o está siendo utilizada");

        lblUbicacionSeleccionada.setText("Ubicación seleccionada:");

        lblInsertarDireccion.setText("dirección");

        lblInsertarArea.setText("area");

        lblInsertarID.setText("id");

        lblInsertarFecha.setText("fecha");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDireccion.setText("Dirección:");

        lblArea.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblArea.setText("Área:");

        lblFechaRegistrada.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFechaRegistrada.setText("Fecha registrada:");

        lblIDObra.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIDObra.setText("ID:");

        ImagenEdificio.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenEdificios.png")); // NOI18N

        ImagenCasa.setIcon(new javax.swing.ImageIcon("D:\\Documentos\\Word\\ITSON\\3er-4to Semestre\\4°\\Pruebas de Software\\UObra\\src\\main\\java\\Multimedia\\ImagenCasa.png")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblUbicaciones)
                            .addComponent(ScrollPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblTitulo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(UObraLogoPeque))
                            .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ScrollPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblUtilizadasObras)
                                    .addComponent(lblInstrucción)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(lblUbicacionSeleccionada)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(65, 65, 65)
                                                .addComponent(lblArea))
                                            .addComponent(lblIDObra, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblFechaRegistrada, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addComponent(lblDireccion)))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblInsertarFecha)
                                            .addComponent(lblInsertarID)
                                            .addComponent(lblInsertarDireccion)
                                            .addComponent(lblInsertarArea))))
                                .addGap(37, 37, 37)
                                .addComponent(ImagenEdificio)
                                .addGap(18, 18, 18)
                                .addComponent(ImagenCasa))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(btnRegresar)))
                .addGap(21, 21, 21))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(btnAceptarSeleccion)
                .addGap(374, 374, 374))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lblTitulo))
                    .addComponent(UObraLogoPeque, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(6, 6, 6)
                .addComponent(Separador1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblUbicaciones)
                .addGap(18, 18, 18)
                .addComponent(ScrollPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAceptarSeleccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblInstrucción, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDireccion)
                            .addComponent(lblInsertarDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblUbicacionSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblInsertarArea, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblArea))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFechaRegistrada)
                            .addComponent(lblInsertarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIDObra)
                            .addComponent(lblInsertarID, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblUtilizadasObras)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ImagenEdificio)
                            .addComponent(ImagenCasa, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(17, 17, 17)))
                .addComponent(ScrollPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegresar)
                .addGap(19, 19, 19))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        new PanelCliente(ClientesDAO.consultarCliente(this.cliente.getId())).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnAceptarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarSeleccionActionPerformed
        try {
            int fila = obtenerFila();
            if (fila != -1) {
                Ubicaciones ubicacion = UbicacionesDAO.consultarUbicacion((Long) tblUbicaciones.getValueAt(fila, 0));
                this.lblInsertarArea.setText(ubicacion.getArea().toString() + " m²");
                this.lblInsertarDireccion.setText(ubicacion.getDireccion());
                this.lblInsertarFecha.setText(fecha.formatoFecha(ubicacion.getFechaRegistro()));
                this.lblInsertarID.setText(ubicacion.getId().toString());
                if (!ubicacion.getObras().isEmpty()) {
                    this.cargarTablaObrasUbicacion(ubicacion.getObras());
                } else {
                    JOptionPane.showMessageDialog(null, "Error, seleccione a una ubicación válida. (La ubicación no tiene obras asignadas)");
                    this.lblInsertarArea.setText("");
                    this.lblInsertarDireccion.setText("");
                    this.lblInsertarFecha.setText("");
                    this.lblInsertarID.setText("");
                }
            }
        } catch (Exception e) {
            Logger.getLogger(ConsultarUbicaciones.class.getName()).log(Level.SEVERE, null, e);
        }
    }//GEN-LAST:event_btnAceptarSeleccionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ImagenCasa;
    private javax.swing.JLabel ImagenEdificio;
    private javax.swing.JScrollPane ScrollPanel1;
    private javax.swing.JScrollPane ScrollPanel2;
    private javax.swing.JSeparator Separador1;
    private javax.swing.JLabel UObraLogoPeque;
    private javax.swing.JButton btnAceptarSeleccion;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel lblArea;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblFechaRegistrada;
    private javax.swing.JLabel lblIDObra;
    private javax.swing.JLabel lblInsertarArea;
    private javax.swing.JLabel lblInsertarDireccion;
    private javax.swing.JLabel lblInsertarFecha;
    private javax.swing.JLabel lblInsertarID;
    private javax.swing.JLabel lblInstrucción;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUbicacionSeleccionada;
    private javax.swing.JLabel lblUbicaciones;
    private javax.swing.JLabel lblUtilizadasObras;
    private javax.swing.JTable tblObras;
    private javax.swing.JTable tblUbicaciones;
    // End of variables declaration//GEN-END:variables
}