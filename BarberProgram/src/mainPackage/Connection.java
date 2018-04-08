package mainPackage;

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
}
