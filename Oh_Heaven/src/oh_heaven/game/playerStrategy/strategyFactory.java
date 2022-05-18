package oh_heaven.game.playerStrategy;

enum StrategyType {
    smart,
    random,
    legal,
}

public class strategyFactory {
    private static strategyFactory instance = null;
    private AbleToPlayCard newStrategy = null;
    
    public static synchronized strategyFactory getInstance(){
        if (instance == null){
            instance = new strategyFactory();
        }
        return instance;
    }

    public AbleToPlayCard getDisneyAdapter(StrategyType strategyType) {
        
        switch (strategyType) {
            case smart:
                newStrategy = new smartStrategy();
                break;
            case random:
                newStrategy = new randomStrategy();
                break;
            case legal:
                newStrategy = new legalStrategy();
                break;
            default:
                newStrategy = new legalStrategy();
            };
       
        return newStrategy;
    }
}
