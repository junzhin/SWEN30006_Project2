package oh_heaven.game.player;
import ch.aplu.jcardgame.Card;
import oh_heaven.game.RoundInfo;
import oh_heaven.game.playerStrategy.AbleToPlayCard;
import oh_heaven.game.playerStrategy.StrategyType;
import oh_heaven.game.playerStrategy.strategyFactory;

public class NonHumanPlayer extends Player{

    protected final AbleToPlayCard PLAYERSTRATEGY;

    public NonHumanPlayer(int playerIndex,  StrategyType npcStrategy) {
        super(playerIndex);

        // Factory Pattern to create corrseponding strategy
        this.PLAYERSTRATEGY = strategyFactory.getInstance().getStrategyImplementation(npcStrategy);
    }

    @Override
    public Card playOneCard(RoundInfo roundInfo) {
        // TODO Auto-generated method stub
        return PLAYERSTRATEGY.generateOneMove(this,roundInfo);
    }
    
    
}
