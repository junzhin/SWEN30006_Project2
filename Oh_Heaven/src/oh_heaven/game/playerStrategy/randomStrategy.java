package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.TrickStatistics;
import oh_heaven.game.player.Player;

public class randomStrategy implements AbleToPlayCard {
    /**
    Constructor
     */
    public randomStrategy() {
    }

    /**
     * Generate one card to play randomly for current subround
     */
    @Override
    public Card generateOneMove(Player player, TrickStatistics currentTrickStatistics) {
        Hand hand = player.getHand();
        return Oh_Heaven.randomCard(hand);
    }
    
}
