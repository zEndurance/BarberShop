package controller;

import java.io.BufferedReader;
/* Import java, javafx */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Booking;
import model.BookingCell;
import model.Connection;
import model.GUI;
import model.User;

public class InfoMiniTabController extends ConnectionController implements Initializable {
	
	// FXML - GUI Components
	@FXML Button btnOK;
	@FXML TextArea textArea;
	@FXML Label labelDate;
	@FXML Label labelTime;
	@FXML Label labelName;
	@FXML Label labelContact;
	@FXML Label labelPrice;
	@FXML Label labelService;
	@FXML ImageView imageView;
	
	
	private Booking internalBooking;
	private BookingCell referenceCell;
	private AppointTabController appointTabController;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	
	public void setATC(AppointTabController atc) {
		this.appointTabController = atc;
	}
	
	public AppointTabController getATC() {
		return this.appointTabController;
	}

	public void initData(BookingCell data) throws FileNotFoundException {
		
		
		// Keep reference to the cell
		referenceCell = data;
		
		// Set the current booking
		internalBooking = data.getBooking();
		
		labelName.setText("Client name: " + data.getBooking().getProfile().getName());
		labelDate.setText("Date: " + data.getBooking().getDate());
		labelTime.setText("Time: " + data.getBooking().getStartTime());
		labelContact.setText("Contact Info: " + data.getBooking().getProfile().getMobileNumber());
		
		labelService.setText("Service: " + data.getBooking().getService().getService());
		
		// Format this double
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		labelPrice.setText("Price: " + data.getBooking().getPrice());
		
		
		
		/*
		// Add an image
		if(!image.equals("-1")) {
			System.out.println("Creating image.. at->http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/w3images/" + image);
	        Image imageA = new Image("http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/w3images/" + image);
			imageView.setImage(imageA);
		}
		*/
	}
	
	
	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
		System.out.println("Close this window");
		Stage stage = (Stage) btnOK.getScene().getWindow();
	    stage.close();
	}
	
	private boolean deleteBooking(int id) {

		boolean deleted = false;
		String data = Connection.URL_DELETE_BOOKING;

		try {
			// Build the parameters
			StringBuffer paramsBuilder = new StringBuffer();
			paramsBuilder.append("id=" + id);
			paramsBuilder.append("&email=" + User.getInstance().email);
			paramsBuilder.append("&password=" + User.getInstance().password);
			System.out.println(paramsBuilder.toString());
			
			// Send parameters to web page
			response = connectToPagePost(data, paramsBuilder);
			
			// Read it in JSON
			try {
				makeJSON(response);
				if (query_response.equals("FAILED_DELETE_BOOKING")) {
					deleted = false;
				} else if (query_response.equals("SUCCESSFUL_DELETE_BOOKING")) {
					// TODO - Delete booking parse
					System.out.println("We can successfully delete this from the table!!!");
					// Check if the date has expired so we change it to a grey
					// if the date hasn't expired change to green open
					deleted = true;
				} else {
					System.out.println("Not enough arguments were entered.. try filling both fields");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return deleted;
	}
	
	
	
	
	/**
	 * Trigger a php script that will remove it from the database (just like how we update passwords)
	 * make sure we pass username and password too and confirm it is a legit remove otherwise the script could be ran from 3rd party
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	protected void handleRemoveButtonAction(ActionEvent event) throws IOException {
		System.out.println("Ask if they are sure about removing the appointment completely");
		
		
		// Function that will run when we press yes in the dialogue
		Callable<Boolean> pressedYes = new Callable<Boolean>() {
		    @Override
		    public Boolean call() throws Exception {
		    	
		    	System.out.println("We pressed yes");
		    	
		    	System.out.println("Booking to remove is --> " + internalBooking.getID());
		    	
		    	System.out.println("User that wants to remove is --> " + User.getInstance().email +  " password --> " + User.getInstance().password);
		    	 
		    	// Pass the current username and password to the script and the details of the booking that is to be removed here
		    	
		    	// if we can delete this booking
		    	if(deleteBooking(internalBooking.getID())) {
		    		
		    		System.out.println("Update parent load function here");
		    		
		    		getATC().load();
		    	}
		    	
		    	// Return true if successful
				return true;
		    }
		};
		
		
		// Ask to remove 
		GUI.createDialog("Are you sure? Think carefully, this cannot be reversed!", new String[]{"Yes", "No"}, new Callable[] {pressedYes, null});
	}
	
	@FXML
	protected void handleEditButtonAction(ActionEvent event) throws IOException {
		System.out.println("Ask about handle edits");
		
		// Open a new window that allows us to change booking name, time, service manually 
		
		// Then send all the changes to a script which updates the database
	}
	
}
