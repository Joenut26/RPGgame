package Classes;

import java.awt.*;

public class Player {

    protected String playerClass;
    protected String name;
    protected int level;
    //max hp
    protected double hitPoints;
    protected double baseHp;
    //current hp
    protected double currentHp;
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
    protected int intelligence;
    protected int strength;
    protected int dexterity;
    protected int stamina;
    protected int hit;
    protected double experience;
    protected double xpToLevelUp = 100;
    protected int initiative;
    protected boolean turn;
    protected boolean actionDone;
    protected Image playerIcon;
    
    public Player() {

    }

    // Get & set fields

    public void setPlayerIcon(final Image playerIcon){
        this.playerIcon = playerIcon;
    }

    public Image getPlayerIcon(){
        return this.playerIcon;
    }

    public void setBaseHp(final double baseHp) {
        this.baseHp = baseHp;
    }

    public double getBaseHp() {
        return this.baseHp;
    }

    public boolean isAction() {
        return actionDone;
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

    public double getExperience() {
        return this.experience;
    }

    public void setExperience(final double experience) {
        this.experience = experience;
    }

    public double getInitiative() {
        return this.initiative;
    }

    public void setInitiative(final int initiative) {
        this.initiative = initiative;
    }

    public double getXpToLevelUp() {
        return this.xpToLevelUp;
    }

    public void setXpToLevelUp(final double xpToLevelUp) {
        this.xpToLevelUp = xpToLevelUp;
    }

}
