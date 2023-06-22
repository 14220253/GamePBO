package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.Player;

import java.awt.*;
import java.util.ArrayList;

public class MagicWeaponAnimation implements WeaponAnimation{
    Explotion explotion = new Explotion();
    int fixX = -1;
    int fixY;
    float frameTime = -1;

    public MagicWeaponAnimation() {
    }

    @Override
    public void attack(Player player, ArrayList<TextureRegion> weapon, float frameTime, Batch batch, float sizeScaling) {
        if (fixX < 0){
            fixX = Gdx.input.getX();
            fixY = Gdx.input.getY();
        }
        this.frameTime = frameTime;
        if (frameTime<1f){
            explotion.draw(fixX,fixY,batch);
            batch.draw(weapon.get(0), (float) player.getHitBox().getCenterX(), (float) (player.getPosY()+7), 0, weapon.get(0).getRegionHeight() / 2.0f, (float) weapon.get(0).getRegionWidth(),
                    (float) weapon.get(0).getRegionHeight(), 1.5f, 1.5f, 90.0f);
        }else {
            batch.draw(weapon.get(0), (float) player.getHitBox().getCenterX(), (float) (player.getPosY() ), 0, weapon.get(0).getRegionHeight() / 2.0f, (float) weapon.get(0).getRegionWidth(),
                    (float) weapon.get(0).getRegionHeight(), 1.5f, 1.5f, 90.0f);
            }
        if (fixX > 0 && frameTime > 1f){
            fixX = -1;
        }
    }
    @Override
    public float getMaxFrameTime() {
        return 5f;
    }

    @Override
    public Rectangle[] getHitboxes() {
        if (frameTime < 0 || frameTime>=1f){
            return new Rectangle[0];
        } else {
            return explotion.getHitBoxes();
        }
    }
}
