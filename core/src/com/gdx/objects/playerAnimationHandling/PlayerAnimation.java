package com.gdx.objects.playerAnimationHandling;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface PlayerAnimation {
    TextureRegion getCurrentFrame(float stateTime, boolean looping, boolean isAttacking,boolean isLookingLeft, boolean isIdle);

    float getScalingX();
    float getScalingY();
}
