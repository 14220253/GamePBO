package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.Exceptions.InventoryFullException;
import com.gdx.objects.playerAnimationHandling.PlayerAnimation;

import java.awt.*;
import java.awt.geom.Arc2D;

public class Player extends Karakter implements Attackable { //interface Skill belum tau
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
    private PlayerAnimation playerAnimation;
    private TextureRegion currentFrame;
    private boolean isRunning;
    private boolean isLookingLeft;
    private boolean isAttacking;
    private boolean canMoveLeft;
    private boolean canMoveRight;
    private boolean canMoveUp;
    private boolean canMoveDown;
    private boolean isDying;
    private boolean isDead;
    private int moveUpKey;
    private int moveDownKey;
    private int moveRightKey;
    private int moveLeftKey;
    private float speed; //speed in units per frame
    private float deathStateTime;
    Weapon weapon;

    public Player(Weapon weapon, PlayerAnimation playerAnimation) {
        this.weapon = weapon;
        this.playerAnimation = playerAnimation;
        currentFrame = playerAnimation.getCurrentFrame(0,true,isAttacking,isLookingLeft,!isRunning);
        Rectangle rectangle = new Rectangle();
        setWidth((int) (currentFrame.getRegionWidth()*playerAnimation.getScalingX()) - 5);
        setHeight((int) (currentFrame.getRegionHeight()*playerAnimation.getScalingY()) - 5);
        rectangle.setSize(width, height);
        setHitBox(rectangle);
        moveUpKey = Input.Keys.W;
        moveDownKey = Input.Keys.S;
        moveRightKey = Input.Keys.D;
        moveLeftKey = Input.Keys.A;
        isRunning = false;
        isLookingLeft = false;
        isAttacking = false;
        isDead = false;
        isDying = false;
        health = 100;
        speed = 300f; // 300 pixels a second
    }


    public void drawAttack(float frameTime, Batch batch) {
        weapon.drawAttack(this,frameTime,batch);
    }

    @Override
    public void takeDamage(double dmg) {
        int roll = (int)(Math.random()*101); // random 0-100
        if (roll > getEvasion()){ // kena dmg jika random roll melebihi evasion
            this.health -= checkNegativeDmg((dmg-defense));
            checkHealth();
        }
    }

    @Override
    public void die(SpriteBatch batch, Animation<TextureRegion> animation, float stateTime) {
        //TODO rian what is this, i already make death animation in playerAnimationHandling that runs on player(update)
    }

    public void revive(int health){
        isDying = false;
        isDead = false;
        Rectangle rectangle = new Rectangle();
        rectangle.setSize((int) (currentFrame.getRegionWidth()*playerAnimation.getScalingX()) - 5, (int) (currentFrame.getRegionHeight()*playerAnimation.getScalingY()) - 5);
        setHitBox(rectangle);
        this.health = health;
    }
    public boolean isDying(){
        return isDying;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setMoveUpKey(int moveUpKey) {
        this.moveUpKey = moveUpKey;
    }

    public void setMoveDownKey(int moveDownKey) {
        this.moveDownKey = moveDownKey;
    }

    public void setMoveRightKey(int moveRightKey) {
        this.moveRightKey = moveRightKey;
    }

    public void setMoveLeftKey(int moveLeftKey) {
        this.moveLeftKey = moveLeftKey;
    }

    public PlayerAnimation getPlayerAnimation() {
        return playerAnimation;
    }

    public void setPlayerAnimation(PlayerAnimation playerAnimation) {
        this.playerAnimation = playerAnimation;
    }

    public void setCanMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    public void setCanMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public void setCanMoveDown(boolean canMoveDown) {
        this.canMoveDown = canMoveDown;
    }
    public void canMoveFree(){
        canMoveUp = true;
        canMoveDown = true;
        canMoveRight = true;
        canMoveLeft = true;
    }
    public void cannotMove(){
        canMoveUp = false;
        canMoveDown = false;
        canMoveRight = false;
        canMoveLeft = false;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
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
    public void draw(Batch batch){
        batch.draw(currentFrame,getPosX(),getPosY(),0,0,currentFrame.getRegionWidth(),currentFrame.getRegionHeight(),playerAnimation.getScalingX(),playerAnimation.getScalingY(),0);
    }
    public void update(float deltaTime, float stateTime){
        if (health <= 0 && !isDying){
            isDying = true;
            deathStateTime = 0;
        }
        if (!isDying) {
            isRunning = false;
            if (Gdx.input.isKeyPressed(moveUpKey) && canMoveUp) {
                posY += speed * deltaTime;
                isRunning = true;
            }
            if (Gdx.input.isKeyPressed(moveDownKey) && canMoveDown) {
                posY -= speed * deltaTime;
                isRunning = true;
            }
            if (Gdx.input.isKeyPressed(moveLeftKey) && canMoveLeft) {
                posX -= speed * deltaTime;
                isRunning = true;
                isLookingLeft = true;
            }
            if (Gdx.input.isKeyPressed(moveRightKey) && canMoveRight) {
                posX += speed * deltaTime;
                isRunning = true;
                isLookingLeft = false;
            }
            if (!Gdx.input.isKeyPressed(moveUpKey) &&
                    !Gdx.input.isKeyPressed(moveDownKey) &&
                    !Gdx.input.isKeyPressed(moveRightKey) &&
                    !Gdx.input.isKeyPressed(moveLeftKey)) {
                isRunning = false;
            }
            currentFrame = playerAnimation.getCurrentFrame(stateTime, true, isAttacking, isLookingLeft, !isRunning);
            updateHitbox();
        } else {
            deathStateTime+=deltaTime;
            if (deathStateTime <= playerAnimation.getMaxDyingStateTime()) {
                currentFrame = playerAnimation.getDyingFrame(deathStateTime, isLookingLeft);
            } else if (deathStateTime+2.5f > playerAnimation.getMaxDyingStateTime()) {
                isDead = true;
                hitBox = null;
            }
        }
    }
    enum state {
        IDLE,
        RUN
    }
    enum move {
        //w, s, a, d
    }
}
