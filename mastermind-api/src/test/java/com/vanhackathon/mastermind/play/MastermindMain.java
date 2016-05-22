package com.vanhackathon.mastermind.play;

import java.util.Scanner;

import com.vanhackathon.mastermind.domain.Game;

public class MastermindMain {

    public static void main(String[] args) {
        Game game = new Game();
        System.out.println("DO NOT CHEAT! ;) ");
        System.out.println(game);
        
        try (Scanner scanner = new Scanner(System.in)) {
            String read = "";
            while (true) {
                System.out.print("Guess next sequence: ");
                read = scanner.nextLine();
                if ("quit".equals(read)) {
                    System.exit(1);
                }

                Game guess = game.guess(read, "test");
                System.out.println(guess.getGuesses().get(guess.getGuesses().size() - 1));
            }

        }

    }

}
