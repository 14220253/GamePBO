package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;

import java.util.ArrayList;

public class Explotion {
    int frame = 0;
    Texture texture;
    GameMain app;
    ArrayList<TextureRegion> frames = new ArrayList<>();

    public Explotion() {
        app = (GameMain) Gdx.app.getApplicationListener();
        texture= app.getManager().get("Vortex/Effect_TheVortex_1_427x431.png");
        int X = 0;
        int Y = 0;
        for (int i = 0; i < 30; i++) {
            frames.add(new TextureRegion(texture,X,Y,427,431));
            X+=427;
            if (X > 2562){
                X = 0;
                Y += 427;
            }
            if (Y > 2155){
                Y = 0;
            }
        }
    }
    public void draw(int X, int Y, Batch batch){
        float centerX = X - (427 * 0.2f) / 2;
        float centerY = Gdx.graphics.getHeight() - Y - (431 * 0.2f) / 2;
        batch.draw(frames.get(frame), centerX,centerY,0,0,427,431,0.2f,0.2f,0);
        frame++;
        if (frame == 30){
            frame = 0;
        }
    }
}
