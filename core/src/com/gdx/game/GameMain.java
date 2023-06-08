package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.GdxAI;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.UI.MainMenuUI;
import com.gdx.objects.*;
import com.gdx.objects.playerAnimationHandling.MeleePlayerAnimation;
import com.gdx.objects.playerAnimationHandling.RangedPlayerAnimation;
import com.gdx.objects.weaponAnimationHandling.CreateProjectile;
import com.gdx.objects.weaponAnimationHandling.MagicWeaponAnimation;
import com.gdx.objects.weaponAnimationHandling.MeleeWeaponAnimation;
import com.gdx.objects.weaponAnimationHandling.RangeWeaponAnimation;

import java.awt.*;
import java.util.ArrayList;

public class GameMain extends Game {
	AssetManager manager;
	MainMenuUI mainMenuUI = new MainMenuUI();
	ArrayList<Projectile>projectiles = new ArrayList<>();
	SpriteBatch batch;
	Texture tiles;
	Texture weapons;
	Player player;
	Texture skeletonIdle;
	Texture skeletonRun;
	Texture skeletonDie;
	float attackStateTime;
	float attackCooldown;
	Sprite activePlayerProjectile;

	float stateTime;
	ArrayList<Floor> floors;
	Ruangan ruangan;

	@Override
	public void create () {
		batch = new SpriteBatch();
		stateTime = 0f;
		manager = new AssetManager();
		manager.load("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Heroes/Knight/Run/Run-Sheet.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Heroes/Knight/Death/Death-Sheet.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Heroes/Rogue/Idle/Idle-Sheet.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Heroes/Rogue/Run/Run-Sheet.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Idle/Idle-Sheet.png", Texture.class);
		manager.load("healthbar/monsterHealthBar.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Death/Death-Sheet.png", Texture.class);
		manager.load("Vortex/Effect_TheVortex_1_427x431.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Run/Run-Sheet.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Idle/Idle-Sheet.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Run/Run-Sheet.png", Texture.class);
		manager.load("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Death/Death-Sheet.png", Texture.class);
		manager.finishLoading();

		floors = new ArrayList<>();
		ruangan = new Ruangan("dungeon");
		ruangan.initialize(5, 1);

		skeletonIdle = manager.get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Idle/Idle-Sheet.png");
		skeletonRun = manager.get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Run/Run-Sheet.png");
		skeletonDie = manager.get("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Death/Death-Sheet.png");
		tiles = manager.get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		weapons = manager.get("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");

		player = makeRangedPlayer();
		player.setPosX(400);
		player.setPosY(300);

		mainMenuUI.forCreate();
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();
//		mainMenuUI.forRender(); //comment untuk cek game
		mainGame(batch); //comment untuk cek UI

		batch.end();
	}
	public void mainGame(SpriteBatch batch) {

		ruangan.draw(batch, stateTime);
		stateTime += Gdx.graphics.getDeltaTime();

		player.canMoveFree();
		if (player.getPosY() >= ruangan.getUpperborder().getY() - 20) {
			player.setCanMoveUp(false);
			}
		if (player.getPosY() <=ruangan.getBottomBorder().getY()) {
			player.setCanMoveDown(false);
			}
		if (player.getPosX() <= ruangan.getLeftBorder().getX() + 10) {
			player.setCanMoveLeft(false);
			}
		if (player.getPosX() >= ruangan.getRightBorder().getX()) {
			player.setCanMoveRight(false);
			}
		player.update(Gdx.graphics.getDeltaTime(),stateTime);
		player.draw(batch);

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !player.isAttacking() && attackCooldown == 0 && !player.isDying()) {
			player.setAttacking(true);
			attackStateTime = 0;
			attackCooldown = player.getWeapon().getCooldown();
		}
		attackCooldown-= Gdx.graphics.getDeltaTime();
		attackCooldown = Math.max(attackCooldown, 0);
		if (player.isAttacking()) {
			player.drawAttack(attackStateTime, batch);
			attackStateTime+=Gdx.graphics.getDeltaTime();
			System.out.println(attackStateTime);
		}
		if (attackStateTime >= player.getWeapon().getMaxFrame()) {
			player.setAttacking(false);
			attackStateTime = 0;
		}
		if (player.getWeapon().getWeaponAnimation() instanceof MagicWeaponAnimation){
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && attackCooldown == 0){
				attackStateTime = 0;
				attackCooldown = player.getWeapon().getCooldown();
			}
		}
		if (player.getWeapon().getWeaponAnimation() instanceof CreateProjectile && ((CreateProjectile) player.getWeapon().getWeaponAnimation()).getframeToCreateProjectile() <= attackStateTime && ((CreateProjectile) player.getWeapon().getWeaponAnimation()).canCreateProjectile()){
			Projectile p = ((CreateProjectile) player.getWeapon().getWeaponAnimation()).createProjectile(player,activePlayerProjectile);
			projectiles.add(p);
		}
		updateAllProjectile();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		manager.dispose();

	}
	public static float getAngleToMouse(float mouseX, float mouseY, float charX, float charY) {
		// Calculate the angle between the character and the mouse position
		mouseY = Gdx.graphics.getHeight() - mouseY;
		float deltaX = mouseX - charX;
		float deltaY = charY - mouseY;
		float angleRad = (float) Math.atan2(deltaY, deltaX);
		float angleDeg = (float) Math.toDegrees(angleRad);

		return angleDeg;
	}
	public void resize(int width, int height) {
                //viewport.update(width, height);
	}
	public Player makeMeleePlayer(){
		MeleeWeaponAnimation meleeWeaponAnimation = new MeleeWeaponAnimation();
		Weapon weapon = new Weapon("Excalibur", "OP", 99, 1, 2.0f, 2.0f,0.5f,meleeWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(weapons,0, 0,16,46));
		Player player1 = new Player(weapon, new MeleePlayerAnimation());
		return player1;
	}
	public Player makeRangedPlayer(){
		RangeWeaponAnimation rangeWeaponAnimation = new RangeWeaponAnimation();
		Weapon weapon = new Weapon("Bowsmth", "NotOP", 99, 1, 2.0f, 1.5f,1.0f,rangeWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(weapons,52,48,9,31));
		weapon.addTextureRegion(new TextureRegion(weapons,67,50,12,27));
		weapon.addTextureRegion(new TextureRegion(weapons,80,51,15,25));
		Texture tmp = manager.get("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");
		activePlayerProjectile = new Sprite(tmp,32,4,15,6);
		Player player1 = new Player(weapon, new RangedPlayerAnimation());
		return player1;
	}
	public Player makeMagicPlayer(){
		MagicWeaponAnimation magicWeaponAnimation = new MagicWeaponAnimation();
		Weapon weapon = new Weapon("Woo", "VeryCOOL", 99, 1, 2.0f, 1.5f,2.0f,magicWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(weapons,81,3,28,9));
		Player player1 = new Player(weapon, new MeleePlayerAnimation());
		return player1;
	}

	public AssetManager getManager() {
		return manager;
	}
	public void updateAllProjectile(){
		ArrayList<Integer>indexToDelete = new ArrayList<>();
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).draw(batch);
			projectiles.get(i).update();
			if (projectiles.get(i).getPositionY() >= ruangan.getUpperborder().getY()) {
				indexToDelete.add(i);
			}else
			if (projectiles.get(i).getPositionY() <= ruangan.getBottomBorder().getY() - 15) {
				indexToDelete.add(i);
			}else
			if (projectiles.get(i).getPositionX() <= ruangan.getLeftBorder().getX()) {
				indexToDelete.add(i);
			}else
			if (projectiles.get(i).getPositionX() >= ruangan.getRightBorder().getX() + 30) {
				indexToDelete.add(i);
			}
		}
		for (int i = 0; i < indexToDelete.size(); i++) {
			projectiles.remove(indexToDelete.get(i)-i);
		}
	}
}
