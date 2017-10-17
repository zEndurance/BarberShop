package controllersPackage;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
 
public class MainProgramController {

    @FXML
	protected void handleSubmitButtonAction(ActionEvent event) throws IOException {

    	Alert alert = new Alert(AlertType.NONE);
    	//alert.setTitle("Confirmation");
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

}
