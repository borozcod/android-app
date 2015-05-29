package edu.ncc.cis18b.project.Borrow;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;

import java.util.ArrayList;

public class BorrowListFragment<T extends BorrowObject> extends ListFragment //TODO: template ListFragment?
{
    private BorrowArrayAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) //TODO: change to onCreate
    {
        super.onActivityCreated(savedInstanceState);

        Log.d("Sagev", "Fragment initialized");
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id)
    {
        T t = (T)getListAdapter().getItem(position);

        t.viewObject(getActivity());
    }

    private void toast(String msg)
    {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    protected void loadList(ArrayList<T> list)
    {
        if (adapter == null) {
            adapter = new BorrowArrayAdapter(getActivity(), list);
            setListAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            adapter.updateList(list);
        }
    }

    // inner class
    private class BorrowArrayAdapter extends ArrayAdapter<T>
    {
        private Context context;
        private ArrayList<T> values;

        public BorrowArrayAdapter(Context context, ArrayList<T> values)
        {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.values = values;
            Log.d("Sagev", "Adapter initialized");
        }

        public void updateList(ArrayList<T> values)
        {
            this.values = values;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            textView.setText(values.get(position).toString());
            String s = values.get(position).toString();

            imageView.setImageBitmap(values.get(position).getPic());

            return rowView;
        }
    }
}