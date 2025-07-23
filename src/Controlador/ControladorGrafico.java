package Controlador;

import Modelo.Grafico;
import Vista.Admin.Graficos;

import org.jfree.chart.*;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.Date;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;

public class ControladorGrafico {

    private final Graficos vista;
    private final Grafico modelo;
    private DefaultCategoryDataset dataset;

    public ControladorGrafico(Graficos vista, Grafico modelo) {
        this.vista = vista;
        this.modelo = modelo;
        configurarInicialmente();
        configurarEventos();
        actualizarGrafico();
    }

    private void configurarInicialmente() {
        // Configuración inicial
        vista.getRdBarras().setSelected(true);
        vista.getjComboBox1().setSelectedIndex(0);

        // Establecer rango de fechas por defecto (últimos 30 días)
        Date fechaFin = new Date();
        Date fechaInicio = new Date(fechaFin.getTime() - (30L * 24 * 60 * 60 * 1000));
        vista.getjDateChooserDesde().setDate(fechaInicio);
        vista.getjDateChooserHasta().setDate(fechaFin);
    }

    private void configurarEventos() {
        // Listener para el ComboBox
        vista.getjComboBox1().addActionListener(e -> actualizarGrafico());

        // Listeners para los RadioButtons
        ActionListener radioListener = e -> actualizarGrafico();
        vista.getRdBarras().addActionListener(radioListener);
        vista.getRdPastel().addActionListener(radioListener);
        vista.getRdAreas().addActionListener(radioListener);

        // Listeners para cambios en fechas
        PropertyChangeListener fechaListener = evt -> {
            if ("date".equals(evt.getPropertyName())) {
                actualizarGrafico();
            }
        };
        vista.getjDateChooserDesde().getDateEditor().addPropertyChangeListener(fechaListener);
        vista.getjDateChooserHasta().getDateEditor().addPropertyChangeListener(fechaListener);
    }

    private void actualizarGrafico() {
        if (!validarFechas()) {
            return;
        }

        try {
            Date fechaDesde = vista.getjDateChooserDesde().getDate();
            Date fechaHasta = vista.getjDateChooserHasta().getDate();
            String filtro = (String) vista.getjComboBox1().getSelectedItem();

            switch (filtro) {
                case "Registros de usuarios":
                    dataset = modelo.obtenerRegistrosPorFecha(fechaDesde, fechaHasta);
                    break;
                case "Actividad por unidad":
                    dataset = modelo.obtenerUsuariosActivosPorUnidad(fechaDesde, fechaHasta);
                    break;
                case "Progreso completado":
                    dataset = modelo.obtenerProgresoCompletado(fechaDesde, fechaHasta);
                    break;
                case "Resultados de evaluaciones":
                    dataset = modelo.obtenerMetricasEvaluaciones(fechaDesde, fechaHasta);
                    break;
                case "Usuarios inactivos":
                    dataset = modelo.obtenerUsuariosInactivos(fechaDesde, fechaHasta);
                    break;
                default:
                    dataset = new DefaultCategoryDataset();
            }

            mostrarGrafico(crearGraficoSegunTipo());

        } catch (Exception e) {
            mostrarError("Error al generar gráfico: " + e.getMessage());
        }
    }

    private boolean validarFechas() {
        Date fechaDesde = vista.getjDateChooserDesde().getDate();
        Date fechaHasta = vista.getjDateChooserHasta().getDate();

        if (fechaDesde == null || fechaHasta == null) {
            mostrarError("Debe seleccionar ambas fechas");
            return false;
        }

        if (fechaDesde.after(fechaHasta)) {
            mostrarError("La fecha inicial no puede ser posterior a la final");
            return false;
        }

        return true;
    }

    private JFreeChart crearGraficoSegunTipo() {
        String titulo = (String) vista.getjComboBox1().getSelectedItem();

        if (vista.getRdPastel().isSelected()) {  // Cambiado de RdLineas a RdPastel
            return crearGraficoDePastel(titulo);
        } else if (vista.getRdAreas().isSelected()) {
            return crearGraficoDeAreas(titulo);
        } else {
            return crearGraficoDeBarras(titulo);
        }
    }

    private JFreeChart crearGraficoDeBarras(String titulo) {
        JFreeChart chart = ChartFactory.createBarChart(
                titulo,
                "Categoría",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        // Obtener el plot del gráfico
        CategoryPlot plot = chart.getCategoryPlot();

        // Configurar el renderizador de barras
        BarRenderer renderer = (BarRenderer) plot.getRenderer();

        // Establecer un ancho de barra fijo (en unidades relativas)
        renderer.setMaximumBarWidth(0.1); // Valor entre 0.0 y 1.0

        // Espacio entre categorías (en unidades relativas)
        renderer.setItemMargin(0.05); // Valor típico entre 0.0 y 0.2

        // Configurar el margen entre grupos de barras
        plot.setAxisOffset(new RectangleInsets(5, 5, 5, 5));

        // Asegurar que el eje Y empiece en 0
        plot.getRangeAxis().setLowerBound(0);

        return chart;
    }

    private JFreeChart crearGraficoDePastel(String titulo) {
        // Convertir DefaultCategoryDataset a DefaultPieDataset
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        // Asume que solo hay una serie de datos (fila 0)
        for (int i = 0; i < dataset.getColumnCount(); i++) {
            String categoria = dataset.getColumnKey(i).toString();
            Number valor = dataset.getValue(0, i);
            pieDataset.setValue(categoria, valor);
        }

        // Crear el gráfico de pastel
        JFreeChart chart = ChartFactory.createPieChart(
                titulo,
                pieDataset,
                true, // Incluir leyenda
                true, // Mostrar tooltips
                false // No generar URLs
        );

        // Personalización del gráfico
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setCircular(true); // Asegurar que sea circular perfecto
        plot.setLabelGap(0.02); // Espacio entre etiquetas
        plot.setInteriorGap(0.05); // Espacio interior (hueco central)

        // Configurar etiquetas con formato
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
                "{0}: {1} ({2})", // Formato: Nombre: Valor (Porcentaje)
                new DecimalFormat("0"), // Formato para valores
                new DecimalFormat("0%") // Formato para porcentajes
        ));

        return chart;
    }

    private JFreeChart crearGraficoDeAreas(String titulo) {
        return ChartFactory.createAreaChart(
                titulo,
                "Categoría",
                "Cantidad",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    private void mostrarGrafico(JFreeChart chart) {
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(
                vista.getPanelGraficos().getWidth(),
                vista.getPanelGraficos().getHeight()));

        vista.getPanelGraficos().removeAll();
        vista.getPanelGraficos().add(chartPanel, BorderLayout.CENTER);
        vista.getPanelGraficos().revalidate();
        vista.getPanelGraficos().repaint();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
