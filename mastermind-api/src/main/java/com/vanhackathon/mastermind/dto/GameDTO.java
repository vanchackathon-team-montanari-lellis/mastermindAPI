package com.vanhackathon.mastermind.dto;

import java.util.List;

import com.vanhackathon.mastermind.domain.Colors;
import com.vanhackathon.mastermind.domain.User;

public class GameDTO {

	private String gameKey;

	private List<Colors> secret;

	private User hostUser;

	private boolean singlePlayer;

	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public void setSinglePlayer(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
	}

	public List<Colors> getSecret() {
		return secret;
	}

	public void setSecret(List<Colors> secret) {
		this.secret = secret;
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
