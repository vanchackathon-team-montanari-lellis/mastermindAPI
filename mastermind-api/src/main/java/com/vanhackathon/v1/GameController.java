package com.vanhackathon.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhackathon.exceptions.UserNotFoundException;
import com.vanhackathon.mastermind.dto.Colors;
import com.vanhackathon.mastermind.dto.Game;
import com.vanhackathon.mastermind.dto.Guess;
import com.vanhackathon.mastermind.dto.User;
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
	public ResponseEntity<List<Colors>> create() {

		Colors[] values = Colors.values();
		List<Colors> asList = Arrays.asList(values);

		return ResponseEntity.status(HttpStatus.CREATED).body(asList);
	}

	@RequestMapping(value = "/guess", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Guess guess) {

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@RequestMapping(value = "/createGame", method = RequestMethod.POST)
	public ResponseEntity<Game> createGame(@RequestBody String usernameHost) {
		// , @RequestBody boolean singlePlayer
		
		User hostUser;
		try {
			hostUser = userService.findByUsername(usernameHost);
		} catch (UserNotFoundException e) {
			throw new RuntimeException(e);
		}
		
		Game game = new Game();
		game.setHostUser(hostUser);
		game.setCreationDate(new Date());
		game.setSinglePlayer(true);

		// Creating secret for this game.
		List<Colors> colors = new ArrayList<Colors>();
		colors.add(Colors.BLUE);
		colors.add(Colors.GREEN);
		colors.add(Colors.RED);
		colors.add(Colors.YELLOW);
		
		game.setSecret(colors);
		
		game = gameService.save(game);

		return ResponseEntity.status(HttpStatus.CREATED).body(game);
	}
}
