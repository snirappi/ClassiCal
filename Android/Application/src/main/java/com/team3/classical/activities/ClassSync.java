package com.team3.classical.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

import com.team3.classical.slidingtabs.R;

/**
 * Created by clevo on 2015/2/26.
 */
public class ClassSync  extends Activity {
    private static final String TAG = ClassSync.class.getSimpleName();

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);
    }

    public void startListener(View v) {
        Button sync = (Button) v.findViewById(R.id.button);
        sync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                syncCourses(courseNames, courseTime);
            }
        });
    }

    public void syncCourses(char[][] names, int[] time) {
        LinearLayout ll1 = (LinearLayout)findViewById(R.id.ll1);
        LinearLayout ll2 = (LinearLayout)findViewById(R.id.ll2);
        LinearLayout ll3 = (LinearLayout)findViewById(R.id.ll3);
        LinearLayout ll4 = (LinearLayout)findViewById(R.id.ll4);
        LinearLayout ll5 = (LinearLayout)findViewById(R.id.ll5);

    }

}