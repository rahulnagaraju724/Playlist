package com.example.playlist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter  {

    private LayoutInflater lInflater;
    private List<ItemObject> listStorage;

    public CustomAdapter(Context context,
                         List<ItemObject> customizedListView) {
        lInflater = (LayoutInflater)context.getSystemService
                (Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView songTitle = null;
        TextView songYear = null;
        TextView songArtist = null;

        if (convertView == null) {
            convertView = lInflater.inflate(R.layout.list, parent,
                    false);
        }
        songTitle = convertView.findViewById(R.id.textView);
        songYear = convertView.findViewById(R.id.textView2);
        songArtist = convertView.findViewById(R.id.textView3);
        songTitle.setText("Song Title: " +
                listStorage.get(position).getTitle());
        songYear.setText("Song Year: " +
                listStorage.get(position).getYear());
        songArtist.setText("Song Artist: " +
                listStorage.get(position).getArtist());

        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.GRAY);
        } else {
            convertView.setBackgroundColor(Color.LTGRAY);
        }


        return convertView;
    }


}
