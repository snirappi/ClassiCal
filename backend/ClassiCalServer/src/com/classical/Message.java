package com.classical;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class Message extends MongoDoc {
	
	public static final int DOES_NOT_EXIST = -1;
	
	private Date date;
	private int upnotes;
	private int reports;
	private String user;
	private String title;
	private String content;
	private List<String> upnoters;
	private List<String> reporters;
	private int id = DOES_NOT_EXIST;
	private int parentId = DOES_NOT_EXIST;
	
	public Message(String user, String content, int id, int parentId) {
		this(user, "", content, id, parentId);
	}
	
	public Message(String user, String title, String content, int id, int parentId) {
		this(user, title, content, id, parentId, new Date(), 0, new LinkedList<String>());
	}

	public Message(String user, String title, String content, int id, int parentId, Date date, int upnotes, List<String> upnoters) {
		this.user = user;
		this.title = title;
		this.content = content;
		this.id = id;
		this.parentId = parentId;
		this.date = date;
		this.upnotes = upnotes;
		this.upnoters = upnoters;
	}
	
	public String getUser() {
		return user;
	}
	
	public String getTitle() {
		return Generator.clean(title);
	}
	
	public String getContent() {
		return Generator.clean(content);
	}
	
	public int getId() {
		return id;
	}
	
	public int getReports() {
		return reports;
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
	
	public void downnote(String user) {
		if (!hasUpnoted(user)) {
			upnoters.add(user);
			upnotes--;
		}
	}
	
	public boolean hasReported(String user) {
		return reporters.contains(user);
	}
	
	public void report(String user) {
		if (!hasReported(user)) {
			reporters.add(user);
			reports++;
		}
	}
	
	public String toJson() {
		StringBuilder b = new StringBuilder();
		b.append("{");
		b.append("\"user\":\"" + user +
				"\",\"title\":\"" + title +
				"\",\"content\":\"" + content +
				"\",\"id\":\"" + id +
				"\",\"parentId\":\"" + parentId + "\"");
		b.append("\", upnoters\":[");
		for (int i = 0; i < upnoters.size(); i++) {
			String u = upnoters.get(i);
			b.append("{\"username\":\"");
			b.append(u);
			b.append("\"}");
			if (i < upnoters.size() - 1) {
				b.append(",");
			}
		}
		b.append("]");
		b.append("\", reporters\":[");
		for (int i = 0; i < reporters.size(); i++) {
			String u = reporters.get(i);
			b.append("{\"username\":\"");
			b.append(u);
			b.append("\"}");
			if (i < reporters.size() - 1) {
				b.append(",");
			}
		}
		b.append("]");
		b.append("}");
		return b.toString();
	}
	
	public String toString() {
		return "User: " + user + ", Title: " + title + ", Content: \n\"" + content + "\"";
	}
	
	public static String toJson(List<Message> messages) {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"messages\":[");
		for (int i = 0; i < messages.size(); i++) {
			builder.append(messages.get(i).toJson());
			if (i < messages.size() - 1) {
				builder.append(",");
			}
		}
		builder.append("]}");
		return builder.toString();
	}

	@Override
	public BasicDBObject toDocument() {
		return (BasicDBObject) JSON.parse(toJson());
	}
	
}
