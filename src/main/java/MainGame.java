import Displays.Display;
import GameMechanics.*;
import Main.GameState;
import Menus.MenuService;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.Serializable;

public class MainGame {

    private final Display display;
    public static boolean done = false;

    //TODO make sure all threads are synchronized
    //TODO for save/load look into java.io.Serializable

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
