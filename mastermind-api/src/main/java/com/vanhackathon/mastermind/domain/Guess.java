package com.vanhackathon.mastermind.domain;

/**
 * Each guess for each player.
 * Describes player's answer, exact and near pens and whether the guess solved the game or not.
 * 
 */
public class Guess {
    private String answer;
    private int exactPens;
    private int nearPens;
    private String player;

    private GameStatus status = GameStatus.PLAYING;

    public Guess(String answer, String player) {
        this.answer = answer.toUpperCase();
        this.player = player;
    }

    private int findExactPens(String secret) {
        int exact = 0;
        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == secret.charAt(i)) {
                exact++;
            }
        }
        exactPens = exact;
        return exactPens;
    }

    private void findNearPens(String secret) {
        int exactPens = findExactPens(secret);
        int near = 0;
        for (int i = 0; i < answer.length(); i++) {
            if (secret.indexOf(answer.charAt(i)) >= 0) {
                near++;
                secret = secret.replace(answer.charAt(i), '_');
            }
        }
        near = near - exactPens;
        nearPens = (near < 0) ? 0 : near;
    }

    public boolean solve(String secret) {
        findExactPens(secret);
        findNearPens(secret);

        if (secret.equals(answer)) {
            this.status = GameStatus.SOLVED;
            return true;
        }
        return false;
    }

    @Override
	public String toString() {
		return "Guess [answer=" + answer + ", exactPens=" + exactPens + ", nearPens=" + nearPens + ", player=" + player
				+ ", status=" + status + "]";
	}
}
