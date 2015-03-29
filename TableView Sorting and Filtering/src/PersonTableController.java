

import java.awt.event.ActionEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;


/**
 * View-Controller for the person table.
 * 
 * @author Marco Jakob
 */
public class PersonTableController {
	
	@FXML
	private TextField filterField;
	@FXML 
	private Button backButton;
	@FXML
	private TableView<Person> personTable;
	@FXML
    private TableColumn<Person, String>timestampcolumn;
    @FXML
    private TableColumn<Person, String> levelcolumn;
    @FXML
    private TableColumn<Person, String> locationcolumn;
    @FXML
    private TableColumn<Person, String> messagecolumn;
    @FXML
    private TableColumn<Person, String> infocolumn;


    

    // Reference to the main application.
    private Main mainApp;


	/**
	 * Just add some sample data in the constructor.
	 */
	public PersonTableController() {
		
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 * 
	 * Initializes the table columns and sets up sorting and filtering.
	 */
	@FXML
	private void initialize() {
		// 0. Initialize the columns.
		
	
		timestampcolumn.setCellValueFactory(cellData -> cellData.getValue().timestampProperty());
        levelcolumn.setCellValueFactory(cellData -> cellData.getValue().levelProperty());
        locationcolumn.setCellValueFactory(cellData -> cellData.getValue().locationProperty());
        infocolumn.setCellValueFactory(cellData -> cellData.getValue().infoProperty());
        messagecolumn.setCellValueFactory(cellData -> cellData.getValue().messageProperty());
		
		// 1. Wrap the ObservableList in a FilteredList (initially display all data).
		
	}
	public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
       
/*
        // Add observable list data to the table
        personTable.setItems(mainApp.getLogData());
  */      
        FilteredList<Person> filteredData = new FilteredList<>(mainApp.getLogData(), p -> true);
		
    
		// 2. Set the filter Predicate whenever the filter changes.
		filterField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(person -> {
				// If filter text is empty, display all persons.
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (person.gettimestamp().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches first name.
				} else if (person.getlevel().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else if (person.getlocation().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else if (person.getmessage().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Person> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(personTable.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		personTable.setItems(sortedData);
    }
	
	@FXML
	public void loadPrev() {
		GUIMainPage page = new GUIMainPage();
		page.start(mainApp.getPrimaryStage());
	}
}
