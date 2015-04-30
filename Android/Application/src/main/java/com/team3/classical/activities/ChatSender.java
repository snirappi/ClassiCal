package com.team3.classical.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.team3.classical.slidingtabs.R;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_76;
import org.java_websocket.handshake.ServerHandshake;

import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * Created by Tim on 2/22/2015.
 */
public class ChatSender extends Activity {
    private static final String TAG = ChatSender.class.getSimpleName();
    String msg;
    public static int numInstances;
    private WebSocketClient mWebSocketClient;
    private Socket socket;
    private PrintWriter out = null;
    private Scanner in = null;

    private static final int SERVERPORT = 8080;
    private static final String SERVER_IP = "192.168.1.18";

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    public void startListener(View v){

        final EditText chatMessage = (EditText) v.findViewById(R.id.chatTextField);
        final TextView message = (TextView) v.findViewById(R.id.textOutput2);
        Button send = (Button) v.findViewById(R.id.buttonSend);
        //ClientThread ct = new ClientThread();
        //ct.setV(message);
        //new Thread(ct).start();
        //getChat task = new getChat(message);
        //task.execute();
        connectWebSocket();
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
                    mWebSocketClient.send(msg);
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
               // OutputStream out = socket.getOutputStream();
                //PrintWriter output = new PrintWriter(out);
                //output.println("Client:" + message);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Couldn't send Message!");
            }

        }
    }
    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://73.168.58.212:8080/chatserver/vincent3/CS%20307");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, new Draft_76()){
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from " + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //TextView textView = (TextView)findViewById(R.id.messages);
                        //textView.setText(textView.getText() + "\n" + message);
                        System.out.print("GOT" + message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

/**    class ClientThread implements Runnable {
        TextView v;
        public void setV(TextView view){
            v= view;
        }
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
                try {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new Scanner(socket.getInputStream());
                } catch (Exception e) {
                    System.out.println(e);
                    e.printStackTrace();
                }

                try {
                    // Start reading from the client
                    String inputLine = null;
                    while (in.hasNextLine()) {
                        inputLine = in.nextLine();
                        appendToMessageHistory("User", inputLine, v);
                    }
                } catch (Exception e) {
                    // Socket closed, don't worry
                    System.out.println("Could not connect");
                }


        }

    }**/
}
