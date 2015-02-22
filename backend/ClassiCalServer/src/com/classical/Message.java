package com.classical;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class Message {
	private Date date;
	private int upNotes;
	private String user;
	private String content;
	
	public Message(DBCollection messages, String u, String s) {
		upNotes = 0;
		date = new Date();
		user = u;
		content = s;
		BasicDBObject time = new BasicDBObject("ts", date);
		messages.save(time);		
	}
	
	public void report(DBCollection reports, String whistleblower) {
		BasicDBObject reporter = new BasicDBObject("wb", whistleblower);
		
		upNotes++;
	}
}
