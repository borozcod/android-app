/*




This is still very work-in-progress
I'm only pushing this because Bryan insists




 */

package com.example.appleuser.borrow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // borrowappandroid@gmail.com
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "xEVrr9KpRW07GEYHlph9pkrRAdeAXFg5xZllfaN8", "OTu2sE7Dr72YZQazWAVkPPVN9cDuC1kBSB23Nfau");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toSigninActivity(View v) {setContentView(R.layout.activity_signin);}

    public void toSignupActivity(View v) {setContentView(R.layout.activity_signup);}

    public void toMainActivity(View v) {setContentView(R.layout.activity_main);}

    public void createAccount(View v)
    {
        String username = ((EditText)findViewById(R.id.signupUsernameText)).getText().toString();
        String password1 = ((EditText)findViewById(R.id.signupPasswordText)).getText().toString();
        String password2 = ((EditText)findViewById(R.id.signupPasswordRepeatText)).getText().toString();

        if (username.length() == 0)
        {
            Toast.makeText(MainActivity.this, "Please enter a Username", Toast.LENGTH_LONG).show();
            return;
        }

        if (password1.length() == 0)
        {
            Toast.makeText(MainActivity.this, "Please enter a Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (password2.length() == 0)
        {
            Toast.makeText(MainActivity.this, "Please repeat your Password", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password1.equals(password2))
        {
            Toast.makeText(MainActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.d("Sagev", "Threw error");
                } else {
                    Log.d("Sagev", "Switch view");
                    setContentView(R.layout.activity_home);
                    //Log.d("Sagev", "Modify greeting");
                    //((TextView) findViewById(R.id.homeWelcomeText)).setText("Welcome to Borrow, " + ((EditText) findViewById(R.id.signupUsernameText)).getText().toString());
                    Log.d("Sagev", "end if-block");
                }
            }
        });
        Log.d("Sagev", "Finish");
    }

    public void signin(View v)
    {
        String username = ((EditText)findViewById(R.id.signinUsernameText)).getText().toString();
        String password = ((EditText)findViewById(R.id.signinPasswordText)).getText().toString();

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                Log.d("Sagev", "Start if block");
                if (e != null) {
                    Log.d("Sagev", "Caught error");
                    Toast.makeText(MainActivity.this, e.getMessage(),
                            Toast.LENGTH_LONG).show();
                    Log.d("Sagev", "Threw error");
                } else {
                    Log.d("Sagev", "Switch view");
                    setContentView(R.layout.activity_home);
                    //Log.d("Sagev", "Modify greeting");
                    //((TextView) findViewById(R.id.homeWelcomeText)).setText("Welcome to Borrow, " + ((EditText) findViewById(R.id.signupUsernameText)).getText().toString());
                    Log.d("Sagev", "end if-block");
                }
            }
        });
        Log.d("Sagev", "Finish");
    }
}
