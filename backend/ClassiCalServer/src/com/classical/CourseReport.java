package com.classical;

import java.util.LinkedList;
import java.util.List;

public class CourseReport {

	public static final int SPAM = 0;
	public static final int CHEATING = 1;
	public static final String[] TYPES = {"spam", "cheating"};
	
	private Course course;
	private List<Message> reported;
	
	public CourseReport(Course course) {
		this.course = course;
		reported = new LinkedList<Message>();
	}
	
	public void report(Message message, String type) {
		if (type.equals(TYPES[CHEATING]) && !reported.contains(message)) {
			reported.add(message);
		}
	}
	
	public void send() {
		//TODO: implement email sending
	}
	
	public void clear() {
		reported.clear();
	}
	
	public Course getCourse() {
		return course;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Message m : reported) {
			builder.append(m.toString());
			builder.append("\n---\n");
		}
		return builder.toString();
	}
	
}