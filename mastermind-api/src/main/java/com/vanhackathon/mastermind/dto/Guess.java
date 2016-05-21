package com.vanhackathon.mastermind.dto;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "Guess")
public class Guess {

	private String usernameHost;

	private String gameKey;

	private List<String> colorsGuessed;

	public String getGameKey() {
		return gameKey;
	}

	public String getUsernameHost() {
		return usernameHost;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

	public void setUsernameHost(String usernameHost) {
		this.usernameHost = usernameHost;
	}

	public List<String> getColorsGuessed() {
		return colorsGuessed;
	}

	public void setColorsGuessed(List<String> colorsGuessed) {
		this.colorsGuessed = colorsGuessed;
	}

}
