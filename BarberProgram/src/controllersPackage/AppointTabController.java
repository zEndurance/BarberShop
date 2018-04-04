package controllersPackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
/* Import java, javafx, mainPackage */
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.JSONException;
import org.json.JSONObject;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import mainPackage.ApData;
import mainPackage.Booking;
import mainPackage.BookingRow;
import mainPackage.BusinessHours;
import mainPackage.Connection;
import mainPackage.User;

public class AppointTabController implements Initializable {
	
	
	private final String BOOKING_FILE = "bookings.csv";

	// FXML Table GUI
	@FXML
	private TableView<ObservableList<String>> appointTable;
	String headers[] = null;
	String items[] = null;

	// Table data
	List<String> columns = new ArrayList<String>();
	List<String> rows = new ArrayList<String>();
	ObservableList<ObservableList<String>> csvData = FXCollections.observableArrayList();

	private ArrayList<Booking> selectedBookings = new ArrayList<Booking>();
	private ArrayList<String> closedDays = new ArrayList<String>();

	// Custom row colour names
	private ArrayList<String> tableNames = new ArrayList<String>(); // =
																	// {"Open",
																	// "Booked",
																	// "Closed"};
	private ArrayList<String> tableColours = new ArrayList<String>(); // =
																		// {"lightgreen",
																		// "lightblue",
																		// "lightcoral"};

	@FXML
	private DatePicker appointDate;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		appointDate.setShowWeekNumbers(true);

		// Set the value of the appointDate (GUI) to the current date
		appointDate.setValue(NOW_LOCAL_DATE());

		// Read table colours
		loadCellColours();

		loadsDatesData();

		try {
			URL url = new URL(Connection.getInstance().URL_BUSINESS_HOURS);
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
				System.out.println(json.getString("query_business_hours"));
				String query_response = json.getString("query_business_hours");

				if (query_response.equals("FAILED_HOURS")) {

				} else if (query_response.equals("SUCCESS_HOURS")) {

					BusinessHours[] rgBHours = new BusinessHours[7];

					// Workout the business hours of a week
					for (int i = 0; i < 7; i++) {
						JSONObject animal = json.getJSONObject(Integer.toString(i));

						int day = animal.getInt("DayOfWeek");
						String open = animal.getString("OpenTime");
						String close = animal.getString("CloseTime");

						rgBHours[i] = new BusinessHours(day, open, close);

						System.out.println("On " + getDay(day) + " the opening hours are " + open
								+ " and closing hours are " + close);
					}

					// Now calculate the earliest opening hour and the latest
					// closing hour so we can see how many columns
					// we need to make

					String startTime = "23:30:00";
					String closeTime = "00:00:00";

					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
					Date start = simpleDateFormat.parse(startTime);
					Date end = simpleDateFormat.parse(closeTime);

					int previousOpening = 23;
					int previousClosing = 0;
					boolean addHour = false;
					for (int i = 0; i < 7; i++) {

						BusinessHours b = rgBHours[i];

						// firstly check if open and closing are the same, in
						// which case we can just ignore
						// assume same times means its a closed day
						if (!b.getOpeningHours().equals(b.getClosingHours())) {

							Date checkingOpenTime = simpleDateFormat.parse(b.getOpeningHours());
							Date checkingCloseTime = simpleDateFormat.parse(b.getClosingHours());
							// Compare dates
							if (checkingOpenTime.before(start)) {
								start = checkingOpenTime;
							}

							if (checkingCloseTime.after(end)) {
								end = checkingCloseTime;
							}

						} else {
							System.out.println("It must be closed today on a " + b.getDay());
							closedDays.add(b.getDay());
						}
					}

					System.out.println("Earliest we start the business week is: : " + simpleDateFormat.format(start));
					System.out.println("Latest we start the business week is: : " + simpleDateFormat.format(end));

					int interval = 30;

					long cols = ((Math.abs((start.getTime() - end.getTime())) / 1000) / 60) / interval;

					System.out.println("We're gunna need this much intervals: " + cols);

					buildTable(BOOKING_FILE);
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// END
		System.out.println("// END of AppointTab Initialize");
	}

	@FXML
	protected void handleDateButtonAction(ActionEvent event) throws IOException {
		loadsDatesData();

		// Update the current table data
		// Change the csvData file itself...
		changeCSV();

		// Clear everything before rebuilding the table
		csvData.clear();
		columns.clear();
		rows.clear();
		appointTable.getColumns().clear();
		appointTable.getItems().clear();

		
		// reload the table
		buildTable(BOOKING_FILE);
	}

	private void loadCellColours() {
		// Read the csv file
		File f = new File("tablecolours.csv");
		if (f.exists() && !f.isDirectory()) {
			try (FileReader fin = new FileReader(f); BufferedReader in = new BufferedReader(fin);) {
				String l;
				while ((l = in.readLine()) != null) {
					// Break up the csv (should be two values e.g:
					// Booking,lightgreen
					// brokenLine[0] = Booking
					// brokenLine[1] = lightgreen
					String[] brokenLine = l.split(",");

					// Save to the String ArrayLists to use when colouring cells
					tableNames.add(brokenLine[0]);
					tableColours.add(brokenLine[1]);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static final LocalDate NOW_LOCAL_DATE() {
		String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	private void loadsDatesData() {
		// Get the week number of a date selected
		LocalDate date = appointDate.getValue();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
		System.out.println("Selected week is: " + weekNumber);

		// Get the days of the week
		Calendar now = Calendar.getInstance();
		now.set(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth() - 1);
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		String[] days = new String[7];
		int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2; // add 2 if
																	// your week
																	// start on
																	// monday
		now.add(Calendar.DAY_OF_MONTH, delta);
		for (int i = 0; i < 7; i++) {
			days[i] = format.format(now.getTime());
			now.add(Calendar.DAY_OF_MONTH, 1);
		}
		System.out.println(Arrays.toString(days));

		// Clear selected bookings
		selectedBookings.clear();

		// Now select from Booking Data values between the dates above
		for (int i = 0; i < User.getInstance().bookings.size(); i++) {

			// TODO - think about scalability issues here !!!
			// O(n^2) seems bad?
			for (int z = 0; z < days.length; z++) {
				// Check if any of these bookings from User match the current
				// dates shown
				if (User.getInstance().bookings.get(i).getDate().equals(days[z])) {
					System.out.println("This is a matching week!");

					// Save this into 'Selected Bookings ArrayList'
					selectedBookings.add(new Booking(User.getInstance().bookings.get(i)));
				}
			}

			System.err.println(User.getInstance().bookings.get(i).getDate());
		}

		// Now Loop through the Table Cells and check the days we have bookings
		// on and the timelaps
		for (int i = 0; i < selectedBookings.size(); i++) {
			System.err.println("Selected bookings: " + selectedBookings.get(i).getDay() + " starting "
					+ selectedBookings.get(i).getStartTime());
		}

		// Top row of the csvData are the headers...
	}

	/*
	 * Helper method to return which column index this time should be in
	 */
	private int getColumnValue(String startTime) {
		for (int i = 0; i < columns.size(); i++) {
			System.out.println("Column->" + columns.get(i) + " ===? " + startTime);
			if (columns.get(i).equals(startTime)) {
				return i;
			}
		}
		return 0;
	}

	/*
	 * Rebuilds the CSV file so that we can rebuild the table data
	 */
	private void changeCSV() {
		// If we have data to change
		if (columns.size() > 0) {
			String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
			Map<String, String[]> aMap = new HashMap<String, String[]>();
			String[][] emptyRow = new String[days.length][columns.size() - 1];

			// Decide what the String array values are on each row
			for (int i = 0; i < emptyRow.length; i++) {
				for (int z = 0; z < columns.size() - 1; z++) {
					emptyRow[i][z] = "Open";
				}
			}

			// Loop through each selectedBooking
			for (int p = 0; p < selectedBookings.size(); p++) {
				for (int d = 0; d < days.length; d++) {
					// For this day
					if (selectedBookings.get(p).getDay().equals(days[d])) {
						int foundTime = getColumnValue(selectedBookings.get(p).getStartTime());
						// Update the row of that day
						emptyRow[d][foundTime] = "Booked";
					}
				}
			}

			// Update the rows that are closed
			for (int i = 0; i < days.length; i++) {
				// Loop through closed days
				for (int p = 0; p < closedDays.size(); p++) {
					if (days[i].equals(closedDays.get(p))) {
						for (int x = 0; x < columns.size() - 1; x++) {
							emptyRow[i][x] = "Closed";
						}
					}
				}
			}

			// Place each row into a hashMap
			// E.g. "Monday" -> { "Open", "Booked", ... };
			for (int i = 0; i < days.length; i++) {
				aMap.put(days[i], emptyRow[i]);
			}

			// Write a new file based off our headers!
			PrintWriter pw;
			try {
				pw = new PrintWriter(new File(BOOKING_FILE));
				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < columns.size() - 1; i++) {
					sb.append(columns.get(i));
					sb.append(',');
				}
				sb.append(columns.get(columns.size() - 1));
				sb.append('\n');

				// ------ ROW DATA ------
				// Write the days
				for (int i = 0; i < days.length; i++) {
					sb.append(days[i]);
					sb.append(',');

					String[] rowData = aMap.get(days[i]);
					// Loop through the columns
					for (int x = 1; x < columns.size() - 1; x++) {
						sb.append(rowData[x - 1]);
						sb.append(',');
					}
					sb.append(rowData[rowData.length - 1]);
					sb.append('\n');
				}

				pw.write(sb.toString());
				pw.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void buildTable(String filename) {

		// Read the csv file containing the headers and data of the table
		File f = new File(filename);

		if (f.exists() && !f.isDirectory()) {

			try (FileReader fin = new FileReader(f); BufferedReader in = new BufferedReader(fin);) {
				String l;
				int i = 0;

				while ((l = in.readLine()) != null) {

					if (i < 1) {
						headers = l.split(",");

						// Column names (first line in table.csv file)
						for (String w : headers) {
							columns.add(w);
						}

						System.out.println("Column size is now: " + columns.size());

						for (int ii = 0; ii < columns.size(); ii++) {
							final int finalIdx = ii;
							TableColumn<ObservableList<String>, String> column = new TableColumn<>(columns.get(ii));
							column.setSortable(false);
							// column.setResizable(false);

							// Set the text of the cell
							column.setCellValueFactory(
									param -> new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx)));

							// Set the colour of the cell
							column.setCellFactory(param -> {
								return new TableCell<ObservableList<String>, String>() {
									protected void updateItem(String item, boolean empty) {
										super.updateItem(item, empty);

										setText(empty ? "" : getItem().toString());
										setGraphic(null);

										if (item == null || empty) {
											setStyle("");
										} else {

											// Setting the colours based of the
											// csv file
											for (int i = 0; i < tableNames.size(); i++) {
												if (item.equals(tableNames.get(i))) {
													setStyle("-fx-background-color:" + tableColours.get(i));
												}
											}
										}
									}
								};
							});

							appointTable.getColumns().add(column);
						}

					} else {
						ObservableList<String> row = FXCollections.observableArrayList();
						row.clear();
						items = l.split(",");
						for (String item : items) {
							System.out.println(item);
							row.add(item);
						}
						csvData.add(row);
					}
					i++;
				}

				appointTable.setItems(csvData);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("File Not Found");
		}
	}

	private void createTable(long cols, Date start) {
		// Create the table columns, workout how many 30min intervals is between
		// previousOpening and previousClosing
		TableColumn[] columns = new TableColumn[(int) cols + 2];
		columns[0] = new TableColumn("             "); // Empty column

		int hour = start.getHours();
		int mins = start.getMinutes();

		boolean change = true;
		for (int i = 1; i < columns.length; i++) {
			columns[i] = new TableColumn(hour + ":" + mins + "0");

			columns[i].setCellValueFactory(new PropertyValueFactory<BookingRow, String>("values"));

			if (mins == 3) {
				mins = 0;
				hour++;
			} else {
				mins += 3;
			}
		}

		System.out.println("Column length: " + columns.length);
		appointTable.getColumns().addAll(columns);
		// Load in all the appointment data!
	}

	private String getDay(int day) {
		String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };
		return days[day];
	}

	@FXML
	private void handleClickTableView(MouseEvent click) {

		/*
		 * // Grab the person data BookingRow person =
		 * appointTable.getSelectionModel().getSelectedItem();
		 * 
		 * // Grab the column index to find values
		 * 
		 * @SuppressWarnings("rawtypes") TablePosition tp =
		 * appointTable.getFocusModel().getFocusedCell();
		 * 
		 * // Grab values after Day and Date if (tp.getColumn() >= 2 &&
		 * person.getBookingValue(tp.getColumn() - 2).equals("1")) {
		 * 
		 * // Gets data from the column selected based off index // If we
		 * clicked on the first available booking it would be the 2nd // Index
		 * in the table // But this 9am booking starts from 0 in the array
		 * stored in the // ApData object person String name =
		 * person.getName(tp.getColumn() - 2); String desc =
		 * person.getDescription(tp.getColumn() - 2); String date =
		 * person.getDate();
		 * 
		 * // Converting the time value we receive, since the value of 9am //
		 * Starts from index 0 // We add 9 and then convert the rest by adding
		 * :00 string and then // Adding 1 to 9 because // Its 9:00-10:00 int
		 * nTime = (tp.getColumn() - 2) + 9; String time =
		 * Integer.toString(nTime) + ":00-" + Integer.toString(nTime + 1) +
		 * ":00"; String contact = person.getContactInfo(tp.getColumn() - 2);
		 * String image = person.getImage(tp.getColumn() - 2);
		 * 
		 * double price = person.getBookingPrice(tp.getColumn() - 2);
		 * System.out.println("DATA FOUND FOR PRICE: " + price);
		 * 
		 * // Open new window try { FXMLLoader loader = new
		 * FXMLLoader(getClass().getResource("/fxmlPackage/infoMiniTab.fxml"));
		 * 
		 * Stage stage = new Stage(StageStyle.DECORATED); stage.setTitle(
		 * "Client Information"); stage.setScene(new Scene((Pane)
		 * loader.load()));
		 * 
		 * InfoMiniTabController controller = loader.<InfoMiniTabController>
		 * getController(); controller.initData(name, desc, date, time, contact,
		 * image, price);
		 * 
		 * stage.show(); } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * }
		 */
		// END handleClickTableView
	}
	// END
}
