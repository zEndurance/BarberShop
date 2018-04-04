package mainPackage;


import controllersPackage.MainProgramController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
    

	
	
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
    
    @Override
    public void start(Stage appStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxmlPackage/loginPage.fxml"));
        
        appStage.setTitle("Barber Shop Login");
        appStage.setScene(new Scene(root, 944, 600));
		appStage.show();
    }
}
