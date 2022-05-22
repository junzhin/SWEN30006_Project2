package oh_heaven.game.playerStrategy;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven;
import oh_heaven.game.RoundInfo;
import oh_heaven.game.player.Player;

import java.util.ArrayList;

public class legalStrategy implements AbleToPlayCard {

    public legalStrategy() {
    }

    @Override
    public Card generateOneMove(Player player, RoundInfo currentRoundInfo) {
        Card legalPlayedCard = null;
        Oh_Heaven.Suit leadSuit = currentRoundInfo.getLead();
        Hand playerHand = player.getHand();
        if (leadSuit == null) {
            legalPlayedCard = Oh_Heaven.randomCard(playerHand);
        }
        else {

            ArrayList<Card> leadSuitCard = playerHand.getCardsWithSuit(leadSuit);
            if (leadSuitCard.size()!=0) {
                legalPlayedCard = Oh_Heaven.randomCard(leadSuitCard);
            }
            else {
                legalPlayedCard = Oh_Heaven.randomCard(playerHand);
            }
        }

        return legalPlayedCard;
    }

    
}
