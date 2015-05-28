package edu.ncc.cis18b.project.Borrow;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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
    protected static ArrayList<BorrowObject> savedObjectList = null;
    private boolean firstCreate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        String uid = ParseUser.getCurrentUser().getString("desiredUserCase") + "Object";

        Log.d("Sagev", "queryLocalDatabase() start");

        ParseQuery<ParseObject> q = ParseQuery.getQuery(uid);

        q.fromLocalDatastore();
        q.whereExists("name");

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                savedObjectList = new ArrayList<BorrowObject>();
                for (ParseObject p : list) {
                    BorrowObject bo = new BorrowObject();
                    bo.fromParseObject(p);
                    bo.markSaved();
                    savedObjectList.add(bo);
                    Log.d("Sagev", bo.toString());
                }
                listFragment.loadList(savedObjectList); // loads items into list
            } // end done()
        }); // end FindCallBack<ParseObject>

        databaseInitialized = true;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        if (firstCreate) {
            initializeActivity();
            firstCreate = false;
        }

        if (savedObjectList != null && listFragment != null && !savedObjectList.isEmpty())
            listFragment.loadList(savedObjectList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_saved_item, menu);
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            savedObjectList = null;
            finish();
        }
        return super.onKeyDown(keyCode, event);
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
                savedObjectList = null;
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
