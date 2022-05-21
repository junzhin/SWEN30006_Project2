package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import oh_heaven.game.player.HumanPlayer;
import oh_heaven.game.player.NonHumanPlayer;
import oh_heaven.game.player.Player;
import oh_heaven.game.playerStrategy.StrategyType;

import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")

public class Oh_Heaven extends CardGame {
	/*=============================================================================================================== */

	/*******Defintions of Graphical Settings, Constants and Variables ********/
	// 定义受手牌的花色
	public enum Suit {
		SPADES, HEARTS, DIAMONDS, CLUBS
	}

	// 定义rank
	public enum Rank {
		// Reverse order of rank importance (see rankGreater() below)
		// Order of cards is tied to card images
		ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
	}

	// 图像对应的花色， 用于游戏graphical purposes
	final String trumpImage[] = { "bigspade.gif", "bigheart.gif", "bigdiamond.gif", "bigclub.gif" };

	private final String version = "1.0";
	// 玩家数量
	public final int nbPlayers = 4;
	// 每个玩家的数量
	public int nbStartCards = 13;
	// 每局游戏的轮数
	public int nbRounds = 3;
	// 当赢得回合数量与预测胜利回合数量相等的时候的格外奖励
	public final int madeBidBonus = 10;

	private ArrayList<String> playerType = new ArrayList<>();
	private ArrayList<Player> players = new ArrayList<>();

	// 随机seeds 设置
	public static int seed = 30006;

	// 图像的设置
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

	// 是否设置游戏需要施加游戏规则， *不确定(需要configure)
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

	// Setting random seed for the game behaviour
	private static Random random = new Random(seed);

	/*****End of  Defintions of Graphical Settings, Constants and Variables *****/

	/*=============================================================================================================== */

	/******************Helper Functions**************/

	// 回合开始的时候随机发牌
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

	// 用于比较两张手牌的大小
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
	 * 复原用于记录的scores 的 array
	 */
	private void initScores() {
		for (int i = 0; i < nbPlayers; i++) {
			scores[i] = 0;
		}
	}

	/**
	 * 重置记录赢得比赛次数的array， 每一回合重置一次
	 */
	private void initTricks() {
		for (int i = 0; i < nbPlayers; i++) {
			tricks[i] = 0;
		}
	}
	
	private void initScoreForGraphicalPurpose() {
		for (int i = 0; i < nbPlayers; i++) {
			// scores[i] = 0;
			String text = "[" + String.valueOf(scores[i]) + "]" + String.valueOf(tricks[i]) + "/"
					+ String.valueOf(bids[i]);
			scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
			addActor(scoreActors[i], scoreLocations[i]);
		}
	}


	private void initialiseProperties(Properties properties) {

		// Load the relevant parameters from the peroperties files before starting the
		// game
		this.nbStartCards = properties.getProperty("nbStartCards") == null ? this.nbStartCards
				: Integer.parseInt(properties.getProperty("nbStartCards"));

		// ToDo: other parameters to load from the properties file (Similar to the above
		// situation)

		this.enforceRules = properties.getProperty("enforceRules") == null ? this.enforceRules
				: Boolean.parseBoolean(properties.getProperty("enforceRules"));

		this.nbRounds = properties.getProperty("rounds") == null ? this.nbRounds
				: Integer.parseInt(properties.getProperty("rounds"));

		this.seed = properties.getProperty("seed") == null ? this.seed
				: Integer.parseInt(properties.getProperty("seed"));
		playerType.addAll(PropertiesLoader.loadPlayerTypes(properties));

	}

	/**
	 * 初始化 bids 值
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
		for (Player player: players) {
			if (player instanceof HumanPlayer) {
				((HumanPlayer)player).initialiseCardListener();
			}
		}
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



	/**
	 * 更新用于graphical rendering 的 Scores 的 Array， ！大概率没有用
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
	 *  更新玩家对应当前的游戏分数的数组
	 */
	private void updateScores() {
		for (int i = 0; i < nbPlayers; i++) {
			scores[i] += tricks[i];
			if (tricks[i] == bids[i])
				scores[i] += madeBidBonus;
		}
	}

	/****************** End of Initialization and Updating **************/

	/*=============================================================================================================== */

	/****One Round of the game ****/

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
			/*if (0 == nextPlayer) { // Select lead depending on player type
				hands[0].setTouchEnabled(true);
				setStatus("Player 0 double-click on card to lead.");
				while (null == selected)
					delay(100);
			} else {
				setStatusText("Player " + nextPlayer + " thinking...");
				delay(thinkingTime);
				selected = randomCard(hands[nextPlayer]);
			}*/
			selected = players.get(nextPlayer).playOneCard(roundInfo);



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
				/*if (0 == nextPlayer) {
					hands[0].setTouchEnabled(true);
					setStatus("Player 0 double-click on card to follow.");
					while (null == selected)
						delay(100);
				} else {
					setStatusText("Player " + nextPlayer + " thinking...");
					delay(thinkingTime);
					selected = randomCard(hands[nextPlayer]);
				}*/
				selected = players.get(nextPlayer).playOneCard(roundInfo);
				// Follow with selected card
				trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards() + 2) * trickWidth));
				trick.draw();
				selected.setVerso(false); // In case it is upside down
				// Check: Following card must follow suit if possible
				// 这里应该是有关游戏规则的检查的设置， 看花色是否等于lead 的花色， 如果不是检查玩家手牌中有没有与lead 花色一致的手牌， 没有则违反规则
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

	/**** End of One Round of the game ****/
	/*
	 * ==============================================================================================================*/


	private void initialisePlayers() {
		for (int i=0;i<nbPlayers;i++) {
			String currentPlayerType = playerType.get(i);
			System.out.println(currentPlayerType);
			if (currentPlayerType.equals("random")) {
				players.add(new NonHumanPlayer(i, StrategyType.random));
			}
			else if (currentPlayerType.equals("human")) {
				players.add(new HumanPlayer(i));
			}
			else if (currentPlayerType.equals("legal")) {
				players.add(new NonHumanPlayer(i,StrategyType.legal));
			}
			else if (currentPlayerType.equals("smart")) {
				players.add(new NonHumanPlayer(i,StrategyType.smart));
			}
			String text = "[" + String.valueOf(scores[i]) + "]" + String.valueOf(tricks[i]) + "/" + String.valueOf(bids[i]);
			scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
			addActor(scoreActors[i], scoreLocations[i]);
		}
	}

	/**** Running Process of the Game Program *****/
	public Oh_Heaven(Properties gameProperies) {
		super(700, 700, 30);
		setTitle("Oh_Heaven (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
		setStatusText("Initializing...");

		// 初始化对应的游戏的数据
		initialiseProperties(gameProperies);
		initialisePlayers();

		// 两种functions 的不同是一个用于显示， 一个是用于记录
		initScores();
		//initScoreForGraphicalPurpose();

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
