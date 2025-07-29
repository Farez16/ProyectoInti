package Vista.Admin;

import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Vista_Reportes extends javax.swing.JPanel {



    public Vista_Reportes() {
        initComponents();

    }
    
    public JLabel getLblDesde() {
        return LblDesde;
    }

    public JLabel getLblHasta() {
        return LblHasta;
    }

    public JLabel getLblTitulo() {
        return LblTitulo;
    }

    public JPanel getPanelProgresoGeneral() {
        return PanelProgresoGeneral;
    }

    public JPanel getPanelRendimientoUnidad() {
        return PanelRendimientoUnidad;
    }

    public JPanel getPanelUltimaActividad() {
        return PanelUltimaActividad;
    }

    public JButton getBtnProgresoGeneral() {
        return btnProgresoGeneral;
    }

    public JButton getBtnRendimientoUnidad() {
        return btnRendimientoUnidad;
    }

    public JButton getBtnUltimaActividad() {
        return btnUltimaActividad;
    }

    public JDateChooser getjDateChooserDesde() {
        return jDateChooserDesde;
    }

    public JDateChooser getjDateChooserHasta() {
        return jDateChooserHasta;
    }

    public JPanel getjPanel1() {
        return jPanel1;
    }

    public Date getFechaInicio() {
        return jDateChooserDesde.getDate();
    }

    public Date getFechaFin() {
        return jDateChooserHasta.getDate();
    }
    public void setFechaInicio(Date fecha) {
    jDateChooserDesde.setDate(fecha);
}

public void setFechaFin(Date fecha) {
    jDateChooserHasta.setDate(fecha);
}

    public String getFechaInicioFormateada() {
        return getFechaInicio() != null ? new SimpleDateFormat("dd/MM/yyyy").format(getFechaInicio()) : "";
    }

    public String getFechaFinFormateada() {
        return getFechaFin() != null ? new SimpleDateFormat("dd/MM/yyyy").format(getFechaFin()) : "";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        LblTitulo = new javax.swing.JLabel();
        PanelProgresoGeneral = new javax.swing.JPanel();
        btnProgresoGeneral = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        PanelRendimientoUnidad = new javax.swing.JPanel();
        btnRendimientoUnidad = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        PanelUltimaActividad = new javax.swing.JPanel();
        btnUltimaActividad = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        LblHasta = new javax.swing.JLabel();
        LblDesde = new javax.swing.JLabel();
        jDateChooserHasta = new com.toedter.calendar.JDateChooser();
        jDateChooserDesde = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(200, 225, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1190, 660));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblTitulo.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        LblTitulo.setForeground(new java.awt.Color(255, 255, 255));
        LblTitulo.setText("Reportes");
        jPanel1.add(LblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 28, -1, -1));

        PanelProgresoGeneral.setBackground(new java.awt.Color(255, 255, 255));
        PanelProgresoGeneral.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelProgresoGeneral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnProgresoGeneral.setBackground(new java.awt.Color(255, 102, 0));
        btnProgresoGeneral.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        btnProgresoGeneral.setForeground(new java.awt.Color(255, 255, 255));
        btnProgresoGeneral.setText("Generar Reporte");
        PanelProgresoGeneral.add(btnProgresoGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 305, -1, -1));

        jLabel2.setText("<html> \n  <div style=\"font-family: Montserrat; font-size: 13pt; font-weight: bold; margin-bottom: 10px; text-align: center;\">\n    Progreso General\n  </div>\n  <div style=\"font-family: Montserrat; font-size: 10pt; font-weight: normal;\">\n    Muestra el avance individual de los estudiantes en las unidades del curso, incluyendo porcentaje y última actividad.\n  </div>\n</html>");
        PanelProgresoGeneral.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 80));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/g1.jpg"))); // NOI18N
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelProgresoGeneral.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jPanel1.add(PanelProgresoGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(103, 190, 260, 350));

        PanelRendimientoUnidad.setBackground(new java.awt.Color(255, 255, 255));
        PanelRendimientoUnidad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelRendimientoUnidad.setPreferredSize(new java.awt.Dimension(260, 350));
        PanelRendimientoUnidad.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnRendimientoUnidad.setBackground(new java.awt.Color(255, 102, 0));
        btnRendimientoUnidad.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        btnRendimientoUnidad.setForeground(new java.awt.Color(255, 255, 255));
        btnRendimientoUnidad.setText("Generar Reporte");
        PanelRendimientoUnidad.add(btnRendimientoUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 305, -1, -1));

        jLabel3.setText("<html> \n  <div style=\"font-family: Montserrat; font-size: 13pt; font-weight: bold; margin-bottom: 10px; text-align: center;\">\n    Rendimiento Unidad\n  </div>\n  <div style=\"font-family: Montserrat; font-size: 10pt; font-weight: normal;\">\n    Resume el rendimiento promedio de los estudiantes en cada unidad del curso, con calificaciones y tiempo promedio.\n  </div>\n</html>");
        PanelRendimientoUnidad.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 80));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/g2.jpg"))); // NOI18N
        jLabel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelRendimientoUnidad.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jPanel1.add(PanelRendimientoUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(466, 190, 260, 350));

        PanelUltimaActividad.setBackground(new java.awt.Color(255, 255, 255));
        PanelUltimaActividad.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelUltimaActividad.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnUltimaActividad.setBackground(new java.awt.Color(255, 102, 0));
        btnUltimaActividad.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        btnUltimaActividad.setForeground(new java.awt.Color(255, 255, 255));
        btnUltimaActividad.setText("Generar Reporte");
        PanelUltimaActividad.add(btnUltimaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 305, -1, -1));

        jLabel4.setText("<html> \n  <div style=\"font-family: Montserrat; font-size: 13pt; font-weight: bold; margin-bottom: 10px; text-align: center;\">\n    Última Actividad\n  </div>\n  <div style=\"font-family: Montserrat; font-size: 10pt; font-weight: normal;\">\n    Lista completa de usuarios registrados con fecha de último acceso y estado actual.\n  </div>\n</html>");
        PanelUltimaActividad.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 80));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/g3.jpg"))); // NOI18N
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        PanelUltimaActividad.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, -1));

        jPanel1.add(PanelUltimaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(829, 190, 260, 350));

        LblHasta.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        LblHasta.setForeground(new java.awt.Color(255, 255, 255));
        LblHasta.setText("Hasta:");
        jPanel1.add(LblHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, -1, -1));

        LblDesde.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        LblDesde.setForeground(new java.awt.Color(255, 255, 255));
        LblDesde.setText("Filtrar por fecha desde:");
        jPanel1.add(LblDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, -1, -1));
        jPanel1.add(jDateChooserHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 150, -1));
        jPanel1.add(jDateChooserDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 80, 150, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoooo12.jpg"))); // NOI18N
        jLabel1.setPreferredSize(new java.awt.Dimension(1190, 660));
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 660));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel LblDesde;
    public javax.swing.JLabel LblHasta;
    public javax.swing.JLabel LblTitulo;
    public javax.swing.JPanel PanelProgresoGeneral;
    public javax.swing.JPanel PanelRendimientoUnidad;
    public javax.swing.JPanel PanelUltimaActividad;
    public javax.swing.JButton btnProgresoGeneral;
    public javax.swing.JButton btnRendimientoUnidad;
    public javax.swing.JButton btnUltimaActividad;
    public com.toedter.calendar.JDateChooser jDateChooserDesde;
    public com.toedter.calendar.JDateChooser jDateChooserHasta;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
