package Displays;

import GameMechanics.GameMechanics;
import GameMechanics.GameObjects;
import Listeners.EventHandler;
import Main.Tools;
import Menus.MenuService;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    //add game Thread for the event handlers
    private final GameMechanics gameMechanics;
    //set card layout which shows one of the panels from the deck
    private final CardLayout currentPanel = new CardLayout();
    //create deck for the panels
    private final JPanel panelDeck = new JPanel(currentPanel){

        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(background,0,0, this.getWidth(), this.getHeight(),null);
        }
    };

    //Define Panels that can be added to panelDeck
    private final BackgroundPanel mainMenu = new BackgroundPanel();
    private final BackgroundPanel optionMenu = new BackgroundPanel();
    private final BackgroundPanel characterPanel = new BackgroundPanel();
    private final BackgroundPanel savedGamePanel = new BackgroundPanel();
    private final JPanel mainPanel = new JPanel();

    //Layout to switch between options in the mainPanel
    private final CardLayout currentOption = new CardLayout();
    private final JPanel optionDeck = new JPanel(currentOption);
    private final JPanel gameOptions = new JPanel();
    private final JPanel attackOptions = new JPanel();
    private final GameScreen canvas;

    private Image background = Tools.requestImage("src/main/resources/BigBG.jpg");

    //Define textarea to display messages
    public static final JTextArea MESSAGE_BOX = new JTextArea();

    public Display(GameMechanics gameMechanics){
        this.gameMechanics = gameMechanics;
        this.canvas = new GameScreen(this.gameMechanics);
        mainMenu();
        createCharacter();
        mainPanel();
        attackOptions();
        initDisplay();
    }

    private void initDisplay(){

        this.setSize(800,600);
        this.add(panelDeck);
        panelDeck.add("mainMenu", mainMenu);
        panelDeck.add("options", optionMenu);
        panelDeck.add("character", characterPanel);
        panelDeck.add("save", savedGamePanel);
        panelDeck.add("mainPanel", mainPanel);
        mainMenu.setOpaque(false);
        characterPanel.setOpaque(false);
        savedGamePanel.setOpaque(false);
        optionMenu.setOpaque(false);
        this.setContentPane(panelDeck);
        this.setResizable(true);
        this.setTitle("Game");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    private void updateBackground(final Image bgImage){
        this.background = bgImage;
        repaint();

    }

    private void mainMenu() {
        GridBagLayout mainMenuGrid = new GridBagLayout();
        GridBagConstraints mainMenuConstraints = new GridBagConstraints();
        mainMenu.setLayout(mainMenuGrid);
        mainMenu.setBackgroundImage(background);
        //set up menu buttons
        JButton[] menuButtons = new JButton[MenuService.mainMenuOptionsList.size()];

        for (int i = 0; i < menuButtons.length; i++) {
            menuButtons[i] = new JButton(MenuService.mainMenuOptionsList.get(i));
            menuButtons[i].addActionListener(EventHandler.mainMenuListener(menuButtons, this));
            setConstraints(mainMenuConstraints, GridBagConstraints.BOTH, 0, 0, 0, 1, 0, i);
            mainMenu.add(menuButtons[i], mainMenuConstraints);
        }
    }

    private void mainPanel() {

        //This will be the "game screen with a filled out canvas (weight in y set to 1)
        canvas.setPlayerImage(Tools.requestImage("src/main/resources/idle.png"));
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
        MESSAGE_BOX.setText("You are on floor ");
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

    }

    private void createCharacter() {
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
            buttons[i].addActionListener(EventHandler.characterPanelListener());
            buttons[i].setOpaque(false);
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
        characterReady.addActionListener(EventHandler.characterReadyListener(buttons, characterReady, inputName, this, gameMechanics));
        characterPanel.add(characterReady, characterConstraints);

    }

    private void attackOptions() {
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

    public JPanel getOptionDeck() {
        return this.optionDeck;
    }

    public GameMechanics getGameMechanics() {
        return this.gameMechanics;
    }

    public CardLayout getCurrentOption() {
        return this.currentOption;
    }

    public GameScreen getCanvas() {
        return this.canvas;
    }

    public JPanel getGameOptions() {
        return this.gameOptions;
    }

    public JPanel getAttackOptions() {
        return this.attackOptions;
    }

    public JPanel getMainPanel() {
        return this.mainPanel;
    }

    public CardLayout getCurrentPanel() {
        return this.currentPanel;
    }

    public BackgroundPanel getMainMenu(){
        return this.mainMenu;
    }

    public BackgroundPanel getOptionMenu(){
        return this.optionMenu;
    }

    public BackgroundPanel getCharacterPanel(){
        return this.characterPanel;
    }

    public BackgroundPanel getSavedGamePanel() {
        return this.savedGamePanel;
    }

}