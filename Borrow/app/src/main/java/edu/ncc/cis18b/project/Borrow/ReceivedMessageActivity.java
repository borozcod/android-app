package edu.ncc.cis18b.project.Borrow;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class ReceivedMessageActivity extends ActionBarActivity {

    private Button buttonToCompose;
    private Button buttonToSent;
    private Button buttonToReceived;
    private Intent i;
    private BorrowListFragmentMessages<BorrowMessage> listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActionBar();

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_received_message);

        initializeButtons();

        initializeList();

        queryParse();
    }

    private void initializeActionBar() {
        // set title
        setTitle("MC :: Received Msg");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeButtons()
    {
        buttonToReceived = (Button)findViewById(R.id.receivedButtonReceived);
        buttonToSent = (Button)findViewById(R.id.receivedButtonSent);
        buttonToCompose = (Button)findViewById(R.id.receivedButtonCompose);

        buttonToCompose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toComposeActivity();
            }
        });

        buttonToSent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toSentActivity();
            }
        });

        buttonToReceived.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toReceivedActivity();
            }
        });
    }

    private void initializeList()
    {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        listFragment = new BorrowListFragmentMessages<>();
        ft.add(R.id.recievedMessageContainer, listFragment);

        ft.commit();
        // TODO: perhaps place these as member variables
        // TODO: may fix list loading issues
    }

    private void queryParse() // TODO: replace, improve -- do something with this method
    {
        BorrowQueryManager.queryBorrowMessageUserReceived(listFragment);
    }

    private void toComposeActivity()
    {
        i = new Intent(getApplicationContext(), ComposeMessageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

    private void toSentActivity()
    {
        i = new Intent(getApplicationContext(), SentMessageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

    private void toReceivedActivity()
    {
        i = new Intent(getApplicationContext(), ReceivedMessageActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(i);
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recieved_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
