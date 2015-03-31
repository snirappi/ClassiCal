package com.team3.classical.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;

import com.team3.classical.slidingtabs.DisplayPostActivity;
import com.team3.classical.slidingtabs.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Tim on 3/4/2015.
 */
public class ListViewActivity extends Activity {
    private ListView        listView;
    private final String    TAG = "ListViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public ListViewActivity(View v){
        listView = (ListView) v.findViewById(R.id.list);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        final ArrayList<String> topics = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            topics.add(values[i]);
            Log.d(values[i]," Added!");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_list_item_1, topics);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Selected " + topics.get(position), "List View Activity");
                //Intent intent = new Intent(view.getContext(), DisplayPostActivity.class);
               // startActivity(intent);

            }
        });

    }

}
