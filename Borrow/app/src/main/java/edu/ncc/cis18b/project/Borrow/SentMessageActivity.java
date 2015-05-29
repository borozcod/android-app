package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SentMessageActivity extends ActionBarActivity {

    private Button buttonToCompose;
    private Button buttonToSent;
    private Button buttonToReceived;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_sent_message);

        initializeActionBar();

        initializeButtons();
    }

    private void initializeActionBar() {
        // set title
        setTitle("MC :: Sent Msg");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeButtons()
    {
        buttonToReceived = (Button)findViewById(R.id.sentButtonReceived);
        buttonToSent = (Button)findViewById(R.id.sentButtonSent);
        buttonToCompose = (Button)findViewById(R.id.sentButtonCompose);

        buttonToCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toComposeActivity();
            }
        });

        buttonToSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSentActivity();
            }
        });

        buttonToReceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toReceivedActivity();
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_sent_message, menu);
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
