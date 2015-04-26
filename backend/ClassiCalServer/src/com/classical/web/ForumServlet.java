package com.classical.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classical.Forum;
import com.classical.Message;
import com.classical.Mongo;

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
	
//	private List<Forum> forums;	//forums for each course
	private Forum forum;//unnecessary with mongo interaction
	
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
		
		//TODO: POST TO COURSE BY CRN
		String crn = request.getParameter("crn");
		if (crn == null) {
			error(out, "COURSE_NOT_FOUND");
			return;
		}
		
		String contents = "COMMAND_NOT_FOUND";
		StringBuilder list;
		if (command.equals(COMMANDS[GET_PARENTS])) {
//			List<Message> parents = forum.getParents();
//			contents = Message.toJson(parents);
			list = new StringBuilder();
			list.append("{\"messages\":");
			list.append(Mongo.getInstance().getForumParents(crn).toArray().toString());
			list.append("}");
			contents = list.toString();
		} else if (command.equals(COMMANDS[GET_CHILDREN])) {
			String parentId = request.getParameter("parentId");
			if (parentId == null) {
				error(out, "PARENT_ID_NOT_FOUND");
				return;
			}
//			List<Message> children = forum.getChildren(Integer.parseInt(parentId));
//			contents = Message.toJson(children);
			list = new StringBuilder();
			list.append("{\"messages\":");
			list.append(Mongo.getInstance().getForumChildren(crn, Integer.parseInt(parentId)).toArray().toString());
			list.append("}");
			contents = list.toString();
		} else if (command.equals(COMMANDS[POST])) {
			String user = request.getParameter("user");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			if (user == null || title == null || content == null) {
				error(out, "POST_BODY_NOT_FOUND");
				return;
			}
//			forum.post(user, title, content);
			Mongo.getInstance().insertForumMessage(new Message(crn, user, title, content, Mongo.getInstance().countForumMessages(crn), Message.DOES_NOT_EXIST));
			contents = "SUCCESSFUL_POST";
		} else if (command.equals(COMMANDS[REPLY])) {
			String user = request.getParameter("user");
			String content = request.getParameter("content");
			String parentId = request.getParameter("parentId");
			if (user == null || content == null || parentId == null) {
				error(out, "REPLY_BODY_NOT_FOUND");
				return;
			}
//			forum.reply(user, content, Integer.parseInt(parentId));
			Mongo.getInstance().insertForumMessage(new Message(crn, user, "", content, Mongo.getInstance().countForumMessages(crn), Integer.parseInt(parentId)));
			contents = "SUCCESSFUL_REPLY";
		} else if (command.equals(COMMANDS[SCORE])) {
			String user = request.getParameter("user");
			String id = request.getParameter("id");
			String up = request.getParameter("up");
			if (user == null || id == null || up == null) {
				error(out, "SCORE_BODY_NOT_FOUND");
			}
//			forum.score(user, Integer.parseInt(id), up.equals("true"));
			Mongo.getInstance().upvote(crn, Integer.parseInt(id), user, up.equals("true"));
		} else if (command.equals(COMMANDS[REPORT])) {
			String user = request.getParameter("user");//the person reporting
			String id = request.getParameter("id");
			String type = request.getParameter("type");
			if (user == null || id == null || type == null) {
				error(out, "SCORE_BODY_NOT_FOUND");
			}
//			forum.report(user, Integer.parseInt(id), type);
			//ignore for now
		}
		out.println(contents);
		out.close();
	}
	
	private void error(PrintWriter o, String e) {
		o.println(e);
		o.close();
	}
	
}
