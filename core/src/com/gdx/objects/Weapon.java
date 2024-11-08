package com.gdx.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.objects.weaponAnimationHandling.WeaponAnimation;

import java.awt.*;
import java.util.ArrayList;

public class Weapon extends Item{
    //weapon yang diequip player

    //stats
    private final int atk;
    private int level;
    private final float multiplier;
    private float sizeScaling;
    private float cooldown;
    private final ArrayList<TextureRegion>textureRegions = new ArrayList<>();
    private final WeaponAnimation weaponAnimation;

    //methods

    public Weapon(String itemName, String itemDescription, int atk, int level, float multiplier, float sizeScaling, float cooldown, WeaponAnimation weaponAnimation) {
        super(itemName, itemDescription);
        this.atk = atk;
        this.level = level;
        this.multiplier = multiplier;
        this.sizeScaling = sizeScaling;
        this.cooldown = cooldown;
        this.weaponAnimation = weaponAnimation;
    }

    public void addLevel(){
        level++;
    }

    public float getCooldown() {
        return cooldown;
    }

    public WeaponAnimation getWeaponAnimation() {
        return weaponAnimation;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public float getMaxFrame()throws NullPointerException{
        return weaponAnimation.getMaxFrameTime();
    }

    public float getSizeScaling() {
        return sizeScaling;
    }

    public void setSizeScaling(double sizeScaling) {
        this.sizeScaling = (float) sizeScaling;
    }
    public void addTextureRegion(TextureRegion textureRegion){
        this.textureRegions.add(textureRegion);
    }
    public void drawAttack(Player player, float frameTime, Batch batch){
        weaponAnimation.attack(player,textureRegions,frameTime,batch,sizeScaling);
    }

    @Override
    void itemDetails() {

    }

    public int getAtk() {
        return atk;
    }
}
