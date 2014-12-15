package com.marinamurashev.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.marinamurashev.simpletodo.adapters.ItemsAdapter;
import com.marinamurashev.simpletodo.models.Item;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private ArrayList<Item> items = new ArrayList<Item>();
    private ItemsAdapter itemsAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    public static final String ITEM_EXTRA = "item";
    public static final String ITEM_POSITION_EXTRA = "item position";

    private final int REQUEST_CODE = 20;
    private final String todoListFilename = "todo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNewItem = (EditText) findViewById(R.id.etNewItem);
        lvItems = (ListView) findViewById(R.id.lvItems);

        readItems();

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
            itemsAdapter.add(new Item(itemText));
            etNewItem.setText("");
            writeItems();
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
                    writeItems();
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

    private void readItems(){
        Item item = new Item("test");
        item.save();

        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, todoListFilename);
        try {
            ArrayList<String> item_names = new ArrayList<String>(FileUtils.readLines(todoFile));
            for(String item_name : item_names){
                items.add(new Item(item_name));
            }
        } catch (IOException e) {
            return;
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, todoListFilename);
        try {
            ArrayList<String> item_strings = new ArrayList<String>();
            for(Item item : items){
                item_strings.add(item.getName());
            }
            FileUtils.writeLines(todoFile, item_strings);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Item new_item = (Item) i.getExtras().getSerializable(ITEM_EXTRA);
            int item_position = i.getExtras().getInt(ITEM_POSITION_EXTRA);
            items.set(item_position, new_item);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
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
