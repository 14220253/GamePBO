package com.gdx.objects.Bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.game.GameMain;
import com.gdx.objects.Karakter;
import com.gdx.objects.Monsters.Monster;
import com.gdx.objects.Skills.Skill;

public abstract class Boss extends Karakter {
    //musuh boss

    //stats

    //hp
    //atk
    //def
    //lvl
    protected double hpMultiplier;
    protected double damageMultiplier;
    protected double defenceMultiplier;
    protected double maxHealth;
    protected boolean deathSoundPlayed;
    protected Texture healthBar;
    protected GameMain app;
    protected boolean isDead;

    public Boss(double health, double attack, double defense, int level,
        double hpMultiplier, double damageMultiplier, double defenceMultiplier) {
        super(health, attack, defense, level);
        this.hpMultiplier = hpMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.defenceMultiplier = defenceMultiplier;
        maxHealth = this.health;
        app = (GameMain) Gdx.app.getApplicationListener();
        healthBar = app.getManager().get("healthbar/monsterHealthBar.png");
        deathSoundPlayed = false;
        isDead = false;
    }

    @Override
    public void takeDamage(double dmg) {
        health -= checkNegativeDmg(dmg-defense);
        checkHealth();
    }

    public abstract void die(SpriteBatch batch, float stateTime);
    public abstract void draw(SpriteBatch batch, float stateTime);

    public void checkHealth() {
        health = Math.max(health, 0);
    }
    public double checkNegativeDmg(double dmg){ //Dmg tidak boleh negative (nanti jadi heal) (dan minimal 1 untuk monster)
        if (dmg < 1){
            return 1;
        }
        else return dmg;
    }

    public boolean isDead() {
        return isDead;
    }
}
