package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.Player;
import java.util.ArrayList;

public class MagicWeaponAnimation implements WeaponAnimation{
    Explotion explotion = new Explotion();
    int fixX = -1;
    int fixY;

    public Explotion getExplotion() {
        return explotion;
    }

    @Override
    public void attack(Player player, ArrayList<TextureRegion> weapon, int frame, Batch batch, float sizeScaling) {
        if (fixX < 0){
            fixX = Gdx.input.getX();
            fixY = Gdx.input.getY();
        }
        if (!player.isLookingLeft()) {
            if (frame<60){
                explotion.draw(fixX,fixY,batch);
                batch.draw(weapon.get(0), (float) player.getHitBox().getCenterX(), (float) (player.getHitBox().getCenterY()), 0, weapon.get(0).getRegionHeight() / 2.0f, (float) weapon.get(0).getRegionWidth(),
                        (float) weapon.get(0).getRegionHeight(), 1.5f, 1.5f, 90.0f);
            }else {
                batch.draw(weapon.get(0), (float) player.getHitBox().getCenterX(), (float) (player.getHitBox().getCenterY() - (player.getHitBox().getHeight() / 2.0f)), 0, weapon.get(0).getRegionHeight() / 2.0f, (float) weapon.get(0).getRegionWidth(),
                        (float) weapon.get(0).getRegionHeight(), 1.5f, 1.5f, 90.0f);
            }
        } else {
            if (frame<60){
                explotion.draw(fixX,fixY,batch);
                batch.draw(weapon.get(0), (float) player.getHitBox().getCenterX()+7, (float) (player.getHitBox().getCenterY()), 0, weapon.get(0).getRegionHeight() / 2.0f, (float) weapon.get(0).getRegionWidth(),
                        (float) weapon.get(0).getRegionHeight(), 1.5f, 1.5f, 90.0f);
            } else {
                batch.draw(weapon.get(0), (float) player.getHitBox().getCenterX() + 7, (float) (player.getHitBox().getCenterY() - (player.getHitBox().getHeight() / 2.0f)), 0, weapon.get(0).getRegionHeight() / 2.0f, (float) weapon.get(0).getRegionWidth(),
                        (float) weapon.get(0).getRegionHeight(), 1.5f, 1.5f, 90.0f);
            }
        }
        if (fixX > 0 && frame > 60){
            fixX = -1;
        }
    }
    @Override
    public int getMaxFrame() {
        return 300;
    }
}
