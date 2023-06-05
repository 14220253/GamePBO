package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.objects.Monster;
import com.gdx.objects.Player;
import com.gdx.objects.Projectile;

import java.awt.*;
import java.util.ArrayList;

public class GameMain extends ApplicationAdapter {
	ArrayList<Projectile>projectiles = new ArrayList<>();
	SpriteBatch batch;
	Texture tiles;
	Texture weapons;
	Player player;
	Texture knightSprite;
	int fps;
	boolean isMeleeAttacking = false;
	boolean isRangedAttacking = false;
	boolean isMagicAttacking = false;
	int frameCount = 0;
	int fixX, fixY;
	int attackCooldown;
	Rectangle leftBorder;
	Rectangle rightBorder;
	Rectangle bottomBorder;
	Rectangle upperborder;
	Texture mainMenu;
	TextureRegion menuWindow;
	TextureRegion startButtonIdle;
	TextureRegion optionsButtonIdle;
	TextureRegion exitButtonIdle;
	Rectangle startButtonBox;
	Rectangle optionsButtonBox;
	Rectangle exitButtonBox;
	TextureRegion startButtonHover;
	TextureRegion optionsButtonHover;
	TextureRegion exitButtonHover;
	TextureRegion weaponMelee;
	TextureRegion currentFrame;
	Sprite weaponRanged1;
	Sprite weaponRanged2;
	Sprite weaponRanged3;
	Sprite arrow;
	Sprite activeAnimation;
	TextureRegion weaponMagic;
	int weaponSizeScaling;
	boolean isMeleePlayer;
	boolean isRangedPlayer;
	boolean isMagicPlayer;
	float stateTime;
	Animation<TextureRegion> playerIdleRight;
	Animation<TextureRegion> playerIdleLeft;
	Monster monster1;

	@Override
	public void create () {
		batch = new SpriteBatch();
		stateTime = 0f;
		tiles = new Texture("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		knightSprite = new Texture("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png");
		weapons = new Texture("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");



		weaponMelee = new TextureRegion(weapons,0, 0,16,46);

		weaponRanged1 = new Sprite(weapons,52,48,9,31);
		weaponRanged1.setScale(1.5f);
		weaponRanged1.setOrigin(0,weaponRanged1.getHeight()/2);
		weaponRanged2 = new Sprite(weapons,67,50,12,27);
		weaponRanged2.setScale(1.5f);
		weaponRanged2.setOrigin(weaponRanged2.getWidth()/2,weaponRanged2.getHeight()/2);
		weaponRanged3 = new Sprite(weapons,80,51,15,25);
		weaponRanged3.setScale(1.5f);
		weaponRanged3.setOrigin(weaponRanged3.getWidth()/2,weaponRanged3.getHeight()/2);

		arrow = new Sprite(weapons, 32,4,15,6);

		fps = 0;
		weaponSizeScaling = 2;
		attackCooldown = 0;
		player = new Player();
		player.setPosX(400);
		player.setPosY(80);
		player.setHitBox(new Rectangle(32, 32));
		leftBorder = new Rectangle(48, 40, 5, 500);
		rightBorder = new Rectangle(710, 40, 5, 500);
		bottomBorder = new Rectangle(48, 55, 705, 8);
		upperborder = new Rectangle(48, 525, 705, 8);

		isRangedPlayer= true; //testing ranged

		mainMenu = new Texture("mainMenu/menuUI.png");
		menuWindow = new TextureRegion(mainMenu, 479, 0, 470, 300);
		startButtonIdle = new TextureRegion(mainMenu, 0, 0, 478, 141);
		optionsButtonIdle = new TextureRegion(mainMenu, 0, 429, 478, 141);
		exitButtonIdle = new TextureRegion(mainMenu, 479, 429, 478, 141);
		startButtonBox = new Rectangle(240, 150, 350, 90);
		optionsButtonBox = new Rectangle(240, 250, 350, 90);
		exitButtonBox = new Rectangle(240, 350, 350, 90);
		startButtonHover = new TextureRegion(mainMenu, 0, 283, 478, 146);
		optionsButtonHover = new TextureRegion(mainMenu, 0, 711, 478, 146);
		exitButtonHover = new TextureRegion(mainMenu, 479, 711, 478, 146);

		playerIdleRight = Drawer.animate(knightSprite, 4, 1);
		playerIdleLeft = Drawer.animateFlip(knightSprite, 4, 1);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		mainGame(batch);
		//main menu
//		batch.draw(menuWindow, 50, 50, 700, 500);
//		if (Gdx.input.getX() >= startButtonBox.getX() &&
//				Gdx.input.getX() <= startButtonBox.getX() + startButtonBox.getWidth() &&
//				Gdx.input.getY() >= startButtonBox.getY() &&
//				Gdx.input.getY() <= startButtonBox.getY() + startButtonBox.getHeight()
//		) {
//			batch.draw(startButtonHover, 240, 350, 350, 90);
//		}
//		else {
//			batch.draw(startButtonIdle, 240, 350, 350, 90);
//		}
//
//		if (Gdx.input.getX() >= optionsButtonBox.getX() &&
//				Gdx.input.getX() <= optionsButtonBox.getX() + optionsButtonBox.getWidth() &&
//				Gdx.input.getY() >= optionsButtonBox.getY() &&
//				Gdx.input.getY() <= optionsButtonBox.getY() + optionsButtonBox.getHeight()
//		) {
//			batch.draw(optionsButtonHover, 240, 250, 350, 90);
//		}
//		else {
//			batch.draw(optionsButtonIdle, 240, 250, 350, 90);
//		}
//
//		if (Gdx.input.getX() >= exitButtonBox.getX() &&
//				Gdx.input.getX() <= exitButtonBox.getX() + exitButtonBox.getWidth() &&
//				Gdx.input.getY() >= exitButtonBox.getY() &&
//				Gdx.input.getY() <= exitButtonBox.getY() + exitButtonBox.getHeight()
//		) {
//			batch.draw(exitButtonHover, 240, 150, 350, 90);
//		}
//		else {
//			batch.draw(exitButtonIdle, 240, 150, 350, 90);
//		}

		batch.end();
	}
	public void mainGame(SpriteBatch batch) {
		batch.begin();

		Drawer.drawDungeon(batch, tiles);

		stateTime += Gdx.graphics.getDeltaTime();


		if (player.isLookingLeft()) {
			currentFrame = playerIdleLeft.getKeyFrame(stateTime, true);
		} else {
			currentFrame = playerIdleRight.getKeyFrame(stateTime, true);
		}

		player.setSprite(currentFrame);

		batch.draw(currentFrame, player.getPosX(), player.getPosY(), 40, 50);

		fps ++;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (player.getPosY() <= upperborder.getY() - 20) {
				player.moveUp();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (player.getPosY() >= bottomBorder.getY()) {
				player.moveDown();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (!player.isLookingLeft()) {
				player.setLookingLeft(true);
			}
			if (player.getPosX() >= leftBorder.getX() + 10) {
				player.moveLeft();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (player.isLookingLeft()) {
				player.setLookingLeft(false);
			}
			if (player.getPosX() <= rightBorder.getX()) {
				player.moveRight();
			}
		}
		player.updateHitbox();



		if (isMeleePlayer) {
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				if (!isMeleeAttacking) {
					isMeleeAttacking = true;
					frameCount = 0;
					fixX = Gdx.input.getX();
					fixY = Gdx.input.getY();
				}
			}
			if (isMeleeAttacking) {
				float angle = getAngleToMouse(fixX, fixY, player.getPosX() + (player.getSpriteWidth() / 2.0f), player.getPosY() + (player.getSpriteHeight() / 2.0f));
				batch.draw(weaponMelee, player.getPosX() + (player.getSpriteWidth() / 2.0f), player.getPosY() + (player.getSpriteHeight() / 2.0f), 8, 0, 16, 46, weaponSizeScaling, weaponSizeScaling, (240 - angle) + (frameCount * 8));
				frameCount++;
			}
			if (frameCount == 10) {
				isMeleeAttacking = false;
			}
		}

		if (isRangedPlayer){
			if (attackCooldown == 0) {
				if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
					attackCooldown = 60;
					frameCount = 0;
					isRangedAttacking=true;
				}
			}
			if (isRangedAttacking){
				float angle = getAngleToMouse(Gdx.input.getX(),Gdx.input.getY(), player.getPosX() + (player.getSpriteWidth() / 2.0f), player.getPosY() + (player.getSpriteHeight() / 2.0f));
				frameCount++;
				if (frameCount<10){
					activeAnimation = weaponRanged1;
				} else if (frameCount < 20){
					activeAnimation = weaponRanged2;
				} else {
					activeAnimation = weaponRanged3;
				}
				activeAnimation.setX(player.getPosX() + (player.getSpriteWidth() /2.0f));
				activeAnimation.setY(player.getPosY() + (player.getSpriteHeight() /3.0f));
				activeAnimation.setRotation(0-angle);
				activeAnimation.draw(batch);
			}
			if (frameCount == 27){
				float angleProjectile = getAngleToMouse(Gdx.input.getX(),Gdx.input.getY(), player.getPosX() + (player.getSpriteWidth() / 2.0f), player.getPosY() + (player.getSpriteHeight() / 2.0f));
				projectiles.add(new Projectile(player.getPosX()+player.getSpriteWidth()/2.0f,player.getPosY()+player.getSpriteHeight()/3.0f, 0 -angleProjectile,20,arrow));
			}
			if (frameCount == 30){
				isRangedAttacking = false;
			}
			attackCooldown--;
			attackCooldown = Math.max(attackCooldown,0);

		}

		if (isMagicPlayer){
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
				//langsung ledakan di cursor SAAT di click BUKAN di lepas
			}
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
	public float getAngleToMouse(float mouseX, float mouseY, float charX, float charY) {
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

}
