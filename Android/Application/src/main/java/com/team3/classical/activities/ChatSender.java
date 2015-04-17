package com.team3.classical.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.team3.classical.slidingtabs.R;

/**
 * Created by Tim on 2/22/2015.
 */
public class ChatSender extends Activity {
    private static final String TAG = ChatSender.class.getSimpleName();
    String msg;
    public static int numInstances;
    @Override public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "CREATED CHATSENDER");
        super.onCreate(savedInstanceState);


    }
    public void startListener(View v){
        final EditText chatMessage = (EditText) v.findViewById(R.id.chatTextField);
        final TextView message = (TextView) v.findViewById(R.id.textOutput2);
        Button send = (Button) v.findViewById(R.id.buttonSend);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                msg  = chatMessage.getText().toString();
                appendToMessageHistory("You", msg, message);
                Log.d(TAG, "Sent message: " + msg);
                chatMessage.setText("");
            }

        });

        chatMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    msg  =  chatMessage.getText().toString();
                    appendToMessageHistory("You", msg, message);//message.setText("You: "+ msg);
                    Log.d(TAG, "Sent message: " + msg);
                    chatMessage.setText("");
                    return true;
                }
                    return false;
            }
        });
        //TODO: Pass message to server

    }
    public  void appendToMessageHistory(String user, String message, TextView messageHistory) {
        if (user != null && message != null && !message.equals("")) {
            messageHistory.append(user + ": ");
            messageHistory.append(message + "\n");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
