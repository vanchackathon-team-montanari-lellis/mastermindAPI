package com.vanhackathon.mastermind.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * All colors possible.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
public enum Colors {

	RED("R"), BLUE("B"), GREEN("G"), YELLOW("Y"), PURPLE("P"), ORANGE("O"), CYAN("C"), MAGENTA("M");

	private String color;

	Colors(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}
	
	public static String parseColors(List<String> colors) {
		String result = "";
		for (String string : colors) {
			result += string;
		}
		
		return result;
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
	
	public static Colors transform(String firstLetter) {
		Colors[] values = values();
		for (int i = 0; i < values.length; i++) {
			String colorFirstLetter = values[i].getColor();
			if (colorFirstLetter.equals(firstLetter)) {
				return values[i];
			}
		}

		return null;
	}
	
	public static List<Colors> tranformString(String entireSecret) {
		List<Colors> result = new ArrayList<>();
		
		char[] allColors = entireSecret.toCharArray();
		for (int i = 0; i < allColors.length; i++) {
			char c = allColors[i];
			result.add(Colors.transform(String.valueOf(c)));
		}
		
		return result;
	}

}
