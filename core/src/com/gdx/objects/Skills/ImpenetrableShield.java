package com.gdx.objects.Skills;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;
import com.gdx.game.Static;
import com.gdx.objects.Player;

public class ImpenetrableShield implements Skill {
    int manaCost = 100;
    GameMain app;
    float skillDuration = 5f;
    float frameTime;
    Texture texture;
    TextureRegion textureRegion;
    Animation<TextureRegion> animation;
    final int WIDTH;
    final int HEIGHT;
    Color color;
    Color colorDefault;

    public ImpenetrableShield() {
        color = new Color(0.6f, 0.9f, 1f, 0.6f);
        colorDefault = new Color(1,1,1,1);
        app = (GameMain) Gdx.app.getApplicationListener();
        TextureRegion[] temp = new TextureRegion[90];
        int count = 0;
        texture = app.getManager().get("Free Pixel Effects Pack/17_felspell_spritesheet.png");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                temp[count] = new TextureRegion(texture,j*100,i*100,100,100);
                count++;
            }
        }
        WIDTH = 100;
        HEIGHT = 100;
        animation = new Animation<>(0.015f,temp);
    }

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
    public void draw(Player player, Batch batch) {
        if (player.isSkillActive()){
            batch.setColor(color);
            batch.draw(animation.getKeyFrame(frameTime,true),
                    player.getPosX()-(WIDTH >> 1)+(player.getWidth() >> 1),
                    player.getPosY()-(HEIGHT >> 1)+(player.getHeight() >> 1),
                    WIDTH >> 1,
                    HEIGHT >> 1,
                    WIDTH,
                    HEIGHT,
                    1.2f,
                    1.2f,
                    0f);
            batch.setColor(colorDefault);
        }
    }

    @Override
    public void end(Player player) {
        player.setInvulnerable(false);
        player.setSkillActive(false);
    }
}
