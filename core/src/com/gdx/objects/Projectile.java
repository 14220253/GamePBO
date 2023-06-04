package com.gdx.objects;

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

    public Projectile(float positionX, float positionY, float angle, float speed, Sprite sprite) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.angle = angle;
        float radians = (float) Math.toRadians(angle);
        velocityX = speed * (float) Math.cos(radians);
        velocityY = speed * (float) Math.sin(radians);
        this.sprite = sprite;
        sprite.setOriginCenter();
    }

    public void draw(Batch batch){
        sprite.setRotation(angle);
        positionX+=velocityX;
        positionY+=velocityY;
        sprite.setX(positionX);
        sprite.setY(positionY);
        sprite.draw(batch);
    }
}
