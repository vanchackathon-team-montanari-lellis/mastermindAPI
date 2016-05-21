package com.vanhackathon.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 
 * 
 * 
 */
public class Game {

    private static final int TIME_WINDOW = 30 * 60 * 1000;
    private static final String COLORS = "RBGYPOCM";

    private String id = UUID.randomUUID().toString();
    private long startTime = System.currentTimeMillis();
    private int totalGuesses;
    private GameStatus status = GameStatus.PLAYING;

    private List<Guess> guesses = new ArrayList<>();

    private String secret;

    public Game() {
        this.secret = this.generateSecretCode();
    }

    private String generateSecretCode() {
        Random random = new Random();
        String code = COLORS;
        return code
                .chars()
                .mapToObj(c -> String.valueOf(code.charAt(random.nextInt(code.length()))))
                .collect(Collectors.joining());
    }

    public Game guess(String answer) {
        checkTimeLimit();
        if (isCompleted()) {
            return this;
        }

        Guess guess = new Guess(answer);
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

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "Game [id=" + id + ", startTime=" + startTime + ", totalGuesses=" + totalGuesses + ", status=" + status
                + ", guesses=" + guesses + ", secret=" + secret + "]";
    }

}
