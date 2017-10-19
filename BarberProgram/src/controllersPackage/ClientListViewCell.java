package controllersPackage;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;
import mainPackage.TodayData;
import mainPackage.TodayData.STATUS;

public class ClientListViewCell extends ListCell<TodayData>  {
	@FXML
    private Label label1;
	
	@FXML
	private ChoiceBox cBox;

    //@FXML private Label label2;

   // @FXML private FontAwesomeIconView fxIconGender;

    @FXML
    private GridPane gridPane;

    private FXMLLoader mLLoader;

    @Override
    protected void updateItem(TodayData student, boolean empty) {
        super.updateItem(student, empty);

        if(empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {
            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/fxmlPackage/listViewItem.fxml"));
                mLLoader.setController(this);

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            label1.setText(String.valueOf(student.getName()));
            //label2.setText(student.getName());

            ObservableList<String> data = FXCollections.observableArrayList();
            
            data.addAll("Checked In", "Waiting", "Not Showed", "Canceled");

            cBox.setItems(data); // Inserting data into the ChoiceBox
            
            //cBox.setItems(value);
            /*
            if(student.getGender().equals(Student.GENDER.MALE)) {
                fxIconGender.setIcon(FontAwesomeIcon.MARS);
            } else if(student.getGender().equals(Student.GENDER.FEMALE)) {
                fxIconGender.setIcon(FontAwesomeIcon.VENUS);
            } else {
                fxIconGender.setIcon(FontAwesomeIcon.GENDERLESS);
            }*/

            setText(null);
            setGraphic(gridPane);
        }
    }
}
