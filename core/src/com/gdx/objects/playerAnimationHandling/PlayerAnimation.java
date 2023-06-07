package com.gdx.objects.playerAnimationHandling;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface PlayerAnimation {
    TextureRegion getCurrentFrame(float stateTime, boolean looping, boolean isAttacking,boolean isLookingLeft, boolean isIdle);
    TextureRegion getDyingFrame(float stateTime, boolean isLookingLeft);

    float getMaxDyingStateTime();

    float getScalingX();
    float getScalingY();
}
