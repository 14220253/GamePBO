package com.gdx.objects;

import java.util.ArrayList;
import java.util.Random;

public class Floor {
    //floor level

    //ruangan
    private ArrayList<Ruangan> rooms;

    private int level;
    private String type;

    public Floor(int level, String type) {
        this.level = level;
        this.type = type;
    }

    public void initialize() {
        Ruangan ruangan1 = new Ruangan("dungeon");
        ruangan1.initialize(new Random().nextInt(1, 7), this.level);
        rooms.add(ruangan1);

        Ruangan ruangan2= new Ruangan("dungeon");
        ruangan2.initialize(new Random().nextInt(1, 7), this.level);
        rooms.add(ruangan2);

        Ruangan ruangan3= new Ruangan("dungeon");
        ruangan3.initialize(new Random().nextInt(1, 7), this.level);
        rooms.add(ruangan3);

        Ruangan ruangan4= new Ruangan("dungeon");
        ruangan4.initialize(new Random().nextInt(1, 7), this.level);
        rooms.add(ruangan4);

        Ruangan ruangan5= new Ruangan("dungeon");
        ruangan5.initialize(new Random().nextInt(1, 7), this.level);
        rooms.add(ruangan5);

        Ruangan ruanganShop = new Ruangan("shop");
        ruanganShop.initialize(0, this.level);
        rooms.add(ruanganShop);

        Ruangan ruanganBoss = new Ruangan("dungeon");
        ruanganBoss.initialize(0, this.level);
        rooms.add(ruanganBoss);
    }
}
