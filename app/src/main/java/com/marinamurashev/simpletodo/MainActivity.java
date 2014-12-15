package com.marinamurashev.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.util.SQLiteUtils;
import com.marinamurashev.simpletodo.adapters.ItemsAdapter;
import com.marinamurashev.simpletodo.models.Item;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ArrayList<Item> items = new ArrayList<Item>();
    private ItemsAdapter itemsAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    public static final String ITEM_EXTRA = "item";
    public static final String ITEM_POSITION_EXTRA = "item position";

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);

        items = (ArrayList) SQLiteUtils.rawQuery(Item.class, "SELECT * from items", null);

        itemsAdapter = new ItemsAdapter(this, items);
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View view) {
        hideSoftKeyboard();

        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if(itemText.length() > 0) {
            Item item = new Item(itemText);
            item.save();
            itemsAdapter.add(item);
            etNewItem.setText("");
        } else {
            Toast.makeText(this, getString(R.string.blank_item_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener(){
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter,
                                               View item, int position, long id){
                    items.remove(position);
                    itemsAdapter.notifyDataSetChanged();
                    return true;
                }
            }
        );

        lvItems.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapter,
                                        View item, int position, long id){
                    Intent intent = new Intent(MainActivity.this, EditItemActivity.class);

                    intent.putExtra(ITEM_EXTRA, items.get(position));
                    intent.putExtra(ITEM_POSITION_EXTRA, position);

                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Item new_item = (Item) i.getExtras().getSerializable(ITEM_EXTRA);
            int item_position = i.getExtras().getInt(ITEM_POSITION_EXTRA);
            items.set(item_position, new_item);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSoftKeyboard();
    }

    private void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(imm.isAcceptingText()) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
    }
}
