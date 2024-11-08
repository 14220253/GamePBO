package com.gdx.objects.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.game.GameMain;
import com.gdx.objects.Player;

import java.util.ArrayList;
import java.util.Random;

public class Floor {
    //floor level

    //ruangan
    private ArrayList<Ruangan> rooms;

    private final int LEVEL;
    private Ruangan currentRoom;
    private int room;
    private boolean done;
    private Player player;
    private GameMain app;
    private Music music;

    public Floor(int level, Player player) {
        this.player = player;
        this.LEVEL = level;
        initialize();
    }
    public Floor(int start, int level,  Player player) {
        this.player = player;
        LEVEL = level;
        initialize();
        room = start;
        currentRoom = rooms.get(room);
    }

    public void initialize() {
        app = (GameMain) Gdx.app.getApplicationListener();

        music = app.getManager().get("Music.mp3");
        music.setVolume(app.masterMusic);

        rooms = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Ruangan ruangan;
            if (i < 5) {
                ruangan = new Ruangan("dungeon", player);
                ruangan.initialize(new Random().nextInt(1, 7), this.LEVEL, (i + 1));
                rooms.add(ruangan);
            }
            if (i == 5) {
                Ruangan ruanganShop = new Ruangan("shop", player);
                ruanganShop.initialize(100, this.LEVEL, (i + 1));
                rooms.add(ruanganShop);
            }
            if (i == 6) {
                Ruangan ruanganBoss = new Ruangan("boss", player);
                ruanganBoss.initialize(0, this.LEVEL, (i + 1));
                rooms.add(ruanganBoss);
            }
        }
        room = 0;
        currentRoom = rooms.get(room);
        done = false;
    }

    public void draw(SpriteBatch batch, float stateTime, Player player) {
        if (!currentRoom.isDone()) {
            music.play();
            currentRoom.draw(batch, stateTime);
        }
        else {
            music.pause();
            if (room == 6) {
                done = true;
            }
            else {
                room++;
                currentRoom = rooms.get(room);
                if (room == 5) {
                    player.setPosX(300);
                    player.setPosY(220);
                    player.canMoveFree();
                } else {
                    player.setPosX(400);
                    player.setPosY(100);
                    player.canMoveFree();
                }
            }
        }
    }

    public boolean isDone() {
        return done;
    }

    public Ruangan getCurrentRoom() {
        return currentRoom;
    }
}
