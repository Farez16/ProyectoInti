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

        setPreferredSize(new java.awt.Dimension(1190, 660));

        PanelVistas.setBackground(new java.awt.Color(247, 249, 252));
        PanelVistas.setPreferredSize(new java.awt.Dimension(1190, 660));

        TxtBuscar.setFont(new java.awt.Font("Montserrat", 0, 15)); // NOI18N

        btnBuscar.setBackground(new java.awt.Color(255, 102, 0));
        btnBuscar.setFont(new java.awt.Font("Montserrat", 1, 16)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");

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

        jLabel1.setFont(new java.awt.Font("Montserrat", 1, 15)); // NOI18N
        jLabel1.setText("Ingrese un correo para Buscar:");

        javax.swing.GroupLayout PanelVistasLayout = new javax.swing.GroupLayout(PanelVistas);
        PanelVistas.setLayout(PanelVistasLayout);
        PanelVistasLayout.setHorizontalGroup(
            PanelVistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVistasLayout.createSequentialGroup()
                .addGroup(PanelVistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelVistasLayout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel1)
                        .addGap(75, 75, 75)
                        .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelVistasLayout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1094, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(35, 35, 35))
        );
        PanelVistasLayout.setVerticalGroup(
            PanelVistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVistasLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(PanelVistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelVistasLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1))
                    .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelVistas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelVistas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel PanelVistas;
    public javax.swing.JTable TablaDatos;
    public javax.swing.JTextField TxtBuscar;
    public javax.swing.JButton btnBuscar;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
