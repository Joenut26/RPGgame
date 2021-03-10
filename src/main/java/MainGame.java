import Main.GameState;

public class MainGame {

    //TODO look into threading
    private static final GameState gameState = new GameState();
    private static boolean gameOver = false;

    public static void main(String[] args) {
        while (!gameOver)
            gameState.gameStateUpdate();
    }
}
