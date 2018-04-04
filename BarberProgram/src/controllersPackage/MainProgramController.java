package controllersPackage;

/* Import java, javafx */
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
 
public class MainProgramController implements Initializable {
	
    @FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {
    	// Logout Alert Window
    	Alert alert = new Alert(AlertType.NONE);
    	alert.setContentText("Are you sure you want to logout?");
    	ButtonType buttonTypeOne = new ButtonType("Yes");
    	ButtonType buttonTypeTwo = new ButtonType("No");
    	
    	alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
  
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == buttonTypeOne){
    		Parent blah = FXMLLoader.load(getClass().getResource("/fxmlPackage/loginPage.fxml"));
    		Scene scene = new Scene(blah);
    		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		appStage.setScene(scene);
    		appStage.setTitle("Barber Shop Login");
    		appStage.setWidth(944);
    		appStage.setHeight(600);
    		appStage.show();
    	}else if(result.get() == buttonTypeOne) {
    	}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO - MySQL grabbing all user data should happen here and not each time a tab opens
	}

}
