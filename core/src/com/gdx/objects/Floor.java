package com.gdx.objects;

import java.util.ArrayList;
import java.util.Random;

public class Floor {
    //floor level

    //ruangan
    private ArrayList<Ruangan> rooms;

    private final int level;
    private final String type;

    public Floor(int level, String type) {
        this.level = level;
        this.type = type;
    }

    public void initialize() {
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
    }
}
