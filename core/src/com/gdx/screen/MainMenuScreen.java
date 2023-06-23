package com.gdx.screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.gdx.game.GameMain;
import com.gdx.game.MainGameScreen;

public class MainMenuScreen implements Screen, InputProcessor {
    GameMain parentGame;
    Texture mainMenu;
    Stage stage;
    TextureRegion menuWindow;
    TextureRegion startButtonIdle;
    TextureRegion optionsButtonIdle;
    TextureRegion exitButtonIdle;
    TextureRegion startButtonHover;
    TextureRegion optionsButtonHover;
    TextureRegion exitButtonHover;
    Button startButton;
    Button optionButton;
    Button exitButton;
    public MainMenuScreen(final SpriteBatch batch) {
        parentGame = (GameMain) Gdx.app.getApplicationListener();
        stage = new Stage(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
        mainMenu = new Texture("mainMenu/menuUI.png");
        menuWindow = new TextureRegion(mainMenu, 479, 0, 470, 300);
        startButtonIdle = new TextureRegion(mainMenu, 0, 1, 475, 133);
        optionsButtonIdle = new TextureRegion(mainMenu, 0, 429, 475, 133);
        exitButtonIdle = new TextureRegion(mainMenu, 479, 429, 475, 133);
        startButtonHover = new TextureRegion(mainMenu, 0, 290, 475, 133);
        optionsButtonHover = new TextureRegion(mainMenu, 0, 717, 475, 133);
        exitButtonHover = new TextureRegion(mainMenu, 479, 717, 475, 133);

        startButton = new Button(new TextureRegionDrawable(startButtonIdle));
        startButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                startButton.getStyle().up = new TextureRegionDrawable(startButtonHover);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                startButton.getStyle().up = new TextureRegionDrawable(startButtonIdle);
            }
        });
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                parentGame.setScreen(new MainGameScreen(batch));
            }
        });
        startButton.setX(240);
        startButton.setY(350);
        startButton.setWidth(350);
        startButton.setHeight(90);
        stage.addActor(startButton);

        optionButton = new Button(new TextureRegionDrawable(optionsButtonIdle));
        optionButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                optionButton.getStyle().up = new TextureRegionDrawable(optionsButtonHover);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                optionButton.getStyle().up = new TextureRegionDrawable(optionsButtonIdle);
            }
        });
        optionButton.setX(240);
        optionButton.setY(250);
        optionButton.setWidth(350);
        optionButton.setHeight(90);
        stage.addActor(optionButton);

        exitButton = new Button(new TextureRegionDrawable(exitButtonIdle));
        exitButton.addListener(new InputListener() {
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                exitButton.getStyle().up = new TextureRegionDrawable(exitButtonHover);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                exitButton.getStyle().up = new TextureRegionDrawable(exitButtonIdle);
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        exitButton.setX(240);
        exitButton.setY(150);
        exitButton.setWidth(350);
        exitButton.setHeight(90);
        stage.addActor(exitButton);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act();
        stage.draw();
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
        stage.dispose();
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
