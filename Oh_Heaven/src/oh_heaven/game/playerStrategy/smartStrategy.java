package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.TrickStatistics;
import oh_heaven.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class smartStrategy implements AbleToPlayCard {

    /**
    Constructor
     */
    public smartStrategy() {
    }

    /**
     * Generate one card to play for current subround
     */
    @Override
    public Card generateOneMove(Player player, TrickStatistics currentRoundInfo) {
        Card smartPlayedCard;
        Oh_Heaven.Suit leadSuit = currentRoundInfo.getLead();
        Hand playerHand = player.getHand();
        Oh_Heaven.Suit trumpSuit = currentRoundInfo.getCurrentTrump();
        ArrayList<Card> trumpSuitCards = playerHand.getCardsWithSuit(trumpSuit);



        //player.getHand().sort(Hand.SortType.RANKPRIORITY, false);




        // active mode for the player, meaning that the player currently is trying to pursue a win

        // Check if the current player is the first players to play a card in a round
        if (leadSuit == null) {
            // Check if the player has the trump Suit Cards on hands, need to check with the trump suit of current round
            // from the round info  clas
            smartPlayedCard = getCardFromTrumpSuitList(trumpSuitCards,player,"high");
        } // If the current player is not the first player,
        else {
            // Obtain the relevant statistic from roundInfo
            Card currentWinningCard = currentRoundInfo.getCurrentWinningCard();
            ArrayList<Card> leadSuitCard = playerHand.getCardsWithSuit(leadSuit);

            // if the current player has the cards with the lead suit
            if (leadSuitCard.size()!=0) {
                rankCards(leadSuitCard);

                
                Card biggestCard = leadSuitCard.get(0);
                Card lowestCard = leadSuitCard.get(leadSuitCard.size()-1);


                if (rankGreater(biggestCard, currentWinningCard)) {
                    smartPlayedCard = biggestCard;
                }
                else {
                    smartPlayedCard = lowestCard;
                }
            }
            // if the current player does not have the cards with lead suit
            else {

                // Check if the current player has the trumpsuit cards
                smartPlayedCard = getCardFromTrumpSuitList(trumpSuitCards, player, "low");


            }
        }


        // Inactive mode for the player, meaning that the player currently is not trying to pursue a win
        return smartPlayedCard;

    }
    	// 用于比较两张手牌的大小
	public boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	}

    public void rankCards(ArrayList<Card> cards) {
        Collections.sort(cards, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getRankId() - o2.getRankId();
            }
        });

    }

    public Card getCardFromTrumpSuitList(ArrayList<Card> trumpSuitCards,Player player, String neededCard) {
        if (trumpSuitCards.size()!=0) {
            // Sorting the trump suit cards if exist
            rankCards(trumpSuitCards);


            return trumpSuitCards.get(0);
        } else {
            player.getHand().sort(Hand.SortType.RANKPRIORITY, false);
            System.out.println(
                "+++Start++++++++++++++++++++++++++"
            );
            for (Card eachCard: player.getHand().getCardList()){
                System.out.println(eachCard.toString());
            }
            System.out.println(
                    "+-End++++++++++++++++++++++++++++"
            );
            if (neededCard.equals("high")) {
                System.out.println("give the highest card");
                return player.getHand().getFirst();
            }
            else if (neededCard.equals("low")) {
                System.out.println("enter the low if, get the lowesr card");
                return player.getHand().getLast();
            }
        }
        return null;
    }
    
}
