package com.phuc.profile.repository;

import com.phuc.profile.entity.GroupNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface GroupNodeRepository extends Neo4jRepository<GroupNode, String> {
} 