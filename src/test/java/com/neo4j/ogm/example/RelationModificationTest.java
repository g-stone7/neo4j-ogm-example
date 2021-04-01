package com.neo4j.ogm.example;

import com.neo4j.ogm.example.domain.User;
import com.neo4j.ogm.example.domain.UserGroup;
import com.neo4j.ogm.example.repository.UserGroupRepository;
import com.neo4j.ogm.example.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.ogm.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RelationModificationTest {

	@Autowired
	private Session session;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserGroupRepository userGroupRepository;

	private UserGroup userGroup = null;
	private UserGroup anotherUserGroup = null;
	private User user = null;

	@BeforeEach
	void setUp() {
		userGroup = UserGroup.builder()
				.name("User Group #1")
				.build();
		userGroupRepository.save(userGroup);

		anotherUserGroup = UserGroup.builder()
				.name("User Group #2")
				.build();
		userGroupRepository.save(anotherUserGroup);

		user = User.builder()
				.email("email@test.local")
				.username("username")
				.userGroup(userGroup)
				.build();
		userRepository.save(user);
		session.clear();
	}

	@AfterEach
	void tearDown (){
		session.purgeDatabase();
	}

	@Test
	void removeUserGroupRelation() {
		final UserGroup userGroup = userGroupRepository.getById(this.userGroup.getId());
		final User user = userRepository.getById(this.user.getId());

		// modify user group with custom query
		userGroupRepository.upsertName(userGroup.getId(), "User Group #1 - UPDATED");

		// remove relation from user group
		user.setUserGroup(null);
		userRepository.save(user);


		// refresh session to get current state from db
		session.clear();
		final User updatedUser = userRepository.getById(user.getId());
		// will fail because relation is not removed
		assertThat(updatedUser.getUserGroup()).isNull();
	}

	@Test
	void changeUserGroupRelation() {
		final UserGroup userGroup = userGroupRepository.getById(this.userGroup.getId());
		final User user = userRepository.getById(this.user.getId());

		// modify user group with custom query
		userGroupRepository.upsertName(userGroup.getId(), user.getUsername() + " Group");

		// remove relation from user group
		user.setUserGroup(anotherUserGroup);
		userRepository.save(user);


		// refresh session to get current state from db
		session.clear();
		final User updatedUser = userRepository.getById(user.getId());
		// will fail because relation is not removed
		assertThat(updatedUser.getUserGroup().getId()).isNotEqualTo(this.userGroup.getId());
		assertThat(updatedUser.getUserGroup().getId()).isEqualTo(this.anotherUserGroup.getId());
	}

}
