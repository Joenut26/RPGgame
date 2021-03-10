package Classes;

public class Wizard extends Player {

    public Wizard() {

        playerClass = "Wizard";
        resourceType = "Mana";
        final int baseMana = 100;
        resource = Math.ceil(baseMana + 0.75 * intelligence);
        intelligence = 10;
        strength = 5;
        dexterity = 5;
        stamina = 10;
        hit = 0;
        final int baseHp = 70;
        hitPoints = baseHp + 5 * stamina;
        currentHp = hitPoints;
        currentResource = resource;
    }
}
