package com.gdx.objects;

import com.gdx.Exceptions.InventoryFullException;
import com.gdx.Exceptions.NotEnoughCoinsException;

import java.util.ArrayList;

public class Inventory {
    //backpack
    private final int CAPACITY = 6;
    private int coins = 0;
    private final ArrayList<Item> ITEMS = new ArrayList<>();

    public int getCoins() {
        return coins;
    }
    public void addCoin(int amount) {
        coins += amount;
    }

    public void addItem(Item item) throws InventoryFullException {
        if (ITEMS.size() == CAPACITY) {
            throw new InventoryFullException();
        } else
            ITEMS.add(item);
    }

    public void deleteItem(int indexItem) {
        ITEMS.remove(indexItem);
    }
    public void gainCoin(int coin){
        coins += coin;
    }
    public void spendCoin(int coin) throws NotEnoughCoinsException {
        if ((coins - coin) < 0){
            System.out.println("KURANG KOIN");
            throw new NotEnoughCoinsException();
        } else {
            System.out.println("KOIN CUKUP");
            coins -= coin;
        }
    }
}
