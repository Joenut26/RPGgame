package Animations;

import Displays.GameScreen;
import Main.GameEntity;

import java.awt.*;
import java.util.ArrayList;

public interface Animation extends Runnable {

    void terminate();

    void run();

    void animation(ArrayList<Image> imageArrayList, GameEntity gameEntity, GameScreen gameScreen, double xDiff);






}

