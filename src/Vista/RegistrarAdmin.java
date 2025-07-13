package Vista;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistrarAdmin extends javax.swing.JPanel {


    public RegistrarAdmin() {
        initComponents();
    }

    public JLabel getLblContraseña() {
        return LblContraseña;
    }

    public JLabel getLblCorreo() {
        return LblCorreo;
    }

    public JLabel getLblNombre() {
        return LblNombre;
    }

    public JPanel getPanelRegistrarAdmin() {
        return PanelRegistrarAdmin;
    }

    public JTextField getTxtCorreo() {
        return TxtCorreo;
    }

    public JTextField getTxtNombre() {
        return TxtNombre;
    }

    public JButton getBtnRegistrarAd() {
        return btnRegistrarAd;
    }

    public JLabel getjLabel1() {
        return LblTitulo;
    }

    public JPasswordField getjPassword() {
        return jPassword;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelRegistrarAdmin = new javax.swing.JPanel();
        LblTitulo = new javax.swing.JLabel();
        LblNombre = new javax.swing.JLabel();
        LblCorreo = new javax.swing.JLabel();
        TxtCorreo = new javax.swing.JTextField();
        TxtNombre = new javax.swing.JTextField();
        LblContraseña = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        btnRegistrarAd = new javax.swing.JButton();
        LblimagenFondo = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1160, 660));

        PanelRegistrarAdmin.setBackground(new java.awt.Color(255, 255, 255));
        PanelRegistrarAdmin.setMinimumSize(new java.awt.Dimension(1190, 660));
        PanelRegistrarAdmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblTitulo.setText("Registrar Administrador");
        PanelRegistrarAdmin.add(LblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(464, 119, -1, -1));

        LblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblNombre.setText("Nombre:");
        PanelRegistrarAdmin.add(LblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 218, -1, -1));

        LblCorreo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblCorreo.setText("Correo:");
        PanelRegistrarAdmin.add(LblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 172, -1, -1));
        PanelRegistrarAdmin.add(TxtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 172, 156, -1));
        PanelRegistrarAdmin.add(TxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 218, 156, -1));

        LblContraseña.setBackground(new java.awt.Color(0, 0, 0));
        LblContraseña.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblContraseña.setText("Contraseña:");
        PanelRegistrarAdmin.add(LblContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(373, 279, -1, -1));
        PanelRegistrarAdmin.add(jPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 279, 156, -1));

        btnRegistrarAd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistrarAd.setText("Registrar");
        PanelRegistrarAdmin.add(btnRegistrarAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(517, 428, -1, -1));

        LblimagenFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lbl.jpg"))); // NOI18N
        PanelRegistrarAdmin.add(LblimagenFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 660));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelRegistrarAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelRegistrarAdmin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblContraseña;
    public javax.swing.JLabel LblCorreo;
    public javax.swing.JLabel LblNombre;
    public javax.swing.JLabel LblTitulo;
    public javax.swing.JLabel LblimagenFondo;
    public javax.swing.JPanel PanelRegistrarAdmin;
    public javax.swing.JTextField TxtCorreo;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JButton btnRegistrarAd;
    public javax.swing.JPasswordField jPassword;
    // End of variables declaration//GEN-END:variables
}
