package com.example.appleuser.borrow;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;


public class AddItemActivity extends ActionBarActivity
{
    private Button buttonUploadPicture;
    private Button buttonCancel;
    private Button buttonAddItem;
    private EditText editTextName;
    private EditText editTextDesc;
    private EditText editTextPrice;
    private Object pic; // temporary
    private StringBuilder errMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_add_new_item);

        initializeActionBar();

        initializeButtons();
    }

    private void initializeActionBar()
    {
        // set title
        setTitle("Borrow :: Add Item");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeButtons()
    {
        buttonAddItem = (Button)findViewById(R.id.addItemButtonAddItem);
        buttonCancel = (Button)findViewById(R.id.addItemButtonCancel);
        buttonUploadPicture = (Button)findViewById(R.id.addItemButtonUploadPicture);

        buttonAddItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeActivity();
            }
        });

        buttonUploadPicture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open camera and return picture?
                toast("Placeholder: Upload Picture onClick");
            }
        });
    }

    private void addItem()
    {
        BorrowObject bo;

        if (readUserInput()) {
            toast(errMsg.toString());
            return;
        } else {
            bo = getItem();
            bo.save();
            toast("Placeholder: Item created!");
            toHomeActivity();
        }
    }

    private boolean readUserInput()
    {
        errMsg = new StringBuilder("Issue(s) with item\n");
        boolean err = false;
        editTextName = (EditText)findViewById(R.id.addItemEditTextName);
        editTextDesc = (EditText)findViewById(R.id.addItemEditTextDesc);
        editTextPrice = (EditText)findViewById(R.id.addItemEditTextPrice);

        if (editTextName.length() == 0){
            errMsg.append("Item must have a name\n");
            err = true;
        }
        if (editTextDesc.length() == 0){
            errMsg.append("Item must have a description\n");
            err = true;
        }
        if (editTextPrice.length() == 0){
            errMsg.append("Item must have a price\n");
            err = true;
        }
        // if statement for picture

        return err;
    }

    private BorrowObject getItem()
    {
        Log.d("Sagev", "create obj");
        BorrowObject bo = new BorrowObject();

        Log.d("Sagev", "set name");
        bo.setName(editTextName.getText().toString());
        Log.d("Sagev", "set desc");
        bo.setDesc(editTextDesc.getText().toString());
        Log.d("Sagev", "set price");
        bo.setPrice(Double.parseDouble(editTextPrice.getText().toString()));
        // set parse user here
        // set picture here

        Log.d("Sagev", "return obj");
        return bo;
    }

    private void toHomeActivity()
    {
        finish();
    }

    private void toast(String s)
    {
        Toast.makeText(AddItemActivity.this, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_item, menu);
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
                toHomeActivity();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
