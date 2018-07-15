package controllers;

/* Import java, javafx, mainPackage, enumPackage */
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import enums.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import main.Booking;
import main.TodayData;
import main.User;

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
		
		// Format the picked date to a useable one so we can search for a booking
		LocalDate localDate = datePicker.getValue();
		Date date = java.sql.Date.valueOf(localDate);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		String newDate = sdf.format(date);
		System.err.println("Selected date: " + date);
		System.out.println("useable date: " + newDate);
		
		// Search through bookings and see if we find a match with the date picked
		for(int i=0; i<User.getInstance().bookings.size(); i++){
			System.out.println(User.getInstance().bookings.get(i).toString());
			
			Booking b = User.getInstance().bookings.get(i);
			
			// TODO - bookings need to be connected to a customer profile
			
			// We have a match, so add it to the clientObersableList
			if(b.getDate().equals(newDate)){
				clientObservableList.addAll(new TodayData(b.getProfile().getFirstName(), Status.NOTSHOWED, b.getStartTime(), b.getEndTime()));
			}
		}

		// Update the list view of bookings
		listView.setItems(clientObservableList);
		listView.setCellFactory(studentListView -> new ClientListViewCell());
	}

	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Gets the value of the current date and sets it to the todaysTab date
		datePicker.setValue(NOW_LOCAL_DATE());
		loadsDatesData();
		// Sets the right side of the page to a web browser
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