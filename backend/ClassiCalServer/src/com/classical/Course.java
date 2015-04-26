package com.classical;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class Course extends Event {
	
	private String crn;
	private String manager; //professor, TA, etc
	private String days; //SMTWRFS 0101010=-M-W-F-
	
	public Course (String courseName, String crn, String manager, String location, String startTime, String endTime, String days) {
		super(courseName, location, startTime, endTime, true);
		this.crn = crn;
		this.manager = manager;
		this.days = days;
	}
	
	public Course (BasicDBObject o) {
		this((String)o.get("courseName"), (String)o.get("crn"), (String)o.get("manager"),
			(String)o.get("location"), (String)o.get("startTime"),
			(String)o.get("endTime"), (String)o.get("days"));
//		o.toString();
	}

	public String getCrn() {
		return crn;
	}

	public String getManager() {
		return manager;
	}

	public String getDays() {
		return days;
	}
	
	public String toJson() {
		return "{\"name\":\"" + name +
			"\",\"location\":\"" + location +
			"\",\"startTime\":\"" + startTime +
			"\",\"endTime\":\"" + endTime +
			"\",\"recurring\":" + recurring + 
			"\",\"crn\":\"" + crn +
			"\",\"manager\":\"" + manager +
			"\",\"days\":" + days + "}";
	}

	@Override
	public BasicDBObject toDocument() {
		return (BasicDBObject)JSON.parse(toJson());
	}
	
}
