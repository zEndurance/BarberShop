import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

    	
        primaryStage.setTitle("Barber Program");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.WHITE);

        // The tab pane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        
        
        // layout
        BorderPane borderPane = new BorderPane();
        
        
        // Create pages
        ArrayList<Page> pages = new ArrayList<Page>();
        
        System.out.println("Test");
        
        
        pages.add(new Summary("Summary"));
        pages.add(new Appointments("Appointments"));
        
        
        // Make the tabs equal to pages
        for (int i = 0; i < pages.size(); i++) {
        	
            //Tab tab = new Tab();
            
            
            //
            
            HBox hbox = new HBox();
            
            //hbox.getChildren().add(new Label("Tab" + i));
            
            
            hbox.setAlignment(Pos.CENTER);
            pages.get(i).setContent(hbox);
            tabPane.getTabs().add(pages.get(i));
        }
        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());
        
        borderPane.setCenter(tabPane);
        root.getChildren().add(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
