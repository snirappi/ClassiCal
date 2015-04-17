package com.team3.classical.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.team3.classical.slidingtabs.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Created by Tim on 3/4/2015.
 */
public class ListViewActivity extends Activity {
    private ListView        listView;
    private TextView        title;
    private TextView        author;
    private TextView        body;
    private Intent          intent;
    private final String    TAG = "ListViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public ListViewActivity(View v){
        listView = (ListView) v.findViewById(R.id.list);
        title = (TextView) v.findViewById(R.id.head);
        author = (TextView) v.findViewById(R.id.auth);
        body = (TextView) v.findViewById(R.id.bdy);

        String json = "[\n" +
                "    {\n" +
                "        \"title\":\"Hello,world!\", \n" +
                "        \"desc\":\"Test please ignore\", \n" +
                "        \"date\":1427670264, \n" +
                "        \"score\":10101,\n" +
                "        \"creator\":\"Tim\", \n" +
                "        \"id\":1 \n" +
                "    },\n" +
                "    {\n" +
                "        \"title\":\"When is the homework due?\", \n" +
                "        \"desc\":\"For sprint 2\", \n" +
                "        \"date\":1427670309, \n" +
                "        \"score\":2,\n" +
                "        \"creator\":\"Shawn\", \n" +
                "        \"id\":2\n" +
                "    },\n" +
                "    {\n" +
                "        \"title\":\"Will the test be curved\", \n" +
                "        \"desc\":\"Midterm 1\", \n" +
                "        \"date\":1427670371, \n" +
                "        \"score\":10,\n" +
                "        \"creator\":\"Luke\", \n" +
                "        \"id\":3\n" +
                "    }\n" +
                "]";
        try {
            Scanner in = new Scanner(getResources().openRawResource(R.raw.posts));
            in.useDelimiter("\\Z");
            json = in.next();
            in.close();
        }
        catch (Exception e){
            System.out.println("Cannot find file!");
        }
        //String[] values = new String[]{};
        ArrayList<String> values = new ArrayList<String>();
        try {
            JSONArray arr = new JSONArray(json);
            for(int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String name = obj.getString("title"),
                        desc = obj.getString("desc");
                values.add(name + " - " + desc);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        final ArrayList<String> topics = new ArrayList<String>();
        for (int i = 0; i < values.size(); ++i) {
            topics.add(values.get(i));
            //Log.d(values.get(i)," Added!");
        }
         intent= new Intent(this, LoginActivity.class);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, topics);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Selected " + topics.get(position), "List View Activity");
                //Intent intent = new Intent(view.getContext(), DisplayPostActivity.class);
               // startActivity(intent);
                listView.setVisibility(View.GONE);
                title.setText(topics.get(position));
                title.setVisibility(View.VISIBLE);
                author.setVisibility(View.VISIBLE);
                body.setVisibility(View.VISIBLE);
                try{
                    startActivity(intent);
                }
                catch (Exception e){
                    System.out.println("Cannot start activity!");
                }

            }
        });

    }

}
