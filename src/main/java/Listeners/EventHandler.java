package Listeners;

import Displays.Display;

import GameMechanics.GameMechanics;
import GameMechanics.GameObjects;
import Main.GameState;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public abstract class EventHandler implements KeyListener, ActionListener {

    //keylistener when escape is pressed
    public static KeyListener attackOptionsKeyListener(Display display) {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    //go back to previous panel in options deck

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
    }

    //Listener for the main menu
    public static ActionListener mainMenuListener(JButton[] buttons, Display display) {
        return click -> {
            if (click.getSource() == buttons[0]) {
                //new game -> go to character creation
                GameState.currentState = GameState.State.CHARACTER_STATE;
            } else if (click.getSource() == buttons[1]) {
                //load game -> go to saved games
                GameState.currentState = GameState.State.SAVED_STATE;
            } else if (click.getSource() == buttons[2]) {
                //options
                GameState.currentState = GameState.State.OPTIONS_STATE;
            } else if (click.getSource() == buttons[3]) {
                //make popup Are you sure you want to quit
                final int choice = JOptionPane.showConfirmDialog(display, "Are you sure you want to quit?");
                if (choice == JOptionPane.YES_OPTION) {
                    display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.exit(0);
                }
            }
            GameState.gameStateUpdate(display);
        };
    }

    //Listener for the character panel
    public static ActionListener characterPanelListener() {
        return action -> {
            //checks the radiobuttons
            switch (action.getActionCommand()) {
                case "Warrior":
                    GameObjects.player = GameObjects.CLASSES.get(0);
                    break;
                case "Wizard":
                    GameObjects.player = GameObjects.CLASSES.get(1);
                    break;
                case "Rogue":
                    GameObjects.player = GameObjects.CLASSES.get(2);
                    break;
                case "Cleric":
                    GameObjects.player = GameObjects.CLASSES.get(3);
                    break;
            }
        };
    }

    public static ActionListener characterReadyListener(JRadioButton[] buttons, JButton button, JTextField textField, Display display, GameMechanics gameMechanics) {
        return action -> {
            //listener for the ready button
            if (action.getSource() == button) {
                //check if player has chosen a class and name, if not ask to do so
                if (!(textField.getText().isEmpty()) && Arrays.stream(buttons).anyMatch(AbstractButton::isSelected)) {
                    GameObjects.player.setName(textField.getText());
                    synchronized (gameMechanics.getLock()) {
                        //let the gamemechanics thread know it can continue
                        gameMechanics.getLock().notifyAll();
                    }
                    //switch to ingame state
                    GameState.currentState = GameState.State.INGAME_STATE;

                } else if (textField.getText().isEmpty()) {
                    //message when the player hasn't entered a name
                    JOptionPane.showMessageDialog(display.getCharacterPanel(), "Please enter your name");
                } else if (Arrays.stream(buttons).noneMatch(AbstractButton::isSelected)) {
                    //message when there is no class selected
                    JOptionPane.showMessageDialog(display.getCharacterPanel(), "Please choose a class");
                }
            }
            GameState.gameStateUpdate(display);
        };
    }

    //Listener for the gameOptions panel
    public static ActionListener gameOptionsListener(JButton[] buttons, Display display, GameMechanics gameMechanics) {
        return click -> {
            if (click.getSource() == buttons[0]) {
                //only take action when it's the player's turn
                if (GameObjects.player.isTurn()) {
                    //attack
                    display.getCurrentOption().show(display.getAttackOptions().getParent(), "attackOptions");
                    Display.MESSAGE_BOX.setText("What do you want to use? Press escape to go back");
                } else {
                    Display.MESSAGE_BOX.setText("It's not your turn");
                }
            } else if (click.getSource() == buttons[1]) {
                System.out.println("lmao");
            } else if (click.getSource() == buttons[2]) {
                System.out.println("rofl");
            } else if (click.getSource() == buttons[3]) {
                System.out.println("yep");
            }
        };
    }

    //Listener for the attackOptions "card" in the Options deck
    public static ActionListener attackOptionsListener(JButton[] buttons, Display display, GameMechanics gameMechanics) {
        return click -> {
            synchronized (gameMechanics.getPlayerTurn()) {
                gameMechanics.getPlayerTurn().notify();
            }
            if (click.getSource() == buttons[0]) {
                //attack 1
                //execute attack here
                GameObjects.player.setActionDone(true);
                GameObjects.player.setTurn(false);
            } else if (click.getSource() == buttons[1]) {
                System.out.println("attack2");
            } else if (click.getSource() == buttons[2]) {
                System.out.println("attack3");
            } else if (click.getSource() == buttons[3]) {
                System.out.println("attack4");
            }
            //switch back to the main options
            display.getCurrentOption().previous(display.getOptionDeck());
        };
    }


}


