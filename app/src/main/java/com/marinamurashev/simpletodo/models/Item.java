package com.marinamurashev.simpletodo.models;

import java.io.Serializable;

public class Item implements Serializable {
    private static final long serialVersionUID = 5177222050535318633L;
    private String name;

    public Item(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
