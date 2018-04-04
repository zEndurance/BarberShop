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
		
		// Get values from URL/JSON
		String data = Connection.getInstance().URL_GET_PROFILE + "?id=" + User.getInstance().currentID;
		
		// send these values to the php script
		System.out.println("Connecting to page ----------> " + data);

		try {
			URL url = new URL(data);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			// Read the JSON output here
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Try reading it in JSON format
			try {
				JSONObject json = new JSONObject(response.toString());
				System.out.println(json.getString("query_result"));

				String query_response = json.getString("query_result");

				if (query_response.equals("FAILED_PROFILE")) {
					// Give a response to the user that its incorrect
					System.out.println("Incorrect email or password entered!");
				} else if (query_response.equals("SUCCESSFUL_PROFILE")) {

					// Read up the JSON values
					JSONObject obj = json.getJSONObject("0");

					String id = obj.getString("id");
					String first_name = obj.getString("first_name");
					String middle_name = obj.getString("middle_name");
					String last_name = obj.getString("last_name");
					String age = obj.getString("age");
					String home_telephone = obj.getString("home_telephone");
					String mobile = obj.getString("mobile");
					String emergency_name = obj.getString("emergency_name");
					String emergency_number = obj.getString("emergency_number");
					String profile_picture = obj.getString("profile_picture");
					
					
					// Set values for this logged in user
					tfEmployeeID.setText(id);
					tfEmployeeName.setText(first_name);
					tfMobile.setText(mobile);
					tfECName.setText(emergency_name);
					tfECNum.setText(emergency_number);
					tfEmployeeEmail.setText(User.getInstance().currentEmail);
					tfEmployeePassword.setText(User.getInstance().currentPass);
					
					
				} else {
					System.out.println("Not enough arguments were entered.. try filling both fields");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
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


