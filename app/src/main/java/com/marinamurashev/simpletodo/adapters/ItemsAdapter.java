package com.marinamurashev.simpletodo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.marinamurashev.simpletodo.R;
import com.marinamurashev.simpletodo.models.Item;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Item>{

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        tvName.setText(item.name);
        return convertView;
    }

}
