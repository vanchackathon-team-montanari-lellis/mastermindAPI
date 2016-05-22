package com.vanhackathon.mastermind.api.dto;

public class NewGameDTO {

	private String username;

	private boolean singlePlayer = true;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public void setSinglePlayer(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
	}

}
