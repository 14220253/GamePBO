package com.gdx.objects.playerAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.Animator;
import com.gdx.game.GameMain;
import com.gdx.game.Static;

import java.util.ArrayList;

public class MagicPlayerAnimation implements PlayerAnimation{
    private final GameMain app;
    private final Texture textureIdle;
    private final Texture textureRun;
    private final Texture textureDying;
    private final Animation<TextureRegion> playerIdleRight;
    private final Animation<TextureRegion> playerIdleLeft;
    private final Animation<TextureRegion> playerRunRight;
    private final Animation<TextureRegion> playerRunLeft;
    private final Animation<TextureRegion> playerDyingRight;
    private final Animation<TextureRegion> playerDyingLeft;
    private TextureRegion currentFrame;
    private final float scalingX;
    private final float scalingY;
    private final float maxDyingStateTime;

    public MagicPlayerAnimation() {
        scalingX = 1.5f;
        scalingY = 1.7f;

        app = (GameMain) Gdx.app.getApplicationListener();
        TextureRegion[] temp1 = new TextureRegion[4];
        TextureRegion[] temp2 = new TextureRegion[4];
        TextureRegion[] temp3 = new TextureRegion[6];
        TextureRegion[] temp4 = new TextureRegion[6];
        TextureRegion[] temp5 = new TextureRegion[6];
        TextureRegion[] temp6 = new TextureRegion[6];

        textureIdle = app.getManager().get("Pixel Crawler - FREE - 1.8/Heroes/Wizzard/Idle/Idle-Sheet.png");
        temp1[0] = (new TextureRegion(textureIdle,4,0,24,31));
        temp1[1] = (new TextureRegion(textureIdle,36,0,24,31));
        temp1[2] = (new TextureRegion(textureIdle,68,0,24,31));
        temp1[3] = (new TextureRegion(textureIdle,100,0,24,31));
        playerIdleRight = new Animation<>(0.2f, temp1);

        temp2[0] = (new TextureRegion(textureIdle,4,0,24,31));
        temp2[1] = (new TextureRegion(textureIdle,36,0,24,31));
        temp2[2] = (new TextureRegion(textureIdle,68,0,24,31));
        temp2[3] = (new TextureRegion(textureIdle,100,0,24,31));
        temp2 = Animator.flipImageX(temp2);
        playerIdleLeft = new Animation<>(0.2f, temp2);

        textureRun = app.getManager().get("Pixel Crawler - FREE - 1.8/Heroes/Wizzard/Run/Run-Sheet.png");
        temp3[0] = (new TextureRegion(textureRun,19,29,25,34));
        temp3[1] = (new TextureRegion(textureRun,83,29,25,34));
        temp3[2] = (new TextureRegion(textureRun,147,29,25,34));
        temp3[3] = (new TextureRegion(textureRun,211,29,25,34));
        temp3[4] = (new TextureRegion(textureRun,275,29,25,34));
        temp3[5] = (new TextureRegion(textureRun,339,29,25,34));
        playerRunRight = new Animation<>(0.2f, temp3);

        temp4[0] = (new TextureRegion(textureRun,19,29,25,34));
        temp4[1] = (new TextureRegion(textureRun,83,29,25,34));
        temp4[2] = (new TextureRegion(textureRun,147,29,25,34));
        temp4[3] = (new TextureRegion(textureRun,211,29,25,34));
        temp4[4] = (new TextureRegion(textureRun,275,29,25,34));
        temp4[5] = (new TextureRegion(textureRun,339,29,25,34));
        temp4 = Animator.flipImageX(temp4);
        playerRunLeft= new Animation<>(0.2f, temp4);

        textureDying = app.getManager().get("Pixel Crawler - FREE - 1.8/Heroes/Wizzard/Death/Death-Sheet.png");
        temp5[0] = (new TextureRegion(textureDying,4,0,24,31));
        temp5[1] = (new TextureRegion(textureDying,72,1,24,31));
        temp5[2] = (new TextureRegion(textureDying,137,1,36,31));
        temp5[3] = (new TextureRegion(textureDying,203,1,39,31));
        temp5[4] = (new TextureRegion(textureDying,265,1,41,31));
        temp5[5] = (new TextureRegion(textureDying,329,1,41,31));
        playerDyingRight = new Animation<>(0.6f, temp5);

        temp6[0] = (new TextureRegion(textureDying,4,0,24,31));
        temp6[1] = (new TextureRegion(textureDying,72,1,24,31));
        temp6[2] = (new TextureRegion(textureDying,137,1,36,31));
        temp6[3] = (new TextureRegion(textureDying,203,1,39,31));
        temp6[4] = (new TextureRegion(textureDying,265,1,41,31));
        temp6[5] = (new TextureRegion(textureDying,329,1,41,31));
        Animator.flipImageX(temp6);
        playerDyingLeft = new Animation<>(0.6f, temp6);

        maxDyingStateTime = 0.6f * (6);
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

    @Override
    public TextureRegion getDyingFrame(float stateTime, boolean isLookingLeft) {
        if (isLookingLeft){
            return playerDyingLeft.getKeyFrame(stateTime);
        } else {
            return playerDyingRight.getKeyFrame(stateTime);
        }
    }

    @Override
    public float getMaxDyingStateTime() {
        return maxDyingStateTime;
    }

    @Override
    public float getScalingX() {
        return scalingX;
    }

    @Override
    public float getScalingY() {
        return scalingY;
    }

}