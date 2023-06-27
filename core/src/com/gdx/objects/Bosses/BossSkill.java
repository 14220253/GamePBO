package com.gdx.objects.Bosses;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.objects.Map.Ruangan;
import com.gdx.objects.Player;

import java.awt.*;

public interface BossSkill {
    void drawSkill(SpriteBatch batch, float stateTime, Player player, Ruangan ruangan);
    Rectangle getHurtBox();
}
