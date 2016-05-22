package com.vanhackathon.mastermind.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Mastermind domain logic.
 */
@Document(collection = "games")
public class Game {

	private static final int TIME_WINDOW = 30 * 60 * 1000;
	private static final String COLORS = Colors.getColorValues();

	@Id
	private String gameKey;
	private long startTime = System.currentTimeMillis();
	private int totalGuesses;
	private GameStatus status = GameStatus.PLAYING;

	private List<Guess> guesses = new ArrayList<>();

	private String secret;
	private User hostUser;
	private boolean singlePlayer;
	
	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public void setSinglePlayer(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
	}

	public Game() {
		this.secret = this.generateSecretCode();
	}

	private String generateSecretCode() {
		Random random = new Random();
		String code = COLORS;
		// randomly choose a color sequence. repeated colors are allowed.
		return code.chars().mapToObj(c -> String.valueOf(code.charAt(random.nextInt(code.length()))))
				.collect(Collectors.joining());
	}

	public Game guess(String answer, String player) {
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

	private void checkTimeLimit() {
		if (isCompleted()) {
			return;
		}

		if (System.currentTimeMillis() - TIME_WINDOW > startTime) {
			this.status = GameStatus.TIME_IS_OVER;
		}
	}

	private void continuePlaying(Guess guess) {
		incrementGuesses();
		addGuess(guess);
	}

	private void gameSolved() {
		this.status = GameStatus.SOLVED;
	}

	private void incrementGuesses() {
		totalGuesses++;
	}

	private boolean isCompleted() {
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

	public User getHostUser() {
		return hostUser;
	}

	public void setHostUser(User hostUser) {
		this.hostUser = hostUser;
	}

	@Override
	public String toString() {
		return "Game [gameKey=" + gameKey + ", startTime=" + startTime + ", totalGuesses=" + totalGuesses + ", status="
				+ status + ", guesses=" + guesses + ", secret=" + secret + "]";
	}

}
