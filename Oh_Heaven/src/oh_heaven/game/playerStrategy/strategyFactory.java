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

        if(processString.equals("smart")){
            newStrategy = new smartStrategy();
        } else if (processString.equals("random")){
            newStrategy = new randomStrategy();
        } else if (processString.equals("legal")){
            newStrategy = new legalStrategy();
        } else {
            newStrategy = new legalStrategy();
        }
       
        return newStrategy;
    }
}
