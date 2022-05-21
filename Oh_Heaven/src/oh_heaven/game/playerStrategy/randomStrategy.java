package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.RoundInfo;
import oh_heaven.game.player.Player;

public class randomStrategy implements AbleToPlayCard {

    public randomStrategy() {
    }

    @Override
    public Card generateOneMove(Player player, RoundInfo currentRoundInfo) {
        Hand hand = player.getHand();
        return Oh_Heaven.randomCard(hand);
    }
    
}
