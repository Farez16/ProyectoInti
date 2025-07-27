package Vista.Admin;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Cuenta extends JPanel {

    public javax.swing.JButton jButton2SubirURL;
    public JLabel labelCorreoUsuario;
    public JLabel labelImagenPerfil;
    public JButton btnSubirImagenArchivo;
    public JButton btnSubirImagenURL;
    public JLabel labelFechaHoraActual;
    public JLabel labelFechaRegistro;
    public JLabel labelUltimoAcceso;

    public Cuenta() {
        initComponents();
    }

    public JLabel getLabelCorreoUsuario() {
        return labelCorreoUsuario;
    }

    public JLabel getLabelImagenPerfil() {
        return labelImagenPerfil;
    }

    public JButton getBtnSubirImagenArchivo() {
        return btnSubirImagenArchivo;
    }

    public JButton getBtnSubirImagenURL() {
        return btnSubirImagenURL;
    }

    public JLabel getLabelFechaHoraActual() {
        return labelFechaHoraActual;
    }

    public JLabel getLabelFechaRegistro() {
        return labelFechaRegistro;
    }

    public JLabel getLabelUltimoAcceso() {
        return labelUltimoAcceso;
    }

    public JLabel getLabelPerfildelUsuario() {
        return LabelPerfildelUsuario;
    }

    public JLabel getLblSaludo() {
        return LblSaludo;
    }

    public JLabel getLblimagen() {
        return Lblimagen;
    }

    public JPanel getPanelVistas() {
        return PanelVistas;
    }

    public JButton getjButton1CambiarContraseña() {
        return jButton1CambiarContraseña;
    }

    public JButton getjButton1SubirImagen() {
        return jButton1SubirImagen;
    }

    public JLabel getjLabel1Contra1() {
        return jLabel1Contra1;
    }

    public JLabel getjLabel1FechayHora1() {
        return jLabel1FechayHora1;
    }

    public JLabel getjLabel1Usuario() {
        return jLabel1Usuario;
    }

    public JLabel getjLabel1loginactivity() {
        return jLabel1loginactivity;
    }


    public JPanel getjPanel1() {
        return jPanel1;
    }

    public JTextField getjTextField1Contraseña1() {
        return jTextField1Contraseña1;
    }

    public JTextField getjTextField1MostrarUsuario() {
        return jTextField1MostrarUsuario;
    }

    public void setLabelCorreoUsuario(JLabel labelCorreoUsuario) {
        this.labelCorreoUsuario = labelCorreoUsuario;
    }

    public void setLabelImagenPerfil(JLabel labelImagenPerfil) {
        this.labelImagenPerfil = labelImagenPerfil;
    }

    public void setBtnSubirImagenArchivo(JButton btnSubirImagenArchivo) {
        this.btnSubirImagenArchivo = btnSubirImagenArchivo;
    }

    public void setBtnSubirImagenURL(JButton btnSubirImagenURL) {
        this.btnSubirImagenURL = btnSubirImagenURL;
    }

    public void setLabelFechaHoraActual(JLabel labelFechaHoraActual) {
        this.labelFechaHoraActual = labelFechaHoraActual;
    }

    public void setLabelFechaRegistro(JLabel labelFechaRegistro) {
        this.labelFechaRegistro = labelFechaRegistro;
    }

    public void setLabelUltimoAcceso(JLabel labelUltimoAcceso) {
        this.labelUltimoAcceso = labelUltimoAcceso;
    }

    public void setLabelPerfildelUsuario(JLabel LabelPerfildelUsuario) {
        this.LabelPerfildelUsuario = LabelPerfildelUsuario;
    }

    public void setLblSaludo(JLabel LblSaludo) {
        this.LblSaludo = LblSaludo;
    }

    public void setLblimagen(JLabel Lblimagen) {
        this.Lblimagen = Lblimagen;
    }

    public void setjButton1CambiarContraseña(JButton jButton1CambiarContraseña) {
        this.jButton1CambiarContraseña = jButton1CambiarContraseña;
    }


    public void setjButton1SubirImagen(JButton jButton1SubirImagen) {
        this.jButton1SubirImagen = jButton1SubirImagen;
    }

    public void setjLabel1Contra1(JLabel jLabel1Contra1) {
        this.jLabel1Contra1 = jLabel1Contra1;
    }

    public void setjLabel1FechayHora1(JLabel jLabel1FechayHora1) {
        this.jLabel1FechayHora1 = jLabel1FechayHora1;
    }

    public void setjLabel1Usuario(JLabel jLabel1Usuario) {
        this.jLabel1Usuario = jLabel1Usuario;
    }

    public void setjLabel1loginactivity(JLabel jLabel1loginactivity) {
        this.jLabel1loginactivity = jLabel1loginactivity;
    }


    public void setjPanel1(JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    public void setjTextField1Contraseña1(JTextField jTextField1Contraseña1) {
        this.jTextField1Contraseña1 = jTextField1Contraseña1;
    }

    public void setjTextField1MostrarUsuario(JTextField jTextField1MostrarUsuario) {
        this.jTextField1MostrarUsuario = jTextField1MostrarUsuario;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PanelVistas = new javax.swing.JPanel();
        jLabel1Usuario = new javax.swing.JLabel();
        jTextField1MostrarUsuario = new javax.swing.JTextField();
        Lblimagen = new javax.swing.JLabel();
        jButton1SubirImagen = new javax.swing.JButton();
        jButton1CambiarContraseña = new javax.swing.JButton();
        jLabel1loginactivity = new javax.swing.JLabel();
        jLabel1FechayHora1 = new javax.swing.JLabel();
        jLabel1Contra1 = new javax.swing.JLabel();
        jTextField1Contraseña1 = new javax.swing.JTextField();
        LblimagenFondo = new javax.swing.JLabel();
        LblSaludo = new javax.swing.JLabel();
        LabelPerfildelUsuario = new javax.swing.JLabel();

        jPanel1.setPreferredSize(new java.awt.Dimension(1190, 660));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelVistas.setBackground(new java.awt.Color(255, 255, 255));
        PanelVistas.setPreferredSize(new java.awt.Dimension(1190, 660));
        PanelVistas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1Usuario.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel1Usuario.setForeground(new java.awt.Color(55, 65, 81));
        jLabel1Usuario.setText("Usuario:");
        PanelVistas.add(jLabel1Usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, -1, -1));

        jTextField1MostrarUsuario.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        PanelVistas.add(jTextField1MostrarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, 210, 30));

        Lblimagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Users.png"))); // NOI18N
        PanelVistas.add(Lblimagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 100, 100));

        jButton1SubirImagen.setBackground(new java.awt.Color(255, 122, 0));
        jButton1SubirImagen.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        jButton1SubirImagen.setForeground(new java.awt.Color(255, 255, 255));
        jButton1SubirImagen.setText("Subir Imagen");
        jButton1SubirImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1SubirImagenActionPerformed(evt);
            }
        });
        PanelVistas.add(jButton1SubirImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 200, 180, 40));

        jButton1CambiarContraseña.setBackground(new java.awt.Color(255, 122, 0));
        jButton1CambiarContraseña.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        jButton1CambiarContraseña.setForeground(new java.awt.Color(255, 255, 255));
        jButton1CambiarContraseña.setText("Cambiar Contraseña");
        jButton1CambiarContraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PanelVistas.add(jButton1CambiarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 380, 210, 40));

        jLabel1loginactivity.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1loginactivity.setForeground(new java.awt.Color(55, 65, 81));
        jLabel1loginactivity.setText("<html>\n    <span style=\"font-size:14px; font-family:Montserrat; color:#000000;\"><b>Actividad de la cuenta</b></span><br><br>\n    <span style=\"font-size:13px; font-family:Montserrat; color:#000000;\"><b>Primer acceso al sitio</b></span><br>\n    <span style=\"font-size:13px; font-family:Montserrat;\">Monday, 22 May 2023, 9:54 PM <i>(2 years 42 days)</i></span><br><br>\n    <span style=\"font-size:13px; font-family:Montserrat; color:#000000;\"><b>Último acceso al sitio</b></span><br>\n    <span style=\"font-size:13px; font-family:Montserrat;\">Thursday, 3 July 2025, 12:11 AM <i>(now)</i></span>\n</html>");
        PanelVistas.add(jLabel1loginactivity, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 470, 430, -1));

        jLabel1FechayHora1.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel1FechayHora1.setForeground(new java.awt.Color(55, 65, 81));
        jLabel1FechayHora1.setText("<html>     <span style=\"font-size:14px; font-family:Montserrat; color:#000000;\">         <b>Fecha y Hora:</b><br>         <span style=\"color:rgb(55,65,81);\">02 de julio de 2025 - 18:45:30</span>     </span> </html>");
        PanelVistas.add(jLabel1FechayHora1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 280, -1));

        jLabel1Contra1.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel1Contra1.setForeground(new java.awt.Color(55, 65, 81));
        jLabel1Contra1.setText("Contraseña: ");
        PanelVistas.add(jLabel1Contra1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, -1, -1));

        jTextField1Contraseña1.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        PanelVistas.add(jTextField1Contraseña1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 310, 210, 30));

        LblimagenFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lbl.jpg"))); // NOI18N
        PanelVistas.add(LblimagenFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel1.add(PanelVistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 660));

        LblSaludo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblSaludo.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(LblSaludo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 219, 24));

        LabelPerfildelUsuario.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LabelPerfildelUsuario.setForeground(new java.awt.Color(255, 255, 255));
        LabelPerfildelUsuario.setText("<html>     <span style=\"font-size:16px; font-family:Segoe UI; color:#2a4365;\"><b>PERFIL DEL USUARIO</b></span> </html> ");
        jPanel1.add(LabelPerfildelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, -1, 40));

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

    private void jButton1SubirImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1SubirImagenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1SubirImagenActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LabelPerfildelUsuario;
    public javax.swing.JLabel LblSaludo;
    public javax.swing.JLabel Lblimagen;
    public javax.swing.JLabel LblimagenFondo;
    public javax.swing.JPanel PanelVistas;
    public javax.swing.JButton jButton1CambiarContraseña;
    public javax.swing.JButton jButton1SubirImagen;
    public javax.swing.JLabel jLabel1Contra1;
    public javax.swing.JLabel jLabel1FechayHora1;
    public javax.swing.JLabel jLabel1Usuario;
    public javax.swing.JLabel jLabel1loginactivity;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JTextField jTextField1Contraseña1;
    public javax.swing.JTextField jTextField1MostrarUsuario;
    // End of variables declaration//GEN-END:variables
}
