package com.classical;

import java.util.List;

import com.mongodb.BasicDBObject;

public class User extends MongoDoc {
	String username;
	//String PUID;	do we need puid?
	String name;
	int id = 0;
	/** This schedule will be built off of a user's registered classes and refreshed on request.
	 * 	This will also determine what course communities a user is a part of. */
	private Schedule classSchedule;
	/** This schedule will be maintained by the user for other events. */
	private Schedule otherSchedule;
	
	public User(String username, String name, Schedule classSchedule, Schedule otherSchedule) {
		this.username = username;
		this.name = name;
		this.classSchedule = classSchedule;
		this.otherSchedule = otherSchedule;
	}
	
	public void ban() {
		
	}
	
	public void emptyCourses() {
		classSchedule.clear();
	}
	
	public void emptyEvents() {
		otherSchedule.clear();
	}
	
	public void register(Course c) {
		classSchedule.addEvent(c);
		c.addUser(this);
	}
	
	public void addEvent(ScheduleEvent e) {
		otherSchedule.addEvent(e);
	}
	
	public String scheduleToJson() {
		StringBuilder builder = new StringBuilder();
		List<ScheduleEvent> events = classSchedule.getEvents();
		events.addAll(otherSchedule.getEvents());
		builder.append("{\"events\":[");
		for (int i = 0; i < events.size(); i++) {
			builder.append(events.get(i).toJson());
			if (i < events.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]}");
		return builder.toString();
	}

	@Override
	public BasicDBObject toDocument() {
		return new BasicDBObject("id", id).append("username", username).append("name", name);
	}
}
