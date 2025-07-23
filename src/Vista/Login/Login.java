package Vista.Login;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Login extends javax.swing.JFrame {

    private String correoUsuario;
    private JPanel panelLoginOriginal;
    public String contrasenaReal = "";
    private Timer timerOcultar;

    public Login() {
        initComponents();
        panelLoginOriginal = panelFormularioLogin;
        setLocationRelativeTo(null);
        TxtContraseña.setEnabled(false);
        campoUsuario();
        campoContraseña();

        // Desbloquear contraseña al escribir usuario
        TxtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (!TxtUsuario.getText().trim().isEmpty() && !TxtUsuario.getText().equals(" Usuario")) {
                    TxtContraseña.setEnabled(true);
                    TxtContraseña.setForeground(Color.BLACK);
                }
            }
        });

        // Simular campo de contraseña seguro con JTextField
        TxtContraseña.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                actualizar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                actualizar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                actualizar();
            }

            private void actualizar() {
                if (TxtContraseña.isEnabled()) {
                    String texto = TxtContraseña.getText();
                    if (!texto.chars().allMatch(c -> c == '*')) {
                        contrasenaReal = texto;
                    }
                    if (timerOcultar != null && timerOcultar.isRunning()) {
                        timerOcultar.stop();
                    }
                    timerOcultar = new Timer(1000, ev -> {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < contrasenaReal.length(); i++) {
                            sb.append("*");
                        }
                        TxtContraseña.setText(sb.toString());
                    });
                    timerOcultar.setRepeats(false);
                    timerOcultar.start();
                }
            }
        });

        SwingUtilities.invokeLater(() -> {
            LblSlogan.requestFocusInWindow();
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

    public JButton getBtnInicar() {
        return btnInicar;
    }

    public JButton getBtnCodigo() {
        return btnCodigo;
    }

    public void mostrarPanelEnPanel1(JPanel panel) {
        Panel1.removeAll();
        Panel1.setLayout(new BorderLayout());
        Panel1.add(panel, BorderLayout.CENTER);
        Panel1.revalidate();
        Panel1.repaint();
    }

    public void limpiarCampos() {
        TxtUsuario.setText("Usuario");
        TxtUsuario.setForeground(new Color(107, 114, 128));
        TxtContraseña.setText("Contraseña");
        TxtContraseña.setForeground(new Color(107, 114, 128));
        contrasenaReal = "";
        TxtContraseña.setEnabled(false);
        TxtUsuario.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel1 = new javax.swing.JPanel();
        panelFormularioLogin = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        TxtUsuario = new javax.swing.JTextField();
        btnCodigo = new javax.swing.JButton();
        btnInicar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        LblSlogan = new javax.swing.JLabel();
        TxtContraseña = new javax.swing.JTextField();
        LblQr = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel1.setBackground(new java.awt.Color(255, 255, 255));
        Panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelFormularioLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TxtUsuario.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        TxtUsuario.setForeground(new java.awt.Color(102, 102, 102));
        TxtUsuario.setText(" Usuario");
        TxtUsuario.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel1.add(TxtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 300, 210, 30));

        btnCodigo.setBackground(new java.awt.Color(255, 122, 0));
        btnCodigo.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        btnCodigo.setForeground(new java.awt.Color(255, 255, 255));
        btnCodigo.setText("REGISTRARSE");
        btnCodigo.setBorder(null);
        btnCodigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCodigoActionPerformed(evt);
            }
        });
        jPanel1.add(btnCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 480, 180, 40));

        btnInicar.setBackground(new java.awt.Color(255, 122, 0));
        btnInicar.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        btnInicar.setForeground(new java.awt.Color(255, 255, 255));
        btnInicar.setText("INICIAR SESION");
        btnInicar.setBorder(null);
        btnInicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicarActionPerformed(evt);
            }
        });
        jPanel1.add(btnInicar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 180, 40));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo178x174.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(181, 25, -1, 170));

        LblSlogan.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        LblSlogan.setForeground(new java.awt.Color(255, 122, 0));
        LblSlogan.setText("Despierta la lengua ancestral");
        jPanel1.add(LblSlogan, new org.netbeans.lib.awtextra.AbsoluteConstraints(134, 225, 273, 23));

        TxtContraseña.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        TxtContraseña.setForeground(new java.awt.Color(102, 102, 102));
        TxtContraseña.setText("Contraseña");
        TxtContraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        jPanel1.add(TxtContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 370, 210, 30));

        LblQr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/qr1.png"))); // NOI18N
        jPanel1.add(LblQr, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 570, -1, -1));

        panelFormularioLogin.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 540, 770));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/FondoLogin.jpg"))); // NOI18N
        panelFormularioLogin.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 770));

        Panel1.add(panelFormularioLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        getContentPane().add(Panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnInicarActionPerformed

    private void btnCodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCodigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCodigoActionPerformed

    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    private void campoUsuario() {
        Color placeholderColor = new Color(107, 114, 128);

        TxtUsuario.setText("Usuario");
        TxtUsuario.setForeground(placeholderColor);

        TxtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TxtUsuario.getText().equals("Usuario")) {
                    TxtUsuario.setText("");
                    TxtUsuario.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                String correo = TxtUsuario.getText().trim();
                if (correo.isEmpty()) {
                    TxtUsuario.setText("Usuario");
                    TxtUsuario.setForeground(placeholderColor);
                } else if (!esCorreoPermitido(correo)) {
                    JOptionPane.showMessageDialog(Login.this,
                            "Ingrese un correo válido de: Gmail, Hotmail, Outlook o institucional.",
                            "Correo inválido", JOptionPane.ERROR_MESSAGE);
                    TxtUsuario.requestFocus();
                    TxtUsuario.selectAll();
                }
            }
        });
    }

    private boolean esCorreoPermitido(String correo) {
        correo = correo.toLowerCase();
        return correo.matches("^[A-Za-z0-9._%+-]+@(gmail|hotmail|outlook)\\.com$")
                || correo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(edu|edu\\.ec|edu\\.mx|edu\\.ar|edu\\.pe|edu\\.co|edu\\.es)$");
    }

    private void campoContraseña() {
        Color placeholderColor = new Color(107, 114, 128);

        TxtContraseña.setText("Contraseña");
        TxtContraseña.setForeground(placeholderColor);

        TxtContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TxtContraseña.getText().equals("Contraseña")) {
                    TxtContraseña.setText("");
                    TxtContraseña.setForeground(Color.BLACK);
                    contrasenaReal = ""; // Limpiar la contraseña real también
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TxtContraseña.getText().isEmpty()) {
                    TxtContraseña.setText("Contraseña");
                    TxtContraseña.setForeground(placeholderColor);
                    contrasenaReal = ""; // Limpiar la contraseña real también
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblQr;
    public javax.swing.JLabel LblSlogan;
    public javax.swing.JPanel Panel1;
    public javax.swing.JTextField TxtContraseña;
    public javax.swing.JTextField TxtUsuario;
    public javax.swing.JButton btnCodigo;
    public javax.swing.JButton btnInicar;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel panelFormularioLogin;
    // End of variables declaration//GEN-END:variables

}
