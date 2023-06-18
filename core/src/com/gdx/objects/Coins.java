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

public class Coins {
    //koin di lantai yang didrop musuh
    private TextureRegion currentFrame;
    private final Animation<TextureRegion> coinAnimation;
    private final Animation<TextureRegion> collectAnimation;
    private State state;
    private final int posX;
    private final int posY;
    private final Rectangle hitbox;
    private final int amount;
    public Coins(int posX, int posY, int floor) {
        GameMain app = (GameMain) Gdx.app.getApplicationListener();
        currentFrame = new TextureRegion();
        Texture coins = app.getManager().get("coins/MonedaD.png");
        coinAnimation = Static.animate(coins, 5, 1, false, false);
        Texture collected = app.getManager().get("coins/Collected.png");
        collectAnimation = Static.animate(collected, 6, 1, false, false, 0.5f);
        state = State.AVALABLE;
        this.posX = posX;
        this.posY = posY;
        hitbox = new Rectangle(posX, posY, 32, 32);
        amount = Math.round(new Random().nextFloat(0.5f, 5.49f)) * floor;
    }

    public int getAmount() {
        return amount;
    }

    public void draw(SpriteBatch batch, float stateTime) {
        if (state == State.AVALABLE) {
            currentFrame = coinAnimation.getKeyFrame(stateTime, true);
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

    enum State{
        AVALABLE,
        COLLECTED,
        GONE
    }
}
