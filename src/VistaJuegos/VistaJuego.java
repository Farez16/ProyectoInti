package VistaJuegos;



import Vista.Estudiante.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class VistaJuego extends javax.swing.JPanel {

    public VistaJuego() {
        initComponents();
    }
        public void mostrarMensaje(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje);
}

    public JLabel getLblRespuesta() {
        return LblRespuesta;
    }

    public JLabel getLblimagen1() {
        return Lblimagen1;
    }

    public JLabel getLblimagen2() {
        return Lblimagen2;
    }

    public JLabel getLblimagen3() {
        return Lblimagen3;
    }

    public JLabel getLblimagen4() {
        return Lblimagen4;
    }

    public JLabel getLblimagen5() {
        return Lblimagen5;
    }

    public JLabel getLblimagen6() {
        return Lblimagen6;
    }

    public JLabel getLblimagen7() {
        return Lblimagen7;
    }

    public JLabel getLblimagen8() {
        return Lblimagen8;
    }

    public JButton getBtn1() {
        return btn1;
    }

    public JButton getBtn2() {
        return btn2;
    }

    public JButton getBtn3() {
        return btn3;
    }

    public JButton getBtn4() {
        return btn4;
    }

    public JButton getBtn5() {
        return btn5;
    }

    public JButton getBtn6() {
        return btn6;
    }

    public JButton getBtn7() {
        return btn7;
    }

    public JButton getBtn8() {
        return btn8;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public JButton getBtnVerificar() {
        return btnVerificar;
    }
    
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn1 = new javax.swing.JButton();
        LblRespuesta = new javax.swing.JLabel();
        Lblimagen2 = new javax.swing.JLabel();
        Lblimagen1 = new javax.swing.JLabel();
        Lblimagen3 = new javax.swing.JLabel();
        Lblimagen4 = new javax.swing.JLabel();
        Lblimagen5 = new javax.swing.JLabel();
        Lblimagen6 = new javax.swing.JLabel();
        Lblimagen7 = new javax.swing.JLabel();
        Lblimagen8 = new javax.swing.JLabel();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btnVerificar = new javax.swing.JButton();
        jButton1Regresaralavistaselecciondejuegos = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1190, 660));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1190, 660));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });
        jPanel1.add(btn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 520, 90, 30));

        LblRespuesta.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblRespuesta.setText("Respuestas:");
        jPanel1.add(LblRespuesta, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, -1, -1));
        jPanel1.add(Lblimagen2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, -1));
        jPanel1.add(Lblimagen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));
        jPanel1.add(Lblimagen3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 20, -1, -1));
        jPanel1.add(Lblimagen4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        jPanel1.add(Lblimagen5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));
        jPanel1.add(Lblimagen6, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 260, -1, -1));
        jPanel1.add(Lblimagen7, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 260, -1, -1));
        jPanel1.add(Lblimagen8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
        jPanel1.add(btn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 590, 90, 30));
        jPanel1.add(btn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 520, 90, 30));
        jPanel1.add(btn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 590, 90, 30));
        jPanel1.add(btn5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 520, 90, 30));
        jPanel1.add(btn6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 580, 90, 30));
        jPanel1.add(btn7, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 520, 90, 30));
        jPanel1.add(btn8, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 580, 90, 30));

        btnVerificar.setText("Verificar");
        jPanel1.add(btnVerificar, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 530, -1, -1));

        jButton1Regresaralavistaselecciondejuegos.setText("REGRESAR");
        jPanel1.add(jButton1Regresaralavistaselecciondejuegos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 640, 140, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblRespuesta;
    public javax.swing.JLabel Lblimagen1;
    public javax.swing.JLabel Lblimagen2;
    public javax.swing.JLabel Lblimagen3;
    public javax.swing.JLabel Lblimagen4;
    public javax.swing.JLabel Lblimagen5;
    public javax.swing.JLabel Lblimagen6;
    public javax.swing.JLabel Lblimagen7;
    public javax.swing.JLabel Lblimagen8;
    public javax.swing.JButton btn1;
    public javax.swing.JButton btn2;
    public javax.swing.JButton btn3;
    public javax.swing.JButton btn4;
    public javax.swing.JButton btn5;
    public javax.swing.JButton btn6;
    public javax.swing.JButton btn7;
    public javax.swing.JButton btn8;
    public javax.swing.JButton btnVerificar;
    public javax.swing.JButton jButton1Regresaralavistaselecciondejuegos;
    public javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
