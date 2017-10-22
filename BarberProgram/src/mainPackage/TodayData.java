package mainPackage;

public class TodayData {
	private static int staticID = 0;
	private int studentId;
	private String clientName;
	private String time;
	public STATUS status;

	public enum STATUS {
		WAITING, NOTSHOWED, CHECKIN, ARRIVED, CANCELLED
	}
	
	public TodayData(String _name, STATUS _status, String _time){
		this.clientName = _name;
		this.status = _status;
		
		
		
		for(int i=0; i<9; i++){
			
			if(_time.equals(Integer.toString(i))){
				_time = Integer.toString(i+9) + ":00-" + Integer.toString(i+10) + ":00";
			}
		}
		
		
		this.time = _time;
	}
	
	public String getTime(){
		return this.time;
	}
	
	public String getName(){
		return this.clientName;
	}
}
