package NPCs;

import java.awt.*;

public class NPC {
    protected String id = "NPC";
    protected String name;
    protected int level;
    protected double hitPoints;
    protected double currentHp;
    protected double currentMana;
    protected double mana;
    protected int intelligence;
    protected int strength;
    protected int dexterity;
    protected int stamina;
    protected int hit;
    protected double xpOnKill;
    protected double initiative;
    protected boolean turn;
    protected int baseHp;
    protected double positionX;
    protected double PositionY;
    protected Image monsterImage;
    protected Image monsterIcon;


    public NPC() {

    }
    // Get & set fields

    public double getPositionY() {
        return this.PositionY;
    }

    public void setPositionY(final double positionY) {
        this.PositionY = positionY;
    }

    public double getPositionX() {
        return this.positionX;
    }

    public void setPositionX(final double positionX) {
        this.positionX = positionX;
    }

    public Image getMonsterIcon() {
        return this.monsterIcon;
    }

    public void setMonsterIcon(final Image monsterIcon) {
        this.monsterIcon = monsterIcon;
    }

    public Image getMonsterImage() {
        return this.monsterImage;
    }

    public void setMonsterImage(final Image monsterImage) {
        this.monsterImage = monsterImage;
    }

    public int getBaseHp() {
        return this.baseHp;
    }

    public void setBaseHp(final int baseHp){
        this.baseHp = baseHp;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(final boolean turn) {
        this.turn = turn;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public double getHitPoints() {
        return this.hitPoints;
    }

    public void setHitpoints(final double hitPoints) {
        this.hitPoints = hitPoints;
    }

    public void setCurrentHp(final double currentHp) {
        this.currentHp = currentHp;
    }

    public double getCurrentHp() {
        return this.currentHp;
    }

    public double getMana() {
        return this.mana;
    }

    public void setMana(final double mana) {
        this.mana = mana;
    }

    public void setCurrentMana(final double currentMana) {
        this.currentMana = currentMana;
    }

    public double getCurrentMana() {
        return this.currentMana;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public void setIntelligence(final int intelligence) {
        this.intelligence = intelligence;
    }

    public int getStrength() {
        return this.strength;
    }

    public void setStrength(final int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public void setDexterity(final int dexterity) {
        this.dexterity = dexterity;
    }

    public int getStamina() {
        return this.stamina;
    }

    public void setStamina(final int stamina) {
        this.stamina = stamina;
    }

    public int getHit() {
        return this.hit;
    }

    public void setHit(final int hit) {
        this.hit = hit;
    }

    public double getExperienceOnKill() {
        return this.xpOnKill;
    }

    public void setExperienceOnKill(final double xpOnKill) {
        this.xpOnKill = xpOnKill;
    }

    public double getInitiativeNPC() {
        return this.initiative;
    }

    public void setInitiativeNPC(final double initiative) {
        this.initiative = initiative;
    }

    // Overrides
    @Override
    public String toString() {
        return this.name;
    }
}
