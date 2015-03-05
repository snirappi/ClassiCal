package com.classical.web;

import java.io.*;
import java.util.*;

import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classical.Message;
import com.mongodb.DBCollection;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet{
	MsgSource source = new MsgSource();
	//List<Message> messages;
	//List<String>  users; //TODO: CHANGE TO <USER>
	//private DBCollection msgCollection;
	

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(getNextMessage()); //waits for next message
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		
		String message = request.getParameter("message");
		String user = request.getParameter("user");
		if(message != null)		
			sendMessage(new Message(user, message, -1, -1)); //sends message to clients
		response.setStatus(response.SC_NO_CONTENT); //no response
		
	}
	
	private String getNextMessage() {
		return new MsgSink().getNextMessage(source);
	}
	
	private void sendMessage(Message message) {
		source.sendMessage(message.getContent());	
	}
	
	class MsgSource extends Observable{
		public void sendMessage(String message) {
			setChanged();
			notifyObservers(message);
		}		
	}
	
	class MsgSink implements Observer {
		String message = null;
		synchronized public void update(Observable o, Object arg) {
			message = (String)arg;
			notify();
		}
		
		synchronized public String getNextMessage(MsgSource source) {
			source.addObserver(this);
			while(message == null) {
				try {
					wait();
					}catch (Exception ignore) {}
				}
			source.deleteObserver(this);
			String cpy = message;
			message = null;
			return cpy;
		}
	}
}
