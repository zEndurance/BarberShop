package controllersPackage;

/* Import java, javafx, mainPackage */
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

	// FXML Table GUI
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

	// Collects ApData objects as each MySQL row
	public ObservableList<ApData> data = FXCollections.observableArrayList();

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Grab data from SQLs
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

		/*
		// MySQL Connection
		try (Connection connection = DriverManager.getConnection(Connection.DATABASE_URL, Connection.DATABASE_USERNAME,
				Connection.DATABASE_PASSWORD)) {

			// Create a statement
			Statement myStmt = connection.createStatement();

			System.out.println("Current logged in ID: " + Main.currentID);

			// Execute a statement
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM appointments WHERE userID ='" + Main.currentID + "'");

			// Grab the whole row data from the above statement
			ResultSetMetaData rsmd = myRs.getMetaData();

			// Debug statements to find column names
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				System.out.println("Column names: " + rsmd.getColumnName(i));
			}

			// While we have row data
			while (myRs.next()) {

				// Grab the data from the table
				String ID = myRs.getString("ID");
				String userID = myRs.getString("userID");
				String day = myRs.getString("Day");
				String date = myRs.getString("Date");
				String t9 = myRs.getString("t9");
				String t10 = myRs.getString("t10");
				String t11 = myRs.getString("t11");
				String t12 = myRs.getString("t12");
				String t13 = myRs.getString("t13");
				String t14 = myRs.getString("t14");
				String t15 = myRs.getString("t15");
				String t16 = myRs.getString("t16");
				String t17 = myRs.getString("t17");

				// Put the string data from the table into separate objects
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

		// Set the data for the table
		appointTable.setItems(data);

		ArrayList<String> dates = new ArrayList<String>();
		// Loop through each column and row
		for (@SuppressWarnings("rawtypes")
		TableColumn tc : appointTable.getColumns()) {

			// System.out.println("Date is equal to: " + day);
			if (tc.getId().startsWith("2017")) {
				dates.add(tc.getId());
			}

			// Colour the cell based off what it has stored inside it
			tc.setCellFactory(column -> {
				return new TableCell<ApData, String>() {
					protected void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);

						// Set the text value as what it was before
						setText(item);

						System.out.println("ITEM EQUAL TO: " + item);

						ApData currentRowdata = (ApData) getTableRow().getItem();

						if (item == null || empty) {
							setText(null);
							setStyle("");
						} else {
							if (item.equals("Available")) {

								try {

									System.out.println("DATE CHECKING: " + currentRowdata.getDate() + " DATE NOW: "
											+ new Date().toString());
									if (new SimpleDateFormat("yyyy-MM-dd").parse(currentRowdata.getDate())
											.after(new Date())) {
										setStyle("-fx-background-color: B1E6FC");
									} else if (new SimpleDateFormat("yyyy-MM-dd").parse(currentRowdata.getDate())
											.equals(new Date())) {
										System.out.println("SAME DATE FOUND!");
									} else if (new SimpleDateFormat("yyyy-MM-dd").parse(currentRowdata.getDate())
											.before(new Date())) {
										setText("Expired");
										setStyle("-fx-background-color: FCB1B1");
									}
								} catch (ParseException e) {
									e.printStackTrace();
								}

							} else if (item.equals("Booked")) {

								try {

									System.out.println("DATE CHECKING: " + currentRowdata.getDate() + " DATE NOW: "
											+ new Date().toString());
									if (new SimpleDateFormat("yyyy-MM-dd").parse(currentRowdata.getDate())
											.after(new Date())) {
										setStyle("-fx-background-color: B1FCBC");
									} else {
										setText("Completed");
										setStyle("-fx-background-color: #FF46FF");
									}
								} catch (ParseException e) {
									e.printStackTrace();
								}

								System.out.println("THIS BOOKING IS ON: " + currentRowdata.getDate());

							} else if (item.equals("-------------")) {
								setStyle("-fx-background-color: FCF2B1");
							} else {
								setStyle("");
							}
						}
					}
				};
			});
			// }
		}
		*/

		// END
		System.out.println("// END of AppointTab Initialize");
	}

	@FXML
	private void handleClickTableView(MouseEvent click) {
		// Grab the person data
		ApData person = appointTable.getSelectionModel().getSelectedItem();

		// Grab the column index to find values
		@SuppressWarnings("rawtypes")
		TablePosition tp = appointTable.getFocusModel().getFocusedCell();

		// Grab values after Day and Date
		if (tp.getColumn() >= 2 && person.getBookingValue(tp.getColumn() - 2).equals("1")) {

			// Gets data from the column selected based off index
			// If we clicked on the first available booking it would be the 2nd
			// Index in the table
			// But this 9am booking starts from 0 in the array stored in the
			// ApData object person
			String name = person.getName(tp.getColumn() - 2);
			String desc = person.getDescription(tp.getColumn() - 2);
			String date = person.getDate();

			// Converting the time value we receive, since the value of 9am
			// Starts from index 0
			// We add 9 and then convert the rest by adding :00 string and then
			// Adding 1 to 9 because
			// Its 9:00-10:00
			int nTime = (tp.getColumn() - 2) + 9;
			String time = Integer.toString(nTime) + ":00-" + Integer.toString(nTime + 1) + ":00";
			String contact = person.getContactInfo(tp.getColumn() - 2);
			String image = person.getImage(tp.getColumn() - 2);

			double price = person.getBookingPrice(tp.getColumn() - 2);
			System.out.println("DATA FOUND FOR PRICE: " + price);

			// Open new window
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxmlPackage/infoMiniTab.fxml"));

				Stage stage = new Stage(StageStyle.DECORATED);
				stage.setTitle("Client Information");
				stage.setScene(new Scene((Pane) loader.load()));

				InfoMiniTabController controller = loader.<InfoMiniTabController>getController();
				controller.initData(name, desc, date, time, contact, image, price);

				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// END handleClickTableView
	}
	// END
}
