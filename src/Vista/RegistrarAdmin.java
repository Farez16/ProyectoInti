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
        return jLabel1;
    }

    public JPasswordField getjPassword() {
        return jPassword;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelRegistrarAdmin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LblNombre = new javax.swing.JLabel();
        LblCorreo = new javax.swing.JLabel();
        TxtCorreo = new javax.swing.JTextField();
        TxtNombre = new javax.swing.JTextField();
        LblContraseña = new javax.swing.JLabel();
        jPassword = new javax.swing.JPasswordField();
        btnRegistrarAd = new javax.swing.JButton();

        PanelRegistrarAdmin.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("Registrar Administrador");

        LblNombre.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblNombre.setText("Nombre:");

        LblCorreo.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblCorreo.setText("Correo:");

        LblContraseña.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblContraseña.setText("Contraseña:");

        btnRegistrarAd.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegistrarAd.setText("Registrar");

        javax.swing.GroupLayout PanelRegistrarAdminLayout = new javax.swing.GroupLayout(PanelRegistrarAdmin);
        PanelRegistrarAdmin.setLayout(PanelRegistrarAdminLayout);
        PanelRegistrarAdminLayout.setHorizontalGroup(
            PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                        .addGap(428, 428, 428)
                        .addComponent(jLabel1))
                    .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                        .addGap(214, 214, 214)
                        .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblCorreo)
                            .addComponent(LblNombre)
                            .addComponent(LblContraseña))
                        .addGap(38, 38, 38)
                        .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxtCorreo)
                            .addComponent(TxtNombre)
                            .addComponent(jPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)))
                    .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                        .addGap(533, 533, 533)
                        .addComponent(btnRegistrarAd)))
                .addContainerGap(576, Short.MAX_VALUE))
        );
        PanelRegistrarAdminLayout.setVerticalGroup(
            PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblCorreo)
                    .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblNombre)
                    .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LblContraseña)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(127, 127, 127)
                .addComponent(btnRegistrarAd)
                .addContainerGap(297, Short.MAX_VALUE))
        );

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
    public javax.swing.JPanel PanelRegistrarAdmin;
    public javax.swing.JTextField TxtCorreo;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JButton btnRegistrarAd;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPasswordField jPassword;
    // End of variables declaration//GEN-END:variables
}
