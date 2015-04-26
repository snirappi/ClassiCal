package com.classical;

import com.mongodb.BasicDBObject;
import com.mongodb.util.JSON;

public class Event extends MongoDoc {
	public String name;
	public String location;
	public String startTime;
	public String endTime;
	public boolean recurring;
	
	public Event(String n, String l, String s, String e, boolean r) {
		name = n;
		location = l;
		startTime = s;
		endTime = e;
		recurring = r;
	}
	
	public String toJson() {
		return "{\"name\":\"" + name +
			"\",\"location\":\"" + location +
			"\",\"startTime\":\"" + startTime +
			"\",\"endTime\":\"" + endTime +
			"\",\"recurring\":" + recurring + "}";
	}

	@Override
	public BasicDBObject toDocument() {
		return (BasicDBObject)JSON.parse(toJson());
	}
	
}
