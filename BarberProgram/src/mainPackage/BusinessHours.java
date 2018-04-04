package mainPackage;

public class BusinessHours {

	private String day;
	private String openingHours;
	private String closingHours;
	
	
	public BusinessHours(int i, String open, String close){
		// if validated
		
		// change values
		
		this.day = getDay(i);
		this.openingHours = open;
		this.closingHours = close;
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
