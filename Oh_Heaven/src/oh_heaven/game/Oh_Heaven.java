package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

import javax.naming.InitialContext;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {

	public enum Suit {
		SPADES, HEARTS, DIAMONDS, CLUBS
	}

	public enum Rank {
		// Reverse order of rank importance (see rankGreater() below)
		// Order of cards is tied to card images
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
	}

	final String trumpImage[] = { "bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif" };

	static public final int seed = 30006;
	static final Random random = new Random(seed);

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

	public boolean rankGreater(Card card1, Card card2) {
		return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
	}

	private final String version = "1.0";
	public final int nbPlayers = 4;
	public  int nbStartCards = 13;
	public final int nbRounds = 3;
	public final int madeBidBonus = 10;
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
	// 记录游戏交互界面玩家的当前的分数的信息
	private Actor[] scoreActors = { null, null, null, null };
	private final Location trickLocation = new Location(350, 350);
	private final Location textLocation = new Location(350, 450);
	private final int thinkingTime = 2000;
	private Hand[] hands;
	private Location hideLocation = new Location(-500, -500);
	private Location trumpsActorLocation = new Location(50, 50);
	private boolean enforceRules = false;

	public void setStatus(String string) {
		setStatusText(string);
	}

	// 记录玩家的分数
	private int[] scores = new int[nbPlayers];
	// 当前一场比赛中 的已经赢得回合的数量
	private int[] tricks = new int[nbPlayers];
	// 在一场比赛中， 预测的能够赢回合的总次数 （一共三个回合）
	private int[] bids = new int[nbPlayers];

	Font bigFont = new Font("Serif", Font.BOLD, 36);

	private void initScore() {
		for (int i = 0; i < nbPlayers; i++) {
			// scores[i] = 0;
			String text = "[" + String.valueOf(scores[i]) + "]" + String.valueOf(tricks[i]) + "/"
					+ String.valueOf(bids[i]);
			scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
			addActor(scoreActors[i], scoreLocations[i]);
		}
	}

	/**
	 * 更新用于rendering 的 Scores 的 Array， ！大概率没有用
	 * @param player
	 */
	private void updateScoreGraphics(int player) {
		removeActor(scoreActors[player]);
		String text = "[" + String.valueOf(scores[player]) + "]" + String.valueOf(tricks[player]) + "/"
				+ String.valueOf(bids[player]);
		scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);   
		addActor(scoreActors[player], scoreLocations[player]);
	}


	/***
	 *  复原用于记录的scores 的 array
	 */
	private void initScores() {
		for (int i = 0; i < nbPlayers; i++) {
			scores[i] = 0;
		}
	}


	/**
	 *  更新玩家对应当前的游戏分数
	 */
	private void updateScores() {
		for (int i = 0; i < nbPlayers; i++) {
			scores[i] += tricks[i];
			if (tricks[i] == bids[i])
				scores[i] += madeBidBonus;
		}
	}

	/**
	 *  重置记录赢得比赛次数的array， 每一回合重置一次
	 */
	private void initTricks() {
		for (int i = 0; i < nbPlayers; i++) {
			tricks[i] = 0;
		}
	}

	
	/**
	 *  初始化 bids 值
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
		}
		dealingOut(hands, nbPlayers, nbStartCards);
		for (int i = 0; i < nbPlayers; i++) {
			hands[i].sort(Hand.SortType.SUITPRIORITY, true);
		}
		// Set up human player for interaction
		CardListener cardListener = new CardAdapter() // Human Player plays card
		{
			public void leftDoubleClicked(Card card) {
				selected = card;
				hands[0].setTouchEnabled(false);
			}
		};
		hands[0].addCardListener(cardListener);
		// graphics
		RowLayout[] layouts = new RowLayout[nbPlayers];
		for (int i = 0; i < nbPlayers; i++) {
			layouts[i] = new RowLayout(handLocations[i], handWidth);
			layouts[i].setRotationAngle(90 * i);
			// layouts[i].setStepDelay(10);
			hands[i].setView(this, layouts[i]);
			hands[i].setTargetArea(new TargetArea(trickLocation));
			hands[i].draw();
		}
		// for (int i = 1; i < nbPlayers; i++) // This code can be used to visually hide
		// the cards in a hand (make them face down)
		// hands[i].setVerso(true); // You do not need to use or change this code.
		// End graphics
	}

	private void playRound() {


		// Select and display trump suit
		// 随机选择random trump Suit
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/" + trumpImage[trumps.ordinal()]);

		addActor(trumpsActor, trumpsActorLocation);//图像交互相关
		// End trump suit
		
		Hand trick; // 这一回合出过的牌
		int winner;// 这一回合的赢家
		Card winningCard;// 决胜的牌
		Suit lead;// 这一回合第一个人错的

		RoundInfo roundInfo = new RoundInfo(trumps);

		int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round

		initBids(trumps, nextPlayer);
		// initScore();
		for (int i = 0; i < nbPlayers; i++)
			updateScoreGraphics(i);

	
		// 当前回合的Lead
		for (int i = 0; i < nbStartCards; i++) {
			trick = new Hand(deck);
			selected = null;
			// if (false) {
			// 用于原始版本的出牌决策， 后期需要换掉 当前回合的lead
			if (0 == nextPlayer) { // Select lead depending on player type
				hands[0].setTouchEnabled(true);
				setStatus("Player 0 double-click on card to lead.");
				while (null == selected)
					delay(100);
			} else {
				setStatusText("Player " + nextPlayer + " thinking...");
				delay(thinkingTime);
				selected = randomCard(hands[nextPlayer]);
			}



			// Lead with selected card
			trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
			trick.draw();
			selected.setVerso(false);

			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;

			// End Lead

			// 更新数据到round info， 首玩家
			roundInfo.cardPlayed(nextPlayer, selected);
			roundInfo.setLead(lead);
			roundInfo.setCurrentWinner(winner);
			roundInfo.setCurrentWinningCard(winningCard);
			//TODO  更新 players 的分数

			// 其他players的出牌逻辑
			for (int j = 1; j < nbPlayers; j++) {
				if (++nextPlayer >= nbPlayers)
					nextPlayer = 0; // From last back to first
				selected = null;
				// 用于原始版本的出牌决策， 后期需要换掉 当前回合的lead
				// if (false) {
				if (0 == nextPlayer) {
					hands[0].setTouchEnabled(true);
					setStatus("Player 0 double-click on card to follow.");
					while (null == selected)
						delay(100);
				} else {
					setStatusText("Player " + nextPlayer + " thinking...");
					delay(thinkingTime);
					selected = randomCard(hands[nextPlayer]);
				}
				// Follow with selected card
				trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
				trick.draw();
				selected.setVerso(false); // In case it is upside down
				// Check: Following card must follow suit if possible
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

					// 更新数据到round info 每一个玩家
					roundInfo.cardPlayed(nextPlayer, selected);
					roundInfo.setLead(lead);
					roundInfo.setCurrentWinner(winner);
					roundInfo.setCurrentWinningCard(winningCard);

					// TODO 更新 players 的分数
				}

				// End Check
				selected.transfer(trick, true); // transfer to trick (includes graphic effect)
				System.out.println("winning: " + winningCard);
				System.out.println(" played: " + selected);
				// System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " +
				// (13 - winningCard.getRankId()));
				// System.out.println(" played: suit = " + selected.getSuit() + ", rank = " +
				// (13 - selected.getRankId()));
				if ( // beat current winner with higher card
				(selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
				// trumped when non-trump was winning
						(selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
					System.out.println("NEW WINNER");
					winner = nextPlayer;
					winningCard = selected;
				}
				// End Follow
			}

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

	public Oh_Heaven(Properties gameProperies) {
		super(700, 700, 30);
		setTitle("Oh_Heaven (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
		setStatusText("Initializing...");

		// 初始化对应的游戏的数据
		initialiseProperties(gameProperies);


		// 两种functions 的不同是一个用于显示， 一个是用于记录
		initScores();
		initScore();

		// 开始进行每一轮的记录
		for (int i = 0; i < nbRounds; i++) {

			initTricks();
			initRound();
			playRound();
			updateScores();
		}
		;

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

	private  void initialiseProperties(Properties properties){
		
	// Load the relevant parameters from the peroperties files before starting the game
		this.nbStartCards = properties.getProperty("nbStartCards") == null ? nbStartCards: Integer.parseInt(properties.getProperty("nbStartCards"));

		// ToDo: other parameters to load from the properties file (Similar to the above situation)

	}

	public static void main(String[] args) {
		// System.out.println("Working Directory = " + System.getProperty("user.dir"));
		final Properties properties;
		if (args == null || args.length == 0) { 
			properties = PropertiesLoader.loadPropertiesFile(null);
		} else {
			properties = PropertiesLoader.loadPropertiesFile(args[0]);
		}
		// Add the parameters to game main program
		new Oh_Heaven(properties);
	}

}
