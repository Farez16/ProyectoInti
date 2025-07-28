package Vista.Admin;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegistrarAdmin extends javax.swing.JPanel {


    public RegistrarAdmin() {
        initComponents();
         // Deshabilitar campos inicialmente
         deshabilitarCampos();
    }

    public JLabel getLblCodigoOTP() {
        return LblCodigoOTP;
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

    public JLabel getLblTitulo() {
        return LblTitulo;
    }


    public JPanel getPanelRegistrarAdmin() {
        return PanelRegistrarAdmin;
    }

    public JTextField getTxtCodigoOTP() {
        return TxtCodigoOTP;
    }

    public JTextField getTxtCorreo() {
        return TxtCorreo;
    }

    public JTextField getTxtNombre() {
        return TxtNombre;
    }

    public JButton getBtnEnviarCodigo() {
        return btnEnviarCodigo;
    }

    public JButton getBtnRegistrarAd() {
        return btnRegistrarAd;
    }

    public JButton getBtnVerificarCodigo() {
        return btnVerificarCodigo;
    }

    public JPasswordField getjPassword() {
        return jPassword;
    }
public void deshabilitarCampos() {
    TxtNombre.setEnabled(false);
    jPassword.setEnabled(false);
    btnRegistrarAd.setEnabled(false);
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
        btnEnviarCodigo = new javax.swing.JButton();
        LblCodigoOTP = new javax.swing.JLabel();
        TxtCodigoOTP = new javax.swing.JTextField();
        btnVerificarCodigo = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1190, 660));

        PanelRegistrarAdmin.setBackground(new java.awt.Color(204, 221, 255));
        PanelRegistrarAdmin.setMinimumSize(new java.awt.Dimension(1190, 660));
        PanelRegistrarAdmin.setPreferredSize(new java.awt.Dimension(1190, 660));

        LblTitulo.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        LblTitulo.setText("Registrar Administrador");

        LblNombre.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblNombre.setText("Nombre:");

        LblCorreo.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblCorreo.setText("Correo:");

        TxtCorreo.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N

        TxtNombre.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N

        LblContraseña.setBackground(new java.awt.Color(0, 0, 0));
        LblContraseña.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblContraseña.setText("Contraseña:");

        jPassword.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N

        btnRegistrarAd.setBackground(new java.awt.Color(255, 122, 0));
        btnRegistrarAd.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        btnRegistrarAd.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarAd.setText("Registrar");

        btnEnviarCodigo.setBackground(new java.awt.Color(255, 122, 0));
        btnEnviarCodigo.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        btnEnviarCodigo.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarCodigo.setText("Enviar Codigo");

        LblCodigoOTP.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblCodigoOTP.setText("Codigo OTP:");

        TxtCodigoOTP.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N

        btnVerificarCodigo.setBackground(new java.awt.Color(255, 122, 0));
        btnVerificarCodigo.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        btnVerificarCodigo.setForeground(new java.awt.Color(255, 255, 255));
        btnVerificarCodigo.setText("Verificar Codigo");

        javax.swing.GroupLayout PanelRegistrarAdminLayout = new javax.swing.GroupLayout(PanelRegistrarAdmin);
        PanelRegistrarAdmin.setLayout(PanelRegistrarAdminLayout);
        PanelRegistrarAdminLayout.setHorizontalGroup(
            PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGap(490, 490, 490)
                .addComponent(LblTitulo))
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGap(373, 373, 373)
                .addComponent(LblCorreo)
                .addGap(61, 61, 61)
                .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnEnviarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addComponent(LblCodigoOTP)
                .addGap(12, 12, 12)
                .addComponent(TxtCodigoOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnVerificarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addComponent(LblNombre)
                .addGap(43, 43, 43)
                .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGap(380, 380, 380)
                .addComponent(LblContraseña)
                .addGap(17, 17, 17)
                .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGap(510, 510, 510)
                .addComponent(btnRegistrarAd, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        PanelRegistrarAdminLayout.setVerticalGroup(
            PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(LblTitulo)
                .addGap(57, 57, 57)
                .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(LblCorreo))
                    .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LblCodigoOTP)
                    .addComponent(TxtCodigoOTP, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(PanelRegistrarAdminLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnVerificarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LblNombre)
                    .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(PanelRegistrarAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LblContraseña)
                    .addComponent(jPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(60, 60, 60)
                .addComponent(btnRegistrarAd, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    public javax.swing.JLabel LblCodigoOTP;
    public javax.swing.JLabel LblContraseña;
    public javax.swing.JLabel LblCorreo;
    public javax.swing.JLabel LblNombre;
    public javax.swing.JLabel LblTitulo;
    public javax.swing.JPanel PanelRegistrarAdmin;
    public javax.swing.JTextField TxtCodigoOTP;
    public javax.swing.JTextField TxtCorreo;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JButton btnEnviarCodigo;
    public javax.swing.JButton btnRegistrarAd;
    public javax.swing.JButton btnVerificarCodigo;
    public javax.swing.JPasswordField jPassword;
    // End of variables declaration//GEN-END:variables
}
