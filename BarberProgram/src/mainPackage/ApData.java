package mainPackage;

import javafx.beans.property.SimpleStringProperty;

public class ApData {
    private final SimpleStringProperty ID;
    private final SimpleStringProperty userID;
    private final SimpleStringProperty day;
    private final SimpleStringProperty date;
    private final SimpleStringProperty customerName;
    private final SimpleStringProperty t9;
    private final SimpleStringProperty t10;
    private final SimpleStringProperty t11;
    private final SimpleStringProperty t12;
    private final SimpleStringProperty t13;
    private final SimpleStringProperty t14;
    private final SimpleStringProperty t15;
    private final SimpleStringProperty t16;
    private final SimpleStringProperty t17;
    
    public ApData(String ID, String userID, String day, String date, String customerName, 
    		String t9, String t10, String t11, String t12, String t13, String t14, String t15, String t16, String t17) {
    	
        this.ID = new SimpleStringProperty(ID);
        this.userID = new SimpleStringProperty(userID);
        this.day = new SimpleStringProperty(day);
        this.date = new SimpleStringProperty(date);
        this.customerName = new SimpleStringProperty(customerName);
        
        this.t9 = new SimpleStringProperty(t9);
        this.t10 = new SimpleStringProperty(t10);
        this.t11 = new SimpleStringProperty(t11);
        this.t12 = new SimpleStringProperty(t12);
        this.t13 = new SimpleStringProperty(t13);
        this.t14 = new SimpleStringProperty(t14);
        this.t15 = new SimpleStringProperty(t15);
        this.t16 = new SimpleStringProperty(t16);
        this.t17 = new SimpleStringProperty(t17);
    }
 
    public String getID() {
        return ID.get();
    }
    public void setFirstName(String fName) {
        ID.set(fName);
    }
        
    public String getUserID() {
        return userID.get();
    }
    public void setUserID(String fName) {
        userID.set(fName);
    }
    
    public String getDay() {
        return day.get();
    }
    public void setDay(String fName) {
        day.set(fName);
    }
    
    public String getDate() {
        return date.get();
    }
    public void setDate(String fName) {
        date.set(fName);
    }
    
    public String getCustomerName() {
        return customerName.get();
    }
    public void setCustomerName(String fName) {
        customerName.set(fName);
    }
    
    // Part that needs changing/refactoring (THIS IS HORRIBLE)
    
    // 9
    
    public String getT9() {
        return t9.get();
    }
    public void setT9(String t) {
        t9.set(t);
    }
    
    // 10
    
    public String getT10() {
        return t10.get();
    }
    public void setT10(String t) {
        t10.set(t);
    }
    
    // 11
    
    public String getT11() {
        return t11.get();
    }
    public void setT11(String t) {
        t11.set(t);
    }
    
    // 12
    
    public String getT12() {
        return t12.get();
    }
    public void setT12(String t) {
        t12.set(t);
    }
    
    // 13
    
    public String getT13() {
        return t13.get();
    }
    public void setT13(String t) {
        t13.set(t);
    }
    
    // 14
    
    public String getT14() {
        return t14.get();
    }
    public void setT14(String t) {
        t14.set(t);
    }
    
    
    // 15
    public String getT15() {
        return t15.get();
    }
    public void setT15(String t) {
        t15.set(t);
    }
    
    // 16
    public String getT16() {
        return t16.get();
    }
    public void setT16(String t) {
        t16.set(t);
    }
    
    // 17
    public String getT17() {
        return t17.get();
    }
    public void setT17(String t) {
        t17.set(t);
    }
}