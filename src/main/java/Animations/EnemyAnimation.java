package Animations;

import Displays.GameScreen;
import GameMechanics.*;
import Main.GameEntity;
import NPCs.NPC;

import java.awt.*;
import java.util.ArrayList;

public class EnemyAnimation implements Animation{

    private volatile boolean done = false;
    private final GameScreen gameScreen;
    private final GameMechanics gameMechanics;
    private final NPC npc;

    public EnemyAnimation(GameScreen gameScreen, NPC npc, GameMechanics gameMechanics) {
        this.gameScreen = gameScreen;
        this.npc = npc;
        this.gameMechanics = gameMechanics;

    }

    @Override
    public void terminate() {
        this.done = true;
    }

    @Override
    public void run() {
        while(!done) {
            if(npc.getId().equals("Goblin")) {
                if (npc.getState().equals("idle")) {
                    animation(GameObjects.GOBLIN_ATTACK, npc, gameScreen,0);
                }
            } else if(npc.getId().equals("Troll")){
                if (npc.getState().equals("idle")){
                    animation(GameObjects.SKELETON_ATTACK, npc, gameScreen,0);
                }
            }
            // add switch for other states
        }
    }

    @Override
    public void animation(ArrayList<Image> imageArrayList, GameEntity gameEntity, GameScreen gameScreen, double xDiff) {
        imageArrayList.forEach(image -> {
            gameEntity.setEntityImage(image);
            gameScreen.repaint();
            try {
                Thread.sleep(150);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }
}
