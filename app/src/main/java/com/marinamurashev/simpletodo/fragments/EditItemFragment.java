package com.marinamurashev.simpletodo.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.marinamurashev.simpletodo.R;
import com.marinamurashev.simpletodo.activities.EditItemActivity;
import com.marinamurashev.simpletodo.activities.MainActivity;
import com.marinamurashev.simpletodo.enumerables.ItemPriority;
import com.marinamurashev.simpletodo.models.Item;

import java.util.Calendar;

public class EditItemFragment extends DialogFragment {
    private Item item;
    private int item_position;

    private EditText etItemValue;
    private RadioGroup rgPriority;
    private TextView tvDueDate;
    private Button bDueDate;

    public static final String ITEM_POSITION_EXTRA = "item position";
    public static final String ITEM_ID_EXTRA = "item id";
    private static final String DIALOG_DATE = "dialog date";
    private static final int REQUEST_DATE = 0;

    public EditItemFragment(){

    }

    public static EditItemFragment newInstance(long itemId, int position){
        EditItemFragment fragment = new EditItemFragment();
        Bundle args = new Bundle();
        args.putInt(ITEM_POSITION_EXTRA, position);
        args.putLong(ITEM_ID_EXTRA, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_edit_item, container);
        etItemValue = (EditText) view.findViewById(R.id.etItemValue);
        rgPriority = (RadioGroup) view.findViewById(R.id.rgPriority);
        tvDueDate = (TextView) view.findViewById(R.id.tvDueDate);
        bDueDate = (Button) view.findViewById(R.id.bDueDate);

        item = Item.getItemWithId(getArguments().getLong(ITEM_ID_EXTRA));
        item_position = getArguments().getInt(ITEM_POSITION_EXTRA);

        bDueDate.setText(R.string.edit_due_date_button);
        setItemEditFieldText();
        setItemDueDateText();
        setupPriorityRadioButtons(view);
        setupPriorityRadioGroupListener();
        setupDueDateDeleteListener();
        setupDueDateButtonListener();

        return view;
    }

    public Item getItem(){
        return item;
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
            formatted_text = DateFormat.getDateFormat(getActivity()).format(cal.getTime());
        }

        tvDueDate.setText(formatted_text);
    }

    private void setupPriorityRadioButtons(View view){
        RadioButton rbLowPriority = (RadioButton) view.findViewById(R.id.rbLowPriority);
        RadioButton rbMediumPriority = (RadioButton) view.findViewById(R.id.rbMediumPriority);
        RadioButton rbHighPriority = (RadioButton) view.findViewById(R.id.rbHighPriority);

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

    private void setupPriorityRadioGroupListener() {
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

    private void setupDueDateDeleteListener(){
        tvDueDate.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                item.removeDueDate();
                setItemDueDateText();
                return false;
            }
        });
    }

    public void setupDueDateButtonListener(){
        bDueDate.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(item.getDueDate());
                dialog.setTargetFragment(EditItemFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });
    }

    public void onSaveItem(View view) {
        //return to new activity
    }
}
