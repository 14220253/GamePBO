package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;

public class PlayerUI {
    Player player;
    GameMain app;
    Texture texture;
    TextureRegion emptyBar;
    TextureRegion hpBar;
    TextureRegion manaBar;
    Texture coins;
    TextureRegion coinIcon;
    BitmapFont font;
    BitmapFontCache text;

    public PlayerUI(Player player) {
        this.player = player;
        app = (GameMain) Gdx.app.getApplicationListener();
        texture = app.getManager().get("healthbar/SleekBars.png");
        emptyBar = new TextureRegion(texture, 0, 0, 128, 32);
        hpBar = new TextureRegion(texture, 0, 33, 128, 32);
        manaBar = new TextureRegion(texture, 0, 65, 128, 32);
        coins = app.getManager().get("coins/MonedaD.png");
        coinIcon = new TextureRegion(coins, 0, 0, 16, 16);
        font = new BitmapFont();
        font.getData().setScale(3);
        text = new BitmapFontCache(font);
    }
    public void draw(SpriteBatch batch) {
        double hpPercent = player.getHpPercent();
        batch.draw(emptyBar, 50, 650, 384, 32);
        hpBar.setRegion(0, 33, 128 * (int) hpPercent, 32);
        batch.draw(hpBar, 50, 650, (float) (384 * hpPercent), 32);
        batch.draw(emptyBar, 50, 600, 256, 32);
        manaBar.setRegion(0, 65, (int) (128 * player.getManaPercent()), 32);
        batch.draw(manaBar, 50, 600, (float) (256 * player.getManaPercent()), 32);
        batch.draw(coinIcon, 700, 616, 64, 64);
        text.setText(String.valueOf(player.getInventory().getCoins()), 660, 660);
        text.draw(batch);
    }
}
