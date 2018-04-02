package controllersPackage;

/* Import javafx, java, mainPackage */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import mainPackage.Connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;

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
		
		String data = "http://" + Connection.getInstance().URL_LOGIN + "?email=" + email + "&password=" + password;

		// send these values to the php script
		System.out.println("Connection: " + data);
		
		try {
			URL url = new URL(data);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			//connection.connect();
			
			
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			
			// Read the JSON output here
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while((inputLine = in.readLine()) != null)
			{
				System.out.println(inputLine);
			}

			in.close();
			
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
		
		
		

		/*
		// Connection to MySql here
		try (Connection connection = DriverManager.getConnection(Connection.DATABASE_URL, Connection.DATABASE_USERNAME, Connection.DATABASE_PASSWORD)) {
			System.out.println("Database connected!");
			Statement myStmt = connection.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM login");
			while (myRs.next()) {

				// Checking for MySQL details are correct
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

					// Change Scenes
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
		}*/
		
		
		

	}

}
