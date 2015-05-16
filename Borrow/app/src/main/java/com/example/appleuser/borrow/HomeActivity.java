package com.example.appleuser.borrow;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class HomeActivity extends ActionBarActivity {

    private Button buttonSignOut;
    private Button buttonAddItem;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity() {
        setContentView(R.layout.activity_home);

        initializeActionBar();

        initializeButtons();
    }

    private void initializeActionBar()
    {
        // set title
        setTitle("Borrow :: Home");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeButtons()
    {
        buttonSignOut = (Button)findViewById(R.id.homeButtonSignOut);
        buttonAddItem = (Button)findViewById(R.id.homeButtonAddItem);

        buttonSignOut.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        buttonAddItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddItemActivity();
            }
        });
    }

    private void toAddItemActivity()
    {
        i = new Intent(getApplicationContext(), AddItemActivity.class);
        startActivity(i);
    }

    private void signOut()
    {
        //signout
        toMainActivity();
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