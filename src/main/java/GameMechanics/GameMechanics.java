package GameMechanics;

import Displays.Display;
import NPCs.Goblin;
import NPCs.NPC;
import NPCs.Troll;

import java.util.*;

public class GameMechanics implements Runnable {

    private boolean gameOver= false;
    private int currentFloor;
    private boolean newFloor = true;
    private final ArrayList<NPC> enemies = new ArrayList<>();
    private final HashMap<Integer, NPC> initiativeMap = new HashMap<>();
    private final Random random = new Random();
    //array of integers which collects the number of each monstertype ( 0 = goblin, 1 = troll etc)
    private int[] numberOfUniqueEnemies;
    private String enemyLog = "";
    private boolean combatDone = true;
    private final Thread gameMechanicsThread;
    //set a lock to control the thread
    private final Object lock = new Object();


    public GameMechanics(){
        //set the floor
        GameObjects.FLOORS.add(new Floor(1));
        this.setCurrentFloor(GameObjects.FLOORS.get(0).getLevel());
        gameMechanicsThread = new Thread(this);
        gameMechanicsThread.start();

    }

    @Override
    public void run() {

        while (!gameOver){
            //gameloop here, will run in the background
            synchronized (lock) {
                try {
                    //wait until the game is started
                    lock.wait();
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

            }
        }
    }

    private void combat() {

        for (final Map.Entry<Integer, NPC> entry : initiativeMap.entrySet()) {
            if (entry.getValue().getName().equals("Player")) {
                Display.MESSAGE_BOX.setText("Your turn");
                GameObjects.player.setTurn(true);
                // playermove
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException interruptedException) {
                        System.out.println(interruptedException.getMessage());
                        interruptedException.printStackTrace();
                    }
                }
                // do monstermove if their hp is > 0
            } else if (entry.getValue().getCurrentHp() > 0) {
                Display.MESSAGE_BOX.setText(entry.getValue().getName() + " " + entry.getKey() + "'s turn");
                entry.getValue().setTurn(true);
                // monstermove
                System.out.println("monsterturn");
            }
        }
    }

    private void rollInitiative() {
        //set player initiative
        GameObjects.player.setInitiative(random.nextInt(20) + 1 + GameObjects.player.getDexterity());
        //monster initiatives
        enemies.forEach(enemy -> enemy.setInitiativeNPC(random.nextInt(20) + 1 + enemy.getDexterity()));
        //add the player initiative to the list to sort them
        NPC playerNPC = new NPC();
        playerNPC.setInitiativeNPC(GameObjects.player.getInitiative());
        playerNPC.setCurrentHp(0);
        playerNPC.setName("Player");
        playerNPC.setMonsterIcon(GameObjects.player.getPlayerIcon());
        enemies.add(playerNPC);
        //sort by descending order
        enemies.sort((npc1, npc2) -> Double.compare(npc2.getInitiativeNPC(), npc1.getInitiativeNPC()));
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
            System.out.println(id);
            switch (id) {
                case "Goblin":
                    enemies.add(i, new Goblin());
                    numberOfUniqueEnemies[0] += 1;
                    break;
                case "Troll":
                    enemies.add(i, new Troll());
                    numberOfUniqueEnemies[1] += 1;
                    break;
            }
            //set the current hp of the enemies to max
            enemies.get(i).setCurrentHp(enemies.get(i).getHitPoints());

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

    public Thread getGameMechanicsThread() {
        return this.gameMechanicsThread;
    }

    public Object getLock(){
        return this.lock;
    }
}