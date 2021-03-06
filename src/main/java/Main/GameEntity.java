package Main;

import java.awt.*;

public class GameEntity {
    protected String name;
    protected int level;

    protected String id;

    public void setHitPoints(double hitPoints) {
        this.hitPoints = hitPoints;
    }

    //max hp
    protected double hitPoints;
    //current hp
    protected double currentHp;

    protected int intelligence;
    protected int strength;
    protected int dexterity;
    protected int stamina;
    protected int hit;
    protected int initiative;

    protected Image entityImage;
    protected Image entityIcon;
    protected String state = "idle";

    protected boolean dead = false;
    protected boolean actionDone = false;
    protected boolean turn;
    protected double positionX;
    protected double positionY;

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isActionDone() {
        return this.actionDone;
    }

    public void setActionDone(final boolean actionDone) {
        this.actionDone = actionDone;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Image getEntityImage() {
        return this.entityImage;
    }

    public void setEntityImage(final Image entityImage) {
        this.entityImage = entityImage;
    }

    public Image getEntityIcon() {
        return this.entityIcon;
    }

    public void setEntityIcon(final Image entityIcon) {
        this.entityIcon = entityIcon;
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

    public double getCurrentHp() {
        return this.currentHp;
    }

    public void setCurrentHp(final double currentHp) {
        this.currentHp = currentHp;
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

    public int getInitiative() {
        return this.initiative;
    }

    public void setInitiative(final int initiative) {
        this.initiative = initiative;
    }

    public String getState() {
        return this.state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

}
