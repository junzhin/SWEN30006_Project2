package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.TrickStatistics;
import oh_heaven.game.player.Player;

public interface AbleToPlayCard {
    // signature of generating one move function
    Card generateOneMove(Player player, TrickStatistics currentRoundInfo);
}
