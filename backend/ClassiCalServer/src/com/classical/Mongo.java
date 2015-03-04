package com.classical;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Mongo {
	
	private static final String HOST = "localhost";
	private static final int PORT = 27017;
	
	private static Mongo instance;
	private DB university;
	private DBCollection users;
	private DBCollection courses;
	private List<DBCollection> messages;
	
	private Mongo(String host, int port) {
		MongoClient client = null;
		try {
			client = new MongoClient(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//initialize basic collections
		university = client.getDB("purdue");
		users = university.getCollection("users");
		courses = university.getCollection("courses");
		//build list of message collections (each course will have its own collection of messages)
		messages = new LinkedList<DBCollection>();
		DBCursor cursor = courses.find();
		while (cursor.hasNext()) {
			messages.add(university.getCollection((String)cursor.next().get("crn")).getCollection("messages"));
		}
	}
	
	public static Mongo getInstance() {
		if (instance == null) {
			instance = new Mongo(HOST, PORT);
		}
		return instance;
	}
	
	//TODO: POPULATE COURSES/MESSAGES
	//TODO: SIMPLE FUNCTIONS
	
	public static void main(String args[]) {
		try {
			// To connect to mongodb server
			MongoClient mongoClient = new MongoClient("localhost", 27017);
			// Now connect to your databases
			DB db = mongoClient.getDB("test");

			System.out.println("Connect to database successfully");

			DBCollection test = db.getCollection("test");
			while (test.count() != 0) {
				test.remove(test.findOne());
			}
//			BasicDBObject doc = new BasicDBObject("title", "MongoDB").append("value", 0);
			for (int i = 0; i < 10; i++) {
				BasicDBObject doc = new BasicDBObject("title", "MongoDB").append("value", i);
				test.insert(doc);
			}
			DBCursor cursor = test.find();
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
			cursor.close();
			// boolean auth = db.authenticate(myUserName, myPassword);
			// System.out.println("Authentication: "+auth);
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}