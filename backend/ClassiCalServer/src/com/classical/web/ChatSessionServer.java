package com.classical.web;
import org.json.*;
import java.io.IOException;

import javax.websocket.*;
import javax.websocket.server.*;

import com.classical.Message;
//{relative host}/chatserver
@ServerEndpoint("/chatserver/{user}/{course}") 
public class ChatSessionServer {
	String user = null;
	String currentCourse = null;
	/*
	 * Tries to handshake with client
	 * 
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("user") String user, @PathParam("course") String course) {	
		System.out.println(session.getId() + " has connected");
		this.user = user;
		currentCourse = course;
		session.setMaxIdleTimeout(600000); //timeout after 10 minutes
		//ChatHandler.initialize();
		ChatHandler.addClient(session, course);
		try {
			session.getBasicRemote().sendText("Connection Accepted");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Deals with message recieved from client
	 * message recieved type should be string
	 * send JSON
	 */
	@OnMessage
	public void onMessage(Session session, String message) {
		System.out.println(message);
		JSONObject obj = new JSONObject(message);
		String content = obj.getString("content");
		if(!currentCourse.equals(obj.getString("crn")))
			ChatHandler.removeClient(session, currentCourse);
		currentCourse = obj.getString("crn");
		if(obj.getString("crn").equals("2g982i29")) {
			ChatHandler.addClient(session, currentCourse);
			return;
		}
		
		Message m = new Message(user, content, -1, -1);	
		ChatHandler.handleMessage(session, currentCourse, m);
	}
	
	//Removes client session from server
	@OnClose
	public void onClose(Session session) {
		ChatHandler.removeClient(session, getCurrentCourse());
		System.out.println(session.getId() + " has quit");	
	}
	
	public String getCurrentCourse() {
		return currentCourse;
	}
}
