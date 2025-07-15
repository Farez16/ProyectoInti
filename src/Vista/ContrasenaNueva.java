package Vista;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ContrasenaNueva extends javax.swing.JPanel {

    private String correoUsuario;

    public ContrasenaNueva() {
        initComponents();
        camposIngresarCorreo();
        camposIngresarCodigo();
        nuevaContrasena();
        confirmarContrasena();
        nombre();
        bloquearCamposRegistro();
        SwingUtilities.invokeLater(() -> jLabel2.requestFocusInWindow());
    }

    public ContrasenaNueva(String correoUsuario) {
        initComponents();
        this.correoUsuario = correoUsuario;
        camposIngresarCorreo();
        camposIngresarCodigo();
        nuevaContrasena();
        confirmarContrasena();
        nombre();
        SwingUtilities.invokeLater(() -> {
            jLabel2.requestFocusInWindow();
        });
    }

    // --- GETTERS ---
public JButton getBtnEnviarCodigo() { return jButton1REnviarCodigo1; }
public JButton getBtnVerificarCodigo() { return jButton1VerificarCodigo; }
public JButton getBtnGuardarContraseña() { return btnGuardarContraseña; }
public JTextField getTextFieldIngresarCorreo() { return TextFieldIngresarCorreo; }
public JTextField getTextFieldIngresarCodigoRecibido() { return TextFieldIngresarCodigoRecibido1; }
public JTextField getTextFieldIngresarNuevaContraseña() { return TextFieldIngresarNuevaContraseña; }
public JTextField getTextFieldConfirmarContraseña() { return TextFieldConfirmarContraseña; }
public JTextField getTxtNombre() { return TxtNombre; }



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnGuardarContraseña = new javax.swing.JButton();
        TxtNombre = new javax.swing.JTextField();
        TextFieldConfirmarContraseña = new javax.swing.JTextField();
        TextFieldIngresarNuevaContraseña = new javax.swing.JTextField();
        TextFieldIngresarCorreo = new javax.swing.JTextField();
        LblNombreProyecto = new javax.swing.JLabel();
        LblLogo = new javax.swing.JLabel();
        TextFieldIngresarCodigoRecibido1 = new javax.swing.JTextField();
        jButton1VerificarCodigo = new javax.swing.JButton();
        jButton1REnviarCodigo1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 770));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Registrar Usuario");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 140, 40));

        btnGuardarContraseña.setBackground(new java.awt.Color(171, 52, 2));
        btnGuardarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarContraseñaActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 390, 180, 40));

        TxtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TxtNombre.setForeground(new java.awt.Color(153, 153, 153));
        TxtNombre.setText("Nombre de Usuario");
        TxtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 340, 175, 25));

        TextFieldConfirmarContraseña.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextFieldConfirmarContraseña.setText("Confirmar Contraseña");
        TextFieldConfirmarContraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TextFieldConfirmarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 280, 175, 25));

        TextFieldIngresarNuevaContraseña.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextFieldIngresarNuevaContraseña.setText("Nueva Contraseña");
        TextFieldIngresarNuevaContraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TextFieldIngresarNuevaContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 220, 175, 25));

        TextFieldIngresarCorreo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextFieldIngresarCorreo.setText("Ingrese Correo");
        TextFieldIngresarCorreo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TextFieldIngresarCorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, 175, 25));

        LblNombreProyecto.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        LblNombreProyecto.setText("I N T I");
        jPanel2.add(LblNombreProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, -1, -1));

        LblLogo.setForeground(new java.awt.Color(255, 255, 255));
        LblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo 90 x90.png"))); // NOI18N
        jPanel2.add(LblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, -1));

        TextFieldIngresarCodigoRecibido1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextFieldIngresarCodigoRecibido1.setText("Codigo Recibido");
        TextFieldIngresarCodigoRecibido1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TextFieldIngresarCodigoRecibido1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 175, 25));

        jButton1VerificarCodigo.setText("Verificar Codigo");
        jPanel2.add(jButton1VerificarCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 160, -1, -1));

        jButton1REnviarCodigo1.setText("Enviar Codigo");
        jPanel2.add(jButton1REnviarCodigo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 580, 460));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo l,d.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-8, 0, 660, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 507, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarContraseñaActionPerformed

    }//GEN-LAST:event_btnGuardarContraseñaActionPerformed

        private void camposIngresarCorreo() {
        TextFieldIngresarCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarCorreo.getText().equals("Correo")) {
                    TextFieldIngresarCorreo.setText("");
                    TextFieldIngresarCorreo.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarCorreo.getText().isEmpty()) {
                    TextFieldIngresarCorreo.setText("Correo");
                    TextFieldIngresarCorreo.setForeground(new Color(187, 187, 187));
                }
            }
        });
    }

    private void camposIngresarCodigo() {
        TextFieldIngresarCodigoRecibido1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarCodigoRecibido1.getText().equals("Codigo Recibido")) {
                    TextFieldIngresarCodigoRecibido1.setText("");
                    TextFieldIngresarCodigoRecibido1.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarCodigoRecibido1.getText().isEmpty()) {
                    TextFieldIngresarCodigoRecibido1.setText("Codigo Recibido");
                    TextFieldIngresarCodigoRecibido1.setForeground(new Color(187, 187, 187));
                }
            }
        });
    }

    private void nuevaContrasena() {
        TextFieldIngresarNuevaContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarNuevaContraseña.getText().equals("Nueva Contraseña")) {
                    TextFieldIngresarNuevaContraseña.setText("");
                    TextFieldIngresarNuevaContraseña.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarNuevaContraseña.getText().isEmpty()) {
                    TextFieldIngresarNuevaContraseña.setText("Nueva Contraseña");
                    TextFieldIngresarNuevaContraseña.setForeground(new Color(187, 187, 187));
                }
            }
        });
    }

    private void confirmarContrasena() {
        TextFieldConfirmarContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextFieldConfirmarContraseña.getText().equals("Confirmar Contraseña")) {
                    TextFieldConfirmarContraseña.setText("");
                    TextFieldConfirmarContraseña.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextFieldConfirmarContraseña.getText().isEmpty()) {
                    TextFieldConfirmarContraseña.setText("Confirmar Contraseña");
                    TextFieldConfirmarContraseña.setForeground(new Color(187, 187, 187));
                }
            }
        });
    }

    private void nombre() {
        TxtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TxtNombre.getText().equals("Nombre de Usuario")) {
                    TxtNombre.setText("");
                    TxtNombre.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TxtNombre.getText().isEmpty()) {
                    TxtNombre.setText("Nombre de Usuario");
                    TxtNombre.setForeground(new Color(187, 187, 187));
                }
            }
        });
    }

    // --- BLOQUEAR Y DESBLOQUEAR CAMPOS ---
    public void bloquearCamposRegistro() {
        TextFieldIngresarNuevaContraseña.setEnabled(false);
        TextFieldConfirmarContraseña.setEnabled(false);
        TxtNombre.setEnabled(false);
    }

    public void desbloquearCamposRegistro() {
        TextFieldIngresarNuevaContraseña.setEnabled(true);
        TextFieldConfirmarContraseña.setEnabled(true);
        TxtNombre.setEnabled(true);
    }



    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblLogo;
    public javax.swing.JLabel LblNombreProyecto;
    public javax.swing.JTextField TextFieldConfirmarContraseña;
    public javax.swing.JTextField TextFieldIngresarCodigoRecibido1;
    public javax.swing.JTextField TextFieldIngresarCorreo;
    public javax.swing.JTextField TextFieldIngresarNuevaContraseña;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JButton btnGuardarContraseña;
    public javax.swing.JButton jButton1REnviarCodigo1;
    public javax.swing.JButton jButton1VerificarCodigo;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
