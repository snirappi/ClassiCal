package com.classical;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Mongo {
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