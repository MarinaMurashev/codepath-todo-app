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

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    private ArrayList<Item> items;
    private ItemsAdapter itemsAdapter;
    private ListView lvItems;

    public static final String ITEM_POSITION_EXTRA = "item position";
    public static final String ITEM_ID_EXTRA = "item id";

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);

        items = (ArrayList) Item.getAll();

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
                    Item item_to_delete = items.get(position);
                    items.remove(position);
                    item_to_delete.delete();
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
                    intent.putExtra(ITEM_ID_EXTRA, items.get(position).getId());
                    intent.putExtra(ITEM_POSITION_EXTRA, position);

                    startActivityForResult(intent, REQUEST_CODE);
                }
            }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            long item_id = i.getExtras().getLong(ITEM_ID_EXTRA);
            Item item = Item.getItemWithId(item_id);
            int item_position = i.getExtras().getInt(ITEM_POSITION_EXTRA);
            items.set(item_position, item);
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
