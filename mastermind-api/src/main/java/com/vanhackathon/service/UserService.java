package com.vanhackathon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.vanhackathon.domain.User;

@Service
public class UserService {

	@Autowired
	private MongoOperations mongoOperations;

	public void save(User user) {
		mongoOperations.save(user);
	}

	public boolean exists(User user) {
		Query query = new Query(Criteria.where("username").is(user.getUsername()));
		User dbUser = mongoOperations.findOne(query, User.class);

		if (dbUser != null) {
			return true;
		}

		return false;
	}
}
