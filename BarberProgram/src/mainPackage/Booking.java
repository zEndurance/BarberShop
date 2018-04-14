package mainPackage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
	
	private int id;

	// Format 4/20/2018
	private Date date;
	
	// Format HH:mm:ss
	private Date startTime;
	private Date endTime;
	
	// unused in local memory
	private int shop_id;
	private int user_id;
	
	// Connection to a persons booking
	private Profile profile;
	
	public Booking(String id, String date, String startTime, String endTime){
		
		// Convert everything in here
		this.id = Integer.parseInt(id);
		
		DateFormat formatterDate = new SimpleDateFormat("dd/mm/yy");
		DateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
		
		try {
			this.date = formatterDate.parse(date);
			this.startTime = formatterTime.parse(startTime);
			this.endTime = formatterTime.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		this.profile = new Profile(new String[]{"Raj"});
	}
	
	public Profile getProfile(){
		return this.profile;
	}
	
	/**
     * Copy constructor
     */
    public Booking(Booking source) {
        id = source.id;
        
        date = source.date;
        
        startTime = source.startTime;
        
        endTime = source.endTime;
    }
    
    @SuppressWarnings("deprecation")
	public String getDay(){
    	String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    	return days[date.getDay()];
    }
	
	public String getDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");
		return sdf.format(date);
	}

	public String getStartTime() {
		SimpleDateFormat hh = new SimpleDateFormat("HH:mm");
		return hh.format(startTime);
	}

	public String getEndTime() {
		SimpleDateFormat hh = new SimpleDateFormat("HH:mm");
		return hh.format(endTime);
	}
	
	@Override
	public String toString(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yy");
		SimpleDateFormat hh = new SimpleDateFormat("HH:mm:ss");
		
		return "ID: " + this.id + " date: " + sdf.format(date) + " start: " + hh.format(startTime) + " end: " + hh.format(endTime);
	}
}
