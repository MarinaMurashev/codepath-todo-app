package com.marinamurashev.simpletodo.models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.marinamurashev.simpletodo.enumerables.ItemPriority;

import java.util.Date;
import java.util.List;

@Table(name = "Items")
public class Item extends Model {

    @Column(name = "Name")
    private String name;

    @Column(name = "DueDate")
    private Date dueDate;

    @Column(name = "Priority")
    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Item(){
        super();
        this.priority = ItemPriority.LOW.getLevelCode();
    }

    public Item(String name, Date dueDate , int priority){
        super();
        this.name = name;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    public String getName(){
        return name;
    }

    public Date getDueDate(){
        return dueDate;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDueDate(Date dueDate){
        this.dueDate = dueDate;
    }

    public boolean isDueDateSet(){
        if(dueDate != null)
            return true;
        return false;
    }

    public void removeDueDate(){
        dueDate = null;
    }

    public static Item getItemWithId(long id) {
        return new Select().from(Item.class).where("id = ?", id).executeSingle();
    }

    public static List<Item> getAll(){
        return new Select().from(Item.class).execute();
    }
}
