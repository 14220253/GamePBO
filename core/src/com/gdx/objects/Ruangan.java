package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.game.Static;
import com.gdx.game.GameMain;

import java.awt.*;
import java.util.ArrayList;

public class Ruangan {
    //bounds
    private Rectangle leftBorder;
    private Rectangle rightBorder;
    private Rectangle bottomBorder;
    private Rectangle upperborder;
    private Texture texture;
    private String type;
    private ArrayList<Monster> monsters;
    GameMain app;

    public Ruangan(String type) {
        this.type = type;
    }

    public void initialize(int template, int level) {
        app = (GameMain) Gdx.app.getApplicationListener();
        monsters = new ArrayList<>();
        int coinFlip;

        if (template == 1) {
            Monster monster1, monster2, monster3;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 400, 400,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 400, 400,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 250, 400,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 250, 400,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 550, 400,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 550, 400,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
        }
    }

    public void draw(SpriteBatch batch, float stateTime) {
        //map
        if (type.equalsIgnoreCase("Dungeon")) {
            texture = app.getManager().get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
            Static.drawDungeon(batch, texture);
            leftBorder = new Rectangle(48, 40, 5, 500);
            rightBorder = new Rectangle(710, 40, 5, 500);
            bottomBorder = new Rectangle(48, 55, 705, 8);
            upperborder = new Rectangle(48, 525, 705, 8);
        }
        else if (type.equalsIgnoreCase("Forest")) {
            //TODO
        }
        else if (type.equalsIgnoreCase("Shop")) {
            //TODO
        }

        //enemies
        for(Monster monster: monsters) {
            monster.draw(batch, stateTime);
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
