package Displays;

import Animations.Animation;
import Animations.EnemyAnimation;
import Animations.PlayerAnimation;
import GameMechanics.GameMechanics;
import GameMechanics.GameObjects;
import Main.Tools;
import NPCs.NPC;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
    private Image backGround;
    private final GameMechanics gameMechanics;
    private final Font mainFont = new Font("Lucida Console", Font.BOLD, 12);
    private int offSet = 5;
    private PlayerAnimation playerAnimation;
    private final ArrayList<EnemyAnimation> enemyAnimations = new ArrayList<>();

    public GameScreen(final GameMechanics gameMechanics) {
        this.gameMechanics = gameMechanics;
        initGameScreen();
    }

    private void initGameScreen() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        playerX = (int) (xScale * WIDTH);
        playerY = (int) (3 * yScale * HEIGHT);

        setBackGround(Tools.requestImage("src/main/resources/Background.png"));
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
        g.drawImage(GameObjects.player.getEntityImage(), playerX, playerY, imageWidth, imageHeight, null);
        for (NPC enemy : gameMechanics.getEnemies()) {
            g.drawImage(enemy.getEntityImage(), (int) enemy.getPositionX(), (int) enemy.getPositionY(), (int) (imageWidth * 1.5), imageHeight *2, null);
            // draw a triangle above the targets head if targeted
            if (enemy.isTargeted()) {
                int[] polyX = {(int) (enemy.getPositionX() + 0.25 * imageWidth), (int) (enemy.getPositionX() + 0.5 * imageWidth), (int) (enemy.getPositionX() + 0.75 * imageWidth)};
                int[] polyY = {(int) (enemy.getPositionY() - 15), (int) (enemy.getPositionY() - 5), (int) (enemy.getPositionY() - 15)};
                Color prevCol = g.getColor();
                g.setColor(Color.GREEN);
                g.fillPolygon(polyX, polyY, 3);
                g.setColor(prevCol);
            }
        }

        //healthbar/resourcebar/name/icon in top left corner
        //icon
        g.drawImage(GameObjects.player.getEntityIcon(), 0, 0, (int) (xScale * getWidth()), (int) (xScale * getWidth()), null);
        g.drawRect(0, 0, (int) (xScale * getWidth()), (int) (xScale * getWidth()));
        //name
        g.setFont(mainFont);
        g.setColor(Color.BLACK);
        //font metrics to place the text on the screen
        FontMetrics fontMetrics = g.getFontMetrics();
        String name = GameObjects.player.getName();
        g.drawString(name, (int) (xScale * getWidth()) + offSet, (int) ((xScale * getWidth() / 3) - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent());
        //healthbar space and make the bars gray (paint health in red on top of it later)
        g.setColor(Color.GRAY);
        g.fillRect((int) (xScale * getWidth()), (int) (xScale * getWidth() / 3), (int) (2 * xScale * getWidth()), (int) (xScale * getWidth() / 3));
        //resourcebar space
        g.fillRect((int) (xScale * getWidth()), (int) (xScale * getWidth() * 2 / 3), (int) (2 * xScale * getWidth()), (int) (xScale * getWidth() / 3));
        //now draw the actual healthbar that depends on the hitpoints of the player (same starting points as the background bar, same height but adjust width to the percantage hp
        // (current hp/max hp) * bar width
        var hpWidth = GameObjects.player.getCurrentHp() / GameObjects.player.getHitPoints();
        g.setColor(Color.GREEN);
        g.fillRect((int) (xScale * getWidth()), (int) (xScale * getWidth() / 3), (int) (2 * xScale * getWidth() * hpWidth), (int) (xScale * getWidth() / 3));
        // resource bar TODO use different colors for classes resources
        var resourceWidth = GameObjects.player.getCurrentResource() / GameObjects.player.getResource();
        g.setColor(Color.BLUE);
        g.fillRect((int) (xScale * getWidth()), (int) (xScale * getWidth() * 2 / 3), (int) (2 * xScale * getWidth() * resourceWidth), (int) (xScale * getWidth() / 3));
        g.setColor(Color.BLACK);
        //display hp and resource information
        String hpInfo = (int) GameObjects.player.getCurrentHp() + "/" + (int) GameObjects.player.getHitPoints() + " " + (int) (hpWidth * 100) + "%";
        g.drawString(hpInfo, (int) (xScale * getWidth() + offSet), (int) (((xScale * getWidth()) - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent()));
        String resourceInfo = (int) GameObjects.player.getCurrentResource() + "/" + (int) GameObjects.player.getResource() + " " + (int) (resourceWidth * 100) + "%";
        g.drawString(resourceInfo, (int) (xScale * getWidth() + offSet), (int) (((xScale * getWidth() * 5 / 3) - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent()));
        //borders for the bars
        g.drawRect((int) (xScale * getWidth()), (int) (xScale * getWidth() / 3), (int) (2 * xScale * getWidth()), (int) (xScale * getWidth() / 3));
        g.drawRect((int) (xScale * getWidth()), (int) (xScale * getWidth() * 2 / 3), (int) (2 * xScale * getWidth()), (int) (xScale * getWidth() / 3));
        //current target top right
        Toolkit.getDefaultToolkit().sync();
    }

    //provide starting coordinates for enemies
    private void initialState() {
        for (NPC enemy : gameMechanics.getEnemies()) {
            enemy.setPositionX(enemy.getPositionX() * WIDTH);
            enemy.setPositionY(enemy.getPositionY() * HEIGHT);
            EnemyAnimation enemyAnimation = new EnemyAnimation(this, enemy, gameMechanics);
            Thread enemyAnimationThread = new Thread(enemyAnimation);
            enemyAnimationThread.start();
            enemy.setAnimation(enemyAnimation);
            enemyAnimations.add(enemyAnimation);


        }

        animatePlayer();

    }

    private void update() {

        //do positions for animations here
        imageWidth = (int) (xScale * getWidth());
        imageHeight = (int) (yScale * getHeight());

        playerX = playerAnimation.getXc();
        gameMechanics.getEnemies().forEach(enemy -> enemy.setPositionX(enemy.getAnimation().getXc()));

        //update states for different animations

    }


    private void animatePlayer() {
        playerAnimation = new PlayerAnimation(this, gameMechanics);
        Thread animationThread = new Thread(playerAnimation);
        animationThread.start();
    }


    @Override
    public void run() {

        long oldTime, timeDiff, sleep;
        //get current time
        oldTime = System.currentTimeMillis();


        //wait for a notification to set up the drawing
        synchronized (gameMechanics.getGameScreenLock()) {
            try {
                gameMechanics.getGameScreenLock().wait();
                initialState();
                repaint();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }


        }

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

    public Thread getAnimator() {
        return animator;
    }

    public int getImageWidth() {
        return imageWidth;
    }
}
