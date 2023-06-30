//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.Exceptions.NotEnoughCoinsException;
import com.gdx.UI.ShopUI;
import com.gdx.objects.Skills.ImpenetrableShield;
import com.gdx.objects.playerAnimationHandling.RangedPlayerAnimation;
import com.gdx.screen.MainMenuScreen;
import com.gdx.objects.*;
import com.gdx.objects.playerAnimationHandling.MagicPlayerAnimation;
import com.gdx.objects.playerAnimationHandling.MeleePlayerAnimation;
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
	BitmapFont font;
	BitmapFontCache text;
	ShopUI shopUI;
	Viewport viewport;
	Camera camera;
	public float masterSound = 0.5f;//GUNAKAN MASTERSOUND UNTUK SEMUA SOUND (bukan music)
	public float masterMusic = 0.7f;//GUNAKAN MASTERMUSIC UNTUK SEMUA MUSIC (bukan sound)

	public GameMain() {
	}
	public void create() {
		this.batch = new SpriteBatch();
		this.manager = new AssetManager();
		//OBJECTS
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
		this.manager.load("gameOverScreen.png", Texture.class);
		manager.load("mainMenu/menuUI.png", Texture.class);
		manager.load("mainMenu/menu.png", Texture.class);
		manager.load("BuffLogos.png", Texture.class);
		manager.load("GUI.png", Texture.class);
		manager.load("healthbar/SleekBars.png", Texture.class);
		manager.load("coins/MonedaD.png", Texture.class);
		manager.load("coins/Collected.png", Texture.class);
		manager.load("pixelCardAssest_V01.png", Texture.class);
		manager.load("Free Pixel Effects Pack/17_felspell_spritesheet.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/PNG sheet/arm launch.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/PNG sheet/death animation.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/PNG sheet/downwards arm launch.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/PNG sheet/glowing.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/PNG sheet/glowing idle.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/PNG sheet/head glow.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/PNG sheet/idle.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/PNG sheet/invunerable.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/weapon PNG/arm_projectile_resize.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/weapon PNG/arm_projectile_glowing.png", Texture.class);
		manager.load("Bosses/Mecha-stone Golem 0.1/weapon PNG/Laser_sheet.png", Texture.class);

		//shop
		manager.load("Idle Working.png", Texture.class);
		SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter("fix1.atlas");
		manager.load("fix1.json", Skin.class, skinParam);
		SkinLoader.SkinParameter skinParam2 = new SkinLoader.SkinParameter("uiskin.atlas");
		manager.load("uiskin.json", Skin.class, skinParam2);
		manager.load("background.png", Texture.class);


		//SOUNDS
		manager.load("Music.mp3", Music.class);
		manager.load("Steps.ogg", Sound.class);
		manager.load("Barrel.mp3", Sound.class);
		manager.load("Box.mp3", Sound.class);
		manager.load("Ceramic.mp3", Sound.class);
		manager.load("Sword.mp3", Sound.class);
		manager.load("Bow.mp3", Sound.class);
		manager.load("Magic.mp3", Sound.class);
		manager.load("SwordSwing.mp3", Sound.class);
		manager.load("SkeletonDies.mp3", Sound.class);
		manager.load("GoblinDies.mp3", Sound.class);
		manager.load("DoorOpens.mp3", Sound.class);
		manager.load("CloseDoor.mp3", Sound.class);
		manager.load("CoinCollect.mp3", Sound.class);
		manager.load("HeartCollect.mp3", Sound.class);
		manager.load("ManaCollect.mp3", Sound.class);

		this.manager.finishLoading();

		font = new BitmapFont();
		text = new BitmapFontCache(font);

		this.tiles = this.manager.get("Pixel Crawler - FREE - 1.8/Environment/Dungeon Prison/Assets/Tiles.png");
		this.weapons = this.manager.get("Pixel Crawler - FREE - 1.8/Weapons/Wood/Wood.png");
		this.player = this.makeMeleePlayer();
		this.player.setSkill(new ImpenetrableShield());
		this.player.setPosX(400);
		this.player.setPosY(100);
		this.player.canMoveFree();
		shopUI = new ShopUI();
		this.setScreen(new MainMenuScreen(batch));

		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 700, camera);
	}
	public void openShopUI(){
		shopUI.show();
		setScreen(shopUI);//---------------------
	}

	public void render() {
		ScreenUtils.clear(0.0F, 0.0F, 0.0F, 1.0F);
		this.batch.begin();
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



	public Player makeMeleePlayer() {
		MeleeWeaponAnimation meleeWeaponAnimation = new MeleeWeaponAnimation();
		Weapon weapon = new Weapon("Excalibur", "OP", 700, 1, 2.0F, 2.0F, 0.5F, meleeWeaponAnimation);
		weapon.addTextureRegion(new TextureRegion(this.weapons, 0, 0, 16, 46));
		return new Player(weapon, new MeleePlayerAnimation());
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
		return new Player(weapon, new MagicPlayerAnimation());
	}

	public AssetManager getManager() {
		return this.manager;
	}
	public void updateAllProjectile() {
//		ArrayList<Integer> indexToDelete = new ArrayList();
//
//		int i;
//		for (i = 0; i < this.projectiles.size(); ++i) {
//			this.projectiles.get(i).draw(this.batch);
//			this.projectiles.get(i).update();
//			if ((double) this.projectiles.get(i).getPositionY() >= this.floors.get(floorCount).getCurrentRoom().getUpperborder().getY()) {
//				indexToDelete.add(i);
//			} else if ((double) this.projectiles.get(i).getPositionY() <= this.floors.get(floorCount).getCurrentRoom().getBottomBorder().getY() - 15.0) {
//				indexToDelete.add(i);
//			} else if ((double) this.projectiles.get(i).getPositionX() <= this.floors.get(floorCount).getCurrentRoom().getLeftBorder().getX()) {
//			}
//		}
	}
	public void updatePlayerAttacks() {
		if (Gdx.input.isButtonJustPressed(0) && !this.player.isAttacking() && this.attackCooldown == 0.0F && !this.player.isDying()) {
			this.player.setAttacking(true);
			this.attackStateTime = 0.0F;
			this.attackCooldown = this.player.getWeapon().getCooldown();

			if (player.getWeapon().getWeaponAnimation() instanceof MeleeWeaponAnimation) {
				((MeleeWeaponAnimation) player.getWeapon().getWeaponAnimation()).getSwingSound().play(masterSound);
			}
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
				if (player.getWeapon().getWeaponAnimation().getHitboxes()[i].intersects(new Rectangle(60, 400))) {
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
			((MagicWeaponAnimation) player.getWeapon().getWeaponAnimation()).getAttackSound().play(masterSound);
		}

		if (this.player.getWeapon().getWeaponAnimation() instanceof CreateProjectile && ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).getframeToCreateProjectile() <= this.attackStateTime && ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).canCreateProjectile()) {
			((RangeWeaponAnimation) player.getWeapon().getWeaponAnimation()).getAttackSound().play(masterSound);
			Projectile p = ((CreateProjectile) this.player.getWeapon().getWeaponAnimation()).createProjectile(this.player, this.activePlayerProjectile);
			this.projectiles.add(p);
		}
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void resize(int width, int height) {
		viewport.update(width, height);;
	}

	public  Vector3 getMousePos() {
		return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
	}
}
