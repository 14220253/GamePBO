package com.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdx.UI.PlayerUI;
import com.gdx.objects.*;

import java.util.ArrayList;

public class
MainGameScreen {
    private ArrayList<Floor> floors;
    private int floorCount;
    private float stateTime;
    private Player player;
    private boolean isOnDebug;
    private PlayerUI UI;
    private GameMain app;
    private ShapeRenderer shapeRenderer;
    private ArrayList<Karakter> entities;

    public MainGameScreen(ArrayList<Floor> floors, int floorCount, float stateTime, Player player, boolean isOnDebug, PlayerUI UI, GameMain app) {
        this.floors = floors;
        this.floorCount = floorCount;
        this.stateTime = stateTime;
        this.player = player;
        this.isOnDebug = isOnDebug;
        this.UI = UI;
        this.app = app;
        shapeRenderer = new ShapeRenderer();
        entities = new ArrayList<>();
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
        try {
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
        } catch (Exception ignored){
            //TODO :)
        }
        this.player.draw(batch);
        if (!floors.get(floorCount).getCurrentRoom().isShowingCard()) {
            this.player.update(Gdx.graphics.getDeltaTime(), this.stateTime);
            app.updatePlayerAttacks();
            app.updateAllProjectile();
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
}
