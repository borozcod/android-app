package edu.ncc.cis18b.project.Borrow;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ActionBarActivity
{
    private ImageButton buttonAddItem;
    private BorrowListFragment<BorrowItem> listFragment;
    private static Intent i;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_home);

        initializeActionBar();

        initializeButtons();

        initializeList();

        if (isConnected())
            queryParse();
        else
            toast("No internet connection");
        // TODO: if a new object is created before Database is initialized
        // TODO: Will probably crash app - fix
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
        buttonAddItem = (ImageButton)findViewById(R.id.homeButtonAddItem);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
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


    private void toProfileActivity() {

        i = new Intent(getApplicationContext(), ProfileActivity.class);
        startActivity(i);
    }

    private void toWelcomeActivity() {
        finish();
    }

    private void toast(String msg)
    {
        Toast.makeText(HomeActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    private void initializeList()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        listFragment = new BorrowListFragment();
        ft.add(R.id.homeFragmentList, listFragment);

        ft.commit();
        // TODO: perhaps place these as member variables
        // TODO: may fix list loading issues
    }

    private void queryParse() // TODO: replace, improve -- do something with this method
    {
        BorrowQueryManager.queryBorrowItemAll(listFragment);
    }

    private boolean isConnected() // checks internet connection
    {
        Context c = getApplication();
        ConnectivityManager cm;
        cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        initializeActivity();
    }

    // menu stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Log.d("Sagev", "Action Bar Start");

        switch (id) {
            case R.id.action_settings : {
                Log.d("Sagev", "Settings");
                return true;
            }
            case android.R.id.home : {
                Log.d("Sagev", "Back");
                toWelcomeActivity();
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
        }

        return super.onOptionsItemSelected(item);
    }
}