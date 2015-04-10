package com.classical;

public class ScheduleEvent {
	
	public String name;
	public String location;
	public String startTime;
	public String endTime;
	
	public ScheduleEvent(Course c) {
		this(c.getCourseName(), c.getLocation(), c.getStartTime(), c.getEndTime());
	}
	
	public ScheduleEvent(String name, String location, String startTime, String endTime) {
		this.name = name;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;	
	}
	
	public String toJson() {
		return "{\"name\":\"" + name +
			"\",\"location\":\"" + location +
			"\",\"startTime\":\"" + startTime +
			"\",\"endTime\":\"" + endTime + "\"}";
	}
	
}