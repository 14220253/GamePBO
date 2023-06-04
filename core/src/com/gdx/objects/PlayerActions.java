package com.gdx.objects;

public interface PlayerActions {
    /**
     * supaya setiap class player (Archer, Warrior, Mage) bisa diberikan attack yang berbeda
     */
    public abstract void attack();
    public abstract void moveUp();
    public abstract void moveDown();
    public abstract void moveRight();
    public abstract void moveLeft();
}
