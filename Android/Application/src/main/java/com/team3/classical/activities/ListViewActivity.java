package com.team3.classical.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.team3.classical.slidingtabs.R;
import com.team3.classical.tools.Client;
import com.team3.classical.tools.ForumPost;

import java.util.ArrayList;


/**
 * Created by Tim on 3/4/2015.
 */
public class ListViewActivity extends Activity {
    private static ListView        listView;
    private static TextView        title;
    private static TextView        author;
    private static TextView        body;
    private Intent          intent;
    private ArrayList<String> poster ;
    private ArrayList<ForumPost> posts = new ArrayList<ForumPost>();
    private final String    TAG = "ListViewActivity";
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public ListViewActivity(View v){
        final Client client = new Client();
        listView = (ListView) v.findViewById(R.id.list);
        title = (TextView) v.findViewById(R.id.head);
        author = (TextView) v.findViewById(R.id.auth);
        body = (TextView) v.findViewById(R.id.bdy);
        context = v.getContext();
        AsyncTask<Void, Void, Integer> getTask = new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                posts = client.get();
                return 1;
            }


        @Override
        protected void onPostExecute(Integer integer){

            final ArrayList<String> topics = new ArrayList<String>();
            poster = new ArrayList<String>();
            for (int i = 0; i < posts.size(); ++i) {
                topics.add(posts.get(i).postTitle + " - " + posts.get(i).postDescription);
                poster.add("By: " + posts.get(i).postAuthor + " - score: " + posts.get(i).postScore);

            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, topics);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Log.d("Selected " + topics.get(position), "List View Activity");
                    listView.setVisibility(View.GONE);
                    title.setText(topics.get(position).split("-")[0].trim());
                    body.setText(topics.get(position).split("-")[1].trim());
                    author.setText(poster.get(position));

                    title.setVisibility(View.VISIBLE);
                    author.setVisibility(View.VISIBLE);
                    body.setVisibility(View.VISIBLE);
                }
            });

            }
        };
        getTask.execute();

    }
    public static void restore(){
        title.setVisibility(View.GONE);
        author.setVisibility(View.GONE);
        body.setVisibility(View.GONE);

        listView.setVisibility(View.VISIBLE);

    }
}
