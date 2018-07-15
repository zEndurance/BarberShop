package main;

import java.util.Locale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * (c) zEndurance 2018
 *
 * @author Raj <raj.nry.k@gmail.com>
 */
public class Main extends Application {
	
	// ===========================================================
	// Methods
	// ===========================================================
	
    public static void main(String[] args) {
    	Locale.setDefault(Locale.UK);
        Application.launch(Main.class, args);
    }
    
    @Override
    public void start(Stage appStage) throws Exception {
    	Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginPage.fxml"));
        appStage.setTitle("Barber Shop Login");
        appStage.setScene(new Scene(root, 944, 600));
		appStage.show();
    }
}