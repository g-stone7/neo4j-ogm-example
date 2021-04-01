package com.neo4j.ogm.example.repository;

import com.neo4j.ogm.example.domain.BaseNode;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseRepository<T extends BaseNode> {

	@Autowired
	protected Session session;

	public T getById(String id) {
		return session.load(getEntityType(), id);
	}

	public void save(T entity) {
		session.save(entity, 1);
	}

	protected abstract Class<T> getEntityType();
}
