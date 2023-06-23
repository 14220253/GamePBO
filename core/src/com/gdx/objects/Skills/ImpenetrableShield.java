package com.gdx.objects.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.Player;

public class ImpenetrableShield implements Skill {
    int manaCost = 100;
    float skillDuration = 5f;
    float frameTime;
    //kurang field sprite yang transparan
    @Override
    public void intitialize(Player player) {
        if (manaCost <= player.getMana()) {
            player.setInvulnerable(true);
            player.setSkillActive(true);
            player.addMana(-100);
            frameTime = 0;
        }
    }

    @Override
    public void update(Player player) {
        if (player.isSkillActive()){
            if (frameTime >= skillDuration){
                player.setSkillActive(false);
                end(player);
            }else {
                frameTime += Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public void draw(Player player) {
        if (player.isSkillActive()){

        }
    }

    @Override
    public void end(Player player) {
        player.setInvulnerable(false);
        player.setSkillActive(false);
    }
}
