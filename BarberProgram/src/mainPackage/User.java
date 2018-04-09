package mainPackage;

import java.util.ArrayList;
import java.util.List;

/*
 * Singleton class of the current logged in User
 */
public class User {

	private static User singleton = new User();

	/*
	 * A private Constructor prevents any other class from instantiating.
	 */
	private User() {
	}

	/* Static 'instance' method */
	public static User getInstance() {
		return singleton;
	}
	
	// TODO - Should be set in the settings somewhere
	public String shop_id = "1";

	// Values for current logged in user
	public String id = "";
	public String email = "";
	public String password = "";
	public String created;
	public String type;
	
	// User Profile
	public String first_name;
	public String middle_name;
	public String last_name;
	public String age;
	public String home_telephone;
	public String mobile;
	public String emergency_name;
	public String emergency_number;
	public String profile_picture;
	
	public String[] profileData = new String[8];
	
	public void fillProfileData(){
		profileData[0] = first_name;
		profileData[1] = middle_name;
		profileData[2] = last_name;
		profileData[3] = age;
		profileData[4] = home_telephone;
		profileData[5] = mobile;
		profileData[6] = emergency_name;
		profileData[7] = emergency_number;
	}
	
	// Data holding bookings and services
	public List<Booking> bookings = new ArrayList<Booking>();
	public List<Service> services = new ArrayList<Service>();
	public List<String> allServicesNames = new ArrayList<String>();
	
	public List<String> getServices(){
		return allServicesNames;
	}
	
	public void flushBookings(){
		bookings.clear();
	}
	
	public void flushServices() {
		services.clear();
	}
}
