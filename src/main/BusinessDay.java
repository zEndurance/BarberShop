package main;

public class BusinessDay {

	private String day;
	private String openingHours;
	private String closingHours;
	private String interval;
	
	public BusinessDay(int i, String open, String close, String interval){
		// if validated
		
		// change values
		
		this.day = getDay(i);
		this.openingHours = open;
		this.closingHours = close;
		this.interval = interval;
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
