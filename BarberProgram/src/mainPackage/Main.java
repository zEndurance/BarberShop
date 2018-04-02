package mainPackage;


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
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxmlPackage/loginPage.fxml"));
        
        stage.setTitle("Barber Shop Login");
        stage.setScene(new Scene(root, 944, 600));
        stage.show();
    }
}
