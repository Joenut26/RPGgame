package GameMechanics;

import Abilities.Ability;
import Displays.Display;
import NPCs.Goblin;
import NPCs.NPC;
import NPCs.Skeleton;
import NPCs.Troll;

import java.util.*;

public class GameMechanics implements Runnable {

    private boolean gameOver = false;
    private int currentFloor;
    private boolean newFloor = true;
    private final ArrayList<NPC> enemies = new ArrayList<>();
    private final HashMap<Integer, NPC> initiativeMap = new HashMap<>();
    private final Random random = new Random();
    //array of integers which collects the number of each monstertype ( 0 = goblin, 1 = troll etc)
    private int[] numberOfUniqueEnemies;
    private String enemyLog = "";
    private boolean gameStarted = false;
    private final Thread gameMechanicsThread;
    //set a lock to control the thread
    private final Object lock = new Object();
    private final Object playerTurn = new Object();
    private final Object monsterTurn = new Object();
    private final Object gameScreenLock = new Object();

    //combat related fields
    private NPC target;
    private final double HIT_CAP = 200;
    private final double MISS_CHANCE = 0.25;
    private final int MAX_ROLL = 20;
    private double baseDamage;
    private double damage;
    private double monsterDamage;
    private String abilityChoice;


    public GameMechanics() {
        //set the floor
        GameObjects.FLOORS.add(new Floor(1));
        this.setCurrentFloor(GameObjects.FLOORS.get(0).getLevel());
        gameMechanicsThread = new Thread(this);
        gameMechanicsThread.start();

    }

    //called when a new GameMechanics is instantiated by thread.start()
    @Override
    public void run() {

        while (!gameOver) {
            //gameloop here, will run in the background
            if (!gameStarted) {
                synchronized (lock) {
                    try {
                        //wait until the game is started
                        lock.wait();
                        gameStarted = true;
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }
            //Spawn enemies and set initiatives for every new floor
            //synchronize with the gameScreen and notify it when enemies are spawned so the initial
            //positions of the enemies are correct
            synchronized (gameScreenLock) {
                if (newFloor) {
                    spawnEnemies();
                    rollInitiative();
                    newFloor = !newFloor;
                }
                gameScreenLock.notifyAll();

            }
            //If the hp of every enemy is 0, combat is over
            boolean combatDone = initiativeMap.entrySet().stream().allMatch(entry -> entry.getValue().getCurrentHp() <= 0);

            if (!combatDone) {
                combat();
                //exit if all enemies are defeated
            } else {
                //loot
                //go to new Floor
                this.currentFloor = +1;
                GameObjects.FLOORS.add(new Floor(this.getCurrentFloor()));
                newFloor = !newFloor;
            }
        }
    }

    public void playerAttack(final String abilityID) throws Exception {
        //find chosen ability in database
        Ability abilityChoice = GameObjects.ABILITY_DATABASE.stream().
                filter(ability -> abilityID.equals(ability.getId())).
                findAny().
                orElseThrow(() -> new Exception(abilityID + " not found"));
        //process given ability
        //check for resource cost
        if (abilityChoice.getCost() <= GameObjects.player.getCurrentResource()) {
            //can use
            if (abilityChoice.getId().equals("Auto Attack")) {
                //TODO add weapon damage here
                baseDamage = Math.round(GameObjects.player.getStrength() + 0.69 * (random.nextInt(GameObjects.player.getStrength())));
                //generate resource on attack if applicable
                if (GameObjects.player.getResourceType().equals("Rage")) {
                    //add rage but never go over max value
                    GameObjects.player.setCurrentResource(Math.min((GameObjects.player.getCurrentResource() + 5), GameObjects.player.getResource()));
                }
            } else {
                int mainstat;
                switch (GameObjects.player.getPlayerClass()) {
                    case "Wizard":
                    case "Cleric":
                        mainstat = GameObjects.player.getIntelligence();
                        baseDamage = Math.round(mainstat * abilityChoice.getBaseDamage() / 13.37 + 0.69 * mainstat);
                        break;
                    case "Rogue":
                        mainstat = GameObjects.player.getDexterity();
                        if (abilityChoice.getCost() > 0) {
                            //if finisher add finisher points to damage
                            baseDamage = Math.round(mainstat * GameObjects.player.getCurrentResource() * abilityChoice.getBaseDamage() / 13.37 + 0.69 * mainstat);
                            //set the cost of finisher equal to current combo points
                            abilityChoice.setCost((int) GameObjects.player.getCurrentResource());
                        }
                        break;
                    default:
                        //default warrior to initialize mainstat here
                        mainstat = GameObjects.player.getStrength();
                        baseDamage = Math.round(mainstat * abilityChoice.getBaseDamage() / 13.37 + 0.69 * mainstat);
                }

                //update resource
                //in case of combo points max is 5
                if (GameObjects.player.getCurrentResource() <= GameObjects.player.getResource()) {
                    GameObjects.player.setCurrentResource(GameObjects.player.getCurrentResource() - abilityChoice.getCost());
                } else {
                    //set resource to max if it exceeds
                    GameObjects.player.setCurrentResource(GameObjects.player.getResource());
                }
            }
        } else {
            Display.MESSAGE_BOX.setText("Not enough " + GameObjects.player.getResourceType());
        }

    }

    public void monsterAttack(final NPC monster) {
        // determine damage
        double damage = Math.round(monster.getStrength() + random.nextInt(monster.getStrength()));
        // roll for hit
        int hitRoll = random.nextInt(monster.getHit()) + 1;
        if (hitRoll == monster.getHit()) {
            // crit
            monsterDamage = 2 * damage;
        } else if (hitRoll < monster.getHit() / 2) {
            monsterDamage = 0;
        } else {
            monsterDamage = damage;
        }
        // update player hp
        GameObjects.player.setCurrentHp(GameObjects.player.getCurrentHp() - damage);
    }


    private void determineHit() {
        //determine if an attack is a hit, miss or crit
        //increases and reductions to the damage take place here too
        double hitcap = 200;
        //base miss chance so that at 0 hit you can still actually hit (0.25 miss chance gives a 75% chance to hit)
        double baseMissChance = 0.25;
        int maxHitRoll = 20;
        // roll for the hit
        int hitRoll = random.nextInt(maxHitRoll) + 1;
        if (hitRoll == maxHitRoll) {
            // crit
            damage = 2 * baseDamage;
            //hitcap 100 for now multiply with half the max roll because hit/hitcap is fraction and if hit=0 you still have a base chance to hit
        } else if (hitRoll < Math.round(maxHitRoll * baseMissChance * (1 - GameObjects.player.getHit() / hitcap))) {
            // miss
            damage = 0;
        } else {
            // normal hit
            damage = baseDamage;
        }

    }


    private void combat() {

        for (final Map.Entry<Integer, NPC> entry : initiativeMap.entrySet()) {
            if (entry.getValue().getName().equals("Player")) {
                synchronized (playerTurn) {
                    Display.MESSAGE_BOX.setText("Your turn");
                    GameObjects.player.setTurn(true);
                    GameObjects.player.setActionDone(false);
                    // playermove
                    try {
                        //wait for user input
                        playerTurn.wait();
                    } catch (InterruptedException interruptedException) {
                        System.out.println(interruptedException.getMessage());
                        interruptedException.printStackTrace();
                    }
                    try {
                        playerAttack(getAbilityChoice());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    //determine hit
                    determineHit();
                    //update animations
                    GameObjects.player.setState("running");

                    if(GameObjects.player.isActionDone()){
                        GameObjects.player.setTurn(false);
                    }
                }
                // do monstermove if their hp is > 0
            } else if (entry.getValue().getCurrentHp() > 0) {
                    //TODO NEED DELAY HERE
                    Display.MESSAGE_BOX.setText(entry.getValue().getName() + " " + entry.getKey() + "'s turn");
                    // monstermove
                    entry.getValue().setTurn(true);
                    entry.getValue().setActionDone(false);
                    monsterAttack(entry.getValue());
                    entry.getValue().setState("walk");
                    System.out.println("kekw");
                    if(entry.getValue().isActionDone()){
                        entry.getValue().setTurn(false);
                    }

            }
        }
    }

    private void rollInitiative() {
        //set player initiative
        GameObjects.player.setInitiative(random.nextInt(20) + 1 + GameObjects.player.getDexterity());
        //monster initiatives
        enemies.forEach(enemy -> enemy.setInitiative(random.nextInt(20) + 1 + enemy.getDexterity()));
        //add the player initiative to the list to sort them
        NPC playerNPC = new NPC();
        playerNPC.setInitiative(GameObjects.player.getInitiative());
        playerNPC.setCurrentHp(0);
        playerNPC.setName("Player");
        playerNPC.setEntityIcon(GameObjects.player.getEntityIcon());
        enemies.add(playerNPC);
        //sort by descending order
        enemies.sort((npc1, npc2) -> Double.compare(npc2.getInitiative(), npc1.getInitiative()));
        //put it in a map to keep track of order
        for (int i = 0; i < enemies.size(); i++) {
            initiativeMap.put(i, enemies.get(i));
        }
        //remove player now
        enemies.remove(playerNPC);
    }

    private void spawnEnemies() {
        //determine number of enemies
        int numberOfEnemies = 2; //can scale later
        numberOfUniqueEnemies = new int[GameObjects.NPC_LIST.size()];
        //reset log and list
        Arrays.fill(numberOfUniqueEnemies, 0);
        enemies.clear();
        //Add enemies to fight from the npc list
        for (int i = 0; i < numberOfEnemies; i++) {
            //fetch a random enemy from the NPC list
            //put for every enemy an image in the list
            final String id = GameObjects.NPC_LIST.get(random.nextInt(GameObjects.NPC_LIST.size())).getId();
            switch (id) {
                case "Goblin":
                    enemies.add(i, new Goblin());
                    numberOfUniqueEnemies[0] += 1;
                    break;
//                case "Troll":
//                    enemies.add(i, new Troll());
//                    numberOfUniqueEnemies[1] += 1;
//                    break;
                case "Skeleton":
                    enemies.add(i, new Skeleton());
                    numberOfUniqueEnemies[1] += 1;
            }
            //set the current hp of the enemies to max
            enemies.get(i).setCurrentHp(enemies.get(i).getHitPoints());

            //set coordinates
            enemies.get(i).setPositionX(0.6 + i * 0.15);
            enemies.get(i).setPositionY(0.5);
        }
        System.out.println(enemies);
    }

    public void logEnemies() {
        for (int i = 0; i < numberOfUniqueEnemies.length; i++) {

            if (numberOfUniqueEnemies[i] != 0) {
                if (numberOfUniqueEnemies[i] == 1) {
                    enemyLog = enemyLog.concat("There is " + numberOfUniqueEnemies[i] + " " + GameObjects.NPC_LIST.get(i));
                } else {
                    enemyLog = "There are " + numberOfUniqueEnemies[i] + " " + GameObjects.NPC_LIST.get(i) + "s";
                }
                if (i >= 1 && numberOfUniqueEnemies[i - 1] != 0 && i < numberOfUniqueEnemies.length - 1) {
                    enemyLog = enemyLog.concat(" and ");
                } else if (i == numberOfUniqueEnemies.length - 1) {
                    enemyLog = enemyLog.concat(".");
                }
            }
        }
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public ArrayList<NPC> getEnemies() {
        return this.enemies;
    }

    public Thread getGameMechanicsThread() {
        return this.gameMechanicsThread;
    }

    public Object getLock() {
        return this.lock;
    }

    public Object getPlayerTurn() {
        return this.playerTurn;
    }

    public boolean isNewFloor() {
        return this.newFloor;
    }

    public boolean isGameStarted() {
        return this.gameStarted;
    }

    public Object getGameScreenLock() {
        return this.gameScreenLock;
    }

    public void setTarget(final NPC target) {
        this.target = target;
    }

    public NPC getTarget() {
        return this.target;
    }

    public String getAbilityChoice() {
        return abilityChoice;
    }

    public void setAbilityChoice(String abilityChoice) {
        this.abilityChoice = abilityChoice;
    }

    public double getDamage() {
        return damage;
    }

    public double getMonsterDamage() {
        return monsterDamage;
    }

    public Object getMonsterTurn() {
        return monsterTurn;
    }
}