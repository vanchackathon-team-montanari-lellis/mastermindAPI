package com.vanhackathon.domain;

import java.math.BigInteger;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wordnik.swagger.annotations.ApiModelProperty;

@Document(collection = "games")
public class Game {

	@ApiModelProperty()
	@Id
	private BigInteger gameKey;

	@ApiModelProperty()
	private Date creationDate;

	public BigInteger getGameKey() {
		return gameKey;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setGameKey(BigInteger gameKey) {
		this.gameKey = gameKey;
	}

}
