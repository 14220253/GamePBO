package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.Player;
import com.gdx.objects.Projectile;

import java.util.ArrayList;

import static com.gdx.game.GameMain.getAngleToMouse;

public class RangeWeaponAnimation implements WeaponAnimation,CreateProjectile{
    int frameToCreateProjectile = 27;
    Sprite animation1, animation2, animation3;
    Sprite activeAnimation;

    @Override
    public void attack(Player player, ArrayList<TextureRegion>weapon, int frame, Batch batch, float sizeScaling) {
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
        float angle = getAngleToMouse(Gdx.input.getX(), Gdx.input.getY(), player.getPosX() + (player.getSpriteWidth() / 2.0f), player.getPosY() + (player.getSpriteHeight() / 2.0f));
        if (frame<10){
				activeAnimation = animation1;
			} else if (frame < 20){
    			activeAnimation = animation2;
			} else {
				activeAnimation = animation3;
			}
        activeAnimation.setX(player.getPosX() + (player.getSpriteWidth() /2.0f));
        activeAnimation.setY(player.getPosY() + (player.getSpriteHeight() /3.0f));
        activeAnimation.setRotation(0-angle);
        activeAnimation.draw(batch);
    }

    @Override
    public int getMaxFrame() {
        return 30;
    }

    @Override
    public Projectile createProjectile(Player player, Sprite arrow) {
        float angleProjectile = getAngleToMouse(Gdx.input.getX(),Gdx.input.getY(), player.getPosX() + (player.getSpriteWidth() / 2.0f), player.getPosY() + (player.getSpriteHeight() / 2.0f));
        return new Projectile(player.getPosX()+player.getSpriteWidth()/2.0f,player.getPosY()+player.getSpriteHeight()/3.0f, 0 -angleProjectile,20,arrow);
    }

    @Override
    public int getframeToCreateProjectile() {
        return frameToCreateProjectile;
    }
}