package com.example.appleuser.borrow;

/**
 * Created by appleuser on 5/16/15.
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseException;

import java.util.List;

public class MySimpleArrayAdapter extends ArrayAdapter<BorrowObject> //TODO: rename this
{
    private final Context context;
    private final List<BorrowObject> values;

    public MySimpleArrayAdapter(Context context, List<BorrowObject> values)
    {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        TextView textView = (TextView)rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView)rowView.findViewById(R.id.icon);

        textView.setText(values.get(position).toString());
        String s = values.get(position).toString();

        try {
            imageView.setImageBitmap(values.get(position).getPic());
        } catch (ParseException pe) {
            Log.e("Sagev", pe.getMessage());
        }

        return rowView;
    }
}