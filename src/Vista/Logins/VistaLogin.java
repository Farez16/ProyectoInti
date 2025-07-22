package Vista;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VistaLogin extends javax.swing.JPanel {

 // Color personalizado para el placeholder (RGB: 107, 114, 128)
    private final Color COLOR_PLACEHOLDER = new Color(107, 114, 128);
    
    public VistaLogin() {
        initComponents();
        configurarPlaceholders(); // Llamar al método que configura los placeholders
    }

    public JLabel getLblImagenfondoLogin() {
        return LblImagenfondoLogin;
    }

    public JLabel getLblLogoINTI() {
        return LblLogoINTI;
    }

    public JLabel getLblSlogan() {
        return LblSlogan;
    }

    public JPanel getPanelContenedorLogin() {
        return PanelContenedorLogin;
    }

    public JPanel getPanelDatosLogin() {
        return PanelDatosLogin;
    }

    public JTextField getTxtUsuario() {
        return TxtUsuario;
    }

    public JButton getBtnIniciarSesion() {
        return btnIniciarSesion;
    }

    public JButton getBtnRegresar() {
        return btnRegresar;
    }

    public JPasswordField getjPasswordLogin() {
        return jPasswordLogin;
    }

// Método para configurar los placeholders
    private void configurarPlaceholders() {
        // Configurar placeholder para el campo de usuario
        TxtUsuario.setText("Usuario");
        TxtUsuario.setForeground(COLOR_PLACEHOLDER); //Usamos el color personalizado

        TxtUsuario.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (TxtUsuario.getText().equals("Usuario")) {
                    TxtUsuario.setText("");
                    TxtUsuario.setForeground(Color.BLACK); //Texto color negro normal al escribir
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (TxtUsuario.getText().isEmpty()) {
                    TxtUsuario.setText("Usuario");
                    TxtUsuario.setForeground(COLOR_PLACEHOLDER); //Restauramos al color placeholder
                }
            }
        });

        // Configurar placeholder para el campo de contraseña
        jPasswordLogin.setEchoChar((char) 0); // Mostrar texto normal al inicio
        jPasswordLogin.setText("Contraseña");
        jPasswordLogin.setForeground(COLOR_PLACEHOLDER); //Usamos el color personalizado

        jPasswordLogin.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(jPasswordLogin.getPassword()).equals("Contraseña")) {
                    jPasswordLogin.setText("");
                    jPasswordLogin.setForeground(Color.BLACK); //Texto color negro normal al escribir
                    jPasswordLogin.setEchoChar('•'); // Cambiar a caracteres de contraseña
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (jPasswordLogin.getPassword().length == 0) {
                    jPasswordLogin.setEchoChar((char) 0); //Mostrar texto en claro
                    jPasswordLogin.setText("Contraseña");
                    jPasswordLogin.setForeground(COLOR_PLACEHOLDER); //Restauramos al color personalizado
                }
            }
        });
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelContenedorLogin = new javax.swing.JPanel();
        PanelDatosLogin = new javax.swing.JPanel();
        LblLogoINTI = new javax.swing.JLabel();
        LblSlogan = new javax.swing.JLabel();
        TxtUsuario = new javax.swing.JTextField();
        jPasswordLogin = new javax.swing.JPasswordField();
        btnIniciarSesion = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();
        LblImagenfondoLogin = new javax.swing.JLabel();

        PanelContenedorLogin.setBackground(new java.awt.Color(255, 255, 255));
        PanelContenedorLogin.setPreferredSize(new java.awt.Dimension(1370, 770));
        PanelContenedorLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelDatosLogin.setBackground(new java.awt.Color(255, 255, 255));

        LblLogoINTI.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/X1.png"))); // NOI18N

        LblSlogan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        LblSlogan.setText("Despierta la lengua ancestral");

        TxtUsuario.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        jPasswordLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N

        btnIniciarSesion.setBackground(new java.awt.Color(255, 122, 0));
        btnIniciarSesion.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnIniciarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnIniciarSesion.setText("Iniciar Sesion");

        btnRegresar.setBackground(new java.awt.Color(255, 122, 0));
        btnRegresar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("Regresar");

        javax.swing.GroupLayout PanelDatosLoginLayout = new javax.swing.GroupLayout(PanelDatosLogin);
        PanelDatosLogin.setLayout(PanelDatosLoginLayout);
        PanelDatosLoginLayout.setHorizontalGroup(
            PanelDatosLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosLoginLayout.createSequentialGroup()
                .addContainerGap(217, Short.MAX_VALUE)
                .addComponent(LblLogoINTI)
                .addGap(145, 145, 145))
            .addGroup(PanelDatosLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelDatosLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TxtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                    .addComponent(jPasswordLogin))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(PanelDatosLoginLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnIniciarSesion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelDatosLoginLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(LblSlogan)
                .addGap(107, 107, 107))
        );
        PanelDatosLoginLayout.setVerticalGroup(
            PanelDatosLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelDatosLoginLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(LblLogoINTI)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LblSlogan)
                .addGap(80, 80, 80)
                .addComponent(TxtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75)
                .addComponent(jPasswordLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addGroup(PanelDatosLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(157, Short.MAX_VALUE))
        );

        PanelContenedorLogin.add(PanelDatosLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 0, 540, 770));

        LblImagenfondoLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Whisk_421cbaaf9d (1).jpg"))); // NOI18N
        PanelContenedorLogin.add(LblImagenfondoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 770));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelContenedorLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelContenedorLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblImagenfondoLogin;
    public javax.swing.JLabel LblLogoINTI;
    public javax.swing.JLabel LblSlogan;
    public javax.swing.JPanel PanelContenedorLogin;
    public javax.swing.JPanel PanelDatosLogin;
    public javax.swing.JTextField TxtUsuario;
    public javax.swing.JButton btnIniciarSesion;
    public javax.swing.JButton btnRegresar;
    public javax.swing.JPasswordField jPasswordLogin;
    // End of variables declaration//GEN-END:variables
}
