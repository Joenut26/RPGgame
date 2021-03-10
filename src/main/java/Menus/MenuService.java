package Menus;

import java.util.ArrayList;

public class MenuService {

    public final static ArrayList<String> mainMenuOptionsList = new ArrayList<>();
    public final static ArrayList<String> generalOptionsList = new ArrayList<>();
    public final static ArrayList<String> classesList = new ArrayList<>();
    public final static ArrayList<String> gameOptionsList = new ArrayList<>();
    public final static ArrayList<String> warriorAttacks = new ArrayList<>();

    public static void menuInitializer() {
        setMainMenuOptionsList();
        setGeneralOptionsList();
        setClassesList();
        setGameOptionsList();
        setWarriorAttacks();
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
        warriorAttacks.add("Auto attack (+5 rage)");
        warriorAttacks.add("Reckless Strike (-10 rage)");
        warriorAttacks.add("Dragon Swing (-10 rage)");
        warriorAttacks.add("Devastating Blow (- 20 rage)");
    }
}

