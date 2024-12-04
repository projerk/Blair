package controller;

import components.chart.ChartUtils;
import components.interfaces.Listener;
import components.media.WrappedImageView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import utils.FileHelper;

;

public class AdminDashboardController implements Listener {
    
    @FXML
    private VBox piechart;

    @FXML
    private VBox areachart;

    @FXML
    private VBox totalBookIcon;

    @FXML
    private VBox totalStaffIcon;

    @FXML
    private VBox totalUserIcon;

    @FXML
    private VBox totalBorrowIcon;

    @FXML
    private void initialize() {
        setAlignment();
        drawChart();
    }

    private void setAlignment() {
        piechart.setAlignment(Pos.CENTER);
        areachart.setAlignment(Pos.CENTER);
    }

    private void drawChart() {
        iconPreload();
        drawPieChart();
        drawAreaChart();
    }

    private void iconPreload() {
        totalBookIconPreload();
        totalBorrowIconPreload();
        totalStaffIconPreload();
        totalUserIconPreload();
    }

    private void totalBookIconPreload() {
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(FileHelper.getImage("fbook.png"));
        totalBookIcon.getChildren().add(imageView);
    }

    private void totalUserIconPreload() {
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(FileHelper.getImage("fuser.png"));
        totalUserIcon.getChildren().add(imageView);
    }

    private void   totalBorrowIconPreload() {
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(FileHelper.getImage("fborrow.png"));
        totalBorrowIcon.getChildren().add(imageView);
    }

    private void totalStaffIconPreload() {
        WrappedImageView imageView = new WrappedImageView();
        imageView.setImage(FileHelper.getImage("fstaff.png"));
        totalStaffIcon.getChildren().add(imageView);
    }

    

    private void drawPieChart() {
        PieChart chart = ChartUtils.createPieChart("Glyph");
    
        ChartUtils.addPieData(chart, "T1", 100);
        ChartUtils.addPieData(chart, "T2", 200);
        ChartUtils.addPieData(chart, "T3", 300);
        ChartUtils.addPieData(chart, "T4", 400);
        ChartUtils.addPieData(chart, "T5", 150);
    
        chart.setLegendVisible(false);
    
        Platform.runLater(() -> {
            int i = 0;
            String[] grayShades = {"#D3D3D3", "#B0B0B0", "#808080", "#696969", "#505050"};
            for (PieChart.Data data : chart.getData()) {
                Node node = data.getNode();
                if (node != null) {
                    node.setStyle("-fx-pie-color: " + grayShades[i % grayShades.length] + ";");
                    i++;
                }
            }
        });
    
        piechart.getChildren().add(chart);
    }
    
    

    private void drawAreaChart() {
        BarChart<String, Number> chart = ChartUtils.createBarChart("Glyph", "G2", "G3");
        ChartUtils.addBarData(chart, "g6", "Hello", 10);
        ChartUtils.addBarData(chart, "g6", "Hello", 20);
        ChartUtils.addBarData(chart, "g6", "Hello", 40);
        ChartUtils.addBarData(chart, "g6", "Hello", 90);
        ChartUtils.addBarData(chart, "g6", "Hello", 29);
        ChartUtils.addBarData(chart, "g6", "Hello", 80);
        ChartUtils.addBarData(chart, "g6", "Hello", 16);
        ChartUtils.addBarData(chart, "g6", "Hello", 67);
        ChartUtils.addBarData(chart, "g6", "Hello", 17);

        chart.setLegendVisible(false);
        areachart.getChildren().add(chart);
    
        Platform.runLater(() -> {
            chart.lookupAll(".chart-bar").forEach(node -> {
                node.setStyle("-fx-bar-fill: black; -fx-background-radius: 3px 3px 0px 0px; -fx-width: 15px;");
            });
        });
    }


    

    public void openCanvas(int id) {

    }

    public void closeCanvas() {

    }
}