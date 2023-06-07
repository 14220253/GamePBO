package com.gdx.objects.playerAnimationHandling;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;
import com.gdx.game.Static;

public class MeleePlayerAnimation implements PlayerAnimation{
    private GameMain app;
    private Texture texture;
    private Animation<TextureRegion> playerIdleRight;
    private Animation<TextureRegion> playerIdleLeft;
    private Animation<TextureRegion> playerRunRight;
    private Animation<TextureRegion> playerRunLeft;
    private TextureRegion currentFrame;

    public MeleePlayerAnimation() {
        app = new GameMain();
        texture = new Texture("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png");
        playerIdleRight = Static.animate(texture, 4, 1, false,false);
        playerIdleLeft = Static.animate(texture, 4, 1, true, false);
        texture = new Texture("Pixel Crawler - FREE - 1.8/Heroes/Knight/Run/Run-Sheet.png");
        playerRunLeft = Static.animate(texture, 6, 1,true, false);
        playerRunRight = Static.animate(texture, 6, 1, false, false);
    }

    @Override
    public TextureRegion getCurrentFrame(float stateTime, boolean looping, boolean isAttacking, boolean isLookingLeft, boolean isIdle) {
        if (isIdle && isLookingLeft){
            currentFrame = playerIdleLeft.getKeyFrame(stateTime,looping);
        }
        if (isIdle && !isLookingLeft){
            currentFrame = playerIdleRight.getKeyFrame(stateTime,looping);
        }
        if (!isIdle && isLookingLeft){
            currentFrame = playerRunLeft.getKeyFrame(stateTime,looping);
        }
        if (!isIdle && !isLookingLeft){
            currentFrame = playerRunRight.getKeyFrame(stateTime,looping);
        }
        return currentFrame;
    }
}
