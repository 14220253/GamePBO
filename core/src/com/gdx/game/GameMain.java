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


	@Override
	public void create () {
		batch = new SpriteBatch();
		tiles = new Texture("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		heroSprites = new Texture("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png");
		idle1 = new TextureRegion(heroSprites, 0, 0, 30, 30);
		
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		Drawer.drawDungeon(batch, tiles);

		batch.draw(idle1, 400, 80, 40, 50);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		tiles.dispose();
	}
}
