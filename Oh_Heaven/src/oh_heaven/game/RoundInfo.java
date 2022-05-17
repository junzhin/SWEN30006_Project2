package oh_heaven.game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.Oh_Heaven.Suit;

public class RoundInfo {
    private Suit currentTrump;

    private int currentWinner;
    private Card currentWinningCard;
    private Suit lead;

    private HashMap<Integer, HashSet<Card>> cardsPlayed;

    private ArrayList<Integer> ScoresForPlayers;

    public RoundInfo(Suit Trump) {
        this.currentTrump = Trump;
        this.cardsPlayed = new HashMap<>();
        this.ScoresForPlayers = new ArrayList<>();
}
 

// Getters and Setters
public void cardPlayed(int player, Card playedCard){

    if (cardsPlayed.containsKey(player)) {
        cardsPlayed.get(player).add(playedCard);
    } else {
        HashSet<Card> cards = new HashSet<>();
        cards.add(playedCard);
        cardsPlayed.put(player, cards);
    }

}


    public Suit getCurrentTrump() {
        return this.currentTrump;
    }

    public void setCurrentTrump(Suit currentTrump) {
        this.currentTrump = currentTrump;
    }

    public RoundInfo currentTrump(Suit currentTrump) {
        setCurrentTrump(currentTrump);
        return this;
    }

    public int getCurrentWinner() {
        return this.currentWinner;
    }

    public void setCurrentWinner(int currentWinner) {
        this.currentWinner = currentWinner;
    }

    public RoundInfo currentWinner(int currentWinner) {
        setCurrentWinner(currentWinner);
        return this;
    }

    public Card getCurrentWinningCard() {
        return this.currentWinningCard;
    }

    public void setCurrentWinningCard(Card currentWinningCard) {
        this.currentWinningCard = currentWinningCard;
    }

    public RoundInfo currentWinningCard(Card currentWinningCard) {
        setCurrentWinningCard(currentWinningCard);
        return this;
    }

    public Suit getLead() {
        return this.lead;
    }

    public void setLead(Suit lead) {
        this.lead = lead;
    }

    public RoundInfo lead(Suit lead) {
        setLead(lead);
        return this;
    }

    public HashMap<Integer,HashSet<Card>> getCardsPlayed() {
        return this.cardsPlayed;
    }

    public void setCardsPlayed(HashMap<Integer,HashSet<Card>> cardsPlayed) {
        this.cardsPlayed = cardsPlayed;
    }

    public RoundInfo cardsPlayed(HashMap<Integer,HashSet<Card>> cardsPlayed) {
        setCardsPlayed(cardsPlayed);
        return this;
    }

    public ArrayList<Integer> getScoresForPlayers() {
        return this.ScoresForPlayers;
    }

    public void setScoresForPlayers(ArrayList<Integer> ScoresForPlayers) {
        this.ScoresForPlayers = ScoresForPlayers;
    }

    public RoundInfo ScoresForPlayers(ArrayList<Integer> ScoresForPlayers) {
        setScoresForPlayers(ScoresForPlayers);
        return this;
    }


}
