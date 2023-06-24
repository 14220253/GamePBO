package com.gdx.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public abstract class Karakter {
    //diturunkan ke player, musuh, boss
    //bisa bergerak, mati, menyerang, dll
    protected double health;
    protected double attack;
    protected double defense;
    protected int level;
    protected int posX;
    protected int posY;
    protected Rectangle hitBox;
    protected TextureRegion sprite;
    protected boolean lookingLeft;
    protected int width;
    protected int height;

    public Karakter() {
    }

    public Karakter(double health, double attack, double defense, int level) {
        this.health = health;
        this.attack = attack;
        this.defense = defense;
        this.level = level;
        hitBox = new Rectangle();
    }


    public double getHealth() {
        return health;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public void updateHitbox() {
        hitBox.setLocation(getPosX(), getPosY());
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }

    public boolean isLookingLeft() {
        return lookingLeft;
    }
    public void setLocation(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setLookingLeft(boolean lookingLeft) {
        this.lookingLeft = lookingLeft;
    }
    protected abstract void takeDamage(double dmg);

    public double getAttack() {return attack;}

    public double getDefense() {return defense;}

    public int getLevel() {return level;}
}
