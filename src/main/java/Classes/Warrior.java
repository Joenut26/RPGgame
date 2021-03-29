package Classes;

import Main.Tools;

public class Warrior extends Player {

    public Warrior() {

        playerClass = "Warrior";
        resourceType = "Rage";
        resource = 100;
        intelligence = 5;
        strength = 10;
        dexterity = 5;
        stamina = 10;
        hit = 0;
        baseHp = 150;
        hitPoints = baseHp + 5 * stamina;
        currentHp = hitPoints;
        entityIcon = Tools.requestImage("src/main/resources/flipW.jpg");
    }

}
