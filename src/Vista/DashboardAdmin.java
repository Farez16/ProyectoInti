package Vista;

import Controlador.ControladorBotones;
import Modelo.TextoBotones;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class DashboardAdmin extends javax.swing.JPanel {

    private String correoUsuario;
    private ControladorBotones controladorBotones;

    public DashboardAdmin(String correoUsuario) {
        initComponents();
        iniciarEfectosBotones();
        this.correoUsuario = correoUsuario;
        btnMenu1.setOpaque(false);
        btnMenu1.setContentAreaFilled(false);
        btnMenu1.setBorderPainted(false);

        btnCuenta1.setOpaque(false);
        btnCuenta1.setContentAreaFilled(false);
        btnCuenta1.setBorderPainted(false);

        btnGraficos1.setOpaque(false);
        btnGraficos1.setContentAreaFilled(false);
        btnGraficos1.setBorderPainted(false);

        btnCrearAdmin.setOpaque(false);
        btnCrearAdmin.setContentAreaFilled(false);
        btnCrearAdmin.setBorderPainted(false);

        btnSalir1.setOpaque(false);
        btnSalir1.setContentAreaFilled(false);
        btnSalir1.setBorderPainted(false);
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public JLabel getLblLogo() {
        return LblLogo;
    }

    public JLabel getLblNombreProyecto() {
        return LblNombreProyecto;
    }

    public JLabel getLblPanelSuperior() {
        return LblPanelSuperior;
    }

    public JLabel getLblRol() {
        return LblRol;
    }

    public JLabel getLblimagenPrincipal() {
        return LblimagenPrincipal;
    }

    public JPanel getPanelVistas() {
        return PanelVistas;
    }

    public JButton getBtnCertificado1() {
        return btnGraficos1;
    }

    public JButton getBtnCuenta1() {
        return btnCuenta1;
    }

    public JButton getBtnDashboard1() {
        return btnMenu1;
    }

    public JButton getBtnJuegos1() {
        return btnCrearAdmin;
    }

    public JButton getBtnSalir1() {
        return btnSalir1;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void mostrarVista(JPanel nuevaVista) {
        PanelVistas.removeAll(); // Elimina lo que haya
        PanelVistas.add(nuevaVista, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, PanelVistas.getWidth(), PanelVistas.getHeight()));
        PanelVistas.revalidate(); // Refresca el layout
        PanelVistas.repaint();    // Vuelve a dibujar
    }

    public void setNombreUsuario(String nombre) {
        lblNombre.setText(nombre);
    }

    public void setRolUsuario(String rol) {
        LblRol.setText(rol);
    }

    public void actualizarSaludo(String saludo) {
        LblSaludo.setText(saludo);
    }

    public void setCorreoUsuario(String correo) {
        this.correoUsuario = correo;
    }

    public JLabel getLblSaludo() {
        return LblSaludo;
    }

    public JTable getTablaDatos() {
        return TablaDatos;
    }

    public JButton getBtnGraficos1() {
        return btnGraficos1;
    }

    public JButton getBtnMenu() {
        return btnMenu1;
    }

    public JButton getBtnCrearAdmin() {
        return btnCrearAdmin;
    }
    

    private void iniciarEfectosBotones() {
        // 1. Crear el mapa de botones
        Map<String, JButton> botonesMap = new HashMap<>();
        botonesMap.put("btnMenu1", btnMenu1);
        botonesMap.put("btnCuenta1", btnCuenta1);
        botonesMap.put("btnGraficos1", btnGraficos1);
        botonesMap.put("btnCrearAdmin", btnCrearAdmin);
        botonesMap.put("btnSalir1", btnSalir1);

        // 2. Pasar el mapa al controlador
        TextoBotones modeloTextos = new TextoBotones();
        System.out.println("ModeloTextos creado: " + modeloTextos); // Debug
        controladorBotones = new ControladorBotones(botonesMap, modeloTextos);
        controladorBotones.iniciar();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelVistas = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        LblSaludo = new javax.swing.JLabel();
        LblimagenPrincipal = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        LblRol = new javax.swing.JLabel();
        TxtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnMenu1 = new javax.swing.JButton();
        btnCuenta1 = new javax.swing.JButton();
        btnGraficos1 = new javax.swing.JButton();
        btnCrearAdmin = new javax.swing.JButton();
        LblNombreProyecto = new javax.swing.JLabel();
        LblLogo = new javax.swing.JLabel();
        btnSalir1 = new javax.swing.JButton();
        LblPanelSuperior = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelVistas.setBackground(new java.awt.Color(255, 255, 255));
        PanelVistas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TablaDatos);

        PanelVistas.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 800, 510));

        jPanel1.add(PanelVistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 1190, 660));

        LblSaludo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LblSaludo.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(LblSaludo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 24, 380, 60));

        LblimagenPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Users.png"))); // NOI18N
        jPanel1.add(LblimagenPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 90, 90));

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 20, 130, 40));

        LblRol.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LblRol.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(LblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 60, 130, 30));
        jPanel1.add(TxtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 50, 160, -1));

        btnBuscar.setText("Buscar");
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 50, 90, -1));

        btnMenu1.setBackground(new java.awt.Color(102, 102, 102));
        btnMenu1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnMenu1.setForeground(new java.awt.Color(255, 255, 255));
        btnMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/menu.png"))); // NOI18N
        btnMenu1.setText("  Inicio");
        btnMenu1.setBorder(null);
        btnMenu1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMenu1.setOpaque(true);
        btnMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenu1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 160, 42));

        btnCuenta1.setBackground(new java.awt.Color(102, 102, 102));
        btnCuenta1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCuenta1.setForeground(new java.awt.Color(255, 255, 255));
        btnCuenta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icono-usuario.png"))); // NOI18N
        btnCuenta1.setText("  Cuenta");
        btnCuenta1.setBorder(null);
        btnCuenta1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCuenta1.setOpaque(true);
        btnCuenta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuenta1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCuenta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 160, 42));

        btnGraficos1.setBackground(new java.awt.Color(102, 102, 102));
        btnGraficos1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnGraficos1.setForeground(new java.awt.Color(255, 255, 255));
        btnGraficos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/certificado.png"))); // NOI18N
        btnGraficos1.setText(" Graficos");
        btnGraficos1.setBorder(null);
        btnGraficos1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGraficos1.setOpaque(true);
        jPanel1.add(btnGraficos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 160, 42));

        btnCrearAdmin.setBackground(new java.awt.Color(102, 102, 102));
        btnCrearAdmin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCrearAdmin.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/play.png"))); // NOI18N
        btnCrearAdmin.setText("  Interactivo");
        btnCrearAdmin.setBorder(null);
        btnCrearAdmin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCrearAdmin.setOpaque(true);
        btnCrearAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearAdminActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrearAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 160, 42));

        LblNombreProyecto.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        LblNombreProyecto.setForeground(new java.awt.Color(255, 255, 255));
        LblNombreProyecto.setText("INTI");
        jPanel1.add(LblNombreProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, -1, -1));

        LblLogo.setForeground(new java.awt.Color(255, 255, 255));
        LblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo 90 x90.png"))); // NOI18N
        jPanel1.add(LblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        btnSalir1.setBackground(new java.awt.Color(102, 102, 102));
        btnSalir1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalir1.setForeground(new java.awt.Color(255, 255, 255));
        btnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cierre-de-sesión.png"))); // NOI18N
        btnSalir1.setText("  Salir");
        btnSalir1.setBorder(null);
        btnSalir1.setOpaque(true);
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, 168, 42));

        LblPanelSuperior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo dashboard.png"))); // NOI18N
        jPanel1.add(LblPanelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCrearAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearAdminActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCrearAdminActionPerformed

    private void btnCuenta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuenta1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCuenta1ActionPerformed

    private void btnMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMenu1ActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalir1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblLogo;
    public javax.swing.JLabel LblNombreProyecto;
    public javax.swing.JLabel LblPanelSuperior;
    public javax.swing.JLabel LblRol;
    public javax.swing.JLabel LblSaludo;
    public javax.swing.JLabel LblimagenPrincipal;
    public javax.swing.JPanel PanelVistas;
    public javax.swing.JTable TablaDatos;
    public javax.swing.JTextField TxtBuscar;
    public javax.swing.JButton btnBuscar;
    public javax.swing.JButton btnCrearAdmin;
    public javax.swing.JButton btnCuenta1;
    public javax.swing.JButton btnGraficos1;
    public javax.swing.JButton btnMenu1;
    public javax.swing.JButton btnSalir1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblNombre;
    // End of variables declaration//GEN-END:variables
}
