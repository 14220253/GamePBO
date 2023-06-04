package com.gdx.objects;

import com.gdx.Exceptions.InventoryFullException;
import com.gdx.Exceptions.NotEnoughCoinsException;

public class Player extends Karakter implements PlayerActions, Attackable, Skill { //interface Skill belum tau
    //karakter yang dikendalikan

    //stats

    //hp
    //atk
    //def
    //lvl
    private int currentMana;
    private int maxMana; //default
    private int maxHealth; //default max hp 100, nanti bisa ditambah
    private int evasion = 0;
    private double healMultiplier; //multiplier untuk healing mungkin bisa digunakan untuk buff/debuff
    private double exp;
    private Inventory inventory;
    private final int maxEvasion = 60;
    private final double baseExpNeededToLevelUp = 100;
    private final double expNeededMultiplier = 0.2;//percentage untuk kenaikan exp yang dibutuhkan tiap lvl up
    private double currentExpNeededToLevelUp;
    // example: lvl 1 ~> 100
    // lvl 2 ~> 120  (dapat dari 100 + 100*0.2 dimana 0.2 adalah expNeededMultiplier)
    // lvl 3 ~> 144  (dapat dari 120 + 120*0.2 dimana 0.2 adalah expNeededMultiplier)
    // lvl 4 dst...


    public Player(double health, int attack, int defense, int maxMana, int maxHealth, int evasion) {
        super(health, attack, defense, 1);
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.maxHealth = maxHealth;
        this.evasion = evasion;
        this.exp = 0;
        this.healMultiplier = 1; //default 1 dulu
        this.inventory = new Inventory();
        this.currentExpNeededToLevelUp = baseExpNeededToLevelUp;
    }

    @Override
    public void attack() {
        //TODO
    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void moveLeft() {

    }
    @Override
    public void takeDamage(double dmg) {
        int roll = (int)(Math.random()*101); // random 0-100
        if (roll > getEvasion()){ // kena dmg jika random roll melebihi evasion
            this.health -= checkNegativeDmg((dmg-defense));
            checkHealth();
        }
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
        while (canLevelUp()){
            levelUp();
        }
    }
    public void gainCoin(int coin){
        inventory.gainCoin(coin);
    }
    public void spendCoin(int coin) throws NotEnoughCoinsException {
        inventory.spendCoin(coin);
    }
    public void takeItem(Item item) throws InventoryFullException {
        inventory.addItem(item);
    }
    public void dropItem(int indexItem){
        inventory.deleteItem(indexItem);
    }
    public void levelUp(){
        exp-= currentExpNeededToLevelUp;
        level++;
    }
    public boolean canLevelUp(){
        currentExpNeededToLevelUp += currentExpNeededToLevelUp*expNeededMultiplier;
        return this.exp >= currentExpNeededToLevelUp;
    }
}
