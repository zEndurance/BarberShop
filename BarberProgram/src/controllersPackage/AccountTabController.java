package controllersPackage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.jdbc.PreparedStatement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mainPackage.Main;
import mainPackage.MySQL;

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
		// Set values for this logged in user
		tfEmployeeID.setText(Integer.toString(Main.currentID));
		tfEmployeeName.setText(Main.currentName);
		tfMobile.setText(Main.currentMobile);
		tfECName.setText(Main.currentECName);
		tfECNum.setText(Main.currentECNum);
		tfEmployeeEmail.setText(Main.currentEmail);
		tfEmployeePassword.setText(Main.currentPass);
	}
	
	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
		// change the password
		
		int id = Integer.parseInt(tfEmployeeID.getText());
		String password = tfEmployeePassword.getText();
		
		// SQL database query
		
		///////////// QM CONNECTION
		
		/*
		String url = "jdbc:mysql://dbprojects.eecs.qmul.ac.uk/mm335";
		String dbUsername = "mm335";
		String dbPassword = "NpgigVp28He0g";
		
		*/
		
		///////////// EVERYWHERE ELSE CONNECTION
		
		/*
		String url = "jdbc:mysql://sql2.freesqldatabase.com:3306/sql2199713";
		String dbUsername = "sql2199713";
		String dbPassword = "nW7*wP8!";*/
		
		System.out.println("Connecting database...");
		
		System.out.println("232323");
		String sql = "UPDATE login SET Password='"+password+"' WHERE ID='" + tfEmployeeID.getText()+"'";

		System.out.println("SQL: " + sql);
		
		

		try (Connection connection = DriverManager.getConnection(MySQL.DATABASE_URL, MySQL.DATABASE_USERNAME, MySQL.DATABASE_PASSWORD)) {
			//Statement myStmt = connection.createStatement();

			//System.out.println("SQL: " + sql);
			//ResultSet myRs = myStmt.executeQuery(sql);
			
			
			// create the java mysql update preparedstatement
			String query = "update login set Password = ? where ID = ?";
			PreparedStatement preparedStmt = (PreparedStatement) connection.prepareStatement(query);
			preparedStmt.setString(1, password);
			preparedStmt.setInt(2, id);

			// execute the java preparedstatement
			preparedStmt.executeUpdate();
			
			
			// Create an alert window saying we did it!
			Alert alert = new Alert(AlertType.NONE);
	    	//alert.setTitle("Confirmation");
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
	}
}


