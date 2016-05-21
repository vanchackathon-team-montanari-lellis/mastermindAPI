package com.vanhackathon.domain;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User")
@Document(collection = "users")
public class User {

	@ApiModelProperty()
	@Id
	private BigInteger id;

	@ApiModelProperty()
	private String username;

	public String getUsername() {
		return username;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
