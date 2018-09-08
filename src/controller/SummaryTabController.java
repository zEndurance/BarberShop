package controller;

/* Import java, javafx, mainPackage */
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
//import mainPackage.ApData;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;

public class SummaryTabController implements Initializable {
	// Collects ApData objects as each MySQL row
	//public ObservableList<ApData> data = FXCollections.observableArrayList();

	// Called by the FXML loader after the labels declared above are injected:
	@FXML
	private Label lblCustomers;
	@FXML
	private Label lblHours;
	@FXML
	private Label lblMoney;
	@FXML
	private Label lbldayBest;
	@FXML
	private Label lblAverageHour;
	@FXML
	private Label lblExpensiveCut;
	@FXML
	private BarChart<String, Double> barChart;
	@FXML
	private CategoryAxis x;
	@FXML
	private NumberAxis y;

	public void initialize(URL arg0, ResourceBundle arg1) {
		int amountCustomers = 0;
		int loop = 0;
		int amountHours = 0;
		double amountMoney = 0;
		double mostDaily = 0;
		String dayBest = "";
		String mostExpensive = "";

		// Daily earnings for the bar chart
		double monEarn = 0;
		double TueEarn = 0;
		double WedEarn = 0;
		double ThurEarn = 0;
		double FriEarn = 0;
		double SatEarn = 0;
		double sunEarn = 0;

		String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
		Double[] earnings = { monEarn, TueEarn, WedEarn, ThurEarn, FriEarn, SatEarn, sunEarn };

		/*
		try (Connection connection = DriverManager.getConnection(Connection.DATABASE_URL, Connection.DATABASE_USERNAME,
				Connection.DATABASE_PASSWORD)) {

			// Create a statement
			Statement myStmt = connection.createStatement();

			// Execute a statement
			ResultSet myRs = myStmt.executeQuery("SELECT * FROM appointments WHERE userID ='" + Main.currentID + "'");

			while (myRs.next()) {
				// Grab the data from the table
				double dayMoneyTotal = 0;
				double mostSpent = 0;
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
				String[] variables = { t9, t10, t11, t12, t13, t14, t15, t16, t17 };

				// Get earnings for the bar chart
				for (int i = 0; i < variables.length; i++) {
					String[] splitStr = variables[i].split(", ");
					if (splitStr.length > 1) {
						// Working out daily earnings
						if (splitStr[1].length() > 2) {
							earnings[loop] = Double.parseDouble(splitStr[5]);
						}
					}
				}
				loop++; // Increments from 0 for x amount of loops

				// Check if the row contains data
				for (int i = 0; i < variables.length; i++) {
					String[] splitStr = variables[i].split(", ");
					if (splitStr.length > 1) {
						if (splitStr[1].length() > 2) {
							amountCustomers++; // overall customers
							amountHours++; // overall hours
							amountMoney += Double.parseDouble(splitStr[5]); // overall money
							dayMoneyTotal += Double.parseDouble(splitStr[5]); // daily money
						}
					}
				}

				// Check for certain row data
				for (int j = 0; j < variables.length; j++) {
					String[] splitStr = variables[j].split(", ");
					if (splitStr.length > 1)
						if (Double.parseDouble(splitStr[5]) > mostSpent) {
							// Searches for the most expensive type of cut that has been appointed.
							mostSpent = Double.parseDouble(splitStr[5]);
							mostExpensive = splitStr[2];
						}
					// Searches for the day with most money earned
					if (dayMoneyTotal > mostDaily) {
						mostDaily = amountMoney;
						dayBest = day;
					}
				} // End of for loop
			} // End of while loop

			XYChart.Series<String, Double> barData = new XYChart.Series<>(); // arraylist of data for the chart
			// Debugging for daily earnings array
			for (int i = 0; i < earnings.length; i++) {
				barData.getData().add(new XYChart.Data<>(days[i], earnings[i])); // add earnings to the bar chart
				System.out.println(earnings[i]);
			}
			barChart.getData().add(barData);

			// Initialising labels
			DecimalFormat df = new DecimalFormat("####0.00");
			lbldayBest.setText(dayBest);

			lblMoney.setText("£" + df.format(amountMoney));
			lblCustomers.setText(Integer.toString(amountCustomers));
			lblHours.setText(Integer.toString(amountHours));
			double average = amountMoney / amountHours;
			lblAverageHour.setText("£" + df.format(average));
			lblExpensiveCut.setText(mostExpensive);

		} catch (SQLException e) {
			System.out.println("ERROR IN SQL");
			e.printStackTrace();
		}
		*/

	}
}