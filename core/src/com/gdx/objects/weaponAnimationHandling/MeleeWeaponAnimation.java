package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdx.objects.Player;

import java.awt.*;
import java.util.ArrayList;

import static com.gdx.game.GameMain.getAngleToMouse;

public class MeleeWeaponAnimation implements WeaponAnimation{
    int fixX;
    int fixY;
    float angle = 0;
    Vector2 vector2;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    @Override
    public void attack(Player player, ArrayList<TextureRegion>weapon, float frameTime, Batch batch, float sizeScaling) {
        if (frameTime == 0f) {
            fixX = Gdx.input.getX();
            fixY = Gdx.input.getY();
            angle = getAngleToMouse(fixX, fixY, (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), (player.getPosY() + (player.getHitBox().height / 2.0f)));
            vector2 = getCoordinatesFromAngle(angle, sizeScaling);
        }
        batch.draw(weapon.get(0), (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), (player.getPosY() + (player.getHitBox().height / 2.0f)), 8, 0, 16, 46, sizeScaling, sizeScaling, (210 - angle) + (frameTime * 60 * 11));
        drawRectangleAtPlayer(player, vector2.x, 0-vector2.y, 43 * sizeScaling,43 * sizeScaling,shapeRenderer);
    }
    public void drawRectangleAtPlayer(Player player, float offsetX, float offsetY, float width, float height, ShapeRenderer shapeRenderer) {
        float x = (player.getPosX()+player.getWidth()/2) - (width/2) + 5 +  offsetX;
        float y = (player.getPosY()+player.getHeight()/2) - (height/2) + offsetY;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(x, y, width, height);
        shapeRenderer.end();
    }
    public Vector2 getCoordinatesFromAngle(float angle, float sizeScaling) {
        float length = 25.0f * sizeScaling;
        float radians = MathUtils.degreesToRadians * angle;
        float x = length * MathUtils.cos(radians);
        float y = length * MathUtils.sin(radians);
        return new Vector2(x, y);
    }

    @Override
    public float getMaxFrameTime() {
        return 0.16666666666666f;
    }
}
