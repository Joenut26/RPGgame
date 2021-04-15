package GameMechanics;

import Abilities.Ability;
import Classes.*;
import Main.Tools;
import NPCs.Goblin;
import NPCs.NPC;
import NPCs.Skeleton;
import NPCs.Troll;

import javax.tools.Tool;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GameObjects {

    public static Player player = new Player();
    public static final ArrayList<Floor> FLOORS = new ArrayList<>();
    public static final ArrayList<Player> CLASSES = new ArrayList<>();
    public static final ArrayList<NPC> NPC_LIST = new ArrayList<>();
    public static final ArrayList<Image> SKELETON_ATTACK = new ArrayList<>();
    public static final ArrayList<Image> SKELETON_IDLE = new ArrayList<>();
    public static final ArrayList<Image> SKELETON_DEATH= new ArrayList<>();
    public static final ArrayList<Image> SKELETON_SHIELD = new ArrayList<>();
    public static final ArrayList<Image> SKELETON_WALK = new ArrayList<>();
    public static final ArrayList<Image> SKELETON_GETHIT = new ArrayList<>();
    public static final ArrayList<Image> GOBLIN_ATTACK = new ArrayList<>();
    public static final ArrayList<Image> GOBLIN_IDLE = new ArrayList<>();
    public static final ArrayList<Image> GOBLIN_WALK = new ArrayList<>();
    public static final ArrayList<Image> WARRIOR_IDLE = new ArrayList<>();
    public static final ArrayList<Image> WARRIOR_RUNNING = new ArrayList<>();
    public static final ArrayList<Image> WARRIOR_ATTACK = new ArrayList<>();
    public static final ArrayList<Image> WARRIOR_GETHIT = new ArrayList<>();
    public static final ArrayList<Ability> ABILITY_DATABASE = new ArrayList<>();

    public static void initializeGameObjects() {
        initializeClasses();
        initializeNPCs();
        initializeImages();
        initializeAbilityDatabase();
    }

    private static void initializeClasses() {
        CLASSES.add(new Warrior());
        CLASSES.add(new Wizard());
        CLASSES.add(new Rogue());
        CLASSES.add(new Cleric());
    }

    private static void initializeNPCs() {
        NPC_LIST.add(new Goblin());
        NPC_LIST.add(new Skeleton());
    }

    private static void initializeImages() {
        //loads images for animations and collects them in IMAGES
        WARRIOR_IDLE.addAll(Tools.addImageToList("src/main/resources/Final Assets/Warrior/Idle"));
        WARRIOR_RUNNING.addAll(Tools.addImageToList("src/main/resources/Final Assets/Warrior/run"));
        WARRIOR_ATTACK.addAll(Tools.addImageToList("src/main/resources/Final Assets/Warrior/attack"));
        WARRIOR_GETHIT.addAll(Tools.addImageToList("src/main/resources/Final Assets/Warrior/getHit"));
        SKELETON_IDLE.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Skeleton/idle"));
        SKELETON_ATTACK.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Skeleton/attack"));
        SKELETON_SHIELD.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Skeleton/shield"));
        SKELETON_WALK.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Skeleton/walk"));
        SKELETON_DEATH.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Skeleton/death"));
        SKELETON_GETHIT.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Skeleton/getHit"));
        GOBLIN_ATTACK.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Goblin/bomb"));
        GOBLIN_IDLE.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Goblin/idle"));
        GOBLIN_WALK.addAll(Tools.addImageToListReverse("src/main/resources/Final Assets/Goblin/walk"));
    }

    private static void initializeAbilityDatabase() {
        ABILITY_DATABASE.add(new Ability("Auto Attack", "Auto Attack", "", "physical",0,10,false,false));
        ABILITY_DATABASE.add(new Ability("Reckless Strike", "Reckless Strike", "Rage", "physical", 5, 20, false, false));
        ABILITY_DATABASE.add(new Ability("Dragon Swing", "Dragon Swing", "Rage", "fire", 10, 30, false, false));
        ABILITY_DATABASE.add(new Ability("Devastating Blow", "Devastating Blow", "Rage", "physical", 15, 15, true, false));
        ABILITY_DATABASE.add(new Ability("Fireball", "Fireball", "Mana", "fire", 10, 40, false, false));
        ABILITY_DATABASE.add(new Ability("Ice Nova", "Ice Nova", "Mana", "water", 15, 20, true, false));
        ABILITY_DATABASE.add(new Ability("Wind Shear", "Wind Shear", "Mana", "air", 10, 40, false, false));
        ABILITY_DATABASE.add(new Ability("Backstab", "Backstab", "Combo Points", "physical", -1, 25, false, false));
        ABILITY_DATABASE.add(new Ability("Ambush", "Ambush", "Combo Points", "physical", -2, 50, false, false));
        ABILITY_DATABASE.add(new Ability("Eviscerate", "Eviscerate", "Combo Points", "physical", 1, 15, false, false));
        ABILITY_DATABASE.add(new Ability("Healing Surge", "Healing Surge", "Mana", "holy", 20, 40, false, false));
        ABILITY_DATABASE.add(new Ability("Storm Blow", "Storm Blow", "Mana", "air", 15, 30, true, false));
        ABILITY_DATABASE.add(new Ability("Lightning Spear", "Lightning Spear", "Mana", "lightning", 25, 50, false, false));

    }

}
