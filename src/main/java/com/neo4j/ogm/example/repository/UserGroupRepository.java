package com.neo4j.ogm.example.repository;

import com.neo4j.ogm.example.domain.UserGroup;
import org.neo4j.ogm.model.Result;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserGroupRepository extends BaseRepository<UserGroup> {

	public UserGroup upsertName(String id, String name) {
		Map<String, Object> queryParams = Map.of(
				"id", id,
				"name", name
		);
		final String query = "MATCH (userGroup:UserGroup)\n" +
				"WHERE userGroup.id = $id\n" +
				"SET userGroup.name = $name\n" +
				"RETURN userGroup LIMIT 1";
		Result result = session.query(query, queryParams, false);
		return result.queryResults().iterator().hasNext()
				? (UserGroup) result.queryResults().iterator().next().get("userGroup")
				: null;
	}

	@Override
	protected Class<UserGroup> getEntityType() {
		return UserGroup.class;
	}

}
