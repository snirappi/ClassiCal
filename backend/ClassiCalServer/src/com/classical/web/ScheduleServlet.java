package com.classical.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classical.Course;
import com.classical.Event;
import com.classical.Mongo;

@WebServlet("/schedule")
public class ScheduleServlet extends HttpServlet {

	private static final int GET_EVENTS = 0;
	private static final int GET_COURSES = 1;
	private static final int ADD_EVENT = 2;
	private static final int ADD_COURSE = 3;
	private static final int REMOVE_EVENT = 4;
	private static final int EDIT_EVENT = 5;
	private static final String[] COMMANDS = {"getEvents", "getCourses", "addEvent", "addCourse", "removeEvent", "editEvent"};
	
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
		String username = request.getParameter("username");
		if (username == null) {
			error(out, "USER_NOT_FOUND");
			return;
		}
		String contents = "COMMAND_NOT_FOUND";
		StringBuilder list;
		
		if (command.equals(COMMANDS[GET_EVENTS])) {
			list = new StringBuilder();
			list.append("{\"events\":");
			list.append(Mongo.getInstance().getUser(username).get("events").toString());
			list.append("}");
			contents = list.toString();
		} else if (command.equals(COMMANDS[GET_COURSES])) {
			list = new StringBuilder();
			list.append("{\"courses\":");
			list.append(Mongo.getInstance().getUser(username).get("courses").toString());
			list.append("}");
			contents = list.toString();
		} else if (command.equals(COMMANDS[ADD_EVENT])) {
			String name = request.getParameter("name");
			String location = request.getParameter("location");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String recurring = request.getParameter("recurring");
			if (name == null || location == null || startTime == null || endTime == null || recurring == null) {
				error(out, "ADDE_BODY_NOT_FOUND");
				return;
			}
			Mongo.getInstance().addEvent(username, new Event(name, location, startTime, endTime, recurring.equals("true")));
			contents = "SUCCESSFUL_ADDE";
		} else if (command.equals(COMMANDS[ADD_COURSE])) {
			String crn = request.getParameter("crn");
			if (crn == null) {
				error(out, "ADDC_BODY_NOT_FOUND");
				return;
			}
			Mongo.getInstance().addCourse(username, (Course)Mongo.getInstance().getCourse(crn));
			contents = "SUCCESSFUL_ADDC";
		} else if (command.equals(COMMANDS[REMOVE_EVENT])) {
			String name = request.getParameter("name");
			String location = request.getParameter("location");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String recurring = request.getParameter("recurring");
			if (name == null || location == null || startTime == null || endTime == null || recurring == null) {
				error(out, "RME_BODY_NOT_FOUND");
				return;
			}
			Mongo.getInstance().removeEvent(username, new Event(name, location, startTime, endTime, recurring.equals("true")));
			contents = "SUCCESSFUL_RME";
		} else if (command.equals(COMMANDS[EDIT_EVENT])) {
			String name = request.getParameter("name");
			String location = request.getParameter("location");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String recurring = request.getParameter("recurring");
			if (name == null || location == null || startTime == null || endTime == null || recurring == null) {
				error(out, "EDITE_BODY_NOT_FOUND");
				return;
			}
			Mongo.getInstance().addEvent(username, new Event(name, location, startTime, endTime, recurring.equals("true")));
			contents = "SUCCESSFUL_EDITE";
		}
		out.println(contents);
		out.close();
	}
	
	private void error(PrintWriter o, String e) {
		o.println(e);
		o.close();
	}
	
}
