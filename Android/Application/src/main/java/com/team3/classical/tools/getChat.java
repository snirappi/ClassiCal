package com.team3.classical.tools;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.Socket;

/**
 * Created by Tim on 4/19/2015.
 */
public class getChat extends AsyncTask<Void, Integer, Void> {

    private WeakReference<TextView> mUpdateView;

    public getChat(TextView view) {
        this.mUpdateView = new WeakReference<TextView>(view);
    }


    @Override
    protected Void doInBackground(Void... params) {
        byte[] buffer = new byte[256];

        try {
            Socket socket = new Socket("52.0.251.88", 8080);
            InputStream is = socket.getInputStream();

            int read = is.read(buffer);
            while(read != -1){
                publishProgress(read);
                read = is.read(buffer);
            }

            is.close();
            socket.close();



        } catch (Exception e) {
            e.printStackTrace();
        }

            return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if(mUpdateView.get() != null && values.length > 0){
            mUpdateView.get().setText(values[0].toString());
        }
    }

}