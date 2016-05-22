package com.vanhackathon.mastermind.dto;

public class GuessDTO {

	private String usernameOfGuesser;

	private String gameKey;

	private String colorsGuessed;

	public String getGameKey() {
		return gameKey;
	}

	public String getUsernameOfGuesser() {
		return usernameOfGuesser;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public void setUsernameOfGuesser(String usernameOfGuesser) {
		this.usernameOfGuesser = usernameOfGuesser;
	}

	public String getColorsGuessed() {
		return colorsGuessed;
	}
	
	public void setColorsGuessed(String colorsGuessed) {
		this.colorsGuessed = colorsGuessed;
	}

}
