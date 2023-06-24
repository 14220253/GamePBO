package com.gdx.objects.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.gdx.game.Animator;
import com.gdx.game.Drawer;
import com.gdx.game.Static;
import com.gdx.game.GameMain;
import com.gdx.objects.Bosses.Boss;
import com.gdx.objects.Bosses.StoneGolem;
import com.gdx.objects.Breakable;
import com.gdx.objects.Buffs;
import com.gdx.objects.Drops;
import com.gdx.objects.Monsters.Monster;
import com.gdx.objects.Monsters.Orc;
import com.gdx.objects.Monsters.Skeleton;
import com.gdx.objects.Player;
import com.gdx.objects.weaponAnimationHandling.MeleeWeaponAnimation;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Ruangan {
    //bounds
    private Rectangle leftBorder;
    private Rectangle rightBorder;
    private Rectangle bottomBorder;
    private Rectangle upperborder;
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
    private Texture buttons;
    private TextureRegion button;
    private Rectangle leftButtonHitbox;
    private Rectangle rightButtonHitbox;
    private Buffs selectedBuff;
    private boolean done;
    private final Player PLAYER;
    private Sound openDoor;
    private Sound closeDoor;
    private Boss boss;

    public Ruangan(String type, Player player) {
        this.TYPE = type;
        PLAYER = player;
    }

    /**
     * initialize enemy rooms
     * @param template 1-6 monster location template
     * @param level level scaling (belum diimplement)
     */
    public void initialize(int template, int level, int roomNumber) {
        this.level = level;
        app = (GameMain) Gdx.app.getApplicationListener();
        openDoor = app.getManager().get("DoorOpens.mp3");
        closeDoor = app.getManager().get("CloseDoor.mp3");
        monsters = new ArrayList<>();
        breakables = new ArrayList<>();
        drops = new ArrayList<>();
        buffs = new ArrayList<>();
        centerDoorHitbox = new Rectangle(365, 475, 100, 150);
        leftDoorHitbox = new Rectangle(-25, 265, 150, 100);
        rightDoorHitbox = new Rectangle(675, 265, 150, 100);
        this.roomNumber = roomNumber;
        showingCard = false;
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
        if (TYPE.equalsIgnoreCase("Dungeon") || TYPE.equalsIgnoreCase("boss")) {
            PLAYER.canMoveFree();

            Drawer.drawDungeon(batch);
            leftBorder = new Rectangle(48, 40, 5, 500);
            rightBorder = new Rectangle(710, 40, 5, 500);
            bottomBorder = new Rectangle(48, 55, 705, 8);
            upperborder = new Rectangle(48, 525, 705, 8);
        }
        else if (TYPE.equalsIgnoreCase("Shop")) {
            PLAYER.canMoveFree();

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
            if(Static.rectangleCollisionDetect(new Rectangle(400,300,64,64),PLAYER.getHitBox())) {
                app.openShopUI();//this
            }
            if(PLAYER.getPosY() == 300 && PLAYER.getPosX() ==400){
                app.openShopUI();
            }
            centerDoorHitbox.setLocation(350, 320);

        }

        //entities
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
                        monster.moveToCoordinates(PLAYER.getPosX(), PLAYER.getPosY(), Gdx.graphics.getDeltaTime());
                    }
                }
                monster.draw(batch, stateTime);
                monster.setRunning(true);
            }
            //COLLECT FLOOR ITEMS
            for (int i = 0; i < drops.size(); i++) {
                if (Static.rectangleCollisionDetect(PLAYER.getHitBox(), drops.get(i).getHitbox())) {
                    if (drops.get(i).getType() == Drops.Type.COIN && drops.get(i).getState() != Drops.State.COLLECTED) {
                        PLAYER.getInventory().addCoin(drops.get(i).getAmount());
                        drops.get(i).getCollectSound().play(0.5f);
                    }
                    if (drops.get(i).getType() == Drops.Type.HEALTH && drops.get(i).getState() != Drops.State.COLLECTED) {
                        PLAYER.addHealth(drops.get(i).getAmount());
                        drops.get(i).getCollectSound().play(0.3f);
                    }
                    if (drops.get(i).getType() == Drops.Type.MANA && drops.get(i).getState() != Drops.State.COLLECTED) {
                        PLAYER.addMana(drops.get(i).getAmount());
                        drops.get(i).getCollectSound().play(0.8f);
                    }
                    drops.get(i).setState(Drops.State.COLLECTED);
                }
                drops.get(i).draw(batch, stateTime);
                if (drops.get(i).getState() == Drops.State.GONE) {
                    drops.remove(i);
                }
            }
            //MONSTER HIT PLAYER
            for(Monster monster: monsters) {
                if (Static.rectangleCollisionDetect(monster.getHitBox(), PLAYER.getHitBox())) {
                    if (PLAYER.getImmunityFrames() == 0) {
                        monster.takeDamage(PLAYER.getAttack());
                        PLAYER.takeDamage(monster.getAttack());
                    }
                }
            }
            //PLAYER ATTACKING
            if (PLAYER.isAttacking()) {
                for (int i = 0; i < PLAYER.getWeapon().getWeaponAnimation().getHitboxes().length; i++) {
                    for (Breakable breakable : breakables) {
                        if (Static.rectangleCollisionDetect(PLAYER.getWeapon().getWeaponAnimation().getHitboxes()[i],
                                breakable.getHitbox())) {
                            breakable.setState(Breakable.State.HALFBROKEN);
                            breakable.getBreakSound().play(0.25f);
                        }
                    }
                    for (Monster monster:monsters) {
                        if (Static.rectangleCollisionDetect(PLAYER.getWeapon().getWeaponAnimation().getHitboxes()[i],
                                monster.getHitBox())) {
                            if (PLAYER.getWeapon().getWeaponAnimation() instanceof MeleeWeaponAnimation && monster.getImmunityFrames() == 0) {
                                ((MeleeWeaponAnimation) PLAYER.getWeapon().getWeaponAnimation()).getAttackSound().play(0.8f);
                            }
                            monster.takeDamage(PLAYER.getAttack());
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
        }

        if (TYPE.equalsIgnoreCase("boss")){
            if (boss instanceof StoneGolem) {
                boss.draw(batch, stateTime);
                if (((StoneGolem) boss).isSpawning()) {
                    PLAYER.cannotMove();
                }
                else {
                    PLAYER.canMoveFree();
                }
            }
        }


        //PINTU
        if ((Static.rectangleCollisionDetect(PLAYER.getHitBox(), centerDoorHitbox) ||
                Static.rectangleCollisionDetect(PLAYER.getHitBox(), leftDoorHitbox) ||
                        Static.rectangleCollisionDetect(PLAYER.getHitBox(), rightDoorHitbox)) && monsters.size() == 0) {

            BitmapFont font = new BitmapFont();
            font.getData().setScale(1.5f);
            BitmapFontCache text = new BitmapFontCache(font);
            text.setText("Press Spacebar", PLAYER.getPosX() - 40, PLAYER.getPosY() + PLAYER.getHeight() * 2);
            text.draw(batch);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (!showingCard) {
                    showingCard = true;
                    openDoor.play(1.2f);
                }
                if  (TYPE.equalsIgnoreCase("shop")) {
                    done = true;
                    closeDoor.play(1.2f);
                }
            }


            if (showingCard && !TYPE.equalsIgnoreCase("shop")) {
                PLAYER.cannotMove();
                if (Static.rectangleCollisionDetect(PLAYER.getHitBox(), centerDoorHitbox)) {
                    Drawer.drawCard(batch, buffs.get(0));
                    selectedBuff = buffs.get(0);
                }
                if (Static.rectangleCollisionDetect(PLAYER.getHitBox(), leftDoorHitbox)) {
                    Drawer.drawCard(batch, buffs.get(1));
                    selectedBuff = buffs.get(1);
                }
                if (Static.rectangleCollisionDetect(PLAYER.getHitBox(), rightDoorHitbox)) {
                    Drawer.drawCard(batch, buffs.get(2));
                    selectedBuff = buffs.get(2);
                }
                if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                    showingCard = false;
                    PLAYER.canMoveFree();
                    closeDoor.play(1.2f);
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
                        PLAYER.canMoveFree();
                        closeDoor.play(1.2f);
                    }
                    if (Static.rectangleCollisionDetect(rightButtonHitbox, new Rectangle(Gdx.input.getX(), Gdx.input.getY(), 1, 1))) {
                        done = true;
                        selectedBuff.activate(PLAYER);
                        closeDoor.play(1.2f);
                    }
                }
            }
        }
        //remove semua monster kalau player mati
        if (PLAYER.isDying()){
            if (monsters.size() > 0) {
                monsters.subList(0, monsters.size()).clear();
            }
        }
    }



    private void initializeTemplate(int template, int level) {
        int health = 50;
        int attack = 10;
        int defense = level / 2;
        double hpMultiplier = 1 + ((double) level / 10);
        double damageMultiplier = 1 + ((double) level / 10);
        double defenseMultiplier = ((double) level / 10);

        if (template == 0) {
            boss = new StoneGolem(health * 20, attack * 5, defense * 5, level, hpMultiplier, damageMultiplier, defenseMultiplier);
        }
        if (template == 1) {
            int posX = 200;
            int posY = 400;
            for (int i = 1; i <= 3; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                            damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                }
                posX += 200;
            }
        }
        if (template == 2) {
            int posX;
            int posY;
            for (int i = 1; i <= 3; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monsters.add(monster);
                        switch (i) {
                            case 1:
                                posX = 400;
                                posY = 450;
                                monster.setLocation(posX, posY);
                                break;
                            case 2:
                                posX = 125;
                                posY = 300;
                                monster.setLocation(posX, posY);
                                break;
                            case 3:
                                posX = 625;
                                posY = 300;
                                monster.setLocation(posX, posY);
                                break;
                        }
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        switch (i) {
                            case 1:
                                posX = 400;
                                posY = 450;
                                monster.setLocation(posX, posY);
                                break;
                            case 2:
                                posX = 125;
                                posY = 300;
                                monster.setLocation(posX, posY);
                                break;
                            case 3:
                                posX = 625;
                                posY = 300;
                                monster.setLocation(posX, posY);
                                break;
                        }
                        monsters.add(monster);
                        break;
                }

            }
        }
        if (template == 3) {
            int posX = 150;
            int posY = 400;
            for (int i = 1; i <= 4; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                }
                posX += 150;
            }
        }
        if (template == 4) {
            int posX = 250;
            int posY = 450;
            for (int i = 1; i <= 2; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                }
                posX += 300;
            }
            posX = 100;
            posY = 250;
            for (int i = 1; i <= 2; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                }
                posX += 550;
            }
        }
        if (template == 5) {
            int posX = 150;
            int posY = 250;
            for (int i = 1; i <= 2; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                }
                posX += 500;
            }
            posX = 250;
            posY = 450;
            for (int i = 1; i <= 2; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                }
                posX += 250;
            }
        }
        if (template == 6) {
            int posX = 250;
            int posY = 375;
            for (int i = 1; i <= 2; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                }
                posX += 300;
            }
            posX = 150;
            posY = 250;
            for (int i = 1; i <= 2; i++) {
                int coinFlip = Static.randomizer(2);
                Monster monster;
                switch (coinFlip) {
                    case 0:
                        monster = new Orc(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                    case 1:
                        monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                                damageMultiplier, defenseMultiplier);
                        monster.setLocation(posX, posY);
                        monsters.add(monster);
                        break;
                }
                posX += 500;
            }
            posX = 400;
            posY = 450;
            int coinFlip = Static.randomizer(2);
            Monster monster;
            switch (coinFlip) {
                case 0:
                    monster = new Orc(health, attack, defense, level, hpMultiplier,
                            damageMultiplier, defenseMultiplier);
                    monster.setLocation(posX, posY);
                    monsters.add(monster);
                    break;
                case 1:
                    monster = new Skeleton(health, attack, defense, level, hpMultiplier,
                            damageMultiplier, defenseMultiplier);
                    monster.setLocation(posX, posY);
                    monsters.add(monster);
                    break;
            }
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
