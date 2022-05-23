package oh_heaven.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;
import oh_heaven.game.RoundInfo;

import static ch.aplu.jgamegrid.GameGrid.delay;

public class HumanPlayer extends Player {

    private Card selectedCard;

    public HumanPlayer(int playerIndex) {
        super(playerIndex);
    }

    @Override
    public Card playOneCard(RoundInfo roundInfo) {
        selectedCard = null;
        hand.setTouchEnabled(true);

        while (selectedCard == null) {
            delay(100);
        }
        hand.setTouchEnabled(false);

        return selectedCard;

    }

    
    public void initialiseCardListener() {
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) {
                selectedCard = card;
                hand.setTouchEnabled(false); }
        };
        hand.addCardListener(cardListener);
    }

}