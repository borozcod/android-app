package com.example.appleuser.borrow;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyListActivity extends ListActivity {
    private ImageButton buttonAddItem;
    private static Intent i;
    protected static ArrayList<BorrowObject> values = new ArrayList<BorrowObject>();

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_my_list);

        initializeButtons();

        createTestObject(); //temporary
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
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }

    private void createTestObject()
    {
        MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, values);

        setListAdapter(adapter);
    }
}