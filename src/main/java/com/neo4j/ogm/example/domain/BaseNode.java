package com.neo4j.ogm.example.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.id.UuidStrategy;

@Data
@SuperBuilder
@NodeEntity
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseNode {

	@Id
	@GeneratedValue
	private Long nodeId;
	@Id
	@GeneratedValue(strategy = UuidStrategy.class)
	private String id;

}
