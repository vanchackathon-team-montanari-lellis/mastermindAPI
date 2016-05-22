package com.vanhackathon.mastermind.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vanhackathon.mastermind.exception.NotYourTurnException;

/**
 * Mastermind domain logic.
 */
@Document(collection = "games")
public class Game {

	private static final int TIME_WINDOW_IN_MS = 30 * 60 * 1000;

	@Id
	private String gameKey;
	private long startTime = System.currentTimeMillis();
	private String secret;
	private int totalGuesses;
	private GameStatus status;

	private List<Guess> guesses = new ArrayList<>();

	private boolean isSinglePlayer = true;
	private User hostPlayer;
	private User secondPlayer;

	private User nextTurn;

	/**
	 * Create a single or multi player game.
	 * 
	 * @param isSinglePlayer
	 *            whether the game should be created as single or multi player.
	 */
	@JsonCreator
	public Game(@JsonProperty("isSinglePlayer") boolean isSinglePlayer) {
		this.secret = this.generateSecretCode();
		this.status = isSinglePlayer ? GameStatus.READY : GameStatus.WAITING;
	}

	private String generateSecretCode() {
		Random random = new Random();
		String code = Colors.getColorValues();
		// randomly choose a color sequence. repeated colors are allowed.
		return code.chars().mapToObj(c -> String.valueOf(code.charAt(random.nextInt(code.length()))))
				.collect(Collectors.joining());
	}

	public Game guess(String answer, String player) {
		playing(player);

		checkTimeLimit();
		if (isCompleted()) {
			return this;
		}

		Guess guess = new Guess(answer, player);
		if (guess.solve(secret)) {
			gameSolved();
		}

		continuePlaying(guess);
		return this;
	}

	private void playing(String player) {
		this.status = GameStatus.READY.equals(status) ? GameStatus.PLAYING : status;

		// turn set to first user
		if (nextTurn == null) {
			nextTurn = new User(player);
		} else if (!nextTurn.equals(new User(player))) {
			throw new NotYourTurnException("It is not your turn! Please wait.");
		}
	}

	private void checkTimeLimit() {
		if (isCompleted()) {
			return;
		}

		if (System.currentTimeMillis() - TIME_WINDOW_IN_MS > startTime) {
			this.status = GameStatus.TIME_IS_OVER;
		}
	}

	private void continuePlaying(Guess guess) {
		nextTurn();
		incrementGuesses();
		addGuess(guess);
	}

	private void nextTurn() {
		nextTurn = nextTurn.equals(hostPlayer) ? secondPlayer : hostPlayer;
	}

	public void play(User secondPlayer) {
		setSecondPlayer(secondPlayer);
		status = GameStatus.READY;
	}

	private void gameSolved() {
		this.status = GameStatus.SOLVED;
	}

	private void incrementGuesses() {
		totalGuesses++;
	}

	public boolean isCompleted() {
		return GameStatus.SOLVED.equals(status) || GameStatus.TIME_IS_OVER.equals(status);
	}

	private void addGuess(Guess guess) {
		guesses.add(guess);
	}

	public long getStartTime() {
		return startTime;
	}

	public int getTotalGuesses() {
		return totalGuesses;
	}

	public GameStatus getStatus() {
		return status;
	}

	public List<Guess> getGuesses() {
		return guesses;
	}

	public String getSecret() {
		return secret;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	@Override
	public String toString() {
		return "Game [gameKey=" + gameKey + ", startTime=" + startTime + ", secret=" + secret + ", totalGuesses="
				+ totalGuesses + ", status=" + status + ", guesses=" + guesses + ", isSinglePlayer=" + isSinglePlayer
				+ ", hostPlayer=" + hostPlayer + ", secondPlayer=" + secondPlayer + ", nextTurn=" + nextTurn + "]";
	}

	public boolean isSinglePlayer() {
		return isSinglePlayer;
	}

	public void setSecondPlayer(User secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public User getSecondPlayer() {
		return secondPlayer;
	}

	public User getHostPlayer() {
		return hostPlayer;
	}

	public void setHostPlayer(User hostPlayer) {
		this.hostPlayer = hostPlayer;
	}

	public User getNextTurn() {
		return nextTurn;
	}

}
