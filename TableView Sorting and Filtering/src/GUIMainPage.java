import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
 
public class GUIMainPage extends Application {
	
	ObservableList<String> dates = FXCollections.observableArrayList();
 
    
    private static String date = "";
    private Stage stage;
    public static int  min = Integer.MAX_VALUE;
    public static int max = 0;
    
    
	@Override
	public void start(Stage primaryStage) {
		
		stage = primaryStage;
		stage.setTitle("OpenStack Log analysis");
		
		final Button tableview = new Button("Table view");
		final Button linechart1 = new Button("Use-Case visualization");
		final Button linechart2 = new Button("Flow Identification");
		
	    final VBox controls = new VBox(10);
		controls.setPadding(new Insets(10));
		controls.setAlignment(Pos.CENTER);
	    final BorderPane root = new BorderPane();
		root.setCenter(controls);
		controls.getChildren().addAll(tableview, linechart1, linechart2);
		
		tableview.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Main app = new Main();
  			   app.start(stage);
            }
        });
		
		linechart2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	ZoomableLineChart chart1 = new ZoomableLineChart();
            	chart1.start(stage);
            }
        });
		
		linechart1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	LineChart2 chart2 = new LineChart2();
            	chart2.start(stage);
            }
        });
		
		final Scene scene = new Scene(root, 200, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}       
 
	public static void main(String[] args) {
		launch(args);
	}
}