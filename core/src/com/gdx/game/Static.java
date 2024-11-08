package com.gdx.game;

import com.badlogic.gdx.math.Intersector;

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
        com.badlogic.gdx.math.Rectangle rectangle1 = new com.badlogic.gdx.math.Rectangle();
        com.badlogic.gdx.math.Rectangle rectangle2 = new com.badlogic.gdx.math.Rectangle();
        rectangle1.setX(rect1.x);
        rectangle1.setY(rect1.y);
        rectangle1.setWidth(rect1.width);
        rectangle1.setHeight(rect1.height);
        rectangle2.setX(rect2.x);
        rectangle2.setY(rect2.y);
        rectangle2.setWidth(rect2.width);
        rectangle2.setHeight(rect2.height);
        return Intersector.overlaps(rectangle1, rectangle2);
    }
    /**
     * coin flip 0/1
     * @param n batas atas(tidak termasuk)
     * @return 0/1
     */
    public static int randomizer(int n) {
        return new Random().nextInt(0, n);
    }
    public static int randomizer(int n1, int n2) {
        return new Random().nextInt(n1, n2);
    }


    public static int getDigits(int num) {
        int result = 0;
        while (num > 0) {
            num /= 10;
            result++;
        }
        return result;
    }
}
