package edu.ncc.cis18b.project.Borrow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class ComposeMessageActivity extends ActionBarActivity {

    private Button buttonToCompose;
    private Button buttonToSent;
    private Button buttonToReceived;
    private ImageButton buttonCancel;
    private ImageButton buttonSend;
    private EditText editTextRecipient;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private Intent i;
    private String message;
    private String subject;
    private String recipient;
    private StringBuilder errMsg;
    protected static String requestedRecipient = null;

    InterstitialAd mInterstitialAd;
    Button mNewGameButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeActivity();

        mNewGameButton = (Button) findViewById(R.id.newgame_button);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    //Begin Game, continue with app
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                //Begin Game, continue with app
            }
        });

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.loadAd(adRequest);

    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_compose_message);

        initializeActionBar();

        initializeTextFields();

        initializeButtons();

        if (!isConnected())
            toast("No internet connection");
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
        editTextSubject = (EditText)findViewById(R.id.composeEditTextSubject);
        editTextRecipient = (EditText)findViewById(R.id.composeEditTextTo);

        if (requestedRecipient != null)
            editTextRecipient.setText(requestedRecipient);

        editTextRecipient.setFilters(userFilter);
    }

    private void initializeButtons()
    {
        buttonToReceived = (Button)findViewById(R.id.composeButtonReceived);
        buttonToSent = (Button)findViewById(R.id.composeButtonSent);
        buttonToCompose = (Button)findViewById(R.id.composeButtonCompose);
        buttonCancel = (ImageButton)findViewById(R.id.composeButtonCancel);
        buttonSend = (ImageButton)findViewById(R.id.composeButtonSend);

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
                if (isConnected())
                    send();
                else
                    noConnectionAlert();
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
        finish();
    }

    private void send()
    {
        Log.d("Sagev", "Send");

        getValuesFromTextFields();

        if (!userInputIsValid()) {
            toast(errMsg.toString());
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Querying database");
        dialog.show();

        // TODO: Add following to BorrowQueryManager
        ParseQuery<ParseUser> q = ParseUser.getQuery();

        q.whereEqualTo("username", recipient.toLowerCase());

        q.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> list, ParseException pe) {
                dialog.dismiss();
                if (list.isEmpty())
                    toast("Recipient does not exist");
                else
                    sendMessage(list.get(0));
            } // end done()
        }); // end FindCallBack<ParseObject>
    }

    private void sendMessage(ParseUser recipient)
    {
        toast("Sent message to " + this.recipient);

        BorrowMessage message = new BorrowMessage();

        message.setMessage(this.message);
        message.setSender(ParseUser.getCurrentUser());
        message.setRecipient(recipient);
        message.setSubject(subject);
        try {
            message.setPic(recipient.getParseFile(BorrowObject.KEY_PIC).getData());
        } catch (ParseException pe) {
            Log.d("Sagev", pe.getMessage());
        }
        
        message.saveInBackground();
    }

    private boolean userInputIsValid()
    {
        errMsg = new StringBuilder();
        errMsg.append("Please correct the following errors:");
        boolean isValid = true;

        if (recipient.length() == 0) {
            isValid = false;
            errMsg.append("\nNo recipient entered");
        }
        if (subject.length() == 0) {
            isValid = false;
            errMsg.append("\nNo subject entered");
        }
        if (message.length() == 0) {
            isValid = false;
            errMsg.append("\nNo message entered");
        }

        return isValid;
    }

    private void getValuesFromTextFields()
    {
        message = editTextMessage.getText().toString();
        subject = editTextSubject.getText().toString();
        recipient = editTextRecipient.getText().toString();
    }

    private void toast(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    // Network state checks
    private boolean isConnected()
    {
        Context c = this;
        ConnectivityManager cm;
        cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void noConnectionAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("No internet connection");
        builder.setTitle("Error");

        builder.create().show();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        requestedRecipient = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compose_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings: {
                Log.d("Sagev", "Settings");
                return true;
            }
            case android.R.id.home: {
                Log.d("Sagev", "Back");
                finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
