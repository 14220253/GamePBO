package com.gdx.objects.playerAnimationHandling;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.game.GameMain;
import com.gdx.game.Static;

import java.util.ArrayList;

public class MeleePlayerAnimation implements PlayerAnimation{
    private GameMain app;
    private Texture textureIdle;
    private Texture textureRun;
    private Animation<TextureRegion> playerIdleRight;
    private Animation<TextureRegion> playerIdleLeft;
    private Animation<TextureRegion> playerRunRight;
    private Animation<TextureRegion> playerRunLeft;
    private TextureRegion currentFrame;
    private float scalingX, scalingY;

    public MeleePlayerAnimation() {
        scalingX = 1.5f;
        scalingY = 1.7f;

        app = (GameMain) Gdx.app.getApplicationListener();
        TextureRegion[] temp1 = new TextureRegion[4];
        TextureRegion[] temp2 = new TextureRegion[4];
        TextureRegion[] temp3 = new TextureRegion[6];
        TextureRegion[] temp4 = new TextureRegion[6];

        textureIdle = app.getManager().get("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png");
        temp1[0] = (new TextureRegion(textureIdle,4,1,23,30));
        temp1[1] = (new TextureRegion(textureIdle,36,1,23,30));
        temp1[2] = (new TextureRegion(textureIdle,68,1,23,30));
        temp1[3] = (new TextureRegion(textureIdle,100,1,23,30));
        playerIdleRight = new Animation<>(0.2f, temp1);

        temp2[0] = (new TextureRegion(textureIdle,4,1,23,30));
        temp2[1] = (new TextureRegion(textureIdle,36,1,23,30));
        temp2[2] = (new TextureRegion(textureIdle,68,1,23,30));
        temp2[3] = (new TextureRegion(textureIdle,100,1,23,30));
        temp2 = Static.flipImageX(temp2);
        playerIdleLeft = new Animation<>(0.2f, temp2);

        textureRun = app.getManager().get("Pixel Crawler - FREE - 1.8/Heroes/Knight/Run/Run-Sheet.png");
        temp3[0] = (new TextureRegion(textureRun,21,33,23,30));
        temp3[1] = (new TextureRegion(textureRun,85,33,23,30));
        temp3[2] = (new TextureRegion(textureRun,149,33,23,30));
        temp3[3] = (new TextureRegion(textureRun,213,33,23,30));
        temp3[4] = (new TextureRegion(textureRun,277,33,23,30));
        temp3[5] = (new TextureRegion(textureRun,341,33,23,30));
        playerRunRight = new Animation<>(0.2f, temp3);

        temp4[0] = (new TextureRegion(textureRun,21,33,23,30));
        temp4[1] = (new TextureRegion(textureRun,85,33,23,30));
        temp4[2] = (new TextureRegion(textureRun,149,33,23,30));
        temp4[3] = (new TextureRegion(textureRun,213,33,23,30));
        temp4[4] = (new TextureRegion(textureRun,277,33,23,30));
        temp4[5] = (new TextureRegion(textureRun,341,33,23,30));
        temp4 = Static.flipImageX(temp4);
        playerRunLeft= new Animation<>(0.2f, temp4);
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
    public float getScalingX() {
        return scalingX;
    }

    @Override
    public float getScalingY() {
        return scalingY;
    }

}
