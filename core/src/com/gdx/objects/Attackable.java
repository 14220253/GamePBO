package com.gdx.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Attackable {
    void takeDamage(double dmg);
    void die(SpriteBatch batch, float stateTime);
}
