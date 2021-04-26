package Animations;

import Displays.GameScreen;
import GameMechanics.*;
import Main.GameEntity;
import NPCs.NPC;

import java.awt.*;
import java.util.ArrayList;

public class EnemyAnimation implements Animation {

    private volatile boolean done = false;
    private final GameScreen gameScreen;
    private final GameMechanics gameMechanics;
    private final NPC npc;
    private final double startX;
    private final double startY;
    private double xc;
    private double yc;
    private double hitBox;


    public EnemyAnimation(GameScreen gameScreen, NPC npc, GameMechanics gameMechanics) {
        this.gameScreen = gameScreen;
        this.npc = npc;
        this.gameMechanics = gameMechanics;
        this.startX = npc.getPositionX();
        this.startY = npc.getPositionY();
        this.xc = startX;
        this.yc = startY;
        this.hitBox = gameScreen.getImageWidth();

    }

    @Override
    public void terminate() {
        this.done = true;
    }

    @Override
    public void run() {
        while (!done) {
            if (npc.getId().equals("Goblin")) {
                switch (npc.getState()) {
                    case "idle":
                        animation(GameObjects.GOBLIN_IDLE, npc, gameScreen, 0);
                        break;
                    case "attack":
                        animation(GameObjects.GOBLIN_ATTACK, npc, gameScreen, 0);
                        xc = startX;
                        npc.setState("idle");
                        synchronized (gameMechanics.getMonsterTurn()){
                            gameMechanics.getMonsterTurn().notifyAll();
                        }
                        break;
                    case "getHit":
                        break;
                    case "death":
                        break;
                    case "walk":
                        animation(GameObjects.GOBLIN_WALK, npc, gameScreen, -10);
                        break;
                    case "block":
                        break;
                }
            } else if (npc.getId().equals("Skeleton")) {
                switch (npc.getState()) {
                    case "idle":
                        animation(GameObjects.SKELETON_IDLE, npc, gameScreen, 0);
                        break;
                    case "getHit":
                        animation(GameObjects.SKELETON_GETHIT, npc, gameScreen, 0);
                        npc.setState("idle");
                        break;
                    case "attack":
                        animation(GameObjects.SKELETON_ATTACK, npc, gameScreen, 0);
                        xc = startX;
                        npc.setActionDone(true);
                        npc.setState("idle");
                        synchronized (gameMechanics.getMonsterTurn()){
                            gameMechanics.getMonsterTurn().notifyAll();
                        }
                        break;
                    case "block":
                        animation(GameObjects.SKELETON_SHIELD, npc, gameScreen, 0);
                        npc.setState("idle");
                        break;
                    case "walk":
                        animation(GameObjects.SKELETON_WALK, npc, gameScreen, -10);
                        break;
                    case "death":
                        animation(GameObjects.SKELETON_DEATH, npc, gameScreen, 0);
                        break;
                }
            }


            if (xc <= (gameScreen.getPlayerX() + hitBox) && npc.isTurn()) {
                npc.setState("attack");
                if (gameMechanics.getMonsterDamage() != 0) {
                    GameObjects.player.setState("getHit");
                }

            }
        }
    }

    @Override
    public void animation(ArrayList<Image> imageArrayList, GameEntity gameEntity, GameScreen gameScreen, double xDiff) {
        imageArrayList.forEach(image -> {
            gameEntity.setEntityImage(image);
            xc += xDiff;

            gameScreen.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }

    public double getXc() {
        return xc;
    }
}
