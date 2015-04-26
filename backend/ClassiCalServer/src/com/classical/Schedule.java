package com.classical;

import java.util.LinkedList;
import java.util.List;

public class Schedule {
	
	private List<Event> events;
	
	public Schedule() {
		this(new LinkedList<Event>());
	}
	
	public Schedule(List<Event> events) {
		this.events = events;
	}
	
	public List<Event> getEvents() {
		return events;
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void addEvents(List<Event> events) {
		events.addAll(events);
	}
	
	public void clear() {
		events.clear();
	}
	
	public Event removeEvent(Event event) {
		if (events.remove(event)) {
			return event;
		}
		return null;
	}
	
	public String toJson(boolean single) {
		StringBuilder builder = new StringBuilder();
		if (single) builder.append("{");
		builder.append("\"events\":[");
		for (int i = 0; i < events.size(); i++) {
			builder.append(events.get(i).toJson());
			if (i < events.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]");
		if (single) builder.append("}");
		return builder.toString();
	}
	
}
