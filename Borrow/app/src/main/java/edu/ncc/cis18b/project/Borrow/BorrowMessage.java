package edu.ncc.cis18b.project.Borrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.ParseClassName;

import java.io.ByteArrayOutputStream;

/**
 * Created by S on 5/29/2015.
 */
@ParseClassName("BorrowMessage")
public class BorrowMessage extends BorrowObject
{
    public final static String KEY_SUBJECT = "subject";
    public final static String KEY_RECIPIENT = "recipient";
    public final static String KEY_SENDER = "sender";
    public final static String KEY_MESSAGE = "message";

    public BorrowMessage(){}

    public void setSubject(String subject){this.put(KEY_SUBJECT, subject);}
    public String getSubject(){return this.getString(KEY_SUBJECT);}

    public void setRecipient(ParseUser recipient){this.put(KEY_RECIPIENT, recipient);}
    public ParseUser getRecipient(){return this.getParseUser(KEY_RECIPIENT);}

    public void setSender(ParseUser sender){this.put(KEY_SENDER, sender);}
    public ParseUser getSender(){return this.getParseUser(KEY_SENDER);}

    public void setMessage(String message){this.put(KEY_MESSAGE, message);}
    public String getMessage(){return this.getString(KEY_MESSAGE);}

    @Override
    public void viewObject(Context context)
    {
        BorrowMessageViewActivity.message = this; // TODO:encapsulate this
        Intent i = new Intent(context, BorrowMessageViewActivity.class);
        context.startActivity(i);
    }

    @Override
    public String toString()
    {
        String sender;

        try {
            sender = getSender().fetchIfNeeded().getString(BorrowObject.KEY_DISPLAY_NAME);
        } catch (ParseException pe) {
            sender = "null";
        }

        return ( "Subject:\t" + getSubject() + "\n"
                + "Sender:\t" + sender + "\n"
                + this.getCreatedAt() );
    }
}
