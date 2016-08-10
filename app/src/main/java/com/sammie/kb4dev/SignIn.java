package com.sammie.kb4dev;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sammie on 8/10/2016.
 */

public class SignIn extends AppCompatActivity{

    EditText uName, pass;
    static TextView info;
    String username, password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        init();
    }
    protected void sign_in(View view)
    {
        username = uName.getText().toString();
        password = pass.getText().toString();
        if (!username.equals(null) && !password.equals(null))
            new signInAsync(this).execute(username, password);
        else
            Toast.makeText(this, "One or more fields are left empty!", Toast.LENGTH_SHORT).show();
    }
    private void init()
    {
        uName = (EditText)findViewById(R.id.editText2);
        pass = (EditText)findViewById(R.id.editText3);
        info = (TextView)findViewById(R.id.textView2);
    }
}
