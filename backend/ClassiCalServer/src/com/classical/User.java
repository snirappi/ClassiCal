package com.classical;

import java.util.LinkedList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class User extends MongoDoc {
	
	private String username;
	private String name;
	/** This schedule will be built off of a user's registered classes and refreshed on request.*/
	private List<Event> events;
	private List<Course> courses;
	
	public User(String username, String name) {
		this(username, name, new LinkedList<Event>(), new LinkedList<Course>());
	}
	
	public User(String username, String name, List<Event> events, List<Course> courses) {
		this.username = username;
		this.name = name;
		this.events = events;
		this.courses = courses;
	}
	
	public void ban() {
		
	}
	
	public void addEvent(Event e) {
		events.add(e);
	}
	
	public void addEvents(List<Event> es) {
		for (Event e : es) {
			addEvent(e);
		}
	}
	
	public void removeEvent(Event e) {
		events.remove(e);
	}
	
	public String eventsToJson() {
		StringBuilder builder = new StringBuilder();
		builder.append("\"events\":[");
		for (int i = 0; i < events.size(); i++) {
			builder.append(events.get(i).toJson());
			if (i < events.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}
	
	public String coursesToJson() {
		StringBuilder builder = new StringBuilder();
		builder.append("\"courses\":[");
		for (int i = 0; i < courses.size(); i++) {
			builder.append(courses.get(i).toJson());
			if (i < courses.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		return builder.toString();
	}
	
	public String toJson() {
		return "{\"_id\":\"" + username +
			"\",\"username\":\"" + username +
			"\",\"name\":\"" + name + "\"," +
			eventsToJson() + "," +
			coursesToJson() + "}";
	}

	@Override
	public BasicDBObject toDocument() {
		return (BasicDBObject)JSON.parse(toJson());
	}
}
