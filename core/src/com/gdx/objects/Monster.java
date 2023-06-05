package com.gdx.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class Monster extends Karakter implements Attackable{
    //musuh

    //stats

    //hp
    //atk
    //def
    //lvl

    //multiplier untuk stats per naik level
    private double hpMultiplier;
    private double damageMultiplier;
    private double defenceMultiplier;
    private Animation<TextureRegion> animation;
    private TextureRegion sprite;
    private TextureRegion currentFrame;
    private Texture healthBar;
    private double maxHealth;

    public Monster(double health, int attack, int defense, int level, int posX, int posY, Rectangle hitBox, double hpMultiplier, double damageMultiplier, double defenceMultiplier, Animation<TextureRegion> animation, Texture healthBar) {
        super(health, attack, defense, level, posX, posY, hitBox);
        this.hpMultiplier = hpMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.defenceMultiplier = defenceMultiplier;
        this.animation = animation;
        this.hitBox.setLocation(this.posX, this.posY);
        this.healthBar = healthBar;
        maxHealth = this.health;
    }

    public TextureRegion getSprite() {
        return sprite;
    }

    public void setSprite(TextureRegion sprite) {
        this.sprite = sprite;
    }

    private TextureRegion hpBar() {
        double hpPercent = health / maxHealth;
        double width = healthBar.getWidth() * hpPercent;
        return new TextureRegion(healthBar, (int) width, healthBar.getHeight());
    }

    public void draw(SpriteBatch batch, float stateTime) {
        currentFrame = animation.getKeyFrame(stateTime, true);
        setSprite(currentFrame);
        batch.draw(currentFrame, getPosX(), getPosY(), 40, 50);
        batch.draw(hpBar(), getPosX(), getPosY() + 50, 40, 5);
    }


    @Override
    public void takeDamage(double dmg) {
        health -= checkNegativeDmg(dmg-defense);
        checkHealth();
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
