package VistaJuegos;



import Vista.Estudiante.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Vista_JuegoMemorama extends javax.swing.JPanel {

    public Vista_JuegoMemorama() {
        initComponents();
    }
        public void mostrarMensaje(String mensaje) {
    JOptionPane.showMessageDialog(this, mensaje);
}

    public JLabel getLblRespuesta() {
        return LblTexto;
    }

    public JLabel getLblTexto() {
        return LblTexto;
    }

    public JLabel getjLabel1Memorama() {
        return jLabelImagen1;
    }

    public JLabel getjLabel2Seleccionalosanimales() {
        return jLabel2ImagenIgual1;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public void setLblTexto(JLabel LblTexto) {
        this.LblTexto = LblTexto;
    }

    public void setjLabel1Memorama(JLabel jLabel1Memorama) {
        this.jLabelImagen1 = jLabel1Memorama;
    }

    public void setjLabel2Seleccionalosanimales(JLabel jLabel2Seleccionalosanimales) {
        this.jLabel2ImagenIgual1 = jLabel2Seleccionalosanimales;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

    public JButton getjButton1ComprobarRespuestas() {
        return jButton1ComprobarRespuestas;
    }

    public JButton getjButton1Regresaralavistaselecciondejuegos() {
        return jButton1Regresaralavistaselecciondejuegos;
    }

    public JLabel getjLabel2ImagenIgual1() {
        return jLabel2ImagenIgual1;
    }

    public JLabel getjLabel2ImagenIgual2() {
        return jLabel2ImagenIgual2;
    }

    public JLabel getjLabel2ImagenIgual3() {
        return jLabel2ImagenIgual3;
    }

    public JLabel getjLabel2ImagenIgual4() {
        return jLabel2ImagenIgual4;
    }

    public JLabel getjLabel2ImagenIgual5() {
        return jLabel2ImagenIgual5;
    }

    public JLabel getjLabel2ImagenIgual6() {
        return jLabel2ImagenIgual6;
    }

    public JLabel getjLabelImagen1() {
        return jLabelImagen1;
    }

    public JLabel getjLabelImagen2() {
        return jLabelImagen2;
    }

    public JLabel getjLabelImagen3() {
        return jLabelImagen3;
    }

    public JLabel getjLabelImagen4() {
        return jLabelImagen4;
    }

    public JLabel getjLabelImagen5() {
        return jLabelImagen5;
    }

    public JLabel getjLabelImagen6() {
        return jLabelImagen6;
    }

    public void setjButton1ComprobarRespuestas(JButton jButton1ComprobarRespuestas) {
        this.jButton1ComprobarRespuestas = jButton1ComprobarRespuestas;
    }

    public void setjButton1Regresaralavistaselecciondejuegos(JButton jButton1Regresaralavistaselecciondejuegos) {
        this.jButton1Regresaralavistaselecciondejuegos = jButton1Regresaralavistaselecciondejuegos;
    }

    public void setjLabel2ImagenIgual1(JLabel jLabel2ImagenIgual1) {
        this.jLabel2ImagenIgual1 = jLabel2ImagenIgual1;
    }

    public void setjLabel2ImagenIgual2(JLabel jLabel2ImagenIgual2) {
        this.jLabel2ImagenIgual2 = jLabel2ImagenIgual2;
    }

    public void setjLabel2ImagenIgual3(JLabel jLabel2ImagenIgual3) {
        this.jLabel2ImagenIgual3 = jLabel2ImagenIgual3;
    }

    public void setjLabel2ImagenIgual4(JLabel jLabel2ImagenIgual4) {
        this.jLabel2ImagenIgual4 = jLabel2ImagenIgual4;
    }

    public void setjLabel2ImagenIgual5(JLabel jLabel2ImagenIgual5) {
        this.jLabel2ImagenIgual5 = jLabel2ImagenIgual5;
    }

    public void setjLabel2ImagenIgual6(JLabel jLabel2ImagenIgual6) {
        this.jLabel2ImagenIgual6 = jLabel2ImagenIgual6;
    }

    public void setjLabelImagen1(JLabel jLabelImagen1) {
        this.jLabelImagen1 = jLabelImagen1;
    }

    public void setjLabelImagen2(JLabel jLabelImagen2) {
        this.jLabelImagen2 = jLabelImagen2;
    }

    public void setjLabelImagen3(JLabel jLabelImagen3) {
        this.jLabelImagen3 = jLabelImagen3;
    }

    public void setjLabelImagen4(JLabel jLabelImagen4) {
        this.jLabelImagen4 = jLabelImagen4;
    }

    public void setjLabelImagen5(JLabel jLabelImagen5) {
        this.jLabelImagen5 = jLabelImagen5;
    }

    public void setjLabelImagen6(JLabel jLabelImagen6) {
        this.jLabelImagen6 = jLabelImagen6;
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LblTexto = new javax.swing.JLabel();
        jLabelImagen1 = new javax.swing.JLabel();
        jLabel2ImagenIgual1 = new javax.swing.JLabel();
        jLabel2ImagenIgual2 = new javax.swing.JLabel();
        jLabel2ImagenIgual3 = new javax.swing.JLabel();
        jLabel2ImagenIgual4 = new javax.swing.JLabel();
        jLabelImagen2 = new javax.swing.JLabel();
        jLabelImagen3 = new javax.swing.JLabel();
        jLabelImagen4 = new javax.swing.JLabel();
        jLabel2ImagenIgual5 = new javax.swing.JLabel();
        jLabel2ImagenIgual6 = new javax.swing.JLabel();
        jLabelImagen5 = new javax.swing.JLabel();
        jLabelImagen6 = new javax.swing.JLabel();
        jButton1ComprobarRespuestas = new javax.swing.JButton();
        jButton1Regresaralavistaselecciondejuegos = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1190, 660));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1190, 660));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblTexto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        LblTexto.setText("SELECCIONE UN JUEGO");
        jPanel1.add(LblTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, -1, -1));

        jLabelImagen1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelImagen1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabelImagen1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 300, 140, 160));

        jLabel2ImagenIgual1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2ImagenIgual1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel2ImagenIgual1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 140, 160));

        jLabel2ImagenIgual2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2ImagenIgual2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel2ImagenIgual2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 100, 140, 160));

        jLabel2ImagenIgual3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2ImagenIgual3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel2ImagenIgual3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 140, 160));

        jLabel2ImagenIgual4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2ImagenIgual4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel2ImagenIgual4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 140, 160));

        jLabelImagen2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelImagen2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabelImagen2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, 140, 160));

        jLabelImagen3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelImagen3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabelImagen3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 300, 140, 160));

        jLabelImagen4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelImagen4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabelImagen4, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 300, 140, 160));

        jLabel2ImagenIgual5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2ImagenIgual5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel2ImagenIgual5, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 300, 140, 160));

        jLabel2ImagenIgual6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabel2ImagenIgual6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel2ImagenIgual6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 140, 160));

        jLabelImagen5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelImagen5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabelImagen5, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 140, 160));

        jLabelImagen6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLabelImagen6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabelImagen6, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 100, 140, 160));

        jButton1ComprobarRespuestas.setText("COMPROBAR");
        jPanel1.add(jButton1ComprobarRespuestas, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 520, -1, -1));

        jButton1Regresaralavistaselecciondejuegos.setText("REGRESAR");
        jPanel1.add(jButton1Regresaralavistaselecciondejuegos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 610, -1, -1));

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblTexto;
    public javax.swing.JButton jButton1ComprobarRespuestas;
    public javax.swing.JButton jButton1Regresaralavistaselecciondejuegos;
    public javax.swing.JLabel jLabel2ImagenIgual1;
    public javax.swing.JLabel jLabel2ImagenIgual2;
    public javax.swing.JLabel jLabel2ImagenIgual3;
    public javax.swing.JLabel jLabel2ImagenIgual4;
    public javax.swing.JLabel jLabel2ImagenIgual5;
    public javax.swing.JLabel jLabel2ImagenIgual6;
    public javax.swing.JLabel jLabelImagen1;
    public javax.swing.JLabel jLabelImagen2;
    public javax.swing.JLabel jLabelImagen3;
    public javax.swing.JLabel jLabelImagen4;
    public javax.swing.JLabel jLabelImagen5;
    public javax.swing.JLabel jLabelImagen6;
    public javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
