package oh_heaven.game.playerStrategy;


public class strategyFactory {

    /**  
    *Single Object for the strateFactory Class 
    */
    private static strategyFactory instance = null;

    /**
    *Getter for single point of access
    */
    public static synchronized strategyFactory getInstance(){
        if (instance == null){
            instance = new strategyFactory();
        }
        return instance;
    }

    /**
    * Factory for creating the corresponding Strategy
    */
    public AbleToPlayCard getStrategyImplementation(String strategyType) {

        AbleToPlayCard newStrategy = null;
        String processString = strategyType.toLowerCase();
        
        switch (processString) {
            case "smart":
                newStrategy = new smartStrategy();
                break;
            case "random":
                newStrategy = new randomStrategy();
                break;
            case "legal":
                newStrategy = new legalStrategy();
                break;
            default:
                newStrategy = new legalStrategy();
            };
       
        return newStrategy;
    }
}
