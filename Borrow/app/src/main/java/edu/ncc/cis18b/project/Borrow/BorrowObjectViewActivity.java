package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DecimalFormat;


public class BorrowObjectViewActivity extends ActionBarActivity
{
    private TextView viewName;
    private TextView viewDesc;
    private TextView viewPrice;
    private TextView viewUser;
    private ImageView viewPic;
    private Button buttonContact;
    private Button buttonSave;
    protected static BorrowObject borrowItem;
    private Intent i;
    private final int WIDTH = 224;
    private final int HEIGHT = 126;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_borrow_object_view);

        initializeActionBar();

        initializeWidgets();
    }

    private void initializeActionBar()
    {
        // set title
        setTitle("Borrow :: Item#" + ((BorrowItem) borrowItem).getName());

        // TODO: set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeWidgets() // TODO: seperate this
    {
        DecimalFormat df = new DecimalFormat("$,###.00"); // TODO: fix this?
        String formattedPrice = df.format(((BorrowItem)borrowItem).getPrice());

        viewName = (TextView)findViewById(R.id.viewNameText);
        viewDesc = (TextView)findViewById(R.id.viewDescText);
        viewPrice = (TextView)findViewById(R.id.viewPriceText);
        viewUser = (TextView)findViewById(R.id.viewUserText);
        viewPic = (ImageView)findViewById(R.id.viewPicImage);

        viewName.setText(((BorrowItem)borrowItem).getName());
        viewDesc.setText(((BorrowItem)borrowItem).getDesc());
        viewPrice.setText(formattedPrice);
        viewUser.setText(((BorrowItem)borrowItem).getUser());
        viewPic.setImageBitmap(borrowItem.getPic(WIDTH, HEIGHT)); // TODO: replace this?

        initializeSaveButton();
        initializeDeleteButton();
    }

    private void initializeSaveButton()
    {
        buttonSave = (Button)findViewById(R.id.viewButtonSaveItem);

        if (SavedItemActivity.savedItemList.contains(borrowItem)) { // TODO: FIX THIS!!!!!
            buttonSave.setText("Unsave item");
            buttonSave.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    unsaveObject();
                }
            });
        } else {
            buttonSave.setText("Save item");
            buttonSave.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveObject();
                }
            });
        }
    }

    private void initializeDeleteButton()
    {
        buttonContact = (Button)findViewById(R.id.viewButtonContactOwner);

        if (((BorrowItem)borrowItem).getUser().toLowerCase().equals(ParseUser.getCurrentUser().getUsername())) {
            buttonContact.setText("Delete item");
            buttonContact.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteObject();
                }
            });
        } else {
            buttonContact.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    toComposeMessageActivity();
                }
            });
        }
    }

    private void deleteObject()
    {
        if (SavedItemActivity.savedItemList.contains(borrowItem))
            SavedItemActivity.savedItemList.remove(borrowItem);

        borrowItem.deleteInBackground(); // TODO: replace with deleteEventually()?

        toast("Item may take a few moments to be deleted");
        finish();
    }

    private void toast(String msg)
    {
        Toast.makeText(BorrowObjectViewActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    private void toHomeActivity()
    {
        finish();
    }

    // saves to local database
    private void saveObject()
    {
        borrowItem.pinInBackground();

        SavedItemActivity.savedItemList.add((BorrowItem)borrowItem);

        initializeSaveButton();
    }

    private void unsaveObject()
    {
        borrowItem.unpinInBackground();
        SavedItemActivity.savedItemList.remove(borrowItem);

        initializeSaveButton();
    }

    private void toComposeMessageActivity()
    {
        ComposeMessageActivity.requestedRecipient = ((BorrowItem)borrowItem).getUser();
        i = new Intent(getApplicationContext(), ComposeMessageActivity.class);
        startActivity(i);
    }

    //menu stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_borrow_object_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        Log.d("Sagev", "Action Bar Start");

        switch (id)
        {
            case R.id.action_settings : {
                Log.d("Sagev", "Settings");
                return true;
            }
            case android.R.id.home : {
                Log.d("Sagev", "Back");
                toHomeActivity();
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
        }

        return super.onOptionsItemSelected(item);
    }
}
