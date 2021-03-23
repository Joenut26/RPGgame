import Displays.Display;
import GameMechanics.*;
import Main.GameState;
import Menus.MenuService;

import javax.swing.*;
import java.awt.*;

public class MainGame {

    private final Display display;
    public static boolean done = false;

    //TODO make sure all threads are synchronized

    public MainGame(){
        initGame();
        GameMechanics gameMechanics = new GameMechanics();
        display = new Display(gameMechanics);

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
