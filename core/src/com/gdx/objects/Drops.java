package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.Animator;
import com.gdx.game.GameMain;

import java.awt.*;
import java.util.Random;

public class Drops {
    //koin, health, mana di lantai yang didrop musuh
    private TextureRegion currentFrame;
    private Animation<TextureRegion> animation;
    private Animation<TextureRegion> collectAnimation;
    private State state;
    private final int POSX;
    private final int POSY;
    private final Rectangle HITBOX;
    private int amount;
    private Texture texture;
    private Type type;
    private GameMain app;
    private float startCollect = 0f;
    private Sound collectSound;
    public enum Type {
        COIN,
        HEALTH,
        MANA
    }
    public enum State{
        AVALABLE,
        COLLECTED,
        GONE
    }

    public Type getType() {return type;}

    public Drops(int posX, int posY, int floor, Type type) {
        app = (GameMain) Gdx.app.getApplicationListener();
        currentFrame = new TextureRegion();
        switch (type) {
            case COIN:
                texture = app.getManager().get("coins/MonedaD.png");
                animation = Animator.animate(texture, 5, 1, false, false);
                amount = Math.round(new Random().nextFloat(0.5f, 5.49f)) * floor;
                collectSound = app.getManager().get("CoinCollect.mp3");
                break;
            case HEALTH:
                texture = app.getManager().get("heart.png");
                animation = Animator.animate(texture, 3, 3, false, false, 2);
                amount = 10;
                collectSound = app.getManager().get("HeartCollect.mp3");
                break;
            case MANA:
                texture = app.getManager().get("star.png");
                animation = Animator.animate(texture, 4, 1, false, false);
                amount = 10;
                collectSound = app.getManager().get("ManaCollect.mp3");
                break;
        }

        Texture collected = app.getManager().get("coins/Collected.png");
        collectAnimation = Animator.animate(collected, 6, 1, false, false, 0.05f);
        state = State.AVALABLE;
        this.POSX = posX;
        this.POSY = posY;
        HITBOX = new Rectangle(posX, posY, 32, 32);
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void draw(SpriteBatch batch, float stateTime) {
        if (state == State.AVALABLE) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, POSX, POSY, 32, 32);
        }
        else if (state == State.COLLECTED) {
            HITBOX.setSize(0, 0);
            collect(batch, startCollect);
            startCollect += 1 * Gdx.graphics.getDeltaTime();
        }
    }

    public void collect(SpriteBatch batch, float stateTime) {
        currentFrame = collectAnimation.getKeyFrame(stateTime, false);
        batch.draw(currentFrame, POSX, POSY);
        if (collectAnimation.isAnimationFinished(stateTime)) {
            state = State.GONE;
        }
    }

    public Rectangle getHitbox() {
        return HITBOX;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public Sound getCollectSound() {return collectSound;}
}
