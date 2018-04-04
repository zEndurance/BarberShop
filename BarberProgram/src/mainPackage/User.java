package mainPackage;

import java.util.ArrayList;

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
	
	public void flushBookings(){
		bookings.clear();
	}
	
	public ArrayList<Booking> bookings = new ArrayList<Booking>();

	// Values for current user
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

}
