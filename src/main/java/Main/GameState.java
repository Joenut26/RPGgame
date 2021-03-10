package Main;

import Displays.Display;
import GameMechanics.*;

public class GameState {

    public enum State{
        MENU_STATE,
        OPTIONS_STATE,
        CHARACTER_STATE,
        SAVED_STATE,
        INGAME_STATE,
        REWARDS_STATE
    }

    public static State currentState = State.MENU_STATE;
    private final GameMechanics gameMechanics = new GameMechanics();
    private final Display display = new Display(gameMechanics);

    public void gameStateUpdate(){
        switch (currentState){
            case MENU_STATE:
                display.getCurrentPanel().show(display.getMainMenu().getParent(), "mainMenu");
                break;
            case SAVED_STATE:
                display.getCurrentPanel().show(display.getSavedGamePanel().getParent(), "savedGame");
            case OPTIONS_STATE:
                display.getCurrentPanel().show(display.getOptionMenu().getParent(), "options");
                break;
            case INGAME_STATE:
                display.getCurrentPanel().show(display.getMainPanel().getParent(), "mainPanel");
                //call the mainGame method because this is where we start combining display and logic
                gameMechanics.runGame(display);
                break;
            case CHARACTER_STATE:
                display.getCurrentPanel().show(display.getCharacterPanel().getParent(), "character");
                break;
        }
    }

    public GameMechanics getGameMechanics(){
        return gameMechanics;
    }
}
