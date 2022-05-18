package oh_heaven.game.player;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.RoundInfo;

public class HumanPlayer extends Player {

    public HumanPlayer(int playerIndex) {
        super(playerIndex);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Card playCard(RoundInfo roundInfo) {
        
        hand.setTouchEnabled(true);

        selectedCard = null;

        hand.setTouchEnabled(false);

        return selectedCard;


  
    }

}