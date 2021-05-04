import Displays.Display;
import GameMechanics.*;
import Menus.MenuService;

import java.awt.*;

public class MainGame {

    private final Display display;
    public static boolean done = false;

    //TODO make sure all threads are synchronized
    //TODO for save/load look into java.io.Serializable

    public MainGame(){
        initGame();
        GameMechanics gameMechanics = new GameMechanics();
        display = new Display(gameMechanics);
        gameMechanics.setGameState(display.getGameState());
    }

    private void initGame(){
        MenuService.menuInitializer();
        GameObjects.initializeGameObjects();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() ->{
            final MainGame mainGame = new MainGame();

        });

    }


}
