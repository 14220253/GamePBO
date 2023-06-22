package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.awt.*;
import java.util.ArrayList;

public class Explotion {
    Texture texture = new Texture("Vortex/Effect_TheVortex_1_429x429.png");
    ArrayList<TextureRegion> frames = new ArrayList<>();
    Animation<TextureRegion>animation;
    Rectangle[]hitBoxes = new Rectangle[12];
    ShapeRenderer shapeRenderer;

    public Explotion() {
        shapeRenderer = new ShapeRenderer();
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
        TextureRegion[]tmp = frames.toArray(new TextureRegion[0]);
        animation = new Animation<>(0.01667f,tmp);
    }

    public void draw(int X, int Y, Batch batch, float frameTime){
        float centerX = X - (429 * 0.2f) / 2;
        float centerY = Gdx.graphics.getHeight() - Y - (429 * 0.2f) / 2;
        batch.draw(animation.getKeyFrame(frameTime), centerX,centerY,0,0,429,429,0.2f,0.2f,0); // ini di comment untuk lihat hitbox
        setHitBoxes(X,Gdx.graphics.getHeight() - Y, 429 * 0.2f / 4,30,30,12,shapeRenderer);
    }
    public void setHitBoxes(float cursorX, float cursorY, float radius, float rectangleWidth, float rectangleHeight, int numRectangles, ShapeRenderer shapeRenderer) {
        float angleIncrement = 360.0f / numRectangles;

        for (int i = 0; i < numRectangles; i++) {
            float angle = i * angleIncrement;
            float radians = MathUtils.degreesToRadians * angle;

            float rectX = cursorX + radius * MathUtils.cos(radians) - rectangleWidth / 2.0f;
            float rectY = cursorY + radius * MathUtils.sin(radians) - rectangleHeight / 2.0f;

            hitBoxes[i] = new Rectangle((int) rectangleWidth, (int) rectangleHeight);
            hitBoxes[i].setLocation((int) rectX, (int) rectY);

            //shapeRenderer.begin(ShapeRenderer.ShapeType.Line); //ini di comment untuk tidak lihat hitbox
            //shapeRenderer.rect(rectX, rectY, rectangleWidth, rectangleHeight); //ini di comment untuk tidak lihat hitbox
            //shapeRenderer.end(); //ini di comment untuk tidak lihat hitbox
        }
    }
    public Rectangle[] getHitBoxes(){
        return hitBoxes;
    }

}
