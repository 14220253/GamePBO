package com.gdx.objects;

import com.gdx.Exceptions.InventoryFullException;

import java.util.ArrayList;

public class Inventory {
    //backpack
    private final int CAPACITY = 6;
    private int money;
    private ArrayList<Item> items = new ArrayList<>();

    public void addItem(Item item) throws InventoryFullException {
        if (items.size() == CAPACITY) {
            throw new InventoryFullException();
        } else
            items.add(item);
    }

    public void deleteItem(int indexItem) {
        items.remove(indexItem);
    }
}
