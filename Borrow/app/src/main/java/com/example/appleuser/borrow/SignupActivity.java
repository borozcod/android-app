package com.example.appleuser.borrow;

import android.content.Intent;
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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignupActivity extends ActionBarActivity
{
    private Button buttonCreateAccount;
    private Button buttonCancel;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_signup);

        initializeActionBar();

        initializeButtons();
    }

    private void initializeActionBar()
    {
        // set title
        setTitle("Borrow :: Sign Up");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeButtons()
    {
        buttonCreateAccount = (Button)findViewById(R.id.signupButtonCreateAccount);
        buttonCancel = (Button)findViewById(R.id.signupButtonCancel);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
    }

    private void createAccount()
    {
        String username = ((EditText)findViewById(R.id.signupUsernameText)).getText().toString();
        String password1 = ((EditText)findViewById(R.id.signupPasswordText)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.signupPasswordRepeatText)).getText().toString();

        if (username.length() == 0)
        {
            Toast.makeText(SignupActivity.this, "Please enter a Username", Toast.LENGTH_LONG).show();
            return;
        }

        if (password1.length() == 0)
        {
            Toast.makeText(SignupActivity.this, "Please enter a Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (password2.length() == 0)
        {
            Toast.makeText(SignupActivity.this, "Please repeat your Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password1.equals(password2))
        {
            Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
            return;
        }

        Log.d("Sagev", "New acc");
        ParseUser newUser = new ParseUser();
        Log.d("Sagev", "Set user");
        newUser.setUsername(username);
        Log.d("Sagev", "Set pass");
        newUser.setPassword(password1);
        Log.d("Sagev", "Push acc");
        newUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                Log.d("Sagev", "Start if block");
                if (e != null) {
                    Log.d("Sagev", "Caught error");
                    Toast.makeText(SignupActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.d("Sagev", "Threw error");
                } else {
                    Log.d("Sagev", "Switch view");
                    toHomeActivity();
                    //Log.d("Sagev", "Modify greeting");
                    //((TextView) findViewById(R.id.homeWelcomeText)).setText("Welcome to Borrow, " + ((EditText) findViewById(R.id.signupUsernameText)).getText().toString());
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
        getMenuInflater().inflate(R.menu.menu_signup, menu);
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
