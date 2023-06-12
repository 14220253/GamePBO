package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.Player;

import java.util.ArrayList;

import static com.gdx.game.GameMain.getAngleToMouse;

public class MeleeWeaponAnimation implements WeaponAnimation{
    int fixX;
    int fixY;
    float angle = 0;

    @Override
    public void attack(Player player, ArrayList<TextureRegion>weapon, float frameTime, Batch batch, float sizeScaling) {
        if (frameTime == 0f) {
            fixX = Gdx.input.getX();
            fixY = Gdx.input.getY();
            angle = getAngleToMouse(fixX, fixY, (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), (player.getPosY() + (player.getHitBox().height / 2.0f)));
        }
        batch.draw(weapon.get(0), (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), (player.getPosY() + (player.getHitBox().height / 2.0f)), 8, 0, 16, 46, sizeScaling, sizeScaling, (175 - angle) + (frameTime * 60 * 19));
    }

    @Override
    public float getMaxFrameTime() {
        return 0.16666666666666f;
    }
}
