package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* Import javafx, java, mainPackage */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Booking;
import model.Connection;
import model.Service;
import model.User;

public class LoginController implements Initializable {
	
	// FXML - GUI Components
	@FXML
	Text actiontarget;
	@FXML
	PasswordField passwordField;
	@FXML
	TextField userField;
	
	
	// Used to open connections to PHP pages
	private HttpURLConnection connection;
	private URL url;
	// The string that the PHP pages response with
	private StringBuffer response;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO - MySQL grabbing all user data should happen here and not each time a tab opens
		
		// DEBUG 
		userField.setText("raj@barbershop.com");
		passwordField.setText("barber");
	}

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

		System.out.println("trying to connect to a php script...");

		String email = userField.getText().toString();
		String password = passwordField.getText().toString();

		System.out.println("We entered the values to send: " + email + ":" + password);

		String data = Connection.URL_LOGIN + "?email=" + email + "&password=" + password;

		// send these values to the php script
		System.out.println("Connection: " + data);

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

				if (query_response.equals("FAILED_LOGIN")) {
					// Give a response to the user that its incorrect
					System.out.println("Incorrect email or password entered!");
					actiontarget.setText("Wrong email or password!");
				} else if (query_response.equals("SUCCESSFUL_LOGIN")) {
					// We can go to the main program controller
					System.out.println("Successfull email and password entered!");
					
					// Read up the JSON values
					JSONObject obj = json.getJSONObject("0");

					//String id = obj.getString("id");
					User.getInstance().id = obj.getString("id");
					User.getInstance().created = obj.getString("created");
					User.getInstance().type = obj.getString("type");
					User.getInstance().email = email;
					User.getInstance().password = password;
					
					// Get profile data too
					loadProfileData();
					
					
					
					// Get all services available from the boss
					loadAllServicesData();
					
					// Get his service data
					loadServicesData();
					
					// Get his booking data also
					loadBookingData();

					// Go to next stage
					login(event);
					
				} else {
					System.out.println("Not enough arguments were entered.. try filling both fields");
					actiontarget.setText("Enter in both fields!");
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
	
	
	private StringBuffer connectToPage(String data) throws IOException {
		url = new URL(data);
		connection = (HttpURLConnection) url.openConnection();
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
		
		return response;
	}
	
	private void debugConnection(String data) {
		// Send these values to the PHP script
		System.out.println("Connecting to page ----------> " + data);
	}
	
	private void loadBookingData(){
		String data = Connection.URL_GET_BOOKINGS + "?id=" + User.getInstance().id;
		
		debugConnection(data);

		// Try connecting to the parse and then parsing the StringBuffer into objects
		try {
			response = connectToPage(data);
			parseJSONBookingData(response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseJSONBookingData(StringBuffer response) {
		// Try reading it in JSON format
		try {
			JSONObject json = new JSONObject(response.toString());
			System.out.println(json.getString("query_result"));

			String query_response = json.getString("query_result");

			if (query_response.equals("FAILED_BOOKINGS")) {
				// Give a response to the user that its incorrect
				System.out.println("Incorrect email or password entered!");
			} else if (query_response.equals("SUCCESSFUL_BOOKINGS")) {

				// Read up the JSON values
				List<String> list = new ArrayList<String>();
				JSONArray array = json.getJSONArray("bookings");
				
				// Clear bookings
			    User.getInstance().flushBookings();
				
				for(int i = 0 ; i < array.length() ; i++){
					
					String[] insertBooking = {array.getJSONObject(i).getString("id"),
		    				array.getJSONObject(i).getString("date"),
		    				array.getJSONObject(i).getString("start_time"),
		    				array.getJSONObject(i).getString("end_time"),
		    				array.getJSONObject(i).getString("person_id"),
		    				array.getJSONObject(i).getString("service_id")};
					
				    // Now add bookings
				    User.getInstance().bookings.add(
				    		new Booking(insertBooking));
				}
				
				for(int z=0; z<User.getInstance().bookings.size(); z++){
			    	System.out.println("Booking: " + User.getInstance().bookings.get(z).toString());
			    }
				
				// Sort the bookings arraylist
				Collections.sort(User.getInstance().bookings, new Comparator<Booking>() {
					@Override
					public int compare(Booking o1, Booking o2) {
						try {
				            return new SimpleDateFormat("HH:mm").parse(o1.getStartTime()).compareTo(new SimpleDateFormat("HH:mm").parse(o2.getStartTime()));
				        } catch (ParseException e) {
				            return 0;
				        }
				    }
				});

			} else {
				System.out.println("Not enough arguments were entered.. try filling both fields");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void loadProfileData(){
		// Get values from URL/JSON
		String data = Connection.URL_GET_PROFILE + "?id=" + User.getInstance().id;
		
		debugConnection(data);

		try {
			response = connectToPage(data);
			parseJSONProfileData(response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseJSONProfileData(StringBuffer data) {
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

				//String id = obj.getString("id");
				User.getInstance().first_name = obj.getString("first_name");
				User.getInstance().middle_name = obj.getString("middle_name");
				User.getInstance().last_name = obj.getString("last_name");
				User.getInstance().age = obj.getString("age");
				User.getInstance().home_telephone = obj.getString("home_telephone");
				User.getInstance().mobile = obj.getString("mobile");
				User.getInstance().emergency_name = obj.getString("emergency_name");
				User.getInstance().emergency_number = obj.getString("emergency_number");
				User.getInstance().profile_picture = obj.getString("profile_picture");
				
				
				// Set values for this logged in user
				
				
				
			} else {
				System.out.println("Not enough arguments were entered.. try filling both fields");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void loadServicesData(){
		String data = Connection.URL_GET_SERVICES + "?id=" + User.getInstance().id;
		
		debugConnection(data);
		
		try {
			response = connectToPage(data);

			parseJSONServicesData(response);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void parseJSONServicesData(StringBuffer data) {
		// Try reading it in JSON format
		try {
			JSONObject json = new JSONObject(response.toString());
			System.out.println(json.getString("query_result"));

			String query_response = json.getString("query_result");

			if (query_response.equals("FAILED_SERVICE")) {
				// Give a response to the user that its incorrect
				System.out.println("Incorrect email or password entered!");
			} else if (query_response.equals("SUCCESSFUL_SERVICE")) {

				// Read up the JSON values
				List<String> list = new ArrayList<String>();
				
				// Get the amount of objects
				int len = json.getInt("amount");
				
				// Flush Users current service data
				User.getInstance().flushServices();
				
				// Loop through each array element
				for(int i=0; i<len; i++){
					JSONObject obj = json.getJSONObject(Integer.toString(i));
					
					// Create the service objects of this User
					String[] vals = new String[3];
					vals[0] = obj.getString("id");
					vals[1] = obj.getString("service");
					vals[2] = obj.getString("price");
					
					// Add this to the Users services array
					User.getInstance().services.add(new Service(vals));
				}
			} else {
				System.out.println("Not enough arguments were entered.. try filling both fields");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	

	private void loadAllServicesData(){
		String data = Connection.URL_GET_ALL_SERVICES;
		
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

				if (query_response.equals("FAILED_SERVICE")) {
					// Give a response to the user that its incorrect
					System.out.println("Incorrect email or password entered!");
				} else if (query_response.equals("SUCCESSFUL_SERVICE")) {

					// Read up the JSON values
					List<String> list = new ArrayList<String>();
					
					// Get the amount of objects
					int len = json.getInt("amount");
					
					// Flush Users current service data
					User.getInstance().allServicesNames.clear();
					
					// Loop through each array element
					for(int i=0; i<len; i++){
						JSONObject obj = json.getJSONObject(Integer.toString(i));
						// Add this to the Users services array
						User.getInstance().allServicesNames.add(obj.getString("name"));
						
						// Save this to an object also
						
					}
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
	
	
	private void login(ActionEvent event) throws IOException{
		Parent parent = FXMLLoader.load(getClass().getResource("/view/mainProgram.fxml"));
		Scene scene = new Scene(parent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(scene);
		appStage.setTitle("Barber Shop");
		appStage.setWidth(944);
		appStage.setHeight(600);
		appStage.show();
	}

}
