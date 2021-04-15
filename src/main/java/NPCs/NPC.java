package NPCs;

import Animations.EnemyAnimation;
import Main.GameEntity;

import java.awt.*;

public class NPC extends GameEntity {
    protected String id = "NPC";
    protected double currentMana;
    protected double mana;
    protected double xpOnKill;
    protected boolean turn;
    protected int baseHp;
    protected boolean targeted;
    protected boolean actionDone = false;
    protected EnemyAnimation animation;


    public NPC() {

    }
    // Get & set fields

    public boolean isActionDone() {
        return actionDone;
    }

    public void setActionDone(boolean actionDone) {
        this.actionDone = actionDone;
    }

    public EnemyAnimation getAnimation() {
        return animation;
    }

    public void setAnimation(EnemyAnimation animation) {
        this.animation = animation;
    }

    public void setTargeted(final boolean targeted) {
        this.targeted = targeted;
    }

    public boolean isTargeted() {
        return this.targeted;
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
