package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdx.game.Static;
import com.gdx.game.GameMain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ruangan {
    //bounds
    private Rectangle leftBorder;
    private Rectangle rightBorder;
    private Rectangle bottomBorder;
    private Rectangle upperborder;
    private Texture texture;
    private final String type;
    private ArrayList<Monster> monsters;
    private ArrayList<Breakable> breakables;
    private Rectangle doorHitbox;
    private Rectangle leftDoorHitbox;
    private Rectangle rightDoorHitbox;
    GameMain app;

    public Ruangan(String type) {
        this.type = type;
    }

    /**
     * initialize enemy rooms
     * @param template 1-6 monster location template
     * @param level level scaling (belum diimplement)
     */
    public void initialize(int template, int level) {
        app = (GameMain) Gdx.app.getApplicationListener();
        monsters = new ArrayList<>();
        breakables = new ArrayList<>();
        doorHitbox = new Rectangle(365, 475, 100, 150);
        leftDoorHitbox = new Rectangle(-25, 265, 150, 100);
        rightDoorHitbox = new Rectangle(675, 265, 150, 100);

        initializeBreakable();
        initializeTemplate(template, level);
    }
    private void initializeBreakable() {
        Random randomizer = new Random();
        int breakableAmount = randomizer.nextInt(1, 6);
        for (int i = 0; i < breakableAmount; i++) {
            int posX, posY;
            posX = randomizer.nextInt(58, 720); //posX
            posY = randomizer.nextInt(50,520); //posY

            Breakable breakable = new Breakable(posX, posY);
            breakables.add(breakable);
        }
    }


    public void draw(SpriteBatch batch, float stateTime, Player player) {
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
            texture = app.getManager().get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
            Static.drawDungeonShop(batch, texture);
            rightBorder = new Rectangle(510, 203, 5, 300);
            leftBorder = new Rectangle(205, 203, 5, 300);
            bottomBorder = new Rectangle(205, 213, 347, 8);
            upperborder = new Rectangle(205, 362, 347, 8);
        }

        //enemies
        if (!type.equalsIgnoreCase("Shop") && !type.equalsIgnoreCase("boss")) {
            for (Monster monster : monsters) {
                monster.draw(batch, stateTime);
            }
            for (Breakable b: breakables) {
                b.draw(batch);
            }
        }

        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getState() == Monster.State.DEAD) {
                monsters.remove(i);
            }
        }

        if ((Static.rectangleCollisionDetect(player.hitBox, doorHitbox) ||
                Static.rectangleCollisionDetect(player.hitBox, leftDoorHitbox) ||
                        Static.rectangleCollisionDetect(player.hitBox, rightDoorHitbox)) && monsters.size() == 0) {

            BitmapFont font = new BitmapFont();
            font.getData().setScale(1.5f);
            BitmapFontCache text = new BitmapFontCache(font);
            text.setText("Press Enter", player.getPosX() - 40, player.getPosY() + player.getHeight() * 2);
            text.draw(batch);

            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {

            }
        }
    }











    private void initializeTemplate(int template, int level) {
        if (template == 1) {
            Monster monster1, monster2, monster3;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 200, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 200, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 600, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 600, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
        }
        if (template == 2) {
            Monster monster1, monster2, monster3;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 125, 300,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 125, 300,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 675, 300,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 675, 300,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
        }
        if (template == 3) {
            Monster monster1, monster2, monster3, monster4;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 100, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 100, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 300, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 300, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 450, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 450, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster4= new Monster(50, 10, 0, level, 650, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster4= new Monster(50, 10, 0, level, 650, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
            monsters.add(monster4);
        }
        if (template == 4) {
            Monster monster1, monster2, monster3, monster4;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 100, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 100, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 250, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 250, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 550, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 550, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
            monsters.add(monster4);
        }
        if (template == 5) {
            Monster monster1, monster2, monster3, monster4, monster5;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 150, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 150, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 150, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 150, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 650, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 650, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster5= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster5= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
            monsters.add(monster4);
            monsters.add(monster5);
        }
        if (template == 6) {
            Monster monster1, monster2, monster3, monster4, monster5;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 150, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 150, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 250, 375,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 250, 375,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 550, 375,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 550, 375,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster5= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster5= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
            monsters.add(monster4);
            monsters.add(monster5);
        }
        if (template == 101) {
            Monster monster1;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
        }
    }

    public ArrayList<Monster> getMonsters() {
        ArrayList<Monster> monsterArrayList = new ArrayList<>(monsters);
        return monsterArrayList;
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

    public Rectangle getDoorHitbox() {
        return doorHitbox;
    }
}
