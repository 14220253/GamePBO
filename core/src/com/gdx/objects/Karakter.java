package com.gdx.objects;

import java.awt.*;

public abstract class Karakter {
    //diturunkan ke player, musuh, boss
    //bisa bergerak, mati, menyerang, dll
    protected double health;
    protected int attack;
    protected int defense;
    protected int level;
    protected int posX;
    protected int posY;
    protected Rectangle hitBox;

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
}
