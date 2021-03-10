package Classes;



public class Rogue extends Player {

    public Rogue() {

        playerClass = "Rogue";
        resourceType = "Combo Points";
        resource = 5;
        intelligence = 5;
        strength = 5;
        dexterity = 10;
        stamina = 10;
        hit = 0;
        baseHp = 80;
        hitPoints = baseHp + 5 * stamina;
        currentHp = hitPoints;
        currentResource = 0;

    }

}