package mainPackage;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Booking {
	
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
	
	public Booking(String id, String date, String startTime, String endTime, String person_id, String service_id){
		
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
		
		
		boolean found = false;
		// Try to find this persons ID 
		for(int i=0; i<rgCustomers.size(); i++){
			
			if(person_id.equals(rgCustomers.get(i).getID())){
				
				System.out.println("FOUND A CUSTOMER MATCH!!!" + person_id + " @ " + rgCustomers.get(i).getID());
				this.profile = rgCustomers.get(i);
				found = true;
				break;
			}
		}
		
		
		if(!found){
			// Open a URL connection and retrieve the data instead
			String data = Connection.URL_GET_CUSTOMER + "?id=" + person_id;
			
			System.out.println("Trying to find customer--->" + data);
			
			try {
				URL url = new URL(data);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("User-Agent", "Mozilla/5.0");

				// Read the JSON output here
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;

				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				// Try reading it in JSON format
				try {
					JSONObject json = new JSONObject(response.toString());
					System.out.println(json.getString("query_result"));

					String query_response = json.getString("query_result");

					if (query_response.equals("FAILED_CUSTOMER")) {
						
					} else if (query_response.equals("SUCCESSFUL_CUSTOMER")) {

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
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			if(User.getInstance().services.get(i).getId() == Integer.parseInt(service_id)){
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
