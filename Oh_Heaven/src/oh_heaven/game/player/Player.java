package oh_heaven.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.TrickStatistics;

public abstract class Player {

    protected int score; // Records the scores of each player
    protected int nbWon; // Records the number of subrounds that the player has won so fars 
    protected int bid; // bid how many tricks in total a player can win in a game
    protected Hand hand; // current player's hand cards

    // card selected by player
    protected Card selectedCard = null;

    protected final int PLAYERINDEX;

    public Player(int playerIndex) {
        this.PLAYERINDEX = playerIndex;
    }

    // Abstract method for playing one card to the trick
    public abstract Card playOneCard(TrickStatistics roundInfo);


    // Getters and Setters
    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player score(int score) {
        setScore(score);
        return this;
    }

    public int getNbWon() {
        return this.nbWon;
    }

    public void setNbWon(int nbWon) {
        this.nbWon = nbWon;
    }

    public Player nbWon(int nbWon) {
        setNbWon(nbWon);
        return this;
    }

    public int getBid() {
        return this.bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public Player bid(int bid) {
        setBid(bid);
        return this;
    }

    public Hand getHand() {
        return this.hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Player hand(Hand hand) {
        setHand(hand);
        return this;
    }

    public Card getSelectedCard() {
        return this.selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Player selectedCard(Card selectedCard) {
        setSelectedCard(selectedCard);
        return this;
    }

    public int getPLAYERINDEX() {
        return this.PLAYERINDEX;
    }
}
