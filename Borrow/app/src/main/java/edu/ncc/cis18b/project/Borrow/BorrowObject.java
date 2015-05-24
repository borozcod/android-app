package edu.ncc.cis18b.project.Borrow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

/**
 * Created by S on 5/16/2015.
 */
public class BorrowObject //TODO: extend ParseObject
{
    private String name;
    private String desc; // description
    private Double price;
    private ParseFile pic;
    private ParseUser user;
    private ParseObject borrowObject; // TODO: replace this by extending ParseObject
    private final String KEY_NAME = "name";
    private final String KEY_DESC = "desc";
    private final String KEY_PRICE = "price";
    private final String KEY_PIC = "pic";
    private final String KEY_USER = "user";

    public BorrowObject(String name, String desc, Double price, ParseFile pic, ParseUser user)
    {
        this.name = name;
        this.desc = desc;
        this.price = price;
        this.pic = pic;
        this.user = user;
    }

    public BorrowObject(){}

    public void setName(String name){this.name = name;}
    public String getName(){return name;}

    public void setDesc(String desc){this.desc = desc;}
    public String getDesc(){return desc;}

    public void setPrice(Double price){this.price = price;}
    public Double getPrice(){return price;}

    public void setUser(ParseUser user){this.user = user;}
    public ParseUser getUser(){return user;}

    // picture getter/setters
    public void setPic(Bitmap pic, String name)
    {
        byte[] data;
        ByteArrayOutputStream stream;

        stream = new ByteArrayOutputStream();
        pic.compress(Bitmap.CompressFormat.PNG, 100, stream);
        data = stream.toByteArray();

        this.pic = new ParseFile("pic", data);
    }

    public Bitmap getPic() throws ParseException
    {
        byte[] data = pic.getData();

        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    @Override
    public String toString()
    {
        String tUser;

        try {
            tUser = user.fetchIfNeeded().getUsername();
        } catch (ParseException pe) {
            Log.e("Sagev", pe.getMessage());
            tUser = "NULL";
        }

        return ( "Name:\t" + name + "\n"
                + "Desc:\t" + desc + "\n"
                + "Price:\t" + price + "\n"
                + "Owner:\t" + tUser);
    }

    //TODO: cleanup these methods
    public void save()
    {
        if (borrowObject == null)
            toParseObject();

        borrowObject.saveInBackground();
    }

    public void toParseObject()
    {
        borrowObject = new ParseObject("BorrowObject");

        borrowObject.put(KEY_NAME, name);
        borrowObject.put(KEY_DESC, desc);
        borrowObject.put(KEY_PRICE, price);
        borrowObject.put(KEY_USER, user);

        if (pic != null)
            borrowObject.put(KEY_PIC, pic);
    }

    public void fromParseObject(ParseObject po)
    {
        name = po.getString(KEY_NAME);
        desc = po.getString(KEY_DESC);
        price = po.getDouble(KEY_PRICE);
        user = po.getParseUser(KEY_USER);
        pic = po.getParseFile(KEY_PIC);
    }
}