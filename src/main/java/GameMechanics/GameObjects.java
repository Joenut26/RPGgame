package GameMechanics;

import Classes.*;
import NPCs.Goblin;
import NPCs.NPC;
import NPCs.Troll;


import java.util.ArrayList;

public class GameObjects {

    public static Player player = new Player();
    public static final ArrayList<Floor> FLOORS = new ArrayList<>();
    public static final ArrayList<Player> CLASSES = new ArrayList<>();
    public static final ArrayList<NPC> NPC_LIST = new ArrayList<>();

    public static void initializeGameObjects(){
        initializeClasses();
        initializeNPCs();
    }

    private static void initializeClasses(){
        CLASSES.add(new Warrior());
        CLASSES.add(new Wizard());
        CLASSES.add(new Rogue());
        CLASSES.add(new Cleric());
    }

    private static void initializeNPCs(){
        NPC_LIST.add(new Goblin());
        NPC_LIST.add(new Troll());
    }
}
