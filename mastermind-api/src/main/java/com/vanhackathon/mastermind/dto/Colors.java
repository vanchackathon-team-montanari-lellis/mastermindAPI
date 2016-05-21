package com.vanhackathon.mastermind.dto;

/**
 * All colors possible.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
public enum Colors {

	RED("red"), BLUE("blue"), GREEN("green"), YELLOW("yellow");

	private String color;

	Colors(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

}
