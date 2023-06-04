package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.objects.Player;

import java.awt.*;

public class GameMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tiles;
	Texture swords;
	Player player;
	Texture heroSprites;
	TextureRegion idle1;
	TextureRegion idle2;
	TextureRegion idle3;
	TextureRegion idle4;
	int fps;
	boolean isMeleeAttacking = false;
	int frameCount = 0;
	int fixX, fixY;
	int attackCooldown = 0;
	Rectangle leftBorder;
	Rectangle rightBorder;
	Rectangle bottomBorder;
	Rectangle upperborder;
	TextureRegion weapon;
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

	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		heroSprites = new Texture("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png");
		swords = new Texture("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");
		idle1 = new TextureRegion(heroSprites, 0, 0, 32, 32);
		idle2 = new TextureRegion(heroSprites, 32, 0, 32, 32);
		idle3 = new TextureRegion(heroSprites, 64, 0, 32, 32);
		idle4 = new TextureRegion(heroSprites, 96, 0, 32, 32);
		weapon = new TextureRegion(swords,0, 0,16,46);
		fps = 0;
		player = new Player();
		player.setSprite(idle1);
		player.setPosX(400);
		player.setPosY(80);
		player.setHitBox(new Rectangle(32, 32));
		leftBorder = new Rectangle(48, 40, 5, 500);
		rightBorder = new Rectangle(710, 40, 5, 500);
		bottomBorder = new Rectangle(48, 55, 705, 8);
		upperborder = new Rectangle(48, 525, 705, 8);
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
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		//main menu
		batch.draw(menuWindow, 50, 50, 700, 500);
		if (Gdx.input.getX() >= startButtonBox.getX() &&
				Gdx.input.getX() <= startButtonBox.getX() + startButtonBox.getWidth() &&
				Gdx.input.getY() >= startButtonBox.getY() &&
				Gdx.input.getY() <= startButtonBox.getY() + startButtonBox.getHeight()
		) {
			batch.draw(startButtonHover, 240, 350, 350, 90);
		}
		else {
			batch.draw(startButtonIdle, 240, 350, 350, 90);
		}

		if (Gdx.input.getX() >= optionsButtonBox.getX() &&
				Gdx.input.getX() <= optionsButtonBox.getX() + optionsButtonBox.getWidth() &&
				Gdx.input.getY() >= optionsButtonBox.getY() &&
				Gdx.input.getY() <= optionsButtonBox.getY() + optionsButtonBox.getHeight()
		) {
			batch.draw(optionsButtonHover, 240, 250, 350, 90);
		}
		else {
			batch.draw(optionsButtonIdle, 240, 250, 350, 90);
		}

		if (Gdx.input.getX() >= exitButtonBox.getX() &&
				Gdx.input.getX() <= exitButtonBox.getX() + exitButtonBox.getWidth() &&
				Gdx.input.getY() >= exitButtonBox.getY() &&
				Gdx.input.getY() <= exitButtonBox.getY() + exitButtonBox.getHeight()
		) {
			batch.draw(exitButtonHover, 240, 150, 350, 90);
		}
		else {
			batch.draw(exitButtonIdle, 240, 150, 350, 90);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tiles.dispose();
	}
	public void mainGame(SpriteBatch batch) {
		Drawer.drawDungeon(batch, tiles);

		fps ++;

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			if (player.getPosY() <= upperborder.getY() - player.getSprite().getRegionHeight()) {
				player.moveUp();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (player.getPosY() >= bottomBorder.getY()) {
				player.moveDown();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (!player.isLookingLeft()) {
				idle1.flip(true, false);
				idle2.flip(true, false);
				idle3.flip(true, false);
				idle4.flip(true, false);
				player.setLookingLeft(true);
			}
			if (player.getPosX() >= leftBorder.getX() + leftBorder.getWidth()) {
				player.moveLeft();
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (player.isLookingLeft()) {
				idle1.flip(true, false);
				idle2.flip(true, false);
				idle3.flip(true, false);
				idle4.flip(true, false);
				player.setLookingLeft(false);
			}
			if (player.getPosX() <= rightBorder.getX()) {
				player.moveRight();
			}
		}
		player.updateHitbox();
		batch.draw(player.getSprite(), player.getPosX(), player.getPosY(), 40, 50);

		if (fps == 1) {
			player.setSprite(idle1);
		}
		if (fps == 15) {
			player.setSprite(idle2);
		}
		if (fps == 30) {
			player.setSprite(idle3);
		}
		if (fps == 45) {
			player.setSprite(idle4);
		}
		if (fps == 60) {
			fps = 0;
		}

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
			if (!isMeleeAttacking) {
				isMeleeAttacking = true;
				frameCount = 0;
				fixX = Gdx.input.getX();
				fixY = Gdx.input.getY();
				attackCooldown = 1;
			}
		}
		if (isMeleeAttacking){
			float angle = getAngleToMouse(fixX,fixY, player.getPosX()+(player.getSpriteWidth()/2),player.getPosY()+(player.getSpriteHeight()/2));
			batch.draw(weapon,player.getPosX()+(player.getSpriteWidth()/2),player.getPosY()+(player.getSpriteHeight()/2),8,0,16,46,2,2,(240-angle)+(frameCount*8));
			frameCount++;
		}
		if (frameCount == 10){
			isMeleeAttacking =false;
		}
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
