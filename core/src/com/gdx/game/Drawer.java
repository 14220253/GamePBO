package com.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.Buffs;

public class Drawer {
    /**
     * menggambar floor untuk level dungeon
     * @param batch spriteBatch untuk fungsi draw
     */
    public static void drawDungeon(SpriteBatch batch){
        //textures
        GameMain app = (GameMain) Gdx.app.getApplicationListener();
        Texture tiles = app.getManager().get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
        TextureRegion wallBump = new TextureRegion(tiles, 8, 0, 55, 50);
        TextureRegion wallBumpWithShadow = new TextureRegion(tiles, 0, 0, 63, 50);
        TextureRegion walls = new TextureRegion(tiles, 8, 0, 32, 50);
        TextureRegion closedDoor = new TextureRegion(tiles, 0, 112, 32, 50);
        TextureRegion sideWall = new TextureRegion(tiles, 0, 50, 3, 24);
        TextureRegion bottomLeftWall = new TextureRegion(tiles, 0, 56, 24, 24);
        TextureRegion bottomRightWall = new TextureRegion(tiles, 25, 56, 24, 24);
        TextureRegion bottomWall = new TextureRegion(tiles, 3, 75, 40, 5);
        TextureRegion floor = new TextureRegion(tiles, 71, 16, 31, 25);
        TextureRegion topShadowFloor = new TextureRegion(tiles, 71, 0, 31, 25);
        TextureRegion rightDoor = new TextureRegion(tiles, 248, 95, 7, 90);
        TextureRegion leftDoor = new TextureRegion(tiles, 255, 95, 10, 90);

        //bottom wall
        batch.draw(bottomRightWall, 713, 40, 40, 40);
        batch.draw(bottomLeftWall, 48, 40, 40, 40);

        for (int i = 80; i <= 680; i+= 40) {
            batch.draw(bottomWall, i, 40, 64, 8);
        }

        //floor
        for (int i = 53; i <= 683; i += 63) { //kiri ke kanan
            for (int j = 48; j <= 497 ; j += 50) { //atas ke bawah
                batch.draw(floor, i, j, 63, 50);
            }
            batch.draw(topShadowFloor, i, 475, 63, 50);
        }

        //upper walls + buff doors
        for (int i = 700; i >= 50 ; i -= 50) {
            if (i == 400) {
                batch.draw(wallBumpWithShadow, i - 25, 525, 100, 75);
                batch.draw(closedDoor, i - 13, 525, 50, 75);
                batch.draw(wallBump, i - 125, 525, 100, 75);
                i -= 100;
            }
            else {
                batch.draw(walls, i, 525, 50, 75);
            }
        }

        //side wall
        for (int i = 540; i > 40; i -= 25) {
            batch.draw(sideWall, 745, i, 5, 40);
            batch.draw(sideWall, 48, i, 5, 40);
        }

        //side door
        batch.draw(rightDoor, 738, 250, 10, 120);
        batch.draw(leftDoor, 48, 250, 10, 120);
    }

    /**
     * dungeon shop room
     */
    public static void drawDungeonShop(SpriteBatch batch) {
        GameMain app = (GameMain) Gdx.app.getApplicationListener();
        Texture tiles = app.getManager().get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
        TextureRegion walls = new TextureRegion(tiles, 8, 0, 32, 50);
        TextureRegion closedDoor = new TextureRegion(tiles, 0, 112, 32, 50);
        TextureRegion sideWall = new TextureRegion(tiles, 0, 50, 3, 24);
        TextureRegion bottomLeftWall = new TextureRegion(tiles, 0, 56, 24, 24);
        TextureRegion bottomRightWall = new TextureRegion(tiles, 25, 56, 24, 24);
        TextureRegion bottomWall = new TextureRegion(tiles, 3, 75, 40, 5);
        TextureRegion floor = new TextureRegion(tiles, 71, 16, 31, 25);
        TextureRegion topShadowFloor = new TextureRegion(tiles, 71, 0, 31, 25);

        //bottom wall
        batch.draw(bottomRightWall, 514, 200, 40, 40);
        batch.draw(bottomLeftWall, 210, 200, 40, 40);

        //floor
        for (int i = 216; i <= 510; i += 47) { //kiri ke kanan
            for (int j = 208; j <= 330 ; j += 50) { //atas ke bawah
                batch.draw(floor, i, j, 47, 50);
            }
            batch.draw(topShadowFloor, i, 331, 47, 50);
        }

        //bottom wall pt.2
        for (int i = 250; i <= 450; i+= 40) {
            batch.draw(bottomWall, i, 200, 64, 8);
        }

        //upper walls + buff doors
        for (int i = 212; i < 540 ; i += 42) {
            batch.draw(walls, i, 373, 42, 75);
            if (i == 380) {
                batch.draw(closedDoor, 356, 373, 50, 75);
            }
        }

        //side wall
        for (int i = 240; i <= 400; i += 25) {
            batch.draw(sideWall, 546, i, 5, 40);
            batch.draw(sideWall, 210, i, 5, 40);
        }
    }

    public static void drawCard(SpriteBatch batch, Buffs buff) {
        GameMain app = (GameMain) Gdx.app.getApplicationListener();
        Texture cards = app.getManager().get("pixelCardAssest_V01.png");

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f);
        BitmapFontCache text = new BitmapFontCache(font);


        switch (buff.getType()) {
            case RESTORE_HP:
            case HEALTH_MULTIPLIER:
                drawRedCard(batch, buff, cards, text);
                break;
            case DEFENSE:
            case RESTORE_MANA:
                drawBlueCard(batch, buff, cards, text);
                break;
            case ATTACK:
                drawGreyCard(batch, buff, cards, text);
                break;
            case EVASION:
                drawGreenCard(batch, buff, cards, text);
            case WEAPON_SIZE:
            case MAX_HEALTH:
                drawYellowCard(batch, buff, cards, text);
        }
    }
    private static void drawRedCard(SpriteBatch batch, Buffs buff, Texture texture, BitmapFontCache text) {
        TextureRegion base = new TextureRegion(texture, 121, 0, 120, 135);
        batch.draw(base, 200, 150, 400, 450);
//        text.setText("Press Enter");
//        text.draw(batch);
    }
    private static void drawBlueCard(SpriteBatch batch, Buffs buff, Texture texture, BitmapFontCache text) {
        TextureRegion base = new TextureRegion(texture, 0, 0, 120, 135);
        batch.draw(base, 200, 150, 400, 450);
    }
    private static void drawGreyCard(SpriteBatch batch, Buffs buff, Texture texture, BitmapFontCache text) {
        TextureRegion base = new TextureRegion(texture, 241, 0, 120, 135);
        batch.draw(base, 200, 150, 400, 450);
    }
    private static void drawGreenCard(SpriteBatch batch, Buffs buff, Texture texture, BitmapFontCache text) {
        TextureRegion base = new TextureRegion(texture, 361, 0, 120, 135);
        batch.draw(base, 200, 150, 400, 450);
    }
    private static void drawYellowCard(SpriteBatch batch, Buffs buff, Texture texture, BitmapFontCache text) {
        TextureRegion base = new TextureRegion(texture, 481, 0, 120, 135);
        batch.draw(base, 200, 150, 400, 450);
    }
}
