package com.gdx.objects;

public abstract class Item {
    //item yang disimpan di inventory dan diequip

    //item detail
    private final String itemName;
    private final String itemDescription;

    //methods

    public Item(String itemName, String itemDescription) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }
    abstract void itemDetails();
}
