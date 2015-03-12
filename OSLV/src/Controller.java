

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class Controller {
    @FXML
    private TableView<Log> LogTable;
    @FXML
    private TableColumn<Log, String> datecolumn;
    @FXML
    private TableColumn<Log, String>timestampcolumn;
    @FXML
    private TableColumn<Log, String> levelcolumn;
    @FXML
    private TableColumn<Log, String> locationcolumn;
    @FXML
    private TableColumn<Log, String> messagecolumn;
    @FXML
    private TableColumn<Log, String> infocolumn;


    

    // Reference to the main application.
    private MainApp mainApp;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Controller() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the Log table with the two columns.
        datecolumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        timestampcolumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());
        levelcolumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
        locationcolumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        infocolumn.setCellValueFactory(cellData -> cellData.getValue().infoProperty());
        messagecolumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        LogTable.setItems(mainApp.getLogData());
    }
}