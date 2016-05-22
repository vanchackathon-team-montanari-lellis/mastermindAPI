package com.vanhackathon.mastermind.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClientURI;

/**
 * Mongo configuration class.
 * 
 * We preferred to store our data on the cloud. We left the localhost version
 * commented for testing.
 * 
 * @author lmontanari (lucas_montanari@hotmail.com)
 */
@Configuration
public class MongoConfig {

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {

		// Connecting to the cloud.
		MongoClientURI uri = new MongoClientURI(
				"mongodb://vancouver_user:vancouver2016@ds011893.mlab.com:11893/mastermind");
		SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(uri);

		// Or connect localhost instead.
		// SimpleMongoDbFactory simpleMongoDbFactory = new
		// SimpleMongoDbFactory(new MongoClient(), "mastermind");

		return simpleMongoDbFactory;
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}
}
