package mainPackage;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
	// Values for current user
	public static int currentID = -1;
	public static String currentUser = "";
	public static String currentPass = "";
	public static String currentEmail = "";
	public static String currentName = "";
	public static String currentMobile = "";
	public static String currentECName = "";
	public static String currentECNum = "";
	
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlPackage/loginPage.fxml"));
        
        stage.setTitle("Barber Shop Login");
        stage.setScene(new Scene(root, 944, 600));
        stage.show();
    }
}
