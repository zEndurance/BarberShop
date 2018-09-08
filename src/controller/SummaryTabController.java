package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;

/**
 * 
 * @author Oscar
 *
 */
public class SummaryTabController implements Initializable {

	// Called by the FXML loader after the labels declared above are injected:
	@FXML
	Label lblCustomers;
	@FXML
	Label lblHours;
	@FXML
	Label lblMoney;
	@FXML
	Label lbldayBest;
	@FXML
	Label lblAverageHour;
	@FXML
	Label lblExpensiveCut;
	@FXML
	BarChart<String, Double> barChart;
	@FXML
	CategoryAxis x;
	@FXML
	NumberAxis y;

	public void initialize(URL arg0, ResourceBundle arg1) {
		setupStatistics();
	}

	private void setupStatistics() {
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
	}
}