package edu.ncc.cis18b.project.Borrow;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseUser;
import com.parse.ParseClassName;

import java.io.ByteArrayOutputStream;

/**
 * Created by S on 5/29/2015.
 */
@ParseClassName("BorrowItem")
public class BorrowItem extends BorrowObject
{
    public final static String KEY_NAME = "name";
    public final static String KEY_DESC = "desc";
    public final static String KEY_PRICE = "price";
    public final static String KEY_USER = "user";
    public final static String KEY_IS_LENT = "isLent";
    public final static String KEY_BORROWER = "borrower";

    // default constructor required for ParseClassName
    public BorrowItem(){}

    public void setName(String name){this.put(KEY_NAME, name);}
    public String getName(){return this.getString(KEY_NAME);}

    public void setDesc(String desc){this.put(KEY_DESC, desc);}
    public String getDesc(){return this.getString(KEY_DESC);}

    public void setPrice(Double price){this.put(KEY_PRICE, price);}
    public Double getPrice(){return this.getDouble(KEY_PRICE);}

    // set owner
    public void setUser(ParseUser user) {
        this.put(KEY_USER, user.getString(BorrowObject.KEY_DISPLAY_NAME));}
    public String getUser(){return this.getString(KEY_USER);}

    public void setIsLent(boolean isLent){this.put(KEY_IS_LENT, isLent);}
    public boolean getIsLent(){return this.getBoolean(KEY_IS_LENT);}

    public void setBorrower(ParseUser borrower) {
        this.put(KEY_BORROWER, borrower.getString(BorrowObject.KEY_DISPLAY_NAME));}
    public String getBorrower(){return this.getString(KEY_BORROWER);}

    @Override
    public int getArrayAdapterStyle()
    {
        return R.layout.rowlayout;
    }

    @Override
    public void viewObject(Context context)
    {
        BorrowObjectViewActivity.borrowItem = this;

        Intent i = new Intent(context, BorrowObjectViewActivity.class);
        context.startActivity(i);
    }

    @Override
    public String toString()
    {
        return ( "Name:\t" + getName() + "\n"
                + "Desc:\t" + getDesc() + "\n"
                + "Price:\t" + getPrice() + "\n"
                + "Owner:\t" + getUser() );
    }


}
