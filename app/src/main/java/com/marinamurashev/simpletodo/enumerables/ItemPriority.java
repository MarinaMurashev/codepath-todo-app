package com.marinamurashev.simpletodo.enumerables;

public enum ItemPriority {
    LOW(5, "Low"), MEDIUM(10, "Medium"), HIGH(15, "High");

    private int levelCode;
    private String levelText;
    private ItemPriority(int value, String levelText) {
        this.levelCode = value;
        this.levelText = levelText;
    }

    public int getLevelCode(){
        return this.levelCode;
    }

    public String getLevelText(){
        return this.levelText;
    }
}
