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
        this.monsterImage = Tools.requestImage("src/main/resources/Goblin.png");
        this.monsterIcon = Tools.requestImage("src/main/resources/flipW.jpg");
    }

}