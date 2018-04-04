package mainPackage;

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
	
	public Booking(){
		
	}
}
