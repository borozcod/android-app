package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.parse.Parse;
import com.parse.ParseUser;

public class MainActivity extends ActionBarActivity
{
    private ImageButton buttonSignin;
    private ImageButton buttonSignup;
    private Intent i;
    private static boolean initializeParse = true;
    final private int SIGNUP_REQUEST = 0;
    final private int SIGNIN_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        initializeDatabase();

        getUserStatus();

        setContentView(R.layout.activity_main);

        initializeActionBar();

        initializeButtons();
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
                toSigninActivity();
            }
        });

        buttonSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toSignupActivity();
            }
        });

    }

    private void initializeDatabase()
    {
        if (initializeParse) {
            // connects to Parse database: borrowappandroid@gmail.com
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, "xEVrr9KpRW07GEYHlph9pkrRAdeAXFg5xZllfaN8", "OTu2sE7Dr72YZQazWAVkPPVN9cDuC1kBSB23Nfau");
            initializeParse = false;
        }
    }

    private void toSigninActivity()
    {
        i = new Intent(getApplicationContext(), SigninActivity.class);
        Log.d("Sagev", "start activity signin");
        startActivityForResult(i, SIGNIN_REQUEST);
    }

    private void toSignupActivity()
    {
        i = new Intent(getApplicationContext(), SignupActivity.class);
        Log.d("Sagev", "start activity signup");
        startActivityForResult(i, SIGNUP_REQUEST);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        Log.d("Sagev", "recieved result");
        if (requestCode == SIGNIN_REQUEST) {
            Log.d("Sagev", "request caught");
            if (resultCode == RESULT_OK) {
                Log.d("Sagev", "Okay");
                i = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("Sagev", "canceled");
            } else {
                Log.d("Sagev", "err");
            }
        }

        Log.d("Sagev", "recieved result");
        if (requestCode == SIGNUP_REQUEST) {
            Log.d("Sagev", "request caught");
            if (resultCode == RESULT_OK) {
                Log.d("Sagev", "Okay");
                i = new Intent(getApplicationContext(), WelcomeActivity.class);
                startActivity(i);
                finish();
            } else if (resultCode == RESULT_CANCELED) {
                Log.d("Sagev", "canceled");
            } else {
                Log.d("Sagev", "err");
            }
        }
    }
}
