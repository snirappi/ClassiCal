package com.classical;

import java.util.List;

import com.mongodb.BasicDBObject;

public class Course extends MongoDoc {
	private String courseName;
	private String crn;
	private String manager; //professor, TA, etc
	private List<User> users;
	private String location;
	private String startTime;
	private String endTime;
	private boolean[] days; //SMTWTFS
	
	public Course (String courseName, String crn, String manager, List<User> users, 
		String location, String startTime, String endTime, boolean[] days) {
		this.courseName = courseName;
		this.crn = crn;
		this.manager = manager;
		this.users = users;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;	
		this.days = days;
	}
	
	public Course (BasicDBObject o) {
		this((String)o.get("courseName"), (String)o.get("crn"), (String)o.get("manager"),
			(List<User>)o.get("users"), (String)o.get("location"), (String)o.get("startTime"),
			(String)o.get("endTime"), (boolean[])o.get("days"));
	}
	
	public void addUser(User u) {
		users.add(u);
	}
	
	public void addUser(List<User> userAdd) {
		users.addAll(userAdd);
	}
	
	public void removeUser(User u) {
		users.remove(u);
	}
	
	public void removeUser(List<User> userRemove) {
		users.removeAll(userRemove);
	}
	
	public String getCourseName() {
		return courseName;
	}

	public String getCrn() {
		return crn;
	}

	public String getManager() {
		return manager;
	}

	public List<User> getUsers() {
		return users;
	}

	public String getLocation() {
		return location;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public boolean[] getDays() {
		return days;
	}

	@Override
	public BasicDBObject toDocument() {
		return new BasicDBObject("courseName", courseName).append("crn", crn).
			append("manager", manager).append("users", users).
			append("location", location).append("startTime", startTime).
			append("endTime", endTime).append("days", days);
	}
	
	
}
