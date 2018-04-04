package mainPackage;

// Singleton class

public class Connection {
	
	private static Connection singleton = new Connection();
	
	public static Connection getInstance(){
		return singleton;
	}
	
	private Connection(){};
	
	/* URL locations to send our program data to */
	public final String URL = "http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/scripts/";
	public final String URL_LOGIN  = URL + "login.php";
	public final String URL_SIGNUP = URL + "signup.php";
	public final String URL_BUSINESS_HOURS = URL + "business_hours.php";
	public final String URL_GET_PROFILE = URL + "get_profile.php";
}
