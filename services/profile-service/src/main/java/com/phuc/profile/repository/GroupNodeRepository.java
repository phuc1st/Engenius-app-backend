package com.phuc.profile.repository;

import com.phuc.profile.dto.response.GroupStatsDTO;
import com.phuc.profile.entity.GroupNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupNodeRepository extends Neo4jRepository<GroupNode, String> {
    @Query("""
    MATCH (u:user_profile {userId: $userId})
    MATCH (g:group {id: $groupId})
    MERGE (u)-[:CREATED]->(g)
    MERGE (u)-[:JOINED]->(g)
""")
    void createCreatedByRelationship(@Param("userId") String userId, @Param("groupId") String groupId);

    @Query("""
    MATCH (g:group)
    WHERE $searchQuery IS NULL OR toLower(g.name) CONTAINS toLower($searchQuery)
    OPTIONAL MATCH (u:user_profile)-[:JOINED]->(g)
    WITH g, count(u) AS memberCount
    OPTIONAL MATCH (currentUser:user_profile {userId: $userId})-[:JOINED]->(g)
    RETURN g, memberCount, count(currentUser) > 0 AS joined
    SKIP $skip
    LIMIT $limit
""")
    List<GroupStatsDTO> findGroupsWithStats(
            @Param("userId") String userId,
            @Param("searchQuery") String searchQuery,
            @Param("skip") long skip,
            @Param("limit") long limit
    );

    @Query("""
    MATCH (u:user_profile {userId: $userId})
    MATCH (g:group {id: $groupId})
    MERGE (u)-[:JOINED]->(g)
""")
    void joinGroup(@Param("userId") String userId, @Param("groupId") String groupId);
} 