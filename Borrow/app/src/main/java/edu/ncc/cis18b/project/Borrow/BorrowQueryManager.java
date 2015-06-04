package edu.ncc.cis18b.project.Borrow;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by S on 5/30/2015.
 */
public class BorrowQueryManager
{
    // For initializing list on startup
    public static void queryLocalDatabaseAll() // TODO: replace, improve -- do something with this method
    {
        Log.d("Sagev", "queryLocalDatabaseAll() start");

        ParseQuery<ParseObject> q = ParseQuery.getQuery("BorrowItem"); //TODO: replace string literal with variable

        q.fromLocalDatastore();
        q.whereExists(BorrowItem.KEY_NAME);

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e)
            {
                ArrayList<BorrowItem> savedItemList = new ArrayList<BorrowItem>();

                for (ParseObject p : list)
                {
                    savedItemList.add((BorrowItem)p);
                    Log.d("Sagev", (p).toString());
                }

                SavedItemActivity.savedItemList = savedItemList;
            }
        });
    }

    // for updating fragment after startup
    public static void queryLocalDatabaseAll(final BorrowListFragment listFragment) // TODO: replace, improve -- do something with this method
    {
        Log.d("Sagev", "queryLocalDatabaseAll(BorrowListFragment) start");

        ParseQuery<ParseObject> q = ParseQuery.getQuery("BorrowItem"); //TODO: replace string literal with variable

        q.fromLocalDatastore();
        q.whereExists(BorrowItem.KEY_NAME);

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e)
            {
                ArrayList<BorrowItem> savedItemList = new ArrayList<BorrowItem>();

                for (ParseObject p : list)
                {
                    savedItemList.add((BorrowItem)p);
                    Log.d("Sagev", (p).toString());
                }

                listFragment.loadList(savedItemList, BorrowItem.class);
            }
        });
    }

    public static void queryBorrowItemAll(final BorrowListFragment listFragment) // TODO: replace, improve -- do something with this method
    {
        Log.d("Sagev", "queryBorrowItemAll(BorrowListFragment) start");

        ParseQuery<ParseObject> q = ParseQuery.getQuery("BorrowItem"); //TODO: replace string literal with variable

        q.whereExists(BorrowItem.KEY_NAME);

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e)
            {
                ArrayList<BorrowItem> borrowObjects = new ArrayList<BorrowItem>();

                for (ParseObject p : list) {
                    borrowObjects.add((BorrowItem)p);
                    Log.d("Sagev", p.toString());
                }

                listFragment.loadList(borrowObjects, BorrowItem.class);
            }
        });
    }

    public static void queryBorrowMessageUserReceived(final BorrowListFragment<BorrowMessage> listFragment) // TODO: replace, improve -- do something with this method
    {
        Log.d("Sagev", "queryBorrowMessageUserReceived(BorrowListFragment) start");

        ParseQuery<ParseObject> q = ParseQuery.getQuery("BorrowMessage"); //TODO: replace string literal with variable

        q.whereEqualTo(BorrowMessage.KEY_RECIPIENT, ParseUser.getCurrentUser());

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e)
            {
                ArrayList<BorrowMessage> borrowMessages = new ArrayList<BorrowMessage>();

                for (ParseObject p : list) {
                    borrowMessages.add((BorrowMessage)p);
                    Log.d("Sagev", p.toString());
                }

                listFragment.loadList(borrowMessages, BorrowMessage.class);
            }
        });
    }

    public static void queryBorrowMessageUserSent(final BorrowListFragment<BorrowMessage> listFragment) // TODO: replace, improve -- do something with this method
    {
        Log.d("Sagev", "queryBorrowMessageUserSent(BorrowListFragment) start");

        ParseQuery<ParseObject> q = ParseQuery.getQuery("BorrowMessage"); //TODO: replace string literal with variable

        q.whereEqualTo(BorrowMessage.KEY_SENDER, ParseUser.getCurrentUser());

        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e)
            {
                ArrayList<BorrowMessage> borrowMessages = new ArrayList<BorrowMessage>();

                for (ParseObject p : list) {
                    borrowMessages.add((BorrowMessage) p);
                    Log.d("Sagev", p.toString());
                }

                listFragment.loadList(borrowMessages, BorrowMessage.class);
            }
        });
    }

    public static void queryBorrowedObjects(final BorrowListFragment<BorrowItem> listFragment)
    {
        Log.d("Sagev", "queryBorrowedObjects(BorrowListFragment");

        // TODO: change other queries to be like this
        ParseQuery<BorrowItem> q = ParseQuery.getQuery(BorrowItem.class);

        // TODO: replace getCurrentUser with a variable
        q.whereEqualTo(BorrowItem.KEY_BORROWER,
                ParseUser.getCurrentUser().get(BorrowObject.KEY_DISPLAY_NAME));

        q.findInBackground(new FindCallback<BorrowItem>() {
            @Override
            public void done(List<BorrowItem> list, ParseException e) {
                ArrayList<BorrowItem> borrowedItems = new ArrayList<BorrowItem>();

                for (BorrowItem b : list)
                    borrowedItems.add(b);

                listFragment.loadList(borrowedItems, BorrowItem.class);
            }
        });
    }

    public static void queryLentObjects(final BorrowListFragment<BorrowItem> listFragment)
    {
        Log.d("Sagev", "queryLentObjects(BorrowListFragment)");

        // TODO: change other queries to be like this
        ParseQuery<BorrowItem> q = ParseQuery.getQuery(BorrowItem.class);

        // TODO: replace getCurrentUser with a variable
        q.whereEqualTo(BorrowItem.KEY_USER,
                ParseUser.getCurrentUser().getString(BorrowObject.KEY_DISPLAY_NAME));

        q.findInBackground(new FindCallback<BorrowItem>() {
            @Override
            public void done(List<BorrowItem> list, ParseException e) {
                ArrayList<BorrowItem> lentItems = new ArrayList<BorrowItem>();

                for (BorrowItem b : list)
                    if (b.getIsLent())
                        lentItems.add(b);

                listFragment.loadList(lentItems, BorrowItem.class);
            }
        });
    }
}
