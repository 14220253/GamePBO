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

	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		wallBump = new TextureRegion(tiles, 8, 0, 55, 50);
		wallBumpWithShadow = new TextureRegion(tiles, 0, 0, 63, 50);
		walls = new TextureRegion(tiles, 8, 0, 32, 50);
		closedDoor = new TextureRegion(tiles, 0, 112, 32, 50);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
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
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tiles.dispose();
	}
}
