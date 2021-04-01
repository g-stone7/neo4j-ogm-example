package com.neo4j.ogm.example.configuration;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Neo4jConfiguration {

	@Bean
	public Session session(SessionFactory sessionFactory) {
		return sessionFactory.openSession();
	}

	@Bean
	public SessionFactory sessionFactory(org.neo4j.ogm.config.Configuration configuration) {
		return new SessionFactory(configuration, "com.neo4j.ogm.example.domain");
	}

	@Bean
	public org.neo4j.ogm.config.Configuration getConfiguration() {
		return new org.neo4j.ogm.config.Configuration.Builder()
				.uri("bolt://localhost:7687")
				.credentials("neo4j", "neo4jneo4j")
				.build();
	}

}
