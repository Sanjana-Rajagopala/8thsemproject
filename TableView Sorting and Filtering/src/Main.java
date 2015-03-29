

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * Main class to start the application.
 * 
 * @author Marco Jakob
 */
public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> LogData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public Main() {
       
    	BufferedReader fp = null;
		String pattern = "[a-z]+";
		String num = "[0-9]+";
    	 
		try {
			String curLine = null;
			String location = null;
			String[] curArr;
			String timestamp = null;
			String logmsg = null;
			String loglevel = null;
			String reqid = null;
			fp = new BufferedReader(new FileReader("E:\\finalyear\\ALL_LOGS.txt"));
			while((curLine = fp.readLine()) != null) 
			{
				
				
				curArr = curLine.split("[ ]+");
				Pattern p1 = Pattern.compile(num);
				Matcher m1 = p1.matcher(curArr[0]);
				//check if the first token is date or string; if date continue otherwise go to next line
				
				if(m1.find() && Character.isDigit(curArr[0].charAt(0)))
				{
					timestamp = curArr[0] + " " + curArr[1];
					//System.out.println(timestamp);	
					Pattern p2 = Pattern.compile(num);
					Matcher m2 = p2.matcher(curArr[2]);
					//check if the 3rd token is number or log level
					if(!m2.find())
					{
					loglevel = curArr[2];
					//System.out.println(loglevel);
					location = curArr[3];
				//	System.out.println(location);				
					reqid = curArr[4].substring(1,curArr[4].length());
					//System.out.println(reqid);
					logmsg = curArr[7];
					for(int j=8;j<curArr.length;j++)
					{
						logmsg += curArr[j];
					}
					//System.out.println(logmsg);
					}
					else
					{
						loglevel = curArr[3];
						//System.out.println(loglevel);
						location = curArr[4];
						if(curArr.length >= 7 ){
							//System.out.println(location);
							logmsg = curArr[6];
							for(int j =7; j<curArr.length;j++)
							{
								logmsg += curArr[j];
							}
						}
						//System.out.println(logmsg);
					}
					
				}
				
				boolean add = LogData.add(new Person(timestamp,loglevel,location,reqid,logmsg));	
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fp != null)fp.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    	
    	
    }

    
    /**
     * Returns the data as an observable list of Logs. 
     * @return
     */
    public ObservableList<Person> getLogData() {
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
            loader.setLocation(Main.class.getResource("grslayout.fxml"));
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
            loader.setLocation(Main.class.getResource("PersonTable.fxml"));
            AnchorPane LogOverview = (AnchorPane) loader.load();

            // Set Log overview into the center of root layout.
            rootLayout.setCenter(LogOverview);
            
             PersonTableController controller = loader.getController();
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
