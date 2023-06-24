package com.gdx.objects.Monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;
import com.gdx.objects.Karakter;

public abstract class Monster extends Karakter {
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
    protected State state = State.ALIVE;
    protected Movement movement = Movement.IDLE;
    protected String type;
    protected int immunityFrames;
    protected float speed;
    protected float deathTimer;
    protected boolean runsToPlayer;
    protected Sound deathSound;
    protected boolean deathSoundPlayed;
    public Monster(double health, double attack, double defense, int level,
                   double hpMultiplier, double damageMultiplier, double defenceMultiplier) {
        super(health, attack, defense, level);
        app = (GameMain) Gdx.app.getApplicationListener();
        this.hpMultiplier = hpMultiplier;
        this.damageMultiplier = damageMultiplier;
        this.defenceMultiplier = defenceMultiplier;
        maxHealth = this.health;
        speed = (float) ((Math.random() * 90) + 80);
        healthBar = app.getManager().get("healthbar/monsterHealthBar.png");
        deathSoundPlayed = false;

        this.health = health * hpMultiplier;
        this.attack = attack * damageMultiplier;
        this.defense = defense * defenceMultiplier;


        hitBox.setSize(40, 50);
        this.hitBox.setLocation(this.posX, this.posY);
    }

    public enum State {
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

    public Sound getDeathSound() {
        return deathSound;
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

        if (movement == Movement.RUNNING && state == State.ALIVE){
            if (getHealth() == 0) {
                state = State.DYING;
            }
            if (lookingLeft)
                currentFrame = animationRunLeft.getKeyFrame(stateTime, true);
            else
                currentFrame = animationRunRight.getKeyFrame(stateTime, true);

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
            if (!deathSoundPlayed) {
                deathSoundPlayed = true;
                deathSound.play(0.4f);
            }
            die(batch, deathTimer);
            deathTimer += 1 * Gdx.graphics.getDeltaTime();
        }
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
        if (animationDeathLeft.isAnimationFinished(deathTimer) || animationDeathRight.isAnimationFinished(deathTimer)) {
            state = State.DEAD;
        }
    }

    public State getState() {
        return state;
    }

    public void checkHealth() {
        health = Math.max(health, 0);
    }

    public boolean isRunsToPlayer() {
        return runsToPlayer;
    }

    public double checkNegativeDmg(double dmg){ //Dmg tidak boleh negative (nanti jadi heal) (dan minimal 1 untuk monster)
        if (dmg < 1){
            return 1;
        }
        else return dmg;
    }
    public void moveToCoordinates(int x, int y, float deltaTime){
        float deltaX = x - posX;
        float deltaY = y - posY;
        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        float distanceToMove = speed * deltaTime;

        if (distance == 0){
            movement = Movement.IDLE;
        }
        else {
            movement = Movement.RUNNING;
        } if (distanceToMove >= distance && state == State.ALIVE) {
            posX = x;
            posY = y;
        } else {
            float moveX = (deltaX / distance) * distanceToMove;
            float moveY = (deltaY / distance) * distanceToMove;
            posX += moveX;
            posY += moveY;
        }
        if (posX > x){
            lookingLeft = true;
        }
        if (posX < x){
            lookingLeft = false;
        }
        updateHitbox();
    }

    public void setRunning(boolean bool) {
        runsToPlayer = bool;
    }

    public int getImmunityFrames() {
        return immunityFrames;
    }
}
