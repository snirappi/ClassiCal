package com.classical;

import java.util.ArrayList;
import java.util.List;

public class Event {
	public String name;
	public User manager;
	public String location;
	public List<User> attendees;
	//implement startitme in schedule
	public boolean recurring;
	/*
	 * For constructors, add start and end time
	 */
	public Event(String n, User m, String l, boolean r) {
		name = n;
		manager = m;
		location = l;
		attendees = new ArrayList<User>();
		attendees.add(manager);
		recurring = r;
	}
	
	public Event(String n, User m, String l, List<User> a, boolean r) {
		name = n;
		manager = m;
		location = l;
		attendees = a;
		recurring = r;
	}
	
	public void join(User u) {
		attendees.add(u);
	}
	
	public User leave(User u) {
		attendees.remove(u);
		return u;
	}
	
	/*
	 * Edit() - complete suite of event editting methods
	 */
	
	public Event delete() {
		//remove event from db
		return this;
	}
}
