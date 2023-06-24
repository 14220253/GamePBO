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
import com.gdx.Exceptions.NotEnoughCoinsException;
import com.gdx.game.GameMain;
import com.gdx.screen.MainGameScreen;
import com.gdx.objects.Accesories;
import com.gdx.objects.Consumable;
import com.gdx.objects.Item;


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
    int coin = 0;
    InputMultiplexer multiInput;
    public ShopUI(){
        //deklarasi parentgame
        parentGame = (GameMain) Gdx.app.getApplicationListener();
        //mendapatkan asset manager yang ada di game main
        assetManager = parentGame.getManager();
        //set camera
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //set viewport
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        //set shop ui batch
        batch = new SpriteBatch();
        //set stage camera
        stageCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stageCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //set stage dengan memasukkan view port yang telah dibuat
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), stageCamera));

        //input multiplexer
        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

        //load skin yang digunakan
        Skin mySkin = assetManager.get("fix1.json", Skin.class);
        Skin mySkin2 = assetManager.get("uiskin.json", Skin.class);

        //set judul shop ui
        BitmapFont font = new BitmapFont();
        BitmapFontCache text = new BitmapFontCache(font);
        titleLabel = new Label("SHOP", mySkin);
        Label.LabelStyle style = new Label.LabelStyle(titleLabel.getStyle());
        style.font = text.getFont();
        titleLabel.setStyle(style);
        titleLabel.setHeight(100);
        titleLabel.setX(350);
        titleLabel.setY(500);
        titleLabel.setAlignment(Align.center);
        stage.addActor(titleLabel);

        //set xbutton
        xButton = new TextButton("X", mySkin2);
        xButton.setHeight(20);
        xButton.setWidth(20);
        xButton.setX(600);
        xButton.setY(600);

        xButton.addListener(new InputListener(){
            //jika ditekan current item akan menjadi ring dan option window akan dimunculkan
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    dispose();
                    parentGame.setScreen(new MainGameScreen(parentGame.getBatch()));
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
        ring.setWidth(100);
        ring.setHeight(100);
        ring.setX(200);
        ring.setY(300);
        ring.addListener(new InputListener(){
            //jika ditekan current item akan menjadi ring dan option window akan dimunculkan
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("ring", "hp + 10");
                    clickedButton = "ring";
                    coin = 200;
                    optionWindow.setVisible(true);
                    optionWindowText.setText("Buy for" + coin + "coin ?");

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
        potion.setWidth(100);
        potion.setHeight(100);
        potion.setX(300);
        potion.setY(300);
        potion.addListener(new InputListener() {
           //jika ditekan current item akan menjadi potion dan option window akan dimunculkan
           @Override
           public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
               if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                   currentItem = new Consumable("potion", "hp + 15");
                   clickedButton = "potion";
                   coin = 205;
                   optionWindow.setVisible(true);
                   optionWindowText.setText("Buy for" + coin + "coin ?");

               }
           }
           @Override
           public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
               return true;
           }
       });


        //-------------------------------------BOOK--------------------
        book = new ImageButton(mySkin,"book");
        book.setWidth(100);
        book.setHeight(100);
        book.setX(400);
        book.setY(300);
        book.addListener(new InputListener() {
            //jika ditekan current item akan menjadi book dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("book", "atk + 5");
                    clickedButton = "book";
                    coin = 100;
                    optionWindow.setVisible(true);
                    optionWindowText.setText("Buy for" + coin + "coin ?");

                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //--------------------HELMET---------------------
        helmet = new ImageButton(mySkin,"helmet");
        helmet.setWidth(100);
        helmet.setHeight(100);
        helmet.setX(500);
        helmet.setY(300);
        helmet.addListener(new InputListener() {
            //jika ditekan current item akan menjadi helmet dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("helmet", "def + 5");
                    clickedButton = "helmet";
                    coin = 150;
                    optionWindow.setVisible(true);
                    optionWindowText.setText("Buy for" + coin + "coin ?");

                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //---------------------------SHOES---------------------
        shoes = new ImageButton(mySkin,"shoes");
        shoes.setWidth(100);
        shoes.setHeight(100);
        shoes.setX(250);
        shoes.setY(200);
        shoes.addListener(new InputListener() {
            //jika ditekan current item akan menjadi helmet dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("shoes", "def + 3");
                    clickedButton = "shoes";
                    coin = 75;
                    optionWindow.setVisible(true);
                    optionWindowText.setText("Buy for" + coin + "coin ?");

                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //----------------------------CROWN--------------------
        crown = new ImageButton(mySkin,"crown");
        crown.setWidth(100);
        crown.setHeight(100);
        crown.setY(200);
        crown.setX(350);
        crown.addListener(new InputListener() {
            //jika ditekan current item akan menjadi helmet dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("crown", "def + 2");
                    clickedButton = "crown";
                    coin = 50;
                    optionWindow.setVisible(true);
                    optionWindowText.setText("Buy for" + coin + "coin ?");

                }
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //--------------------------DIAMOND--------------------------
        diamond = new ImageButton(mySkin, "diamond");
        diamond.setWidth(100);
        diamond.setHeight(100);
        diamond.setX(450);
        diamond.setY(200);
        diamond.addListener(new InputListener() {
            //jika ditekan current item akan menjadi helmet dan option window akan dimunculkan
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    currentItem = new Accesories("diamond", "atk + 2");
                    clickedButton = "diamond";
                    coin = 65;
                    optionWindow.setVisible(true);
                    optionWindowText.setText("Buy for" + coin + "coin ?");

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
        optionWindow = new Window("WARNING", mySkin2, "default");
        //set ukuran dari option window
        optionWindow.setHeight(300);
        optionWindow.setWidth(400);
        optionWindow.setX(200);
        optionWindow.setY(200);
        optionWindow.setMovable(true); //bisa digerakkan
        optionWindow.setModal(true);
        optionWindow.setResizable(false); //tidak bisa dibesar kecilkan
        optionWindow.setVisible(false); //awalnya option window tidak diperlihatkan
        optionWindow.getTitleLabel().setAlignment(Align.center);
        stage.addActor(optionWindow);

        //menambahkan semua actor yang diperukan di option window
        //-----------------------------textfield
        optionWindowText = new TextField(("Buy for " + coin +"coin?"), mySkin2);
        optionWindowText.setHeight(400);
        optionWindowText.setWidth(500);
        optionWindowText.setX(200);
        optionWindowText.setY(200);
        optionWindow.add(optionWindowText);
        //confirm sebagai buy button
        confirmButton = new TextButton("CONFIRM", mySkin2);
        confirmButton.setX(250);
        confirmButton.setY(50);
        confirmButton.setWidth(75);
        confirmButton.setHeight(50);
        //-----------------------------confirm button
        confirmButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if( x >= 0 && y >= 0 && x <= event.getTarget().getWidth() && y <= event.getTarget().getHeight()) {
                    try {
                        parentGame.getPlayer().getInventory().addItem(currentItem);
                        currentItem = null;
                    } catch (InventoryFullException e) {
                        throw new RuntimeException(e);
                    }
                    try{
                        parentGame.getPlayer().getInventory().spendCoin(coin);
                    } catch (NotEnoughCoinsException e) {
                        optionWindowText.setText("Sorry, not enough coin");
                        clickedButton = "";
                        e.printStackTrace();
                    }
                    //disable selected button
                    if(clickedButton.equals("ring")){
                        ring.setDisabled(true);
                        clickedButton = "";
                        coin = 0;
                    }else if (clickedButton.equals("potion")){
                        potion.setDisabled(true);
                        clickedButton = "";
                        coin = 0;
                    }else if (clickedButton.equals("helmet")){
                        helmet.setDisabled(true);
                        clickedButton = "";
                        coin = 0;
                    }else if (clickedButton.equals("shoes")){
                        shoes.setDisabled(true);
                        clickedButton = "";
                        coin = 0;
                    }else if (clickedButton.equals("diamond")){
                        diamond.setDisabled(true);
                        clickedButton = "";
                        coin = 0;
                    }else if (clickedButton.equals("book")){
                        book.setDisabled(true);
                        clickedButton = "";
                        coin = 0;
                    }else if (clickedButton.equals("crown")){
                        crown.setDisabled(true); //karena ga bisa beli lebih dari 1 item yang sama
                        clickedButton = "";
                        coin = 0;
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
        cancelButton = new TextButton("CANCEL", mySkin2);
        cancelButton.setX(325);
        cancelButton.setY(50);
        cancelButton.setWidth(75);
        cancelButton.setHeight(50);
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

        Texture background = assetManager.get("background.png", Texture.class);

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
        stage.dispose();
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