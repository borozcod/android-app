package edu.ncc.cis18b.project.Borrow;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ComposeMessageActivity extends ActionBarActivity {

    private Button buttonToCompose;
    private Button buttonToSent;
    private Button buttonToReceived;
    private Button buttonCancel;
    private Button buttonSend;
    private EditText editTextRecipient;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_compose_message);

        initializeActionBar();

        initializeTextFields();

        initializeButtons();
    }

    private void initializeActionBar() {
        // set title
        setTitle("MC :: Compose Msg");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeTextFields()
    {
        BorrowInputFilter borrowFilter = new BorrowInputFilter();
        InputFilter[] userFilter = {borrowFilter};

        editTextMessage = (EditText)findViewById(R.id.composeEditTextMessage);
        editTextRecipient = (EditText)findViewById(R.id.composeEditTextTo);
        editTextSubject = (EditText)findViewById(R.id.composeEditTextSubject);

        editTextRecipient.setFilters(userFilter);
    }

    private void initializeButtons()
    {
        buttonToReceived = (Button)findViewById(R.id.composeButtonReceived);
        buttonToSent = (Button)findViewById(R.id.composeButtonSent);
        buttonToCompose = (Button)findViewById(R.id.composeButtonCompose);
        buttonCancel = (Button)findViewById(R.id.composeButtonCancel);
        buttonSend = (Button)findViewById(R.id.composeButtonSend);

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

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send();
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

    private void cancel()
    {
        Log.d("Sagev", "Cancel");
    }

    private void send()
    {
        Log.d("Sagev", "Send");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose_message, menu);
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
