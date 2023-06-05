package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.Player;

import java.util.ArrayList;

public interface WeaponAnimation {

    void attack(Player player, ArrayList<TextureRegion>weapon, int frame, Batch batch, float sizeScaling);

    int getMaxFrame();
}
