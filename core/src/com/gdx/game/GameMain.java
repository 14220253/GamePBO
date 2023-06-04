package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.objects.Floor;
import com.gdx.objects.Ruangan;

public class GameMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tiles;
	TextureRegion wallBump;
	TextureRegion wallBumpWithShadow;
	TextureRegion walls;
	TextureRegion closedDoor;
	TextureRegion sideWall;
	TextureRegion bottomLeftWall;
	TextureRegion bottomRightWall;
	TextureRegion bottomWall;
	TextureRegion floor;
	TextureRegion topShadowFloor;
	TextureRegion bottomShadowFloor;
	TextureRegion leftShadowFloor;
	TextureRegion rightShadowFloor;
	TextureRegion topLeftShadowFloor;
	TextureRegion topRightShadowFloor;
	TextureRegion bottomLeftShadowFloor;
	TextureRegion bottomRightShadowFloor;

	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		wallBump = new TextureRegion(tiles, 8, 0, 55, 50);
		wallBumpWithShadow = new TextureRegion(tiles, 0, 0, 63, 50);
		walls = new TextureRegion(tiles, 8, 0, 32, 50);
		closedDoor = new TextureRegion(tiles, 0, 112, 32, 50);
		sideWall = new TextureRegion(tiles, 0, 50, 3, 24);
		bottomLeftWall = new TextureRegion(tiles, 0, 56, 24, 24);
		bottomRightWall = new TextureRegion(tiles, 25, 56, 24, 24);
		bottomWall = new TextureRegion(tiles, 3, 75, 40, 5);
		floor = new TextureRegion(tiles, 71, 16, 31, 25);
		topShadowFloor = new TextureRegion(tiles, 71, 0, 31, 25);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		//bottom wall
		batch.draw(bottomRightWall, 713, 40, 40, 40);
		batch.draw(bottomLeftWall, 48, 40, 40, 40);

		for (int i = 80; i <= 680; i+= 40) {
			batch.draw(bottomWall, i, 40, 64, 8);
		}

		//floor
		for (int i = 53; i < 690; i += 63) { //kiri ke kanan
			for (int j = 48; j <= 497 ; j += 50) { //atas ke bawah
				batch.draw(floor, i, j, 63, 50);
			}
			batch.draw(topShadowFloor, i, 475, 63, 50);
		}

		//upper walls + buff doors
		for (int i = 700; i >= 50 ; i -= 50) {
			if (i == 200 || i == 300 || i == 400 || i == 500) {
				if (i != 200) {
					batch.draw(wallBumpWithShadow, i - 25, 525, 100, 75);
					batch.draw(closedDoor, i - 13, 525, 50, 75);
				} else {
					batch.draw(wallBump, i - 25, 525, 100, 75);
				}
				if (i != 200) {
					i -= 50;
				}
			}
			else {
				batch.draw(walls, i, 525, 50, 75);
			}
		}

		//side wall
		for (int i = 540; i > 40; i -= 25) {
			batch.draw(sideWall, 745, i, 5, 40);
			batch.draw(sideWall, 48, i, 5, 40);
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tiles.dispose();
	}
}
