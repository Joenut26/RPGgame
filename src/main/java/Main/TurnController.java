package Main;

import GameMechanics.*;

import java.util.HashMap;

public class TurnController {

    private final GameMechanics gameMechanics;

    public TurnController(GameMechanics gameMechanics){
        this.gameMechanics = gameMechanics;

    }

    public void updateTurn(){
        if(gameMechanics.getInitiativeMap().get(gameMechanics.getCurrentTurn()).getId().equals("Player")){
            GameObjects.player.setTurn(false);
        }
        gameMechanics.getInitiativeMap().get(gameMechanics.getCurrentTurn()).setTurn(false);
        if(gameMechanics.getCurrentTurn() < gameMechanics.getInitiativeMap().size() - 1) {
            gameMechanics.setCurrentTurn(gameMechanics.getCurrentTurn() + 1);
        } else {
            gameMechanics.setCurrentTurn(0);
        }
        gameMechanics.getInitiativeMap().get(gameMechanics.getCurrentTurn()).setTurn(true);
        if(gameMechanics.getInitiativeMap().get(gameMechanics.getCurrentTurn()).getId().equals("Player")){
            GameObjects.player.setTurn(true);
        }
    }


}
