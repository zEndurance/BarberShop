package mainPackage;

// Singleton class

public class Connection {
	
	private static Connection singleton = new Connection();
	
	public static Connection getInstance(){
		return singleton;
	}
	
	private Connection(){};
	
	/* URL locations to send our program data to */
	public final String URL_LOGIN  = "http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/scripts/login.php";
	public final String URL_SIGNUP = "http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/scripts/signup.php";
	public final String URL_BUSINESS_HOURS = "http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/scripts/business_hours.php";
 	
	public static void connect(){
		// Connection code here
	}
	
	public static void query(String query){
		// Query codes here
	}
}
