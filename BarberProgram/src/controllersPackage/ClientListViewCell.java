package controllersPackage;

/* Import java, enumPackage, javafx, mainPackage */
import java.io.IOException;
import enumPackage.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import mainPackage.TodayData;

public class ClientListViewCell extends ListCell<TodayData>  {
	@FXML
    private Label label1;
	@FXML
	private Label labelTime;
	@FXML
	private ChoiceBox<String> cBox;
    @FXML
    private GridPane gridPane;
    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(TodayData student, boolean empty) {
        super.updateItem(student, empty);
        
        // Empty entry 
        if(empty || student == null) {
            setText(null);
            setGraphic(null);
        } else {
        	// Custom load FXML
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxmlPackage/listViewItem.fxml"));
                mLLoader.setController(this);
                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            // Values of labels/javafx items
            labelTime.setText(student.getStartTime() + "-" + student.getEndTime());
            label1.setText(student.getName());
            
            ObservableList<String> data = FXCollections.observableArrayList();
            
            data.addAll(Status.CHECKIN.toString(), Status.WAITING.toString(), Status.NOTSHOWED.toString(), Status.CANCELLED.toString(), Status.ARRIVED.toString());
            
            // Inserting data into the ChoiceBox
            cBox.setItems(data);
            
            setText(null);
            setGraphic(gridPane);
        }
    }
}
