 package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.RoundInfo;
import oh_heaven.game.player.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class smartStrategy implements AbleToPlayCard {

        @Override
        public Card generateOneMove(Player player, RoundInfo currentRoundInfo) {
            Card legalPlayedCard;
            Oh_Heaven.Suit leadSuit = currentRoundInfo.getLead();
            Hand playerHand = player.getHand();
            Oh_Heaven.Suit trumpSuit = currentRoundInfo.getCurrentTrump();
            ArrayList<Card> trumpSuitCards = playerHand.getCardsWithSuit(trumpSuit);



            player.getHand().sort(Hand.SortType.RANKPRIORITY, false);




            // active mode for the player, meaning that the player currently is trying to pursue a win

            // Check if the current player is the first players to play a card in a round
            if (leadSuit == null) {
                // Check if the player has the trump Suit Cards on hands, need to check with the trump suit of current round
                // from the round info clas
                if (trumpSuitCards != null) {
                    // Sorting the trump suit cards if exist
                    Collections.sort(trumpSuitCards, new Comparator<Card>() {
                        @Override
                        public int compare(Card o1, Card o2) {
                            return o2.getRankId() - o1.getRankId();

                        }
                    });

                    // Card debug mode to check if the ordering exists
                    for (Card eachCard : trumpSuitCards) {
                        System.out.println(eachCard.toString());
                    }

                    legalPlayedCard = trumpSuitCards.get(0);
                } else {
                    player.getHand().sort(Hand.SortType.RANKPRIORITY, false);
                    legalPlayedCard = player.getHand().getLast();
                }
            } // If the current player is not the first player,
            else {
                // Obtain the relevant statistic from roundInfo
                Card currentWinningCard = currentRoundInfo.getCurrentWinningCard();
                ArrayList<Card> leadSuitCard = playerHand.getCardsWithSuit(leadSuit);

                // if the current player has the cards with the lead suit
                if (leadSuitCard!=null) {
                    Collections.sort(leadSuitCard, new Comparator<Card>() {
                        @Override
                        public int compare(Card o1, Card o2) {
                            return o2.getRankId() - o1.getRankId();
                        }
                    });


                    Card biggestCard = leadSuitCard.get(0);
                    Card lowestCard = leadSuitCard.get(leadSuitCard.size()-1);


                    if (rankGreater(biggestCard, currentWinningCard)) {
                        legalPlayedCard = biggestCard;
                    }
                    else {
                        legalPlayedCard = lowestCard;
                    }
                }
                // if the current player does not have the cards with lead suit
                else {

                    // Check if the current player has the trumpsuit cards
                    if (trumpSuitCards!=null) {
                        // Sorting the trump suit cards if exist
                        Collections.sort(trumpSuitCards, new Comparator<Card>() {
                            @Override
                            public int compare(Card o1, Card o2) {
                                return o1.getRankId() - o2.getRankId();
                            }
                        });

                        // Just get the fist card from trumpSuit cards
                        legalPlayedCard = trumpSuitCards.get(0);
                    }
                    else {
                        // Check if the current player does not have the trumpsuit cards
                        player.getHand().sort(Hand.SortType.RANKPRIORITY, false);
                        Card highestHandCard = player.getHand().getLast();
                        if (rankGreater(highestHandCard, currentWinningCard)) {
                            legalPlayedCard = highestHandCard;
                        }
                        else {
                            legalPlayedCard = player.getHand().getFirst();
                        }

                    }


                }
            }


            // Inactive mode for the player, meaning that the player currently is not trying to pursue a win
            return legalPlayedCard;

        }
        // 用于比较两张手牌的大小
        public boolean rankGreater(Card card1, Card card2) {
            return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
        }

}