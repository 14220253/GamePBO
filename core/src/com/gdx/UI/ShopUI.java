package com.gdx.UI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.Exceptions.InventoryFullException;
import com.gdx.game.GameMain;
import com.gdx.game.MainGameScreen;
import com.gdx.objects.Accesories;
import com.gdx.objects.Consumable;
import com.gdx.objects.Item;
import com.gdx.objects.Player;
import sun.security.util.math.intpoly.IntegerPolynomial;

import java.util.ArrayList;


public class ShopUI implements Screen, InputProcessor {
    GameMain parentGame;
    AssetManager assetManager;

    private Viewport viewport;
    private OrthographicCamera camera, stageCamera;
    SpriteBatch batch;

    Stage stage;
    Label titleLabel;
    TextButton cancelButton, confirmButton, xButton;
    ImageButton diamond, ring, potion, crown, helmet, shoes, book;
    Window optionWindow;
    TextField optionWindowText;

    //condition checking
    Item currentItem;
    String clickedButton;
    int coin = 300;
    InputMultiplexer multiInput;
    public ShopUI(){
        //deklarasi parentgame
        parentGame = (GameMain) Gdx.app.getApplicationListener();
        //mendapatkan asset manager yang ada di game main
        assetManager = parentGame.getManager();
        //set camera
        camera = new OrthographicCamera(800,700);
        camera.setToOrtho(false, 800, 700);
        //set viewport
        viewport = new FitViewport(341, 200, camera);
        //set shop ui batch
        batch = new SpriteBatch();
        //set stage camera
        stageCamera = new OrthographicCamera(800, 700);
        stageCamera.setToOrtho(false, 800, 700);
        //set stage dengan memasukkan view port yang telah dibuat
        stage = new Stage(new FitViewport(341, 200, stageCamera));

        //input multiplexer
        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

        //load skin yang digunakan
        Skin mySkin = assetManager.get("fix1.json", Skin.class);



        //set judul shop ui
        BitmapFont font = new BitmapFont();
        BitmapFontCache text = new BitmapFontCache(font);
        titleLabel = new Label("SHOP", mySkin);
        Label.LabelStyle style = new Label.LabelStyle(titleLabel.getStyle());
        style.font = text.getFont();
        titleLabel.setStyle(style);
        titleLabel.setHeight(75);
        titleLabel.setX(113.5f);
        titleLabel.setY(180);
        titleLabel.setAlignment(Align.center);
        stage.addActor(titleLabel);

        //set xbutton
        xButton = new TextButton("X", mySkin);
        xButton.setHeight(20);
        xButton.setWidth(20);
        xButton.setX(320);
        xButton.setY(180);

        xButton.addListener(new InputListener(){
            //jika ditekan current item akan menjadi ring dan option window akan dimunculkan
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    dispose();
                    parentGame.setScreen(new GameMain());
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(xButton);

        //---------------------------------------RING-----------------------
        //set image button sebagai gambaran item yang nantinya dibeli
        //image button ring set posisi dan set bentuk gambar
        ring = new ImageButton(mySkin,"ring");
        ring.setWidth(50);
        ring.setHeight(50);
        ring.setX(55);
        ring.setY(100);
        ring.addListener(new InputListener(){
            //jika ditekan current item akan menjadi ring dan option window akan dimunculkan
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("ring", "");
                    clickedButton = "ring";
                    optionWindow.setVisible(true);
                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //------------------------------------POTION---------------------

        //image button potion set posisi dan ukuran
        potion = new ImageButton(mySkin,"potion");
        potion.setWidth(50);
        potion.setHeight(50);
        potion.setX(105);
        potion.setY(100);
        potion.addListener(new InputListener() {
           //jika ditekan current item akan menjadi potion dan option window akan dimunculkan
           @Override
           public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                   currentItem = new Consumable("potion", "");
                   clickedButton = "potion";
                   optionWindow.setVisible(true);
               }
           }
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               return true;
           }
       });


        //-------------------------------------BOOK--------------------
        book = new ImageButton(mySkin,"book");
        book.setWidth(50);
        book.setHeight(50);
        book.setX(155);
        book.setY(100);
        book.addListener(new InputListener() {
            //jika ditekan current item akan menjadi book dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("book", "");
                    clickedButton = "book";
                    optionWindow.setVisible(true);
                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //--------------------HELMET---------------------
        helmet = new ImageButton(mySkin,"helmet");
        helmet.setWidth(50);
        helmet.setHeight(50);
        helmet.setX(205);
        helmet.setY(100);
        helmet.addListener(new InputListener() {
            //jika ditekan current item akan menjadi helmet dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("helmet", "");
                    clickedButton = "helmet";
                    optionWindow.setVisible(true);
                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //---------------------------SHOES---------------------
        shoes = new ImageButton(mySkin,"shoes");
        shoes.setWidth(50);
        shoes.setHeight(50);
        shoes.setX(80);
        shoes.setY(90);
        shoes.addListener(new InputListener() {
            //jika ditekan current item akan menjadi helmet dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("shoes", "");
                    clickedButton = "shoes";
                    optionWindow.setVisible(true);
                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //----------------------------CROWN--------------------
        crown = new ImageButton(mySkin,"crown");
        crown.setWidth(50);
        crown.setHeight(50);
        crown.setY(90);
        crown.setX(130);
        crown.addListener(new InputListener() {
            //jika ditekan current item akan menjadi helmet dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("crown", "");
                    clickedButton = "crown";
                    optionWindow.setVisible(true);
                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //--------------------------DIAMOND--------------------------
        diamond = new ImageButton(mySkin, "diamond");
        diamond.setWidth(50);
        diamond.setHeight(50);
        diamond.setX(180);
        diamond.setY(90);
        diamond.addListener(new InputListener() {
            //jika ditekan current item akan menjadi helmet dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("diamond", "");
                    clickedButton = "diamond";
                    optionWindow.setVisible(true);
                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //add semua image button ke dalam stage agar bisa ditekan
        stage.addActor(ring);
        stage.addActor(diamond);
        stage.addActor(helmet);
        stage.addActor(potion);
        stage.addActor(book);
        stage.addActor(crown);
        stage.addActor(shoes);

        //option window setelah mengklik image button
        optionWindow = new Window("WARNING", mySkin, "default");
        //set ukuran dari option window
        optionWindow.setHeight(200);
        optionWindow.setWidth(300);
        optionWindow.setMovable(true); //bisa digerakkan
        optionWindow.setModal(true);
        optionWindow.setResizable(false); //tidak bisa dibesar kecilkan
        optionWindow.setVisible(false); //awalnya option window tidak diperlihatkan
        optionWindow.getTitleLabel().setAlignment(Align.center);
        stage.addActor(optionWindow);

        //menambahkan semua actor yang diperukan di option window
        //-----------------------------textfield
        optionWindowText = new TextField(("Are You Sure Want to Buy This for" + coin +" "), mySkin);
        optionWindowText.setAlignment(Align.center);
        optionWindowText.setHeight(32);
        optionWindowText.setWidth(50);
        optionWindowText.setX(125);
        optionWindowText.setY(116);
        optionWindow.add(optionWindowText);
        //confirm sebagai buy button
        confirmButton = new TextButton("CONFIRM", mySkin);
        confirmButton.setX(127.5f);
        confirmButton.setY(80);
        //-----------------------------confirm button
        confirmButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    try {
                        parentGame.getPlayer().getInventory().addItem(currentItem);
                        currentItem = null;
                        parentGame.getPlayer().getInventory().addCoin(-1*coin);
                    } catch (InventoryFullException e) {
                        throw new RuntimeException(e);
                    }
                    //disable selected button
                    if(clickedButton.equals("ring")){
                        ring.setDisabled(true);
                        clickedButton = "";
                    }else if (clickedButton.equals("potion")){
                        potion.setDisabled(true);
                        clickedButton = "";
                    }else if (clickedButton.equals("helmet")){
                        helmet.setDisabled(true);
                        clickedButton = "";
                    }else if (clickedButton.equals("shoes")){
                        shoes.setDisabled(true);
                        clickedButton = "";
                    }else if (clickedButton.equals("diamond")){
                        diamond.setDisabled(true);
                        clickedButton = "";
                    }else if (clickedButton.equals("book")){
                        book.setDisabled(true);
                        clickedButton = "";
                    }else if (clickedButton.equals("crown")){
                        crown.setDisabled(true); //karena ga bisa beli lebih dari 1 item yang sama
                        clickedButton = "";
                    }
                    //code here buat balik ke scene shop

                }
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });
        optionWindow.addActor(confirmButton);
        //------------------cancel button
        cancelButton = new TextButton("CANCEL", mySkin);
        cancelButton.setX(137.5f);
        cancelButton.setY(80);
        cancelButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    clickedButton = "";
                    currentItem = null;
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
        viewport.update(width, height);
        stage.getViewport().update(width, height);
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