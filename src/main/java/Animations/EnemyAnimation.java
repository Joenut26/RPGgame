package Animations;

import Displays.GameScreen;
import GameMechanics.*;
import Main.GameEntity;
import Main.Tools;
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
            update();
            if (npc.getId().equals("Goblin")) {
                switch (npc.getState()) {
                    case "idle":
                        animation(GameObjects.GOBLIN_IDLE, npc, gameScreen, 0);
                        break;
                    case "getHit":
                        animation(GameObjects.GOBLIN_GETHIT, npc, gameScreen, 0);
                        npc.setState("idle");
                        break;
                    case "attack":
                        animation(GameObjects.GOBLIN_ATTACK, npc, gameScreen, 0);
                        break;
                    case "reverse":
                        animation(GameObjects.GOBLIN_REVERSE, npc, gameScreen, 10);
                        break;
                    case "block":
                        npc.setState("idle");
                        break;
                    case "walk":
                        animation(GameObjects.GOBLIN_WALK, npc, gameScreen, -10);
                        break;
                    case "death":
                        animation(GameObjects.GOBLIN_DEATH, npc, gameScreen, 4);
                        gameMechanics.getEnemies().removeIf(GameEntity::isDead);
                        terminate();
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
                        break;
                    case "reverse":
                        animation(GameObjects.SKELETON_REVERSE, npc, gameScreen, 10);
                        break;
                    case "block":
                        animation(GameObjects.SKELETON_BLOCK, npc, gameScreen, 0);
                        npc.setState("idle");
                        break;
                    case "walk":
                        animation(GameObjects.SKELETON_WALK, npc, gameScreen, -10);
                        break;
                    case "death":
                        animation(GameObjects.SKELETON_DEATH, npc, gameScreen, 0);
                        gameMechanics.getEnemies().removeIf(GameEntity::isDead);
                        terminate();
                        break;
                }
            }
            //update();
        }

    }

    private void update() {

        if (npc.getCurrentHp() <= 0) {
            npc.setDead(true);
            npc.setTargeted(false);
            npc.setState("death");
            System.out.println("kekw");
        }

        if (xc <= (gameScreen.getPlayerX() + hitBox) && npc.isTurn()) {
            if (!npc.isActionDone()) {
                npc.setState("attack");
                if (gameMechanics.getMonsterDamage() != 0) {
                    GameObjects.player.setState("getHit");
                }
                npc.setActionDone(true);
            } else {
                npc.setState("reverse");
            }
        }

        if (npc.getState().equals("reverse")) {
            if (xc == startX) {
                npc.setState("idle");
                synchronized (gameMechanics.getMonsterTurn()) {
                    gameMechanics.getMonsterTurn().notifyAll();
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
