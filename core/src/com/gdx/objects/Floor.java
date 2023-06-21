package com.gdx.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

public class Floor {
    //floor level

    //ruangan
    private ArrayList<Ruangan> rooms;

    private final int level;
    private Ruangan currentRoom;
    private int room;
    private boolean done;

    public Floor(int level) {
        this.level = level;
    }

    public void initialize() {
        rooms = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Ruangan ruangan;
            if (i < 5) {
                ruangan = new Ruangan("dungeon");
                ruangan.initialize(new Random().nextInt(1, 7), this.level, (i + 1));
                rooms.add(ruangan);
            }
            if (i == 5) {
                Ruangan ruanganShop = new Ruangan("shop");
                ruanganShop.initialize(100, this.level, (i + 1));
                rooms.add(ruanganShop);
            }
            if (i == 6) {
                Ruangan ruanganBoss = new Ruangan("dungeon");
                ruanganBoss.initialize(0, this.level, (i + 1));
                rooms.add(ruanganBoss);
            }
        }
        room = 0;
        currentRoom = rooms.get(room);
        done = false;
    }

    public void draw(SpriteBatch batch, float stateTime, Player player) {
        if (!currentRoom.isDone()) {
            currentRoom.draw(batch, stateTime, player);
        }
        else {
            if (room == 6) {
                done = true;
            }
            else {
                room++;
                currentRoom = rooms.get(room);
                if (room == 5) {
                    player.setPosX(400);
                    player.setPosY(300);
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
