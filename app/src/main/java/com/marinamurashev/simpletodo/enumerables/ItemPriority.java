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

    public static ItemPriority fromCode(int levelCode){
        switch (levelCode){
            case 5: return ItemPriority.LOW;
            case 10: return ItemPriority.MEDIUM;
            case 15: return ItemPriority.HIGH;
        }

        return null;
    }
}
