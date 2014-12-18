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

import java.util.Calendar;
import java.util.Date;


public class AddItemActivity extends ActionBarActivity {

    private EditText etItemValue;
    private TextView tvDueDate;
    private Date itemDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText) findViewById(R.id.etItemValue);
        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            AddItemActivity addItemActivity = (AddItemActivity) getActivity();
            TextView tvDueDate = (TextView) addItemActivity.findViewById(R.id.tvDueDate);

            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);

            addItemActivity.itemDueDate = cal.getTime();

            String date_text = Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
            tvDueDate.setText(date_text);
        }
    }

    public void onEditItem(View view) {
        String item_name = etItemValue.getText().toString();

        if(item_name.length() > 0) {
            Item item = new Item();
            item.setName(etItemValue.getText().toString());
            item.setDueDate(itemDueDate);
            item.save();

            Intent i = new Intent(this, AddItemActivity.class);
            i.putExtra(MainActivity.ITEM_ID_EXTRA, item.getId());
            setResult(RESULT_OK, i);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.blank_item_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

}
