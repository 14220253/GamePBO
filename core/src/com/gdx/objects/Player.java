package com.gdx.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.Exceptions.InventoryFullException;

public class Player extends Karakter implements PlayerActions, Attackable { //interface Skill belum tau
    //karakter yang dikendalikan

    //stats

    //hp
    //atk
    //def
    //lvl
    private int mana;
    private int maxHealth = 100; //default max hp 100, nanti bisa ditambah
    private int evasion = 0;
    private double healMultiplier = 1; //multiplier untuk healing mungkin bisa digunakan untuk buff/debuff
    private double exp;
    private Inventory inventory;
    private final int maxEvasion = 60;
    private TextureRegion sprite;
    private boolean lookingLeft = false;
    Weapon weapon;

    public Player(Weapon weapon) {
        this.weapon = weapon;
    }


    public void attack(int frame, Batch batch) {

        weapon.attack(this,frame,batch);
    }

    @Override
    public void moveUp() {
        setPosY(getPosY() + 3);
    }

    @Override
    public void moveDown() {
        setPosY(getPosY() - 3);
    }

    @Override
    public void moveRight() {
        setPosX(getPosX() + 3);
    }

    @Override
    public void moveLeft() {
        setPosX(getPosX() - 3);
    }
    @Override
    public void takeDamage(double dmg) {
        int roll = (int)(Math.random()*101); // random 0-100
        if (roll > getEvasion()){ // kena dmg jika random roll melebihi evasion
            this.health -= checkNegativeDmg((dmg-defense));
            checkHealth();
        }
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void gainHealth(double healing) {
        this.health += healing*healMultiplier;
    }
    public int getEvasion(){
        return Math.min(evasion, maxEvasion); //evasion tidak boleh melebihi max evasion (perma miss OP)
    }
    public void checkHealth(){
        health = Math.max(health, 0); //Health tidak boleh dibawah 0
    }
    public double checkNegativeDmg(double dmg){ //Dmg tidak boleh negative (nanti jadi heal)
        if (dmg < 0){
            return 0;
        }
        else return dmg;
    }
    public void gainExp(double exp){
        this.exp += exp;
    }
    public void takeItem(Item item) throws InventoryFullException {
        inventory.addItem(item);
    }
    public void dropItem(int indexItem){
        inventory.deleteItem(indexItem);
    }
    public void levelUp(){
        // exp =- expNeeded
        // level++
    }
    public int getSpriteWidth(){
        return sprite.getRegionWidth();
    }
    public int getSpriteHeight(){
        return sprite.getRegionHeight();
    }
}
