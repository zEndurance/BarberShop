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
import mainPackage.Main;

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
					Main.currentID = id;
					Main.currentUser = username;
					Main.currentPass = password;
					Main.currentEmail = email;
					Main.currentName = name;
					Main.currentECName = ecName;
					Main.currentECNum = ecNum;
					Main.currentMobile = mobile;
					
					
					
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
