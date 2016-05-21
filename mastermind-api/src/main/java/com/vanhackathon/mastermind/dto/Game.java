package com.vanhackathon.mastermind.dto;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "Game")
@Document(collection = "games")
public class Game {

	@Id
	private String gameKey;

	private Date creationDate;

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setGameKey(String gameKey) {
		this.gameKey = gameKey;
	}

}
