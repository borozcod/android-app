package edu.ncc.cis18b.project.Borrow;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends ActionBarActivity
{
    private ImageButton buttonAddItem;
    private BorrowListFragment listFragment;
    private static Intent i;
    protected static ArrayList<BorrowObject> borrowObjects;
    // TODO: replace static array with method getter/setter
    private boolean databaseInitialized = false;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_home);

        initializeActionBar();

        initializeButtons();

        initializeList();

        queryParse();
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
        if (databaseInitialized)
            return;

        borrowObjects = new ArrayList<BorrowObject>();

        Log.d("Sagev", "queryParse() start");

        ParseQuery<ParseObject> q = ParseQuery.getQuery("BorrowObject");

        q.whereExists("pic");

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject p : list) {
                    BorrowObject bo = new BorrowObject();
                    bo.fromParseObject(p);
                    borrowObjects.add(bo);
                    Log.d("Sagev", bo.toString());
                }
                listFragment.loadList(borrowObjects); // loads items into list
            } // end done()
        }); // end FindCallBack<ParseObject>

        databaseInitialized = true;
    }

    // menu stuff
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