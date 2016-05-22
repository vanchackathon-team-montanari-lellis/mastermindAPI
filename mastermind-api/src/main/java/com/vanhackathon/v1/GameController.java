package com.vanhackathon.v1;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhackathon.exceptions.UserNotFoundException;
import com.vanhackathon.mastermind.domain.Colors;
import com.vanhackathon.mastermind.domain.Game;
import com.vanhackathon.mastermind.domain.GameStatus;
import com.vanhackathon.mastermind.domain.User;
import com.vanhackathon.mastermind.dto.GameDTO;
import com.vanhackathon.mastermind.dto.GuessDTO;
import com.vanhackathon.repository.GameRepository;
import com.vanhackathon.repository.UsersRepository;

/**
 * Rest services to play games.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
@RestController
@RequestMapping("/v1")
public class GameController {
	
	@Autowired
	private UsersRepository userService;
	
	@Autowired
	private GameRepository gameService;

	@RequestMapping(value = "/colors", method = RequestMethod.GET)
	public ResponseEntity<List<Colors>> getAllColors() {

		Colors[] values = Colors.values();
		List<Colors> asList = Arrays.asList(values);

		return ResponseEntity.status(HttpStatus.CREATED).body(asList);
	}

	@RequestMapping(value = "/guess", method = RequestMethod.POST)
	public ResponseEntity<GameDTO> guess(@RequestBody GuessDTO guess) {
		if (StringUtils.isEmpty(guess.getUsernameOfGuesser())) {
			// Username cant be null.
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		if (StringUtils.isEmpty(guess.getGameKey())) {
			// GameKey cant be null.
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		if (StringUtils.isEmpty(guess.getColorsGuessed())) {
			// Colors cant be null.
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		if (guess.getColorsGuessed().length() != 8) {
			// Colors size cant be less than 8.
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		
		String gameKey = guess.getGameKey();
		Game game = gameService.findByGameKey(gameKey);
		
		String secretGuessed = guess.getColorsGuessed();
		
		game = game.guess(secretGuessed);
		
		game = gameService.save(game);
		
		// Transform into DTO.
		GameDTO gameDTO = new GameDTO();
		gameDTO.setGameKey(game.getGameKey());
		gameDTO.setHostUser(game.getHostUser());
		gameDTO.setSinglePlayer(game.isSinglePlayer());
		gameDTO.setSolved(game.getStatus().equals(GameStatus.SOLVED));
		gameDTO.setTotalGuesses(game.getTotalGuesses());

		return ResponseEntity.status(HttpStatus.CREATED).body(gameDTO);
	}
	
	@RequestMapping(value = "/createGame", method = RequestMethod.POST)
	public ResponseEntity<GameDTO> createGame(@RequestBody(required=true) String hostUsername) {
		if (StringUtils.isEmpty(hostUsername)) {
			// Username cant be null.
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		User hostUser;
		try {
			hostUser = userService.findByUsername(hostUsername);
		} catch (UserNotFoundException e) {
			// If it doesnt exist, lets create one.
			hostUser = new User();
			hostUser.setUsername(hostUsername);
			userService.save(hostUser);
		}
		
		// Domain class to create secret.
		Game domainGame = new Game();
		domainGame.setHostUser(hostUser);
		domainGame.setSinglePlayer(true);
		
		domainGame = gameService.save(domainGame);
		
		GameDTO gameDTO = new GameDTO();
		gameDTO.setGameKey(domainGame.getGameKey());
		gameDTO.setHostUser(domainGame.getHostUser());
		gameDTO.setSinglePlayer(domainGame.isSinglePlayer());
		gameDTO.setSolved(false);
		gameDTO.setTotalGuesses(0);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(gameDTO);
	}
}
