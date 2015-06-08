package edu.ncc.cis18b.project.Borrow;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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

    protected void loadList(ArrayList<T> list, Class<T> listParameterType)
    {
        try {
            if (adapter == null) {
                adapter = new BorrowArrayAdapter(getActivity(),
                        getNewInstanceOfType(listParameterType).getArrayAdapterStyle(), list);
                setListAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else {
                adapter.updateList(list);
            }
        } catch (IllegalAccessException iae) {
            Log.d("Sagev", iae.getMessage());
        } catch (java.lang.InstantiationException ie) {
            Log.d("Sagev", ie.getMessage());
        }
    }

    private T getNewInstanceOfType(Class<T> listParameterType) throws IllegalAccessException, java.lang.InstantiationException
    {
        return listParameterType.newInstance();
    }

    // inner class
    private class BorrowArrayAdapter extends ArrayAdapter<T>
    {
        private Context context;
        private ArrayList<T> objects;
        private int layoutResourceId;

        public BorrowArrayAdapter(Context context, int layoutResourceId, ArrayList<T> objects)
        {
            super(context, layoutResourceId, objects);
            this.context = context;
            this.objects = objects;
            this.layoutResourceId = layoutResourceId;
            Log.d("Sagev", "Adapter initialized");
        }

        public void updateList(ArrayList<T> objects)
        {
            this.objects = objects;
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(layoutResourceId, parent, false);

            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            textView.setText(objects.get(position).toString());
            String s = objects.get(position).toString();

            imageView.setImageBitmap(objects.get(position).getPic());

            Typeface face10=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Aventura-Bold.otf");
            textView.setTypeface(face10);



            return rowView;
        }
    }
}

/*
 //start performanceArrayAdapter HOW DO YOU IMPLEMENT THIS CODE/?????????///
            View rowView = convertView;
                if (rowView == null) {
                    rowView = inflater.inflate(R.layout.rowlayout, null);
                    ViewHolder viewHolder = new ViewHolder();
                    viewHolder.text = (TextView) rowView.findViewById(R.id.label);
                    viewHolder.image = (ImageView) rowView
                            .findViewById(R.id.icon);
                    rowView.setTag(viewHolder);
            }
            ViewHolder holder = (ViewHolder) rowView.getTag();
            String s = names[position];
            holder.text.setText(s);
            if (s.startsWith("Windows7") || s.startsWith("iPhone")
                    || s.startsWith("Solaris")) {
                holder.image.setImageResource(R.drawable.image1);
            } else {
                holder.image.setImageResource(R.drawable.image2);
            }
            //end performanceArrayAdapter

 */