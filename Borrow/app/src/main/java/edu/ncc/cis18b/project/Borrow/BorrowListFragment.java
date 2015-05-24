package edu.ncc.cis18b.project.Borrow;

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

public class BorrowListFragment extends ListFragment
{
    private BorrowArrayAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        Log.d("Sagev", "Fragment initialized");
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id)
    {
        BorrowObject bo = (BorrowObject)getListAdapter().getItem(position);
        toast("Placeholder:\t" + bo.getName());

        BorrowObjectViewActivity.bo = bo;

        Intent i = new Intent(getActivity(), BorrowObjectViewActivity.class);
        startActivity(i);
    }

    private void toast(String msg)
    {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    protected void loadList(ArrayList<BorrowObject> list)
    {
        adapter = new BorrowArrayAdapter(getActivity(), list);
        setListAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    // inner class
    private class BorrowArrayAdapter extends ArrayAdapter<BorrowObject>
    {
        private Context context;
        private ArrayList<BorrowObject> values;

        public BorrowArrayAdapter(Context context, ArrayList<BorrowObject> values)
        {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.values = values;
            Log.d("Sagev", "Adapter initialized");
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