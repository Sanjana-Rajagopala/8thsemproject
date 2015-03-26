import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
 
public class ZoomableLineChart extends Application {
	
	ObservableList<String> dates = FXCollections.observableArrayList();
 
    
    private static String date = "";
    private Stage stage;
    public static float min = Float.MAX_VALUE;
    public static float max = 0;
    
    Map<XYChart.Data, String> points = new HashMap<>();
    
    
	@Override
	public void start(Stage primaryStage) {
		
		 stage = primaryStage;
		 stage.setTitle("OpenStack Log analysis");
		 BufferedReader reader = null; 
		 String startdate = "";
			try {
	 
				String sCurrentLine;			 
				reader = new BufferedReader(new FileReader("E:\\finalyear\\LineChartData.csv"));
				while ((sCurrentLine = reader.readLine()) != null) {
						
					String arr[] = sCurrentLine.split(",");
					if(dates.indexOf(arr[0]) < 0)
					{
						dates.add(arr[0]);
						startdate = arr[0];
				    }
				}   		
			}
			catch (IOException e) {
				e.printStackTrace();
			}

		
		ComboBox<String> datebox = new ComboBox<String>();
	    datebox.getItems().addAll(dates);
	    datebox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	    	  @Override public void changed(ObservableValue<? extends String> selected, String oldValue, String newValue) {
	    		  date = newValue;
	    		  updateChart();
	    	  }
	    	}); 
	    Label label1 = new Label("Select Date");
	    final HBox controls = new HBox(10);
		controls.setPadding(new Insets(10));
		controls.setAlignment(Pos.CENTER);
	    controls.getChildren().addAll(label1, datebox);
	    final BorderPane root = new BorderPane();
	   // root.setCenter(chartContainer);
		root.setCenter(controls);
		final Scene scene = new Scene(root, 200, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private void updateChart()
	{
		final LineChart<Number, Number> chart= createChart();
		
		final StackPane chartContainer = new StackPane();
		
		chartContainer.getChildren().add(chart);		
		
		final Rectangle zoomRect = new Rectangle();
		zoomRect.setManaged(false);
		zoomRect.setFill(Color.LIGHTSEAGREEN.deriveColor(0, 1, 1, 0.5));
		chartContainer.getChildren().add(zoomRect);
		
		setUpZooming(zoomRect, chart);
		
		final HBox controls = new HBox(10);
		controls.setPadding(new Insets(10));
		controls.setAlignment(Pos.CENTER);
		
		final Button resetButton = new Button("Reset");
		final TextField textField = new TextField ();
		final Button searchButton = new Button("Search");
		
		
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final NumberAxis xAxis = (NumberAxis)chart.getXAxis();
                xAxis.setLowerBound(min);
                xAxis.setUpperBound(max);
                final NumberAxis yAxis = (NumberAxis)chart.getYAxis();
                yAxis.setLowerBound(0);
                yAxis.setUpperBound(1000);
                
                zoomRect.setWidth(0);
                zoomRect.setHeight(0);
            }
        });
		
		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
	         public void handle(ActionEvent event) {
				 String text = textField.getText();
				 for (Map.Entry<XYChart.Data, String> entry : points.entrySet())
				 {
				     System.out.println(entry.getKey() + "/" + entry.getValue());
				     if(entry.getValue().contains(text)){
				  
				    	 entry.getKey().getNode().setStyle("-fx-background-color:red");
				    	// entry.getKey().getNode().setOnMouseEntered(value);
				    	 System.out.println(" " + text + "  " + entry.getValue());
				     }
				    	 
				 }
				 
	         }
		});
		
		
		final BooleanBinding disableControls = 
		        zoomRect.widthProperty().lessThan(5)
		        .or(zoomRect.heightProperty().lessThan(5));
		
		
		
		zoomRect.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                doZoom(zoomRect, chart);
            }
        });
		
		controls.getChildren().addAll(resetButton, textField, searchButton);
		final BorderPane root = new BorderPane();
		root.setCenter(chartContainer);
		root.setBottom(controls);
		
		final Scene scene = new Scene(root, 800, 600);
		
		stage.setScene(scene);
		stage.show();
		
	}
	private LineChart<Number, Number> createChart() {
	    
	    final NumberAxis xAxis = new NumberAxis("timestamp", 48500.000, 48680.000, 10000);
	    final NumberAxis yAxis = new NumberAxis();
	    yAxis.setLabel("UseCase");
	    final LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
	    //chart.setAnimated(false);
	   // chart.setCreateSymbols(false);
	       
        chart.setTitle("OpenStack Log Visualization");
       
        BufferedReader reader = null; 
		try {
 
			String sCurrentLine;			 
			reader = new BufferedReader(new FileReader("E:\\finalyear\\LineChartData.csv"));
			String requestId = "";			
			Boolean first = true;
		 		
			XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
			
			int cnt = 1;

			while ((sCurrentLine = reader.readLine()) != null) {
				
					
				String arr[] = sCurrentLine.split(",");
				
				if(arr[0].equals(date)) {
					
					if(first) {
						requestId = arr[2];
						first = false;
						series.setName(cnt + "-" + requestId);
					}
					if(requestId.equals("13476"))
					{
						continue;
					}
					if(arr[2].equals(requestId))
					{
					
						Number tmstmp = Float.parseFloat(arr[1]);
						System.out.println(tmstmp  + "  "  + requestId);
						String msg = arr[3];
						if((float)tmstmp < min)
							min = (float) tmstmp;
						if(max < (float)tmstmp)
							max = (float) tmstmp;
						
						
						XYChart.Data<Number,Number> data = new XYChart.Data<Number,Number>(tmstmp, cnt);
						data.setNode(new HoveredThresholdNode(msg,(float)tmstmp));
						points.put(data, msg);
						series.getData().add(data);
						
					
					}
					else{
					
							chart.getData().add(series);
							series = new XYChart.Series<Number,Number>();
							requestId = arr[2];
							cnt ++;
							series.setName(cnt + "-" + requestId);	
							
					}									
				}								
			}
			   
	      		
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)reader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} 
		xAxis.setLowerBound(min);
		xAxis.setUpperBound(max);
		
		xAxis.setTickUnit((max - min ) / 10 );
	    return chart ;
	}
    
    private void setUpZooming(final Rectangle rect, final Node zoomingNode) {
        final ObjectProperty<Point2D> mouseAnchor = new SimpleObjectProperty<>();
        zoomingNode.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseAnchor.set(new Point2D(event.getX(), event.getY()));
                rect.setWidth(0);
                rect.setHeight(0);
            }
        });
        zoomingNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                rect.setX(Math.min(x, mouseAnchor.get().getX()));
                rect.setY(Math.min(y, mouseAnchor.get().getY()));
                rect.setWidth(Math.abs(x - mouseAnchor.get().getX()));
                rect.setHeight(Math.abs(y - mouseAnchor.get().getY()));
            }
        });
    }
    
    private void doZoom(Rectangle zoomRect, LineChart<Number, Number> chart) {
        Point2D zoomTopLeft = new Point2D(zoomRect.getX(), zoomRect.getY());
        Point2D zoomBottomRight = new Point2D(zoomRect.getX() + zoomRect.getWidth(), zoomRect.getY() + zoomRect.getHeight());
        final NumberAxis yAxis = (NumberAxis) chart.getYAxis();
        Point2D yAxisInScene = yAxis.localToScene(0, 0);
        final NumberAxis xAxis = (NumberAxis) chart.getXAxis();
        Point2D xAxisInScene = xAxis.localToScene(0, 0);
        double xOffset = zoomTopLeft.getX() - yAxisInScene.getX() ;
        double yOffset = zoomBottomRight.getY() - xAxisInScene.getY();
        double xAxisScale = xAxis.getScale();
        double yAxisScale = yAxis.getScale();
        xAxis.setLowerBound(xAxis.getLowerBound() + xOffset / xAxisScale);
        xAxis.setUpperBound(xAxis.getLowerBound() + zoomRect.getWidth() / xAxisScale);
        yAxis.setLowerBound(yAxis.getLowerBound() + yOffset / yAxisScale);
        yAxis.setUpperBound(yAxis.getLowerBound() - zoomRect.getHeight() / yAxisScale);
        zoomRect.setWidth(0);
        zoomRect.setHeight(0);
    }
 
    class HoveredThresholdNode extends StackPane {
        HoveredThresholdNode(String msg, float tmstmp) {
          setPrefSize(15, 15);
          
     
          final Label label = createDataThresholdLabel(msg, tmstmp);
          
          
          setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
              getChildren().setAll(label);
              setCursor(Cursor.NONE);
              toFront();
            }
          });
       setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
              getChildren().clear();
              setCursor(Cursor.CROSSHAIR);
            }
          });
        }
     
       
        private Label createDataThresholdLabel(String msg, float tmstmp) {
          final Label label = new Label(msg);
          label.setCenterShape(true);
          label.setWrapText(true);
          label.setPrefWidth(300);
          label.setCenterShape(true);
          label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
          label.setStyle("-fx-font-size: 10; -fx-font-weight: bold;");
          
          /*label.setOnMouseClicked(new EventHandler<MouseEvent>() {
        	  @Override
        	  public void handle(MouseEvent mseve){
        		  
        		                final Stage dialog = new Stage();
        		                dialog.initModality(Modality.APPLICATION_MODAL);
        		                dialog.initOwner(stage);
        		                VBox dialogVbox = new VBox(20);
        		                Text txt = new Text(msg);
        		                txt.setW
        		                dialogVbox.getChildren().add(new Text(msg));
        		                
        		                Scene dialogScene = new Scene(dialogVbox, 300, 200);
        		                dialog.setScene(dialogScene);
        		                dialog.show();
        		            }
        		         });*/
      
    
          
     
          label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);
          return label;
        }
      }
     
	public static void main(String[] args) {
		launch(args);
	}
}