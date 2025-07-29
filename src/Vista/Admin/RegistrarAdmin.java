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
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1190, 660));

        PanelRegistrarAdmin.setBackground(new java.awt.Color(200, 225, 255));
        PanelRegistrarAdmin.setMinimumSize(new java.awt.Dimension(1190, 660));
        PanelRegistrarAdmin.setPreferredSize(new java.awt.Dimension(1190, 660));
        PanelRegistrarAdmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblTitulo.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        LblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        LblTitulo.setText("Registrar Administrador");
        PanelRegistrarAdmin.add(LblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

        LblNombre.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblNombre.setForeground(new java.awt.Color(255, 255, 255));
        LblNombre.setText("Nombre:");
        PanelRegistrarAdmin.add(LblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, -1, -1));

        LblCorreo.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblCorreo.setForeground(new java.awt.Color(255, 255, 255));
        LblCorreo.setText("Correo:");
        PanelRegistrarAdmin.add(LblCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 170, -1, -1));

        TxtCorreo.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        PanelRegistrarAdmin.add(TxtCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, 210, 30));

        TxtNombre.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        PanelRegistrarAdmin.add(TxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 210, 30));

        LblContraseña.setBackground(new java.awt.Color(0, 0, 0));
        LblContraseña.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblContraseña.setForeground(new java.awt.Color(255, 255, 255));
        LblContraseña.setText("Contraseña:");
        PanelRegistrarAdmin.add(LblContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 310, -1, -1));

        jPassword.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        PanelRegistrarAdmin.add(jPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 310, 210, 30));

        btnRegistrarAd.setBackground(new java.awt.Color(255, 122, 0));
        btnRegistrarAd.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        btnRegistrarAd.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarAd.setText("Registrar");
        PanelRegistrarAdmin.add(btnRegistrarAd, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 400, 180, 40));

        btnEnviarCodigo.setBackground(new java.awt.Color(255, 122, 0));
        btnEnviarCodigo.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        btnEnviarCodigo.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviarCodigo.setText("Enviar Codigo");
        PanelRegistrarAdmin.add(btnEnviarCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 170, 169, 30));

        LblCodigoOTP.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        LblCodigoOTP.setForeground(new java.awt.Color(255, 255, 255));
        LblCodigoOTP.setText("Codigo OTP:");
        PanelRegistrarAdmin.add(LblCodigoOTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 210, -1, -1));

        TxtCodigoOTP.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        PanelRegistrarAdmin.add(TxtCodigoOTP, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, 210, 30));

        btnVerificarCodigo.setBackground(new java.awt.Color(255, 122, 0));
        btnVerificarCodigo.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        btnVerificarCodigo.setForeground(new java.awt.Color(255, 255, 255));
        btnVerificarCodigo.setText("Verificar Codigo");
        PanelRegistrarAdmin.add(btnVerificarCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 220, 169, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoooo12.jpg"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(1190, 110));
        PanelRegistrarAdmin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 660));

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
    public javax.swing.JLabel jLabel1;
    public javax.swing.JPasswordField jPassword;
    // End of variables declaration//GEN-END:variables
}
