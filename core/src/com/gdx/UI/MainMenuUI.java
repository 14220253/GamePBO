package com.gdx.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;


public class MainMenuUI {
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

    public void forCreate() {
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
                //code buat pindah scene
//                System.out.println("HIIIIIIII");
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
                //code buat exit
            }
        });
        exitButton.setX(240);
        exitButton.setY(150);
        exitButton.setWidth(350);
        exitButton.setHeight(90);
        stage.addActor(exitButton);
    }

    public void forRender() {
        stage.act();
        stage.draw();
    }

    public MainMenuUI() {
    }
}
