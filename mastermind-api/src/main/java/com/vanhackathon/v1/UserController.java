package com.vanhackathon.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vanhackathon.mastermind.dto.User;
import com.vanhackathon.repository.UsersRepository;

/**
 * Rest services to manage users.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
@RestController
@RequestMapping("/v1")
public class UserController {

	@Autowired
	private UsersRepository userService;

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody User user) {

		// Let it be created by Mongo.
		user.setId(null);

		boolean userAlreadyExists = userService.exists(user);

		if (!userAlreadyExists) {
			userService.save(user);
		}

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
