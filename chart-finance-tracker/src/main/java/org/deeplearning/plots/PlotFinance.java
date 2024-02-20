package org.deeplearning.plots;

import lombok.Getter;
import org.deeplearning.exceptions.SaveChartException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import java.io.File;
import java.io.IOException;

public class PlotFinance extends ApplicationFrame {

    private static final String ACTUAL_SERIES_LABEL = "Actual";
    private static final String PREDICTED_SERIES_LABEL = "Predicted";
    private static final int DAY = 1;
    private static final int MONTH = 6;
    private static final int YEAR = 2023;
    private static final String CHART_TITLE = "Data vs Predictions";
    private static final String DOMAIN_LABEL = "Date";
    private static final String RANGE_LABEL = "Price";
    private static final int RANGE_AXIS_LOWER_BOUND = 200;
    private static final int RANGE_AXIS_UPPER_BOUND = 500;
    private static final int CHART_PANEL_WIDTH = 700;
    private static final int CHART_PANEL_HEIGHT = 700;
    private static final int SAVED_CHART_WIDTH = 900;
    private static final int SAVED_CHART_HEIGHT = 900;
    private final JFreeChart chart;

    @Getter
    private String savedPath;

    public PlotFinance(String title, double[] actual, double[] predicted) {
        super(title);
        final TimeSeriesCollection data = getTimeSeriesCollection(actual, predicted);

        this.chart = ChartFactory.createTimeSeriesChart(
                CHART_TITLE,
                DOMAIN_LABEL,
                RANGE_LABEL,
                data,
                true,
                true,
                false
        );

        final XYPlot plot = chart.getXYPlot();
        plot.getRangeAxis().setRange(new Range(RANGE_AXIS_LOWER_BOUND, RANGE_AXIS_UPPER_BOUND));

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, false); // Don't show lines for actual values
        renderer.setSeriesShapesVisible(0, true); // Show points for actual values
        renderer.setSeriesLinesVisible(1, false); // Don't show lines for predicted values
        renderer.setSeriesShapesVisible(1, true); // Show points for predicted values
        plot.setRenderer(renderer);

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(CHART_PANEL_WIDTH, CHART_PANEL_HEIGHT));
        setContentPane(chartPanel);
    }

    private TimeSeriesCollection getTimeSeriesCollection(double[] actual, double[] predicted) {
        final TimeSeries series1 = new TimeSeries(ACTUAL_SERIES_LABEL);
        for (int i = 0; i < actual.length; i++) {
            try {
                Day day = new Day(i + DAY, MONTH, YEAR);
                series1.add(day, actual[i]);
            } catch (IllegalArgumentException ignored) {
            }
        }

        final TimeSeries series2 = new TimeSeries(PREDICTED_SERIES_LABEL);
        for (int i = 0; i < predicted.length; i++) {
            try {
                Day day = new Day(i + DAY, MONTH, YEAR);
                series2.add(day, predicted[i]);
            } catch (IllegalArgumentException ignored) {
            }
        }

        final TimeSeriesCollection data = new TimeSeriesCollection();
        data.addSeries(series1);
        data.addSeries(series2);
        return data;
    }

    public void saveChart(String filePath) throws SaveChartException {
        try {
            this.savedPath = filePath;
            ChartUtilities.saveChartAsPNG(new File(filePath), this.chart, SAVED_CHART_WIDTH, SAVED_CHART_HEIGHT);
        } catch (IOException e) {
            throw new SaveChartException(e, filePath);
        }
    }
}