package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Projectile {
    private float velocityX;
    private float velocityY;
    private float positionX;
    private float positionY;
    private Sprite sprite;
    private float angle;
    private boolean isplayerProjectile;

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
