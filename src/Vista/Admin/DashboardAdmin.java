package Vista.Admin;

import Controlador.ControladorBotones;
import Modelo.TextoBotones;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

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
        
        btnReportes.setOpaque(false);
        btnReportes.setContentAreaFilled(false);
        btnReportes.setBorderPainted(false);
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public JLabel getLblLogo() {
        return LblLogo;
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
        System.out.println("[DEBUG] Mostrando vista. Tamaño PanelVistas: " + PanelVistas.getSize());
        System.out.println("[DEBUG] Nueva vista: " + nuevaVista.getClass().getSimpleName());
        PanelVistas.removeAll(); // Elimina lo que haya
        PanelVistas.add(nuevaVista, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, PanelVistas.getWidth(), PanelVistas.getHeight()));
        PanelVistas.revalidate(); // Refresca el layout
        PanelVistas.repaint();    // Vuelve a dibujar
        System.out.println("[DEBUG] Componentes en PanelVistas: " + PanelVistas.getComponentCount());
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

    public JButton getBtnGraficos1() {
        return btnGraficos1;
    }

    public JButton getBtnMenu() {
        return btnMenu1;
    }

    public JButton getBtnCrearAdmin() {
        return btnCrearAdmin;
    }

    public JButton getBtnMenu1() {
        return btnMenu1;
    }

    public JButton getBtnReportes() {
        return btnReportes;
    }
    public String getNombreUsuario() {
        return lblNombre.getText(); // Directamente del JLabel que ya tiene el nombre
    }

    private void iniciarEfectosBotones() {
        // 1. Crear el mapa de botones
        Map<String, JButton> botonesMap = new HashMap<>();
        botonesMap.put("btnMenu1", btnMenu1);
        botonesMap.put("btnCuenta1", btnCuenta1);
        botonesMap.put("btnGraficos1", btnGraficos1);
        botonesMap.put("btnCrearAdmin", btnCrearAdmin);
        botonesMap.put("btnSalir1", btnSalir1);
        botonesMap.put("btnReportes", btnReportes);

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
        LblSaludo = new javax.swing.JLabel();
        LblimagenPrincipal = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        LblRol = new javax.swing.JLabel();
        btnMenu1 = new javax.swing.JButton();
        btnCuenta1 = new javax.swing.JButton();
        btnGraficos1 = new javax.swing.JButton();
        btnCrearAdmin = new javax.swing.JButton();
        LblLogo = new javax.swing.JLabel();
        btnSalir1 = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelVistas.setBackground(new java.awt.Color(200, 225, 255));
        PanelVistas.setPreferredSize(new java.awt.Dimension(1190, 660));
        PanelVistas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(PanelVistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 1190, 660));

        LblSaludo.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        LblSaludo.setForeground(new java.awt.Color(55, 65, 81));
        jPanel1.add(LblSaludo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 24, 380, 60));

        LblimagenPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Users.png"))); // NOI18N
        jPanel1.add(LblimagenPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 10, 90, 90));

        lblNombre.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(55, 65, 81));
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 130, 30));

        LblRol.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblRol.setForeground(new java.awt.Color(55, 65, 81));
        jPanel1.add(LblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 60, 130, 30));

        btnMenu1.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnMenu1.setForeground(new java.awt.Color(55, 65, 81));
        btnMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconAdmin/icons8-menú-principal.png"))); // NOI18N
        btnMenu1.setBorder(null);
        btnMenu1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnMenu1.setOpaque(true);
        btnMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenu1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnMenu1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 160, 42));

        btnCuenta1.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnCuenta1.setForeground(new java.awt.Color(55, 65, 81));
        btnCuenta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconAdmin/icons8-profileN.png"))); // NOI18N
        btnCuenta1.setBorder(null);
        btnCuenta1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCuenta1.setOpaque(true);
        btnCuenta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuenta1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCuenta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 160, 42));

        btnGraficos1.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnGraficos1.setForeground(new java.awt.Color(55, 65, 81));
        btnGraficos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconAdmin/icons8-estadísticasN.png"))); // NOI18N
        btnGraficos1.setBorder(null);
        btnGraficos1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGraficos1.setOpaque(true);
        jPanel1.add(btnGraficos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 160, 42));

        btnCrearAdmin.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnCrearAdmin.setForeground(new java.awt.Color(55, 65, 81));
        btnCrearAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconAdmin/icons8-agregar-administradorN.png"))); // NOI18N
        btnCrearAdmin.setBorder(null);
        btnCrearAdmin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCrearAdmin.setOpaque(true);
        btnCrearAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearAdminActionPerformed(evt);
            }
        });
        jPanel1.add(btnCrearAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 160, 42));

        LblLogo.setForeground(new java.awt.Color(255, 255, 255));
        LblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo135x132.png"))); // NOI18N
        jPanel1.add(LblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 23, -1, -1));

        btnSalir1.setBackground(new java.awt.Color(153, 204, 255));
        btnSalir1.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnSalir1.setForeground(new java.awt.Color(55, 65, 81));
        btnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconAdmin/icons8-cierre-de-sesión-redondeado-hacia-la-izquierdaN.png"))); // NOI18N
        btnSalir1.setToolTipText("");
        btnSalir1.setBorder(null);
        btnSalir1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir1.setOpaque(true);
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 681, 160, 42));

        btnReportes.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnReportes.setForeground(new java.awt.Color(55, 65, 81));
        btnReportes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconAdmin/icons8-flyer-promotionN.png"))); // NOI18N
        btnReportes.setBorder(null);
        btnReportes.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnReportes.setOpaque(true);
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        jPanel1.add(btnReportes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 160, 42));

        jPanel2.setBackground(new java.awt.Color(249, 250, 251));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 176, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 766, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 770));

        jPanel3.setBackground(new java.awt.Color(255, 122, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1186, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 106, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 0, 1190, 110));

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

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnReportesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblLogo;
    public javax.swing.JLabel LblRol;
    public javax.swing.JLabel LblSaludo;
    public javax.swing.JLabel LblimagenPrincipal;
    public javax.swing.JPanel PanelVistas;
    public javax.swing.JButton btnCrearAdmin;
    public javax.swing.JButton btnCuenta1;
    public javax.swing.JButton btnGraficos1;
    public javax.swing.JButton btnMenu1;
    public javax.swing.JButton btnReportes;
    public javax.swing.JButton btnSalir1;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JLabel lblNombre;
    // End of variables declaration//GEN-END:variables
}
