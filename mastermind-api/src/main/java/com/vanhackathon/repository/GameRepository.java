package com.vanhackathon.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.vanhackathon.mastermind.domain.Game;

/**
 * Controls access data to Game.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
@Service
public class GameRepository {

	@Autowired
	private MongoOperations mongoOperations;

	public Game save(Game game) {
		mongoOperations.save(game);
		return game;
	}
	
	public Game findByGameKey(String gameKey) {
		Game game = mongoOperations.findById(gameKey, Game.class);
		return game;
	}

}
