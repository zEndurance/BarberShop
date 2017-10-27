package controllersPackage;

/* Import java, javafx, mainPackage, enumPackage */
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mainPackage.Main;
import mainPackage.MySQL;
import mainPackage.TodayData;
import enumPackage.Status;

public class TodaysTabController implements Initializable {
	@FXML
	private ListView<TodayData> listView;
	@FXML
	private WebView webView;
	@FXML
	private DatePicker datePicker;

	/* Holds all the data for the list view */
	private ObservableList<TodayData> clientObservableList;


	public TodaysTabController() {
		// Construct data
		clientObservableList = FXCollections.observableArrayList();
	}

	@FXML
	protected void handleDateButtonAction(ActionEvent event) throws IOException {
		loadsDatesData();
	}

	private void loadsDatesData() {
		// Reset the list
		clientObservableList.remove(0, clientObservableList.size());

		LocalDate date = datePicker.getValue();
		System.err.println("Selected date: " + date);

		// Construct a new string
		String newDate = date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();

		System.out.println("useable date: " + newDate);

		// MySQL query here
		System.out.println("Connecting database...");
		try (Connection connection = DriverManager.getConnection(MySQL.DATABASE_URL, MySQL.DATABASE_USERNAME,
				MySQL.DATABASE_PASSWORD)) {
			// Select row based off date
			Statement myStmt = connection.createStatement();

			System.out.println("Current logged in ID: " + Main.currentID);

			// Execute a statement
			ResultSet myRs = myStmt.executeQuery(
					"SELECT * FROM appointments WHERE userID ='" + Main.currentID + "' AND Date='" + newDate + "'");
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
				String date2 = myRs.getString("Date");
				String t9 = myRs.getString("t9");
				String t10 = myRs.getString("t10");
				String t11 = myRs.getString("t11");
				String t12 = myRs.getString("t12");
				String t13 = myRs.getString("t13");
				String t14 = myRs.getString("t14");
				String t15 = myRs.getString("t15");
				String t16 = myRs.getString("t16");
				String t17 = myRs.getString("t17");
				// Debugging statements
				System.out.println("ID: " + ID);
				System.out.println("Day: " + day);
				System.out.println("date: " + date2);
				System.out.println("t9: " + t9);
				System.out.println("t10: " + t10);
				System.out.println("t11: " + t11);
				System.out.println("t12: " + t12);
				System.out.println("t13: " + t13);
				System.out.println("t14: " + t14);
				System.out.println("t15: " + t15);
				System.out.println("t16: " + t16);
				System.out.println("t17: " + t17);

				String[] combinedStr = { t9, t10, t11, t12, t13, t14, t15, t16, t17 };

				ArrayList<String> times = new ArrayList<String>();
				times.addAll(Arrays.asList(combinedStr));

				System.out.println("COMBINED STR LENGTH: ------------ > " + combinedStr.length);

				for (int i = 0; i < times.size(); i++) {
					String[] split = times.get(i).split(", ");

					if (split.length >= 3 && split[0].equals("1")) {
						System.out.println("Split: " + split[1]);
						clientObservableList.addAll(new TodayData(split[1], Status.NOTSHOWED, Integer.toString(i)));
					}
				}

				listView.setItems(clientObservableList);
				listView.setCellFactory(studentListView -> new ClientListViewCell());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		datePicker.setValue(NOW_LOCAL_DATE());
		loadsDatesData();
		// WebView view = new WebView();
		WebEngine engine = webView.getEngine();
		engine.load("http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/#");
	}

}


// add some Data
/*
 * clientObservableList.addAll(new TodayData("John Doe",
 * TodayData.STATUS.NOTSHOWED), new TodayData("Jane Doe",
 * TodayData.STATUS.CANCELLED), new TodayData("Donte Dunigan",
 * TodayData.STATUS.ARRIVED), new TodayData("Gavin Genna",
 * TodayData.STATUS.CHECKIN), new TodayData("Darin Dear",
 * TodayData.STATUS.CHECKIN), new TodayData("Pura Petty",
 * TodayData.STATUS.CANCELLED), new TodayData("Herma Hines",
 * TodayData.STATUS.WAITING));
 */