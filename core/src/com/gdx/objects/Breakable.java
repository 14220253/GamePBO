package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;

import java.awt.*;
import java.util.Random;

public class Breakable {
    //pot dll.
    GameMain app;
    Texture props;
    TextureRegion halfBroken;
    TextureRegion normal;
    TextureRegion broken;
    Rectangle hitbox;
    int posX;
    int posY;

    enum state {
        NORMAL,
        BROKEN
    }

    public Breakable(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
        app = (GameMain) Gdx.app.getApplicationListener();
        props = app.getManager().get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Props.png");
        initialize();
    }
    public void draw(SpriteBatch batch) {
        batch.draw(normal, posX, posY, 32, normal.getRegionHeight() == 16 ? 32 : 64);
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
        hitbox = new Rectangle(posX, 600 - posY - 32, 16, 32);
    }
    private void initializeBarrel() {
        normal = new TextureRegion(props, 16, 0, 16, 32);
        halfBroken= new TextureRegion(props, 16, 32, 16, 32);
        broken = new TextureRegion(props, 16, 64, 16, 16);
        hitbox = new Rectangle(posX, 600 - posY - 32, 16, 32);
    }
    private void initializeWidePot() {
        Random r = new Random();
        int randomizer = r.nextInt(1, 101);
        if (randomizer <= 80) {
            normal = new TextureRegion(props, 32, 16, 16, 16);
        }
        if (randomizer > 80 && randomizer <= 90) {
            normal = new TextureRegion(props, 96, 240, 16, 16);
        }
        if (randomizer > 90 && randomizer <= 100) {
            normal = new TextureRegion(props, 128, 240, 16, 16);
        }
        halfBroken = new TextureRegion(props, 32, 32, 16, 16);
        broken = new TextureRegion(props, 32, 48, 16, 16);
        hitbox = new Rectangle(posX, 600 - posY - 16, 16, 16);
    }
    private void initializeTallPot() {
        Random r = new Random();
        int randomizer = r.nextInt(1, 101);
        if (randomizer <= 80) {
            normal = new TextureRegion(props, 48, 0, 16, 32);
        }
        if (randomizer > 80 && randomizer <= 90) {
            normal = new TextureRegion(props, 112, 240, 16, 32);
        }
        if (randomizer > 90 && randomizer <= 100) {
            normal = new TextureRegion(props, 144, 224, 16, 32);
        }
        halfBroken = new TextureRegion(props, 48, 32, 16, 16);
        broken = new TextureRegion(props, 48, 48, 16, 16);
        hitbox = new Rectangle(posX, 600 - posY - 32, 16, 32);
    }
}
