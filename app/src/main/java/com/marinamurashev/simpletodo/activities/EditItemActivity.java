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
import android.widget.Toast;

import com.marinamurashev.simpletodo.R;
import com.marinamurashev.simpletodo.models.Item;

import java.util.Calendar;


public class EditItemActivity extends ActionBarActivity {
    private EditText etItemValue;
    private int itemPosition;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText) findViewById(R.id.etItemValue);

        long item_id = getIntent().getLongExtra(MainActivity.ITEM_ID_EXTRA, 0);
        item = Item.getItemWithId(item_id);
        itemPosition = getIntent().getIntExtra(MainActivity.ITEM_POSITION_EXTRA, 0);

        setItemEditFieldText();
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Toast.makeText(getActivity(), "date is set", Toast.LENGTH_SHORT).show();
        }
    }

    public void onEditItem(View view) {
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
}
