package com.gdx.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.awt.*;

public class Ruangan {

    private Sprite floor;
    private Sprite wall;
    //bounds
    private Rectangle upperWall;
    private Rectangle bottomWall;
    private Rectangle leftWall;
    private Rectangle rightWall;

    public Ruangan(Sprite floor, Sprite wall, Rectangle upperWall, Rectangle bottomWall, Rectangle leftWall, Rectangle rightWall) {
        this.floor = floor;
        this.wall = wall;
        this.upperWall = upperWall;
        this.bottomWall = bottomWall;
        this.leftWall = leftWall;
        this.rightWall = rightWall;
    }
}
