package com.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.UI.PlayerUI;
import com.gdx.objects.Floor;
import com.gdx.objects.Player;

import java.util.ArrayList;

public class MainGameScreen {
    private ArrayList<Floor> floors;
    private int floorCount;
    private float stateTime;
    private Player player;
    private boolean isOnDebug;
    private PlayerUI UI;
    private GameMain app;

    public MainGameScreen(ArrayList<Floor> floors, int floorCount, float stateTime, Player player, boolean isOnDebug, PlayerUI UI, GameMain app) {
        this.floors = floors;
        this.floorCount = floorCount;
        this.stateTime = stateTime;
        this.player = player;
        this.isOnDebug = isOnDebug;
        this.UI = UI;
        this.app = app;
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
                app.enableDebug();
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


        if (!floors.get(floorCount).getCurrentRoom().isShowingCard()) {
            this.player.update(Gdx.graphics.getDeltaTime(), this.stateTime);
            this.player.draw(batch);
            app.updatePlayerAttacks();
            app.updateAllProjectile();
        }
    }
}
