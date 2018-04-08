package controllersPackage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mainPackage.Service;
import mainPackage.User;

public class ServicesTabController implements Initializable {

	@FXML
	private TableView<Service> serviceTable;
	@FXML
	private TableColumn<Service, String> serviceID;
	@FXML
	private TableColumn<Service, String> serviceName;
	@FXML
	private TableColumn<Service, String> servicePrice;
	@FXML
	private ChoiceBox<String> serviceChoiceBox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Load up the table with the current users services
		int len = User.getInstance().services.size();

		// We can do something with the table now since we have services!
		if (len > 0) {

			serviceID.setCellValueFactory(new PropertyValueFactory<Service, String>("Id"));
			serviceName.setCellValueFactory(new PropertyValueFactory<Service, String>("Service"));
			servicePrice.setCellValueFactory(new PropertyValueFactory<Service, String>("Price"));

			serviceTable.getItems().setAll(User.getInstance().services);
		}
		
		List<String> cities = User.getInstance().getServices();
		ObservableList<String> list = FXCollections.observableArrayList(cities);
		serviceChoiceBox.setItems(list);
	}
}
