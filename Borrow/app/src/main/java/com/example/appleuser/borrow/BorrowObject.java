package com.example.appleuser.borrow;

import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by S on 5/16/2015.
 */
public class BorrowObject
{
    private String name;
    private String desc; // description
    private Double price;
    private Object pic; // picture : temporary object
    private ParseUser user;
    private ParseObject borrowObject;

    public BorrowObject(String name, String desc, Double price, Object pic, ParseUser user)
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

    public void setPic(Object pic){this.pic = pic;}
    public Object getPic(){return pic;}

    public void setUser(ParseUser user){this.user = user;}
    public ParseUser getUser(){return user;}

    @Override
    public String toString()
    {
        return ( "Name:\t" + name + "\n"
                + "Desc:\t" + desc + "\n"
                + "Price:\t" + price );
    }

    public void save()
    {
        if (borrowObject == null)
            toParseObject();

        borrowObject.saveInBackground();
    }

    public ParseObject toParseObject()
    {
        borrowObject = new ParseObject("BorrowObject");

        borrowObject.put("name", name);
        borrowObject.put("desc", desc);
        borrowObject.put("price", price);
        //borrowObject.put("pic", pic);
        //borrowObject.put("user", user);

        return borrowObject;
    }

    public void fromParseObject(ParseObject po)
    {
        name = po.getString("name");
        desc = po.getString("desc");
        price = po.getDouble("price");
        //get pic here
        //user = po.getParseUser("user");
    }
}
