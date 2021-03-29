package NPCs;

import Main.Tools;

public class Troll extends NPC {

    public Troll() {

        this.id = "Troll";
        this.name = "Troll";
        this.level = 1;
        this.baseHp = 40;
        this.hitPoints = level * (baseHp + stamina);
        this.intelligence = 1;
        this.strength = 5;
        this.dexterity = 2;
        this.stamina = 4;
        this.hit = 50;
        this.xpOnKill = 30;
        this.state = "idle";
        this.entityImage = Tools.requestImage("src/main/resources/troll.png");
        this.entityIcon = Tools.requestImage("src/main/resources/troll.png");
    }
}
