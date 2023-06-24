package com.gdx.objects.Bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.Animator;
import com.gdx.game.GameMain;

import java.awt.*;

public class StoneGolem extends Boss{
    private float spawnTimer;
    private boolean spawning;
    private Animation<TextureRegion> spawnAnimation;
    private Animation<TextureRegion> idleAnimationRight;
    private Animation<TextureRegion> idleAnimationLeft;
    private TextureRegion currentFrame;
    private final int WIDTH;
    private final int HEIGHT;

    public StoneGolem(double health, double attack, double defense, int level, double hpMultiplier, double damageMultiplier, double defenceMultiplier) {
        super(health, attack, defense, level, hpMultiplier, damageMultiplier, defenceMultiplier);
        spawnTimer = 0f;
        spawning = true;
        posX = 300;
        posY = 350;
        WIDTH = 192;
        HEIGHT = 200;
        hitBox = new Rectangle(posX, posY, WIDTH, HEIGHT);
        initializeIdle();
        initializeSpawnAnimation();

    }


    @Override
    public void die(SpriteBatch batch, float stateTime) {

    }

    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        //SPAWNING
        if (spawnTimer < 2f) {
            currentFrame = spawnAnimation.getKeyFrame(spawnTimer, false);
            batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
            spawnTimer += 1 * Gdx.graphics.getDeltaTime();
        } //BATTLE
        else {
            spawning = false;
            currentFrame = idleAnimationRight.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
        }
    }
    public void initializeIdle() {
        Texture idle = app.getManager().get("Bosses/Mecha-stone Golem 0.1/PNG sheet/idle.png");
        idleAnimationRight = Animator.animate(idle, 4, 1, false, false);
        idleAnimationLeft = Animator.animate(idle, 4, 1, true, false);
    }

    public void initializeSpawnAnimation() {
        //ini dipake reverse jadi spawn animation
        Texture invunerable = app.getManager().get("Bosses/Mecha-stone Golem 0.1/PNG sheet/invunerable.png");
        spawnAnimation = Animator.animate(invunerable, 8, 1, false, false, true);
    }

    public boolean isSpawning() {
        return spawning;
    }
}
