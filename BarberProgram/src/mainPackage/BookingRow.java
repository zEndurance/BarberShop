package mainPackage;

import javafx.beans.property.SimpleStringProperty;

public class BookingRow {
	
	private final String columnName;
	
	private Booking data;
	
	public BookingRow(String columnName, Booking data){
		this.columnName = columnName;
		this.data = data;
	}
	
	public String getColumnName() {
        return columnName.toString();
    }
	
	public Booking getBooking(){
		return this.data;
	}

}
