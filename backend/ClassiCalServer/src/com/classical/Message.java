package com.classical;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class Message extends MongoDoc {
	
	public static final int DOES_NOT_EXIST = -1;
	
	private Date date;
	private int upnotes;
	private String user;
	private List<String> upnoters;
	private String content;
	private int id = DOES_NOT_EXIST;
	private int parentId = DOES_NOT_EXIST;
	
	public Message(String user, String content, int id, int parentId) {
		upnotes = 0;
		date = new Date();
		this.user = user;
		this.content = content;
		this.parentId = parentId;
		upnoters = new ArrayList<String>();
	}
	
	public String getUser() {
		return user;
	}
	
	public String getContent() {
		return Generator.clean(content);
	}
	
	public int getId() {
		return id;
	}
	
	public boolean hasParent() {
		return parentId != DOES_NOT_EXIST;
	}
	
	public int getParentId() {
		return parentId;
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
		reporter.put("content", content);
		reporter.put("reportee", user);
		reports.save(reporter);
	}
	
	public String toJson() {
		return "{ user: " + user +
				", content: " + content +
				", id: " + id +
				", parentId: " + parentId + "}";
	}

	@Override
	public BasicDBObject toDocument() {
		return new BasicDBObject("id", id).append("parentId", parentId).append("user", user).append("content", content).append("date", date);
	}
	
}
