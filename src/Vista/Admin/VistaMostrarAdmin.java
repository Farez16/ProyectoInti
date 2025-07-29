package Vista.Admin;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class VistaMostrarAdmin extends javax.swing.JPanel {


    public VistaMostrarAdmin() {
        initComponents();
    }

    public JPanel getPanelVistas() {
        return PanelVistas;
    }

    public JTable getTablaDatos() {
        return TablaDatos;
    }

    public JTextField getTxtBuscar() {
        return TxtBuscar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelVistas = new javax.swing.JPanel();
        TxtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaDatos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1190, 660));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        PanelVistas.setBackground(new java.awt.Color(200, 225, 255));
        PanelVistas.setPreferredSize(new java.awt.Dimension(1190, 660));
        PanelVistas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TxtBuscar.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N
        PanelVistas.add(TxtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 200, 35));

        btnBuscar.setBackground(new java.awt.Color(255, 102, 0));
        btnBuscar.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        PanelVistas.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 130, 35));

        TablaDatos.setFont(new java.awt.Font("Montserrat", 0, 13)); // NOI18N
        TablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(TablaDatos);

        PanelVistas.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(61, 110, 1094, 510));

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ingrese un correo para Buscar:");
        PanelVistas.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoooo12.jpg"))); // NOI18N
        jLabel2.setPreferredSize(new java.awt.Dimension(1190, 660));
        PanelVistas.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 660));

        add(PanelVistas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel PanelVistas;
    public javax.swing.JTable TablaDatos;
    public javax.swing.JTextField TxtBuscar;
    public javax.swing.JButton btnBuscar;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
