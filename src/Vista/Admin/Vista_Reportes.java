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
        PanelRendimientoUnidad = new javax.swing.JPanel();
        btnRendimientoUnidad = new javax.swing.JButton();
        PanelUltimaActividad = new javax.swing.JPanel();
        btnUltimaActividad = new javax.swing.JButton();
        LblHasta = new javax.swing.JLabel();
        LblDesde = new javax.swing.JLabel();
        jDateChooserHasta = new com.toedter.calendar.JDateChooser();
        jDateChooserDesde = new com.toedter.calendar.JDateChooser();

        jPanel1.setBackground(new java.awt.Color(204, 221, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1190, 660));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LblTitulo.setText("Reportes");
        jPanel1.add(LblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 28, -1, -1));

        PanelProgresoGeneral.setBackground(new java.awt.Color(255, 255, 255));

        btnProgresoGeneral.setText("Progreso General");

        javax.swing.GroupLayout PanelProgresoGeneralLayout = new javax.swing.GroupLayout(PanelProgresoGeneral);
        PanelProgresoGeneral.setLayout(PanelProgresoGeneralLayout);
        PanelProgresoGeneralLayout.setHorizontalGroup(
            PanelProgresoGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelProgresoGeneralLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(btnProgresoGeneral)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        PanelProgresoGeneralLayout.setVerticalGroup(
            PanelProgresoGeneralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelProgresoGeneralLayout.createSequentialGroup()
                .addContainerGap(178, Short.MAX_VALUE)
                .addComponent(btnProgresoGeneral)
                .addGap(18, 18, 18))
        );

        jPanel1.add(PanelProgresoGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(351, 279, -1, -1));

        PanelRendimientoUnidad.setBackground(new java.awt.Color(255, 255, 255));

        btnRendimientoUnidad.setText("Rendimiento Unidad");

        javax.swing.GroupLayout PanelRendimientoUnidadLayout = new javax.swing.GroupLayout(PanelRendimientoUnidad);
        PanelRendimientoUnidad.setLayout(PanelRendimientoUnidadLayout);
        PanelRendimientoUnidadLayout.setHorizontalGroup(
            PanelRendimientoUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelRendimientoUnidadLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnRendimientoUnidad)
                .addGap(51, 51, 51))
        );
        PanelRendimientoUnidadLayout.setVerticalGroup(
            PanelRendimientoUnidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelRendimientoUnidadLayout.createSequentialGroup()
                .addContainerGap(182, Short.MAX_VALUE)
                .addComponent(btnRendimientoUnidad)
                .addGap(24, 24, 24))
        );

        jPanel1.add(PanelRendimientoUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 290, -1, -1));

        PanelUltimaActividad.setBackground(new java.awt.Color(255, 255, 255));

        btnUltimaActividad.setText("Ultima Actividad");

        javax.swing.GroupLayout PanelUltimaActividadLayout = new javax.swing.GroupLayout(PanelUltimaActividad);
        PanelUltimaActividad.setLayout(PanelUltimaActividadLayout);
        PanelUltimaActividadLayout.setHorizontalGroup(
            PanelUltimaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUltimaActividadLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(btnUltimaActividad)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelUltimaActividadLayout.setVerticalGroup(
            PanelUltimaActividadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelUltimaActividadLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnUltimaActividad)
                .addGap(18, 18, 18))
        );

        jPanel1.add(PanelUltimaActividad, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 270, -1, 229));

        LblHasta.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        LblHasta.setText("Hasta:");
        jPanel1.add(LblHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 80, -1, -1));

        LblDesde.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        LblDesde.setText("Filtrar por fecha desde:");
        jPanel1.add(LblDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(267, 78, -1, -1));
        jPanel1.add(jDateChooserHasta, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 80, 150, -1));
        jPanel1.add(jDateChooserDesde, new org.netbeans.lib.awtextra.AbsoluteConstraints(465, 78, 150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
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
    public javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
