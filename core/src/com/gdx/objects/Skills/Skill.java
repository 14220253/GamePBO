package com.gdx.objects.Skills;

import com.gdx.objects.Player;

public interface Skill {
    void intitialize(Player player);
    void update(Player player);
    void draw(Player player);
    void end(Player player);
    //untuk skill kurang tau interface ato class
    //kalo contoh ppt pbo pakenya komposisi
    //tapi kalo perlu animasi mungkin perlu interface?
}
