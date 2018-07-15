package main;

import enums.Status;

public class TodayData {
	private String clientName;
	private String startTime;
	private String endTime;
	public Status status;

	public TodayData(String _name, Status _status, String _startTime, String _endTime) {
		this.clientName = _name;
		this.status = _status;
		
		this.startTime = _startTime;
		this.endTime = _endTime;
	}

	public String getStartTime() {
		return this.startTime;
	}
	
	public String getEndTime(){
		return this.endTime;
	}

	public String getName() {
		return this.clientName;
	}
}
