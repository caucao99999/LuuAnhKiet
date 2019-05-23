package com.example.luuanhkiet_1706020042;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class CallAPIAsynTask extends AsyncTask<String, Boolean, JSONObject> {

    Map<String, String> mMap;
    Context context;
    private ProgressDialog dialog;
    public CallAPIAsynTask(Context mContext, Map<String, String> contextMap) {
        mMap = contextMap;
        this.context = mContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle("Loading, please wait .....");
        dialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            URL surl = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) surl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject data = new JSONObject(mMap);
            OutputStream out = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(String.valueOf(data));
            writer.flush();
            writer.close();
            out.close();

            connection.connect();
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            publishProgress(true);

        }catch (Exception e){
            Log.i("lỗi: ",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
        if (values[0]){
            dialog.dismiss();
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, MainActivity.class));
        }else {
            Toast.makeText(context, "fail..", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

    }
}
