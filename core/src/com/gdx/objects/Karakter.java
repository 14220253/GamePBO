package com.gdx.objects;

public abstract class Karakter {
    //diturunkan ke player, musuh, boss
    //bisa bergerak, mati, menyerang, dll
    protected double health;
    protected int attack = 0;
    protected int defense = 0;
    protected int level = 1;

    public Karakter() {
    }

    public Karakter(double health, int attack, int defense, int level) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
    }
}
