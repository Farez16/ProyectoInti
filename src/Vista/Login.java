package Vista;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.*;
import Controlador.ControladorLogin;
import java.awt.Color;

public class Login extends javax.swing.JFrame {

    private String correoUsuario;
    private JPanel panelLoginOriginal;

    public Login() {
        initComponents();
        panelLoginOriginal = panelFormularioLogin;
        setLocationRelativeTo(null);
        TxtContraseña.setEnabled(false);
        campoUsuario();
        
        SwingUtilities.invokeLater(() -> {
        // Puedes usar otro componente que no moleste visualmente
        inf.requestFocusInWindow();
    });

    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public JPanel getPanelLoginOriginal() {
        return panelLoginOriginal;
    }

    public JPanel getPanel1() {
        return Panel1;
    }

    public JTextField getTxtContraseña() {
        return TxtContraseña;
    }

    public JTextField getTxtUsuario() {
        return TxtUsuario;
    }

    public JButton getBtnCodigo() {
        return btnCodigo;
    }

    public JButton getBtnInicar() {
        return btnInicar;
    }
    

    public void mostrarPanelEnPanel1(JPanel panel) {
        Panel1.removeAll();
        Panel1.setLayout(new BorderLayout());
        Panel1.add(panel, BorderLayout.CENTER);
        Panel1.revalidate();
        Panel1.repaint();
    }

    public void limpiarCampos() {
        TxtUsuario.setText("");
        TxtContraseña.setText("");
        TxtContraseña.setEnabled(false); // Opcional si usas OTP
        TxtUsuario.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel1 = new javax.swing.JPanel();
        panelFormularioLogin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        TxtUsuario = new javax.swing.JTextField();
        TxtContraseña = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnCodigo = new javax.swing.JButton();
        inf = new javax.swing.JLabel();
        btnInicar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel1.setBackground(new java.awt.Color(255, 255, 255));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelFormularioLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(50, 10, 10));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TxtUsuario.setBackground(new java.awt.Color(255, 255, 255));
        TxtUsuario.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TxtUsuario.setForeground(new java.awt.Color(102, 102, 102));
        TxtUsuario.setText(" Usuario");
        TxtUsuario.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel1.add(TxtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 125, 330, 30));

        TxtContraseña.setBackground(new java.awt.Color(255, 255, 255));
        TxtContraseña.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TxtContraseña.setForeground(new java.awt.Color(102, 102, 102));
        TxtContraseña.setText(" Contraseña");
        TxtContraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel1.add(TxtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 330, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(null);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 230, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel4.setForeground(null);
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, 230, 40));

        btnCodigo.setBackground(new java.awt.Color(171, 52, 2));
        btnCodigo.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnCodigo.setForeground(new java.awt.Color(255, 255, 255));
        btnCodigo.setText("VALIDAR/CREAR CUENTA");
        btnCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(btnCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, 230, 40));

        inf.setBackground(new java.awt.Color(255, 255, 255));
        inf.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inf.setForeground(java.awt.Color.white);
        inf.setText("Para ingresar, deberá validar su cuenta o crear una nueva si aún no tiene una.");
        jPanel1.add(inf, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 400, 490, 20));

        btnInicar.setBackground(new java.awt.Color(171, 52, 2));
        btnInicar.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        btnInicar.setForeground(new java.awt.Color(255, 255, 255));
        btnInicar.setText("INICIAR SESION");
        btnInicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicarActionPerformed(evt);
            }
        });
        jPanel1.add(btnInicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 230, 40));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        jLabel2.setForeground(java.awt.Color.white);
        jLabel2.setText("I N T I");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 40, 150, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo 90 x90.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, -1, 90));

        panelFormularioLogin.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 870, 470));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo l,d.png"))); // NOI18N
        panelFormularioLogin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        Panel1.add(panelFormularioLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        getContentPane().add(Panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCodigoActionPerformed

    private void btnInicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInicarActionPerformed

    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    private void campoUsuario() {
        TxtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TxtUsuario.getText().equals(" Usuario")) {
                    TxtUsuario.setText("");
                    TxtUsuario.setForeground(Color.BLACK); // Cambiar color normal
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TxtUsuario.getText().isEmpty()) {
                    TxtUsuario.setText(" Usuario");
                    TxtUsuario.setForeground(new Color(187,187,187)); // Volver al color del placeholder
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel Panel1;
    public javax.swing.JTextField TxtContraseña;
    public javax.swing.JTextField TxtUsuario;
    public javax.swing.JButton btnCodigo;
    public javax.swing.JButton btnInicar;
    public javax.swing.JLabel inf;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel panelFormularioLogin;
    // End of variables declaration//GEN-END:variables

}
