package com.marinamurashev.simpletodo.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.marinamurashev.simpletodo.R;
import com.marinamurashev.simpletodo.models.Item;

import android.text.format.DateFormat;
import java.util.Date;
import java.util.Calendar;


public class EditItemActivity extends ActionBarActivity {
    private EditText etItemValue;
    private TextView tvDueDate;
    private TextView tvItemFormTitle;
    private int itemPosition;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText) findViewById(R.id.etItemValue);
        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
        tvItemFormTitle = (TextView) findViewById(R.id.tvItemFormTitle);

        tvItemFormTitle.setText(R.string.edit_form_title);

        long item_id = getIntent().getLongExtra(MainActivity.ITEM_ID_EXTRA, 0);
        item = Item.getItemWithId(item_id);
        itemPosition = getIntent().getIntExtra(MainActivity.ITEM_POSITION_EXTRA, 0);

        setItemEditFieldText();
        if(item.isDueDateSet())
            setItemDueDateText();
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            EditItemActivity editItemActivity = (EditItemActivity) getActivity();
            Item item = editItemActivity.item;

            final Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            if(item.isDueDateSet() ){
                cal.setTime(item.getDueDate());
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            }

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            EditItemActivity editItemActivity = (EditItemActivity) getActivity();

            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            Date item_date = cal.getTime();

            editItemActivity.item.setDueDate(item_date);

            TextView tvDueDate = (TextView) editItemActivity.findViewById(R.id.tvDueDate);
            tvDueDate.setText(DateFormat.getDateFormat(getActivity()).format(item_date));
        }
    }

    public void onSaveItem(View view) {
        String newItemText = etItemValue.getText().toString();

        if(newItemText.length() > 0) {
            Intent i = new Intent(this, EditItemActivity.class);
            item.setName(newItemText);
            item.save();

            i.putExtra(MainActivity.ITEM_ID_EXTRA, item.getId());
            i.putExtra(MainActivity.ITEM_POSITION_EXTRA, itemPosition);

            setResult(RESULT_OK, i);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.blank_item_error), Toast.LENGTH_SHORT).show();
            setItemEditFieldText();
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void setItemEditFieldText(){
        etItemValue.setText(item.getName());
        etItemValue.setSelection(item.getName().length());
    }

    private void setItemDueDateText(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(item.getDueDate());
        String formatted_date = DateFormat.getDateFormat(this).format(cal.getTime());
        tvDueDate.setText(formatted_date);
    }
}
