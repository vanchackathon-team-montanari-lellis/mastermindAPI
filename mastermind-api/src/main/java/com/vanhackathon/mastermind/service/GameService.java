package com.vanhackathon.mastermind.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.vanhackathon.mastermind.api.dto.GameDTO;
import com.vanhackathon.mastermind.api.dto.GuessDTO;
import com.vanhackathon.mastermind.domain.Colors;
import com.vanhackathon.mastermind.domain.Game;
import com.vanhackathon.mastermind.domain.GameStatus;
import com.vanhackathon.mastermind.domain.Guess;
import com.vanhackathon.mastermind.domain.User;
import com.vanhackathon.mastermind.exception.InvalidColorException;
import com.vanhackathon.mastermind.exception.UserNotFoundException;
import com.vanhackathon.mastermind.repository.GameRepository;
import com.vanhackathon.mastermind.repository.UsersRepository;

/**
 * Service layer is responsible for Mastermind logic regarding the users who are
 * playing (waiting another user, next turn, etc).
 *
 */
@Service
public class GameService {

	private GameRepository games;

	private UsersRepository users;

	@Autowired
	public GameService(GameRepository games, UsersRepository users) {
		this.games = games;
		this.users = users;
	}

	public GameDTO guess(GuessDTO guess) {
		guessSanityCheck(guess);

		Game game = games.findByGameKey(guess.getGameKey());
		game = game.guess(guess.getColors(), guess.getPlayer());
		game = games.save(game);
		return transformIntoGameDTO(game);
	}

	private void guessSanityCheck(GuessDTO guess) {
		if (StringUtils.isEmpty(guess.getPlayer())) {
			throw new IllegalArgumentException("Player can not be null.");
		}

		if (StringUtils.isEmpty(guess.getGameKey())) {
			throw new IllegalArgumentException("GameKey can not be null.");
		}

		if (StringUtils.isEmpty(guess.getColors())) {
			throw new IllegalArgumentException("Colors can not be null.");
		}

		// if (guess.getColors().length() != 8) {
		// throw new IllegalArgumentException("Colors length must be 8.");
		// }

		char[] colors = guess.getColors().toCharArray();
		for (char c : colors) {
			Colors color = Colors.getColorByChar(c);
			if (color == null) {
				throw new InvalidColorException(String.format("Color [%s] invalid.", c));
			}
		}

	}
	
	private GameDTO transformIntoGameDTO(Game game, String externalUser) {
		GameDTO gameDTO = transformIntoGameDTOBase(game);
		gameDTO.setCompleteGuesses(getGameGuessesOfUser(game, externalUser));

		return gameDTO;
	}

	private GameDTO transformIntoGameDTO(Game game) {
		GameDTO gameDTO = transformIntoGameDTOBase(game);
		gameDTO.setCompleteGuesses(getGameGuesses(game));

		return gameDTO;
	}
	
	private GameDTO transformIntoGameDTOBase(Game game) {
		GameDTO gameDTO = new GameDTO();
		gameDTO.setGameKey(game.getGameKey());
		gameDTO.setHostPlayer(game.getHostPlayer());
		gameDTO.setSecondPlayer(game.getSecondPlayer());
		gameDTO.setSinglePlayer(game.isSinglePlayer());
		gameDTO.setSolved(game.getStatus().equals(GameStatus.SOLVED));
		gameDTO.setStatus(game.getStatus().toString());
		gameDTO.setTotalGuesses(game.getTotalGuesses());

		if (gameDTO.isSolved()) {
			List<Guess> guesses = game.getGuesses();
			for (Guess guess : guesses) {
				if (guess.getStatus().equals(GameStatus.SOLVED)) {
					gameDTO.setWinner(guess.getPlayer());
				}
			}
		}

		return gameDTO;
	}

	// filter user guesses or return all guesses when the game is completed
	private List<Guess> getGameGuesses(Game game) {
		if (game.isCompleted() || game.isSinglePlayer()) {
			return game.getGuesses();
		}

		return game.getGuesses().stream().filter(g -> !g.getPlayer().equals(game.getNextTurn().getUsername()))
				.collect(Collectors.toList());
	}
	
	private List<Guess> getGameGuessesOfUser(Game game, String externalUser) {
		if (game.isCompleted() || game.isSinglePlayer()) {
			return game.getGuesses();
		}

		return game.getGuesses().stream().filter(g -> g.getPlayer().equals(externalUser))
				.collect(Collectors.toList());
	}

	public GameDTO newSinglePlayer(String username) {
		Game game = newSinglePlayerGame(username);
		games.save(game);
		return transformIntoGameDTO(game);
	}

	public GameDTO newMultiPlayer(String username) {
		newGameSanityCheck(username);

		Game game = games.findOneWaiting();
		if (game == null) {
			game = newMultiPlayerGame(username);

		} else if (!isPlayingAgainstMyself(username, game)) {
			game.play(findOrCreateUser(username));
		}

		game = games.save(game);
		return transformIntoGameDTO(game);
	}

	public GameDTO showGameStatus(String gameKey, String externalUser) {
		newGameSanityCheck(externalUser);
		gameKeySanityCheck(gameKey);

		Game game = games.findByGameKey(gameKey);

		// If its not solved yet, show only guesses from externalUser.
		GameDTO gameDTO = transformIntoGameDTO(game, externalUser);

		return gameDTO;
	}

	private Game newSinglePlayerGame(String username) {
		Game game = new Game(true);
		game.setHostPlayer(findOrCreateUser(username));
		return game;
	}

	private Game newMultiPlayerGame(String username) {
		Game game = new Game(false);
		game.setHostPlayer(findOrCreateUser(username));
		return game;
	}

	private boolean isPlayingAgainstMyself(String username, Game game) {
		return game.getHostPlayer().getUsername().equals(username);
	}

	private void newGameSanityCheck(String username) {
		if (StringUtils.isEmpty(username)) {
			throw new IllegalArgumentException("Username can not be null.");
		}
	}

	private void gameKeySanityCheck(String gameKey) {
		if (StringUtils.isEmpty(gameKey)) {
			throw new IllegalArgumentException("GameKey can not be null.");
		}
	}

	private User findOrCreateUser(String username) {
		try {
			return users.findByUsername(username);

		} catch (UserNotFoundException e) {
			// If it doesn't exist, lets create one.
			return users.save(new User(username));
		}
	}
}
