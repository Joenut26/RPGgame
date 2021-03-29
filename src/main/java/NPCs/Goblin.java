package NPCs;

import Main.Tools;

public class Goblin extends NPC {

    public Goblin() {

        this.id = "Goblin";
        this.name = "Goblin";
        this.level = 1;
        this.baseHp = 30;
        this.hitPoints = level * (baseHp + stamina);
        this.intelligence = 1;
        this.strength = 5;
        this.dexterity = 3;
        this.stamina = 3;
        this.hit = 50;
        this.xpOnKill = 20;
        this.state = "idle";
        this.entityImage = Tools.requestImage("src/main/resources/Final Assets/Goblin/goblinbomb (5).png");
        this.entityIcon = Tools.requestImage("src/main/resources/Final Assets/Goblin/goblinbomb (5).png");
    }

}