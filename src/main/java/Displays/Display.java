package Displays;

import GameMechanics.GameMechanics;
import GameMechanics.GameObjects;
import Listeners.EventHandler;
import Main.Tools;
import Menus.MenuService;
import NPCs.NPC;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Display extends JFrame{

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
    private JList<NPC> targets;
    private JList<String> attackList;
    private JScrollPane attackPanel;
    private ArrayList<String> classAbilities;
    private final JPanel targetPanel = new JPanel();
    private final GameScreen canvas;

    private Image background = Tools.requestImage("src/main/resources/BigBG.jpg");

    //Define textarea to display messages
    public static final JTextArea MESSAGE_BOX = new JTextArea();


    private boolean targetsCreated = false;
    private boolean attacksCreated = false;

    public Display(GameMechanics gameMechanics){
        this.gameMechanics = gameMechanics;
        this.canvas = new GameScreen(this.gameMechanics);
        mainMenu();
        createCharacter();
        mainPanel();
        initDisplay();
        //make sure these are the first to show
        currentPanel.show(mainMenu.getParent(), "mainMenu");
        currentOption.show(gameOptions.getParent(), "gameOptions");
    }

    public void updateTargets(){

        //make calls to update targets
        targetSelection();
        targetsCreated = true;

    }

    public void updateAttackList(){
        attackOptions();
        attacksCreated = true;
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

        optionDeck.add("targets", targetPanel);
        optionDeck.add("attackOptions", attackOptions);

        this.setVisible(true);

    }

    public void updateBackground(final Image bgImage){
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
            setConstraints(mainMenuConstraints, 0, 0, 0, 1, 0, i);
            mainMenu.add(menuButtons[i], mainMenuConstraints);
        }
    }

    private void mainPanel() {

        //This will be the "game screen with a filled out canvas (weight in y set to 1)
        // and buttons below (weight in y = 0)
        GridBagLayout mainPanelGrid = new GridBagLayout();
        GridBagConstraints mainPanelConstraints = new GridBagConstraints();
        mainPanel.setLayout(mainPanelGrid);
        //set constraints for the canvas
        canvas.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setConstraints(mainPanelConstraints, 1, 0.95, 0, 1, 0, 0);
        mainPanel.add(canvas, mainPanelConstraints);

        //add a textbox for messages
        MESSAGE_BOX.setEditable(false);
        setConstraints(mainPanelConstraints, 1, 0.05, 0, 1, 0, 1);
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
        setConstraints(mainPanelConstraints, 1, 0, 0, 1, 0, 3);
        mainPanel.add(optionDeck, mainPanelConstraints);

    }

    private void createCharacter() {
        GridBagLayout characterGrid = new GridBagLayout();
        GridBagConstraints characterConstraints = new GridBagConstraints();
        characterPanel.setLayout(characterGrid);

        JLabel nameLabel = new JLabel("Enter your name");
        setConstraints(characterConstraints, 0, 0, 1, 1, 0, 0);
        characterPanel.add(nameLabel, characterConstraints);

        JTextField inputName = new JTextField();
        setConstraints(characterConstraints, 0, 0, 2, 1, 0, 1);
        characterPanel.add(inputName, characterConstraints);

        JLabel classLabel = new JLabel("Choose your class");
        characterConstraints.insets = new Insets(40, 0, 0, 0);
        setConstraints(characterConstraints, 0, 0, 1, 1, 0, 2);
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
        setConstraints(characterConstraints, 0, 0, 2, 1, 0, 5);
        characterReady.addActionListener(EventHandler.characterReadyListener(buttons, characterReady, inputName, this, gameMechanics));
        characterPanel.add(characterReady, characterConstraints);

    }

    private void targetSelection() {
        //display a list of enemies to attack
        targets = new JList(gameMechanics.getEnemies().toArray());
        //set up needs to be done only once
        if(!targetsCreated) {
            targets.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            targets.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            targets.setLayoutOrientation(JList.VERTICAL);
            GridBagLayout targetLayout = new GridBagLayout();
            GridBagConstraints targetConstraints = new GridBagConstraints();
            setConstraints(targetConstraints, 1, 0, 1, 2, 0, 0);
            targetPanel.setLayout(targetLayout);
            JScrollPane targetScrollPane = new JScrollPane(targets,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            targetPanel.add(targetScrollPane, targetConstraints);

            JButton go = new JButton("Go!");
            go.addActionListener(EventHandler.goButton(this));
            setConstraints(targetConstraints, 0, 0, 1, 1, 1, 0);
            targetPanel.add(go, targetConstraints);

            JButton back = new JButton("Back");
            back.addActionListener(EventHandler.backButton(this));
            setConstraints(targetConstraints, 0, 0, 1, 1, 1, 1);
            targetPanel.add(back, targetConstraints);
        }

        targets.addListSelectionListener(EventHandler.targetListener(gameMechanics,targets,this));
    }

    private void setAttackList(){
        //determine which list to use from menuservice
        switch (GameObjects.player.getPlayerClass()){
            case "Warrior": classAbilities = MenuService.warriorAttacks;
            break;
            case "Wizard": classAbilities = MenuService.wizardAttacks;
            break;
            case "Rogue": classAbilities = MenuService.rogueAttacks;
            break;
            case "Cleric": classAbilities = MenuService.clericAttacks;
            break;        }

    }

    private void attackOptions() {
        setAttackList();
        attackList = new JList<>(classAbilities.toArray(new String[0]));
        if(!attacksCreated){
            attackList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            attackList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            attackList.setVisibleRowCount(2);
            attackList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            //draw border around cells
            attackList.setCellRenderer(new DefaultListCellRenderer(){
                @Override
                public Component getListCellRendererComponent(JList<?> list,
                                                              Object value, int index, boolean isSelected,
                                                              boolean cellHasFocus) {
                    JLabel listCellRendererComponent = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
                    listCellRendererComponent.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1,Color.BLACK));
                    listCellRendererComponent.setHorizontalAlignment(SwingConstants.CENTER);
                    return listCellRendererComponent;
                }
            });

            GridBagLayout attackLayout = new GridBagLayout();
            GridBagConstraints attackConstraints = new GridBagConstraints();
            setConstraints(attackConstraints, 1, 0, 1, 2, 0, 0);
            attackOptions.setLayout(attackLayout);
            attackPanel = new JScrollPane(attackList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            attackOptions.add(attackPanel, attackConstraints);

            JButton go = new JButton("Go!");
            go.addActionListener(EventHandler.goButtonAttacks(this));
            setConstraints(attackConstraints, 0, 0, 1, 1, 1, 0);
            attackOptions.add(go, attackConstraints);

            JButton back = new JButton("Back");
            back.addActionListener(EventHandler.backButtonAttacks(this));
            setConstraints(attackConstraints, 0, 0, 1, 1, 1, 1);
            attackOptions.add(back, attackConstraints);
        }

        attackList.addListSelectionListener(EventHandler.attackListener(attackList,gameMechanics,this));

    }

    //Function to set the constants for the gridbaglayout
    private void setConstraints(final GridBagConstraints gridBagConstraints, final double weightx,
                                final double weighty, final int gridwidth, final int gridheight, final int gridx,
                                final int gridy) {
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = weightx;
        gridBagConstraints.weighty = weighty;
        gridBagConstraints.gridwidth = gridwidth;
        gridBagConstraints.gridheight = gridheight;
        gridBagConstraints.gridx = gridx;
        gridBagConstraints.gridy = gridy;
    }

    public ArrayList<String> getClassAbilities() {
        return this.classAbilities;
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

    public JList<NPC> getTargets() {
        return this.targets;
    }

    public JList<String> getAttackList() {
        return this.attackList;
    }

    public JPanel getTargetPanel() {
        return this.targetPanel;
    }


}