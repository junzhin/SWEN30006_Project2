package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import oh_heaven.game.player.HumanPlayer;
import oh_heaven.game.player.Player;
import oh_heaven.game.player.PlayerFactory;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")

public class Oh_Heaven extends CardGame {
	/*=============================================================================================================== */

	/*******Defintions of Graphical Settings, Constants and Variables ********/
	// Defintion of Suit
	public enum Suit {
		SPADES, HEARTS, DIAMONDS, CLUBS
	}

	// Defintion of Rank
	public enum Rank {
		// Reverse order of rank importance (see rankGreater() below)
		// Order of cards is tied to card images
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
	}

	// Defintions of graphical setting
	final String trumpImage[] = { "bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif" };

	private final String version = "1.0";
	// number of players (default: 4)
	public final int nbPlayers = 4;
	// number of cards for each round
	public int nbStartCards = 13;
	// number of round for each game
	public int nbRounds = 3;
	// Additional Bonus Score 
	public final int madeBidBonus = 10;

	// Storing player types and player
	private ArrayList<String> playerType = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();

	// Dafault Random seet
	public static int seed = 30006;

	// Graphical setting
	private final int handWidth = 400;
	private final int trickWidth = 40;
	private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
	private final Location[] handLocations = {
			new Location(350, 625),
			new Location(75, 350),
			new Location(350, 75),
			new Location(625, 350)
	};
	private final Location[] scoreLocations = {
			new Location(575, 675),
			new Location(25, 575),
			new Location(575, 25),
			// new Location(650, 575)
			new Location(575, 575)
	};
	//General Game Graphical Interface setting
	private Actor[] scoreActors = { null, null, null, null };
	private final Location trickLocation = new Location(350, 350);
	private final Location textLocation = new Location(350, 450);
	private  int thinkingTime = 2000;
	private Hand[] hands;
	private Location hideLocation = new Location(-500, -500);
	private Location trumpsActorLocation = new Location(50, 50);

	// Default boolean variable for enforceRule setting
	private boolean enforceRules = false;

	public void setStatus(String string) {
		setStatusText(string);
	}

	// Player scores' array
	private int[] scores = new int[nbPlayers];
	// Current Wining statistic for each player in one round
	private int[] tricks = new int[nbPlayers];
	// Prediction of the number of wining time for one game
	private int[] bids = new int[nbPlayers];

	Font bigFont = new Font("Serif", Font.BOLD, 36);

	// Setting random seed for the game behaviour
	private static Random random = new Random(seed);

	/*****End of  Defintions of Graphical Settings, Constants and Variables *****/

	/*=============================================================================================================== */

	/******************Helper Functions**************/

	// Distribute the cards at the start of each subround
	private void dealingOut(Hand[] hands, int nbPlayers, int nbCardsPerPlayer) {
		Hand pack = deck.toHand(false);
		// pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
		for (int i = 0; i < nbCardsPerPlayer; i++) {
			for (int j = 0; j < nbPlayers; j++) {
				if (pack.isEmpty())
					return;
				Card dealt = randomCard(pack);
				// System.out.println("Cards = " + dealt);
				dealt.removeFromHand(false);
				hands[j].insert(dealt, false);
				// dealt.transfer(hands[j], true);
			}
		}
	}

	// Comparing the cards' rank
	public boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	}

	// return random Enum value
	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}

	// return random Card from Hand
	public static Card randomCard(Hand hand) {
		int x = random.nextInt(hand.getNumberOfCards());
		return hand.get(x);
	}

	// return random Card from ArrayList
	public static Card randomCard(ArrayList<Card> list) {
		int x = random.nextInt(list.size());
		return list.get(x);
	}




	/****************** End Of Helper Functions **************/

	/*=============================================================================================================== */

	/****************** Initialization and Updating **************/
	/***
	 * Reset the scores of each player
	 */
	private void initScores() {
		for (int i = 0; i < nbPlayers; i++) {
			scores[i] = 0;
		}
	}

	/**
	 * Reset the winning time for each players
	 */
	private void initTricks() {
		for (int i = 0; i < nbPlayers; i++) {
			tricks[i] = 0;
		}
	}

	/**
	 * Include the functionality of updating the initScoreForGraphicalPurpose()
	 */
	private void initinitScoreForGraphicalPurpose() {
		
		for (int i = 0; i < nbPlayers; i++) {
			// scores[i] = 0;
			String text = "[" + String.valueOf(scores[i]) + "]" + String.valueOf(tricks[i]) + "/"
					+ String.valueOf(bids[i]);
			scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
			addActor(scoreActors[i], scoreLocations[i]);
		}
	}

	private void initialiseProperties(Properties properties) {

		// Load the relevant parameters from the peroperties files before starting the game;

		String tempStoringVariable = properties.getProperty("enforceRules");
		if (tempStoringVariable != null) {
			this.enforceRules = Boolean.parseBoolean(tempStoringVariable);
		}

		tempStoringVariable = properties.getProperty("nbStartCards");
		if (tempStoringVariable != null){
			this.nbStartCards = Integer.parseInt(tempStoringVariable);
		}

		tempStoringVariable = properties.getProperty("rounds");
		if (tempStoringVariable != null) {
			this.nbRounds = Integer.parseInt(tempStoringVariable);
		}

		tempStoringVariable = properties.getProperty("seed");
		// reassign the seed value to random class
		if (tempStoringVariable != null){
			seed = Integer.parseInt(tempStoringVariable);
			random = new Random(seed);
		}

		tempStoringVariable = properties.getProperty("thinkingTime");
		// reassign the seed value to random class
		if (tempStoringVariable != null) {
			this.thinkingTime = Integer.parseInt(tempStoringVariable);
		}

		playerType.addAll(PropertiesLoader.loadPlayerTypes(properties));
	}

	/**
	 * Initialize the bids value for each player for a game of three rounds
	 * 
	 * @param trumps
	 * @param nextPlayer
	 */
	private void initBids(Suit trumps, int nextPlayer) {
		int total = 0;
		for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
			int iP = i % nbPlayers;
			bids[iP] = nbStartCards / 4 + random.nextInt(2);
			total += bids[iP];
		}
		if (total == nbStartCards) { // Force last bid so not every bid possible
			int iP = (nextPlayer + nbPlayers) % nbPlayers;
			if (bids[iP] == 0) {
				bids[iP] = 1;
			} else {
				bids[iP] += random.nextBoolean() ? -1 : 1;
			}
		}
		// for (int i = 0; i < nbPlayers; i++) {
		// bids[i] = nbStartCards / 4 + 1;
		// }
	}

	private Card selected;

	private void initRound() {
		hands = new Hand[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			hands[i] = new Hand(deck);
			players.get(i).setHand(hands[i]);
		}
		dealingOut(hands, nbPlayers, nbStartCards);
		for (int i = 0; i < nbPlayers; i++) {
			hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		}
		/*check if player is a humna player, then set card listener*/
        setHumanPlayer();
		// graphics
		RowLayout[] layouts = new RowLayout[nbPlayers];
		System.out.println("nbPlayers:"+nbPlayers);
		for (int i = 0; i < nbPlayers; i++) {
			System.out.println("index:"+i);
			layouts[i] = new RowLayout(handLocations[i], handWidth);
			layouts[i].setRotationAngle(90 * i);
			hands[i].setView(this, layouts[i]);
			hands[i].setTargetArea(new TargetArea(trickLocation));
			hands[i].draw();
		}

		// for (int i = 1; i < nbPlayers; i++) // This code can be used to visually hide
		// the cards in a hand (make them face down)
		// hands[i].setVerso(true); // You do not need to use or change this code.
		// End graphics
	}
    private void setHumanPlayer() {
        for (Player player: players) {
			if (player instanceof HumanPlayer) {
                System.out.println("enter humna player");
				initialiseCardListener(player);
			}
		}
    }

    private void initialiseCardListener(Player player) {
        HumanPlayer humanPlayer = (HumanPlayer)player;
        CardListener cardListener = new CardAdapter()  // Human Player plays card
        {
            public void leftDoubleClicked(Card card) {
                humanPlayer.setSelectedCard(card);
                humanPlayer.getHand().setTouchEnabled(false); }
        };
        humanPlayer.getHand().addCardListener(cardListener);
    }


	/**
	 * Rendering the graphical display of each players' scores
	 * @param player
	 */
	private void updateScoreGraphics(int player) {
		removeActor(scoreActors[player]);
		String text = "[" + String.valueOf(scores[player]) + "]" + String.valueOf(tricks[player]) + "/"
				+ String.valueOf(bids[player]);
		scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);   
		addActor(scoreActors[player], scoreLocations[player]);
	}


	/**
	 * Update the scores of each player at the end of one complete round
	 */
	private void updateScores(TrickStatistics roundinfo) {
		for (int i = 0; i < nbPlayers; i++) {
			scores[i] += tricks[i];
			if (tricks[i] == bids[i])
				scores[i] += madeBidBonus;
		}

		roundinfo.setScoresForPlayers(scores);
		 
	}

	/****************** End of Initialization and Updating **************/

	/*=============================================================================================================== */

	/****One Round of the game ****/

	private void playRound(TrickStatistics trickStatistics) {


		// Select and display trump suit
		// For the first subround, a lead player is randomly chosen
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/" + trumpImage[trumps.ordinal()]);

		

		addActor(trumpsActor, trumpsActorLocation);// Graphical Interface for player and its player location
		// End trump suit
		
		Hand trick; // cards played before for one round
		int winner;// currentWinner for a SubRound 
		Card winningCard;// currentWinnning Card for a SubRound
		Suit lead;// The suit of the leading

		trickStatistics.setCurrentTrump(trumps);


		int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round

		initBids(trumps, nextPlayer);
		// initScore();
		for (int i = 0; i < nbPlayers; i++)
			updateScoreGraphics(i);

	
		// One  Round contains 13 sub-round, which is the number of the start cards
		for (int i = 0; i < nbStartCards; i++) {

			// Initialize the variables ready for one sub-round
			trick = new Hand(deck);
			selected = null;
			lead = null;
			trickStatistics.setLead(null);
			winner = 0;
			winningCard = null;


			// One complete sub-round
			for (int j = 0; j < nbPlayers; j++){

				// if it is not lead of a sub-round
				if (lead != null && ++nextPlayer >= nbPlayers){
					nextPlayer = 0; // From last back to first
				}

				setStatusText("Player " + nextPlayer + " thinking...");
		        delay(thinkingTime);
				
				selected = players.get(nextPlayer).playOneCard(trickStatistics);

				
				// Follow with selected card
				trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
				trick.draw();
				selected.setVerso(false); // In case it is upside down

				// if the current turn is the lead of the current subround
				if (lead == null){
					// No restrictions on the card being lead
					lead = (Suit) selected.getSuit();
					winner = nextPlayer;
					winningCard = selected;
				} else {
					// Check: Following card must follow suit if possible, whic is the 
					// game rule violation checking
					if (selected.getSuit() != lead && hands[nextPlayer].getNumberOfCardsWithSuit(lead) > 0) {
						// Rule violation
						String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
					System.out.println(violation);
						if (enforceRules)
							try {
								throw (new BrokeRuleException(violation));
							} catch (BrokeRuleException e) {
								e.printStackTrace();
								System.out.println("A cheating player spoiled the game!");
								System.exit(0);
							}
						
					}
				}

				// Transfer of played Cards to trick array
				selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				System.out.println("winning: " + winningCard);
				System.out.println(" played: " + selected);

				// Update the winner and the corrsesponding winning card
				if ( // beat current winner with higher card
				(selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
				// trumped when non-trump was winning
						(selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					System.out.println("NEW WINNER");
					winner = nextPlayer;
					winningCard = selected;
				}

				// Update the round-Info 
				trickStatistics.setLead(lead);
				trickStatistics.setCurrentWinner(winner);
				trickStatistics.setCurrentWinningCard(winningCard);
			}

			// Graphical and game bebviouring settings
			delay(600);
			trick.setView(this, new RowLayout(hideLocation, 0));
			trick.draw();
			nextPlayer = winner;
			
			setStatusText("Player " + nextPlayer + " wins trick.");
			tricks[nextPlayer]++;
			updateScoreGraphics(nextPlayer);
		}
		removeActor(trumpsActor);
	}

	/**** End of One Round of the game ****/
	/*
	 * ==============================================================================================================*/


	private void initialisePlayers() {
		for (int i=0;i<nbPlayers;i++) {
			String currentPlayerType = playerType.get(i);
			players.add(PlayerFactory.getInstance().getPlayerFactoryImplementation(currentPlayerType, i));
		}
		
	}

	/**** Running Process of the Game Program *****/
	public Oh_Heaven(Properties gameProperies) {
		super(700, 700, 30);
		setTitle("Oh_Heaven (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
		setStatusText("Initializing...");

		// Loading the game settings from the file and initialize the players
		initialiseProperties(gameProperies);
		initialisePlayers();

		// Initialize the Scores and graphical interface for score displaying
		initScores();
		initinitScoreForGraphicalPurpose();

		// 开始进行每一轮的记录
		for (int i = 0; i < nbRounds; i++) {

			// Initialize the roundData Recorder 
			TrickStatistics roundInfo = new TrickStatistics();

			initTricks();
			initRound();
			playRound(roundInfo);
			updateScores(roundInfo);
		}

		for (int i = 0; i < nbPlayers; i++)
			updateScoreGraphics(i);
		int maxScore = 0;
		for (int i = 0; i < nbPlayers; i++)
			if (scores[i] > maxScore)
				maxScore = scores[i];

		Set<Integer> winners = new HashSet<Integer>();

		for (int i = 0; i < nbPlayers; i++)
			if (scores[i] == maxScore)
				winners.add(i);
		String winText;

		if (winners.size() == 1) {
			winText = "Game over. Winner is player: " +
					winners.iterator().next();
		} else {
			winText = "Game Over. Drawn winners are players: " +
					String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toSet()));
		}
		addActor(new Actor("sprites/gameover.gif"), textLocation);
		setStatusText(winText);
		refresh();
	}

	/**** End of Running Process of the Game Program *****/
	/*=============================================================================================================== */


	/**** Main Function of the game ****/
	public static void main(String[] args) {
		// System.out.println("Working Directory = " + System.getProperty("user.dir"));
		final Properties properties;
		if (args == null || args.length == 0) { 
			properties = PropertiesLoader.loadPropertiesFile(null);
		} else {
			properties = PropertiesLoader.loadPropertiesFile(args[0]);
		}
		// Add the parameters to game main program and Run the main game Program
	
		new Oh_Heaven(properties);
	}

	// Getters and Setters


}
