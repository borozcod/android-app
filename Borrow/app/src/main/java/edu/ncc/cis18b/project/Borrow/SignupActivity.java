package edu.ncc.cis18b.project.Borrow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends ActionBarActivity
{
    private Button buttonCreateAccount;
    private Button buttonCancel;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextPasswordRepeat;
    private Intent i;
    private String username;
    private String password;
    private String passwordRepeat;
    private StringBuilder errMsg;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initializeActivity();
    }

    private void initializeActivity()
    {
        setContentView(R.layout.activity_signup);

        if (!isConnected())
            toast("No internet connection");

        initializeActionBar();

        initializeButtons();
        initializeTextFields();
    }

    private void initializeActionBar()
    {
        // set title
        setTitle("Borrow :: Sign Up");

        // set icon
        //getActionBar().setIcon(R.drawable.PICTURE_NAME);

        // set back button
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeButtons()
    {
        buttonCreateAccount = (Button)findViewById(R.id.signupButtonCreateAccount);
        buttonCancel = (Button)findViewById(R.id.signupButtonCancel);

        buttonCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected())
                    signup();
                else
                    noConnectionAlert();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMainActivity();
            }
        });
    }

    private void initializeTextFields()
    {
        BorrowInputFilter borrowFilter = new BorrowInputFilter();

        InputFilter[] userFilter =
                { borrowFilter, new InputFilter.LengthFilter(12) };
        InputFilter[] passFilter =
                { borrowFilter, new InputFilter.LengthFilter(32) };

        editTextUsername = (EditText)findViewById(R.id.signupUsernameText);
        editTextPassword = (EditText)findViewById(R.id.signupPasswordText);
        editTextPasswordRepeat = (EditText)findViewById(R.id.signupPasswordRepeatText);

        editTextUsername.setFilters(userFilter);
        editTextPassword.setFilters(passFilter);
        editTextPasswordRepeat.setFilters(passFilter);
    }

    private void signup()
    {
        getTextFields();

        if (isInputValid())
            createAccount();
        else
            toast(errMsg.toString());
    }

    private void createAccount()
    {
        user = new ParseUser();

        final ProgressDialog dialog = new ProgressDialog(SignupActivity.this);
        dialog.setMessage("Creating account");
        dialog.show();

        user.setUsername(username.toLowerCase());
        user.setPassword(password);

        user.put("desiredUserCase", username);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException pe) {
                dialog.dismiss();
                if (pe != null) {
                    toast(pe.getMessage());
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void getTextFields()
    {
        username = editTextUsername.getText().toString();
        password = editTextPassword.getText().toString();
        passwordRepeat = editTextPasswordRepeat.getText().toString();
    }

    private boolean isInputValid()
    {
        errMsg = new StringBuilder();
        boolean isValid = true;

        if (username.length() == 0) {
            isValid = false;
            errMsg.append("Please enter a username\n");
        }
        if (password.length() == 0) {
            isValid = false;
            errMsg.append("Please enter a password\n");
        } else if (passwordRepeat.length() == 0) {
            isValid = false;
            errMsg.append("Please repeat your password\n");
        } else if (!password.equals(passwordRepeat)) {
            isValid = false;
            errMsg.append("Passwords do not match");
        }

        return isValid;
    }

    private void toast(String msg)
    {
        Toast.makeText(SignupActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    private void toWelcomeActivity()
    {
        finish();
        i = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(i);
    }

    private void toHomeActivity()
    {
        finish();
        i = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(i);
    }

    private void toMainActivity()
    {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        Log.d("Sagev", "Action Bar Start");

        switch (id) {
            case R.id.action_settings : {
                Log.d("Sagev", "Settings");
                return true;
            }
            case android.R.id.home : {
                Log.d("Sagev", "Back");
                toMainActivity();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isConnected() // checks internet connection
    {
        Context c = getApplication();
        ConnectivityManager cm;
        cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void noConnectionAlert()
    {
        AlertDialog dialog;
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);

        builder.setMessage("No internet connection");
        builder.setTitle("Error");

        dialog = builder.create();

        dialog.show();
    }
}
