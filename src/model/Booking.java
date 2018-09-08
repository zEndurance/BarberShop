package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import controller.ConnectionController;

public class Booking extends ConnectionController {
	
	public static ArrayList<Profile> rgCustomers = new ArrayList<Profile>();
	
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
	private Service service;
	
	public Booking(String[] bookingData){
		
		// String id, String date, String startTime, String endTime, String person_id, String service_id
		
		
		// Convert everything in here
		this.id = Integer.parseInt(bookingData[0]);
		
		DateFormat formatterDate = new SimpleDateFormat("dd/mm/yy");
		DateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
		
		try {
			this.date = formatterDate.parse(bookingData[1]);
			this.startTime = formatterTime.parse(bookingData[2]);
			this.endTime = formatterTime.parse(bookingData[3]);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		boolean found = false;
		// Try to find this persons ID 
		for(int i=0; i<rgCustomers.size(); i++){
			
			if(bookingData[4].equals(rgCustomers.get(i).getID())){
				
				System.out.println("FOUND A CUSTOMER MATCH!!!" + bookingData[4] + " @ " + rgCustomers.get(i).getID());
				this.profile = rgCustomers.get(i);
				found = true;
				break;
			}
		}
		
		
		if(!found){
			// Open a URL connection and retrieve the data instead
			String data = Connection.URL_GET_CUSTOMER + "?id=" + bookingData[4];
			try {
				response = connectToPage(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
			found = parseJSONBooking(response);
		}
		
		if(!found){
			try {
				throw new Exception("Couldn't find a customer");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		
		found = false;
		// Link a service
		for(int i=0; i<User.getInstance().services.size(); i++){
			if(User.getInstance().services.get(i).getId() == Integer.parseInt(bookingData[5])){
				this.service = User.getInstance().services.get(i);
				found = true;
				break;
			}
		}
		
		if(!found){
			try {
				throw new Exception("Unable to find service!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private boolean parseJSONBooking(StringBuffer response) {
		boolean found = false;
		// Try reading it in JSON format
		try {
			makeJSON(response);
			if (query_response.equals("SUCCESSFUL_CUSTOMER")) {

				// Read up the JSON values
				List<String> list = new ArrayList<String>();
				JSONArray array = json.getJSONArray("customer");
				
				String[] values = new String[7];
				
				for(int i = 0 ; i < array.length() ; i++){
				    // Now create the profile
					values[0] = array.getJSONObject(i).getString("id");
					values[1] = array.getJSONObject(i).getString("first_name");
					values[2] = array.getJSONObject(i).getString("last_name");
					values[3] = array.getJSONObject(i).getString("age");
					values[4] = array.getJSONObject(i).getString("phone_number");
					values[5] = array.getJSONObject(i).getString("profile_picture");
					values[6] = array.getJSONObject(i).getString("account_id");
				}
				
				Profile profile = new Profile(values);
				rgCustomers.add(profile);
				this.profile = profile;
				
				found = true;
			} else {
				System.out.println("Not enough arguments were entered.. try filling both fields");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return found;
	}
	
	public int getID() {
		return this.id;
	}
	
	public Service getService(){
		return this.service;
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
        
        profile = source.profile;
        
        service = source.service;
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

	public String getPrice() {
		return Double.toString(this.service.getPrice());
	}
}
