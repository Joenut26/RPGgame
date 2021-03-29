package GameMechanics;

import Classes.*;
import Main.Tools;
import NPCs.Goblin;
import NPCs.NPC;
import NPCs.Troll;

import java.awt.*;
import java.util.ArrayList;

public class GameObjects {

    public static Player player = new Player();
    public static final ArrayList<Floor> FLOORS = new ArrayList<>();
    public static final ArrayList<Player> CLASSES = new ArrayList<>();
    public static final ArrayList<NPC> NPC_LIST = new ArrayList<>();
    public static final ArrayList<Image> SKELETON_ATTACK = new ArrayList<>();
    public static final ArrayList<Image> GOBLIN_ATTACK = new ArrayList<>();
    public static final ArrayList<Image> WARRIOR_IDLE = new ArrayList<>();
    public static final ArrayList<Image> WARRIOR_RUNNING = new ArrayList<>();
    public static final ArrayList<Image> WARRIOR_ATTACK = new ArrayList<>();

    public static void initializeGameObjects(){
        initializeClasses();
        initializeNPCs();
        initializeImages();
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

    private static void initializeImages(){
        //loads images and collects them in IMAGES
        WARRIOR_IDLE.addAll(Tools.addImageToList("src/main/resources/Final Assets/Knight/idleknight"));
        WARRIOR_RUNNING.addAll(Tools.addImageToList("src/main/resources/Final Assets/Knight/runningknight"));
        WARRIOR_ATTACK.addAll(Tools.addImageToList("src/main/resources/Final Assets/Knight/attackknight"));
        SKELETON_ATTACK.addAll(Tools.addImageToList("src/main/resources/Final Assets/Skeleton"));
        GOBLIN_ATTACK.addAll(Tools.addImageToList("src/main/resources/Final Assets/Goblin"));

    }

}
