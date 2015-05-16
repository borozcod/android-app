package com.example.appleuser.borrow;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class SigninActivity extends ActionBarActivity
{
    private Button buttonBack;
    private ImageButton buttonSignin;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_signin);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        buttonBack = (Button)findViewById(R.id.signinButtonBack);
        buttonSignin = (ImageButton)findViewById(R.id.signinButtonSignin);

        buttonSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
    }

    private void signin()
    {
        String username = ((EditText)findViewById(R.id.signinUsernameText)).getText().toString();
        String password = ((EditText)findViewById(R.id.signinPasswordText)).getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                Log.d("Sagev", "Start if block");
                if (e != null) {
                    Log.d("Sagev", "Caught error");
                    Toast.makeText(SigninActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.d("Sagev", "Threw error");
                } else {
                    Log.d("Sagev", "Switch view");
                    toHomeActivity();
                    //Log.d("Sagev", "Modify greeting");
                    //((TextView)findViewById(R.id.homeWelcomeText)).setText("Welcome to Borrow, " + ((EditText) findViewById(R.id.signupUsernameText)).getText().toString());
                    Log.d("Sagev", "end if-block");
                }
            }
        });
        Log.d("Sagev", "Finish");
    }

    private void toHomeActivity()
    {
        finish();
        i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    private void toMainActivity()
    {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Log.d("Sagev", "Action Bar Start");

        switch (id) {
            //noinspection SimplifiableIfStatement
            case R.id.action_settings : {
                Log.d("Sagev", "Settings");
                return true;
            }
            case android.R.id.home : {
                Log.d("Sagev", "Back");
                toMainActivity();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}


