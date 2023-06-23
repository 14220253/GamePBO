package com.gdx.objects.Skills;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.gdx.objects.Player;

public interface Skill {
    void intitialize(Player player);
    void update(Player player);
    void draw(Player player, Batch batch);
    void end(Player player);
    //untuk skill kurang tau interface ato class
    //kalo contoh ppt pbo pakenya komposisi
    //tapi kalo perlu animasi mungkin perlu interface?
}
