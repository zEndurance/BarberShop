package controllersPackage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import mainPackage.TodayData;

public class TodaysTabController implements Initializable {
	@FXML
    private ListView<TodayData> listView;

    private ObservableList<TodayData> clientObservableList;

	
	public TodaysTabController(){
		// Construct data
		
		clientObservableList = FXCollections.observableArrayList();

        //add some Students
        clientObservableList.addAll(
                new TodayData("John Doe", TodayData.STATUS.NOTSHOWED),
                new TodayData("Jane Doe", TodayData.STATUS.CANCELLED),
                new TodayData("Donte Dunigan", TodayData.STATUS.ARRIVED),
                new TodayData("Gavin Genna", TodayData.STATUS.CHECKIN),
                new TodayData("Darin Dear", TodayData.STATUS.CHECKIN),
                new TodayData("Pura Petty", TodayData.STATUS.CANCELLED),
                new TodayData("Herma Hines", TodayData.STATUS.WAITING)
        );
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		listView.setItems(clientObservableList);
		
		listView.setCellFactory(studentListView -> new ClientListViewCell());
		   
		   /*
		listView.setCellFactory(new Callback<ListView<TodayData>, ListCell<TodayData>>() {  
		    @Override
		    public ListCell<TodayData> call(ListView<TodayData> studentListView) {
		        return new ClientListViewCell();
		    }
		});*/
	}
}
