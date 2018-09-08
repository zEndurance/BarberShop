package model;

public class BusinessDay {

	private String day;
	private String openingHours;
	private String closingHours;
	private String interval;
	
	public BusinessDay(int i, String[] rgData){
		// if validated
		
		// change values
		
		this.day = getDay(i);
		this.openingHours = rgData[0];
		this.closingHours = rgData[1];
		this.interval = rgData[2];
	}
	
	public int getInterval(){
		return Integer.parseInt(interval);
	}
	
	public String getDay() {
		return day;
	}

	public String getOpeningHours() {
		return openingHours;
	}

	public String getClosingHours() {
		return closingHours;
	}
	
	private String getDay(int day) {
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		return days[day];
	}
}
