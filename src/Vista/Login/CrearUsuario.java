package Vista.Login;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CrearUsuario extends javax.swing.JPanel {

    private String correoUsuario;

    public CrearUsuario() {
        initComponents();
        camposIngresarCorreo();
        camposIngresarCodigo();
        nuevaContrasena();
        confirmarContrasena();
        nombre();
        bloquearCamposRegistro();
        SwingUtilities.invokeLater(() -> jLabel2.requestFocusInWindow());
            // Eliminar el tamaño fijo del panel principal
    jPanel1.setPreferredSize(null);
    
    // Configurar layout para centrar el contenido
    jPanel1.setLayout(new java.awt.BorderLayout());
    jPanel2.setPreferredSize(new java.awt.Dimension(760, 460));
    jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);
    }


    public CrearUsuario(String correoUsuario) {
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
        LblLogo = new javax.swing.JLabel();
        TextFieldIngresarCodigoRecibido1 = new javax.swing.JTextField();
        jButton1VerificarCodigo = new javax.swing.JButton();
        jButton1REnviarCodigo1 = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1330, 770));

        jPanel1.setBackground(new java.awt.Color(255, 204, 51));
        jPanel1.setPreferredSize(new java.awt.Dimension(1330, 770));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("REGISTRAR ");

        btnGuardarContraseña.setBackground(new java.awt.Color(255, 122, 0));
        btnGuardarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarContraseñaActionPerformed(evt);
            }
        });

        TxtNombre.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        TxtNombre.setText("    Ariel Farez");
        TxtNombre.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        TextFieldConfirmarContraseña.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        TextFieldConfirmarContraseña.setText("Confirmar Contraseña");
        TextFieldConfirmarContraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        TextFieldIngresarNuevaContraseña.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        TextFieldIngresarNuevaContraseña.setText("Nueva Contraseña");
        TextFieldIngresarNuevaContraseña.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        TextFieldIngresarCorreo.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        TextFieldIngresarCorreo.setText("Ingrese Correo");
        TextFieldIngresarCorreo.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        LblLogo.setForeground(new java.awt.Color(255, 255, 255));
        LblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo60X60.png"))); // NOI18N

        TextFieldIngresarCodigoRecibido1.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        TextFieldIngresarCodigoRecibido1.setText("Codigo Recibido");
        TextFieldIngresarCodigoRecibido1.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(102, 102, 102)));

        jButton1VerificarCodigo.setBackground(new java.awt.Color(255, 122, 0));
        jButton1VerificarCodigo.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        jButton1VerificarCodigo.setForeground(new java.awt.Color(255, 255, 255));
        jButton1VerificarCodigo.setText("Verificar Codigo");

        jButton1REnviarCodigo1.setBackground(new java.awt.Color(255, 122, 0));
        jButton1REnviarCodigo1.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        jButton1REnviarCodigo1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1REnviarCodigo1.setText("Enviar Codigo");

        btnRegresar.setBackground(new java.awt.Color(255, 122, 0));
        btnRegresar.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("REGRESAR");

        jLabel3.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel3.setText("Correo:");

        jLabel4.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel4.setText("Codigo OTP:");

        jLabel5.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel5.setText("Contraseña:");

        jLabel6.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel6.setText("Contraseña:");

        jLabel7.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel7.setText("Nombre:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(270, 270, 270)
                .addComponent(LblLogo))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel3)
                .addGap(64, 64, 64)
                .addComponent(TextFieldIngresarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jButton1REnviarCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel4)
                .addGap(22, 22, 22)
                .addComponent(TextFieldIngresarCodigoRecibido1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(jButton1VerificarCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel5)
                .addGap(27, 27, 27)
                .addComponent(TextFieldIngresarNuevaContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel6)
                .addGap(27, 27, 27)
                .addComponent(TextFieldConfirmarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jLabel7)
                .addGap(43, 43, 43)
                .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel2)))
                .addGap(150, 150, 150)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(LblLogo)
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel3))
                    .addComponent(TextFieldIngresarCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1REnviarCodigo1))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(TextFieldIngresarCodigoRecibido1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1VerificarCodigo))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel5))
                    .addComponent(TextFieldIngresarNuevaContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(TextFieldConfirmarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(200, 200, 200)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarContraseñaActionPerformed

    }//GEN-LAST:event_btnGuardarContraseñaActionPerformed

         private void camposIngresarCorreo() {
    TextFieldIngresarCorreo.addFocusListener(new java.awt.event.FocusAdapter() {
        @Override
        public void focusGained(java.awt.event.FocusEvent evt) {
            TextFieldIngresarCorreo.setText("");
            TextFieldIngresarCorreo.setForeground(Color.BLACK);
        }
        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            String correo = TextFieldIngresarCorreo.getText().trim();
            if (correo.isEmpty()) {
                TextFieldIngresarCorreo.setText("Correo");
                TextFieldIngresarCorreo.setForeground(new Color(187, 187, 187));
            } else if (!esCorreoPermitido(correo)) {
                JOptionPane.showMessageDialog(CrearUsuario.this, "Ingrese un correo válido de: Gmail, Hotmail, Outlook o institucional.", "Correo inválido", JOptionPane.ERROR_MESSAGE);
                TextFieldIngresarCorreo.requestFocus();
                TextFieldIngresarCorreo.selectAll();
            }
        }
    });
}
private boolean esCorreoPermitido(String correo) {
    correo = correo.toLowerCase();
    // Dominios permitidos
    return correo.matches("^[A-Za-z0-9._%+-]+@(gmail|hotmail|outlook)\\.com$")
        || correo.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(edu|edu\\.ec|edu\\.mx|edu\\.ar|edu\\.pe|edu\\.co|edu\\.es)$");
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
    public javax.swing.JTextField TextFieldConfirmarContraseña;
    public javax.swing.JTextField TextFieldIngresarCodigoRecibido1;
    public javax.swing.JTextField TextFieldIngresarCorreo;
    public javax.swing.JTextField TextFieldIngresarNuevaContraseña;
    public javax.swing.JTextField TxtNombre;
    public javax.swing.JButton btnGuardarContraseña;
    public javax.swing.JButton btnRegresar;
    public javax.swing.JButton jButton1REnviarCodigo1;
    public javax.swing.JButton jButton1VerificarCodigo;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
