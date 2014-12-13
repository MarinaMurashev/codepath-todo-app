package com.marinamurashev.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.marinamurashev.simpletodo.models.Item;


public class EditItemActivity extends ActionBarActivity {
    private EditText etItemValue;
    private int itemPosition;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText) findViewById(R.id.etItemValue);

        item = (Item) getIntent().getSerializableExtra(MainActivity.ITEM_EXTRA);
        itemPosition = getIntent().getIntExtra(MainActivity.ITEM_POSITION_EXTRA, 0);

        setItemEditFieldText();
    }

    public void onEditItem(View view) {
        String newItemText = etItemValue.getText().toString();
        if(newItemText.length() > 0) {
            Intent i = new Intent(this, EditItemActivity.class);
            item.setName(newItemText);
            i.putExtra(MainActivity.ITEM_EXTRA, item);
            i.putExtra(MainActivity.ITEM_POSITION_EXTRA, itemPosition);
            setResult(RESULT_OK, i);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.blank_item_error), Toast.LENGTH_SHORT).show();
            setItemEditFieldText();
        }
    }

    private void setItemEditFieldText(){
        etItemValue.setText(item.getName());
        etItemValue.setSelection(item.getName().length());
    }
}
