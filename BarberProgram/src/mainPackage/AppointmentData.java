package mainPackage;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;

public class AppointmentData {
	
	
	// MYSQL PROPERTIES
	
	private final SimpleStringProperty ID;
	
	private final SimpleStringProperty userID;
	
	private final SimpleStringProperty day;
	
	private final SimpleStringProperty date;
	
	private final SimpleStringProperty customerName;
	
	// Hours which have data
    private final ArrayList<SimpleStringProperty> times = new ArrayList<SimpleStringProperty>();
    

	public AppointmentData(String ID, String userID, String day, String date, String customerName, String[] sTimes) {
		
		this.ID = new SimpleStringProperty(ID);
		this.userID = new SimpleStringProperty(userID);
		this.day = new SimpleStringProperty(day);
		this.date = new SimpleStringProperty(date);
		this.customerName = new SimpleStringProperty(customerName);
		
		// Add times into the array
		for(int i=0; i<sTimes.length; i++) {
			times.add(new SimpleStringProperty(sTimes[i]));
		}
	}
	
	
	// 0-8(index values) and (9-15 times) string values, e.g. index 0 is the 9th hour
	public SimpleStringProperty getTimes(int id) {
		return times.get(id);
	}


	public SimpleStringProperty getID() {
		return ID;
	}


	public SimpleStringProperty getUserID() {
		return userID;
	}


	public SimpleStringProperty getDay() {
		return day;
	}


	public SimpleStringProperty getDate() {
		return date;
	}


	public SimpleStringProperty getCustomerName() {
		return customerName;
	}
	
}
