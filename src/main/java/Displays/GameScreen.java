package Displays;

import Main.Tools;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel implements Runnable {

    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int DELAY = 25;
    private final double xScale = 0.1;
    private final double yScale = 0.2;
    private Thread animator;
    private boolean gameOver = false;
    private int playerX, playerY;
    private int imageWidth, imageHeight;
    private Image playerImage;
    private Image backGround;

    public GameScreen() {
        initGameScreen();
    }

    private void initGameScreen() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        playerX = (int) (xScale * WIDTH);
        playerY = (int) (3 * yScale * HEIGHT);

        setBackGround(Tools.requestImage("src/main/resources/greekbg.png"));
    }

    @Override
    public void addNotify() {
        //is called when Panel is added to Container and starts the thread for the scene
        super.addNotify();
        animator = new Thread(this);
        animator.start();

    }

    @Override
    //set all the drawable images here and update the position in the update method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(getBackGround(), 0, 0, getWidth(), getHeight(), null);
        g.drawImage(getPlayerImage(), playerX, playerY, imageWidth, imageHeight, null);
        Toolkit.getDefaultToolkit().sync();
    }

    private void update() {
        //do positions for animations here

        imageWidth = (int) (xScale * getWidth());
        imageHeight = (int) (yScale * getHeight());

    }

    @Override
    public void run() {

        long oldTime, timeDiff, sleep;
        //get current time
        oldTime = System.currentTimeMillis();

        //loop
        while (!gameOver) {

            update();
            repaint();

            timeDiff = System.currentTimeMillis() - oldTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                String msg = String.format("Thread interrupted: %s", e.getMessage());
                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
            //reset in loop
            oldTime = System.currentTimeMillis();
        }
    }

    public void setBackGround(Image backGround) {
        this.backGround = backGround;
    }

    public Image getBackGround() {
        return this.backGround;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public int getPlayerY() {
        return this.playerY;
    }

    public void setPlayerY(int playerY) {
        this.playerY = playerY;
    }

    public int getPlayerX() {
        return this.playerX;
    }

    public void setPlayerX(int playerX) {
        this.playerX = playerX;
    }

    public Image getPlayerImage() {
        return this.playerImage;
    }

    public void setPlayerImage(Image playerImage) {
        this.playerImage = playerImage;
    }

    public Thread getAnimator() {
        return animator;
    }
}
