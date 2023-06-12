package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.Player;
import com.gdx.objects.Projectile;

import java.awt.*;
import java.util.ArrayList;

import static com.gdx.game.GameMain.getAngleToMouse;

public class RangeWeaponAnimation implements WeaponAnimation,CreateProjectile{
    float frameToCreateProjectile = 0.416f;
    Sprite animation1, animation2, animation3;
    Sprite activeAnimation;
    boolean canCreateProjectile;

    @Override
    public void attack(Player player, ArrayList<TextureRegion>weapon, float frameTime, Batch batch, float sizeScaling) {
        if (animation1 == null || animation2 == null || animation3 == null){
            animation1 = new Sprite(weapon.get(0));
            animation1.setScale(sizeScaling);
            animation1.setOrigin(0,(animation1.getHeight()/2));
            animation2 = new Sprite(weapon.get(1));
            animation2.setScale(sizeScaling);
            animation2.setOrigin((animation2.getWidth()/2),(animation2.getHeight()/2));
            animation3 = new Sprite(weapon.get(2));
            animation3.setScale(sizeScaling);
            animation3.setOrigin((animation3.getWidth()/2),(animation3.getHeight()/2));
        }
        float angle = getAngleToMouse(Gdx.input.getX(), Gdx.input.getY(), (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), (float) (player.getHitBox().getY() + (player.getHitBox().getHeight() / 2.0f)));
        if (frameTime<0.166666666666){
				activeAnimation = animation1;
                if (!canCreateProjectile){
                    canCreateProjectile = true;
                }
			} else if (frameTime < 0.3333333333){
    			activeAnimation = animation2;
			} else {
				activeAnimation = animation3;
			}
        activeAnimation.setX((float) (player.getHitBox().getX() + (player.getHitBox().getWidth() /2.0f)));
        activeAnimation.setY((float) (player.getPosY() + (player.getHitBox().getHeight() /3.0f)));
        activeAnimation.setRotation(0-angle);
        activeAnimation.draw(batch);
    }

    @Override
    public float getMaxFrameTime() {
        return 0.5f;
    }

    @Override
    public Rectangle[] getHitboxes() {
        return new Rectangle[0];
    }

    @Override
    public Projectile createProjectile(Player player, Sprite projectile) {
        canCreateProjectile = false;
        float angleProjectile = getAngleToMouse(Gdx.input.getX(),Gdx.input.getY(), (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), (player.getPosY() + (player.getHitBox().height / 2.0f)));
        return new Projectile( (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), ((player.getPosY()) + (player.getHitBox().height / 2.0f)), 0-angleProjectile,15,projectile,true);
    }

    @Override
    public float getframeToCreateProjectile() {
        return frameToCreateProjectile;
    }

    @Override
    public boolean canCreateProjectile() {
        return canCreateProjectile;
    }
}