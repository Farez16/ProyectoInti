package Controlador;

import Modelo.Grafico;
import Vista.Graficos;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class ControladorGrafico {

    private Graficos vista;
    private Grafico modelo;
    private DefaultCategoryDataset dataset;

    public ControladorGrafico(Graficos vista, Grafico modelo) {
        this.vista = vista;
        this.modelo = modelo;
        this.dataset = modelo.obtenerUsuariosPorUnidad();

        inicializar();
        agregarEventos();
    }

    private void inicializar() {
        mostrarGrafico("barra"); // tipo por defecto
        vista.setVisible(true);
    }

    private void agregarEventos() {
        vista.getRdBarras().addActionListener(e -> {
            if (vista.getRdBarras().isSelected()) {
                mostrarGrafico("barra");
            }
        });

        vista.getRdLineas().addActionListener(e -> {
            if (vista.getRdLineas().isSelected()) {
                mostrarGrafico("pastel");
            }
        });

        vista.getRdAreas().addActionListener(e -> {
            if (vista.getRdAreas().isSelected()) {
                mostrarGrafico("area");
            }
        });
    }

    private void mostrarGrafico(String tipo) {
        JFreeChart chart;

        if (tipo.equals("pastel")) {
            // Convertir DefaultCategoryDataset a DefaultPieDataset
            DefaultPieDataset pieDataset = new DefaultPieDataset();
            for (int i = 0; i < dataset.getColumnCount(); i++) {
                Comparable key = dataset.getColumnKey(i);
                Number value = dataset.getValue(0, i); // fila 0, columna i
                pieDataset.setValue(key.toString(), value.doubleValue());
            }

            chart = ChartFactory.createPieChart(
                    "Usuarios por Unidad",
                    pieDataset,
                    true, true, false);

            // Opcional: personalizar el PiePlot
            PiePlot plot = (PiePlot) chart.getPlot();
            plot.setCircular(true);
            plot.setLabelGap(0.02);

        } else if (tipo.equals("area")) {
            chart = ChartFactory.createAreaChart(
                    "Usuarios por Unidad", "Unidad", "Cantidad de Usuarios",
                    dataset, PlotOrientation.VERTICAL, true, true, false);

        } else if (tipo.equals("barra")) {
            chart = ChartFactory.createBarChart(
                    "Usuarios por Unidad", "Unidad", "Cantidad de Usuarios",
                    dataset, PlotOrientation.VERTICAL, true, true, false);

            CategoryPlot plot = chart.getCategoryPlot();
            plot.getRangeAxis().setLowerBound(0);
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setMaximumBarWidth(0.1);

        } else {
            // Por defecto línea
            chart = ChartFactory.createLineChart(
                    "Usuarios por Unidad", "Unidad", "Cantidad de Usuarios",
                    dataset, PlotOrientation.VERTICAL, true, true, false);
        }

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setMouseWheelEnabled(true);

        JPanel panel = vista.getPanelGraficos();
        panel.removeAll();
        panel.setLayout(new BorderLayout());
        panel.add(chartPanel, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();
    }
}
