package com.vanhackathon.dto;

import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "Guess")
public class GuessDTO {

	private List<String> colorsGuessed;

	public List<String> getColorsGuessed() {
		return colorsGuessed;
	}

	public void setColorsGuessed(List<String> colorsGuessed) {
		this.colorsGuessed = colorsGuessed;
	}

}
