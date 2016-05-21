package com.vanhackathon.v1;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhackathon.domain.Colors;
import com.vanhackathon.domain.Guess;
import com.vanhackathon.domain.User;

@RestController
@RequestMapping("/v1")
public class GameController {

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
	public ResponseEntity<Void> createGame(@RequestBody User user) {

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
