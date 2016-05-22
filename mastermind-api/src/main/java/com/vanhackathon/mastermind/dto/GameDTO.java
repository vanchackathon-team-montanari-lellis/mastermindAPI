package com.vanhackathon.mastermind.dto;

import com.vanhackathon.mastermind.domain.User;

public class GameDTO {

	private String gameKey;

	private User hostUser;

	private boolean singlePlayer;

	private boolean solved;
	
	private Integer totalGuesses;
	
	public Integer getTotalGuesses() {
		return totalGuesses;
	}
	
	public void setTotalGuesses(Integer totalGuesses) {
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

	public User getHostUser() {
		return hostUser;
	}

	public void setHostUser(User hostUser) {
		this.hostUser = hostUser;
	}

	public String getGameKey() {
		return gameKey;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

}
