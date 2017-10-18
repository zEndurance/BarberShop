package controllersPackage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainPackage.ApData;

public class AppointTabController implements Initializable {

	// Editing stuff
	@FXML
	TableView<ApData> appointTable;

	// Columns
	@FXML
	TableColumn<ApData, String> cDay;
	@FXML
	TableColumn<ApData, String> cDate;
	
	@FXML
	TableColumn<ApData, String> cT9;
	@FXML
	TableColumn<ApData, String> cT10;
	@FXML
	TableColumn<ApData, String> cT11;
	@FXML
	TableColumn<ApData, String> cT12;
	@FXML
	TableColumn<ApData, String> cT13;
	@FXML
	TableColumn<ApData, String> cT14;
	@FXML
	TableColumn<ApData, String> cT15;
	@FXML
	TableColumn<ApData, String> cT16;
	@FXML
	TableColumn<ApData, String> cT17;

	public ObservableList<ApData> data = FXCollections.observableArrayList(
	/*
	 * new ApData("1", "1", "Monday", "16/10/2017", "Shrek", "", "", "Booked", "",
	 * "", "", "", "", ""), new ApData("2", "2", "Monday", "16/10/2017", "Taq", "",
	 * "Booked", "", "", "", "", "", "", ""), new ApData("3", "3", "Monday",
	 * "16/10/2017", "Bari", "", "", "", "Booked by Rari", "", "", "", "", ""), new
	 * ApData("4", "4", "Monday", "16/10/2017", "Tom", "", "", "Booked", "", "", "",
	 * "", "", ""), new ApData("4", "4", "Monday", "16/10/2017", "Alex", "Booked",
	 * "", "", "", "", "", "", "", "")
	 */
	);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// GRAB DATA FROM SQL
		cDay.setCellValueFactory(new PropertyValueFactory<ApData, String>("Day"));
		cDate.setCellValueFactory(new PropertyValueFactory<ApData, String>("Date"));

		// Set column names to grab certain data
		cT9.setCellValueFactory(new PropertyValueFactory<ApData, String>("T9"));
		cT10.setCellValueFactory(new PropertyValueFactory<ApData, String>("T10"));
		cT11.setCellValueFactory(new PropertyValueFactory<ApData, String>("T11"));
		cT12.setCellValueFactory(new PropertyValueFactory<ApData, String>("T12"));
		cT13.setCellValueFactory(new PropertyValueFactory<ApData, String>("T13"));
		cT14.setCellValueFactory(new PropertyValueFactory<ApData, String>("T14"));
		cT15.setCellValueFactory(new PropertyValueFactory<ApData, String>("T15"));
		cT16.setCellValueFactory(new PropertyValueFactory<ApData, String>("T16"));
		cT17.setCellValueFactory(new PropertyValueFactory<ApData, String>("T17"));

		// Mysql reconnect
		
		/*
		String url = "jdbc:mysql://dbprojects.eecs.qmul.ac.uk/mm335";
		String username = "mm335";
		String password = "NpgigVp28He0g";
		*/
		
		///////////////////////////////////////////////////////////////////// EVERYWHERE ELSE CONNECTION
				
		String url = "jdbc:mysql://sql2.freesqldatabase.com:3306/sql2199713";
		String username = "sql2199713";
		String password = "nW7*wP8!";
		
		
		

		try (Connection connection = DriverManager.getConnection(url, username, password)) {

			Statement myStmt = connection.createStatement();

			System.out.println("CURRENT ID TO FIND IS >>>>>>>>>>>>>" + SignInController.currentID);
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM appointments WHERE userID ='" + SignInController.currentID + "'");

			ResultSetMetaData rsmd = myRs.getMetaData();

			// Debug statements to find column names
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.println("Column names: " + rsmd.getColumnName(i));
			}

			while (myRs.next()) {

				String ID = myRs.getString("ID");
				String userID = myRs.getString("userID");
				String day = myRs.getString("Day");
				String date = myRs.getString("Date");
				//String customerName = myRs.getString("customerName");
				String t9 = myRs.getString("t9");
				String t10 = myRs.getString("t10");
				String t11 = myRs.getString("t11");
				String t12 = myRs.getString("t12");
				String t13 = myRs.getString("t13");
				String t14 = myRs.getString("t14");
				String t15 = myRs.getString("t15");
				String t16 = myRs.getString("t16");
				String t17 = myRs.getString("t17");

				// Add String data
				data.add(new ApData(ID, userID, day, date, t9, t10, t11, t12, t13, t14, t15, t16, t17));

				// Debugging statements
				System.out.println("ID: " + ID);
				System.out.println("Day: " + day);
				System.out.println("date: " + date);
				System.out.println("t9: " + t9);
				System.out.println("t10: " + t10);
				System.out.println("t11: " + t11);
				System.out.println("t12: " + t12);
				System.out.println("t13: " + t13);
				System.out.println("t14: " + t14);
				System.out.println("t15: " + t15);
				System.out.println("t16: " + t16);
				System.out.println("t17: " + t17);
				
			}
		} catch (SQLException e) {
			System.out.println("ERROR IN SQL");
			e.printStackTrace();
		}

		appointTable.setItems(data);
		System.out.println("// END of AppointTab Initialize");
	}

	
	@FXML
	private void handleClickTableView(MouseEvent click) {
		// Grab the person data
		ApData person = appointTable.getSelectionModel().getSelectedItem();
		
		// Grab the column index to find values
		@SuppressWarnings("rawtypes")
		TablePosition tp = appointTable.getFocusModel().getFocusedCell();
		
		if(tp.getColumn() >= 2 && !person.getDescription(tp.getColumn() - 2).equals("")) {

			String name = person.getName(tp.getColumn() - 2);
			String desc = person.getDescription(tp.getColumn() - 2);
			String date = person.getDate();
			
			int nTime = (tp.getColumn() - 2) + 9;
			
			String time = Integer.toString(nTime) + ":00-" + Integer.toString(nTime+1) + ":00";
			
			String contact = person.getContactInfo(tp.getColumn() - 2);
			
			String image =  person.getImage(tp.getColumn() - 2);
			
			
			// Open new window
			
			Parent root;
	        try {
	        	FXMLLoader loader = new FXMLLoader(
	        		    getClass().getResource(
	        		      "/fxmlPackage/infoMiniTab.fxml"
	        		    )
	        		  );

	        		  Stage stage = new Stage(StageStyle.DECORATED);
	        		  stage.setTitle("Client Information");
	        		  stage.setScene(
	        		    new Scene(
	        		      (Pane) loader.load()
	        		    )
	        		  );
	        		  
	        		  InfoMiniTabController controller = 
	        				    loader.<InfoMiniTabController>getController();
	        				  controller.initData(name, desc, date, time, contact, image);
	            
	            
	            stage.show();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
			
		}
		
		
		
		
	}
	
}
