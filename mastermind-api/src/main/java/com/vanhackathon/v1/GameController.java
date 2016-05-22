package com.vanhackathon.v1;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhackathon.exceptions.UserNotFoundException;
import com.vanhackathon.mastermind.domain.Colors;
import com.vanhackathon.mastermind.domain.Game;
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
	public ResponseEntity<GuessDTO> guess(@RequestBody GuessDTO guess) {
		
		String gameKey = guess.getGameKey();
		Game game = gameService.findByGameKey(gameKey);
		
		User hostUser;
		try {
			hostUser = userService.findByUsername(game.getHostUser().getUsername());
		} catch (UserNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		// Domain class
		Game domainGame = new Game();

		return ResponseEntity.status(HttpStatus.CREATED).body(guess);
	}
	
	@RequestMapping(value = "/createGame", method = RequestMethod.POST)
	public ResponseEntity<GameDTO> createGame(@RequestBody String hostUsername) {
		
		User hostUser;
		try {
			hostUser = userService.findByUsername(hostUsername);
		} catch (UserNotFoundException e) {
			throw new RuntimeException(e);
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
		gameDTO.setSecret(Colors.tranformString(domainGame.getSecret()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(gameDTO);
	}
}
