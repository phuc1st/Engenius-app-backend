package com.phuc.profile.repository;

import com.phuc.profile.entity.GroupNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

public interface GroupNodeRepository extends Neo4jRepository<GroupNode, String> {
    @Query("MATCH (u:user_profile {userId: $userId}) " +
           "MATCH (g:group {id: $groupId}) " +
           "CREATE (u)-[:CREATED]->(g)")
    void createCreatedByRelationship(@Param("userId") String userId, @Param("groupId") String groupId);
} 