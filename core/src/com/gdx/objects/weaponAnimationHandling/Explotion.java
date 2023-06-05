package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class Explotion {
    int frame = 0;
    Texture texture = new Texture("VFX/Effect_TheVortex/30fps/Frames/Effect_TheVortex_1_427x431.png");
    ArrayList<TextureRegion> frames = new ArrayList<>();

    public Explotion() {
        int X = 0;
        int Y = 0;
        for (int i = 0; i < 30; i++) {
            frames.add(new TextureRegion(texture,X,Y,427,431));
            X+=427;
            Y+=431;
            if (X > 2562){
                X = 0;
            }
            if (Y > 2155){
                Y = 0;
            }
        }
    }
    public void draw(int X, int Y, Batch batch){
        batch.draw(frames.get(frame),X,Y);
        frame++;
        if (frame == 30){
            frame = 0;
        }
    }
}
