package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;

import java.awt.*;
import java.util.Random;

public class Breakable {
    //pot dll.
    private GameMain app;
    private Texture props;
    private TextureRegion halfBroken;
    private TextureRegion normal;
    private TextureRegion broken;
    private Rectangle hitbox;
    private int posX;
    private int posY;
    private State state;
    private Drops.Type type;
    private Sound breakSound;

    enum State {
        NORMAL,
        HALFBROKEN,
        BROKEN
    }

    public State getState() {return state;}

    public void setState(State state) {this.state = state;}

    public Drops.Type getType() {return type;}

    public Rectangle getHitbox() {return hitbox;}

    public int getPosX() {return posX;}

    public int getPosY() {return posY;}

    public Breakable(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        app = (GameMain) Gdx.app.getApplicationListener();
        props = app.getManager().get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Props.png");
        state = State.NORMAL;
        initialize();
    }
    public void draw(SpriteBatch batch) {
        if (state == State.NORMAL) {
            batch.draw(normal, posX, posY, 32, normal.getRegionHeight() == 16 ? 32 : 64);
        }
        else if (state == State.HALFBROKEN) {
            hitbox.setLocation(0, 0);
            hitbox.setSize(0, 0);
            batch.draw(halfBroken, posX, posY, 32, halfBroken.getRegionHeight() == 16 ? 32 : 64);
            state = State.BROKEN;
        }
        else if (state == State.BROKEN) {
            batch.draw(broken, posX, posY, 32, 32);
        }
    }
    private void initialize() {
        Random r = new Random();
        int randomizer = r.nextInt(1, 101);
        if (randomizer <= 25) {
            initializeBox();
        }
        if (randomizer > 25 && randomizer <= 50) {
            initializeBarrel();
        }
        if (randomizer > 50 && randomizer <= 75) {
            initializeWidePot();
        }
        if (randomizer > 75 && randomizer <= 100) {
            initializeTallPot();
        }
    }
    private void initializeBox() {
        normal = new TextureRegion(props, 0, 0, 16, 32);
        halfBroken= new TextureRegion(props, 0, 32, 16, 32);
        broken = new TextureRegion(props, 0, 64, 16, 16);
        hitbox = new Rectangle(posX, posY, 32, 48);
        type = Drops.Type.COIN;
        breakSound = app.getManager().get("Box.mp3");
    }
    private void initializeBarrel() {
        normal = new TextureRegion(props, 16, 0, 16, 32);
        halfBroken= new TextureRegion(props, 16, 32, 16, 32);
        broken = new TextureRegion(props, 16, 64, 16, 16);
        hitbox = new Rectangle(posX, posY, 32, 48);
        type = Drops.Type.COIN;
        breakSound = app.getManager().get("Barrel.mp3");
    }
    private void initializeWidePot() {
        Random r = new Random();
        int randomizer = r.nextInt(1, 101);
        if (randomizer <= 80) {
            normal = new TextureRegion(props, 32, 16, 16, 16);
            type = Drops.Type.COIN;
        }
        if (randomizer > 80 && randomizer <= 90) {
            normal = new TextureRegion(props, 96, 240, 16, 16);
            type = Drops.Type.HEALTH;
        }
        if (randomizer > 90 && randomizer <= 100) {
            normal = new TextureRegion(props, 128, 240, 16, 16);
            type = Drops.Type.MANA;
        }
        halfBroken = new TextureRegion(props, 32, 32, 16, 16);
        broken = new TextureRegion(props, 32, 48, 16, 16);
        hitbox = new Rectangle(posX,  posY, 32, 32);
        breakSound = app.getManager().get("Ceramic.mp3");
    }
    private void initializeTallPot() {
        Random r = new Random();
        int randomizer = r.nextInt(1, 101);
        if (randomizer <= 80) {
            normal = new TextureRegion(props, 48, 0, 16, 32);
            type = Drops.Type.COIN;
        }
        if (randomizer > 80 && randomizer <= 90) {
            normal = new TextureRegion(props, 112, 224, 16, 32);
            type = Drops.Type.HEALTH;
        }
        if (randomizer > 90 && randomizer <= 100) {
            normal = new TextureRegion(props, 144, 224, 16, 32);
            type = Drops.Type.MANA;
        }
        halfBroken = new TextureRegion(props, 48, 32, 16, 16);
        broken = new TextureRegion(props, 48, 48, 16, 16);
        hitbox = new Rectangle(posX + 8,  posY, 16, 32);
        breakSound = app.getManager().get("Ceramic.mp3");
    }

    public Sound getBreakSound() {
        return breakSound;
    }
}
