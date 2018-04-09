package controllersPackage;

import java.io.BufferedReader;
/* Imports java, com, javafx, mainPackage */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ResourceBundle;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import mainPackage.Connection;
import mainPackage.User;

public class AccountTabController implements Initializable {
	
	@FXML private TextField tfFirstName;
	@FXML private TextField tfMiddleName;
	@FXML private TextField tfLastName;
	@FXML private TextField tfAge;
	@FXML private TextField tfHomeTel;
	@FXML private TextField tfMobTel;
	
	@FXML private TextField tfEmergencyName;
	@FXML private TextField tfEmergencyTel;
	
	@FXML private TextField tfEmail;
	@FXML private TextField tfPassword;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Set GUI components
		updateGUI();
	}
	
	@FXML
	protected void handleUpdateAccount(ActionEvent event) throws IOException {
		
		String email = tfEmail.getText().toString();
		String password = tfPassword.getText().toString();
		
		// Validate that changes were made
		if(!email.equals(User.getInstance().email) || !password.equals(User.getInstance().password)){
			System.out.println("Change in email or password found!");
			
			// We can now perform a validaition
			if(validAccount(email, password)){
				// Send to php script
				updateAccount(email, password);
				updateGUI();
			}
		}

		// return outcome
		System.out.println("// End of Update Account");
	}

	/**
	 * Validates that we entered a correct email and password
	 * @param email
	 * @param password
	 * @return
	 * 		true if data inputed was valid
	 */
	private boolean validAccount(String email, String password){
		return true;
	}
	
	/**
	 * Sends data to a php script and updates the current user values
	 * @param email
	 * @param password
	 */
	private void updateAccount(String email, String password){
		boolean updated = false;
		String data = Connection.URL_UPDATE_ACCOUNT;

		try {
			URL calledUrl = new URL(data);
			URLConnection phpConnection = calledUrl.openConnection();

			HttpURLConnection httpBasedConnection = (HttpURLConnection) phpConnection;
			httpBasedConnection.setRequestMethod("POST");
			httpBasedConnection.setDoOutput(true);
			StringBuffer paramsBuilder = new StringBuffer();
			paramsBuilder.append("id=" + User.getInstance().id);
			paramsBuilder.append("&email=" + email);
			paramsBuilder.append("&password=" + password);

			PrintWriter requestWriter = new PrintWriter(httpBasedConnection.getOutputStream(), true);
			requestWriter.print(paramsBuilder.toString());
			requestWriter.close();

			BufferedReader responseReader = new BufferedReader(new InputStreamReader(phpConnection.getInputStream()));

			String receivedLine;
			StringBuffer responseAppender = new StringBuffer();

			while ((receivedLine = responseReader.readLine()) != null) {
				responseAppender.append(receivedLine);
				responseAppender.append("\n");
			}
			responseReader.close();
			String result = responseAppender.toString();
			System.out.println(result);

			// Read it in JSON
			try {
				JSONObject json = new JSONObject(result);
				System.out.println(json.getString("query_result"));
				String query_response = json.getString("query_result");

				if (query_response.equals("FAILED_UPDATE_ACCOUNT")) {
					updated = false;
				} else if (query_response.equals("SUCCESSFUL_UPDATE_ACCOUNT")) {
					System.out.println("We can successfully delete this from the table!!!");
					updated = true;
					
					// Update the current user
					User.getInstance().email = email;
					User.getInstance().password = password;
					
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

		System.out.println("Updated?: " + updated);
	}
	
	@FXML
	protected void handleUpdatePicture(ActionEvent event) throws IOException {
		// Open Dialog to find image
		
		// Upload Image to web server
		
		// Change Image in GUI
	}
	
	@FXML
	protected void handleUpdateProfile(ActionEvent event) throws IOException {
		
		String[] val = new String[8];
		
		val[0] = tfFirstName.getText().toString();
		val[1] = tfMiddleName.getText().toString();
		val[2] = tfLastName.getText().toString();
		val[3] = tfAge.getText().toString();
		val[4] = tfHomeTel.getText().toString();
		val[5] = tfMobTel.getText().toString();
		val[6] = tfEmergencyName.getText().toString();
		val[7] = tfEmergencyTel.getText().toString();
		
		User usr = User.getInstance();
		usr.fillProfileData();
		
		boolean changed = false;
		for(int i=0; i<val.length; i++){
			if(!val[i].equals(usr.profileData[i])){
				changed = true;
			}
		}
		
		// Validate that changes were made
		if(changed){
			System.out.println("Change in profile was found!");
			
			// We can now perform a validation
			if(validProfile(val)){
				// Send to php script
				updateProfile(val);
				updateGUI();
			}
		}

		// return outcome
		System.out.println("// End of Update Account");
	}
	
	private void updateProfile(String[] rgVals) {
		boolean updated = false;
		String data = Connection.URL_UPDATE_PROFILE;

		try {
			URL calledUrl = new URL(data);
			URLConnection phpConnection = calledUrl.openConnection();

			HttpURLConnection httpBasedConnection = (HttpURLConnection) phpConnection;
			httpBasedConnection.setRequestMethod("POST");
			httpBasedConnection.setDoOutput(true);
			StringBuffer paramsBuilder = new StringBuffer();
			paramsBuilder.append("id=" + User.getInstance().id);
			paramsBuilder.append("&fName=" + rgVals[0]);
			paramsBuilder.append("&mName=" + rgVals[1]);
			paramsBuilder.append("&lName=" + rgVals[2]);
			paramsBuilder.append("&age=" + rgVals[3]);
			paramsBuilder.append("&tel=" + rgVals[4]);
			paramsBuilder.append("&mob=" + rgVals[5]);
			paramsBuilder.append("&eName=" + rgVals[6]);
			paramsBuilder.append("&eTel=" + rgVals[7]);
			

			PrintWriter requestWriter = new PrintWriter(httpBasedConnection.getOutputStream(), true);
			requestWriter.print(paramsBuilder.toString());
			requestWriter.close();

			BufferedReader responseReader = new BufferedReader(new InputStreamReader(phpConnection.getInputStream()));

			String receivedLine;
			StringBuffer responseAppender = new StringBuffer();

			while ((receivedLine = responseReader.readLine()) != null) {
				responseAppender.append(receivedLine);
				responseAppender.append("\n");
			}
			responseReader.close();
			String result = responseAppender.toString();
			System.out.println(result);

			// Read it in JSON
			try {
				JSONObject json = new JSONObject(result);
				System.out.println(json.getString("query_result"));
				String query_response = json.getString("query_result");

				if (query_response.equals("FAILED_UPDATE_PROFILE")) {
					updated = false;
				} else if (query_response.equals("SUCCESSFUL_UPDATE_PROFILE")) {
					System.out.println("We can successfully delete this from the table!!!");
					updated = true;
					
					User usr = User.getInstance();
					
					// Update the current user
					usr.first_name = rgVals[0];
					usr.middle_name = rgVals[1];
					usr.last_name = rgVals[2];
					usr.age = rgVals[3];
					usr.home_telephone = rgVals[4];
					usr.mobile = rgVals[5];
					usr.emergency_name = rgVals[6];
					usr.emergency_number = rgVals[7];
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

		System.out.println("Updated?: " + updated);
	}

	private boolean validProfile(String[] rgVals) {
		return true;
	}

	private void updateGUI(){
		tfFirstName.setText(User.getInstance().first_name);
		tfMiddleName.setText(User.getInstance().middle_name);
		tfLastName.setText(User.getInstance().last_name);
		tfAge.setText(User.getInstance().age);
		tfHomeTel.setText(User.getInstance().home_telephone);
		tfMobTel.setText(User.getInstance().mobile);
		
		tfEmergencyName.setText(User.getInstance().emergency_name);
		tfEmergencyTel.setText(User.getInstance().emergency_number);
		
		tfEmail.setText(User.getInstance().email);
		tfPassword.setText(User.getInstance().password);
	}
}


