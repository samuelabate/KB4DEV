package com.sammie.kb4dev;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignupActivity extends AsyncTask<String, Void, String> {

    private Context context;

    public SignupActivity(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... arg0) {
        String fullName = arg0[0];
        String userName = arg0[1];
        String passWord = arg0[2];
        String phoneNumber = arg0[3];
        String emailAddress = arg0[4];
        String link;
        String data;
        BufferedReader bufferedReader;
        String result;

        try {

            data = "?fullname=" + URLEncoder.encode(fullName, "UTF-8");
            data += "&username=" + URLEncoder.encode(userName, "UTF-8");
            data += "&password=" + URLEncoder.encode(passWord, "UTF-8");
            data += "&phonenumber=" + URLEncoder.encode(phoneNumber, "UTF-8");
            data += "&emailaddress=" + URLEncoder.encode(emailAddress, "UTF-8");

            link = "http://192.168.0.190/kb4dev/signup.php" + data;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            //result = "{\"query_result\":\"SUCCESS\"}";
            return result;
        } catch (Exception e) {
            Log.d("Exception caught", e.toString());
            String s = "{\"query_result\":\""+ e.toString() +"\"}";
            return s;/*new String("Exception: " + e.getMessage())*/
        }
    }

    @Override
    protected void onPostExecute(String result) {
        String jsonStr = result;
        if (jsonStr != null) {
            try {
                Log.d("Hi Sammie!!!", "I'm inside onPostExecute's try...");
                JSONObject jsonObj = new JSONObject(jsonStr);
                Log.d("Hi Sammie!!!", "After creating a json object giving a string parameter...");
                String query_result = jsonObj.getString("query_result");
                Log.d("Hi Sammie!!!", "After getting string from json...");
                SignUp.progress.dismiss();
                if (query_result.equals("SUCCESS")) {
                    Toast.makeText(context, "Signup Successfull!", Toast.LENGTH_SHORT).show();
                } else if (query_result.equals("FAILURE")) {
                    Toast.makeText(context, "Signup failed!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, query_result, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Log.d("Exception Caught!!!", e.toString());
                Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "No response from server.", Toast.LENGTH_SHORT).show();
        }
    }
}