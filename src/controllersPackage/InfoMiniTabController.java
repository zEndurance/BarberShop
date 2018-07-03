package controllersPackage;

/* Import java, javafx */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mainPackage.BookingCell;

public class InfoMiniTabController implements Initializable {
	
	@FXML private Button btnOK;
	@FXML private TextArea textArea;
	@FXML private Label labelDate;
	@FXML private Label labelTime;
	@FXML private Label labelName;
	@FXML private Label labelContact;
	@FXML private Label labelPrice;
	@FXML private Label labelService;
	@FXML private ImageView imageView;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}

	public void initData(BookingCell data) throws FileNotFoundException {
		labelName.setText("Client name: " + data.getBooking().getProfile().getName());
		//textArea.setText("Client notes: " + desc);
		labelDate.setText("Date: " + data.getBooking().getDate());
		labelTime.setText("Time: " + data.getBooking().getStartTime());
		labelContact.setText("Contact Info: " + data.getBooking().getProfile().getMobileNumber());
		
		labelService.setText("Service: " + data.getBooking().getService().getService());
		
		// Format this double
		NumberFormat currency=NumberFormat.getCurrencyInstance();
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
	
	@FXML
	protected void handleRemoveButtonAction(ActionEvent event) throws IOException {
		System.out.println("Ask if they are sure about removing the appointment completely");
		
		// Ask to remove 
		
		// Trigger a php script that will remove it from the database (just like how we update passwords)
		
		// make sure we pass username and password too and confirm it is a legit remove otherwise the script could be ran from 3rd party
	}
	
	@FXML
	protected void handleEditButtonAction(ActionEvent event) throws IOException {
		System.out.println("Ask about handle edits");
		
		// Open a new window that allows us to change booking name, time, service manually 
		
		// Then send all the changes to a script which updates the database
	}
	
}
