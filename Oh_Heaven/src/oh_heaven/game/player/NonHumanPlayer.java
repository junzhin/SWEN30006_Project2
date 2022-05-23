package oh_heaven.game.player;
import ch.aplu.jcardgame.Card;
import oh_heaven.game.RoundInfo;
import oh_heaven.game.playerStrategy.AbleToPlayCard;
import oh_heaven.game.playerStrategy.strategyFactory;

public class NonHumanPlayer extends Player{
    
    // Playet Strategy for Non-human player
    protected final AbleToPlayCard PLAYERSTRATEGY;

    // Constructor
    public NonHumanPlayer(int playerIndex,  String npcStrategy) {
        super(playerIndex);
        
        // Factory Pattern to create corrseponding strategy
        this.PLAYERSTRATEGY = strategyFactory.getInstance().getStrategyImplementation(npcStrategy);
    }

    // Generate one player Card
    @Override
    public Card playOneCard(RoundInfo roundInfo) {
        // stratergy pattern to play the card based on the stratergy the play has.
        return PLAYERSTRATEGY.generateOneMove(this,roundInfo);
    }
    
    
}
