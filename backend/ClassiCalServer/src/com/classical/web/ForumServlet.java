package com.classical.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.classical.Forum;
import com.classical.Generator;

@WebServlet("/forums")
public class ForumServlet extends HttpServlet {
	
	//TODO: BUILD FORUM OBJECT
	//TODO: LOGINS (???)
	//TODO: INTERACT WITH FORUM OBJECT WITH GET/POST REQUESTS
	//TODO: ONE FORUM OBJECT PER CLASS
	//TODO: DUMMY FORUM POPULATING
	
//	private List<Forum> forums;
	private Forum forum = new Forum("43855");
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String crn = request.getParameter("crn");
		if (crn == null) {
			out.println(Generator.NOT_FOUND);
			out.close();
			return;
		}
		String[] top = {"<h1>Messages</h1>"};
		String[] contents = {"forumcontents"};
		String[] bot = {"<br><br><input id='message' value='Enter message here!'><button onclick='send()'>Send</button>"};
		String page = Generator.generatePage("Simple forum", new String[]{}, new String[]{"/index.js", "http://code.jquery.com/jquery-1.11.1.js"}, new String[]{"top", "messages", "bot"}, new String[][]{top, contents, bot});
		out.println(page);
		out.close();
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String crn = request.getParameter("crn");
		if (crn != null) {
			out.println("NOT_FOUND");
			out.close();
			return;
		}
		String contents = "forumresponse";
		out.println(contents);
		out.close();
	}
}
