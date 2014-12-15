package com.marinamurashev.simpletodo.models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Items")
public class Item extends Model {

    @Column(name = "Name")
    private String name;

    public Item(){
        super();
    }

    public Item(String name){
        super();
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public static Item getItemWithId(long id) {
        return new Select().from(Item.class).where("id = ?", id).executeSingle();
    }

    public static List<Item> getAll(){
        return new Select().from(Item.class).execute();
    }
}
