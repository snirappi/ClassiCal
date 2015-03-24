package com.classical;

import java.util.LinkedList;
import java.util.List;

public class Forum {
	
	/** Contains all the posts in the forum, both topics and replies */
	private List<Message> messages;
	/** Doubles as total message count and id for each new post */
	private int postId = 0;
	/** Identifies the course to which this forum belongs to */
	private String crn;
	
	public Forum(String crn) {
//		messages = Message.toMessages(Mongo.getInstance().getForumMessages(crn));	//pull messages from mongo
		this.crn = crn;
		messages = new LinkedList<Message>();
	}
	
	public List<Message> getParents() {
		List<Message> parents = new LinkedList<Message>();
		for (Message m : messages) {
			if (!m.hasParent()) {
				parents.add(m);	
			}
		}
		return parents;
	}
	
	public List<Message> getChildren(int parentId) {
		List<Message> children = new LinkedList<Message>();
		for (Message m : messages) {
			if (m.getParentId() == parentId && m.getReports() < 5) {
				children.add(m);	
			}
		}
		return children;
	}
	
	public void post(String user, String title, String content) {
		messages.add(new Message(user, title, content, postId++, Message.DOES_NOT_EXIST));
	}
	
	public void reply(String user, String content, int parentId) {
		messages.add(new Message(user, content, postId++, parentId));
	}
	
	public void score(String user, int id, boolean up) {
		for (Message m : messages) {
			if (m.getId() == id) {
				if (up) {
					m.upnote(user);
				} else {
					m.downnote(user);
				}
				return;
			}
		}
	}
	
	public void report(String user, int id, String type) {
		for (Message m : messages) {
			if (m.getId() == id) {
				//report
				return;
			}
		}
	}
	
	public String getCrn() {
		return crn;
	}
	
}