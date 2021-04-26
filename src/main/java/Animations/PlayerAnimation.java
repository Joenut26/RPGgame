package Animations;

import Displays.GameScreen;
import GameMechanics.*;
import Main.GameEntity;

import java.awt.*;
import java.util.ArrayList;

public class PlayerAnimation implements Animation {

    private volatile boolean done = false;
    private final Object attackLock = new Object();
    private final GameScreen gameScreen;
    private final GameMechanics gameMechanics;
    private final int startX;
    private final int startY;
    private int xc;
    private int yc;
    private final double hitBox;

    public PlayerAnimation(GameScreen gameScreen, GameMechanics gameMechanics) {
        this.gameScreen = gameScreen;
        this.gameMechanics = gameMechanics;
        this.startX = gameScreen.getPlayerX();
        this.startY = gameScreen.getPlayerY();
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
            switch (GameObjects.player.getState()) {
                case "idle":
                    animation(GameObjects.WARRIOR_IDLE, GameObjects.player, gameScreen, 0);
                    break;
                case "running":
                    animation(GameObjects.WARRIOR_RUNNING, GameObjects.player, gameScreen, 10);
                    break;
                case "getHit":
                    animation(GameObjects.WARRIOR_GETHIT, GameObjects.player, gameScreen, 0);
                    GameObjects.player.setState("idle");
                    break;
                case "attack":
                    animation(GameObjects.WARRIOR_ATTACK, GameObjects.player, gameScreen, 0);
                    //reset states
                    xc = startX;
                    GameObjects.player.setState("idle");
                    synchronized (gameMechanics.getPlayerTurn()){
                        gameMechanics.getPlayerTurn().notifyAll();
                    }
            }
            if (gameMechanics.getTarget() != null) {

                if (xc + hitBox >= gameMechanics.getTarget().getPositionX() && GameObjects.player.isTurn()) {
                    GameObjects.player.setState("attack");
                    if (gameMechanics.getDamage() == 0) {
                        gameMechanics.getTarget().setState("block");
                    } else {
                        gameMechanics.getTarget().setState("getHit");

                    }
                }
            }
        }
    }

    @Override
    public void animation(ArrayList<Image> imageArrayList, GameEntity gameEntity, GameScreen gameScreen, double xDiff) {
        imageArrayList.forEach(image -> {
            gameEntity.setEntityImage(image);
            //position changes before the repaint to smooth it out
            xc += xDiff;

            gameScreen.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
    }

    public int getXc() {
        return this.xc;
    }

    public int getYc() {
        return this.yc;
    }

    public Object getAttackLock() {
        return this.attackLock;
    }

    public double getHitBox() {
        return hitBox;
    }
}
