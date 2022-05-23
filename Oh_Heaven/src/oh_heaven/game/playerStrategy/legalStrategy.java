package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.TrickStatistics;
import oh_heaven.game.player.Player;

import java.util.ArrayList;

public class legalStrategy implements AbleToPlayCard {
    
    // constructor
    public legalStrategy() {
    }


    // Generate one card to play for current subround which is legal to play
    @Override
    public Card generateOneMove(Player player, TrickStatistics currentRoundInfo) {
        Card legalPlayedCard = null;
        Oh_Heaven.Suit leadSuit = currentRoundInfo.getLead();
        Hand playerHand = player.getHand();
        if (leadSuit != null) {
            ArrayList<Card> leadSuitCard = playerHand.getCardsWithSuit(leadSuit);
            if (leadSuitCard.size()!=0) {
                legalPlayedCard = Oh_Heaven.randomCard(leadSuitCard);
            }
            else {
                legalPlayedCard = getRandomFromHand(playerHand);
            }
        }
        else {
            legalPlayedCard = getRandomFromHand(playerHand);

        }

        return legalPlayedCard;
    }
    private Card getRandomFromHand(Hand hand) {
        Card legalPlayedCard = Oh_Heaven.randomCard(hand);
        return legalPlayedCard;
    }
    
}
