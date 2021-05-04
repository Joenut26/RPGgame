package Listeners;

import Displays.Display;

import GameMechanics.GameMechanics;
import GameMechanics.GameObjects;
import Main.GameState;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;


public abstract class EventHandler implements KeyListener, ActionListener, ListSelectionListener {

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

    //Listener for the targetList
    public static ListSelectionListener targetListener(GameMechanics gameMechanics, JList jList, Display display) {
        return selection -> {
            if (selection.getValueIsAdjusting()) {
                var selected = jList.getSelectedIndex();
                //set everything to false to make sure the marker disappears when updating selection
                gameMechanics.getEnemies().forEach(enemy -> enemy.setTargeted(false));
                gameMechanics.getEnemies().get(selected).setTargeted(true);
                gameMechanics.setTarget(gameMechanics.getEnemies().get(selected));
            }

        };
    }

    //listener for the attacks list
    public static ListSelectionListener attackListener(JList jlist, GameMechanics gameMechanics, Display display){
        return selection -> {
            if(selection.getValueIsAdjusting()) {
                var selected = jlist.getSelectedIndex();
                var abilityID = display.getClassAbilities().get(selected);
                gameMechanics.setAbilityChoice(abilityID);
            }
        };
    }

    //back button for the option deck
    public static ActionListener backButton(Display display) {
        return action -> display.getCurrentOption().show(display.getGameOptions().getParent(), "gameOptions");
    }

    //go button for the option deck
    public static ActionListener goButton(Display display) {
        return action -> display.getCurrentOption().show(display.getAttackOptions().getParent(), "attackOptions");
    }

    //back button for attacks
    public static ActionListener backButtonAttacks(Display display) {
        return action -> display.getCurrentOption().show(display.getTargetPanel().getParent(), "targets");
    }

    //go button for attacks
    public static ActionListener goButtonAttacks(Display display) {
        return action -> {
            display.getCurrentOption().show(display.getGameOptions().getParent(), "gameOptions");
            synchronized (display.getGameMechanics().getPlayerTurn()) {
               display.getGameMechanics().getPlayerTurn().notify();
            }
            GameObjects.player.setState("running");
        };
    }

    //Listener for the main menu
    public static ActionListener mainMenuListener(JButton[] buttons, Display display) {
        return click -> {
            if (click.getSource() == buttons[0]) {
                //new game -> go to character creation
                display.getGameState().setCurrentState(GameState.State.CHARACTER_STATE);
            } else if (click.getSource() == buttons[1]) {
                //load game -> go to saved games
                display.getGameState().setCurrentState(GameState.State.SAVED_STATE);
            } else if (click.getSource() == buttons[2]) {
                //options
                display.getGameState().setCurrentState(GameState.State.OPTIONS_STATE);
            } else if (click.getSource() == buttons[3]) {
                //make popup Are you sure you want to quit
                final int choice = JOptionPane.showConfirmDialog(display, "Are you sure you want to quit?");
                if (choice == JOptionPane.YES_OPTION) {
                    display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    System.exit(0);
                }
            }
            display.getGameState().gameStateUpdate();
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
                    display.getGameState().setCurrentState(GameState.State.INGAME_STATE);

                } else if (textField.getText().isEmpty()) {
                    //message when the player hasn't entered a name
                    JOptionPane.showMessageDialog(display.getCharacterPanel(), "Please enter your name");
                } else if (Arrays.stream(buttons).noneMatch(AbstractButton::isSelected)) {
                    //message when there is no class selected
                    JOptionPane.showMessageDialog(display.getCharacterPanel(), "Please choose a class");
                }
            }
            display.getGameState().gameStateUpdate();

        };
    }

    //Listener for the gameOptions panel
    public static ActionListener gameOptionsListener(JButton[] buttons, Display display, GameMechanics gameMechanics) {
        return click -> {
            if (click.getSource() == buttons[0]) {
                //only take action when it's the player's turn
                if (GameObjects.player.isTurn()) {
                    //attack
                    display.updateTargets();
                    display.updateAttackList();
                    display.getCurrentOption().show(display.getTargetPanel().getParent(), "targets");

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

    public static ActionListener gameOverListener(JButton button, Display display, GameMechanics gameMechanics){
        return click -> {
            if(click.getSource() == button){
                display.getGameState().setCurrentState(GameState.State.MENU_STATE);
                display.getGameState().gameStateUpdate();
            }
        };
    }


}


