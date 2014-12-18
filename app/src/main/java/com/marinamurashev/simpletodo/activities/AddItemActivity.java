package com.marinamurashev.simpletodo.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
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
    private TextView tvItemFormTitle;
    private Button bDueDate;
    private Item item = new Item();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText) findViewById(R.id.etItemValue);
        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
        tvItemFormTitle = (TextView) findViewById(R.id.tvItemFormTitle);
        bDueDate = (Button) findViewById(R.id.bDueDate);

        tvItemFormTitle.setText(R.string.add_form_title);
        bDueDate.setText(R.string.add_due_date_button);

        setupDueDateListener();
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            AddItemActivity addItemActivity = (AddItemActivity) getActivity();
            TextView tvDueDate = (TextView) addItemActivity.findViewById(R.id.tvDueDate);

            Calendar cal = Calendar.getInstance();
            cal.set(year, month, day);
            Date item_date = cal.getTime();

            addItemActivity.item.setDueDate(item_date);

            tvDueDate.setText(DateFormat.getDateFormat(getActivity()).format(item_date));
        }
    }

    private void setupDueDateListener(){
        tvDueDate.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                item.removeDueDate();
                tvDueDate.setText("");
                return false;
            }
        });
    }


    public void onSaveItem(View view) {
        String item_name = etItemValue.getText().toString();

        if(item_name.length() > 0) {
            item.setName(etItemValue.getText().toString());
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
