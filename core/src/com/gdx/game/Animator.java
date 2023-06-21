package com.gdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animator {
    /**
     * split animasi sprite yang sizenya sama semua menjadi array untuk class animation
     * @param texture texture yang displit
     * @param column column dalam sheet
     * @param row row dalam sheet
     * @return frames dalam array
     */
    public static TextureRegion[] textureSplitter(Texture texture, int column, int row, int redundantFrames) {
        TextureRegion[] frames;
        TextureRegion[][] temp = TextureRegion.split(texture, texture.getWidth() / column, texture.getHeight() / row);
        frames = new TextureRegion[(column * row) - redundantFrames];
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (index < (column * row) - redundantFrames) {
                    frames[index++] = temp[i][j];
                }
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
        return animate(texture, column, row, flipX, flipY, 0.2f);
    }
    public static Animation<TextureRegion> animate(Texture texture, int column, int row, boolean flipX, boolean flipY, int redundantFrames) {
        return animate(texture, column, row, flipX, flipY, 0.2f, redundantFrames);
    }
    public static Animation<TextureRegion> animate(Texture texture, int column, int row, boolean flipX, boolean flipY, float duration) {
        return animate(texture, column, row, flipX, flipY, duration, 0);
    }
    public static Animation<TextureRegion> animate(Texture texture, int column, int row, boolean flipX, boolean flipY, float duration, int redundantFrames) {
        TextureRegion[] array = textureSplitter(texture, column, row, redundantFrames);
        if (flipX || flipY) {
            for (TextureRegion t : array) {
                t.flip(flipX, flipY);
            }
        }
        return new Animation<>(duration, array);
    }
    public static TextureRegion[] flipImageX(TextureRegion[] textureRegion){
        for (TextureRegion i: textureRegion) {
            i.flip(true,false);
        }
        return textureRegion;
    }
}
