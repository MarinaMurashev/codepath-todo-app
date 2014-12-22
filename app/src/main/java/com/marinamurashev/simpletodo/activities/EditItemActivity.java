package com.marinamurashev.simpletodo.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marinamurashev.simpletodo.R;
import com.marinamurashev.simpletodo.enumerables.ItemPriority;
import com.marinamurashev.simpletodo.models.Item;

import android.text.format.DateFormat;
import android.view.View.OnLongClickListener;
import java.util.Date;
import java.util.Calendar;


public class EditItemActivity extends ActionBarActivity {
    private EditText etItemValue;
    private TextView tvDueDate;
    private TextView tvItemFormTitle;
    private Button bDueDate;
    private int itemPosition;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText) findViewById(R.id.etItemValue);
        tvDueDate = (TextView) findViewById(R.id.tvDueDate);
        tvItemFormTitle = (TextView) findViewById(R.id.tvItemFormTitle);
        bDueDate = (Button) findViewById(R.id.bDueDate);

        tvItemFormTitle.setText(R.string.edit_form_title);
        bDueDate.setText(R.string.edit_due_date_button);

        long item_id = getIntent().getLongExtra(MainActivity.ITEM_ID_EXTRA, 0);
        item = Item.getItemWithId(item_id);
        itemPosition = getIntent().getIntExtra(MainActivity.ITEM_POSITION_EXTRA, 0);

        setItemEditFieldText();
        if(item.isDueDateSet())
            setItemDueDateText();

        setupDueDateListener();
        setupPriorityRadioButtons();
        setupPriorityRadioGroupListener();
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

    private void setupDueDateListener(){
        tvDueDate.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                item.removeDueDate();
                setItemDueDateText();
                return false;
            }
        });
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
        String formatted_text = "";

        if(item.isDueDateSet()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(item.getDueDate());
            formatted_text = DateFormat.getDateFormat(this).format(cal.getTime());
        }

        tvDueDate.setText(formatted_text);
    }

    private void setupPriorityRadioGroupListener() {

        RadioGroup rgPriority = (RadioGroup) findViewById(R.id.rgPriority);
        rgPriority.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbLowPriority:
                        item.setPriority(ItemPriority.LOW.getLevelCode());
                        break;
                    case R.id.rbMediumPriority:
                        item.setPriority(ItemPriority.MEDIUM.getLevelCode());
                        break;
                    case R.id.rbHighPriority:
                        item.setPriority(ItemPriority.HIGH.getLevelCode());
                        break;
                    default: break;
                }
            }
        });

    }

    private void setupPriorityRadioButtons(){
        RadioButton rbLowPriority = (RadioButton) findViewById(R.id.rbLowPriority);
        RadioButton rbMediumPriority = (RadioButton) findViewById(R.id.rbMediumPriority);
        RadioButton rbHighPriority = (RadioButton) findViewById(R.id.rbHighPriority);

        rbLowPriority.setText(ItemPriority.LOW.getLevelText());
        rbMediumPriority.setText(ItemPriority.MEDIUM.getLevelText());
        rbHighPriority.setText(ItemPriority.HIGH.getLevelText());

        ItemPriority itemPriority = ItemPriority.fromCode(item.getPriority());
        if(itemPriority.equals(ItemPriority.HIGH)){
            rbHighPriority.setChecked(true);
        } else if(itemPriority.equals(ItemPriority.MEDIUM)){
            rbMediumPriority.setChecked(true);
        } else{
            rbLowPriority.setChecked(true);
        }
    }
}
