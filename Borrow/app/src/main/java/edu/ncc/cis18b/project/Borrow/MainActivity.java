package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class MainActivity extends ActionBarActivity
        implements SignInDialog.SignInListener, SignUpDialog.SignUpListener
{
    private ImageButton buttonSignin;
    private ImageButton buttonSignup;
    private Intent i;
    private static boolean initializeParse = true;
    final private int SIGNUP_REQUEST = 0;
    final private int SIGNIN_REQUEST = 1;
    private SignInDialog signInDialog;
    private SignUpDialog signUpDialog;

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
        i = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(i);
        finish();
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
                launchSignInDialog();
                //toSigninActivity();
            }
        });

        buttonSignup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSignUpDialog();
                //toSignupActivity();
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

    private void launchSignInDialog()
    {
        signInDialog = new SignInDialog();
        signInDialog.show(getSupportFragmentManager(), "signin_dialog");
    }

    private void launchSignUpDialog()
    {
        signUpDialog = new SignUpDialog();
        signUpDialog.show(getSupportFragmentManager(), "signup_dialog");
    }

    private void toast(String msg)
    {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    // implemented methods
    @Override
    public void signInSuccess(DialogFragment dialog)
    {
        Log.d("Sagev", "Sign in success");
        toWelcomeActivity();
    }

    @Override
    public void signUpSuccess(DialogFragment dialog)
    {
        Log.d("Sagev", "Sign up success");
        toWelcomeActivity();
    }

    // menu methods
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
