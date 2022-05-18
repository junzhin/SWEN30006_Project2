package oh_heaven.game.player;

import java.lang.management.PlatformLoggingMXBean;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.RoundInfo;
import oh_heaven.game.playerStrategy.AbleToPlayCard;

public class NonHumanPlayer extends Player{

    protected final AbleToPlayCard PLAYERSTRATEGY;

    public NonHumanPlayer(int playerIndex, String npcStrategy) {
        super(playerIndex);

        // Factory Pattern to create corrseponding strategy
        this.PLAYERSTRATEGY = null;
    }

    @Override
    public Card playCard(RoundInfo roundInfo) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
}
