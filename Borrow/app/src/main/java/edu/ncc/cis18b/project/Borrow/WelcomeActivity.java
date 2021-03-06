package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.parse.ParseUser;

public class WelcomeActivity extends ActionBarActivity
{
    private Button buttonContinue;
    private Button buttonSignOut;
    private TextView textUser;
    private Intent i;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeActivity();

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_welcome);

        initializeActionBar();

        initializeWidgets();
    }

    private void initializeActionBar()
    {
        // set title
        setTitle("Borrow");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);
    }

    private void initializeWidgets()
    {
        buttonContinue = (Button)findViewById(R.id.welcomeButtonContinue);
        buttonSignOut = (Button)findViewById(R.id.welcomeButtonSignOut);
        textUser = (TextView)findViewById(R.id.userText);

        user = ParseUser.getCurrentUser().getString("desiredUserCase");
        textUser.setText(user);

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeActivity();
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
    }

    private void toHomeActivity()
    {
        i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    private void toMainActivity()
    {
        ParseUser.logOut();

        i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

        finish();
    }

    private void toProfileActivity()
    {
        i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
    }

    private void toSettingsActivity()
    {
        i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings : {
                Log.d("Sagev", "Settings");
                toSettingsActivity();
                return true;
            }
            case R.id.action_logout : {
                Log.d("Sagev", "ActionMenu logout");
                ParseUser.getCurrentUser().logOut();
                i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            }
            case R.id.action_profile : {
                Log.d("Sagev", "Profile");
                toProfileActivity();
                return true;
            }
            case R.id.action_saved_objects_list : {
                i = new Intent(getApplicationContext(), SavedItemActivity.class);
                startActivity(i);
                return true;
            }
            case R.id.action_message_center : {
                i = new Intent(getApplicationContext(), ReceivedMessageActivity.class);
                startActivity(i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
