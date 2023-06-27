package com.gdx.objects.Bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.Animator;
import com.gdx.game.Static;
import com.gdx.objects.Map.Ruangan;
import com.gdx.objects.Player;
import jdk.tools.jlink.plugin.PluginException;

import java.awt.*;

public class StoneGolem extends Boss{
    private float spawnTimer;
    private boolean spawning;
    private Animation<TextureRegion> spawnAnimation;
    private Animation<TextureRegion> idleAnimationRight;
    private Animation<TextureRegion> idleAnimationLeft;
    private Animation<TextureRegion> normalArmLaunchLeft;
    private Animation<TextureRegion> normalArmLaunchRight;
    private Texture armLaunchProjectile;
    private Sprite armLaunchSprite;
    private Rectangle armLaunchHitbox;
    private TextureRegion currentFrame;
    private final int WIDTH;
    private final int HEIGHT;
    private final int CENTERX;
    private final int CENTERY;
    private float skillCooldown;
    private BossSkill armLaunch;
    private Facing facing;
    private int playerPosX;
    private int playerPosY;
    private double projectilePosX;
    private double projectilePosY;
    private int projectileWidth;
    private int projectileHeight;
    private Player player;
    private Ruangan ruangan;
    private float immunityFrame;
    enum Facing{
        LEFT,
        RIGHT
    }

    public StoneGolem(double health, double attack, double defense, int level,
                      double hpMultiplier, double damageMultiplier, double defenceMultiplier,
                      Player player, Ruangan ruangan) {
        super(health, attack, defense, level, hpMultiplier, damageMultiplier, defenceMultiplier);
        spawnTimer = 0f;
        spawning = true;
        posX = 270;
        posY = 200;
        WIDTH = 288;
        HEIGHT = 300;
        hitBox = new Rectangle(posX, posY, WIDTH, HEIGHT);
        skillCooldown = 5f;
        facing = Facing.RIGHT;
        playerPosX = 0;
        playerPosY= 0;
        projectileWidth = 300;
        projectileHeight = 300;
        this.player = player;
        this.ruangan = ruangan;
        CENTERX = posX + (WIDTH /2);
        CENTERY = posY + (HEIGHT / 2);
        projectilePosX= posX;
        projectilePosY = posY;

        initializeIdle();
        initializeSpawnAnimation();
        initializeNormalArmLaunch();

        TES = 0f;
    }


    @Override
    public void die(SpriteBatch batch, float stateTime) {

    }

    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        if (playerPosX < posX) {
            facing = Facing.LEFT;
        }
        else {
            facing = Facing.RIGHT;
        }

        if (immunityFrame != 0) {
            currentFrame = idleAnimationRight.getKeyFrame(stateTime, true);
            batch.setColor(Color.RED);
            batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
            immunityFrame -= 1 * Gdx.graphics.getDeltaTime();
            batch.setColor(1, 1, 1, 1);
        }

        //SPAWNING
        if (spawnTimer < 1.5f) {
            currentFrame = spawnAnimation.getKeyFrame(spawnTimer, false);
            batch.draw(currentFrame, posX - 10, posY, WIDTH, HEIGHT);
            spawnTimer += 1 * Gdx.graphics.getDeltaTime();
        } //BATTLE
        else {
            spawning = false;
//            currentFrame = idleAnimationRight.getKeyFrame(stateTime, true);
//            batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);

            armLaunch.drawSkill(batch, TES, player, ruangan);
            TES += 1.5 * Gdx.graphics.getDeltaTime();
        }
    }
    //TEST
    private float TES;
    double degree = 0;
    double angle = 0;
    double kemiringan = 0;
    public void initializeNormalArmLaunch() {
        final Texture armLaunchTexture = app.getManager().get("Bosses/Mecha-stone Golem 0.1/PNG sheet/arm launch.png");
        normalArmLaunchLeft = Animator.animate(armLaunchTexture, 9, 1, true,false);
        normalArmLaunchRight = Animator.animate(armLaunchTexture, 9, 1, false, false);
        armLaunchProjectile = app.getManager().get("Bosses/Mecha-stone Golem 0.1/weapon PNG/arm_projectile.png");
        armLaunchHitbox = new Rectangle();
        armLaunchHitbox.setSize(100, 100);
        armLaunchSprite = new Sprite(armLaunchProjectile);
        armLaunch = new BossSkill() {
            @Override
            public void drawSkill(SpriteBatch batch, float stateTime, Player player, Ruangan ruangan) {
                switch (facing) {
                    case LEFT:
                        currentFrame = normalArmLaunchLeft.getKeyFrame(stateTime, false);
                        batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
                        break;
                    case RIGHT:
                        currentFrame = normalArmLaunchRight.getKeyFrame(stateTime, false);
                        batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
                        break;
                }
                if (normalArmLaunchLeft.isAnimationFinished(stateTime) || normalArmLaunchRight.isAnimationFinished(stateTime)) {
                    if (playerPosX == 0 && playerPosY == 0) {
                        playerPosX = player.getPosX();
                        playerPosY = player.getPosY();
                        angle = Math.atan2(CENTERY - playerPosY, CENTERX - playerPosX);
                        degree = Math.toDegrees(angle);
                        kemiringan = (double) (playerPosY - posY) / (playerPosX - posX) + Static.randomizer(-2, 3);
                        armLaunchSprite.setOrigin((float)armLaunchTexture.getWidth() / 2, (float) armLaunchTexture.getHeight() / 2);
                        armLaunchSprite.rotate((float) degree);

                        if (playerPosX > CENTERX) {
                            projectilePosX= posX;
                            projectilePosY = posY;
                        } else {
                            projectilePosX= posX - (WIDTH / 2);
                            projectilePosY = posY;
                            System.out.println(kemiringan);
                            System.out.println(playerPosX);
                            System.out.println(playerPosY);
                        }
                    }

                    //in border
                    if ((projectilePosX < ruangan.getRightBorder().getX() &&
                            projectilePosX > ruangan.getLeftBorder().getX() - projectileWidth) &&
                            (projectilePosY > ruangan.getBottomBorder().getY() - projectileHeight &&
                                    projectilePosY < ruangan.getUpperborder().getY())) {

                        if (playerPosX > CENTERX) {
                            projectilePosX += 5f;
                            projectilePosY = ((projectilePosX - posX) * kemiringan) + (posX / 2);
                        } else {
                            //TODO
                            projectilePosX -= 5f;
                            projectilePosY = ((projectilePosX - posX) * kemiringan) + posX;
                        }

                        //draw projectile
                        batch.draw(armLaunchSprite, (float) projectilePosX, (float) projectilePosY, projectileWidth, projectileHeight);
                    } else {
                        //stop projectile
                        if (playerPosX > CENTERX) {
                            projectilePosX= posX;
                            projectilePosY = posY;
                        } else {
                            projectilePosX= posX - (WIDTH / 2);
                            projectilePosY = posY;
                        }
                        playerPosX = 0;
                        playerPosY = 0;
                    }
                }
            }

            @Override
            public Rectangle getHurtBox() {
                armLaunchHitbox.setLocation((int) projectilePosX, (int) projectilePosY);
                armLaunchHitbox.setSize(projectileWidth, projectileHeight);
                return armLaunchHitbox;
            }
        };
    }
    public void initializeIdle() {
        Texture idle = app.getManager().get("Bosses/Mecha-stone Golem 0.1/PNG sheet/idle.png");
        idleAnimationRight = Animator.animate(idle, 4, 1, false, false);
        idleAnimationLeft = Animator.animate(idle, 4, 1, true, false);
    }

    public void initializeSpawnAnimation() {
        //ini dipake reverse jadi spawn animation
        Texture invunerable = app.getManager().get("Bosses/Mecha-stone Golem 0.1/PNG sheet/invunerable.png");
        spawnAnimation = Animator.animate(invunerable, 8, 1, false, false, true);
    }

    public boolean isSpawning() {
        return spawning;
    }
    @Override
    public void takeDamage(double dmg) {
        if (immunityFrame == 0) {
            health -= checkNegativeDmg(dmg-defense);
            checkHealth();
            immunityFrame = 30;
        }
    }
    public BossSkill getCurrentSkill() {
        return armLaunch;
    }
}
