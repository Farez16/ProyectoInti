package Vista;

import Controlador.ControladorBotones;
import Modelo.TextoBotones;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dashboard extends javax.swing.JPanel {

    private String correoUsuario;
    private ControladorBotones controladorBotones;

    public Dashboard(String correoUsuario) {
        initComponents();
        iniciarEfectosBotones();
        this.correoUsuario = correoUsuario;
        btnDashboard.setOpaque(false);
        btnDashboard.setContentAreaFilled(false);
        btnDashboard.setBorderPainted(false);

        btnCuenta.setOpaque(false);
        btnCuenta.setContentAreaFilled(false);
        btnCuenta.setBorderPainted(false);

        btnCertificado.setOpaque(false);
        btnCertificado.setContentAreaFilled(false);
        btnCertificado.setBorderPainted(false);

        btnJuegos.setOpaque(false);
        btnJuegos.setContentAreaFilled(false);
        btnJuegos.setBorderPainted(false);

        btnSalir.setOpaque(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setBorderPainted(false);
    }

    private void iniciarEfectosBotones() {
    // 1. Crear el mapa de botones
    Map<String, JButton> botonesMap = new HashMap<>();
    botonesMap.put("btnDashboard", btnDashboard);
    botonesMap.put("btnCuenta", btnCuenta);
    botonesMap.put("btnCertificado", btnCertificado);
    botonesMap.put("btnJuegos", btnJuegos);
    botonesMap.put("btnSalir", btnSalir);
    
    // 2. Pasar el mapa al controlador
    TextoBotones modeloTextos = new TextoBotones();
    controladorBotones = new ControladorBotones(botonesMap, modeloTextos);
    controladorBotones.iniciar();
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

    public JButton getBtnCertificado() {
        return btnCertificado;
    }

    public JButton getBtnCuenta() {
        return btnCuenta;
    }

    public JButton getBtnDashboard() {
        return btnDashboard;
    }

    public JButton getBtnJuegos() {
        return btnJuegos;
    }

    public JButton getBtnSalir() {
        return btnSalir;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void mostrarVista(JPanel nuevaVista) {
    PanelVistas.removeAll();
    PanelVistas.setLayout(new java.awt.BorderLayout()); // Usa BorderLayout
    PanelVistas.add(nuevaVista, java.awt.BorderLayout.CENTER);
    PanelVistas.revalidate();
    PanelVistas.repaint();
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
   public void actualizarImagenPerfil(String ruta) {
        try {
            BufferedImage img;
            if (ruta.startsWith("http")) {
                img = ImageIO.read(new URL(ruta));
            } else {
                img = ImageIO.read(new File(ruta));
            }
            
            int size = 120;
            BufferedImage circleBuffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();
            Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
            g2.setClip(circle);
            g2.drawImage(img, 0, 0, size, size, null);
            g2.dispose();

            LblimagenPrincipal.setIcon(new ImageIcon(circleBuffer));
            LblimagenPrincipal.revalidate();
            LblimagenPrincipal.repaint();
            
        } catch (Exception ex) {
            System.err.println("Error actualizando imagen en Dashboard: " + ex.getMessage());
        }
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
        btnDashboard = new javax.swing.JButton();
        btnCuenta = new javax.swing.JButton();
        btnCertificado = new javax.swing.JButton();
        btnJuegos = new javax.swing.JButton();
        LblLogo = new javax.swing.JLabel();
        btnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelVistas.setBackground(new java.awt.Color(247, 249, 252));
        PanelVistas.setPreferredSize(new java.awt.Dimension(1190, 660));
        PanelVistas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(PanelVistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 1190, 660));

        LblSaludo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LblSaludo.setForeground(new java.awt.Color(55, 65, 81));
        jPanel1.add(LblSaludo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 24, 380, 60));

        LblimagenPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Users.png"))); // NOI18N
        jPanel1.add(LblimagenPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, 90, 90));

        lblNombre.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(55, 65, 81));
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 20, 130, 40));

        LblRol.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LblRol.setForeground(new java.awt.Color(55, 65, 81));
        jPanel1.add(LblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 60, 130, 30));

        btnDashboard.setBackground(new java.awt.Color(255, 122, 0));
        btnDashboard.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(107, 114, 128));
        btnDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/menu.png"))); // NOI18N
        btnDashboard.setBorder(null);
        btnDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDashboard.setOpaque(true);
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });
        jPanel1.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 160, 42));

        btnCuenta.setBackground(new java.awt.Color(255, 122, 0));
        btnCuenta.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCuenta.setForeground(new java.awt.Color(107, 114, 128));
        btnCuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/icono-usuario.png"))); // NOI18N
        btnCuenta.setBorder(null);
        btnCuenta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCuenta.setOpaque(true);
        btnCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuentaActionPerformed(evt);
            }
        });
        jPanel1.add(btnCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 160, 42));

        btnCertificado.setBackground(new java.awt.Color(255, 122, 0));
        btnCertificado.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCertificado.setForeground(new java.awt.Color(107, 114, 128));
        btnCertificado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/certificado.png"))); // NOI18N
        btnCertificado.setBorder(null);
        btnCertificado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCertificado.setOpaque(true);
        jPanel1.add(btnCertificado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 160, 42));

        btnJuegos.setBackground(new java.awt.Color(255, 122, 0));
        btnJuegos.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnJuegos.setForeground(new java.awt.Color(107, 114, 128));
        btnJuegos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/play.png"))); // NOI18N
        btnJuegos.setBorder(null);
        btnJuegos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnJuegos.setOpaque(true);
        btnJuegos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuegosActionPerformed(evt);
            }
        });
        jPanel1.add(btnJuegos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 160, 42));

        LblLogo.setForeground(new java.awt.Color(255, 255, 255));
        LblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo60X60.png"))); // NOI18N
        jPanel1.add(LblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, -1, -1));

        btnSalir.setBackground(new java.awt.Color(255, 122, 0));
        btnSalir.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(107, 114, 128));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cierre-de-sesión.png"))); // NOI18N
        btnSalir.setBorder(null);
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.setOpaque(true);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 681, 160, 42));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 770, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 770));

        jPanel3.setBackground(new java.awt.Color(247, 249, 252));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1190, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
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

    private void btnJuegosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJuegosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnJuegosActionPerformed

    private void btnCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuentaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCuentaActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblLogo;
    public javax.swing.JLabel LblRol;
    public javax.swing.JLabel LblSaludo;
    public javax.swing.JLabel LblimagenPrincipal;
    public javax.swing.JPanel PanelVistas;
    public javax.swing.JButton btnCertificado;
    public javax.swing.JButton btnCuenta;
    public javax.swing.JButton btnDashboard;
    public javax.swing.JButton btnJuegos;
    public javax.swing.JButton btnSalir;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JLabel lblNombre;
    // End of variables declaration//GEN-END:variables
}
