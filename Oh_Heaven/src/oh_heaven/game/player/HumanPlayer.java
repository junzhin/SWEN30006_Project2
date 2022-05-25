package oh_heaven.game.player;
import ch.aplu.jcardgame.Card;
import oh_heaven.game.TrickStatistics;

import static ch.aplu.jgamegrid.GameGrid.delay;


public class HumanPlayer extends Player {

    // Constructor
    public HumanPlayer(int playerIndex) {
        super(playerIndex);
    }

    // Generate one player Card
    @Override
    public Card playOneCard(TrickStatistics trickStatistics) {
        selectedCard = null;
        hand.setTouchEnabled(true);
        // delay for the selecting card
        while (selectedCard == null) {
            delay(100);
        }
        hand.setTouchEnabled(false);

        return selectedCard;
    }


}