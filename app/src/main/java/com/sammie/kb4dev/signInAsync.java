package com.sammie.kb4dev;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Sammie on 8/10/2016.
 */
public class signInAsync extends AsyncTask<String, Void, String> {

    private Context context;

    public signInAsync(Context context) {
        this.context = context;
    }

    String username, pass, link, data, result;
    BufferedReader bufferedReader;

    @Override
    protected String doInBackground(String... strings) {
        username = strings[0];
        pass = strings[1];
        try {
            data = "?username=" + URLEncoder.encode(username, "UTF-8");
            data += "&password=" + URLEncoder.encode(pass, "UTF-8");
            link = "http://192.168.0.188/kb4dev/signin.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String s = bufferedReader.readLine();
            Log.d("Hi Sammie!", "I'm going to return the read line..." + s);
            return s;
        } catch (Exception e) {
            Log.d("Exception Caught!", e.toString());
            return "{\"query_result\":\"" + e.toString() + "\"}";
        }
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(s);
                String query_result = jsonObject.getString("query_result");
                if (query_result.equals("SUCCESS")) {
                    SignIn.info.setText("Hi " + jsonObject.getString("full_name") +"! You're now a'uthenticated!");
                } else {
                    SignIn.info.setText(jsonObject.getString("query_result"));
                }
            } catch (Exception e) {
                Log.d("Exception Caught!", e.toString());
                SignIn.info.setText("Sign In Failed!");
            }
        }else
        {
            Toast.makeText(context, "No response from server.", Toast.LENGTH_SHORT).show();
        }
    }
}