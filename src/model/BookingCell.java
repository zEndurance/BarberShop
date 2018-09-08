package model;

public class BookingCell {
	
	private final String time;
	private final String date;
	private final String day;
	private final boolean useable;
	private String text;
	private Booking booking;
	
	public BookingCell(String[] rgValues, boolean use, Booking data){
		this.time = rgValues[0];
		this.date = rgValues[1];
		this.day = rgValues[2];
		this.text = rgValues[3];
		this.useable = use;
		this.booking = data;
	}
	
	public BookingCell(BookingCell b){
		this.time = b.getTime();
		this.date = b.getDate();
		this.day = b.getDay();
		this.text = b.getText();
		this.useable = b.getUseable();
		this.booking = b.getBooking();
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText(){
		return this.text.toString();
	}
	
	public String getDay(){
		return this.day.toString();
	}
	
	public String getTime() {
        return this.time.toString();
    }
	
	public String getDate(){
		return this.date.toString();
	}
	
	public Booking getBooking(){
		return this.booking;
	}

	public boolean getUseable() {
		return this.useable;
	}
}
