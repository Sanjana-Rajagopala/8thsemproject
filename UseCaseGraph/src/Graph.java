import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
 
 
public class Graph extends Application {
	

    
 
    @Override public void start(Stage stage) {
        stage.setTitle("Line Chart Sample");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis("timestamp", 48500000, 48680000, 10000);
        final NumberAxis yAxis = new NumberAxis();
        
        xAxis.setLabel("TimeStamp");
        yAxis.setLabel("Message ID");
        
        
       
        //creating the chart
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("OpenStack Log Visualization");
        
        BufferedReader reader = null; 
		try {
 
			String sCurrentLine;

			 
			reader = new BufferedReader(new FileReader("E:\\finalyear\\LineChartData.csv"));
			String requestId = "[req-29e2c5a2-25ab-46e4-9997-3115a3e6086d";
			
			Boolean first = true;
			int cnt = 0;

			Scene scene  = new Scene(lineChart,800,600); 
			
			XYChart.Series<Number,Number> series = new XYChart.Series<Number,Number>();
			series.setName(requestId);
			
			while ((sCurrentLine = reader.readLine()) != null) {
				
					
				String arr[] = sCurrentLine.split(",");
				//System.out.println(sCurrentLine);
				
				if(arr[0].equals("20140527")) {
					if(arr[2].equals(requestId))
					{
						Number tmstmp = Integer.parseInt(arr[1]);
						Number msgId = Integer.parseInt(arr[3]);
						series.getData().add(new XYChart.Data<Number,Number>(tmstmp, msgId));
					
					}
					else{
					
							lineChart.getData().add(series);
							series = new XYChart.Series<Number,Number>();
							requestId = arr[2];
							series.setName(requestId);
						
					}
						
					
					
				}
				else
					continue;
				
				
			}
			   
	      	stage.setScene(scene);
	      	stage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)reader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} 
		
    }
 
    public static void main(String[] args) {
    	
        launch(args);
    }
}