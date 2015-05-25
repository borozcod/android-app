package edu.ncc.cis18b.project.Borrow;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * @author Sage
 */
public class SignInDialog extends DialogFragment
{
    // interface
    public interface SignInListener
    {
        void signInSuccess(DialogFragment dialog);
    }

    private SignInListener listener;
    private EditText textUsername;
    private EditText textPassword;
    private AlertDialog.Builder builder;
    private View view;
    private final int USERNAME_LENGTH = 12;
    private final int PASSWORD_LENGTH = 32;
    private String username;
    private String password;
    private boolean closeDialog = false;

    @Override
    public void onAttach(Activity a)
    {
        super.onAttach(a);

        try {
            listener = (SignInListener)a;
        } catch (ClassCastException cce) {
            String err = a.toString() + " must implement SignInListener";
            throw new ClassCastException(err);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        if (!isConnected())
            toast("No internet connection");

        return initializeDialog();
    }

    @Override
    public void onStart()
    {
        super.onStart();    //super.onStart() is where dialog.show() is actually called on the underlying dialog, so we have to do it after this point
        AlertDialog d = (AlertDialog)getDialog();
        if(d != null)
        {
            Button positiveButton = (Button)d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (isConnected())
                        signIn();
                    else
                        noConnectionAlert();

                    if(closeDialog)
                        dismiss();
                }
            });
        }
    }

    private Dialog initializeDialog()
    {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        view = (View)inflater.inflate(R.layout.fragment_sign_in_dialog, null);

        // set layout
        builder.setView(view);

        // initialize buttons
        builder.setPositiveButton("Sign in", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // needed for older versions of android - nothing goes here
                // See: onStart()
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                cancel();
            }
        });

        initializeTextFields();

        // create & return dialog
        return builder.create();
    }

    private void initializeTextFields()
    {
        BorrowInputFilter borrowFilter = new BorrowInputFilter();

        InputFilter[] userFilter =
                {borrowFilter, new InputFilter.LengthFilter(USERNAME_LENGTH)};
        InputFilter[] passFilter =
                {borrowFilter, new InputFilter.LengthFilter(PASSWORD_LENGTH)};

        textUsername = (EditText)view.findViewById(R.id.signinDialogUsername);
        textPassword = (EditText)view.findViewById(R.id.signinDialogPassword);

        textUsername.setFilters(userFilter);
        textPassword.setFilters(passFilter);
    }

    private void signIn()
    {
        Log.d("Sagev", "Sign in");

        getTextFieldData();

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Logging in");
        dialog.show();

        ParseUser.logInInBackground(username.toLowerCase(), password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException pe) {
                dialog.dismiss();
                if (pe != null) {
                    toast(pe.getMessage());
                } else {
                    closeDialog = true;
                    listener.signInSuccess(SignInDialog.this);
                }
            }
        });
    }

    private void cancel()
    {
        Log.d("Sagev", "Cancel");
    }

    private void getTextFieldData()
    {
        username = textUsername.getText().toString();
        password = textPassword.getText().toString();
    }

    private void toast(String msg)
    {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    // Network state checks
    private boolean isConnected()
    {
        Context c = getActivity();
        ConnectivityManager cm;
        cm = (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void noConnectionAlert() {
        builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("No internet connection");
        builder.setTitle("Error");

        builder.create().show();
    }
}