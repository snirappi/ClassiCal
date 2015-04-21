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
import com.team3.classical.tools.getChat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Tim on 2/22/2015.
 */
public class ChatSender extends Activity {
    private static final String TAG = ChatSender.class.getSimpleName();
    String msg;
    public static int numInstances;

    private Socket socket;

    private static final int SERVERPORT = 8080;
    private static final String SERVER_IP = "192.168.1.18";

    @Override public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "CREATED CHATSENDER");
        super.onCreate(savedInstanceState);


    }
    public void startListener(View v){
        new Thread(new ClientThread()).start();
        final EditText chatMessage = (EditText) v.findViewById(R.id.chatTextField);
        final TextView message = (TextView) v.findViewById(R.id.textOutput2);
        Button send = (Button) v.findViewById(R.id.buttonSend);
        getChat task = new getChat(message);
        task.execute();
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
            try {
                OutputStream out = socket.getOutputStream();
                PrintWriter output = new PrintWriter(out);

                output.println("Client:" + message);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Couldn't send Message!");
            }

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            try {
                InetAddress serverAddr = InetAddress.getByName(SERVER_IP);

                socket = new Socket(serverAddr, SERVERPORT);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }

    }
}
