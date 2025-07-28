package Vista.Estudiante;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Vista_PanelUnidades extends javax.swing.JPanel {

    public Vista_PanelUnidades() {
        initComponents();
    }

    public JLabel getLblOracionEspañol() {
        return LblOracionEspañol;
    }

    public JLabel getLblOracionKichwa() {
        return LblOracionKichwa;
    }

    public JLabel getLblPalabraKichwa() {
        return LblPalabraKichwa;
    }

    public JLabel getLblTituloPalabraDia() {
        return LblTituloPalabraDia;
    }

    public JLabel getLblTituloUnidades() {
        return LblTituloUnidades;
    }

    public JLabel getLblU1Foto() {
        return LblU1Foto;
    }

    public JLabel getLblU1Titulo() {
        return LblU1Titulo;
    }

    public JLabel getLblU2Foto() {
        return LblU2Foto;
    }

    public JLabel getLblU2Titulo() {
        return LblU2Titulo;
    }

    public JLabel getLblU3Foto() {
        return LblU3Foto;
    }

    public JLabel getLblU3Titulo() {
        return LblU3Titulo;
    }

    public JLabel getLblU4Foto() {
        return LblU4Foto;
    }

    public JLabel getLblU4Titulo() {
        return LblU4Titulo;
    }

    public JLabel getLbliconoPalabra() {
        return LbliconoPalabra;
    }

    public JPanel getPanelPalabraDiaria() {
        return PanelPalabraDiaria;
    }

    public JPanel getPanelUnidad1() {
        return PanelUnidad1;
    }

    public JPanel getPanelUnidad2() {
        return PanelUnidad2;
    }

    public JPanel getPanelUnidad3() {
        return PanelUnidad3;
    }

    public JPanel getPanelUnidad4() {
        return PanelUnidad4;
    }

    public JPanel getPanelVistas() {
        return PanelVistas;
    }

    public JProgressBar getProgresoU1() {
        return ProgresoU1;
    }

    public JProgressBar getProgresoU2() {
        return ProgresoU2;
    }

    public JProgressBar getProgresoU3() {
        return ProgresoU3;
    }

    public JProgressBar getProgresoU4() {
        return ProgresoU4;
    }

    public JProgressBar getProgresoTotal() {
        return ProgresoTotal;
    }

    public JButton getBtnUnidad1() {
        return btnUnidad1;
    }

    public JButton getBtnUnidad2() {
        return btnUnidad2;
    }

    public JButton getBtnUnidad3() {
        return btnUnidad3;
    }

    public JButton getBtnUnidad4() {
        return btnUnidad4;
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelVistas = new javax.swing.JPanel();
        LblTituloUnidades = new javax.swing.JLabel();
        PanelUnidad1 = new javax.swing.JPanel();
        LblU1Titulo = new javax.swing.JLabel();
        ProgresoU1 = new javax.swing.JProgressBar();
        btnUnidad1 = new javax.swing.JButton();
        LblU1Foto = new javax.swing.JLabel();
        PanelUnidad2 = new javax.swing.JPanel();
        LblU2Foto = new javax.swing.JLabel();
        LblU2Titulo = new javax.swing.JLabel();
        ProgresoU2 = new javax.swing.JProgressBar();
        btnUnidad2 = new javax.swing.JButton();
        PanelUnidad3 = new javax.swing.JPanel();
        LblU3Foto = new javax.swing.JLabel();
        LblU3Titulo = new javax.swing.JLabel();
        ProgresoU3 = new javax.swing.JProgressBar();
        btnUnidad3 = new javax.swing.JButton();
        PanelUnidad4 = new javax.swing.JPanel();
        LblU4Foto = new javax.swing.JLabel();
        LblU4Titulo = new javax.swing.JLabel();
        ProgresoU4 = new javax.swing.JProgressBar();
        btnUnidad4 = new javax.swing.JButton();
        PanelPalabraDiaria = new javax.swing.JPanel();
        LblTituloPalabraDia = new javax.swing.JLabel();
        LblPalabraKichwa = new javax.swing.JLabel();
        LblOracionKichwa = new javax.swing.JLabel();
        LblOracionEspañol = new javax.swing.JLabel();
        LbliconoPalabra = new javax.swing.JLabel();
        ProgresoTotal = new javax.swing.JProgressBar();

        PanelVistas.setBackground(new java.awt.Color(180, 210, 255));
        PanelVistas.setPreferredSize(new java.awt.Dimension(1190, 660));
        PanelVistas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblTituloUnidades.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        LblTituloUnidades.setText("Mis Unidades");
        PanelVistas.add(LblTituloUnidades, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        PanelUnidad1.setBackground(new java.awt.Color(253, 253, 253));
        PanelUnidad1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.white, null, null));
        PanelUnidad1.setPreferredSize(new java.awt.Dimension(260, 310));
        PanelUnidad1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblU1Titulo.setForeground(new java.awt.Color(55, 65, 81));
        LblU1Titulo.setText("<html>\n  <div style='font-family: Montserrat; font-size: 15pt; font-weight: bold;'>\n    Unidad 1: Expresión Oral y Escrita\n  </div>\n  <div style='font-family: Montserrat; font-size: 13pt; font-weight: normal;'>\n    Aprende a saludar, pronunciar correctamente y usar pronombres en kichwa.\n  </div>\n</html>\n\n");
        PanelUnidad1.add(LblU1Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, 230, 90));

        ProgresoU1.setBackground(new java.awt.Color(52, 211, 153));
        ProgresoU1.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        ProgresoU1.setToolTipText("Progreso de la unidad");
        ProgresoU1.setName(""); // NOI18N
        ProgresoU1.setStringPainted(true);
        PanelUnidad1.add(ProgresoU1, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 225, 209, 18));

        btnUnidad1.setBackground(new java.awt.Color(255, 102, 0));
        btnUnidad1.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        btnUnidad1.setForeground(new java.awt.Color(255, 255, 255));
        btnUnidad1.setText("Empezar");
        btnUnidad1.setBorderPainted(false);
        PanelUnidad1.add(btnUnidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 265, -1, -1));

        LblU1Foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Unida1.jpg"))); // NOI18N
        PanelUnidad1.add(LblU1Foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, -1));

        PanelVistas.add(PanelUnidad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        PanelUnidad2.setBackground(new java.awt.Color(253, 253, 253));
        PanelUnidad2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelUnidad2.setPreferredSize(new java.awt.Dimension(260, 310));
        PanelUnidad2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblU2Foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Unida2.jpg"))); // NOI18N
        PanelUnidad2.add(LblU2Foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        LblU2Titulo.setForeground(new java.awt.Color(55, 65, 81));
        LblU2Titulo.setText("<html>\n  <div style='font-family: Montserrat; font-size: 15pt; font-weight: bold;'>\n    Unidad 2: Números y Animales\n  </div>\n  <div style='font-family: Montserrat; font-size: 13pt; font-weight: normal;'>\n    Conoce los números, operaciones básicas y vocabulario de animales.\n  </div>\n</html>\n");
        PanelUnidad2.add(LblU2Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, 230, 80));

        ProgresoU2.setBackground(new java.awt.Color(52, 211, 153));
        ProgresoU2.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        ProgresoU2.setStringPainted(true);
        PanelUnidad2.add(ProgresoU2, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 225, 209, 18));

        btnUnidad2.setBackground(new java.awt.Color(255, 102, 0));
        btnUnidad2.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        btnUnidad2.setForeground(new java.awt.Color(255, 255, 255));
        btnUnidad2.setText("Empezar");
        btnUnidad2.setBorderPainted(false);
        PanelUnidad2.add(btnUnidad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 265, -1, -1));

        PanelVistas.add(PanelUnidad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, -1, -1));

        PanelUnidad3.setBackground(new java.awt.Color(253, 253, 253));
        PanelUnidad3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelUnidad3.setPreferredSize(new java.awt.Dimension(260, 310));
        PanelUnidad3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblU3Foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Unida3.jpg"))); // NOI18N
        PanelUnidad3.add(LblU3Foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        LblU3Titulo.setForeground(new java.awt.Color(55, 65, 81));
        LblU3Titulo.setText("<html>\n  <div style='font-family: Montserrat; font-size: 15pt; font-weight: bold;'>\n    Unidad 3: La familia e identidad cultural\n  </div>\n  <div style='font-family: Montserrat; font-size: 13pt; font-weight: normal;'>\n    Explora la estructura familiar y vestimenta tradicional kichwa.\n  </div>\n</html>\n");
        PanelUnidad3.add(LblU3Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, 230, 80));

        ProgresoU3.setBackground(new java.awt.Color(52, 211, 153));
        ProgresoU3.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        ProgresoU3.setStringPainted(true);
        PanelUnidad3.add(ProgresoU3, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 225, 209, 18));

        btnUnidad3.setBackground(new java.awt.Color(255, 102, 0));
        btnUnidad3.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        btnUnidad3.setForeground(new java.awt.Color(255, 255, 255));
        btnUnidad3.setText("Empezar");
        btnUnidad3.setBorderPainted(false);
        PanelUnidad3.add(btnUnidad3, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 265, -1, -1));

        PanelVistas.add(PanelUnidad3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, -1, -1));

        PanelUnidad4.setBackground(new java.awt.Color(253, 253, 253));
        PanelUnidad4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelUnidad4.setPreferredSize(new java.awt.Dimension(260, 310));
        PanelUnidad4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblU4Foto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Unida4.jpg"))); // NOI18N
        PanelUnidad4.add(LblU4Foto, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        LblU4Titulo.setForeground(new java.awt.Color(55, 65, 81));
        LblU4Titulo.setText("<html>\n  <div style='font-family: Montserrat; font-size: 15pt; font-weight: bold;'>\n    Unidad 4: Colores y cuerpo Humano\n  </div>\n  <div style='font-family: Montserrat; font-size: 13pt; font-weight: normal;'>\n    Identifica colores, partes del cuerpo y sus funciones en kichwa.\n  </div>\n</html>\n");
        PanelUnidad4.add(LblU4Titulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, 230, 80));

        ProgresoU4.setBackground(new java.awt.Color(52, 211, 153));
        ProgresoU4.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        ProgresoU4.setStringPainted(true);
        PanelUnidad4.add(ProgresoU4, new org.netbeans.lib.awtextra.AbsoluteConstraints(25, 225, 209, 18));

        btnUnidad4.setBackground(new java.awt.Color(255, 102, 0));
        btnUnidad4.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        btnUnidad4.setForeground(new java.awt.Color(255, 255, 255));
        btnUnidad4.setText("Empezar");
        btnUnidad4.setBorderPainted(false);
        PanelUnidad4.add(btnUnidad4, new org.netbeans.lib.awtextra.AbsoluteConstraints(82, 265, -1, -1));

        PanelVistas.add(PanelUnidad4, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 80, -1, -1));

        PanelPalabraDiaria.setBackground(new java.awt.Color(253, 253, 253));
        PanelPalabraDiaria.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelPalabraDiaria.setPreferredSize(new java.awt.Dimension(531, 182));

        LblTituloPalabraDia.setFont(new java.awt.Font("Montserrat", 1, 18)); // NOI18N
        LblTituloPalabraDia.setText("Palabra del Día");

        LblPalabraKichwa.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        LblPalabraKichwa.setForeground(new java.awt.Color(218, 111, 43));
        LblPalabraKichwa.setText("Tukuy");

        LblOracionKichwa.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        LblOracionKichwa.setText("Tukuy pachapi kuyayki");

        LblOracionEspañol.setFont(new java.awt.Font("Montserrat", 0, 14)); // NOI18N
        LblOracionEspañol.setText("Te amo todo el tiempo");

        LbliconoPalabra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/IconEstu/icons8-libros-64r.png"))); // NOI18N

        javax.swing.GroupLayout PanelPalabraDiariaLayout = new javax.swing.GroupLayout(PanelPalabraDiaria);
        PanelPalabraDiaria.setLayout(PanelPalabraDiariaLayout);
        PanelPalabraDiariaLayout.setHorizontalGroup(
            PanelPalabraDiariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPalabraDiariaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(PanelPalabraDiariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPalabraDiariaLayout.createSequentialGroup()
                        .addComponent(LblPalabraKichwa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LbliconoPalabra)
                        .addGap(63, 63, 63))
                    .addGroup(PanelPalabraDiariaLayout.createSequentialGroup()
                        .addGroup(PanelPalabraDiariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LblOracionKichwa)
                            .addComponent(LblOracionEspañol)
                            .addComponent(LblTituloPalabraDia))
                        .addGap(109, 350, Short.MAX_VALUE))))
        );
        PanelPalabraDiariaLayout.setVerticalGroup(
            PanelPalabraDiariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPalabraDiariaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(PanelPalabraDiariaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(LbliconoPalabra)
                    .addGroup(PanelPalabraDiariaLayout.createSequentialGroup()
                        .addComponent(LblTituloPalabraDia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(LblPalabraKichwa)
                        .addGap(12, 12, 12)
                        .addComponent(LblOracionKichwa)
                        .addGap(18, 18, 18)
                        .addComponent(LblOracionEspañol)))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        PanelVistas.add(PanelPalabraDiaria, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 440, -1, -1));

        ProgresoTotal.setBackground(new java.awt.Color(52, 211, 153));
        ProgresoTotal.setFont(new java.awt.Font("Montserrat", 1, 12)); // NOI18N
        ProgresoTotal.setToolTipText("Progreso General");
        ProgresoTotal.setStringPainted(true);
        PanelVistas.add(ProgresoTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 30, 209, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelVistas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelVistas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblOracionEspañol;
    public javax.swing.JLabel LblOracionKichwa;
    public javax.swing.JLabel LblPalabraKichwa;
    public javax.swing.JLabel LblTituloPalabraDia;
    public javax.swing.JLabel LblTituloUnidades;
    public javax.swing.JLabel LblU1Foto;
    public javax.swing.JLabel LblU1Titulo;
    public javax.swing.JLabel LblU2Foto;
    public javax.swing.JLabel LblU2Titulo;
    public javax.swing.JLabel LblU3Foto;
    public javax.swing.JLabel LblU3Titulo;
    public javax.swing.JLabel LblU4Foto;
    public javax.swing.JLabel LblU4Titulo;
    public javax.swing.JLabel LbliconoPalabra;
    public javax.swing.JPanel PanelPalabraDiaria;
    public javax.swing.JPanel PanelUnidad1;
    public javax.swing.JPanel PanelUnidad2;
    public javax.swing.JPanel PanelUnidad3;
    public javax.swing.JPanel PanelUnidad4;
    public javax.swing.JPanel PanelVistas;
    public javax.swing.JProgressBar ProgresoTotal;
    public javax.swing.JProgressBar ProgresoU1;
    public javax.swing.JProgressBar ProgresoU2;
    public javax.swing.JProgressBar ProgresoU3;
    public javax.swing.JProgressBar ProgresoU4;
    public javax.swing.JButton btnUnidad1;
    public javax.swing.JButton btnUnidad2;
    public javax.swing.JButton btnUnidad3;
    public javax.swing.JButton btnUnidad4;
    // End of variables declaration//GEN-END:variables
}
