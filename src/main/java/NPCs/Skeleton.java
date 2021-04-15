package NPCs;

import Main.Tools;

public class Skeleton extends NPC{

    public Skeleton() {

        this.id = "Skeleton";
        this.name = "Skeleton";
        this.level = 1;
        this.baseHp = 50;
        this.hitPoints = level * (baseHp + stamina);
        this.intelligence = 1;
        this.strength = 6;
        this.dexterity = 2;
        this.stamina = 4;
        this.hit = 50;
        this.xpOnKill = 40;
        this.state = "idle";
        this.entityImage = Tools.requestImage("src/main/resources/Final Assets/Skeleton/idle/idle (1).png");
        this.entityIcon = Tools.requestImage("src/main/resources/Final Assets/Skeleton/idle/idle (1).png");
    }
}
