package com.classical.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classical.Course;
import com.classical.Forum;
import com.classical.Message;
import com.classical.User;

@WebServlet("/forums")
public class ForumServlet extends HttpServlet {
	
	//TODO: BUILD FORUM OBJECT
	//TODO: LOGINS (???)
	//TODO: INTERACT WITH FORUM OBJECT WITH GET/POST REQUESTS
	//TODO: ONE FORUM OBJECT PER CLASS
	//TODO: DUMMY FORUM POPULATING

	private static final int GET_PARENTS = 0;
	private static final int GET_CHILDREN = 1;
	private static final int POST = 2;
	private static final int REPLY = 3;
	private static final int SCORE = 4;
	private static final int REPORT = 5;
	private static final String[] COMMANDS = {"getParents", "getChildren", "post", "reply", "score", "report"};
	
	private final Course COURSE = new Course ("Software Engineering", "43855", "bxd@purdue.edu", new LinkedList<User>(), 
		"WTHR ???", "3:00pm", "4:15pm"); 
	
//	private List<Forum> forums;	//forums for each course
	private Forum forum = new Forum(COURSE);
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		PrintWriter out = response.getWriter();
		String command = request.getParameter("command");
		if (command == null) {
			error(out, "COMMAND_NOT_FOUND");
			return;
		}
		/*
		//TODO: POST TO COURSE BY CRN
		String crn = request.getParameter("crn");
		if (crn == null) {
			error(out, "COURSE_NOT_FOUND");
			return;
		}
		*/
		String contents = "COMMAND_NOT_FOUND";
		if (command.equals(COMMANDS[GET_PARENTS])) {
			StringBuilder list = new StringBuilder();
			List<Message> parents = forum.getParents();
			contents = Message.toJson(parents);
		} else if (command.equals(COMMANDS[GET_CHILDREN])) {
			String parentId = request.getParameter("parentId");
			if (parentId == null) {
				error(out, "PARENT_ID_NOT_FOUND");
				return;
			}
			List<Message> children = forum.getChildren(Integer.parseInt(parentId));
			contents = Message.toJson(children);
		} else if (command.equals(COMMANDS[POST])) {
			String user = request.getParameter("user");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			if (user == null || title == null || content == null) {
				error(out, "POST_BODY_NOT_FOUND");
				return;
			}
			forum.post(user, title, content);
			contents = "SUCCESSFUL_POST";
		} else if (command.equals(COMMANDS[REPLY])) {
			String user = request.getParameter("user");
			String content = request.getParameter("content");
			String parentId = request.getParameter("parentId");
			if (user == null || content == null || parentId == null) {
				error(out, "REPLY_BODY_NOT_FOUND");
				return;
			}
			forum.reply(user, content, Integer.parseInt(parentId));
			contents = "SUCCESSFUL_REPLY";
		} else if (command.equals(COMMANDS[SCORE])) {
			String user = request.getParameter("user");
			String id = request.getParameter("id");
			String up = request.getParameter("up");
			if (user == null || id == null || up == null) {
				error(out, "SCORE_BODY_NOT_FOUND");
			}
			forum.score(user, Integer.parseInt(id), up.equals("true"));
		} else if (command.equals(COMMANDS[REPORT])) {
			String user = request.getParameter("user");//the person reporting
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			if (user == null || id == null || type == null) {
				error(out, "SCORE_BODY_NOT_FOUND");
			}
			forum.report(user, Integer.parseInt(id), type);
		}
		out.println(contents);
		out.close();
	}
	
	private void error(PrintWriter o, String e) {
		o.println(e);
		o.close();
	}
	
}
