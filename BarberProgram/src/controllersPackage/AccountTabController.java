package controllersPackage;

import java.io.BufferedReader;
/* Imports java, com, javafx, mainPackage */
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainPackage.BusinessHours;
import mainPackage.Connection;
import mainPackage.Main;
import mainPackage.User;

public class AccountTabController implements Initializable {
	
	
	@FXML private TextField tfEmployeeID;
	@FXML private TextField tfEmployeeName;
	@FXML private TextField tfMobile;
	@FXML private TextField tfECName;
	@FXML private TextField tfECNum;
	@FXML private TextField tfEmployeeEmail;
	@FXML private TextField tfEmployeePassword;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tfEmployeeID.setText(User.getInstance().id);
		tfEmployeeName.setText(User.getInstance().first_name);
		tfMobile.setText(User.getInstance().mobile);
		tfECName.setText(User.getInstance().emergency_name);
		tfECNum.setText(User.getInstance().emergency_number);
		tfEmployeeEmail.setText(User.getInstance().email);
		tfEmployeePassword.setText(User.getInstance().password);
		
	}
	
	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
		
		// Grab new details
		int id = Integer.parseInt(tfEmployeeID.getText());
		String password = tfEmployeePassword.getText();
		
		
		/*
		// SQL database query
		try (Connection connection = DriverManager.getConnection(Connection.DATABASE_URL, Connection.DATABASE_USERNAME, Connection.DATABASE_PASSWORD)) {

			// Create the java MYSQL update prepared statement
			String query = "update login set Password = ? where ID = ?";
			PreparedStatement preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			preparedStmt.setString(1, password);
			preparedStmt.setInt(2, id);

			// Execute the java prepared statement
			preparedStmt.executeUpdate();
			
			// Create an alert window saying we did it!
			Alert alert = new Alert(AlertType.NONE);
	    	alert.setContentText("Your password has been changed!");
	    	ButtonType buttonTypeOne = new ButtonType("Ok");
	    	alert.getButtonTypes().setAll(buttonTypeOne);
	  
	    	Optional<ButtonType> result = alert.showAndWait();
	    	if (result.get() == buttonTypeOne){
	    		alert.close();
	    	}
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}
		*/
	}
}


