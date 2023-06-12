package com.gdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.*;
import java.util.Random;

public class Static {
    /**
     * mengecek apakah dua rectangle lagi saling kena
     * @param rect1 rectangle1
     * @param rect2 rectangle2
     * @return boolean
     */
    public static boolean rectangleCollisionDetect(Rectangle rect1, Rectangle rect2) {
        boolean result = false;
        if (rect1.getX() < rect2.getX()+ rect2.getWidth() && //rect 2 di kiri 1 di kanan
                rect1.getY() < rect2.getY() + rect2.getHeight() &&//cek tingginya kalo rect2 di bawah
                rect1.getY() + rect1.getHeight() > rect2.getY() //kalo rect1 di bawah
         ) {
            result = true;
        }
        if (rect1.getX()+ rect1.getWidth() > rect2.getX() && //rect 2 di kanan 1 di kiri
                rect1.getY() < rect2.getY() + rect2.getHeight() &&
                rect1.getY() + rect1.getHeight() > rect2.getY()
        ) {
            result = true;
        }
        if (rect1.getY() + rect1.getHeight() > rect2.getY() && //rect 2 di atas 1 di bawah
                rect1.getX() < rect2.getX() + rect2.getWidth() &&
                rect1.getX() + rect1.getWidth() > rect2.getX()
        ) {
            result = true;
        }
        if (rect1.getY() < rect2.getY() + rect2.getHeight() && //rect 2 di bawah 1 di atas
                rect1.getX() < rect2.getX() + rect2.getWidth() &&
                rect1.getX() + rect1.getWidth() > rect2.getX()
        ) {
            result = true;
        }
        return result;
    }
    /**
     * coin flip 0/1
     * @return 0/1
     */
    public static int coinFlip() {
        return new Random().nextInt(0, 2);
    }
    /**
     * menggambar floor untuk level dungeon
     * @param batch spriteBatch untuk fungsi draw
     * @param tiles png dari asset tiles.png
     */
    public static void drawDungeon(SpriteBatch batch, Texture tiles){
        //textures
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
                batch.draw(wallBump, i - 40, 525, 100, 75);
                    batch.draw(closedDoor, i - 13, 525, 50, 75);
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
    public static void drawDungeonShop(SpriteBatch batch, Texture tiles) {
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

    /**
     * split animasi sprite yang sizenya sama semua menjadi array untuk class animation
     * @param texture texture yang displit
     * @param column column dalam sheet
     * @param row row dalam sheet
     * @return frames dalam array
     */
    public static TextureRegion[] textureSplitter(Texture texture, int column, int row) {
        TextureRegion[] frames;
        TextureRegion[][] temp = TextureRegion.split(texture, texture.getWidth() / column, texture.getHeight() / row);
        frames = new TextureRegion[column * row];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                frames[index++] = temp[i][j];
            }
        }
        return frames;
    }

    /**
     * mereturn class animation untuk animasi
     * @param texture texture animasi
     * @param column column
     * @param row row
     * @return Animation<TextureRegion>
     */
    public static Animation<TextureRegion> animate(Texture texture, int column, int row, boolean flipX, boolean flipY) {
        TextureRegion[] array = textureSplitter(texture, column, row);
        if (flipX || flipY) {
            for (TextureRegion t : array) {
                t.flip(flipX, flipY);
            }
        }
        return new Animation<>(0.2f, array);
    }
    public static TextureRegion[] flipImageX(TextureRegion[] textureRegion){
        for (TextureRegion i: textureRegion) {
            i.flip(true,false);
        }
        return textureRegion;
    }
}
