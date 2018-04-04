package mainPackage;

import javafx.beans.property.SimpleStringProperty;

public class BookingRow {
	
	private final SimpleStringProperty[] values = new SimpleStringProperty[21];
	
	public BookingRow(String[] args){
		
		for(int i=0; i<args.length; i++){
			values[i] = new SimpleStringProperty(args[i]);
		}
	}
	
	public String getValues(int index) {
        return values[index].get();
    }

}
