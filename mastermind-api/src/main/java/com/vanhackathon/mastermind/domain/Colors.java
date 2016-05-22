package com.vanhackathon.mastermind.domain;

/**
 * All colors possible.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
public enum Colors {

	RED("R"), BLUE("B"), GREEN("G"), YELLOW("Y"); //, PURPLE("P"), ORANGE("O"),
						// CYAN("C"), MAGENTA("M");

	private String color;

	Colors(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public static String getColorValues() {
		Colors[] values = values();
		String v = "";
		for (int i = 0; i < values.length; i++) {
			Colors colors = values[i];
			v += colors.getColor();
		}
		return v;
	}

}
