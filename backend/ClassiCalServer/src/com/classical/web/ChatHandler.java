package com.classical.web;

import javax.websocket.Session;
import com.classical.Message;

import java.util.Hashtable;
import java.util.LinkedList;

public class ChatHandler{
	//private static final ChatHandler instance = new ChatHandler();
	private static final Hashtable<String, LinkedList<Session>> courseUsers = new Hashtable<String, LinkedList<Session>>();
	
	/*protected ChatHandler() {	
		//prevent instatiation
	}*/
	
	/*public synchronized static void initialize() {
		if(instance == null) 
			instance = new ChatHandler();
		if(courseUsers == null)
			courseUsers = 
	}*/
	
	public synchronized static void addClient(Session s, String course) {
		if(!courseUsers.containsKey(course)) {
			System.out.println("New Course added " + course);
			courseUsers.put(course, new LinkedList<Session>());
		}
			courseUsers.get(course).add(s);
		System.out.println(courseUsers.toString());
	}
	
	public synchronized static void handleMessage(Session s, String course, Message m) {	
		if(!checkString(course)) return;
		try {
			String jsonText = m.toJson();
			for(Session sesh: courseUsers.get(course)) {
				if(sesh.isOpen())
					sesh.getBasicRemote().sendText(jsonText);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(courseUsers.toString());
	}
	
	public synchronized static void removeClient(Session s, String course) {		
		if(!checkString(course)) return;
		if(!courseUsers.containsKey(course)) return; 
		for(Session sesh: courseUsers.get(course)) {
			if(sesh.equals(s)) {
				courseUsers.get(course).remove(sesh); //slow consider using arraylist with forloop
				
			}
		}
		System.out.println(courseUsers.toString());
	}
	
	private static boolean checkString(String s) {
		if(s == null || "".equals(s))
			return false;
		return true;
	}

}
