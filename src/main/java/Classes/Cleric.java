package Classes;

public class Cleric extends Player {

    public Cleric() {

        playerClass = "Cleric";
        resourceType = "Mana";
        final int baseMana = 120;
        resource = Math.ceil(baseMana + 0.75 * intelligence);
        intelligence = 8;
        strength = 7;
        dexterity = 5;
        stamina = 10;
        hit = 0;
        final int baseHp = 90;
        hitPoints = baseHp + 5 * stamina;
        currentHp = hitPoints;
    }
}
