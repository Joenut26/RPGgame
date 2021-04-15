package Abilities;

public class Ability {

    //id placeholder for description
    protected String id;
    protected String name;
    protected String resource;
    protected String damageType;
    protected int cost;
    protected int baseDamage;
    protected boolean areaOfEffect;
    protected boolean selfCast;

    public Ability(){

    }

    public Ability(final String id, final String name, final String resource, final String damageType, final int cost, final int baseDamage, final boolean areaOfEffect, final boolean selfCast){
        this.id = id;
        this.name = name;
        this.resource = resource;
        this.damageType = damageType;
        this.cost = cost;
        this.baseDamage = baseDamage;
        this.areaOfEffect = areaOfEffect;
        this.selfCast = selfCast;
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

    public String getResource() {
        return this.resource;
    }

    public void setResource(final String resource) {
        this.resource = resource;
    }

    public String getDamageType() {
        return this.damageType;
    }

    public void setDamageType(final String damageType) {
        this.damageType = damageType;
    }

    public int getCost() {
        return this.cost;
    }

    public void setCost(final int cost) {
        this.cost = cost;
    }

    public int getBaseDamage() {
        return this.baseDamage;
    }

    public void setBaseDamage(final int baseDamage) {
        this.baseDamage = baseDamage;
    }

    public boolean isAreaOfEffect() {
        return this.areaOfEffect;
    }

    public void setAreaOfEffect(final boolean areaOfEffect) {
        this.areaOfEffect = areaOfEffect;
    }

    public boolean isSelfCast() {
        return this.selfCast;
    }

    public void setSelfCast(final boolean selfCast) {
        this.selfCast = selfCast;
    }
}
