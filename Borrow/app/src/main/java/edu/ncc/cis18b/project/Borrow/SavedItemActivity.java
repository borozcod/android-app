package edu.ncc.cis18b.project.Borrow;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class SavedItemActivity extends ActionBarActivity
{
    private BorrowListFragment listFragment;
    private boolean databaseInitialized = false;
    private ArrayList<BorrowObject> savedObjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_saved_item);

        initializeActionBar();

        initializeList();

        queryLocalDatabase();
    }

    private void initializeActionBar() {
        // set title
        setTitle("Borrow :: Saved items");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeList()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        listFragment = new BorrowListFragment();
        ft.add(R.id.savedListCont, listFragment);

        ft.commit();
        // TODO: perhaps place these as member variables
        // TODO: may fix list loading issues
    }

    private void queryLocalDatabase() // TODO: replace, improve -- do something with this method
    {
        if (databaseInitialized)
            return;

        savedObjectList = new ArrayList<BorrowObject>();

        Log.d("Sagev", "queryLocalDatabase() start");

        ParseQuery<ParseObject> q = ParseQuery.getQuery("SavedObject");

        q.fromLocalDatastore();
        q.whereEqualTo("whoSaved", ParseUser.getCurrentUser());

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject p : list) {
                    BorrowObject bo = new BorrowObject();

                    try {
                        bo.fromParseObject(p.fetchIfNeeded().getParseObject("BorrowObject"));
                    } catch (ParseException pe) {
                        Log.d("Sagev", pe.getMessage());
                    }

                    savedObjectList.add(bo);
                    Log.d("Sagev", bo.toString());
                }
                listFragment.loadList(savedObjectList); // loads items into list
            } // end done()
        }); // end FindCallBack<ParseObject>

        databaseInitialized = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saved_item, menu);
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
                finish();
                return true;
            }
            case R.id.action_logout : {
                Log.d("Sagev", "ActionMenu logout");
                ParseUser.getCurrentUser().logOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
