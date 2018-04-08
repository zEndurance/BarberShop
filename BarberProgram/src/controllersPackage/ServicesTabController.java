package controllersPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mainPackage.Connection;
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
	@FXML
	private TextField tfPrice;

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

	@FXML
	protected void handleSubmitService(ActionEvent event) throws IOException {

		boolean valid = true;

		// Validate that data was selected/entered (service name, price)
		if (serviceChoiceBox.getValue() == null || tfPrice.getText().equals("")) {
			valid = false;
		} else {
			// Both have been filled

			// Check if this service already exists in the table
			for (int i = 0; i < User.getInstance().services.size(); i++) {
				if (serviceChoiceBox.getValue().equals(User.getInstance().services.get(i).getService())) {
					System.out.println("This service already exists! ");
					valid = false;
					break;
				}
			}

			// Check if we can format the price
			try {
				double price = Double.parseDouble(tfPrice.getText());
			} catch (NumberFormatException e) {
				valid = false;
			}
		}

		if (valid) {

			String service = serviceChoiceBox.getValue();
			String price = tfPrice.getText();

			// Change it on the database via script, get the ID value back then
			// update the GUI!
			String lastID = insertService(service, price);

			if (!lastID.equals("-1")) {
				String[] args = new String[3];
				args[0] = lastID; // ID value would be an increment from the
									// last id (on mysql database)
				args[1] = service;
				args[2] = price;
				// Validate that we can enter this into the table
				User.getInstance().services.add(new Service(args));

				serviceTable.getItems().clear();
				serviceTable.getItems().addAll(User.getInstance().services);
			} else {
				// TODO - Display error message to the user that we couldn't
				// insert onto the database
			}
		}

		System.out.println("Can we enter in this table? " + valid);
	}

	/**
	 * Sends data to a php script that will attempt to INSERT to a MySQL
	 * database
	 * 
	 * @param service
	 *            the name of the service
	 * @param price
	 *            the price of the service with decimals
	 * @return 
	 * 			  the last service id entered onto the database
	 */
	private String insertService(String service, String price) {
		// Assume we couldn't insert this
		String retStr = "-1";

		// Send values to a php script, get the json value back, return the json
		// value
		String data = Connection.URL_INSERT_SERVICE;

		try {
			data += "?barber_id=" + User.getInstance().id;
			data += "&service=" + URLEncoder.encode(service, "UTF-8");
			data += "&price=" + price;
			data += "&shop_id=" + User.getInstance().shop_id;
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		// Try connecting to the php script and passing the values above to it
		try {
			URL url = new URL(data);
			URLEncoder.encode(data, "UTF-8");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");

			// Read the JSON output here
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Try reading it in JSON format
			try {
				JSONObject json = new JSONObject(response.toString());
				System.out.println(json.getString("query_result"));
				String query_response = json.getString("query_result");

				if (query_response.equals("FAILED_INSERT_SERVICE")) {
					// Give a response to the user that its incorrect
					retStr = "-1";
				} else if (query_response.equals("SUCCESSFUL_INSERT_SERVICE")) {
					retStr = Integer.toString(json.getInt("last_id"));
				} else {
					System.out.println("Not enough arguments were entered.. try filling both fields");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return retStr;
	}
}
