package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.awt.*;

public class Projectile {
    private final float velocityX;
    private final float velocityY;
    private float positionX;
    private float positionY;
    private final Sprite sprite;
    private final float angle;
    private boolean isplayerProjectile;
    private Rectangle hitBox;

    public Projectile(float positionX, float positionY, float angle, float speed, Sprite sprite, boolean isPlayerProjectile) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.angle = angle;
        float radians = (float) Math.toRadians(angle);
        velocityX = speed * (float) Math.cos(radians);
        velocityY = speed * (float) Math.sin(radians);
        this.sprite = sprite;
        sprite.setOriginCenter();
        sprite.setRotation(angle);
        hitBox = new Rectangle(5,5);
    }

    public void draw(Batch batch){
        sprite.draw(batch);
    }
    public void update(){
        sprite.setRotation(angle);
        positionX+=velocityX * Gdx.graphics.getDeltaTime()*60;
        positionY+=velocityY * Gdx.graphics.getDeltaTime()*60;
        sprite.setX(positionX);
        sprite.setY(positionY);
        hitBox.setLocation((int) positionX, (int) positionY);
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public boolean isIsplayerProjectile() {
        return isplayerProjectile;
    }
}
