package com.example.appleuser.borrow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

/**
 * Created by S on 5/16/2015.
 */
public class BorrowObject
{
    private String name;
    private String desc; // description
    private Double price;
    private ParseFile pic; // picture : temporary object
    private ParseUser user;
    private ParseObject borrowObject; // TODO: replace this by extending ParseObject

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
        return ( "Name:\t" + name + "\n"
                + "Desc:\t" + desc + "\n"
                + "Price:\t" + price + "\n"
                + "Owner:\t" + user.getUsername());
    }

    public void save()
    {
        if (borrowObject == null)
            toParseObject();

        borrowObject.saveInBackground();
    }

    public void toParseObject()
    {
        borrowObject = new ParseObject("BorrowObject");

        borrowObject.put("name", name);
        borrowObject.put("desc", desc);
        borrowObject.put("price", price);
        borrowObject.put("user", user);

        if (pic != null)
            borrowObject.put("pic", pic);
    }

    public void fromParseObject(ParseObject po)
    {
        name = po.getString("name");
        desc = po.getString("desc");
        price = po.getDouble("price");
        user = po.getParseUser("user");
        pic = po.getParseFile("pic");
    }
}
