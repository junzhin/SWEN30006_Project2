package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.RoundInfo;
import oh_heaven.game.player.Player;

public interface AbleToPlayCard {
    // signature of generating one move
    Card generateOneMove(Player player, RoundInfo currentRoundInfo);
}
