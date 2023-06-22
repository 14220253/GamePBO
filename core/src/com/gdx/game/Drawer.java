package com.gdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.gdx.UI.ShopUI;
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
        //NPC
        Texture NPC = app.getManager().get("Idle Working.png");
        Animation<TextureRegion> NPCAnimation = Animator.animate(NPC,8,1,false, false);
        TextureRegion currentFrame = NPCAnimation.getKeyFrame(app.stateTime,true);
        batch.draw(currentFrame, 250,400);
        //test
        float dx = app.getPlayer().getPosX() - 20;
        float dy = app.getPlayer().getPosY() - 50;
        float d = dx*dx + dy*dy;
        if(d <= 256){
            app.setScreen(new ShopUI());
        }
    }

    public static void drawCard(SpriteBatch batch, Buffs buff) {
        GameMain app = (GameMain) Gdx.app.getApplicationListener();
        Texture cards = app.getManager().get("pixelCardAssest_V01.png");

        //CARD
        switch (buff.getType()) {
            case RESTORE_HP:
                drawRedCard(batch, cards);
                break;
            case DEFENSE:
            case RESTORE_MANA:
                drawBlueCard(batch, cards);
                break;
            case ATTACK:
                drawGreyCard(batch, cards);
                break;
            case EVASION:
                drawGreenCard(batch, cards);
            case WEAPON_SIZE:
            case MAX_HEALTH:
                drawYellowCard(batch, cards);
        }

        //BASE TEXT
        switch (buff.getTier()) {
            case 1:
                drawBronzeBase(batch, cards);
                break;
            case 2:
                drawStoneBase(batch, cards);
                break;
            case 3:
                drawGoldBase(batch, cards);
                break;
        }

        //LOGO BASE
        drawLogoBase(batch, cards);

        //LOGO

        //TEXT
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2f);
        BitmapFontCache text = new BitmapFontCache(font);
        text.setColor(Color.BLACK);
        text.setText(buff.getBuffName(), 320,265);
        text.draw(batch);
        text.setColor(Color.WHITE);
        text.setText(buff.getBuffName(), 320, 263);
        text.draw(batch);
        font.getData().setScale(1.5f);
        text = new BitmapFontCache(font);
        text.setColor(Color.BLACK);
        text.setText(buff.getBuffDescription(), 310,232);
        text.draw(batch);
        text.setColor(Color.WHITE);
        text.setText(buff.getBuffDescription(), 310, 230);
        text.draw(batch);
    }
    private static void drawLogoBase(SpriteBatch batch, Texture texture) {
        TextureRegion wall = new TextureRegion(texture, 601, 150, 120, 135);
        batch.draw(wall, 255, 300, 355, 270);
    }
    private static void drawBronzeBase(SpriteBatch batch, Texture texture) {
        TextureRegion base = new TextureRegion(texture, 0, 220, 120, 40);
        batch.draw(base, 220, 160, 390, 130);
    }
    private static void drawStoneBase(SpriteBatch batch, Texture texture) {
        TextureRegion base = new TextureRegion(texture, 121, 220, 120, 40);
        batch.draw(base, 220, 160, 390, 150);
    }
    private static void drawGoldBase(SpriteBatch batch, Texture texture) {
        TextureRegion base = new TextureRegion(texture, 241, 220, 120, 40);
        batch.draw(base, 220, 160, 390, 150);
    }
    private static void drawRedCard(SpriteBatch batch, Texture texture) {
        TextureRegion card = new TextureRegion(texture, 121, 0, 120, 135);
        batch.draw(card, 205, 150, 430, 450);
    }
    private static void drawBlueCard(SpriteBatch batch, Texture texture) {
        TextureRegion card = new TextureRegion(texture, 0, 0, 120, 135);
        batch.draw(card, 200, 150, 430, 450);
    }
    private static void drawGreyCard(SpriteBatch batch, Texture texture) {
        TextureRegion card = new TextureRegion(texture, 241, 0, 120, 135);
        batch.draw(card, 215, 150, 430, 450);
    }
    private static void drawGreenCard(SpriteBatch batch, Texture texture) {
        TextureRegion card = new TextureRegion(texture, 361, 0, 120, 135);
        batch.draw(card, 200, 150, 430, 450);
    }
    private static void drawYellowCard(SpriteBatch batch, Texture texture) {
        TextureRegion card = new TextureRegion(texture, 481, 0, 120, 135);
        batch.draw(card, 240, 150, 430, 450);
    }
}
