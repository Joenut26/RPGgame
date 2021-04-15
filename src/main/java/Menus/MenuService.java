package Menus;

import java.util.ArrayList;

public class MenuService {

    public final static ArrayList<String> mainMenuOptionsList = new ArrayList<>();
    public final static ArrayList<String> generalOptionsList = new ArrayList<>();
    public final static ArrayList<String> classesList = new ArrayList<>();
    public final static ArrayList<String> gameOptionsList = new ArrayList<>();
    public final static ArrayList<String> warriorAttacks = new ArrayList<>();
    public final static ArrayList<String> wizardAttacks = new ArrayList<>();
    public final static ArrayList<String> rogueAttacks = new ArrayList<>();
    public final static ArrayList<String> clericAttacks = new ArrayList<>();


    public static void menuInitializer() {
        setMainMenuOptionsList();
        setGeneralOptionsList();
        setClassesList();
        setGameOptionsList();
        setWarriorAttacks();
        setWizardAttacks();
        setRogueAttacks();
        setClericAttacks();
    }

    private static void setGameOptionsList() {
        gameOptionsList.add("Attack");
        gameOptionsList.add("Inventory");
        gameOptionsList.add("Character");
        gameOptionsList.add("Options");
    }

    private static void setMainMenuOptionsList() {
        mainMenuOptionsList.add("New Game");
        mainMenuOptionsList.add("Load Game");
        mainMenuOptionsList.add("Options");
        mainMenuOptionsList.add("Exit");
    }

    private static void setGeneralOptionsList() {
        generalOptionsList.add("Background color");
        generalOptionsList.add("Special background");
    }

    private static void setClassesList() {
        classesList.add("Warrior");
        classesList.add("Rogue");
        classesList.add("Mage");
        classesList.add("Druid");
    }

    private static void setWarriorAttacks(){
        warriorAttacks.add("Auto Attack");
        warriorAttacks.add("Reckless Strike");
        warriorAttacks.add("Dragon Swing");
        warriorAttacks.add("Devastating Blow");

    }

    private static void setWizardAttacks(){
        wizardAttacks.add("Auto attack");
        wizardAttacks.add("Fireball");
        wizardAttacks.add("Ice Nova");
        wizardAttacks.add("Wind Shear");

    }

    private static void setRogueAttacks(){
        rogueAttacks.add("Auto attack");
        rogueAttacks.add("Backstab");
        rogueAttacks.add("Ambush");
        rogueAttacks.add("Eviscerate");

    }

    private static void setClericAttacks(){
        clericAttacks.add("Auto attack");
        clericAttacks.add("Healing Surge");
        clericAttacks.add("Storm Blow");
        clericAttacks.add("Lightning Spear");

    }
}

