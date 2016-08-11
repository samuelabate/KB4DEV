package com.sammie.kb4dev;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Welcome extends ListActivity {
    String classes[]= {"SignUp", "SignIn" };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(Welcome.this, android.R.layout.simple_list_item_1, classes));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String cName = classes[position];
        try{
            Class ourClass = Class.forName("com.sammie.kb4dev." + cName);
            Intent i = new Intent(getApplicationContext(), ourClass);
            startActivity(i);
        }catch (Exception e)
        {
            Log.d("Exception Caught!", e.toString());
        }
    }
}