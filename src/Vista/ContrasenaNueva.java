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
        camposIngresarCodigo();
        nuevaContrasena();
        confirmarContrasena();
        nombre();
        SwingUtilities.invokeLater(() -> {
            // Puedes usar otro componente que no moleste visualmente
            jLabel2.requestFocusInWindow();
        });
    }

    public ContrasenaNueva(String correoUsuario) {
        initComponents();
        this.correoUsuario = correoUsuario;
        initComponents();
        camposIngresarCodigo();
        nuevaContrasena();
        confirmarContrasena();
        nombre();
        SwingUtilities.invokeLater(() -> {
            // Puedes usar otro componente que no moleste visualmente
            jLabel2.requestFocusInWindow();
        });
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public JLabel getLblLogo() {
        return LblLogo;
    }

    public JLabel getLblNombreProyecto() {
        return LblNombreProyecto;
    }

    public JTextField getTextFieldConfirmarContraseña() {
        return TextFieldConfirmarContraseña;
    }

    public JTextField getTextFieldIngresarCodigoRecibido() {
        return TextFieldIngresarCodigoRecibido;
    }

    public JTextField getTextFieldIngresarNuevaContraseña() {
        return TextFieldIngresarNuevaContraseña;
    }

    public JButton getBtnGuardarContraseña() {
        return btnGuardarContraseña;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public JTextField getTxtNombre() {
        return TxtNombre;
    }

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
        TextFieldIngresarCodigoRecibido = new javax.swing.JTextField();
        LblNombreProyecto = new javax.swing.JLabel();
        LblLogo = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(500, 770));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("   Guardar Contraseña");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 180, 40));

        btnGuardarContraseña.setBackground(new java.awt.Color(171, 52, 2));
        btnGuardarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarContraseñaActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 180, 40));

        TxtNombre.setBackground(new java.awt.Color(255, 255, 255));
        TxtNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TxtNombre.setForeground(new java.awt.Color(153, 153, 153));
        TxtNombre.setText("Nombre de Usuario");
        TxtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TxtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 310, 175, 25));

        TextFieldConfirmarContraseña.setBackground(new java.awt.Color(255, 255, 255));
        TextFieldConfirmarContraseña.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextFieldConfirmarContraseña.setText("Confirmar Contraseña");
        TextFieldConfirmarContraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TextFieldConfirmarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 250, 175, 25));

        TextFieldIngresarNuevaContraseña.setBackground(new java.awt.Color(255, 255, 255));
        TextFieldIngresarNuevaContraseña.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextFieldIngresarNuevaContraseña.setText("Nueva Contraseña");
        TextFieldIngresarNuevaContraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TextFieldIngresarNuevaContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 190, 175, 25));

        TextFieldIngresarCodigoRecibido.setBackground(new java.awt.Color(255, 255, 255));
        TextFieldIngresarCodigoRecibido.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        TextFieldIngresarCodigoRecibido.setText("Codigo Recibido");
        TextFieldIngresarCodigoRecibido.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));
        jPanel2.add(TextFieldIngresarCodigoRecibido, new org.netbeans.lib.awtextra.AbsoluteConstraints(115, 130, 175, 25));

        LblNombreProyecto.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        LblNombreProyecto.setForeground(new java.awt.Color(0, 0, 0));
        LblNombreProyecto.setText("I N T I");
        jPanel2.add(LblNombreProyecto, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, -1));

        LblLogo.setForeground(new java.awt.Color(255, 255, 255));
        LblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo 90 x90.png"))); // NOI18N
        jPanel2.add(LblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 390, 430));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondo l,d.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(-8, 0, 520, 510));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void camposIngresarCodigo() {
        TextFieldIngresarCodigoRecibido.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarCodigoRecibido.getText().contains("Codigo Recibido")) {
                    TextFieldIngresarCodigoRecibido.setText("");
                    TextFieldIngresarCodigoRecibido.setForeground(Color.BLACK); // Cambiar color normal
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarCodigoRecibido.getText().isEmpty()) {
                    TextFieldIngresarCodigoRecibido.setText("Codigo Recibido");
                    TextFieldIngresarCodigoRecibido.setForeground(new Color(187, 187, 187)); // Volver al color del placeholder
                }
            }

        });
    }

    private void nuevaContrasena() {
        TextFieldIngresarNuevaContraseña.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarNuevaContraseña.getText().contains("Nueva Contraseña")) {
                    TextFieldIngresarNuevaContraseña.setText("");
                    TextFieldIngresarNuevaContraseña.setForeground(Color.BLACK); // Cambiar color normal
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (TextFieldIngresarNuevaContraseña.getText().isEmpty()) {
                    TextFieldIngresarNuevaContraseña.setText("Nueva Contraseña");
                    TextFieldIngresarNuevaContraseña.setForeground(new Color(187, 187, 187)); // Volver al color del placeholder
                }
            }
        });
    }

    private void confirmarContrasena() {
        TextFieldConfirmarContraseña.addFocusListener(
                new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt
            ) {
                if (TextFieldConfirmarContraseña.getText().contains("Confirmar Contraseña")) {
                    TextFieldConfirmarContraseña.setText("");
                    TextFieldConfirmarContraseña.setForeground(Color.BLACK); // Cambiar color normal
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt
            ) {
                if (TextFieldConfirmarContraseña.getText().isEmpty()) {
                    TextFieldConfirmarContraseña.setText("Confirmar Contraseña");
                    TextFieldConfirmarContraseña.setForeground(new Color(187, 187, 187)); // Volver al color del placeholder
                }
            }
        }
        );
    }

    private void nombre() {

        TxtNombre.addFocusListener(
                new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt
            ) {
                if (TxtNombre.getText().contains("Nombre de Usuario")) {
                    TxtNombre.setText("");
                    TxtNombre.setForeground(Color.BLACK); // Cambiar color normal
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt
            ) {
                if (TxtNombre.getText().isEmpty()) {
                    TxtNombre.setText("Nombre de Usuario");
                    TxtNombre.setForeground(new Color(187, 187, 187)); // Volver al color del placeholder
                }
            }
        }
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblLogo;
    public javax.swing.JLabel LblNombreProyecto;
    public javax.swing.JTextField TextFieldConfirmarContraseña;
    public javax.swing.JTextField TextFieldIngresarCodigoRecibido;
    public javax.swing.JTextField TextFieldIngresarNuevaContraseña;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JButton btnGuardarContraseña;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
