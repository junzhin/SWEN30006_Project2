package oh_heaven.game.player;
import java.util.Optional;
 

public class PlayerFactory {

    private static PlayerFactory instance = null;

    public static synchronized PlayerFactory getInstance() {
        if (instance == null) {
            instance = new PlayerFactory();
        }
        return instance;
    }

    public Player getPlayerFactoryImplementation(String playerType, int playerIndex, Optional<String> StrategyType) {
        Player newPlayer  = null;
        //  StrategyType.isPresent()
        String processedStrategyType = StrategyType.isPresent()? StrategyType.get(): null; 

    
        if (playerType.equals("human")) {
            newPlayer = new HumanPlayer(playerIndex);
        }
        else if (playerType.equals("legal")) {
            newPlayer = new NonHumanPlayer(playerIndex,  StrategyType);
        }
        return newPlayer;
    }
}
