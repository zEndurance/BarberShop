package mainPackage;

import java.util.Optional;
import java.util.concurrent.Callable;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class GUI {
	/**
	 * Creates a GUI dialog
	 * 
	 * @param msg
	 *            the message we want to display
	 * @param rgNames
	 *            the names of the buttons to display
	 * @param rgFunc
	 *            the functions each button calls
	 */
	public static void createDialog(String msg, String[] rgNames, Callable[] rgFunc) {
		// Logout Alert Window
		Alert alert = new Alert(AlertType.NONE);
		alert.setContentText(msg);

		int len = rgNames.length;
		ButtonType[] btns = new ButtonType[len];

		// used to check if we passed any functions to this dialog
		boolean nofunc = (rgFunc == null) ? true : false;

		// Setup button names
		for (int i = 0; i < len; i++) {
			btns[i] = new ButtonType(rgNames[i]);
		}

		alert.getButtonTypes().setAll(btns);
		Optional<ButtonType> result = alert.showAndWait();

		// Loop through the buttons, and check if we placed a function call on
		// them
		for (int i = 0; i < len; i++) {
			if (result.get() == btns[i]) {
				if (!nofunc && rgFunc[i] != null) {
					try {
						rgFunc[i].call();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
