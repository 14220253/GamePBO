package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdx.game.GameMain;
import com.gdx.game.Static;

import java.awt.*;

public class Coins {
    //koin di lantai yang didrop musuh

    private Texture coins;
    private Texture collected;
    private TextureRegion currentFrame;
    private Animation<TextureRegion> coinAnimation;
    private Animation<TextureRegion> collectAnimation;
    private GameMain app;
    private State state;
    private int posX;
    private int posY;
    private Rectangle hitbox;
    public Coins(int posX, int posY) {
        app = (GameMain) Gdx.app.getApplicationListener();
        currentFrame = new TextureRegion();
        coins = app.getManager().get("coins/MonedaD.png");
        coinAnimation = Static.animate(coins, 5, 1, false, false);
        collected = app.getManager().get("coins/Collected.png");
        collectAnimation = Static.animate(collected, 6, 1, false, false, 0.5f);
        state = State.AVALABLE;
        this.posX = posX;
        this.posY = posY;
        hitbox = new Rectangle(posX, posY, 32, 32);
    }
    public void draw(SpriteBatch batch, float stateTime) {
        ShapeRenderer render = new ShapeRenderer();
//        render.begin(ShapeRenderer.ShapeType.Line);
//        render.rect(posX, posY, hitbox.width, hitbox.height);
//        render.end();
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
