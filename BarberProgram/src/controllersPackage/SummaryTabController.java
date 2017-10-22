package controllersPackage;
/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;
import mainPackage.ApData;
import mainPackage.Main;

import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.chart.*;



public class SummaryTabController implements Initializable {
	// Collects ApData objects as each MySQL row
	public ObservableList<ApData> data = FXCollections.observableArrayList();
	
	// called by the FXML loader after the labels declared above are injected:
	
	@FXML private Label lblCustomers;
	@FXML private Label lblHours;
	@FXML private Label lblMoney;
	@FXML private Label lbldayBest;
	@FXML private Label lblAverageHour;
	@FXML private Label lblExpensiveCut;
	
	@FXML private BarChart<String, Double> barChart;
	@FXML private CategoryAxis x;
	@FXML private NumberAxis y;
	
	public void initialize(URL arg0, ResourceBundle arg1) {		
		// TODO Auto-generated method stub
		int  amountCustomers = 0;
		int loop = 0;
		int  amountHours = 0;
		double  amountMoney = 0;
		double mostDaily = 0;
		String dayBest = "";
		String mostExpensive = "";
		
		//daily earnings for the bar chart 
		double monEarn = 0;
		double TueEarn = 0;
		double WedEarn = 0;
		double ThurEarn = 0;
		double FriEarn = 0;
		double SatEarn = 0;
		double sunEarn = 0;
		String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
		Double[] earnings = {monEarn, TueEarn, WedEarn, ThurEarn, FriEarn, SatEarn, sunEarn};
		
		String url = "jdbc:mysql://sql2.freesqldatabase.com:3306/sql2199713";
		String username = "sql2199713";
		String password = "nW7*wP8!";
		try (Connection connection = DriverManager.getConnection(url, username, password)) {

			// Create a statement
			Statement myStmt = connection.createStatement();

			// Execute a statement
			ResultSet myRs = myStmt.executeQuery(
					"SELECT * FROM appointments WHERE userID ='" + Main.currentID + "'");
			
			while (myRs.next()) {
				// Grab the data from the table 
				double dayMoneyTotal = 0;
				double mostSpent = 0;
				String ID 		= myRs.getString("ID");
				String userID 	= myRs.getString("userID");
				String day 		= myRs.getString("Day");
				String date	 	= myRs.getString("Date");
				String t9 		= myRs.getString("t9");
				String t10 		= myRs.getString("t10");
				String t11 		= myRs.getString("t11");
				String t12 		= myRs.getString("t12");
				String t13 		= myRs.getString("t13");
				String t14 		= myRs.getString("t14");
				String t15 		= myRs.getString("t15");
				String t16 		= myRs.getString("t16");
				String t17 		= myRs.getString("t17");
				String[] variables = {t9, t10, t11, t12, t13, t14, t15, t16, t17};
				
				//get earnings for the bar chart
				for(int i=0; i<variables.length ; i++) {
					String[] splitStr = variables[i].split(", ");
					if(splitStr.length > 1) {
						//working out daily earnings
						if(splitStr[1].length() > 2) {
							earnings[loop] = Double.parseDouble(splitStr[5]);
						}
					}
				}
				loop ++; //increments from 0 for x amount of loops
				
				//check if the row contains data
				for(int i=0; i<variables.length ; i++) {
					String[] splitStr =  variables[i].split(", ");
					if(splitStr.length > 1) {
						if(splitStr[1].length() > 2) {
							amountCustomers ++; // overall customers
							amountHours ++; // overall hours
							amountMoney += Double.parseDouble(splitStr[5]); // overall money
							dayMoneyTotal += Double.parseDouble(splitStr[5]); // daily money
						}
					}
				}
				
				//check for certain row data
				for(int j=0; j<variables.length; j++) {
					String[] splitStr =  variables[j].split(", ");
						if(splitStr.length > 1)
							if(Double.parseDouble(splitStr[5]) > mostSpent) { 
								//searches for the most expensive type of cut that has been appointed.
								mostSpent = Double.parseDouble(splitStr[5]);
								mostExpensive = splitStr[2];
							}
						//searches for the day with most money earned
						if(dayMoneyTotal>mostDaily) {
							mostDaily =  amountMoney;
							dayBest = day;
						}
				} // end of for loop
			} // end of while loop
			
			XYChart.Series<String, Double> barData = new XYChart.Series<>(); // arraylist of data for the chart
			//debugging for daily earnings array
			for(int i=0; i<earnings.length ; i++) {
				barData.getData().add(new XYChart.Data<>(days[i], earnings[i])); // add earnings to the bar chart
				System.out.println(earnings[i]);
			}
			barChart.getData().add(barData);

			//Initializing labels 
			DecimalFormat df = new DecimalFormat("####0.00");
			lbldayBest.setText(dayBest);
			
			lblMoney.setText("£" + df.format(amountMoney));
			lblCustomers.setText(Integer.toString(amountCustomers));
			lblHours.setText(Integer.toString(amountHours));
			double average = amountMoney / amountHours;
			lblAverageHour.setText("£" + df.format(average));
			lblExpensiveCut.setText(mostExpensive);
			
			
		}catch (SQLException e) {
			System.out.println("ERROR IN SQL");
			e.printStackTrace();
		}
		
	}
}