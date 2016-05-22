package com.vanhackathon.mastermind.api.v1;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhackathon.mastermind.api.dto.GameDTO;
import com.vanhackathon.mastermind.api.dto.GuessDTO;
import com.vanhackathon.mastermind.domain.Colors;
import com.vanhackathon.mastermind.domain.Game;
import com.vanhackathon.mastermind.domain.GameStatus;
import com.vanhackathon.mastermind.domain.User;
import com.vanhackathon.mastermind.exception.GameNotFoundException;
import com.vanhackathon.mastermind.exception.UserNotFoundException;
import com.vanhackathon.mastermind.repository.GameRepository;
import com.vanhackathon.mastermind.repository.UsersRepository;

/**
 * Rest services to play games.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
@RestController
@RequestMapping("/v1")
public class GameController {

	private UsersRepository users;

	private GameRepository games;

	@Autowired
	public GameController(UsersRepository users, GameRepository games) {
		this.users = users;
		this.games = games;
	}

	@RequestMapping(value = "/colors", method = RequestMethod.GET)
	public ResponseEntity<List<Colors>> getAllColors() {
		List<Colors> colors = Arrays.asList(Colors.values());
		return ResponseEntity.status(HttpStatus.OK).body(colors);
	}

	@RequestMapping(value = "/guess", method = RequestMethod.POST)
	public ResponseEntity<GameDTO> guess(@RequestBody GuessDTO guess) {
		guessSanityCheck(guess);

		Game game = games.findByGameKey(guess.getGameKey());
		game = game.guess(guess.getColorsGuessed(), guess.getUsernameOfGuesser());
		game = games.save(game);

		GameDTO gameDTO = transformIntoGameDTO(game);
		return ResponseEntity.status(HttpStatus.OK).body(gameDTO);
	}

	private GameDTO transformIntoGameDTO(Game game) {
		GameDTO gameDTO = new GameDTO();
		gameDTO.setGameKey(game.getGameKey());
		gameDTO.setHostUser(game.getHostUser());
		gameDTO.setSinglePlayer(game.isSinglePlayer());
		gameDTO.setSolved(game.getStatus().equals(GameStatus.SOLVED));
		gameDTO.setTotalGuesses(game.getTotalGuesses());
		return gameDTO;
	}

	private void guessSanityCheck(GuessDTO guess) {
		if (StringUtils.isEmpty(guess.getUsernameOfGuesser())) {
			throw new IllegalArgumentException("Username can not be null.");
		}

		if (StringUtils.isEmpty(guess.getGameKey())) {
			throw new IllegalArgumentException("GameKey can not be null.");
		}

		if (StringUtils.isEmpty(guess.getColorsGuessed())) {
			throw new IllegalArgumentException("Colors can not be null.");
		}

		if (guess.getColorsGuessed().length() != 8) {
			throw new IllegalArgumentException("Colors length must be 8.");
		}
	}

	@RequestMapping(value = "/createGame", method = RequestMethod.POST)
	public ResponseEntity<GameDTO> createGame(@RequestBody(required = true) String hostUsername) {
		newGameSanityCheck(hostUsername);

		Game game = new Game();
		game.setHostUser(findHostUser(hostUsername));
		game.setSinglePlayer(true);
		game = games.save(game);

		GameDTO gameDTO = transformIntoGameDTO(game);
		return ResponseEntity.status(HttpStatus.CREATED).body(gameDTO);
	}

	private User findHostUser(String hostUsername) {
		try {
			return users.findByUsername(hostUsername);

		} catch (UserNotFoundException e) {
			// If it doesn't exist, lets create one.
			User hostUser = new User();
			hostUser.setUsername(hostUsername);
			users.save(hostUser);
			return hostUser;
		}
	}

	private void newGameSanityCheck(String hostUsername) {
		if (StringUtils.isEmpty(hostUsername)) {
			throw new IllegalArgumentException("Username can not be null.");
		}
	}

	@ExceptionHandler({ IllegalArgumentException.class, GameNotFoundException.class })
	public void handleIllegalArguments(HttpServletResponse response, Exception e) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
	}
}
