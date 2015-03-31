package com.classical;

import java.util.LinkedList;
import java.util.List;

public class Reporter {
	
	private static Reporter instance;
	
	private List<CourseReport> reports;
	
	private Reporter() {
		reports = new LinkedList<CourseReport>();
	}
	
	public static Reporter getInstance() {
		if (instance == null) {
			instance = new Reporter();
		}
		return instance;
	}
	
	public void sendReports() {
		//TODO: use this in a chron somewhere
		for (CourseReport r : reports) {
			r.send();
			r.clear();
		}
	}
	
	public CourseReport getReport(Course course) {
		for (CourseReport r : reports) {
			if (r.getCourse().equals(course)) {
				return r;
			}
		}
		//this ensures new course reports are added
		CourseReport r = new CourseReport(course);
		reports.add(r);
		return r;
	}
	
}