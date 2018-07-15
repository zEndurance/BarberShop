package main;

/**
 * Constants which apply to grabbing json data from the database
 */
public class Connection {
	// URL locations to send our program data to
	public static final String URL = "http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/scripts/";
	public static final String URL_LOGIN  = URL + "login.php";
	public static final String URL_SIGNUP = URL + "signup.php";
	public static final String URL_BUSINESS_HOURS = URL + "business_hours.php";
	public static final String URL_GET_PROFILE = URL + "get_profile.php";
	public static final String URL_GET_BOOKINGS = URL + "get_bookings.php";
	public static final String URL_GET_SERVICES = URL + "get_barbers_services_json.php";
	public static final String URL_GET_ALL_SERVICES = URL + "get_all_services.php";
	
	public static final String URL_GET_BOOKINGS_WEEK = URL + "get_bookings_week.php";
	public static final String URL_GET_CUSTOMER = URL + "get_customer.php";
	
	// UPLOADS
	public static final String URL_UPLOAD_IMAGE = URL + "upload_image.php";
	
	// DB INSERTS
	public static final String URL_INSERT_SERVICE = URL + "insert_barbers_services_json.php";
	
	// DB UPDATES
	public static final String URL_UPDATE_ACCOUNT = URL + "update_account.php";
	public static final String URL_UPDATE_PROFILE = URL + "update_profile.php";
	
	// DB DELETES
	public static final String URL_DELETE_SERVICE = URL + "delete_barbers_services_json.php";
	public static final String URL_DELETE_BOOKING = URL + "delete_booking.php";
}
