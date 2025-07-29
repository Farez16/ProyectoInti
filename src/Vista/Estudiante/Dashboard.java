package Vista.Estudiante;

import Controlador.ControladorBotones;
import Controlador.ControladorDashboard;
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
    private int idUsuario;
    private ControladorDashboard controladorDashboard;

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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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

    public ControladorDashboard getControladorDashboard() {
        return controladorDashboard;
    }

    public void setControladorDashboard(ControladorDashboard controladorDashboard) {
        this.controladorDashboard = controladorDashboard;
    }
private void iniciarEfectosBotones() {
    // 1. Crear el mapa de botones usando las referencias directas
    Map<String, JButton> botonesMap = new HashMap<>();
    botonesMap.put("btnDashboard", btnDashboard);
    botonesMap.put("btnCuenta", btnCuenta);
    botonesMap.put("btnCertificado", btnCertificado);
    botonesMap.put("btnJuegos", btnJuegos);
    botonesMap.put("btnSalir", btnSalir);

    // Debug: Verificar el mapeo
    System.out.println("=== Mapeo de Botones Estudiante ===");
    botonesMap.forEach((key, value) -> {
        System.out.println(key + " -> " + value.getText());
    });

    // 2. Inicializar el controlador
    TextoBotones modeloTextos = new TextoBotones();
    controladorBotones = new ControladorBotones(botonesMap, modeloTextos, "ESTUDIANTE");
    controladorBotones.iniciar();
}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelVistas = new javax.swing.JPanel();
        LblimagenPrincipal = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        LblRol = new javax.swing.JLabel();
        btnDashboard = new javax.swing.JButton();
        btnCuenta = new javax.swing.JButton();
        btnCertificado = new javax.swing.JButton();
        btnJuegos = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        LblLogo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        LblSaludo = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelVistas.setBackground(new java.awt.Color(245, 233, 211));
        PanelVistas.setPreferredSize(new java.awt.Dimension(1190, 660));
        PanelVistas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(PanelVistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 110, 1190, 660));

        LblimagenPrincipal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Users.png"))); // NOI18N
        jPanel1.add(LblimagenPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 10, 90, 90));

        lblNombre.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(55, 65, 81));
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 130, 30));

        LblRol.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblRol.setForeground(new java.awt.Color(55, 65, 81));
        jPanel1.add(LblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 60, 130, 30));

        btnDashboard.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(55, 65, 81));
        btnDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconEstu/icons8-menú-principal-25r.png"))); // NOI18N
        btnDashboard.setBorder(null);
        btnDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDashboard.setOpaque(true);
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });
        jPanel1.add(btnDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 160, 42));

        btnCuenta.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnCuenta.setForeground(new java.awt.Color(55, 65, 81));
        btnCuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconEstu/icons8-usuario-masculino-en-círculo-25r.png"))); // NOI18N
        btnCuenta.setBorder(null);
        btnCuenta.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCuenta.setOpaque(true);
        btnCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuentaActionPerformed(evt);
            }
        });
        jPanel1.add(btnCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 160, 42));

        btnCertificado.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnCertificado.setForeground(new java.awt.Color(55, 65, 81));
        btnCertificado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconEstu/icons8-certificado-25r.png"))); // NOI18N
        btnCertificado.setBorder(null);
        btnCertificado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCertificado.setOpaque(true);
        jPanel1.add(btnCertificado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 160, 42));

        btnJuegos.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnJuegos.setForeground(new java.awt.Color(55, 65, 81));
        btnJuegos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconEstu/icons8-control-25r.png"))); // NOI18N
        btnJuegos.setBorder(null);
        btnJuegos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnJuegos.setOpaque(true);
        btnJuegos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJuegosActionPerformed(evt);
            }
        });
        jPanel1.add(btnJuegos, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 160, 42));

        btnSalir.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(55, 65, 81));
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconEstu/icons8-salir-redondeado-25r.png"))); // NOI18N
        btnSalir.setBorder(null);
        btnSalir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSalir.setOpaque(true);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 681, 160, 42));

        jPanel2.setBackground(new java.awt.Color(249, 250, 251));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblLogo.setForeground(new java.awt.Color(255, 255, 255));
        LblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo135x132.png"))); // NOI18N
        jPanel2.add(LblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 23, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 770));

        jPanel3.setBackground(new java.awt.Color(200, 225, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        LblSaludo.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        LblSaludo.setForeground(new java.awt.Color(55, 65, 81));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(LblSaludo, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(774, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addComponent(LblSaludo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
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
