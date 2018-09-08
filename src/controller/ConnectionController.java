package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionController {
	// Used to open connections to PHP pages
	protected HttpURLConnection connection;
	protected URL url;
	// The string that the PHP pages response with
	protected StringBuffer response;
	
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
}
