package com.gdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.UI.PlayerUI;
import com.gdx.game.GameMain;
import com.gdx.objects.*;
import com.gdx.objects.Monsters.Monster;

import java.util.ArrayList;

public class
MainGameScreen implements Screen {
    private ArrayList<Floor> floors;
    private int floorCount;
    private float stateTime;
    private Player player;
    private boolean isOnDebug;
    private PlayerUI UI;
    private GameMain app;
    private ShapeRenderer shapeRenderer;
    private ArrayList<Karakter> entities;
    private Texture gameOverScreen;
    SpriteBatch batch;
    public MainGameScreen(SpriteBatch batch) {
        app = (GameMain) Gdx.app.getApplicationListener();
        floors = new ArrayList<>();
        player = app.getPlayer();
        Floor floor = new Floor(1, player);
        floors.add(floor);
        floorCount = 0;
        stateTime = 0.0F;
        isOnDebug = false;
        this.batch = batch;
        UI = new PlayerUI(player);
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();
        gameOverScreen = app.getManager().get("gameOverScreen.png");

    }

    public void mainGame(SpriteBatch batch) {
        if (!floors.get(floorCount).isDone()) {
            floors.get(floorCount).draw(batch, stateTime, player);
        } else {
            floors.add(new Floor(player.getLevel(), player));
            floorCount++;
        }
        this.stateTime += Gdx.graphics.getDeltaTime();
        UI.draw(batch);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F9)) {
            isOnDebug = !isOnDebug;
        }
        if(isOnDebug) {
            try {
                enableDebug();
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            catch (IllegalStateException ignored){}
        }
        if (!floors.get(floorCount).getCurrentRoom().isDone()) {
            if (floors.get(floorCount).getCurrentRoom().getUpperborder() != null) {
                if ((double) this.player.getPosY() >= floors.get(floorCount).getCurrentRoom().getUpperborder().getY() - 20.0) {
                    this.player.setCanMoveUp(false);
                }

                if ((double) this.player.getPosY() <= floors.get(floorCount).getCurrentRoom().getBottomBorder().getY()) {
                    this.player.setCanMoveDown(false);
                }

                if ((double) this.player.getPosX() <= floors.get(floorCount).getCurrentRoom().getLeftBorder().getX() + 10.0) {
                    this.player.setCanMoveLeft(false);
                }

                if ((double) this.player.getPosX() >= floors.get(floorCount).getCurrentRoom().getRightBorder().getX()) {
                    this.player.setCanMoveRight(false);
                }
            }
        }
        if (!floors.get(floorCount).getCurrentRoom().isShowingCard()) {
            this.player.draw(batch);
            this.player.update(Gdx.graphics.getDeltaTime(), this.stateTime);
            app.updatePlayerAttacks();
            //app.updateAllProjectile();
        }
        //draw game over screen
        if (player.isDead()){
          batch.draw(gameOverScreen,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        }
    }
    public void enableDebug() throws NullPointerException, IllegalStateException{
        getEnities();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.rect(170, 70, 220, 80);
        for (Karakter entity : entities) {
            shapeRenderer.rect(
                    (float) entity.getHitBox().getX(),
                    (float) entity.getHitBox().getY(),
                    (float) entity.getHitBox().getWidth(),
                    (float) entity.getHitBox().getHeight()
            );
            if (entity instanceof Monster) {
                ((Monster) entity).setRunning(false);
            }
        }
        for (int i = 0; i < player.getWeapon().getWeaponAnimation().getHitboxes().length; i++) {
            shapeRenderer.rect(player.getWeapon().getWeaponAnimation().getHitboxes()[i].x,
                    player.getWeapon().getWeaponAnimation().getHitboxes()[i].y,
                    player.getWeapon().getWeaponAnimation().getHitboxes()[i].width,
                    player.getWeapon().getWeaponAnimation().getHitboxes()[i].height);
        }
        for (Breakable b: floors.get(floorCount).getCurrentRoom().getBreakables()) {
            shapeRenderer.rect(b.getHitbox().x, b.getHitbox().y, b.getHitbox().width, b.getHitbox().height);
        }
        for (Drops d: floors.get(floorCount).getCurrentRoom().getDrops()) {
            shapeRenderer.rect(d.getHitbox().x, d.getHitbox().y, d.getHitbox().width, d.getHitbox().height);
        }
        shapeRenderer.end();
    }
    public void getEnities() {
        if (!entities.contains(player)) {
            entities.add(player);
        }
        if (entities.size() <= 1) {
            entities.addAll(floors.get(floorCount).getCurrentRoom().getMonsters());
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        mainGame(batch);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
