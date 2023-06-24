package com.gdx.objects.Monsters;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.game.Animator;

public class Skeleton extends Monster{
    public Skeleton(double health, int attack, int defense, int level, double hpMultiplier, double damageMultiplier, double defenceMultiplier) {
        super(health, attack, defense, level, hpMultiplier, damageMultiplier, defenceMultiplier);
        idle = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Idle/Idle-Sheet.png");
        run = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Run/Run-Sheet-Resize.png");
        die = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Death/Death-Sheet.png");
        animationIdleRight = Animator.animate(idle,4, 1, false, false);
        animationIdleLeft = Animator.animate(idle,4, 1, true, false);
        animationRunRight = Animator.animate(run, 6, 1, false, false);
        animationRunLeft = Animator.animate(run,6, 1, true, false);
        animationDeathRight = Animator.animate(die, 8, 1, false, false, 0.08f);
        animationDeathLeft = Animator.animate(die, 8, 1, true, false, 0.08f);
        this.runsToPlayer = true;

        deathSound = app.getManager().get("SkeletonDies.mp3");
    }
    @Override
    public void die(SpriteBatch batch, float stateTime) {
        super.die(batch, stateTime);
        batch.draw(currentFrame, getPosX() - 40, getPosY(), 130, 100);
    }
}
