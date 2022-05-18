package oh_heaven.game.player;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.RoundInfo;



public abstract class Player {

    protected int score; // 记录一场比赛中玩家的当前的分数
    protected int trick; // 当前一场比赛中 的已经赢得回合的数量
    protected int bid; // 在一场比赛中， 预测的能够赢回合的总次数
    protected Hand hand; // 当前玩家的手牌

    protected Card selectedCard = null;

    protected final int PLAYERINDEX; 
    

    public Player (int playerIndex) {
        this.PLAYERINDEX = playerIndex;
    }

    // Abstract Signatures
    public abstract Card playCard(RoundInfo roundInfo);



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

    public int getTrick() {
        return this.trick;
    }

    public void setTrick(int trick) {
        this.trick = trick;
    }

    public Player trick(int trick) {
        setTrick(trick);
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

