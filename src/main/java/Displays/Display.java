package Displays;

import GameMechanics.GameMechanics;
import GameMechanics.GameObjects;
import Listeners.EventHandler;
import Main.Tools;
import Menus.MenuService;
import NPCs.NPC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class Display extends JFrame {

    //Initialize gameMechanics
    private final GameMechanics gameMechanics;
    //mainframe as top level container
    private final JFrame mainFrame = new JFrame("RPG");
    //set cardlayout which shows one of the panels from the deck
    private final CardLayout currentPanel = new CardLayout();
    //create deck for the panels
    private final JPanel panelDeck = new JPanel(currentPanel) {
        //overwrite paintComponent to draw the background
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    };
    private Image image = Tools.requestImage("src/main/resources/BigBG.jpg");

    //Define Panels that can be added to panelDeck
    private final JPanel mainMenu = new JPanel();
    private final JPanel optionMenu = new JPanel();
    private final JPanel characterPanel = new JPanel();
    private final JPanel mainPanel = new JPanel();
    private final JPanel savedGamePanel = new JPanel();

    //Define textarea to display messages
    public static final JTextArea MESSAGE_BOX = new JTextArea();

    //Define player image
    public static Image playerImage = Tools.requestImage("src/main/resources/idle.png");
    //Define monster images
    public static Image goblinImage = Tools.requestImage("src/main/resources/Goblin.png");
    public static Image trollImage = Tools.requestImage("src/main/resources/KEKW.png");

    private final ArrayList<Rectangle> hitBoxList = new ArrayList<>();
    //Define main canvas

    //TODO make new map of initiative and make new method to put first element in the last place
    private final JPanel canvas = new JPanel() {
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            graphics.drawImage(canvasBackground, 0, 0, this.getWidth(), this.getHeight(), this);
            //all the drawing will be done here
            //draw the player icon, health and resource bars
            Font font = new Font("Lucida Console", Font.PLAIN, 12);

            //set the size and position of the sprites relative to screensize
            int spriteX = (int) (this.getWidth() * 0.1);
            int spriteY = (int) (this.getHeight() * 0.65);
            int spriteWidth = (int) (this.getWidth() * 0.2);
            int spriteHeigth = (int) (this.getHeight() * 0.3);

            //draws the player character
            graphics.drawImage(playerImage, spriteX, spriteY, spriteWidth, spriteHeigth, this);

            //draw enemies
            for (int i = 0; i < gameMechanics.getEnemies().size(); i++) {
                int enemyX = (int) (this.getWidth() * (0.6 + i * 0.15));
                int enemyY = (int) (this.getHeight() * 0.65);
                hitBoxList.add(new Rectangle(enemyX, enemyY, spriteWidth, spriteHeigth));
                graphics.drawImage(gameMechanics.getEnemies().get(i).getMonsterImage(), enemyX, enemyY, spriteWidth, spriteHeigth, this);
            }

            int iconSquare = (int) (0.04 * this.getWidth());
            ArrayList<Rectangle> icons = new ArrayList<>();
            //draw icons in order of turns
            for (int i = 0; i < 3; i++) {
                icons.add(new Rectangle((int) (this.getWidth() / 2 - (1.5 - i) * iconSquare), (int) (0.1 * this.getHeight()), iconSquare, iconSquare));
                if (gameMechanics.getInitiativeMap().size() > 0) {
                    graphics.drawImage(gameMechanics.getInitiativeMap().get(i).getMonsterIcon(), icons.get(i).x, icons.get(i).y, icons.get(i).width, icons.get(i).height, this);
                }

            }
        }

    };

    private Image canvasBackground = Tools.requestImage("src/main/resources/greekbg.png");

    //Layout to switch between options in the mainPanel
    private final CardLayout currentOption = new CardLayout();
    private final JPanel optionDeck = new JPanel(currentOption);
    private final JPanel gameOptions = new JPanel();
    private final JPanel attackOptions = new JPanel();


    public Display(GameMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
        mainMenu();
        createCharacter();
        setUpMainPanel();
        setUpMainFrame();
        addComponentListener(this);
    }

    //Set up the mainframe
    public void setUpMainFrame() {
        mainFrame.setSize(800, 600);
        mainFrame.add(panelDeck);
        //add the panels to the deck
        panelDeck.add("mainMenu", mainMenu);
        panelDeck.add("options", optionMenu);
        panelDeck.add("character", characterPanel);
        panelDeck.add("mainPanel", mainPanel);
        panelDeck.add("savedGame", savedGamePanel);
        mainMenu.setOpaque(false);
        optionMenu.setOpaque(false);
        mainFrame.setContentPane(panelDeck);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    public void setUpMainPanel() {

        //This will be the "game screen with a filled out canvas (weight in y set to 1)
        // and buttons below (weight in y = 0)
        GridBagLayout mainPanelGrid = new GridBagLayout();
        GridBagConstraints mainPanelConstraints = new GridBagConstraints();
        mainPanel.setLayout(mainPanelGrid);
        //set constraints for the canvas
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setConstraints(mainPanelConstraints, GridBagConstraints.BOTH, 1, 0.95, 0, 1, 0, 0);
        mainPanel.add(canvas, mainPanelConstraints);

        //add a textbox for messages
        MESSAGE_BOX.setEditable(false);
        MESSAGE_BOX.setText("You are on floor " + gameMechanics.getCurrentFloor() + ". " + gameMechanics.getEnemyLog());
        setConstraints(mainPanelConstraints, GridBagConstraints.BOTH, 1, 0.05, 0, 1, 0, 1);
        mainPanel.add(MESSAGE_BOX, mainPanelConstraints);

        //add components for the optiondeck
        GridBagLayout optionsGrid = new GridBagLayout();
        GridBagConstraints optionsConstraints = new GridBagConstraints();
        gameOptions.setLayout(optionsGrid);

        //add options to the gameOptions panel
        JButton[] buttons = new JButton[MenuService.gameOptionsList.size()];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(MenuService.gameOptionsList.get(i));
            buttons[i].addActionListener(EventHandler.gameOptionsListener(buttons, this, gameMechanics));
            optionsConstraints.fill = GridBagConstraints.BOTH;
            //place buttons in a square
            optionsConstraints.weightx = 1;
            optionsConstraints.weighty = 1;
            if (i < 2) {
                optionsConstraints.gridx = i;
                optionsConstraints.gridy = 1;
            } else {
                optionsConstraints.gridx = i - 2;
                optionsConstraints.gridy = 2;
            }
            gameOptions.add(buttons[i], optionsConstraints);
        }
        //set constraints for optionDeck
        optionDeck.add("gameOptions", gameOptions);
        setConstraints(mainPanelConstraints, GridBagConstraints.BOTH, 1, 0, 0, 0, 0, 3);
        mainPanel.add(optionDeck, mainPanelConstraints);

        AttackOptions();
    }

    private void AttackOptions() {
        GridBagLayout attacksLayout = new GridBagLayout();
        GridBagConstraints attacksConstraints = new GridBagConstraints();
        attackOptions.setLayout(attacksLayout);

        JButton[] buttons = new JButton[MenuService.warriorAttacks.size()];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(MenuService.warriorAttacks.get(i));
            buttons[i].addActionListener(EventHandler.attackOptionsListener(buttons, this, gameMechanics));
            attacksConstraints.fill = GridBagConstraints.BOTH;
            attacksConstraints.weightx = 1;
            attacksConstraints.weighty = 1;
            if (i < 2) {
                attacksConstraints.gridx = i;
                attacksConstraints.gridy = 0;
            } else {
                attacksConstraints.gridx = i - 2;
                attacksConstraints.gridy = 1;
            }
            attackOptions.add(buttons[i], attacksConstraints);
        }
        optionDeck.add("attackOptions", attackOptions);


        optionDeck.add("attackOptions", attackOptions);
    }

    public void mainMenu() {
        GridBagLayout mainMenuGrid = new GridBagLayout();
        GridBagConstraints mainMenuConstraints = new GridBagConstraints();
        mainMenu.setLayout(mainMenuGrid);
        //set up menu buttons
        JButton[] menuButtons = new JButton[MenuService.mainMenuOptionsList.size()];

        for (int i = 0; i < menuButtons.length; i++) {
            menuButtons[i] = new JButton(MenuService.mainMenuOptionsList.get(i));
            menuButtons[i].addActionListener(EventHandler.mainMenuListener(menuButtons, this));
            setConstraints(mainMenuConstraints, GridBagConstraints.BOTH, 0, 0, 0, 1, 0, i);
            mainMenu.add(menuButtons[i], mainMenuConstraints);
        }
    }

    public void createCharacter() {
        GridBagLayout characterGrid = new GridBagLayout();
        GridBagConstraints characterConstraints = new GridBagConstraints();
        characterPanel.setLayout(characterGrid);

        JLabel nameLabel = new JLabel("Enter your name");
        setConstraints(characterConstraints, GridBagConstraints.BOTH, 0, 0, 1, 1, 0, 0);
        characterPanel.add(nameLabel, characterConstraints);

        JTextField inputName = new JTextField();
        setConstraints(characterConstraints, GridBagConstraints.BOTH, 0, 0, 2, 1, 0, 1);
        characterPanel.add(inputName, characterConstraints);

        JLabel classLabel = new JLabel("Choose your class");
        characterConstraints.insets = new Insets(40, 0, 0, 0);
        setConstraints(characterConstraints, GridBagConstraints.BOTH, 0, 0, 1, 1, 0, 2);
        characterPanel.add(classLabel, characterConstraints);

        JRadioButton[] buttons = new JRadioButton[GameObjects.CLASSES.size()];
        ButtonGroup group = new ButtonGroup();

        //create radiobuttons to check a class
        for (int i = 0; i < GameObjects.CLASSES.size(); i++) {
            buttons[i] = new JRadioButton(GameObjects.CLASSES.get(i).getPlayerClass());
            buttons[i].setActionCommand(GameObjects.CLASSES.get(i).getPlayerClass());
            buttons[i].addActionListener(EventHandler.characterPanelListener(buttons, this));
            characterConstraints.fill = GridBagConstraints.BOTH;
            //reset insets
            characterConstraints.insets = new Insets(0, 0, 0, 0);
            characterConstraints.gridwidth = 1;
            characterConstraints.gridheight = 1;
            if (i < 2) {
                characterConstraints.gridx = 0;
                characterConstraints.gridy = 3 + i;
            } else {
                characterConstraints.gridx = 1;
                characterConstraints.gridy = 1 + i;
            }
            group.add(buttons[i]);
            characterPanel.add(buttons[i], characterConstraints);
        }

        JButton characterReady = new JButton("Start Game");
        characterConstraints.insets = new Insets(40, 0, 0, 0);
        setConstraints(characterConstraints, GridBagConstraints.BOTH, 0, 0, 2, 1, 0, 5);
        characterReady.addActionListener(EventHandler.characterReadyListener(buttons, characterReady, inputName, this));
        characterPanel.add(characterReady, characterConstraints);

    }

    //Function to set the constants for the gridbaglayout
    private void setConstraints(final GridBagConstraints gridBagConstraints, final int fill, final double weightx,
                                final double weighty, final int gridwidth, final int gridheight, final int gridx,
                                final int gridy) {
        gridBagConstraints.fill = fill;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.gridwidth = gridwidth;
        gridBagConstraints.gridheight = gridheight;
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
    }

    //add componentlisteners to get focus of panels
    public void addComponentListener(Display display) {
        display.getAttackOptions().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                display.getAttackOptions().requestFocusInWindow();
                display.getAttackOptions().setFocusable(true);
                display.getAttackOptions().addKeyListener(EventHandler.attackOptionsKeyListener(display));
            }
        });
    }

    public ArrayList<Rectangle> getHitBoxList() {
        return this.hitBoxList;
    }

    public JPanel getCanvas() {
        return this.canvas;
    }

    public CardLayout getCurrentOption() {
        return this.currentOption;
    }

    public JPanel getAttackOptions() {
        return this.attackOptions;
    }

    public JPanel getOptionDeck() {
        return this.optionDeck;
    }

    public CardLayout getCurrentPanel() {
        return this.currentPanel;
    }

    public JPanel getPanelDeck() {
        return this.panelDeck;
    }

    public JPanel getMainMenu() {
        return this.mainMenu;
    }

    public JPanel getOptionMenu() {
        return this.optionMenu;
    }

    public JPanel getCharacterPanel() {
        return this.characterPanel;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public JPanel getSavedGamePanel() {
        return this.savedGamePanel;
    }

    public void setImage(final Image image) {
        this.image = image;
    }

    public Image getImage() {
        return this.image;
    }

    public GameMechanics getGameMechanics() {
        return this.gameMechanics;
    }
}
