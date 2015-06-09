package edu.ncc.cis18b.project.Borrow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DecimalFormat;


public class BorrowObjectViewActivity extends ActionBarActivity {
    private TextView viewName;
    private TextView viewDesc;
    private TextView viewPrice;
    private TextView viewUser;
    private ImageView viewPic;
    private ImageButton buttonContact;
    private ImageButton buttonSave;
    private ImageButton buttonBorrow;
    protected static BorrowObject borrowItem;
    private Intent i;
    private final int WIDTH = 224;
    private final int HEIGHT = 126;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity() {
        setContentView(R.layout.activity_borrow_object_view);

        initializeActionBar();

        initializeWidgets();

        addFonts();
    }

    private void initializeActionBar() {
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
        String formattedPrice = df.format(((BorrowItem) borrowItem).getPrice());

        viewName = (TextView) findViewById(R.id.viewNameText);
        viewDesc = (TextView) findViewById(R.id.viewDescText);
        viewPrice = (TextView) findViewById(R.id.viewPriceText);
        viewUser = (TextView) findViewById(R.id.viewUserText);
        viewPic = (ImageView) findViewById(R.id.viewPicImage);

        viewName.setText(((BorrowItem) borrowItem).getName());
        viewDesc.setText(((BorrowItem) borrowItem).getDesc());
        viewPrice.setText(formattedPrice);
        viewUser.setText(((BorrowItem) borrowItem).getUser());
        viewPic.setImageBitmap(borrowItem.getPic(WIDTH, HEIGHT)); // TODO: replace this?

        initializeSaveButton();
        initializeDeleteButton();
        initializeBorrowButton();
    }

    private void initializeSaveButton() {
        buttonSave = (ImageButton) findViewById(R.id.viewButtonSaveItem);

        if (SavedItemActivity.savedItemList.contains(borrowItem)) { // TODO: FIX THIS!!!!!
            //buttonSave.setText("Unsave item");
            buttonSave.setImageDrawable(getResources().getDrawable(R.drawable.unsave));
            buttonSave.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    unsaveObject();
                }
            });
        } else {
            //buttonSave.setText("Save item");
            buttonSave.setImageDrawable(getResources().getDrawable(R.drawable.save_button));
            buttonSave.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveObject();
                }
            });
        }
    }

    private void initializeDeleteButton() {
        buttonContact = (ImageButton) findViewById(R.id.viewButtonContactOwner);

        if (((BorrowItem) borrowItem).getUser().toLowerCase().equals(ParseUser.getCurrentUser().getUsername())) {
            //buttonContact.setText("Delete item");
            buttonContact.setImageDrawable(getResources().getDrawable(R.drawable.cancel_button)); // Keep in mind this button will replace Contact button!
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

    private void initializeBorrowButton()
    {
        buttonBorrow = (ImageButton) findViewById(R.id.viewButtonBorrow);

        String itemOwner = ((BorrowItem) borrowItem).getUser().toLowerCase();
        String currentUser = ParseUser.getCurrentUser().getUsername();

        boolean isLent = ((BorrowItem) borrowItem).getIsLent();
        boolean isSaved = ((BorrowItem) borrowItem).isSaved();
        boolean currentUserIsOwner = itemOwner.equals(currentUser);

        if (currentUserIsOwner && isLent) {
            buttonBorrow.setImageDrawable(getResources().getDrawable(R.drawable.returned_itme));
            buttonBorrow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnObject();
                }
            });
        } else if (isLent || isSaved || currentUserIsOwner) {
            buttonBorrow.setVisibility(View.INVISIBLE);
        } else {
            buttonBorrow.setImageDrawable(getResources().getDrawable(R.drawable.borrow_button));
            buttonBorrow.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    borrowObject();
                }
            });
        }
    }

    private void deleteObject()
    {
        if (SavedItemActivity.savedItemList.contains(borrowItem))
            SavedItemActivity.savedItemList.remove(borrowItem);

        final ProgressDialog dialog = new ProgressDialog(BorrowObjectViewActivity.this);
        dialog.setMessage("Deleting item");
        dialog.show();

        borrowItem.deleteInBackground(new DeleteCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                toast("Item deleted!");
                finish();
            }
        });
    }

    private void borrowObject()
    {
        ((BorrowItem)borrowItem).setBorrower(ParseUser.getCurrentUser());
        ((BorrowItem)borrowItem).setIsLent(true);
        borrowItem.saveInBackground();

        toast("Item borrowed!");

        initializeBorrowButton();

        AlertDialog alertDialog = new AlertDialog.Builder(BorrowObjectViewActivity.this).create();
        alertDialog.setTitle("Item Borrowed");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void returnObject()
    {
        ((BorrowItem)borrowItem).setBorrower(null);
        ((BorrowItem) borrowItem).setIsLent(false);
        borrowItem.saveInBackground();

        toast("Item returned!");

        initializeBorrowButton();
        AlertDialog alertDialog = new AlertDialog.Builder(BorrowObjectViewActivity.this).create();
        alertDialog.setTitle("Item Returned");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
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

        SavedItemActivity.savedItemList.add((BorrowItem)borrowItem); //TODO: fix this

        initializeSaveButton();

        toast("Added item to saved list");

        AlertDialog alertDialog = new AlertDialog.Builder(BorrowObjectViewActivity.this).create();
        alertDialog.setTitle("Item Saved");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }

    private void unsaveObject()
    {
        borrowItem.unpinInBackground();
        SavedItemActivity.savedItemList.remove(borrowItem);

        initializeSaveButton();

        toast("Removed item from saved list");

        AlertDialog alertDialog = new AlertDialog.Builder(BorrowObjectViewActivity.this).create();
        alertDialog.setTitle("Item Unsaved");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

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
    private void addFonts() // TODO: Clean this method
    {
        TextView tv12=(TextView)findViewById(R.id.textView14);
        Typeface face12=Typeface.createFromAsset(getAssets(),"fonts/Aventura-Bold.otf");
        tv12.setTypeface(face12);

        TextView tv=(TextView)findViewById(R.id.textView15);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Aventura-Bold.otf");
        tv.setTypeface(face);

        TextView tv11=(TextView)findViewById(R.id.textView16);
        Typeface face11=Typeface.createFromAsset(getAssets(),"fonts/Aventura-Bold.otf");
        tv11.setTypeface(face11);

//        TextView tv10=(TextView)findViewById(R.id.textView17);
//        Typeface face10=Typeface.createFromAsset(getAssets(),"fonts/Aventura-Bold.otf");
//        tv10.setTypeface(face10);

        TextView tv9=(TextView)findViewById(R.id.textView13);
        Typeface face9=Typeface.createFromAsset(getAssets(),"fonts/Aventura-Bold.otf");
        tv9.setTypeface(face9);
    }
}
