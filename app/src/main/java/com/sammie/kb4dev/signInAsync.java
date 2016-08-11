package com.sammie.kb4dev;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
        /**/
        username = strings[0];
        pass = strings[1];
        try {
            data = "?username=" + URLEncoder.encode(username, "UTF-8");
            data += "&password=" + URLEncoder.encode(pass, "UTF-8");
            link = "http://192.168.0.190/kb4dev/signin.php" + data;
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
                    Log.d("Hi Sammie!", "Excecutin till the start of 'if' in onPostExecute...");
                    SignIn.pDG.dismiss();
                    SignIn.info.setText("Hi " + jsonObject.getString("full_name") +"! You're now authenticated!");

                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.k).setContentTitle("kb4dev").setContentText("You're signed in to kb4dev.");
                    NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    Intent intent = new Intent(context, Welcome.class);
                    TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
                    taskStackBuilder.addParentStack(SignUp.class);
                    taskStackBuilder.addNextIntent(intent);
                    PendingIntent pendingIntent = taskStackBuilder.getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(pendingIntent);
                    nm.notify(0, mBuilder.build());
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