package com.gdx.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Boss extends Karakter implements Skill, Attackable{
    //musuh boss

    //stats

    //hp
    //atk
    //def
    //lvl
    @Override
    public void takeDamage(double dmg) {
        health -= checkNegativeDmg(dmg-defense);
        checkHealth();
    }

    @Override
    public void die(SpriteBatch batch, Animation<TextureRegion> animation, float stateTime) {

    }

    public void checkHealth() {
        health = Math.max(health, 0);
    }
    public double checkNegativeDmg(double dmg){ //Dmg tidak boleh negative (nanti jadi heal) (dan minimal 1 untuk monster)
        if (dmg < 1){
            return 1;
        }
        else return dmg;
    }
}
