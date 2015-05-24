package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;

import java.text.DecimalFormat;


public class BorrowObjectViewActivity extends ActionBarActivity
{
    private TextView viewName;
    private TextView viewDesc;
    private TextView viewPrice;
    private TextView viewUser;
    private ImageView viewPic;
    protected static BorrowObject bo;

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
        setTitle("Borrow :: Item#" + bo.getName());

        // TODO: set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeWidgets()
    {
        DecimalFormat df = new DecimalFormat("$,###.00");
        String formattedPrice = df.format(bo.getPrice());

        viewName = (TextView)findViewById(R.id.viewNameText);
        viewDesc = (TextView)findViewById(R.id.viewDescText);
        viewPrice = (TextView)findViewById(R.id.viewPriceText);
        viewUser = (TextView)findViewById(R.id.viewUserText);
        viewPic = (ImageView)findViewById(R.id.viewPicImage);

        viewName.setText(bo.getName());
        viewDesc.setText(bo.getDesc());
        viewPrice.setText(formattedPrice);
        viewUser.setText(bo.getUser().getUsername());

        try {
            viewPic.setImageBitmap(bo.getPic());
        } catch (ParseException pe) {
            Log.d("Sagev", pe.getMessage());
        }
    }

    private void toHomeActivity()
    {
        finish();
    }

    //menu stuff
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_add_new_item, menu);
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
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
            case R.id.action_profile : {
                Log.d("Sagev", "Profile");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
