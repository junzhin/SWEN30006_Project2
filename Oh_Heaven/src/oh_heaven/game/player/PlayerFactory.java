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

    public Player getPlayerFactoryImplementation(String playerType, int playerIndex) {
        Player newPlayer  = null;

    
        if (playerType.equals("human")) {
            newPlayer = new HumanPlayer(playerIndex);
        }
        else if (playerType.equals("legal")) {
            newPlayer = new NonHumanPlayer(playerIndex, "legal");
        } else if (playerType.equals("smart")) {
            newPlayer = new NonHumanPlayer(playerIndex, "smart");
        } else {
            newPlayer = new NonHumanPlayer(playerIndex, "legal");
        }
        return newPlayer;
    }
}
