package com.gdx.objects;

import com.gdx.Exceptions.InventoryFullException;
import com.gdx.Exceptions.NotEnoughCoinsException;

import java.util.ArrayList;

public class Inventory {
    //backpack
    private final int CAPACITY = 6;
    private int coins = 0;
    private final ArrayList<Item> items = new ArrayList<>();

    public int getCoins() {
        return coins;
    }

    public void addItem(Item item) throws InventoryFullException {
        if (items.size() == CAPACITY) {
            throw new InventoryFullException();
        } else
            items.add(item);
    }

    public void deleteItem(int indexItem) {
        items.remove(indexItem);
    }
    public void gainCoin(int coin){
        coins += coin;
    }
    public void spendCoin(int coin) throws NotEnoughCoinsException {
        if ((coins -= coin) < 0){
            throw new NotEnoughCoinsException();
        } else {
            coins -= coin;
        }
    }
}
