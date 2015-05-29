package edu.ncc.cis18b.project.Borrow;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Fragment;
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
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.ByteArrayOutputStream;


/**
 * @author Sage
 */
public class SignUpDialog extends DialogFragment
{
    // interface
    public interface SignUpListener
    {
        void signUpSuccess(DialogFragment dialog);
    }

    private SignUpListener listener;
    private EditText textUsername;
    private EditText textPassword;
    private EditText textPasswordRepeat;
    private AlertDialog.Builder builder;
    private View view;
    private final int USERNAME_LENGTH = 12;
    private final int PASSWORD_LENGTH = 32;
    private String username;
    private String password;
    private String passwordRepeat;
    private boolean closeDialog = false;
    private StringBuilder errMsg;
    private ParseUser user;

    public SignUpDialog(){}

    @Override
    public void onAttach(Activity a)
    {
        super.onAttach(a);

        try {
            listener = (SignUpListener)a;
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
        super.onStart();
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
                        signUp();
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

        view = (View)inflater.inflate(R.layout.fragment_sign_up_dialog, null);

        // set layout
        builder.setView(view);

        // initialize buttons
        builder.setPositiveButton("Sign up", new DialogInterface.OnClickListener() {
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

        textUsername = (EditText)view.findViewById(R.id.signupDialogUsername);
        textPassword = (EditText)view.findViewById(R.id.signupDialogPassword);
        textPasswordRepeat = (EditText)view.findViewById((R.id.signupDialogPasswordRepeat));

        textUsername.setFilters(userFilter);
        textPassword.setFilters(passFilter);
        textPasswordRepeat.setFilters(passFilter);
    }

    private void signUp()
    {
        Log.d("Sagev", "Sign up");

        getTextFieldData();

        if (inputIsValid())
            createAccount();
        else
            toast(errMsg.toString());
    }

    private void cancel()
    {
        Log.d("Sagev", "Cancel");
    }

    private void getTextFieldData()
    {
        username = textUsername.getText().toString();
        password = textPassword.getText().toString();
        passwordRepeat = textPasswordRepeat.getText().toString();
    }

    private boolean inputIsValid()
    {
        errMsg = new StringBuilder();
        errMsg.append("Please correct the following errors:");
        boolean isValid = true;

        if (username.length() == 0) {
            isValid = false;
            errMsg.append("\nNo username entered");
        }
        if (password.length() == 0) {
            isValid = false;
            errMsg.append("\nNo password entered");
        }
        if (passwordRepeat.length() == 0) {
            isValid = false;
            errMsg.append("\nNo repeat password entered");
        }
        if (!password.equals(passwordRepeat)) {
            isValid = false;
            errMsg.append("\nPasswords do not match");
        }

        return isValid;
    }

    private void createAccount()
    {
        user = new ParseUser();

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Signing up");
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
                    closeDialog = true;
                    setDefaultProfilePicture();
                    listener.signUpSuccess(SignUpDialog.this);
                }
            }
        });
    }

    private void setDefaultProfilePicture() // TODO: Temporary - replace!
    {
        Drawable d = getResources().getDrawable(R.drawable.camera);
        Bitmap b = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] data = stream.toByteArray();

        ParseFile pf = new ParseFile("pic", data);
        ParseUser.getCurrentUser().put("pic", pf);
        ParseUser.getCurrentUser().saveInBackground();
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
