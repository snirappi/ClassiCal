package com.team3.classical.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.team3.classical.slidingtabs.R;

/**
 * Created by Tim on 2/22/2015.
 */
public class ChatSender extends Activity{
    private static final String TAG = ChatSender.class.getSimpleName();

    @Override public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "CREATED CHATSENDER");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        final EditText chatMessage = (EditText) findViewById(R.id.chatTextField);
        chatMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Log.d(TAG, "Sent message!");
                    chatMessage.setText("");
                    return true;
                } else {
                    chatMessage.setText("HELLO WORLD");
                    return false;
                }
             }
        }
        );
    }
}
