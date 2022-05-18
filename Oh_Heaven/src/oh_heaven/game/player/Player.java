package oh_heaven.game.player;

import ch.aplu.jcardgame.Card;
import oh_heaven.game.RoundInfo;


public abstract class Player {

    protected int score; // 记录一场比赛中玩家的当前的分数
    protected int trick; // 当前一场比赛中 的已经赢得回合的数量
    protected int bid; // 在一场比赛中， 预测的能够赢回合的总次数

    protected Card selectedCard = null;

    protected final int PLAYERINDEX; 
    

    public Player (int playerIndex) {
        this.PLAYERINDEX = playerIndex;

    }

    public abstract Card playCard(RoundInfo roundInfo);
    
}

