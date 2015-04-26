package com.classical;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Mongo {
	
	private static final String HOST = "localhost";
	private static final int PORT = 27017;
	
	private static Mongo instance;
	private DB university;
	private DBCollection users;
	private DBCollection courses;
	private DBCollection forumMessages;
	private DBCollection chatMessages;
	
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
		forumMessages = university.getCollection("forumMessages");
		chatMessages = university.getCollection("chatMessages");
	}
	
	public static Mongo getInstance() {
		if (instance == null) {
			instance = new Mongo(HOST, PORT);
		}
		return instance;
	}
	
	public void insertUser(User u) {
		users.save(u.toDocument());
	}
	
	public void insertCourse(Course c) {
		courses.save(c.toDocument());
	}
	
	public int countForumMessages(String crn) {
		return (int)forumMessages.count(new BasicDBObject("crn", crn));
	}
	
	public void insertForumMessage(Message m) {
		forumMessages.save(m.toDocument());
	}
	
	public void insertChatMessage(Message m) {
		chatMessages.save(m.toDocument());
	}
	
	public DBCollection getUsers() {
		return users;
	}
	
	public DBCollection getCourses() {
		return courses;
	}
	
	public DBObject getCourse(String crn) {
		DBCursor c = courses.find(new BasicDBObject("crn", crn));
		DBObject result = c.next();
		if (result == null) {
			return null;
		}
		return result;
	}
	
	public DBObject getUser(String username) {
		DBCursor c = users.find(new BasicDBObject("username", username));
		DBObject result = c.next();
		if (result == null) {
			return null;
		}
		return result;
	}
	
	public DBCollection getChatMessages() {
		return chatMessages;
	}

	public DBCursor getForumMessages(String crn) {
		return forumMessages.find(new BasicDBObject("crn", crn));
	}
	
	public DBCursor getForumParents(String crn) {
		return getForumChildren(crn, Message.DOES_NOT_EXIST);
	}
	
	public DBCursor getForumChildren(String crn, int parent) {
		return forumMessages.find(new BasicDBObject("crn", crn).append("parentId", parent));
	}
	
	public DBObject getForumMessage(String crn, int id) {
		return forumMessages.findOne(new BasicDBObject("crn", crn).append("_id", id));
	}
	
	public void upvote(DBCollection messages, String crn, int id, String username) {
		DBObject find = new BasicDBObject("crn", crn).append("_id", id);
		DBObject upnoter = new BasicDBObject("upnoters", new BasicDBObject("username", username));
		DBObject update = new BasicDBObject("$push", upnoter);
		messages.update(find, update);
	}
	
	public DBCollection getForumMessages() {
		return forumMessages;
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

//			Mongo.getInstance().courses.drop();
			
			Mongo.getInstance().insertCourse(new Course ("Language Development", "27045", "somelady@purdue.edu", "LYLE 1160", "9:30pm", "10:20pm", "0101010"));
			Mongo.getInstance().insertCourse(new Course ("Software Engineering", "43855", "bxd@purdue.edu", "WTHR ???", "3:00pm", "4:15pm", "0010100"));
			Mongo.getInstance().insertForumMessage(new Message("43855", "du55", "aaabbb", 0, Message.DOES_NOT_EXIST));
			Mongo.getInstance().insertForumMessage(new Message("43855", "du55", "edited you'll never see this", 1, Message.DOES_NOT_EXIST));
			Mongo.getInstance().insertForumMessage(new Message("43855", "du55", "i tried i swear", 1, Message.DOES_NOT_EXIST));
			Mongo.getInstance().insertForumMessage(new Message("43855", "du55", "a reply", 2, 0));
			Mongo.getInstance().insertForumMessage(new Message("43855", "du55", "another reply", 3, 0));
			if (Mongo.getInstance().countForumMessages("43855") < 8) {
				Mongo.getInstance().insertForumMessage(new Message("43855", "du55", "TOPIC", Mongo.getInstance().countForumMessages("43855"), Message.DOES_NOT_EXIST));
			}
			
			Mongo.getInstance().insertUser(new User("du55", "John Du"));
			System.out.println("~~~Courses");
			DBCursor c = Mongo.getInstance().getCourses().find();
			while(c.hasNext()) {
				System.out.println(c.next());
			}
			System.out.println("~~~Users");
			c = Mongo.getInstance().getUsers().find();
			while(c.hasNext()) {
				System.out.println(c.next());
			}
			System.out.println("~~~Forum Messages");
			c = Mongo.getInstance().getForumMessages("43855");
			while(c.hasNext()) {
				System.out.println(c.next());
			}
			System.out.println("~~~Forum Parents");
			c = Mongo.getInstance().getForumParents("43855");
			while(c.hasNext()) {
				System.out.println(c.next());
			}
			System.out.println("~~~Forum Children [0]");
			c = Mongo.getInstance().getForumChildren("43855", 0);
			while(c.hasNext()) {
				System.out.println(c.next());
			}
			System.out.println("~~~Other tests");
			System.out.println("43855 Forum Messages:" + Mongo.getInstance().countForumMessages("43855"));
			System.out.println(Mongo.getInstance().getCourse("43855").get("startTime"));
			System.out.println(Mongo.getInstance().getCourse("43855").get("name"));
			System.out.println(Mongo.getInstance().getUser("du55").get("name"));
			System.out.println(Mongo.getInstance().getForumMessages("43855").next());
			
			DBObject find = new BasicDBObject("crn", "43855").append("_id", 0);
			DBObject listItem = new BasicDBObject("upnoters", new BasicDBObject("username","aaa"));
			DBObject updateQuery = new BasicDBObject("$push", listItem);
			System.out.println(Mongo.getInstance().getForumMessages().update(find, updateQuery));
			System.out.println(Mongo.getInstance().getForumMessages("43855").next());
			
			
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}