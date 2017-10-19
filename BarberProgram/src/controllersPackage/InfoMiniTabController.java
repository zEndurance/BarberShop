package controllersPackage;

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

public class InfoMiniTabController implements Initializable {
	@FXML private Button btnOK;
	@FXML private TextArea textArea;
	
	@FXML private Label labelDate;
	@FXML private Label labelTime;
	@FXML private Label labelName;
	@FXML private Label labelContact;
	@FXML private Label labelPrice;
	
	@FXML private ImageView imageView;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	
	}


	public void initData(String name, String desc, String date, String time, String contact, String image, double price) throws FileNotFoundException {
		// TODO Auto-generated method stub
		labelName.setText("Client name: " + name);
		textArea.setText("Client notes: " + desc);
		labelDate.setText("Date: " + date);
		labelTime.setText("Time: " + time);
		labelContact.setText("Contact Info: " + contact);
		
		// Format this double
		NumberFormat currency=NumberFormat.getCurrencyInstance();
		labelPrice.setText("Price: " + currency.format(price));
		
		// add an image
		if(!image.equals("-1")) {
			
			System.out.println("Creating image.. at->http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/w3images/" + image);
	        
	        
	        Image imageA = new Image("http://webprojects.eecs.qmul.ac.uk/rk308/barbershop/w3images/" + image);
			imageView.setImage(imageA);
	        
	        
	        
	        
		}
		
		
	}
	
	
	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
		
		System.out.println("Close this window");
		
		Stage stage = (Stage) btnOK.getScene().getWindow();
	    stage.close();
	}
	
}
