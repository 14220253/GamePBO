package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.UI.MainMenuUI;
import com.gdx.objects.Monster;
import com.gdx.objects.Player;
import com.gdx.objects.Projectile;
import com.gdx.objects.Weapon;
import com.gdx.objects.weaponAnimationHandling.CreateProjectile;
import com.gdx.objects.weaponAnimationHandling.MeleeWeaponAnimation;
import com.gdx.objects.weaponAnimationHandling.RangeWeaponAnimation;

import java.awt.*;
import java.util.ArrayList;

public class GameMain extends ApplicationAdapter {
	MainMenuUI mainMenuUI;
	ArrayList<Projectile>projectiles = new ArrayList<>();
	SpriteBatch batch;
	Texture tiles;
	Texture weapons;
	Player player;
	Texture knightSprite;
	Texture knightRunSprite;
	Texture orcIdle;
	Texture healthBar;
	int fps;
	boolean running;
	boolean isAttacking = false;
	int frameCount = 0;
	int attackCooldown;
	Rectangle leftBorder;
	Rectangle rightBorder;
	Rectangle bottomBorder;
	Rectangle upperborder;
	Sprite activeProjectile;
	TextureRegion currentFrame;

	float stateTime;
	Animation<TextureRegion> playerIdleRight;
	Animation<TextureRegion> playerIdleLeft;
	Animation<TextureRegion> playerRunLeft;
	Animation<TextureRegion> playerRunRight;
	Animation<TextureRegion> orcIdleRight;
	Monster monster1;

	@Override
	public void create () {
		mainMenuUI = new MainMenuUI();
		batch = new SpriteBatch();
		stateTime = 0f;
		tiles = new Texture("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		knightSprite = new Texture("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png");
		knightRunSprite = new Texture("Pixel Crawler - FREE - 1.8/Heroes/Knight/Run/Run-Sheet.png");
		weapons = new Texture("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");
		orcIdle = new Texture("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Idle/Idle-Sheet.png");
		healthBar = new Texture("healthbar/monsterHealthBar.png");

		activeProjectile = new Sprite(weapons, 32,4,15,6);

		fps = 0;

		player = makeRangedPlayer();
		player.setPosX(400);
		player.setPosY(80);
		player.setHitBox(new Rectangle(32, 32));
		leftBorder = new Rectangle(48, 40, 5, 500);
		rightBorder = new Rectangle(710, 40, 5, 500);
		bottomBorder = new Rectangle(48, 55, 705, 8);
		upperborder = new Rectangle(48, 525, 705, 8);

		running = false;

		mainMenuUI.forCreate(); //create UInya

		playerIdleRight = Drawer.animate(knightSprite, 4, 1);
		playerIdleLeft = Drawer.animateFlip(knightSprite, 4, 1);
		playerRunLeft = Drawer.animateFlip(knightRunSprite, 6, 1);
		playerRunRight = Drawer.animate(knightRunSprite, 6, 1);
		orcIdleRight = Drawer.animate(orcIdle, 4, 1);

		monster1 = new Monster(9999, 1, 1, 1, 400, 400,
				new Rectangle(40, 50), 1, 1, 1, orcIdleRight, healthBar);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();
		mainMenuUI.forRender(); //comment func forRender untuk cek game
		mainGame(batch); //comment func mainGame ini untuk cek main menu

		batch.end();
	}
	public void mainGame(SpriteBatch batch) {

		Drawer.drawDungeon(batch, tiles);

		fps++;
		stateTime += Gdx.graphics.getDeltaTime();

		monster1.draw(batch, stateTime);


		if (player.isLookingLeft() && !running) {
			currentFrame = playerIdleLeft.getKeyFrame(stateTime, true);
		} else if (!running){
			currentFrame = playerIdleRight.getKeyFrame(stateTime, true);
		} else if (running && player.isLookingLeft()) {
			currentFrame = playerRunLeft.getKeyFrame(stateTime, true);
		} else {
			currentFrame = playerRunRight.getKeyFrame(stateTime, true);
		}

		player.setSprite(currentFrame);

		if (running) {
			batch.draw(currentFrame, player.getPosX() - 20, player.getPosY(), 80, 100);
		} else {
			batch.draw(currentFrame, player.getPosX(), player.getPosY(), 40, 50);
		}

		fps ++;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (player.getPosY() <= upperborder.getY() - 20) {
				player.moveUp();
			}
			running = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (player.getPosY() >= bottomBorder.getY()) {
				player.moveDown();
			}
			running = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.isLookingLeft()) {
				player.setLookingLeft(true);
			}
			if (player.getPosX() >= leftBorder.getX() + 10) {
				player.moveLeft();
			}
			running = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (player.isLookingLeft()) {
				player.setLookingLeft(false);
			}
			if (player.getPosX() <= rightBorder.getX()) {
				player.moveRight();
			}
			running = true;
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.W) &&
				!Gdx.input.isKeyPressed(Input.Keys.S) &&
				!Gdx.input.isKeyPressed(Input.Keys.A) &&
				!Gdx.input.isKeyPressed(Input.Keys.D)) {
			running = false;
		}
		player.updateHitbox();


		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !isAttacking && attackCooldown == 0) {
			isAttacking = true;
			frameCount = 0;
			attackCooldown = player.getWeapon().getCooldown();
		}
		attackCooldown--;
		attackCooldown = Math.max(attackCooldown, 0);
		if (isAttacking) {
			player.attack(frameCount, batch);
			frameCount++;
		}
		if (frameCount == player.getWeapon().getMaxFrame()) {
			isAttacking = false;
			frameCount = 0;
		}
		if (player.getWeapon().getWeaponAnimation() instanceof CreateProjectile && ((CreateProjectile) player.getWeapon().getWeaponAnimation()).getframeToCreateProjectile() == frameCount){
			Projectile p = ((CreateProjectile) player.getWeapon().getWeaponAnimation()).createProjectile(player,activeProjectile);
			projectiles.add(p);
		}

		for (Projectile projectile : projectiles) {
			projectile.draw(batch);
			// PERLU TAMBAHI IF CLUSTER UNTUK DELETE JIKA KENA MUSUH (HIT COLLOSION) ATAU NABRAK TEMBOK
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tiles.dispose();
	}
	public static float getAngleToMouse(float mouseX, float mouseY, float charX, float charY) {
		// Calculate the angle between the character and the mouse position
		mouseY = Gdx.graphics.getHeight() - mouseY;
		float deltaX = mouseX - charX;
		float deltaY = charY - mouseY;
		float angleRad = (float) Math.atan2(deltaY, deltaX);
		float angleDeg = (float) Math.toDegrees(angleRad);

		return angleDeg;
	}
	public void resize(int width, int height) {
                //viewport.update(width, height);
	}
	public Player makeMeleePlayer(){
		MeleeWeaponAnimation meleeWeaponAnimation = new MeleeWeaponAnimation();
		Weapon weapon = new Weapon("Excalibur", "OP", 99, 1, 2.0f, 2.0f,30,meleeWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(weapons,0, 0,16,46));
		Player player1 = new Player(weapon);
		return player1;
	}
	public Player makeRangedPlayer(){
		RangeWeaponAnimation rangeWeaponAnimation = new RangeWeaponAnimation();
		Weapon weapon = new Weapon("Bowsmth", "NotOP", 99, 1, 2.0f, 1.5f,60,rangeWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(weapons,52,48,9,31));
		weapon.addTextureRegion(new TextureRegion(weapons,67,50,12,27));
		weapon.addTextureRegion(new TextureRegion(weapons,80,51,15,25));
		Player player1 = new Player(weapon);
		return player1;
	}
}
