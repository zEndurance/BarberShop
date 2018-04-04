package mainPackage;

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

	// Values for current user
	public int currentID = 1;

	public String currentUser = "";
	public String currentPass = "";
	public String currentEmail = "";
	public String currentName = "";
	public String currentMobile = "";
	public String currentECName = "";
	public String currentECNum = "";

}
