package Vista;

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

    public JLabel getLblimagenfondoVista() {
        return LblimagenfondoVista;
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
        LblimagenfondoVista = new javax.swing.JLabel();

        PanelVistas.setBackground(new java.awt.Color(255, 255, 255));

        btnBuscar.setText("Buscar");

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

        LblimagenfondoVista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lbl.jpg"))); // NOI18N

        javax.swing.GroupLayout PanelVistasLayout = new javax.swing.GroupLayout(PanelVistas);
        PanelVistas.setLayout(PanelVistasLayout);
        PanelVistasLayout.setHorizontalGroup(
            PanelVistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVistasLayout.createSequentialGroup()
                .addGap(390, 390, 390)
                .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelVistasLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelVistasLayout.createSequentialGroup()
                .addGap(660, 660, 660)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(LblimagenfondoVista)
        );
        PanelVistasLayout.setVerticalGroup(
            PanelVistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelVistasLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(TxtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelVistasLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(PanelVistasLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnBuscar))
            .addComponent(LblimagenfondoVista)
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
    public javax.swing.JLabel LblimagenfondoVista;
    public javax.swing.JPanel PanelVistas;
    public javax.swing.JTable TablaDatos;
    public javax.swing.JTextField TxtBuscar;
    public javax.swing.JButton btnBuscar;
    public javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
