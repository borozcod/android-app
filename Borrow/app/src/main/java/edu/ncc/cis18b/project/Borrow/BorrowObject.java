package edu.ncc.cis18b.project.Borrow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
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
    private byte[] pic;
    private String user;
    private ParseObject borrowObject; // TODO: replace this by extending ParseObject
    private final String KEY_NAME = "name";
    private final String KEY_DESC = "desc";
    private final String KEY_PRICE = "price";
    private final String KEY_PIC = "pic";
    private final String KEY_USER = "user";

    public BorrowObject(){}

    public void setName(String name){this.name = name;}
    public String getName(){return name;}

    public void setDesc(String desc){this.desc = desc;}
    public String getDesc(){return desc;}

    public void setPrice(Double price){this.price = price;}
    public Double getPrice(){return price;}

    public void setUser(String user){this.user = user;}
    public String getUser(){return user;}

    /**
     * Takes Bitmap and converts it to byte[]
     * Stores byte[] in this.pic
     * @param pic Bitmap to be converted to byte[]
     */
    public void setPic(Bitmap pic)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        pic.compress(Bitmap.CompressFormat.PNG, 100, stream);

        this.pic = stream.toByteArray();
    }

    /**
     * Decodes byte[] pic to Bitmap and returns it
     * @return returns Bitmap decoded from byte[] pic
     */
    public Bitmap getPic()
    {
        return BitmapFactory.decodeByteArray(pic, 0, pic.length);
    }

    /**
     * Decodes byte[] pic and resizes pic to user set scale or
     * returns largest possible scale if picture is too small
     * @param width desired width downscale
     * @param height desired height downscale
     * @return returns scaled Bitmap
     */
    public Bitmap getPic(int width, int height)
    {
        Bitmap b = getPic(); // decoded byte[]

        width = (width > b.getWidth()) ? width : b.getWidth();
        height = (height > b.getHeight()) ? height : b.getHeight();

        return ThumbnailUtils.extractThumbnail(b, width, height);
    }

    @Override
    public String toString()
    {
        return ( "Name:\t" + name + "\n"
                + "Desc:\t" + desc + "\n"
                + "Price:\t" + price + "\n"
                + "Owner:\t" + user);
    }

    //TODO: cleanup these methods
    public void save()
    {
        if (borrowObject == null)
            borrowObject = toParseObject();

        borrowObject.saveInBackground();
    }

    public ParseObject toParseObject()
    {
        if (borrowObject != null)
            return borrowObject;

        borrowObject = new ParseObject("BorrowObject");

        borrowObject.put(KEY_NAME, name);
        borrowObject.put(KEY_DESC, desc);
        borrowObject.put(KEY_PRICE, price);
        borrowObject.put(KEY_USER, user);
        borrowObject.put(KEY_PIC, pic);

        return borrowObject;
    }

    public ParseObject toParseObject(String objName)
    {
        ParseObject newObj = new ParseObject(objName);

        newObj.put(KEY_NAME, name);
        newObj.put(KEY_DESC, desc);
        newObj.put(KEY_PRICE, price);
        newObj.put(KEY_USER, user);

        if (pic != null)
            newObj.put(KEY_PIC, pic);

        return newObj;
    }

    public void fromParseObject(ParseObject po) // TODO: This should return a BorrowObject
    {
        borrowObject = po;

        name = po.getString(KEY_NAME);
        desc = po.getString(KEY_DESC);
        price = po.getDouble(KEY_PRICE);
        user = po.getString(KEY_USER);
        pic = po.getBytes(KEY_PIC);
    }
}