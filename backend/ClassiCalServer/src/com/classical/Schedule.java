package com.classical;

import java.util.LinkedList;
import java.util.List;

public class Schedule {
	
	private List<ScheduleEvent> events;
	
	public Schedule() {
		this(new LinkedList<ScheduleEvent>());
	}
	
	public Schedule(List<ScheduleEvent> events) {
		this.events = events;
	}
	
	public List<ScheduleEvent> getEvents() {
		return events;
	}
	
	public void addEvent(ScheduleEvent event) {
		events.add(event);
	}
	
	public void addEvent(Course c) {
		events.add(new ScheduleEvent(c));
	}
	
	public void clear() {
		events.clear();
	}
	
	public ScheduleEvent removeEvent(ScheduleEvent event) {
		if (events.remove(event)) {
			return event;
		}
		return null;
	}
	
	public String toJson() {
		StringBuilder builder = new StringBuilder();
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
	
}
