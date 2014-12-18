package com.marinamurashev.simpletodo.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.marinamurashev.simpletodo.R;
import com.marinamurashev.simpletodo.models.Item;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Item>{

    private static class ViewHolder {
        TextView name;
        TextView dueDate;
        TextView dueDateLabel;
    }

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvName);
            viewHolder.dueDate = (TextView) convertView.findViewById(R.id.tvDueDate);
            viewHolder.dueDateLabel = (TextView) convertView.findViewById(R.id.tvDueDateLabel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(item.getName());
        if(item.isDueDateSet()) {
            viewHolder.dueDate.setText(DateFormat.getDateFormat(getContext()).format(item.getDueDate()));
            viewHolder.dueDateLabel.setText(getContext().getResources().getString(R.string.list_due_date_label));
        } else {
            viewHolder.dueDate.setText("");
            viewHolder.dueDateLabel.setText("");
        }

        return convertView;
    }

}
