package com.vanhackathon.mastermind.api.dto;

import java.util.List;

import com.vanhackathon.mastermind.domain.Guess;
import com.vanhackathon.mastermind.domain.User;

public class GameDTO {

	private String gameKey;

	private User hostPlayer;
	private User secondPlayer;

	private boolean singlePlayer;
	private boolean solved;

	private int totalGuesses;

	private String status;

	private List<Guess> completeGuesses;
	
	private String winner;

	public int getTotalGuesses() {
		return totalGuesses;
	}

	public void setTotalGuesses(int totalGuesses) {
		this.totalGuesses = totalGuesses;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public void setSinglePlayer(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
	}

	public User getHostPlayer() {
		return hostPlayer;
	}

	public void setHostPlayer(User hostPlayer) {
		this.hostPlayer = hostPlayer;
	}

	public User getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(User secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setCompleteGuesses(List<Guess> guesses) {
		this.completeGuesses = guesses;
		
	}

	public List<Guess> getCompleteGuesses() {
		return completeGuesses;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public void setWinner(String winner) {
		this.winner = winner;
	}

}
