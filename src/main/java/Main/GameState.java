package Main;

import Displays.Display;

public class GameState {

    public enum State {
        MENU_STATE,
        OPTIONS_STATE,
        CHARACTER_STATE,
        SAVED_STATE,
        INGAME_STATE,
        REWARDS_STATE,
        GAME_OVER

    }

    private Display display;
    private State currentState = State.MENU_STATE;

    public GameState(Display display){
        this.display = display;
    }
    public void gameStateUpdate() {
        switch (currentState) {
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
                break;
            case CHARACTER_STATE:
                display.getCurrentPanel().show(display.getCharacterPanel().getParent(), "character");
                break;
            case GAME_OVER:

        }
    }

    public State getCurrentState() {
        return currentState;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }
}