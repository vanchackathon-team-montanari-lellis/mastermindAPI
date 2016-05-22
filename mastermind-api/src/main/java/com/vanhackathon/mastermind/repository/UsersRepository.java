package com.vanhackathon.mastermind.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.vanhackathon.mastermind.domain.User;
import com.vanhackathon.mastermind.exception.UserNotFoundException;

/**
 * Controls access data to Users.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
@Repository
public class UsersRepository {

	private MongoOperations mongoOperations;

	@Autowired
	public UsersRepository(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public void save(User user) {
		mongoOperations.save(user);
	}

	public boolean exists(User user) {
		Query query = new Query(Criteria.where("username").is(user.getUsername()));
		User dbUser = mongoOperations.findOne(query, User.class);
		return dbUser != null;
	}

	public User findByUsername(String username) throws UserNotFoundException {
		Query query = new Query(Criteria.where("username").is(username));
		User dbUser = mongoOperations.findOne(query, User.class);

		if (dbUser != null) {
			return dbUser;
		}

		throw new UserNotFoundException("User not found by username=" + username);
	}
}
