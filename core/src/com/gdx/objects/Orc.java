package com.gdx.objects;

import com.gdx.game.Static;

import java.awt.*;

public class Orc extends Monster{

    public Orc(double health, int attack, int defense, int level, int posX, int posY, Rectangle hitBox, double hpMultiplier, double damageMultiplier, double defenceMultiplier, String type) {
        super(health, attack, defense, level, posX, posY, hitBox, hpMultiplier, damageMultiplier, defenceMultiplier, "orc");

        idle = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Idle/Idle-Sheet.png");
        run = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Run/Run-Sheet.png");
        die = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Death/Death-Sheet.png");
        animationIdleRight = Static.animate(idle,4, 1, false, false);
        animationIdleLeft = Static.animate(idle,4, 1, true, false);
        animationRunRight = Static.animate(run, 6, 1, false, false);
        animationRunLeft = Static.animate(run,6, 1, true, false);
        animationDeathRight = Static.animate(die, 6, 1, false, false);
        animationDeathLeft = Static.animate(die, 6, 1, true, false);
    }

}
