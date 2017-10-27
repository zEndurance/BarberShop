package controllersPackage;

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
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import mainPackage.Main;
import mainPackage.MySQL;

public class SignInController {
	@FXML
	private Text actiontarget;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField userField;

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

		// CONNECTION TO MYSQL HERE
		System.out.println("Connecting database...");

		try (Connection connection = DriverManager.getConnection(MySQL.DATABASE_URL, MySQL.DATABASE_USERNAME,
				MySQL.DATABASE_PASSWORD)) {
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
