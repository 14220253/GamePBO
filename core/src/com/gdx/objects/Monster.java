package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gdx.game.Animator;
import com.gdx.game.GameMain;
import com.gdx.game.Static;

import java.awt.*;
import java.util.Random;

public class Monster extends Karakter {
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
    private Texture idle;
    private Texture run;
    private Texture die;
    private Animation<TextureRegion> animationIdleRight;
    private Animation<TextureRegion> animationIdleLeft;
    private Animation<TextureRegion> animationRunLeft;
    private Animation<TextureRegion> animationRunRight;
    private Animation<TextureRegion> animationDeathLeft;
    private Animation<TextureRegion> animationDeathRight;
    private TextureRegion sprite;
    private TextureRegion currentFrame;
    private Texture healthBar;
    private double maxHealth;
    private GameMain app;
    private State state = State.ALIVE;
    private Movement movement = Movement.IDLE;
    private String type;
    private int immunityFrames;
    private float speed;
    private float deathTimer;
    public Monster(double health, int attack, int defense, int level, int posX, int posY,
                   Rectangle hitBox, double hpMultiplier, double damageMultiplier, double defenceMultiplier, String type) {
        super(health, attack, defense, level, posX, posY, hitBox);
        app = (GameMain) Gdx.app.getApplicationListener();
        this.hpMultiplier = hpMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.defenceMultiplier = defenceMultiplier;
        maxHealth = this.health;
        speed = (float) ((Math.random() * 90) + 40);
        healthBar = app.getManager().get("healthbar/monsterHealthBar.png");

        if (type.equalsIgnoreCase("orc")) {
            idle = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Idle/Idle-Sheet.png");
            run = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Run/Run-Sheet-Resize.png");
            die = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Death/Death-Sheet.png");
            animationIdleRight = Animator.animate(idle,4, 1, false, false);
            animationIdleLeft = Animator.animate(idle,4, 1, true, false);
            animationRunRight = Animator.animate(run, 6, 1, false, false);
            animationRunLeft = Animator.animate(run,6, 1, true, false);
            animationDeathRight = Animator.animate(die, 6, 1, false, false, 0.08f);
            animationDeathLeft = Animator.animate(die, 6, 1, true, false, 0.08f);
            this.type = type;
        }
        if (type.equalsIgnoreCase("skeleton")) {
            idle = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Idle/Idle-Sheet.png");
            run = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Run/Run-Sheet-Resize.png");
            die = app.getManager().get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Death/Death-Sheet.png");
            animationIdleRight = Animator.animate(idle,4, 1, false, false);
            animationIdleLeft = Animator.animate(idle,4, 1, true, false);
            animationRunRight = Animator.animate(run, 6, 1, false, false);
            animationRunLeft = Animator.animate(run,6, 1, true, false);
            animationDeathRight = Animator.animate(die, 8, 1, false, false, 0.08f);
            animationDeathLeft = Animator.animate(die, 8, 1, true, false, 0.08f);
            this.type = type;
        }

        this.hitBox.setLocation(this.posX, this.posY);
    }

    enum State {
        ALIVE,
        DYING,
        DEAD
    }
    enum Movement{
        IDLE,
        RUNNING
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
        if (state == State.ALIVE && movement == Movement.IDLE) {
            if (getHealth() == 0) {
                state = State.DYING;
            }
            currentFrame = animationIdleRight.getKeyFrame(stateTime, true);
            setSprite(currentFrame);
            if (immunityFrames != 0) {
                batch.setColor(Color.RED);
                batch.draw(currentFrame, getPosX(), getPosY(), 40, 50);
                immunityFrames -= 1 * Gdx.graphics.getDeltaTime();
                batch.setColor(1, 1, 1, 1);
            } else {
                batch.draw(currentFrame, getPosX(), getPosY(), 40, 50);
            }
            batch.draw(hpBar(), getPosX() - 5, getPosY() + 50, hpBar().getRegionWidth() * 3, 5);
        }
        if (state == State.DYING) {
            die(batch, deathTimer);
            deathTimer += 1 * Gdx.graphics.getDeltaTime();
        }
    }
    private void run(float stateTime, SpriteBatch batch) {
        if (lookingLeft)
            currentFrame = animationRunLeft.getKeyFrame(stateTime, false);
        else
            currentFrame = animationRunRight.getKeyFrame(stateTime, false);
        setSprite(currentFrame);
        if (type.equalsIgnoreCase("orc")) {
            batch.draw(currentFrame, getPosX(), getPosY(), 80, 100);
        } else {
            batch.draw(currentFrame, getPosX() - 40, getPosY(), 130, 100);
        }
        batch.draw(hpBar(), getPosX() - 5, getPosY() + 50, hpBar().getRegionWidth() * 3, 5);
    }


    @Override
    public void takeDamage(double dmg) {
        if (immunityFrames == 0) {
            health -= checkNegativeDmg(dmg - defense);
            checkHealth();
            immunityFrames = 30;
        }
    }

    public void die(SpriteBatch batch, float stateTime) {
        getHitBox().setSize(0, 0);
        getHitBox().setLocation(0, 0);
        if (isLookingLeft()) {
            currentFrame = animationDeathLeft.getKeyFrame(stateTime, false);
        }
        else {
            currentFrame = animationDeathRight.getKeyFrame(stateTime, false);
        }

        setSprite(currentFrame);
        if (type.equalsIgnoreCase("orc")) {
            batch.draw(currentFrame, getPosX(), getPosY(), 80, 100);
        } else {
            batch.draw(currentFrame, getPosX() - 40, getPosY(), 130, 100);
        }
        if (animationDeathLeft.isAnimationFinished(stateTime) || animationDeathRight.isAnimationFinished(stateTime)) {
            state = State.DEAD;
        }
    }

    public State getState() {
        return state;
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
    public void runToCoordinates(int x, int y, float deltaTime){
        float angle = MathUtils.atan2(y - posY, x - posX);
        float distance = Vector2.dst(posX, posY, x, y);
        float distanceToMove = speed * deltaTime;
        if (distanceToMove >= distance) {
            posX = x;
            posY = y;
        } else {
            posX += distanceToMove * MathUtils.cos(angle);
            posY += distanceToMove * MathUtils.sin(angle);
        }
    }
}
