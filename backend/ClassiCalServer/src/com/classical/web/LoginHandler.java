package com.classical.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.net.www.http.HttpClient;

import com.classical.Mongo;
import com.classical.User;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;




@WebServlet("/login")
public class LoginHandler extends HttpServlet {
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doPost(request, response);
	}
	
	private Boolean auth(boolean method, String url, String pass, String user, CookieStore cookies) throws IOException{
		URL nurl = new URL(url);
		
		Map<String,Object> params = new LinkedHashMap<>();
        params.put("user", user);
        params.put("pass", pass);
        params.put("uuid", "12948");

        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : params.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
		
		HttpURLConnection connection = (HttpURLConnection) nurl.openConnection();
		connection.setDoOutput(true); 
	    connection.setInstanceFollowRedirects(false); 
	    connection.setRequestMethod("GET"); 
	    connection.setRequestProperty("pass", pass); 
	    connection.setRequestProperty("user", user);
	    connection.setRequestProperty("uuid", "0xACA021");
	    connection.setUseCaches( false );
	    
	    if(cookies.getCookies().size() > 0)
		    connection.setRequestProperty("Cookie", join(";",  cookies.getCookies())); 
	    
	    try( DataOutputStream wr = new DataOutputStream( connection.getOutputStream())) {
	    	   wr.writeBytes( postData.toString() );
	    }
	    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String inline;
	    while((inline = in.readLine()) != null){
	    	if(inline.contains("loginok.html"))
	    		return true;
	    }
	    Map<String, List<String>> headerFields = connection.getHeaderFields();
        List<String> cookiesHeader = headerFields.get("Set-Cookie");

        if(cookiesHeader != null)
            for (String cookie : cookiesHeader) 
              cookies.add(null,HttpCookie.parse(cookie).get(0));
	    in.close();
	    return false;
	}
	
	private String request(boolean method, String url, CookieStore cookieJar) throws IOException{
		HttpURLConnection conn;
		 
	    BufferedReader rd;
	    String line;
	    String result = "";
		URL nurl = new URL(url);
        conn = (HttpURLConnection) nurl.openConnection();
        if(cookieJar.getCookies().size() > 0)
		    conn.setRequestProperty("Cookie", join(";",  cookieJar.getCookies()));  
        conn.setRequestMethod("GET");
        rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = rd.readLine()) != null) {
           result += line;
        }
    
        Map<String, List<String>> headerFields = conn.getHeaderFields();
        List<String> cookiesHeader = headerFields.get("Set-Cookie");

        if(cookiesHeader != null)
            for (String cookie : cookiesHeader) 
              cookieJar.add(null,HttpCookie.parse(cookie).get(0));
        rd.close();
        
        return result;
	}
	
	public Boolean loginUser(String user, String pass, CookieStore cookies) throws IOException, URISyntaxException{
		CookieHandler.setDefault( new CookieManager( null, CookiePolicy.ACCEPT_ALL ) );
		request(true, "https://wl.mypurdue.purdue.edu/cp/home/loginf", cookies);
		request(true, "https://wl.mypurdue.purdue.edu/cp/home/displaylogin", cookies);
		//These set the cookies or something I guess
		
		if(auth(false, "https://wl.mypurdue.purdue.edu/cp/home/login", pass, user, cookies)){
			return true;
		}
		else{
			cookies.get(new URI("america")).get(0).setValue("1");
			return false;
		}
		
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		
		CookieManager manager = new CookieManager();
        CookieHandler.setDefault(manager);
        CookieStore cookieJar =  manager.getCookieStore();
        
        HttpCookie validation = new HttpCookie("america", "1");
        try {
			cookieJar.add(new URI("america"), validation);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		int token = 000000;//change to some hash stuff soon
		//DECRYPT HERE IF NECESSARY
		
		//its not necessary now
		
		//LOGGING IN
		try {
			loginUser(user,pass, cookieJar);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		try {
			if(!cookieJar.get(new URI("america")).get(0).getValue().equals("1")){ //authentication fails
				out.println("Authentication failed");
				out.close();
				return;
			}
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//if users exists, stop here and respond, otherwise create the user and refresh
		Mongo Mango = Mongo.getInstance();
		DBCollection item = Mango.getUsers();
		BasicDBObject query = new BasicDBObject("username", user);
		DBObject result = item.findOne(query);
		if(result != null){
			out.print("{\"valid\": true,\n"
					 + "\"username\":" + user + ",\n"
					 + "\"token\": " + token + ",\n"
					 + "\"classes\": [" + result.get("classes").toString() + "]");
			
			
			//user exists, log them in
		} else {
			//user does not exist, create them
			User fresh = new User(user, pass, null, null);
			out.print("{\"valid\": true,\n"
					 + "\"username\":" + user + ",\n"
					 + "\"token\": " + token + ",\n"
					 + "\"classes\": [" + result.get("classes").toString() + "]");
		}
		out.close();
	}
	
	private static String join(CharSequence delimiter, Iterable tokens) {
		StringBuilder sb = new StringBuilder();
		boolean firstTime = true;
		for (Object token: tokens) {
			if (firstTime) {
				firstTime = false;
			} else {
				sb.append(delimiter);
			}
			sb.append(token);
		}
		return sb.toString();
	}
}