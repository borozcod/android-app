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
import android.widget.ImageButton;

import com.parse.ParseUser;

public class HomeActivity extends ActionBarActivity {
    private ImageButton buttonAddItem;
    private static Intent i;

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

    private void initializeActionBar() {
        // set title
        setTitle("Borrow :: Home");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeButtons() {
        buttonAddItem = (ImageButton) findViewById(R.id.homeButtonAddItem);

        buttonAddItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddItemActivity();
            }
        });
    }

    private void toAddItemActivity() {
        i = new Intent(getApplicationContext(), AddItemActivity.class);
        startActivity(i);
    }

    private void toMainActivity() {
        finish();
        i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private void toWelcomeActivity() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_signin, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.d("Sagev", "Action Bar Start");

        switch (id) {
            case R.id.action_settings: {
                Log.d("Sagev", "Settings");
                return true;
            }
            case android.R.id.home: {
                Log.d("Sagev", "Back");
                toWelcomeActivity();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}