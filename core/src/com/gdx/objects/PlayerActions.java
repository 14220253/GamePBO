package com.gdx.objects;

public interface PlayerActions {
    /**
     * supaya setiap class player (Archer, Warrior, Mage) bisa diberikan attack yang berbeda
     */
    void moveUp();
    void moveDown();
    void moveRight();
    void moveLeft();
}
