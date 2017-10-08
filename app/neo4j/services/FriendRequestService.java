package neo4j.services;

import neo4j.Neo4jSessionFactory;
import neo4j.nodes.UserNode;
import neo4j.relationships.FriendshipRequest;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;

public class FriendRequestService extends AbstractRelationshipService<FriendshipRequest> {

    @Inject
    public FriendRequestService(Neo4jSessionFactory neo4jSessionFactory) {
        super(neo4jSessionFactory);
    }

    @Override
    public Class<FriendshipRequest> getEntityType() {
        return FriendshipRequest.class;
    }

    public Optional<FriendshipRequest> findRequested(UserNode requests, UserNode requested) {
        String query = "Match(n:UserNode)-[r:FRIENDSHIP_REQUEST]->(m:UserNode) " +
                "WHERE ID(n)=" + requested.getId() + " AND ID(m)=" + requests.getId() + " " +
                "RETURN n,r,m";
        System.out.println(query);
        return Optional.ofNullable(session.queryForObject(FriendshipRequest.class, query, Collections.emptyMap()));
    }
}
