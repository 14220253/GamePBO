package com.gdx.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.GameMain;


public class ShopUI implements Screen, InputProcessor {
    GameMain parentGame;
    AssetManager assetManager;

    private Viewport viewport;
    private OrthographicCamera camera, stageCamera;
    SpriteBatch batch;

    Stage stage;
    Label titleLabel;
    TextButton buyButton, cancelButton, confirmButton;
    ImageButton diamond, ring, potion, crown, helmet, shoes, book;
    Window optionWindow;

    InputMultiplexer multiInput;
    public ShopUI(){
        parentGame = (GameMain) Gdx.app.getApplicationListener();
        assetManager = parentGame.getManager();
        camera = new OrthographicCamera(341,200);
        camera.setToOrtho(false, 341, 200);
        viewport = new FitViewport(341, 200, camera);
        batch = new SpriteBatch();
        stageCamera = new OrthographicCamera(341, 200);
        stageCamera.setToOrtho(false, 341, 200);
        stage = new Stage(new FitViewport(341, 200, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);
        Skin mySkin = assetManager.get("2dpixelskin.json", Skin.class);

        titleLabel = new Label("SHOP", mySkin);
        Label.LabelStyle style = new Label.LabelStyle(titleLabel.getStyle());
        style.font = assetManager.get("VT323-Regular.ttf", BitmapFont.class);
        titleLabel.setStyle(style);
        titleLabel.setWidth(800);
        titleLabel.setX(0);
        titleLabel.setY(320);
        titleLabel.setAlignment(Align.center);

        ring = new ImageButton(mySkin,"ring");
        ring.setWidth(100);
        ring.setHeight(100);
        potion = new ImageButton(mySkin,"potion");
        potion.setWidth(100);
        potion.setHeight(100);
        book = new ImageButton(mySkin,"book");
        book.setWidth(100);
        book.setHeight(100);
        helmet = new ImageButton(mySkin,"helmet");
        helmet.setWidth(100);
        helmet.setHeight(100);
        shoes = new ImageButton(mySkin,"shoes");
        shoes.setWidth(100);
        shoes.setHeight(100);
        crown = new ImageButton(mySkin,"crown");
        crown.setWidth(100);
        crown.setHeight(100);
        diamond = new ImageButton(mySkin, "diamond");
        diamond.setWidth(100);
        diamond.setHeight(100);



        stage.addActor(titleLabel);
        buyButton = new TextButton("BUY", mySkin);
        buyButton.setHeight(48);
        buyButton.setWidth(150);
        buyButton.setPosition(400 - buyButton.getWidth()/ 2, 140);
        buyButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    optionWindow.setVisible(true);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(buyButton);

        optionWindow = new Window("WARNING", mySkin, "default");
        optionWindow.setHeight(320);
        optionWindow.setWidth(480);
        optionWindow.setMovable(true);
        optionWindow.setModal(true);
        //optionWindow.setResizable(true);
        optionWindow.setVisible(false);
        optionWindow.getTitleLabel().setAlignment(Align.center);
        stage.addActor(optionWindow);

        confirmButton = new TextButton("CONFIRM", mySkin);
        confirmButton.setX(120);
        confirmButton.setY(80);
        confirmButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    //code here buat balik ke scene shop
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        optionWindow.addActor(confirmButton);

        cancelButton = new TextButton("CANCEL", mySkin);
        cancelButton.setX(600);
        cancelButton.setY(80);
        cancelButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    optionWindow.setVisible(false);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        optionWindow.addActor(cancelButton);

    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiInput);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        Texture background = assetManager.get("blue_background.png", Texture.class);

        for(int i=0; i<10; i++) {
            for (int j = 0; j < 18; j++)
                batch.draw(background, j * 64, i * 64);
        }

        batch.end();
        update();

        stage.act();
        stage.draw();
    }
    public void update()
    {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


}