package mainPackage;

// Singleton class

public class Connection {
	
	private static Connection singleton = new Connection();
	
	public static Connection getInstance(){
		return singleton;
	}
	
	private Connection(){};
	
	/* URL locations to send our program data to */
	public final String URL_LOGIN  = "localhost/barbershop/login.php";
	public final String URL_SIGNUP = "localhost/barbershop/signup.php";
 	
	public static void connect(){
		// Connection code here
	}
	
	public static void query(String query){
		// Query codes here
	}
}
