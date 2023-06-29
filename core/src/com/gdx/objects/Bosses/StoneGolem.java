package com.gdx.objects.Bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.gdx.game.Animator;
import com.gdx.game.Static;
import com.gdx.objects.Map.Ruangan;
import com.gdx.objects.Player;

import java.awt.*;

public class StoneGolem extends Boss{
    private float spawnTimer;
    private boolean spawning;
    private Animation<TextureRegion> spawnAnimation;
    private Animation<TextureRegion> idleAnimationRight;
    private Animation<TextureRegion> idleAnimationLeft;
    private Animation<TextureRegion> normalArmLaunchLeft;
    private Animation<TextureRegion> normalArmLaunchRight;
    private Animation<TextureRegion> retractArmLaunchLeft;
    private Animation<TextureRegion> retractArmLaunchRight;
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
    private Vector2 playerPosition;
    private Vector2 projectilePosition;
    private int projectileWidth;
    private int projectileHeight;
    private Player player;
    private Ruangan ruangan;
    private float immunityFrame;
    private float degree;
    private double kemiringan;
    private boolean armLaunchDone;
    private float animationTimer;
    private float animationDoneTimer;
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
        int multiplier = 3;
        posX = 270;
        posY = 200;
        WIDTH = 96 * multiplier;
        HEIGHT = 100 * multiplier;
        hitBox = new Rectangle(posX + (25 * multiplier), posY + (25 * multiplier)
                , WIDTH - (50 * multiplier), HEIGHT - (50 * multiplier));
        skillCooldown = 2f;
        facing = Facing.RIGHT;
        playerPosition = new Vector2(0, 0);
        this.player = player;
        this.ruangan = ruangan;
        CENTERX = posX + (WIDTH /2);
        CENTERY = posY + (HEIGHT / 2);
        projectilePosition = new Vector2(CENTERX, CENTERY);
        armLaunchDone = false;
        kemiringan = 0;
        degree = 0;
        animationTimer = 0f;
        animationDoneTimer = 0f;

        initializeIdle();
        initializeSpawnAnimation();
        initializeNormalArmLaunch();
    }


    @Override
    public void die(SpriteBatch batch, float stateTime) {

    }

    @Override
    public void draw(SpriteBatch batch, float stateTime) {
        if (player.getPosX() < posX) {
            facing = Facing.LEFT;
        }
        else {
            facing = Facing.RIGHT;
        }


        //SPAWNING
        if (!spawnAnimation.isAnimationFinished(spawnTimer)) {
            currentFrame = spawnAnimation.getKeyFrame(spawnTimer, false);
            batch.draw(currentFrame, posX - 10, posY, WIDTH, HEIGHT);
            spawnTimer += 1 * Gdx.graphics.getDeltaTime();
        } //BATTLE
        else {
            spawning = false;
            if (immunityFrame > 0) {
                currentFrame = idleAnimationRight.getKeyFrame(stateTime, true);
                batch.setColor(Color.RED);
                batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
                immunityFrame -= 1 * Gdx.graphics.getDeltaTime();
                batch.setColor(1, 1, 1, 1);
            }
            else {
                System.out.println(skillCooldown);
                if (skillCooldown <= 0) {
                    armLaunch.drawSkill(batch, player, ruangan);
                } else {
                    switch (facing) {
                        case LEFT:
                            currentFrame = idleAnimationRight.getKeyFrame(stateTime, true);
                            break;
                        case RIGHT:
                            currentFrame = idleAnimationLeft.getKeyFrame(stateTime, true);
                            break;
                    }
                    skillCooldown -= 1 * Gdx.graphics.getDeltaTime();
                    batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
                }
//                System.out.println(skillCooldown);
                immunityFrame -= 1 * Gdx.graphics.getDeltaTime();
            }
        }
    }

    public void initializeNormalArmLaunch() {
        Texture armLaunchTexture = app.getManager().get("Bosses/Mecha-stone Golem 0.1/PNG sheet/arm launch.png");
        normalArmLaunchLeft = Animator.animate(armLaunchTexture, 9, 1, true,false);
        normalArmLaunchRight = Animator.animate(armLaunchTexture, 9, 1, false, false);
        armLaunchProjectile = app.getManager().get("Bosses/Mecha-stone Golem 0.1/weapon PNG/arm_projectile_resize.png");
        retractArmLaunchRight= Animator.animate(armLaunchTexture, 9, 1, false, false, true);
        retractArmLaunchLeft= Animator.animate(armLaunchTexture, 9, 1, true, false, true);
        armLaunchHitbox = new Rectangle();
        armLaunchHitbox.setSize(50, 20);
        projectileWidth = armLaunchHitbox.width;
        projectileHeight = armLaunchHitbox.height;
        armLaunch = new BossSkill() {
            @Override
            public void drawSkill(SpriteBatch batch, Player player, Ruangan ruangan) {
                if (!armLaunchDone) {
                    switch (facing) {
                        case LEFT:
                            currentFrame = normalArmLaunchLeft.getKeyFrame(animationTimer, false);
                            batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
                            animationTimer += 1.5 * Gdx.graphics.getDeltaTime();
                            break;
                        case RIGHT:
                            currentFrame = normalArmLaunchRight.getKeyFrame(animationTimer, false);
                            batch.draw(currentFrame, posX, posY, WIDTH, HEIGHT);
                            animationTimer += 1.5 * Gdx.graphics.getDeltaTime();
                            break;
                    }
                    if (normalArmLaunchLeft.isAnimationFinished(animationTimer)
                            || normalArmLaunchRight.isAnimationFinished(animationTimer) &&
                            !armLaunchDone) {
                        if (playerPosition.x == 0 && playerPosition.y == 0) {
                            projectilePosition.set(CENTERX, CENTERY);
                            playerPosition.set(player.getPosX(), player.getPosY());
                            degree = projectilePosition.angleDeg(playerPosition);
                            armLaunchSprite = new Sprite(armLaunchProjectile);
                            kemiringan = ((CENTERY - playerPosition.y)
                                    / (CENTERX - playerPosition.x))
                                    + Static.randomizer(-2, 3)
                            ;
                        }

                        //in border
                        if ((projectilePosition.x <= ruangan.getRightBorder().getX() &&
                                projectilePosition.x > ruangan.getLeftBorder().getX() - projectileWidth) &&
                                (projectilePosition.y >= ruangan.getBottomBorder().getY() - projectileHeight &&
                                        projectilePosition.y < ruangan.getUpperborder().getY())) {

                            if (playerPosition.x >= CENTERX) {
                                projectilePosition.x += 5f;
                            } else {
                                projectilePosition.x -= 5f;
                            }
                            projectilePosition.y = (float)
                                    (playerPosition.y + (
                                            (projectilePosition.x - playerPosition.x) * kemiringan));


                            armLaunchHitbox.setLocation((int) projectilePosition.x, (int) projectilePosition.y);
                            //draw projectile
                            armLaunchSprite.rotate(degree);
                            batch.draw(armLaunchSprite, projectilePosition.x, projectilePosition.y
                                    , projectileWidth * 2, projectileHeight * 2);

                        } else {
                            //stop projectile
                            projectilePosition.set(0, 0);
                            playerPosition.set(0, 0);
                            if (retractArmLaunchRight.isAnimationFinished(animationDoneTimer) ||
                                    retractArmLaunchLeft.isAnimationFinished(animationDoneTimer)) {
                                switch (facing) {
                                    case RIGHT:
                                        currentFrame = retractArmLaunchRight.getKeyFrame(animationDoneTimer, false);
                                        animationDoneTimer += 1.5 * Gdx.graphics.getDeltaTime();
                                        break;
                                    case LEFT:
                                        currentFrame = retractArmLaunchLeft.getKeyFrame(animationDoneTimer, false);
                                        animationDoneTimer += 1.5 * Gdx.graphics.getDeltaTime();
                                        break;
                                }
                            } else {
                                skillCooldown = 5f;
                                armLaunchDone = true;
                                System.out.println("BRUHBRUHBRUH");
                            }
                        }
                    }
                }
            }

            @Override
            public Rectangle getHurtBox() {
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
