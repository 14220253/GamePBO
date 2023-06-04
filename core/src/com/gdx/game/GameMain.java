package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.objects.Floor;
import com.gdx.objects.Player;
import com.gdx.objects.Ruangan;

import static com.gdx.game.Drawer.drawDungeon;

public class GameMain extends ApplicationAdapter {
	SpriteBatch batch;
	Texture tiles;
	Player player;
	Texture heroSprites;
	TextureRegion idle1;
	TextureRegion idle2;
	TextureRegion idle3;
	TextureRegion idle4;
	int fps;


	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		heroSprites = new Texture("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png");
		idle1 = new TextureRegion(heroSprites, 0, 0, 30, 32);
		idle2 = new TextureRegion(heroSprites, 32, 0, 30, 32);
		idle3 = new TextureRegion(heroSprites, 64, 0, 30, 32);
		idle4 = new TextureRegion(heroSprites, 96, 0, 30, 32);
		fps = 0;
		player = new Player();
		player.setSprite(idle1);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		Drawer.drawDungeon(batch, tiles);

		fps ++;

		batch.draw(player.getSprite(), 400, 80, 40, 50);

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
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tiles.dispose();
	}
}
