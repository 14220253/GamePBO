package com.gdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Drawer {
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
            if (i == 200 || i == 300 || i == 400 || i == 500) {
                if (i != 200) {
                    batch.draw(wallBumpWithShadow, i - 25, 525, 100, 75);
                    batch.draw(closedDoor, i - 13, 525, 50, 75);
                } else {
                    batch.draw(wallBump, i - 25, 525, 100, 75);
                }
                if (i != 200) {
                    i -= 50;
                }
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
    }

    /**
     * split animasi sprite yang sizenya sama semua menjadi array untuk class animation
     * @param texture texture yang displit
     * @param column column dalam sheet
     * @param row row dalam sheet
     * @return frames dalam array
     */
    protected static TextureRegion[] textureSplitter(Texture texture, int column, int row) {
        TextureRegion[][] temp = TextureRegion.split(texture, texture.getWidth() / column, texture.getHeight() / row);
        TextureRegion[] frames = new TextureRegion[column * row];
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
    protected static Animation<TextureRegion> animate(Texture texture, int column, int row) {
        TextureRegion[] array = textureSplitter(texture, column, row);
        return new Animation<>(0.2f, array);
    }

    protected static Animation<TextureRegion> animateFlip(Texture texture, int column, int row) {
        TextureRegion[] array = textureSplitter(texture, column, row);
        for (TextureRegion t: array) {
            t.flip(true, false);
        }
        return new Animation<>(0.2f, array);
    }
}
