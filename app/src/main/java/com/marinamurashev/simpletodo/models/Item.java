package com.marinamurashev.simpletodo.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

@Table(name = "Items")
public class Item extends Model implements Serializable {
    private static final long serialVersionUID = 5177222050535318633L;

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
}
