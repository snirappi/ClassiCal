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
		this.crn = crn;
		messages = new LinkedList<Message>();
	}
	
	public List<Message> getAllParents() {
		List<Message> parents = new LinkedList<Message>();
		for (int i = 0; i < messages.size(); i++) {
			Message message = messages.get(i);
			if (!message.hasParent()) {
				parents.add(messages.get(i));	
			}
		}
		return parents;
	}
	
	public List<Message> getAllChildren(int parentId) {
		List<Message> children = new LinkedList<Message>();
		for (int i = 0; i < messages.size(); i++) {
			Message message = messages.get(i);
			if (message.getParentId() == parentId) {
				children.add(messages.get(i));	
			}
		}
		return children;
	}
	
	public void post(String user, String content) {
		reply(user, content, Message.DOES_NOT_EXIST);
	}
	
	public void reply(String user, String content, int id) {
		messages.add(new Message(user, content, postId++, id));
	}
	
	public String getCrn() {
		return crn;
	}
	
}