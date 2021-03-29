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
    private int startX;
    private int startY;
    private int xc;
    private int yc;

    public PlayerAnimation(GameScreen gameScreen, GameMechanics gameMechanics) {
        this.gameScreen = gameScreen;
        this.gameMechanics = gameMechanics;
        this.startX = gameScreen.getPlayerX();
        this.startY = gameScreen.getPlayerY();
        this.xc = startX;
        this.yc = startY;
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
                case "attack":
                    animation(GameObjects.WARRIOR_ATTACK, GameObjects.player, gameScreen, 0);
                    break;
            }
            if(gameMechanics.getTarget() != null) {
                if (xc == gameMechanics.getTarget().getPositionX()) {
                    GameObjects.player.setState("attack");
                }
            }

            if(GameObjects.player.isActionDone()){
                xc = startX;
            }



            // add switch for other states
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
                Thread.sleep(75);
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
}
