package mainPackage;

public class TodayData {
	private static int staticID = 0;
	private int studentId;
	private String clientName;
	public STATUS status;

	public enum STATUS {
		WAITING, NOTSHOWED, CHECKIN, ARRIVED, CANCELLED
	}
	
	public TodayData(String _name, STATUS _status){
		this.clientName = _name;
		this.status = _status;
	}
	
	public String getName(){
		return this.clientName;
	}
}
