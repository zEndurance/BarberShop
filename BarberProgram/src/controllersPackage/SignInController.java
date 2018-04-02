package controllersPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

/* Import javafx, java, mainPackage */
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
import mainPackage.Connection;

public class SignInController {
	@FXML
	private Text actiontarget;
	@FXML
	private PasswordField passwordField;
	@FXML
	private TextField userField;

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

		System.out.println("trying to connect to a php script...");

		String email = userField.getText().toString();
		String password = passwordField.getText().toString();

		System.out.println("We entered the values to send: " + email + ":" + password);

		String data = Connection.getInstance().URL_LOGIN + "?email=" + email + "&password=" + password;

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

					// TODO - Could this be changed?
					/*
					 * Store values of the 'now' user Main.currentID = id;
					 * Main.currentUser = username; Main.currentPass = password;
					 * Main.currentEmail = email; Main.currentName = name;
					 * Main.currentECName = ecName; Main.currentECNum = ecNum;
					 * Main.currentMobile = mobile;
					 */

					Parent parent = FXMLLoader.load(getClass().getResource("/fxmlPackage/mainProgram.fxml"));
					Scene scene = new Scene(parent);
					Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					appStage.setScene(scene);
					appStage.setTitle("Barber Shop");
					appStage.setWidth(944);
					appStage.setHeight(600);
					appStage.show();
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

}
