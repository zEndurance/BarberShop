package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnectionController {
	// Used to open connections to PHP pages
	protected HttpURLConnection connection;
	protected URL url;
	// The string that the PHP pages response with
	protected StringBuffer response;
	
	// JSON parsing variables
	protected JSONObject json;
	protected String query_response;
	
	protected StringBuffer connectToPage(String data) throws IOException {
		url = new URL(data);
		connection = (HttpURLConnection) url.openConnection();
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
		
		return response;
	}
	
	protected void makeJSON(StringBuffer response) throws JSONException {
		json = new JSONObject(response.toString());
		query_response = json.getString("query_result");
	}
	
	protected void debugConnection(String data) {
		// Send these values to the PHP script
		System.out.println("Connecting to page ----------> " + data);
	}
}
