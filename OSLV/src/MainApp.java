
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Log> LogData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
    	/*
        LogData.add(new Log("Hans"));
        LogData.add(new Log("Ruth"));
        LogData.add(new Log("Heinz"));
        LogData.add(new Log("Cornelia"));
        LogData.add(new Log("Werner"));
        LogData.add(new Log("Lydia"));
        LogData.add(new Log("Anna"));
        LogData.add(new Log("Stefan"));
        LogData.add(new Log("Martin"));
    	
    	    	*/
    	BufferedReader reader = null;
    	 
		try {
 
			String sCurrentLine;
 
			reader = new BufferedReader(new FileReader("ALL_LOGS.txt"));
 
			while ((sCurrentLine = reader.readLine()) != null) {
				
				String arr[] = sCurrentLine.split(" ");
				
				
					
				if(isNum(arr[0])){
					if(isNum(arr[2])){
						String tmp = arr[2];
						arr[2] = arr[3];
						arr[3] = arr[4];
						arr[4] = arr[5];
						arr[5] = "";
						if(arr[4].equalsIgnoreCase("[instance:")){
							arr[4] = tmp + arr[4] + arr[6];
							arr[6] = "";
						}
						else
							arr[4] = tmp + arr[4];
							
					}
					if(arr.length > 6){
						String str = "";
						for(int i = 5; i < arr.length; i++)
							str += " " + arr[i];
						LogData.add(new Log(arr[0],arr[1],arr[2],arr[3],arr[4],str));	
					}
					else
						LogData.add(new Log(arr[0],arr[1],arr[2],arr[3],arr[4],arr[5]));
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
    	
    	
    }

    public boolean isNum(String str)
    {
    	if(str.length() >= 1){
    	if(str.toCharArray()[0] >= '0' && str.toCharArray()[0] <= '9')
    		return true;
    	}
    	return false;
    	
    }
    /**
     * Returns the data as an observable list of Logs. 
     * @return
     */
    public ObservableList<Log> getLogData() {
        return LogData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Log Visualization");

        initRootLayout();

       showLogOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("grslayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
           
            

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Log overview inside the root layout.
     */
    public void showLogOverview() {
       try {
            // Load Log overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("secondlayout.fxml"));
            AnchorPane LogOverview = (AnchorPane) loader.load();

            // Set Log overview into the center of root layout.
            rootLayout.setCenter(LogOverview);
            
             Controller controller = loader.getController();
            controller.setMainApp(this);
            
          
        } catch (IOException e) {
            e.printStackTrace();
        }
    	
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}