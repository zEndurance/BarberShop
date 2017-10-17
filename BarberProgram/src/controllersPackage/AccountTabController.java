package controllersPackage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class AccountTabController implements Initializable {
	
	
	@FXML private TextField tfEmployeeID;
	
	@FXML private TextField tfEmployeeName;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		tfEmployeeID.setText(Integer.toString(SignInController.currentID));
		tfEmployeeName.setText(SignInController.currentUser);
	}
}


