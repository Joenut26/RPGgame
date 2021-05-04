package Main;

import GameMechanics.*;

public class TurnController {

    private final GameMechanics gameMechanics;

    public TurnController(GameMechanics gameMechanics){
        this.gameMechanics = gameMechanics;

    }

    public void updateTurn(){

        gameMechanics.getInitiativeMap().get(gameMechanics.getCurrentTurn()).setTurn(false);
        //up the currentTurn unless it's the last turn. In that case reset it to 0
        if(gameMechanics.getCurrentTurn() < gameMechanics.getInitiativeMap().size() - 1) {
            gameMechanics.setCurrentTurn(gameMechanics.getCurrentTurn() + 1);
        } else {
            gameMechanics.setCurrentTurn(0);
        }
        //now enable turn of the next entity
        gameMechanics.getInitiativeMap().get(gameMechanics.getCurrentTurn()).setTurn(true);
        //reset actionDone
        gameMechanics.getInitiativeMap().get(gameMechanics.getCurrentTurn()).setActionDone(false);

        //if the current enemy is dead skip to the next
        if(gameMechanics.getInitiativeMap().get(gameMechanics.getCurrentTurn()).currentHp <=0){
            updateTurn();
        }

    }


}
