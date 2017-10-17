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

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

		// CONNECTION TO MYSQL HERE
		String url = "jdbc:mysql://dbprojects.eecs.qmul.ac.uk/mm335";
		String username = "mm335";
		String password = "NpgigVp28He0g";

		System.out.println("Connecting database...");

		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Database connected!");
			Statement myStmt = connection.createStatement();
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM Login");
			while (myRs.next()) {
				// CHECK FOR MYSQL DETAILS ARE CORRECT
				String Username = myRs.getString("Username");
				String Password = myRs.getString("Password");

				// Check for details

				if (passwordField.getText().equals(Password) && userField.getText().equals(Username)) {

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
