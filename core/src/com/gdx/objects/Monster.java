package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;
import com.gdx.game.Static;

import java.awt.*;

public class Monster extends Karakter implements Attackable{
    //musuh

    //stats

    //hp
    //atk
    //def
    //lvl

    //multiplier untuk stats per naik level
    protected double hpMultiplier;
    protected double damageMultiplier;
    protected double defenceMultiplier;
    protected Texture idle;
    protected Texture run;
    protected Texture die;
    protected Animation<TextureRegion> animationIdleRight;
    protected Animation<TextureRegion> animationIdleLeft;
    protected Animation<TextureRegion> animationRunLeft;
    protected Animation<TextureRegion> animationRunRight;
    protected Animation<TextureRegion> animationDeathLeft;
    protected Animation<TextureRegion> animationDeathRight;
    protected TextureRegion sprite;
    protected TextureRegion currentFrame;
    protected Texture healthBar;
    protected double maxHealth;
    protected GameMain app;
    public Monster(double health, int attack, int defense, int level, int posX, int posY,
                   Rectangle hitBox, double hpMultiplier, double damageMultiplier, double defenceMultiplier, String type) {
        super(health, attack, defense, level, posX, posY, hitBox);
        app = (GameMain) Gdx.app.getApplicationListener();
        this.hpMultiplier = hpMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.defenceMultiplier = defenceMultiplier;
        this.hitBox.setLocation(this.posX, (int) (this.posY - hitBox.getHeight()));
        maxHealth = this.health;
        healthBar = app.getManager().get("healthbar/monsterHealthBar.png");

        if (type.equalsIgnoreCase("orc")) {
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
        if (type.equalsIgnoreCase("skeleton")) {
            idle = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Idle/Idle-Sheet.png");
            run = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Run/Run-Sheet.png");
            die = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Death/Death-Sheet.png");
            animationIdleRight = Static.animate(idle,4, 1, false, false);
            animationIdleLeft = Static.animate(idle,4, 1, true, false);
            animationRunRight = Static.animate(run, 6, 1, false, false);
            animationRunLeft = Static.animate(run,6, 1, true, false);
            animationDeathRight = Static.animate(die, 6, 1, false, false);
            animationDeathLeft = Static.animate(die, 6, 1, true, false);
        }
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }

    private TextureRegion hpBar() {
        double hpPercent = getHealth() / maxHealth;
        double width = healthBar.getWidth() * hpPercent;
        return new TextureRegion(healthBar, 0, 0, (int) width, healthBar.getHeight());
    }

    public void draw(SpriteBatch batch, float stateTime) {
        currentFrame = animationIdleRight.getKeyFrame(stateTime, true);
        setSprite(currentFrame);
        batch.draw(currentFrame, getPosX(), getPosY(), 40, 50);
        batch.draw(hpBar(), getPosX() - 5, getPosY() + 50, hpBar().getRegionWidth() * 3, 5);
    }


    @Override
    public void takeDamage(double dmg) {
        health -= checkNegativeDmg(dmg-defense);
        checkHealth();
    }

    @Override
    public void die(SpriteBatch batch, Animation<TextureRegion> animation, float stateTime) {
        getHitBox().setSize(0, 0);
        currentFrame = animation.getKeyFrame(stateTime, true);
        setSprite(currentFrame);
        batch.draw(currentFrame, getPosX(), getPosY(), 80, 100);
    }

    public void checkHealth() {
        health = Math.max(health, 0);
    }
    public double checkNegativeDmg(double dmg){ //Dmg tidak boleh negative (nanti jadi heal) (dan minimal 1 untuk monster)
        if (dmg < 1){
            return 1;
        }
        else return dmg;
    }
}
