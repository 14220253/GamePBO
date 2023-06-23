//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.UI.ShopUI;
import com.gdx.screen.MainMenuScreen;
import com.gdx.UI.PlayerUI;
import com.gdx.objects.*;
import com.gdx.objects.playerAnimationHandling.MagicPlayerAnimation;
import com.gdx.objects.playerAnimationHandling.MeleePlayerAnimation;
import com.gdx.objects.playerAnimationHandling.RangedPlayerAnimation;
import com.gdx.objects.weaponAnimationHandling.CreateProjectile;
import com.gdx.objects.weaponAnimationHandling.MagicWeaponAnimation;
import com.gdx.objects.weaponAnimationHandling.MeleeWeaponAnimation;
import com.gdx.objects.weaponAnimationHandling.RangeWeaponAnimation;

import java.awt.*;
import java.util.ArrayList;

public class GameMain extends Game implements Screen {
	AssetManager manager;
	ArrayList<Projectile> projectiles = new ArrayList<>();
	SpriteBatch batch;
	Texture tiles;
	Texture weapons;
	Player player;
	float attackStateTime;
	float attackCooldown;
	Sprite activePlayerProjectile;
	float stateTime;
	ArrayList<Floor> floors;
	boolean isOnDebug;
	BitmapFont font;
	BitmapFontCache text;
	PlayerUI UI;
	int floorCount;
	MainGameScreen game;
	ShopUI shopUI;

	public GameMain() {
	}

	public void create() {
		this.batch = new SpriteBatch();
		this.stateTime = 0.0F;
		this.manager = new AssetManager();
		this.manager.load("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Knight/Idle/Idle-Sheet.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Knight/Run/Run-Sheet.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Knight/Death/Death-Sheet.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Rogue/Idle/Idle-Sheet.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Rogue/Run/Run-Sheet.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Rogue/Death/Death-Sheet.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Wizzard/Idle/Idle-Sheet.png",Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Wizzard/Run/Run-Sheet.png",Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Heroes/Wizzard/Death/Death-Sheet.png",Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Idle/Idle-Sheet.png", Texture.class);
		this.manager.load("healthbar/monsterHealthBar.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Death/Death-Sheet.png", Texture.class);
		this.manager.load("Vortex/Effect_TheVortex_1_427x431.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Enemy/Orc Crew/Orc/Run/Run-Sheet-Resize.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Idle/Idle-Sheet.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Run/Run-Sheet-Resize.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Enemy/Skeleton Crew/Skeleton - Base/Death/Death-Sheet.png", Texture.class);
		this.manager.load("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Props.png", Texture.class);
		this.manager.load("heart.png", Texture.class);
		this.manager.load("star.png", Texture.class);
		manager.load("BuffLogos.png", Texture.class);
		manager.load("GUI.png", Texture.class);
		manager.load("healthbar/SleekBars.png", Texture.class);
		manager.load("coins/MonedaD.png", Texture.class);
		manager.load("coins/Collected.png", Texture.class);
		manager.load("pixelCardAssest_V01.png", Texture.class);

		//shop

		manager.load("Idle Working.png", Texture.class);
		SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter("fix1.atlas");
		manager.load("fix1.json", Skin.class, skinParam);
		manager.load("blue_background.png", Texture.class);


		//SOUNDS
		manager.load("ambient.mp3", Music.class);
		manager.load("Steps.ogg", Sound.class);
		manager.load("Barrel.mp3", Sound.class);
		manager.load("Box.mp3", Sound.class);
		manager.load("Ceramic.mp3", Sound.class);
		manager.load("Sword.mp3", Sound.class);
		manager.load("Bow.mp3", Sound.class);
		manager.load("Magic.mp3", Sound.class);

		this.manager.finishLoading();

		font = new BitmapFont();
		text = new BitmapFontCache(font);

		isOnDebug = false;
		this.tiles = this.manager.get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		this.weapons = this.manager.get("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");
		this.player = this.makeMeleePlayer();
		this.player.setPosX(400);
		this.player.setPosY(100);
		UI = new PlayerUI(player);
		this.player.canMoveFree();
		floorCount = 0;
		this.floors = new ArrayList<>();
//		Floor floor = new Floor(6, 1, player); //kalo mau mulai dari floor tertentu
		Floor floor = new Floor(1, player);
		floors.add(floor);
		this.setScreen(new MainMenuScreen());
		game = new MainGameScreen(floors, floorCount, stateTime, player, isOnDebug, UI, this);
		shopUI = new ShopUI();
	}
	public void openShopUI(){
		shopUI.show();  // Panggil show() untuk menampilkan ShopUI
		setScreen(shopUI);//---------------------
	}

	public void render() {
		ScreenUtils.clear(0.0F, 0.0F, 0.0F, 1.0F);
		this.batch.begin();
		game.mainGame(batch);
		getScreen().render(Gdx.graphics.getDeltaTime());//------------------
		this.batch.end();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void hide() {

	}

	public void dispose() {
		this.batch.dispose();
		this.manager.dispose();
		font.dispose();
	}


	public static float getAngleToMouse(float mouseX, float mouseY, float charX, float charY) {
		mouseY = (float) Gdx.graphics.getHeight() - mouseY;
		float deltaX = mouseX - charX;
		float deltaY = charY - mouseY;
		float angleRad = (float) Math.atan2(deltaY, deltaX);
		float angleDeg = (float) Math.toDegrees(angleRad);
		return angleDeg;
	}
	public Player getPlayer(){
		return this.player;
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

	}

	public void resize(int width, int height) {
	}

	public Player makeMeleePlayer() {
		MeleeWeaponAnimation meleeWeaponAnimation = new MeleeWeaponAnimation();
		Weapon weapon = new Weapon("Excalibur", "OP", 100, 1, 2.0F, 2.0F, 0.5F, meleeWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(this.weapons, 0, 0, 16, 46));
		Player player1 = new Player(weapon, new MeleePlayerAnimation());
		return player1;
	}

	public Player makeRangedPlayer() {
		RangeWeaponAnimation rangeWeaponAnimation = new RangeWeaponAnimation();
		Weapon weapon = new Weapon("Bowsmth", "NotOP", 99, 1, 2.0F, 1.5F, 1.0F, rangeWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(this.weapons, 52, 48, 9, 31));
		weapon.addTextureRegion(new TextureRegion(this.weapons, 67, 50, 12, 27));
		weapon.addTextureRegion(new TextureRegion(this.weapons, 80, 51, 15, 25));
		Texture tmp = this.manager.get("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");
		this.activePlayerProjectile = new Sprite(tmp, 32, 4, 15, 6);
		Player player1 = new Player(weapon, new RangedPlayerAnimation());
		return player1;
	}

	public Player makeMagicPlayer() {
		MagicWeaponAnimation magicWeaponAnimation = new MagicWeaponAnimation();
		Weapon weapon = new Weapon("Woo", "VeryCOOL", 99, 1, 2.0F, 1.5F, 2.0F, magicWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(this.weapons, 81, 3, 28, 9));
		Player player1 = new Player(weapon, new MagicPlayerAnimation());
		return player1;
	}

	public AssetManager getManager() {
		return this.manager;
	}
	public void updateNPC (int x, int y){
		if(player.getPosX()== x && player.getPosY() ==y){
			this.setScreen(new ShopUI());
		}

	}

	public void updateAllProjectile() {
		ArrayList<Integer> indexToDelete = new ArrayList();

		int i;
		for (i = 0; i < this.projectiles.size(); ++i) {
			this.projectiles.get(i).draw(this.batch);
			this.projectiles.get(i).update();
			if ((double) this.projectiles.get(i).getPositionY() >= this.floors.get(floorCount).getCurrentRoom().getUpperborder().getY()) {
				indexToDelete.add(i);
			} else if ((double) this.projectiles.get(i).getPositionY() <= this.floors.get(floorCount).getCurrentRoom().getBottomBorder().getY() - 15.0) {
				indexToDelete.add(i);
			} else if ((double) this.projectiles.get(i).getPositionX() <= this.floors.get(floorCount).getCurrentRoom().getLeftBorder().getX()) {
			}
		}
	}
	public void updatePlayerAttacks(){
		if (Gdx.input.isButtonJustPressed(0) && !this.player.isAttacking() && this.attackCooldown == 0.0F && !this.player.isDying()) {
			this.player.setAttacking(true);
			this.attackStateTime = 0.0F;
			this.attackCooldown = this.player.getWeapon().getCooldown();
		}

		this.attackCooldown -= Gdx.graphics.getDeltaTime();
		this.attackCooldown = Math.max(this.attackCooldown, 0.0F);
		if (this.player.isAttacking()) {
			this.player.drawAttack(this.attackStateTime, batch);
			this.attackStateTime += Gdx.graphics.getDeltaTime();
		}

		if (this.player.isAttacking()) {
			for (int i = 0; i < player.getWeapon().getWeaponAnimation().getHitboxes().length; i++) {
				//for (int j = 0; j < banyaknya monster ; j++) {
				//	if (monster.getHitBox.intersects(player.getWeapon().getWeaponAnimation().getHitboxes()[i])){
				//  code kena dmg untuk monster
				//	}
				//}
				if (player.getWeapon().getWeaponAnimation().getHitboxes()[i].intersects(new Rectangle(60,400))){
//					System.out.println("test");
				}
			}
		}

		if (this.attackStateTime >= this.player.getWeapon().getMaxFrame()) {
			this.player.setAttacking(false);
			this.attackStateTime = 0.0F;
		}

		if (this.player.getWeapon().getWeaponAnimation() instanceof MagicWeaponAnimation && Gdx.input.isButtonJustPressed(0) && this.attackCooldown == 0.0F) {
			this.attackStateTime = 0.0F;
			this.attackCooldown = this.player.getWeapon().getCooldown();
			player.addMana(-5);
			((MagicWeaponAnimation) player.getWeapon().getWeaponAnimation()).getAttackSound().play(0.5f);
		}

		if (this.player.getWeapon().getWeaponAnimation() instanceof CreateProjectile && ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).getframeToCreateProjectile() <= this.attackStateTime && ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).canCreateProjectile()) {
			((RangeWeaponAnimation) player.getWeapon().getWeaponAnimation()).getAttackSound().play(0.3f);
			Projectile p = ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).createProjectile(this.player, this.activePlayerProjectile);
			this.projectiles.add(p);
		}

		if(player.getSkill() != null){
			updatePlayerSkill();
		}
	}
	public void updatePlayerSkill(){
		if (Gdx.input.isKeyJustPressed(Input.Keys.E)){
			player.getSkill().intitialize(player);
		}
		player.getSkill().update(player);
		player.getSkill().draw(player);
	}
}
