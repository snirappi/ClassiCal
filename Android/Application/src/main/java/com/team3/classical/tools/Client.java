package com.team3.classical.tools;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Tim on 3/7/2015.
 */
public class Client {

    private static final String TAG= "Client";  //debug tag

    private static final int CONNECTION_TIMEOUT = 10000; // 10 seconds
    private static final String API_ENDPOINT_URL = "http://73.168.58.212:8080/forums";
    HttpPost httppost = new HttpPost(API_ENDPOINT_URL);


    public Client(){
    }

    private HttpClient initClient(){
        HttpClient client = new DefaultHttpClient();
        return client;
    }

    /**
     * Create a string from an InputStream
     *
     * This uses a clever trick: It tokenizes the string based on the delimiter for
     * the beginning of a string. Thus, it effectively tokenizes the string into one
     * long token.
     *
     * Based on http://stackoverflow.com/a/5445161
     *
     * @param is The input stream
     * @return A string containing the content of the stream
     */
    private String inputStreamToString(InputStream is) {
        Scanner scanner = new Scanner(is);
        Scanner tokenizer = scanner.useDelimiter("\\A");
        String str = tokenizer.hasNext() ? tokenizer.next() : "";
        scanner.close();
        return str;
    }

    /**
     * Send a command to the server for execution and return the server response
     *
     * This method serves as an abstraction to send a message to the server and return
     * exactly what the server sends back.
     *
     * @param command The command to execute
     * @return The server's response, or an empty string if the response could not be retrieved
     */
    private ArrayList<ForumPost> executeServerCommand(String command) {
        HttpClient client = new DefaultHttpClient();
        ArrayList<ForumPost> posts = new ArrayList<ForumPost>();

        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("command", "getParents"));
            nameValuePairs.add(new BasicNameValuePair("crn", "43855"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = client.execute(httppost);
            String responseBody = EntityUtils.toString(response.getEntity());

            try {
                JSONObject jr = new JSONObject(responseBody);
                JSONArray arr = jr.getJSONArray("messages");
                for(int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String name = obj.getString("title"),
                            creator = obj.getString("user"),
                            content= obj.getString("content"),
                            score = obj.getString("score");
                    ForumPost fp = new ForumPost(creator, name, content, score);
                    posts.add(fp);
                }
            } catch(JSONException e) {
                e.printStackTrace();
            }
            return posts;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG,"Connection refused!");
            return null;
        }
    }

    public ArrayList<ForumPost> get(){
        String command = "";
        ArrayList response = executeServerCommand(command);
        return response;
    }
}

