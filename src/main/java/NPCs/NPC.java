package NPCs;

import Main.GameEntity;

import java.awt.*;

public class NPC extends GameEntity {
    protected String id = "NPC";
    protected double currentMana;
    protected double mana;
    protected double xpOnKill;
    protected boolean turn;
    protected int baseHp;
    protected double positionX;
    protected double PositionY;
    protected boolean targeted;


    public NPC() {

    }
    // Get & set fields


    public void setTargeted(final boolean targeted) {
        this.targeted = targeted;
    }

    public boolean isTargeted() {
        return this.targeted;
    }

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

    public double getExperienceOnKill() {
        return this.xpOnKill;
    }

    public void setExperienceOnKill(final double xpOnKill) {
        this.xpOnKill = xpOnKill;
    }

    // Overrides
    @Override
    public String toString() {
        return this.name;
    }
}
