package com.example.appleuser.borrow;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity //TODO: rename this
{
    private ImageButton buttonAddItem;
    private static Intent i;
    protected static ArrayList<BorrowObject> values = new ArrayList<BorrowObject>();

    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_my_list);

        initializeButtons();

        //temporary methods
        queryParse();
        setList();
    }

    private void initializeButtons()
    {
        buttonAddItem = (ImageButton)findViewById(R.id.myListButtonAddItem);

        buttonAddItem.setOnClickListener(new View.OnClickListener() {
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

    private void toMainActivity()
    {
        finish();
        i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private void toWelcomeActivity()
    {
        finish();
    }

    // myList methods
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }

    private void setList() // TODO: replace this method
    {
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);

        setListAdapter(adapter);
    }

    private void queryParse() //test query
    {
        ParseQuery<ParseObject> q = ParseQuery.getQuery("BorrowObject");
        q.whereExists("pic");
        q.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                for (ParseObject p : list) {
                    BorrowObject bo = new BorrowObject();
                    bo.fromParseObject(p);
                    values.add(bo);
                }
            }
        });
    }
}