package Classes;

import Main.GameEntity;

import java.awt.*;

public class Player extends GameEntity {

    protected String playerClass;
    protected double baseHp;
    protected String resourceType;
    //max resource
    protected double resource;
    //current resource
    protected double currentResource;
    protected String specialResourceName;
    //max special resource
    protected double specialResource;
    //current special resource
    protected double currentSpecialResource;
    protected double experience;
    protected double xpToLevelUp = 100;
    protected boolean turn;
    protected boolean actionDone;



    public Player() {

    }

    // Get & set fields

       public void setBaseHp(final double baseHp) {
        this.baseHp = baseHp;
    }

    public double getBaseHp() {
        return this.baseHp;
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

    public void setTurn(final boolean turn) {
        this.turn = turn;
    }

    public String getPlayerClass() {
        return this.playerClass;
    }

    public void setPlayerClass(final String playerClass) {
        this.playerClass = playerClass;
    }


    public String getResourceType() {
        return this.resourceType;
    }

    public void setResourceType(final String resourceType) {
        this.resourceType = resourceType;
    }

    public double getResource() {
        return this.resource;
    }

    public void setResource(final double resource) {
        this.resource = resource;
    }

    public void setCurrentResource(final double currentResource) {
        this.currentResource = currentResource;
    }

    public double getCurrentResource() {
        return this.currentResource;
    }

    public void setSpecialResourceName(final String specialResourceName){ this.specialResourceName = specialResourceName;}

    public String getSpecialResourceName(){ return this.specialResourceName;}

    public void setCurrentSpecialResource(final double currentSpecialResource){ this.currentSpecialResource = currentSpecialResource;}

    public double getCurrentSpecialResource(){return this.currentSpecialResource;}

    public double getSpecialResource() {
        return this.specialResource;
    }

    public void setSpecialResource(final double specialResource) {
        this.specialResource = specialResource;
    }

    public double getExperience() {
        return this.experience;
    }

    public void setExperience(final double experience) {
        this.experience = experience;
    }

    public double getXpToLevelUp() {
        return this.xpToLevelUp;
    }

    public void setXpToLevelUp(final double xpToLevelUp) {
        this.xpToLevelUp = xpToLevelUp;
    }

}
