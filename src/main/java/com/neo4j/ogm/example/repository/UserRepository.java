package com.neo4j.ogm.example.repository;

import com.neo4j.ogm.example.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends BaseRepository<User> {

	@Override
	protected Class<User> getEntityType() {
		return User.class;
	}

}
