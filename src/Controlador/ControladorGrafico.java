package Controlador;

import Modelo.Grafico;
import Vista.Graficos;
import java.awt.BorderLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorGrafico {
    private Graficos vista;
    private Grafico modelo;
    private DefaultCategoryDataset dataset;

    public ControladorGrafico(Graficos vista, Grafico modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        inicializar();
        configurarEventos();
        actualizarGrafico(); // Mostrar gráfico inicial
    }

    private void inicializar() {
        // Configurar selección inicial
        vista.getRdBarras().setSelected(true);
        vista.getjComboBox1().setSelectedIndex(0);
    }

    private void configurarEventos() {
        // Evento para el ComboBox
        vista.getjComboBox1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarGrafico();
            }
        });

        // Eventos para los RadioButtons
        ActionListener radioListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarGrafico();
            }
        };

        vista.getRdBarras().addActionListener(radioListener);
        vista.getRdLineas().addActionListener(radioListener);
        vista.getRdAreas().addActionListener(radioListener);
    }

    private void actualizarGrafico() {
        // Obtener datos según la selección del ComboBox
        String filtro = (String) vista.getjComboBox1().getSelectedItem();
        
        if (filtro.equals("Estudiantes por unidad")) {
           // dataset = modelo.obtenerUsuariosPorUnidad();
        } else if (filtro.equals("Promedio de actividades por unidad")) {
           // dataset = modelo.obtenerPromedioActividadesPorUnidad();
        }else if (filtro.equals("Usuarios sin progreso")){
            dataset = modelo.obtenerUsuariosSinProgreso();
        }else if (filtro.equals("Contenido completado por los estudiantes")){
            dataset = modelo.obtenerContenidoCompletado();
        }

        // Determinar tipo de gráfico seleccionado
        String tipoGrafico = "";
        if (vista.getRdBarras().isSelected()) {
            tipoGrafico = "barra";
        } else if (vista.getRdLineas().isSelected()) {
            tipoGrafico = "pastel";
        } else if (vista.getRdAreas().isSelected()) {
            tipoGrafico = "area";
        }

        // Crear y mostrar el gráfico
        JFreeChart chart = crearChart(tipoGrafico, filtro);
        mostrarChartEnPanel(chart);
    }

    private JFreeChart crearChart(String tipo, String titulo) {
        switch (tipo) {
            case "pastel":
                return crearPieChart(titulo);
            case "area":
                return crearAreaChart(titulo);
            default: // "barra" por defecto
                return crearBarChart(titulo);
        }
    }

    private JFreeChart crearBarChart(String titulo) {
        JFreeChart chart = ChartFactory.createBarChart(
                titulo,
                "Unidad",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = chart.getCategoryPlot();
        plot.getRangeAxis().setLowerBound(0);
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setMaximumBarWidth(0.1);

        return chart;
    }

    private JFreeChart crearPieChart(String titulo) {
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        for (int i = 0; i < dataset.getColumnCount(); i++) {
            Comparable key = dataset.getColumnKey(i);
            Number value = dataset.getValue(0, i);
            pieDataset.setValue(key.toString(), value.doubleValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                titulo,
                pieDataset,
                true, true, false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setCircular(true);
        plot.setLabelGap(0.02);

        return chart;
    }

    private JFreeChart crearAreaChart(String titulo) {
        return ChartFactory.createAreaChart(
                titulo,
                "Unidad",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    private void mostrarChartEnPanel(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setPreferredSize(new java.awt.Dimension(1190, 600));

        JPanel panel = vista.getPanelGraficos();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
}
