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

                listFragment.loadList(savedItemList);
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

                listFragment.loadList(borrowObjects);
            }
        });
    }

    public static void queryBorrowMessageUserReceived(final BorrowListFragmentMessages<BorrowMessage> listFragment) // TODO: replace, improve -- do something with this method
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

                listFragment.loadList(borrowMessages);
            }
        });
    }

    public static void queryBorrowMessageUserSent(final BorrowListFragmentMessages<BorrowMessage> listFragment) // TODO: replace, improve -- do something with this method
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

                listFragment.loadList(borrowMessages);
            }
        });
    }
}
