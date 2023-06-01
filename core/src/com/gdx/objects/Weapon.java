package com.gdx.objects;

public class Weapon extends Item{
    //weapon yang diequip player

    //stats
    private int atk;
    private double attackSpeed; //kalo perlu
    private int level;

    //methods
    public Weapon(String itemName, String itemDescription, int atk, double attackSpeed, int level) {
        super(itemName, itemDescription);
        this.atk = atk;
        this.attackSpeed = attackSpeed;
        this.level = level;
    }
}
