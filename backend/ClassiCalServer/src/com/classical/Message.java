package com.classical;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class Message {
	private Date date;
	private int upnotes;
	private String user;
	private List<String> upnoters;
	private String content;
	private Message parent;
	
	public Message(DBCollection messages, String user, String content, Message parent) {
		upnotes = 0;
		date = new Date();
		this.user = user;
		this.content = content;
		this.parent = parent;
		upnoters = new ArrayList<String>();
		BasicDBObject time = new BasicDBObject("ts", date);
		messages.save(time);
	}
	
	public String getUser() {
		return user;
	}
	
	public String getContent() {
		return Generator.clean(content);
	}
	
	public Message getParent() {
		return parent;
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getUpnotes() {
		return upnotes;
	}
	
	public boolean hasUpnoted(String user) {
		return upnoters.contains(user);
	}
	
	public void upnote(String user) {
		if (!hasUpnoted(user)) {
			upnoters.add(user);
			upnotes++;
		}
	}
	
	public void report(DBCollection reports, String whistleblower) {
		BasicDBObject reporter = new BasicDBObject("wb", whistleblower);
		
		upnotes++;
	}
	
	public String toJson() {
		return "";
	}
	
}
