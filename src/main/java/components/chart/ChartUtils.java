package components.chart;

import javafx.scene.chart.*;


/**
 * Easy to create and modify beautiful chart.
 * 
 * @author
 * @version
 * @since
 */
public class ChartUtils {

    /**
     * Creates a BarChart with the given parameters.
     *
     * @param title       Title of the chart
     * @param xAxisLabel  Label for the X axis
     * @param yAxisLabel  Label for the Y axis
     * @return BarChart<String, Number>
     */
    public static BarChart<String, Number> createBarChart(String title, String xAxisLabel, String yAxisLabel) {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel(xAxisLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        return barChart;
    }

    /**
     * Adds a data series to a BarChart.
     *
     * @param barChart    The BarChart instance
     * @param seriesName  Name of the data series
     * @param category    The category on the X axis
     * @param value       The value on the Y axis
     */
    public static void addBarData(BarChart<String, Number> barChart, String seriesName, String category, Number value) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        // series.setName(seriesName);
        series.getData().add(new XYChart.Data<>(category, value));
        barChart.getData().add(series);
    }

    /**
     * Creates a PieChart with the given title.
     *
     * @param title Title of the PieChart
     * @return PieChart
     */
    public static PieChart createPieChart(String title) {
        PieChart pieChart = new PieChart();
        pieChart.setTitle(title);
        return pieChart;
    }

    /**
     * Adds data to a PieChart.
     *
     * @param pieChart The PieChart instance
     * @param label    Label for the data
     * @param value    Value for the data
     */
    public static void addPieData(PieChart pieChart, String label, double value) {
        pieChart.getData().add(new PieChart.Data(label, value));
    }

    /**
     * Creates an AreaChart with the given parameters.
     *
     * @param title       Title of the chart
     * @param xAxisLabel  Label for the X axis
     * @param yAxisLabel  Label for the Y axis
     * @return AreaChart<Number, Number>
     */
    public static AreaChart<Number, Number> createAreaChart(String title, String xAxisLabel, String yAxisLabel) {
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel(xAxisLabel);

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel(yAxisLabel);

        AreaChart<Number, Number> areaChart = new AreaChart<>(xAxis, yAxis);
        areaChart.setTitle(title);
        return areaChart;
    }

    /**
     * Adds data to an AreaChart.
     *
     * @param areaChart   The AreaChart instance
     * @param seriesName  Name of the data series
     * @param xValue      Value on the X axis
     * @param yValue      Value on the Y axis
     */
    public static void addAreaData(AreaChart<Number, Number> areaChart, String seriesName, Number xValue, Number yValue) {
        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName(seriesName);
        series.getData().add(new XYChart.Data<>(xValue, yValue));
        areaChart.getData().add(series);
    }
}

