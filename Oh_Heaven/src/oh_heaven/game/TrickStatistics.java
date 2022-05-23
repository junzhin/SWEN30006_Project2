package oh_heaven.game;
import ch.aplu.jcardgame.Card;
import oh_heaven.game.Oh_Heaven.Suit;

public class TrickStatistics {
    private Suit currentTrump = null;
    private int currentWinner;
    private Card currentWinningCard;
    private Suit leadSuit;

    private int[] ScoresForPlayers;

    public TrickStatistics() {
        // this.currentTrump = Trump;
        this.ScoresForPlayers = null; 
    }

    public Suit getCurrentTrump() {
        return this.currentTrump;
    }

    public void setCurrentTrump(Suit currentTrump) {
        this.currentTrump = currentTrump;
    }

    public TrickStatistics currentTrump(Suit currentTrump) {
        setCurrentTrump(currentTrump);
        return this;
    }

    public int getCurrentWinner() {
        return this.currentWinner;
    }

    public void setCurrentWinner(int currentWinner) {
        this.currentWinner = currentWinner;
    }

    public TrickStatistics currentWinner(int currentWinner) {
        setCurrentWinner(currentWinner);
        return this;
    }

    public Card getCurrentWinningCard() {
        return this.currentWinningCard;
    }

    public void setCurrentWinningCard(Card currentWinningCard) {
        this.currentWinningCard = currentWinningCard;
    }

    public TrickStatistics currentWinningCard(Card currentWinningCard) {
        setCurrentWinningCard(currentWinningCard);
        return this;
    }

    public Suit getLead() {
        return this.leadSuit;
    }

    public void setLead(Suit lead) {
        this.leadSuit = lead;
    }

    public TrickStatistics lead(Suit lead) {
        setLead(lead);
        return this;
    }

    public int[] getScoresForPlayers() {
        return this.ScoresForPlayers;
    }

    public void setScoresForPlayers(int[] ScoresForPlayers) {
        this.ScoresForPlayers = ScoresForPlayers;
    }

    public TrickStatistics ScoresForPlayers(int[] ScoresForPlayers) {
        setScoresForPlayers(ScoresForPlayers);
        return this;
    }
    
    

    @Override
    public String toString() {
        return "{" +
            " currentTrump='" + getCurrentTrump() + "'" +
            ", currentWinner='" + getCurrentWinner() + "'" +
            ", currentWinningCard='" + getCurrentWinningCard() + "'" +
            ", lead='" + getLead() + "'" +
            ", ScoresForPlayers='" + getScoresForPlayers() + "'" +
            "}";
    }

    
}
