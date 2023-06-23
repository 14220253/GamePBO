package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdx.game.GameMain;
import com.gdx.objects.Player;

import java.awt.*;
import java.util.ArrayList;

import static com.gdx.game.GameMain.getAngleToMouse;

public class MeleeWeaponAnimation implements WeaponAnimation{
    int fixX;
    int fixY;
    float angle = 0;
    Vector2 vector2;
    Vector2 vector3;
    Vector2 vector4;
    float angleTime;
    Rectangle[]hitboxes = new Rectangle[3];
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    Sound attackSound;
    GameMain app;

    public MeleeWeaponAnimation() {
        app = (GameMain) Gdx.app.getApplicationListener();
        attackSound = app.getManager().get("Sword.mp3");
    }
    public Sound getAttackSound() {
        return attackSound;
    }
    @Override
    public void attack(Player player, ArrayList<TextureRegion>weapon, float frameTime, Batch batch, float sizeScaling) {
        if (frameTime == 0f) {
            fixX = Gdx.input.getX();
            fixY = Gdx.input.getY();
            angle = getAngleToMouse(fixX, fixY, (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), (player.getPosY() + (player.getHitBox().height / 2.0f)));
        }
        angleTime = (angle+45f) + getMaxFrameTime() - (540 * frameTime);
//        System.out.println(angleTime);
        vector2 = getOffsetFromAngle(angleTime, sizeScaling,12f);
        vector3 = getOffsetFromAngle(angleTime, sizeScaling,24f);
        vector4 = getOffsetFromAngle(angleTime, sizeScaling,36f);
        batch.draw(weapon.get(0), (float) (player.getHitBox().getX() + (player.getHitBox().width / 2.0f)), (player.getPosY() + (player.getHitBox().height / 2.0f)), 8, 0, 16, 46, sizeScaling, sizeScaling, (210 - angle) + (frameTime * 60 * 11));
        hitboxes[0] = setHitBoxSwing(player, vector2.x, 0-vector2.y, 23 * sizeScaling,23 * sizeScaling,shapeRenderer);
        hitboxes[1] = setHitBoxSwing(player, vector3.x, 0-vector3.y, 23 * sizeScaling,23 * sizeScaling,shapeRenderer);
        hitboxes[2] = setHitBoxSwing(player, vector4.x, 0-vector4.y, 23 * sizeScaling,23 * sizeScaling,shapeRenderer);
    }
    public Rectangle setHitBoxSwing(Player player, float offsetX, float offsetY, float width, float height, ShapeRenderer shapeRenderer) {
        float x = (player.getPosX()+player.getWidth()/2) - (width/2) + 5 +  offsetX;
        float y = (player.getPosY()+player.getHeight()/2) - (height/2) + offsetY;

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line); // ini di comment kalau nggak mau liat hitbox
//        shapeRenderer.rect(x, y, width, height); // ini di comment kalau nggak mau liat hitbox
//        shapeRenderer.end(); // ini di comment kalau nggak mau liat hitbox

        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }
    public Vector2 getOffsetFromAngle(float angle, float sizeScaling, float length) {
        length *= sizeScaling;
        float radians = MathUtils.degreesToRadians * angle;
//        System.out.println(angle);
        float x = length * MathUtils.cos(radians);
        float y = length * MathUtils.sin(radians);
        return new Vector2(x, y);
    }
    public Rectangle[] getHitboxes(){
        return hitboxes;
    }

    @Override
    public float getMaxFrameTime() {
        return 0.16666666666666f;
    }
}
