

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Simple model class for the person table.
 * 
 * @author Marco Jakob
 */
public class Person {

	private final StringProperty message;
    private final StringProperty timestamp;
    private final StringProperty level;
    private final StringProperty location;
    private final StringProperty info;

	public Person() {
        this(null);
    }

	public Person(String timestamp) {
        this.timestamp = new SimpleStringProperty(timestamp);
        this.level = new SimpleStringProperty("DEBUG");

        // Some initial dummy data, just for convenient testing.
        this.location = new SimpleStringProperty("some location");
        this.info = new SimpleStringProperty("xxxx");
        this.message = new SimpleStringProperty("some message");
     //   this.date = new SimpleStringProperty("1999, 2, 21");
    }

    public Person(String timestamp, String level, String location, String info, String message) {
    	this.timestamp = new SimpleStringProperty(timestamp);
    	this.level = new SimpleStringProperty(level);
        this.location = new SimpleStringProperty(location);
        this.info = new SimpleStringProperty(info);
        this.message = new SimpleStringProperty(message);
        
    }
	public String gettimestamp() {
        return timestamp.get();
    }

    public void settimestamp(String timestamp) {
        this.timestamp.set(timestamp);
    }

    public StringProperty timestampProperty() {
        return timestamp;
    }

    public String getlevel() {
        return level.get();
    }

    public void setlevel(String level) {
        this.level.set(level);
    }

    public StringProperty levelProperty() {
        return level;
    }

    public String getlocation() {
        return location.get();
    }

    public void setlocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public StringProperty getinfo() {
        return info;
    }

    public void setinfo(String info) {
        this.info.set(info);
    }

    public StringProperty infoProperty() {
        return info;
    }

    public String getmessage() {
        return message.get();
    }

    public void setmessage(String message) {
        this.message.set(message);
    }

    public StringProperty messageProperty() {
        return message;
    }

	
}