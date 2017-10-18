package controllersPackage;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignInController {
	@FXML
	private Text actiontarget;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField userField;
	
	
	// These values are for the current logged in user
	public static int currentID = -1;
	public static String currentUser = "";
	public static String currentPass = "";
	public static String currentEmail = "";
	public static String currentName = "";
	public static String currentMobile = "";
	public static String currentECName = "";
	public static String currentECNum = "";
	

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

		// CONNECTION TO MYSQL HERE
		
		
		///////////////////////////////////////////////////////////////////// QM CONNECTION
		/* 
		String url = "jdbc:mysql://dbprojects.eecs.qmul.ac.uk/mm335";
		String dbUsername = "mm335";
		String dbPassword = "NpgigVp28He0g";

		System.out.println("Connecting database...");
        */
		
		/////////////////////////////////////////////////////////////////////
		
		///////////////////////////////////////////////////////////////////// EVERYWHERE ELSE CONNECTION
		
		String url = "jdbc:mysql://sql2.freesqldatabase.com:3306/sql2199713";
		String dbUsername = "sql2199713";
		String dbPassword = "nW7*wP8!";
		
		System.out.println("Connecting database...");
		

		try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
			System.out.println("Database connected!");
			Statement myStmt = connection.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM login");
			while (myRs.next()) {
				// CHECK FOR MYSQL DETAILS ARE CORRECT
				
				int id = myRs.getInt(1);
				
				String username = myRs.getString("Username");
				String password = myRs.getString("Password");
				String email = myRs.getString("Email");
				String name = myRs.getString("Name");
				String ecName = myRs.getString("ECName");
				String ecNum = myRs.getString("ECNum");
				String mobile = myRs.getString("Mobile");
				

				// Check for details

				if (passwordField.getText().equals(password) && userField.getText().equals(username)) {

					// Store values of the 'now' user
					currentID = id;
					currentUser = username;
					currentPass = password;
					currentEmail = email;
					currentName = name;
					currentECName = ecName;
					currentECNum = ecNum;
					currentMobile = mobile;
					
					
					
					// CHANGE SCENES
					Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/mainProgram.fxml"));
					Scene scene = new Scene(blah);
					Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					appStage.setScene(scene);
					appStage.setTitle("Barber Shop");
					appStage.setWidth(944);
					appStage.setHeight(600);
					appStage.show();
				} else {
					actiontarget.setText("Wrong username or password!");
				}

			}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}

	}

}
