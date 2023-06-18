//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gdx.UI.MainMenuUI;
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

public class GameMain extends Game {
	AssetManager manager;
	MainMenuUI mainMenuUI = new MainMenuUI();
	ArrayList<Projectile> projectiles = new ArrayList();
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
	ShapeRenderer shapeRenderer;
	ArrayList<Karakter> entities;
	boolean isOnDebug;
	BitmapFont font;
	BitmapFontCache text;
	PlayerUI UI;

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
		manager.load("healthbar/SleekBars.png", Texture.class);
		manager.load("coins/MonedaD.png", Texture.class);
		manager.load("coins/Collected.png", Texture.class);
		this.manager.finishLoading();

		font = new BitmapFont();
		text = new BitmapFontCache(font);

		this.floors = new ArrayList();
		entities = new ArrayList<>();
		shapeRenderer = new ShapeRenderer();
		isOnDebug = false;
		this.ruangan = new Ruangan("dungeon", 100);
		this.ruangan.initialize(5, 1);
		this.tiles = this.manager.get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		this.weapons = this.manager.get("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");
		this.player = this.makeMagicPlayer();
		this.player.setPosX(400);
		this.player.setPosY(300);
		UI = new PlayerUI(player);
		this.mainMenuUI.forCreate();
	}

	public void render() {
		ScreenUtils.clear(0.0F, 0.0F, 0.0F, 1.0F);
		this.batch.begin();
		this.mainGame(this.batch);
		if (Gdx.input.isKeyJustPressed(Input.Keys.F9)) {
			isOnDebug = !isOnDebug;
		}
		if(isOnDebug) {
			enableDebug();
		}
		this.batch.end();
	}

	public void mainGame(SpriteBatch batch) {
		this.ruangan.draw(batch, this.stateTime, player);
		this.stateTime += Gdx.graphics.getDeltaTime();
		this.player.canMoveFree();
		UI.draw(batch);

		for (int i = 0; i < ruangan.getMonsters().size(); i++) {
				ruangan.getMonsters().get(i).takeDamage(10);
		}

		if ((double) this.player.getPosY() >= this.ruangan.getUpperborder().getY() - 20.0) {
			this.player.setCanMoveUp(false);
		}

		if ((double) this.player.getPosY() <= this.ruangan.getBottomBorder().getY()) {
			this.player.setCanMoveDown(false);
		}

		if ((double) this.player.getPosX() <= this.ruangan.getLeftBorder().getX() + 10.0) {
			this.player.setCanMoveLeft(false);
		}

		if ((double) this.player.getPosX() >= this.ruangan.getRightBorder().getX()) {
			this.player.setCanMoveRight(false);
		}

		this.player.update(Gdx.graphics.getDeltaTime(), this.stateTime);
		this.player.draw(batch);
		this.updatePlayerAttacks();
		this.updateAllProjectile();
	}

	public void dispose() {
		this.batch.dispose();
		this.manager.dispose();
		font.dispose();
	}

	public void enableDebug() {
		getEnities();
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		for (Karakter entity : entities) {
			shapeRenderer.rect(
					(float) entity.getHitBox().getX(),
					(float) entity.getHitBox().getY(),
					(float) entity.getHitBox().getWidth(),
					(float) entity.getHitBox().getHeight()
			);
		}
		shapeRenderer.end();
	}
	public void getEnities() {
		if (!entities.contains(player)) {
			entities.add(player);
		}
		if (entities.size() <= 1) {
			entities.addAll(ruangan.getMonsters());
		}
	}
	public static float getAngleToMouse(float mouseX, float mouseY, float charX, float charY) {
		mouseY = (float) Gdx.graphics.getHeight() - mouseY;
		float deltaX = mouseX - charX;
		float deltaY = charY - mouseY;
		float angleRad = (float) Math.atan2(deltaY, deltaX);
		float angleDeg = (float) Math.toDegrees(angleRad);
		return angleDeg;
	}

	public void resize(int width, int height) {
	}

	public Player makeMeleePlayer() {
		MeleeWeaponAnimation meleeWeaponAnimation = new MeleeWeaponAnimation();
		Weapon weapon = new Weapon("Excalibur", "OP", 99, 1, 2.0F, 2.0F, 0.5F, meleeWeaponAnimation);
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

	public void updateAllProjectile() {
		ArrayList<Integer> indexToDelete = new ArrayList();

		int i;
		for (i = 0; i < this.projectiles.size(); ++i) {
			this.projectiles.get(i).draw(this.batch);
			this.projectiles.get(i).update();
			if ((double) this.projectiles.get(i).getPositionY() >= this.ruangan.getUpperborder().getY()) {
				indexToDelete.add(i);
			} else if ((double) this.projectiles.get(i).getPositionY() <= this.ruangan.getBottomBorder().getY() - 15.0) {
				indexToDelete.add(i);
			} else if ((double) this.projectiles.get(i).getPositionX() <= this.ruangan.getLeftBorder().getX()) {
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
					System.out.println("test");
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
		}

		if (this.player.getWeapon().getWeaponAnimation() instanceof CreateProjectile && ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).getframeToCreateProjectile() <= this.attackStateTime && ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).canCreateProjectile()) {
			Projectile p = ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).createProjectile(this.player, this.activePlayerProjectile);
			this.projectiles.add(p);
		}
	}
}