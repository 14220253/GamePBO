package com.gdx.objects.weaponAnimationHandling;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gdx.objects.Player;
import com.gdx.objects.Projectile;

public interface CreateProjectile {

    Projectile createProjectile(Player player, Sprite projectile);
    float getframeToCreateProjectile();
    boolean canCreateProjectile();
}
