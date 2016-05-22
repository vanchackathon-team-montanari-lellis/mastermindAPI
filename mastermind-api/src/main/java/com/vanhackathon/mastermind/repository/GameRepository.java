package com.vanhackathon.mastermind.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.vanhackathon.mastermind.domain.Game;
import com.vanhackathon.mastermind.exception.GameNotFoundException;

/**
 * Controls access data to Game.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
@Repository
public class GameRepository {

	@Autowired
	private MongoOperations mongoOperations;

	public Game save(Game game) {
		mongoOperations.save(game);
		return game;
	}
	
	public Game findByGameKey(String gameKey) {
		Game game = mongoOperations.findById(gameKey, Game.class);
		if (game != null) {
			return game;
		}
		
		throw new GameNotFoundException(String.format("Game key [%s] not found", gameKey));
	}

}
