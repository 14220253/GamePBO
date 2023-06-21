package com.gdx.objects;

import java.util.ArrayList;
import java.util.Random;

public class Buffs {
    //buff yang dipilih kalau selesaikan ruangan

    //deskripsi buff
    private String buffName;
    private String buffDescription;
    private BuffType type;
    private int amount;
    private int level;
    int tier;

    public enum BuffType{
        ATTACK,
        DEFENSE,
        RESTORE_HP,
        RESTORE_MANA,
        EVASION,
        MAX_HEALTH,
        WEAPON_SIZE
    }

    public Buffs(int level, int tier) {
        this.level = level;
        this.tier = tier;
        switch (tier) {
            case 1:
                generateTier1(tier);
                break;
            case 2:
                generateTier2(tier);
                break;
            case 3:
                generateTier3(tier);
                break;
        }
    }
    private void generateTier1(int tier) {
        ArrayList<BuffType> tier1Buffs = new ArrayList<>();
        tier1Buffs.add(BuffType.ATTACK);
        tier1Buffs.add(BuffType.DEFENSE);
        tier1Buffs.add(BuffType.RESTORE_HP);
        tier1Buffs.add(BuffType.RESTORE_MANA);
        BuffType selected = tier1Buffs.get(new Random().nextInt(0, tier1Buffs.size()));
        type = selected;

        initialize(selected, tier);
    }

    private void generateTier2(int tier) {
        ArrayList<BuffType> tier2Buffs = new ArrayList<>();
        tier2Buffs.add(BuffType.ATTACK);
        tier2Buffs.add(BuffType.DEFENSE);
        tier2Buffs.add(BuffType.RESTORE_HP);
        tier2Buffs.add(BuffType.RESTORE_MANA);
        tier2Buffs.add(BuffType.EVASION);
        BuffType selected = tier2Buffs.get(new Random().nextInt(0, tier2Buffs.size()));
        type = selected;

        initialize(selected, tier);
    }
    private void generateTier3(int tier) {
        //tidak pakai restore karena masuk floor berikutnya langsung full heal
        ArrayList<BuffType> tier3Buffs = new ArrayList<>();
        tier3Buffs.add(BuffType.ATTACK);
        tier3Buffs.add(BuffType.DEFENSE);
        tier3Buffs.add(BuffType.EVASION);
        tier3Buffs.add(BuffType.MAX_HEALTH);
        tier3Buffs.add(BuffType.WEAPON_SIZE);
        BuffType selected = tier3Buffs.get(new Random().nextInt(0, tier3Buffs.size()));
        type = selected;

        initialize(selected, tier);
    }

    public void initialize(BuffType type, int tier) {
        switch (type) {
            case ATTACK:
                buffName = "Increase Attack";
                amount = level * ((tier * 2) - 1);
                buffDescription = "Increases Attack by " + amount + "%";
                break;
            case DEFENSE:
                buffName = "Increase Defense";
                amount = level * ((tier * 2) - 1);
                buffDescription = "Increases Defense by " + amount + "%";
                break;
            case RESTORE_HP:
                buffName = "Restore Health";
                amount = 20;
                buffDescription = "Restores " + amount + "Health";
                break;
            case RESTORE_MANA:
                buffName = "Restore Mana";
                amount = 30;
                buffDescription = "Restores " + amount + "Mana";
                break;
            case EVASION:
                buffName = "Increases Evasion";
                amount = 5;
                buffDescription = "Increases Evasion by " + amount;
                break;
            case WEAPON_SIZE:
                buffName = "Increase Weapon Size";
                amount = 5;
                buffDescription = "Increases Weapon Size by " + amount + "%";
                break;
            case MAX_HEALTH:
                buffName = "Increases Max Health";
                amount = 10;
                buffDescription = "Increases Max HP by " + amount;
                break;
        }
    }

    public void activate(Player player) {
        switch (type) {
            case ATTACK:
                player.addAttack((double) player.getAttack() * ((double) amount / 100));
                break;
            case DEFENSE:
                player.addDefense((double) player.getDefense() * ((double) amount / 100));
                break;
            case RESTORE_MANA:
                player.addMana(amount);
                break;
            case RESTORE_HP:
                player.addHealth(amount);
                break;
            case MAX_HEALTH:
                player.addMaxHealth(amount);
                break;
            case EVASION:
                player.addEvasion(amount);
                break;
            case WEAPON_SIZE:
                player.addWeaponSize((double) player.getWeapon().getSizeScaling() * ((double) amount / 100));
                break;
        }
    }

    public BuffType getType() {
        return type;
    }

    public String getBuffName() {
        return buffName;
    }

    public String getBuffDescription() {
        return buffDescription;
    }

    public int getAmount() {
        return amount;
    }

    public int getTier() {return tier;}
}
