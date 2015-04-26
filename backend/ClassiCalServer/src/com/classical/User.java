package com.classical;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class User extends MongoDoc {
	
	private String username;
	private String name;
	/** This schedule will be built off of a user's registered classes and refreshed on request.*/
	private Schedule schedule;
	
	public User(String username, String name, Schedule schedule, Schedule otherSchedule) {
		this.username = username;
		this.name = name;
		this.schedule = schedule;
	}
	
	public void ban() {
		
	}
	
	public void addEvent(Event e) {
		schedule.addEvent(e);
	}
	
	public void addEvents(List<Event> es) {
		for (Event e : es) {
			addEvent(e);
		}
	}
	
	public void removeEvent(Event e) {
		schedule.removeEvent(e);
	}
	
	public String toJson() {
		return "{\"username\":\"" + username +
			"\",\"name\":\"" + name + "," +
			schedule.toJson(false) + "}";
	}

	@Override
	public BasicDBObject toDocument() {
		return (BasicDBObject)JSON.parse(toJson());
	}
}
