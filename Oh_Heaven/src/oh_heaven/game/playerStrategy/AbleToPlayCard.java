package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.RoundInfo;

public interface AbleToPlayCard {
    public Card generateOneMove(RoundInfo currentRoundInfo);
}
