package com.marinamurashev.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.marinamurashev.simpletodo.models.Item;


public class AddItemActivity extends ActionBarActivity {

    private EditText etItemValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText) findViewById(R.id.etItemValue);
    }

    public void onEditItem(View view) {
        String item_name = etItemValue.getText().toString();

        if(item_name.length() > 0) {
            Item item = new Item();
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

}
