package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.game.Drawer;
import com.gdx.game.GameMain;
import org.w3c.dom.Text;

import java.awt.*;

public class Ruangan {

    private Sprite floor;
    private Sprite wall;
    //bounds
    private Rectangle leftBorder;
    private Rectangle rightBorder;
    private Rectangle bottomBorder;
    private Rectangle upperborder;
    Texture texture;
    String type;

    public Ruangan(String type) {
        this.type = type;
    }

    public void initialize(SpriteBatch batch) {
    }

    public void draw(SpriteBatch batch) {
        GameMain app = (GameMain) Gdx.app.getApplicationListener();
        if (type.equalsIgnoreCase("Dungeon")) {
            texture = app.getManager().get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
            Drawer.drawDungeon(batch, texture);
            leftBorder = new Rectangle(48, 40, 5, 500);
            rightBorder = new Rectangle(710, 40, 5, 500);
            bottomBorder = new Rectangle(48, 55, 705, 8);
            upperborder = new Rectangle(48, 525, 705, 8);
        }
    }

    public Rectangle getLeftBorder() {
        return leftBorder;
    }

    public Rectangle getRightBorder() {
        return rightBorder;
    }

    public Rectangle getBottomBorder() {
        return bottomBorder;
    }

    public Rectangle getUpperborder() {
        return upperborder;
    }
}
