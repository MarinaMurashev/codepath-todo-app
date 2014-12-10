package com.marinamurashev.simpletodo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EditItemActivity extends Activity {
    private EditText etItemValue;
    private int itemPosition;
    private String itemOriginalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        etItemValue = (EditText) findViewById(R.id.etItemValue);

        itemOriginalText = getIntent().getStringExtra(MainActivity.ITEM_TEXT_EXTRA);
        itemPosition = getIntent().getIntExtra(MainActivity.ITEM_POSITION_EXTRA, 0);

        etItemValue.setText(itemOriginalText);
        etItemValue.setSelection(itemOriginalText.length());
    }

    public void onEditItem(View view) {
        String newItemText = etItemValue.getText().toString();
        if(newItemText.length() > 0) {
            Intent i = new Intent(this, EditItemActivity.class);
            i.putExtra(MainActivity.ITEM_TEXT_EXTRA, newItemText);
            i.putExtra(MainActivity.ITEM_POSITION_EXTRA, itemPosition);
            setResult(RESULT_OK, i);
            finish();
        } else {
            Toast.makeText(this, getString(R.string.blank_item_error), Toast.LENGTH_SHORT).show();
            etItemValue.setText(itemOriginalText);
            etItemValue.setSelection(itemOriginalText.length());
        }
    }
}
