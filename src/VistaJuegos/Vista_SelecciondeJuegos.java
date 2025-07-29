package VistaJuegos;



import Vista.Estudiante.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Vista_SelecciondeJuegos extends javax.swing.JPanel {

    public Vista_SelecciondeJuegos() {
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

    public JButton getjButton1Memorama() {
        return jButton1Memorama;
    }

    public JButton getjButton2Animales() {
        return jButton2Animales;
    }

    public JLabel getjLabel1Memorama() {
        return jLabel1Memorama;
    }

    public JLabel getjLabel2Seleccionalosanimales() {
        return jLabel2Seleccionalosanimales;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public void setLblTexto(JLabel LblTexto) {
        this.LblTexto = LblTexto;
    }

    public void setjButton1Memorama(JButton jButton1Memorama) {
        this.jButton1Memorama = jButton1Memorama;
    }

    public void setjButton2Animales(JButton jButton2Animales) {
        this.jButton2Animales = jButton2Animales;
    }

    public void setjLabel1Memorama(JLabel jLabel1Memorama) {
        this.jLabel1Memorama = jLabel1Memorama;
    }

    public void setjLabel2Seleccionalosanimales(JLabel jLabel2Seleccionalosanimales) {
        this.jLabel2Seleccionalosanimales = jLabel2Seleccionalosanimales;
    }

    public void setjPanel1(JPanel jPanel1) {
        this.jPanel1 = jPanel1;
    }

  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LblTexto = new javax.swing.JLabel();
        jLabel1Memorama = new javax.swing.JLabel();
        jButton1Memorama = new javax.swing.JButton();
        jLabel2Seleccionalosanimales = new javax.swing.JLabel();
        jButton2Animales = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1190, 660));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1190, 660));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblTexto.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        LblTexto.setForeground(new java.awt.Color(255, 255, 255));
        LblTexto.setText("SELECCIONE UN JUEGO");
        jPanel1.add(LblTexto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 20, -1, -1));

        jLabel1Memorama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ImagenesUnidad2/Memorama.png"))); // NOI18N
        jLabel1Memorama.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1Memorama.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel1Memorama, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 90, 260, 310));

        jButton1Memorama.setBackground(new java.awt.Color(255, 122, 0));
        jButton1Memorama.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        jButton1Memorama.setForeground(new java.awt.Color(255, 255, 255));
        jButton1Memorama.setText("MEMORAMA");
        jPanel1.add(jButton1Memorama, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 450, 180, 40));

        jLabel2Seleccionalosanimales.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ImagenesUnidad2/Animales.png"))); // NOI18N
        jLabel2Seleccionalosanimales.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel2Seleccionalosanimales.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(jLabel2Seleccionalosanimales, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 260, 310));

        jButton2Animales.setBackground(new java.awt.Color(255, 122, 0));
        jButton2Animales.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        jButton2Animales.setForeground(new java.awt.Color(255, 255, 255));
        jButton2Animales.setText("ANIMALES");
        jPanel1.add(jButton2Animales, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 450, 180, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoooo12.jpg"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
    public javax.swing.JButton jButton1Memorama;
    public javax.swing.JButton jButton2Animales;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel1Memorama;
    public javax.swing.JLabel jLabel2Seleccionalosanimales;
    public javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
