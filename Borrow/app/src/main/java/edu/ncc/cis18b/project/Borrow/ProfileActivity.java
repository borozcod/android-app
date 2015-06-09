package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.parse.ParseUser;

public class ProfileActivity extends ActionBarActivity
{
    private ImageButton buttonBorrowed;
    private ImageButton buttonLent;
    private Intent i;
    private String user;
    private TextView textUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_profile);

        initializeButtons();
        initializeWidgets();
        initializeActionBar();

        addFonts();
    }

    private void initializeActionBar() {
        // set title
        setTitle("Borrow :: Profile");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeWidgets()
    {
        textUser = (TextView)findViewById(R.id.profileName);

        user = ParseUser.getCurrentUser().getString("desiredUserCase");
        textUser.setText(user);
    }

    private void initializeButtons()
    {
        buttonBorrowed = (ImageButton)findViewById(R.id.profileButtonBorrowed);
        buttonLent = (ImageButton)findViewById(R.id.profileButtonLent);

        buttonBorrowed.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toBorrowedListActivity();
            }
        });

        buttonLent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toLentListActivity();
            }
        });
    }

    private void toBorrowedListActivity()
    {
        i = new Intent(getApplicationContext(), BorrowedListActivity.class);
        startActivity(i);
    }

    private void toLentListActivity()
    {
        i = new Intent(getApplicationContext(), LentListActivity.class);
        startActivity(i);
    }

    private void toSettingsActivity()
    {
        i = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            toSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void addFonts() // TODO: Clean this method
    {
        TextView tv12=(TextView)findViewById(R.id.textView);
        Typeface face12=Typeface.createFromAsset(getAssets(),"fonts/Aventura-Bold.otf");
        tv12.setTypeface(face12);

        TextView tv=(TextView)findViewById(R.id.textView2);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/Aventura-Bold.otf");
        tv.setTypeface(face);

        TextView tv10=(TextView)findViewById(R.id.profileName);
        Typeface face10=Typeface.createFromAsset(getAssets(),"fonts/Aventura-Bold.otf");
        tv10.setTypeface(face10);
    }
}
