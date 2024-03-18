package com.example.playlist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<ItemObject> listStorage;

    public CustomAdapter(Context context, List<ItemObject> customizedListView) {
        inflater = LayoutInflater.from(context);
        listStorage = customizedListView;
    }

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return listStorage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView songTitle;
        TextView songYear;
        TextView songArtist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list, parent, false);

            holder = new ViewHolder();
            holder.songTitle = convertView.findViewById(R.id.textView);
            holder.songYear = convertView.findViewById(R.id.textView2);
            holder.songArtist = convertView.findViewById(R.id.textView3);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemObject currentItem = listStorage.get(position);

        holder.songTitle.setText("Song Title: " + currentItem.getTitle());
        holder.songYear.setText("Song Year: " + currentItem.getYear());
        holder.songArtist.setText("Song Artist: " + currentItem.getArtist());

        // Alternate row background color
        if (position % 2 == 1) {
            convertView.setBackgroundColor(Color.GRAY);
        } else {
            convertView.setBackgroundColor(Color.LTGRAY);
        }

        return convertView;
    }
}