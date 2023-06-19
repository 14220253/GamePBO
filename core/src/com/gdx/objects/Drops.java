package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;
import com.gdx.game.Static;

import java.awt.*;
import java.util.Random;

public class Drops {
    //koin, health, mana di lantai yang didrop musuh
    private TextureRegion currentFrame;
    private Animation<TextureRegion> animation;
    private final Animation<TextureRegion> collectAnimation;
    private State state;
    private final int posX;
    private final int posY;
    private final Rectangle hitbox;
    private int amount;
    private Texture texture;
    private Type type;
    enum Type {
        COIN,
        HEALTH,
        MANA
    }
    enum State{
        AVALABLE,
        COLLECTED,
        GONE
    }

    public Type getType() {return type;}

    public Drops(int posX, int posY, int floor, Type type) {
        GameMain app = (GameMain) Gdx.app.getApplicationListener();
        currentFrame = new TextureRegion();
        switch (type) {
            case COIN:
                texture = app.getManager().get("coins/MonedaD.png");
                animation = Static.animate(texture, 5, 1, false, false);
                amount = Math.round(new Random().nextFloat(0.5f, 5.49f)) * floor;
                break;
            case HEALTH:
                texture = app.getManager().get("heart.png");
                animation = Static.animate(texture, 3, 3, false, false, 2);
                amount = 10;
                break;
            case MANA:
                texture = app.getManager().get("star.png");
                animation = Static.animate(texture, 4, 1, false, false);
                amount = 10;
                break;
        }

        Texture collected = app.getManager().get("coins/Collected.png");
        collectAnimation = Static.animate(collected, 6, 1, false, false, 0.5f);
        state = State.AVALABLE;
        this.posX = posX;
        this.posY = posY;
        hitbox = new Rectangle(posX, posY, 32, 32);
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void draw(SpriteBatch batch, float stateTime) {
        if (state == State.AVALABLE) {
            currentFrame = animation.getKeyFrame(stateTime, true);
            batch.draw(currentFrame, posX, posY, 32, 32);
        }
        else if (state == State.COLLECTED) {
            hitbox.setSize(0, 0);
            collect(batch, stateTime);
        }
    }

    public void collect(SpriteBatch batch, float stateTime) {
        currentFrame = collectAnimation.getKeyFrame(stateTime, false);
        batch.draw(currentFrame, posX, posY);
        if (collectAnimation.isAnimationFinished(stateTime)) {
            state = State.GONE;
        }
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }
}
