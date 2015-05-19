package com.example.appleuser.borrow;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends ActionBarActivity
{
    private ImageButton buttonSignin;
    private ImageButton buttonSignup;
    private Intent i;
    private static boolean initializeParse = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_main);

        initializeActionBar();

        initializeButtons();

        if (initializeParse) {
            initializeDatabase();
            initializeParse = false;
        }
        getUserStatus();
    }

    private void getUserStatus()
    {
        if (ParseUser.getCurrentUser() != null) {
            toWelcomeActivity();
        }
    }

    private void toWelcomeActivity()
    {
        finish();
        i = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(i);
    }

    private void initializeActionBar()
    {
        // set title
        setTitle("Borrow");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);
    }

    private void initializeButtons()
    {
        buttonSignin = (ImageButton)findViewById(R.id.mainButtonSignin);
        buttonSignup = (ImageButton)findViewById(R.id.mainButtonSignup);

        buttonSignin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toSigninActivity(v);
            }
        });

        buttonSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toSignupActivity(v);
            }
        });

    }

    private void initializeDatabase()
    {
        // connects to Parse database: borrowappandroid@gmail.com
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xEVrr9KpRW07GEYHlph9pkrRAdeAXFg5xZllfaN8", "OTu2sE7Dr72YZQazWAVkPPVN9cDuC1kBSB23Nfau");
    }

    private void toSigninActivity(View v)
    {
        i = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(i);
    }

    private void toSignupActivity(View v)
    {
        i = new Intent(getApplicationContext(), SignupActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
