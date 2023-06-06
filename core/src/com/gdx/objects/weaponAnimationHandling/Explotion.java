package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Explotion {
    int frame = 0;
    Texture texture = new Texture("Vortex/Effect_TheVortex_1_429x429.png");
    ArrayList<TextureRegion> frames = new ArrayList<>();

    public Explotion() {
        int X = 0;
        int Y = 0;
        for (int i = 0; i < 60; i++) {
            frames.add(new TextureRegion(texture,X,Y,429,429));
            X+=429;
            if (X > 3861){
                X = 0;
                Y += 429;
            }
        }
    }

    public void draw(int X, int Y, Batch batch){
        float centerX = X - (429 * 0.2f) / 2;
        float centerY = Gdx.graphics.getHeight() - Y - (429 * 0.2f) / 2;
        batch.draw(frames.get(frame), centerX,centerY,0,0,429,429,0.2f,0.2f,0);
        frame++;
        if (frame == 60){
            frame = 0;
        }
    }
}
