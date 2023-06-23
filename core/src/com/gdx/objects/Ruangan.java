package com.gdx.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.gdx.UI.ShopUI;
import com.gdx.game.Animator;
import com.gdx.game.Drawer;
import com.gdx.game.Static;
import com.gdx.game.GameMain;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ruangan {
    //bounds
    private Rectangle leftBorder;
    private Rectangle rightBorder;
    private Rectangle bottomBorder;
    private Rectangle upperborder;
    private Texture texture;
    private final String TYPE;
    private ArrayList<Monster> monsters;
    private ArrayList<Breakable> breakables;
    private ArrayList<Drops> drops;
    private ArrayList<Buffs> buffs;
    private Rectangle centerDoorHitbox;
    private Rectangle leftDoorHitbox;
    private Rectangle rightDoorHitbox;
    private GameMain app;
    private boolean showingCard;
    private int level;
    private int roomNumber;
    private boolean cooldown;
    private Texture buttons;
    private TextureRegion button;
    private Rectangle leftButtonHitbox;
    private Rectangle rightButtonHitbox;
    private Buffs selectedBuff;
    private boolean done;
    private Player player;

    public Ruangan(String type, Player player) {
        this.TYPE = type;
        this.player = player;
    }

    /**
     * initialize enemy rooms
     * @param template 1-6 monster location template
     * @param level level scaling (belum diimplement)
     */
    public void initialize(int template, int level, int roomNumber) {
        this.level = level;
        app = (GameMain) Gdx.app.getApplicationListener();
        monsters = new ArrayList<>();
        breakables = new ArrayList<>();
        drops = new ArrayList<>();
        buffs = new ArrayList<>();
        centerDoorHitbox = new Rectangle(365, 475, 100, 150);
        leftDoorHitbox = new Rectangle(-25, 265, 150, 100);
        rightDoorHitbox = new Rectangle(675, 265, 150, 100);
        this.roomNumber = roomNumber;
        showingCard = false;
        cooldown = false;
        buttons = app.getManager().get("GUI.png");
        button = new TextureRegion(buttons, 144, 80, 48, 16);
        leftButtonHitbox = new Rectangle(170, 550, 220, 80);
        rightButtonHitbox = new Rectangle(450, 550, 220, 80);

        initializeBreakable();
        initializeTemplate(template, level);
        initializeBuffs(roomNumber);
    }
    private void initializeBreakable() {
        Random randomizer = new Random();
        int breakableAmount = randomizer.nextInt(1, 6);
        for (int i = 0; i < breakableAmount; i++) {
            int posX, posY;
            posX = randomizer.nextInt(58, 720); //posX
            posY = randomizer.nextInt(50,520); //posY

            Breakable breakable = new Breakable(posX, posY);
            breakables.add(breakable);
        }
    }

    private void initializeBuffs(int roomNumber) {
        while (buffs.size() < 3) {
            Buffs buff;
            if (roomNumber == 1 || roomNumber == 2 || roomNumber == 3){ //tier1
                buff = new Buffs(level, 1);
                buffs.add(buff);
            }
            if (roomNumber == 4 || roomNumber == 5){ //tier2
                buff = new Buffs(level, 2);
                buffs.add(buff);
            }
            if (roomNumber == 6) { //shop
                break;
            }
            if (roomNumber == 7) {
                buff = new Buffs(level, 3); //boss
                buffs.add(buff);
            }
        }
    }

    public void draw(SpriteBatch batch, float stateTime) {
        //map
        if (TYPE.equalsIgnoreCase("Dungeon")) {
            player.canMoveFree();

            Drawer.drawDungeon(batch);
            leftBorder = new Rectangle(48, 40, 5, 500);
            rightBorder = new Rectangle(710, 40, 5, 500);
            bottomBorder = new Rectangle(48, 55, 705, 8);
            upperborder = new Rectangle(48, 525, 705, 8);
        }
        else if (TYPE.equalsIgnoreCase("Shop")) {
            player.canMoveFree();

            texture = app.getManager().get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
            Drawer.drawDungeonShop(batch);
            rightBorder = new Rectangle(510, 203, 5, 300);
            leftBorder = new Rectangle(205, 203, 5, 300);
            bottomBorder = new Rectangle(205, 213, 347, 8);
            upperborder = new Rectangle(205, 362, 347, 8);

            //NPC
            Texture NPC = app.getManager().get("Idle Working.png");
            Animation<TextureRegion> NPCAnimation = Animator.animate(NPC,8,1,false, false);
            TextureRegion currentFrame = NPCAnimation.getKeyFrame(stateTime,true);
            batch.draw(currentFrame, 400,300, 64, 64);
            //test
            if (Static.rectangleCollisionDetect(player.getHitBox(),
                    new Rectangle(400, 300, 64, 64))) {
                app.setScreen(new ShopUI());
            }

            if(app.getPlayer().getPosX() == 400 && app.getPlayer().getPosY() == 300){
                app.setScreen(new ShopUI());
            }

        }

        //enemies
        if (!TYPE.equalsIgnoreCase("Shop") && !TYPE.equalsIgnoreCase("boss")) {
            //PROPS
            for (Breakable breakable : breakables) {
                if (breakable.getState() == Breakable.State.HALFBROKEN) {
                    drops.add(new Drops(breakable.getPosX(), breakable.getPosY(), level, breakable.getType()));
                }
                breakable.draw(batch);
            }
            //MONSTERS
            for (Monster monster : monsters) {
                if (monster.isRunsToPlayer()){
                    if (monster.getState() != Monster.State.DYING) {
                        monster.moveToCoordinates(player.posX, player.posY, Gdx.graphics.getDeltaTime());
                    }
                }
                monster.draw(batch, stateTime);
            }
            //COLLECT FLOOR ITEMS
            for (int i = 0; i < drops.size(); i++) {
                if (Static.rectangleCollisionDetect(player.getHitBox(), drops.get(i).getHitbox())) {
                    if (drops.get(i).getType() == Drops.Type.COIN) {
                        player.getInventory().addCoin(drops.get(i).getAmount());
                    }
                    if (drops.get(i).getType() == Drops.Type.HEALTH) {
                        player.addHealth(drops.get(i).getAmount());
                    }
                    if (drops.get(i).getType() == Drops.Type.MANA) {
                        player.addMana(drops.get(i).getAmount());
                    }
                    drops.get(i).setState(Drops.State.COLLECTED);
                }
                drops.get(i).draw(batch, stateTime);
                if (drops.get(i).getState() == Drops.State.GONE) {
                    drops.remove(i);
                }
            }
        }

        //MONSTER HIT PLAYER
        for(Monster monster: monsters) {
            if (Static.rectangleCollisionDetect(monster.getHitBox(), player.getHitBox())) {
                monster.takeDamage(player.getAttack());
                player.takeDamage(monster.getAttack());
            }
        }

        //PLAYER ATTACKING
        if (player.isAttacking()) {
            for (int i = 0; i < player.getWeapon().getWeaponAnimation().getHitboxes().length; i++) {
                for (Breakable breakable : breakables) {
                    if (Static.rectangleCollisionDetect(player.getWeapon().getWeaponAnimation().getHitboxes()[i],
                            breakable.getHitbox())) {
                        breakable.setState(Breakable.State.HALFBROKEN);
                    }
                }
                for (Monster monster:monsters) {
                    if (Static.rectangleCollisionDetect(player.getWeapon().getWeaponAnimation().getHitboxes()[i],
                            monster.getHitBox())) {
                        monster.takeDamage(player.getAttack());
                    }
                }
            }
        }

        //MONSTER DIES
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).getState() == Monster.State.DEAD) {
                drops.add(new Drops(monsters.get(i).getPosX(), monsters.get(i).getPosY(), level, Drops.Type.COIN));
                monsters.remove(i);
            }
        }

        //PINTU
        if ((Static.rectangleCollisionDetect(player.hitBox, centerDoorHitbox) ||
                Static.rectangleCollisionDetect(player.hitBox, leftDoorHitbox) ||
                        Static.rectangleCollisionDetect(player.hitBox, rightDoorHitbox)) && monsters.size() == 0) {

            BitmapFont font = new BitmapFont();
            font.getData().setScale(1.5f);
            BitmapFontCache text = new BitmapFontCache(font);
            text.setText("Press Spacebar", player.getPosX() - 40, player.getPosY() + player.getHeight() * 2);
            text.draw(batch);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (!showingCard) {
                    showingCard = true;
                    cooldown = true;
                }
            }

            if (showingCard) {
                player.cannotMove();
                if (Static.rectangleCollisionDetect(player.hitBox, centerDoorHitbox)) {
                    Drawer.drawCard(batch, buffs.get(0));
                    selectedBuff = buffs.get(0);
                }
                if (Static.rectangleCollisionDetect(player.hitBox, leftDoorHitbox)) {
                    Drawer.drawCard(batch, buffs.get(1));
                    selectedBuff = buffs.get(1);
                }
                if (Static.rectangleCollisionDetect(player.hitBox, rightDoorHitbox)) {
                    Drawer.drawCard(batch, buffs.get(2));
                    selectedBuff = buffs.get(2);
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                    if (!cooldown) {
                        showingCard = false;
                        player.canMoveFree();
                    }
                    else
                        cooldown = false;
                }
                //BUTTONS
                batch.draw(button, (float) leftButtonHitbox.getX(), 700 - (float) leftButtonHitbox.getY() - (float) leftButtonHitbox.getHeight(),
                        (float) leftButtonHitbox.getWidth(), (float) leftButtonHitbox.getHeight());
                batch.draw(button, (float) rightButtonHitbox.getX(), 700 - (float) rightButtonHitbox.getY() - (float) rightButtonHitbox.getHeight(),
                        (float) rightButtonHitbox.getWidth(), (float) rightButtonHitbox.getHeight());
                font.getData().setScale(2f);
                text = new BitmapFontCache(font);
                text.setColor(Color.BLACK);
                text.setText("Exit", 255,128);
                text.draw(batch);
                text.setColor(Color.WHITE);
                text.setText("Exit", 255, 123);
                text.draw(batch);

                text.setColor(Color.BLACK);
                text.setText("Select", 520,128);
                text.draw(batch);
                text.setColor(Color.WHITE);
                text.setText("Select", 520, 123);
                text.draw(batch);

                //mouse
                if (Gdx.input.isButtonJustPressed(0)) {
                    if (Static.rectangleCollisionDetect(leftButtonHitbox, new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 1, 1))) {
                        showingCard = false;
                        player.canMoveFree();
                    }
                    if (Static.rectangleCollisionDetect(rightButtonHitbox, new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 1, 1))) {
                        done = true;
                        selectedBuff.activate(player);
                    }
                }
            }
        }
    }



    private void initializeTemplate(int template, int level) {
        if (template == 1) {
            Monster monster1, monster2, monster3;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 200, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 200, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 600, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 600, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
        }
        if (template == 2) {
            Monster monster1, monster2, monster3;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 125, 300,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 125, 300,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 675, 300,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 675, 300,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
        }
        if (template == 3) {
            Monster monster1, monster2, monster3, monster4;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 100, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 100, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 300, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 300, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 450, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 450, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster4= new Monster(50, 10, 0, level, 650, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster4= new Monster(50, 10, 0, level, 650, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
            monsters.add(monster4);
        }
        if (template == 4) {
            Monster monster1, monster2, monster3, monster4;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 100, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 100, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 250, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 250, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 550, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 550, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
            monsters.add(monster4);
        }
        if (template == 5) {
            Monster monster1, monster2, monster3, monster4, monster5;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 150, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 150, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 150, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 150, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 650, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 650, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster5= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster5= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
            monsters.add(monster4);
            monsters.add(monster5);
        }
        if (template == 6) {
            Monster monster1, monster2, monster3, monster4, monster5;
            if (Static.coinFlip() == 0) {
                monster1= new Monster(50, 10, 0, level, 150, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster1= new Monster(50, 10, 0, level, 150, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster2= new Monster(50, 10, 0, level, 250, 375,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster2= new Monster(50, 10, 0, level, 250, 375,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster3= new Monster(50, 10, 0, level, 550, 375,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster3= new Monster(50, 10, 0, level, 550, 375,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster4= new Monster(50, 10, 0, level, 650, 250,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            if (Static.coinFlip() == 0) {
                monster5= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "orc");
            } else {
                monster5= new Monster(50, 10, 0, level, 400, 450,
                        new Rectangle(40,  50), 1.2, 1.2, 1.2, "skeleton");
            }

            monsters.add(monster1);
            monsters.add(monster2);
            monsters.add(monster3);
            monsters.add(monster4);
            monsters.add(monster5);
        }
    }
    public ArrayList<Monster> getMonsters() {return new ArrayList<>(monsters);}
    public Rectangle getLeftBorder() {return leftBorder;}
    public Rectangle getRightBorder() {return rightBorder;}
    public Rectangle getBottomBorder() {return bottomBorder;}
    public Rectangle getUpperborder() {return upperborder;}
    public ArrayList<Breakable> getBreakables() {return breakables;}
    public ArrayList<Drops> getDrops() {return drops;}
    public boolean isShowingCard() {return showingCard;}
    public boolean isDone() {return done;}
}
