package com.gdx.Exceptions;

public class InventoryFullException extends Exception{
    public InventoryFullException(){
        super("Inventory is Full, Can't pick up item!");
    }
}
